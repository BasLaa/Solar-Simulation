package gui.graphics2d;

import java.util.ArrayList;

import functions.AccelerationFunction;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import readers.APIReader2;
import readers.FileReader;
import state.*;
import gui.graphics2d.model.*;
import vector.Vector3D;

public class Gui extends Application {
    /** Number of seconds to update the gui.graphics2d.model with for each iteration (delta T) */
    public static final double TIME_STEP = 24*3600;
    public static final double MAX_TIME = 3600 * 24 * 365*4;
    public static final double TOTAL_STEPS = MAX_TIME / TIME_STEP;
    public static final double stepsPerDay = 144;

    /** initial scale pixel/meter */
    public static final double SCALE =1e8;

    /** radius in pixels of body in gui */
    public static final double BODY_RADIUS_GUI = 1;

    private static final int BOTTOM_AREA_HEIGHT = 100;

    private int iterator = 0;

    /** bodies in system rendered by gui */
    private BodySystem bodySystem;
    private ArrayList<Vector3D>[] expectedSystem;
    private state.State[] stateSystem;

    /** metrics for the timer */
    private static final int SEC_IN_MINUTE = 60;
    private static final int SEC_IN_HOUR = SEC_IN_MINUTE * 60;
    private static final int SEC_IN_DAY = SEC_IN_HOUR * 24;
    private static final int SEC_IN_YEAR = 31536000;
    private long elapsedSeconds = 0;

    /** transforms between coordinates in gui.graphics2d.model and coordinates in gui */
    private CoordinatesTransformer transformer = new CoordinatesTransformer();

    private double canvasWidth = 0;
    private double canvasHeight = 0;

    private Label timeLabel;

    private FileReader reader;

    @Override
    public void start(Stage stage) {
        bodySystem = new SolarSystem();
        stateSystem = createTrajectories();
        System.out.println(stateSystem[0].getPosition(1).toString()+"\n"+stateSystem[1].getPosition(1).toString());

        reader = new FileReader();
        expectedSystem = (ArrayList<Vector3D>[]) new ArrayList[9];
        expectedSystem[0] = reader.readFile("sun.txt");
        expectedSystem[1] = reader.readFile("mercury.txt");
        expectedSystem[2] = reader.readFile("venus.txt");
        expectedSystem[3] = reader.readFile("earth.txt");
        expectedSystem[4] = reader.readFile("moon.txt");
        expectedSystem[5] = reader.readFile("mars.txt");
        expectedSystem[6] = reader.readFile("jupiter.txt");
        expectedSystem[7] = reader.readFile("saturn.txt");
        expectedSystem[8] = reader.readFile("titan.txt");

        
        transformer.setScale(SCALE);
        transformer.setOriginXForScale(850);
        transformer.setOriginYForScale(800);
        GraphicsContext gc = createGui(stage);
        Timeline timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        KeyFrame kf = new KeyFrame(
                Duration.millis(200),
                new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent ae) {
                        if (iterator >= TOTAL_STEPS) {
                            elapsedSeconds = 0;
                            iterator = 0;
                        }
                        updateFrame(gc);
                        iterator++;
                    }
                });
        timeline.getKeyFrames().add(kf);
        timeline.play();
        stage.show();
    }

    private void updateFrame(GraphicsContext gc) {
        // clear the canvas
        this.canvasWidth = gc.getCanvas().getWidth();
        this.canvasHeight = gc.getCanvas().getHeight();
        gc.clearRect(0, 0, canvasWidth, canvasHeight);


        State currentState = stateSystem[(int) ((iterator+1)*stepsPerDay)];

        for (Body body : bodySystem.getBodies()) {

            double scaleX = transformer.modelToScaleX(body.location.getX());
            double scaleY = transformer.modelToScaleY(body.location.getY());

            // draw object circle
            gc.setFill(Color.BLACK);
            gc.fillOval(scaleX - BODY_RADIUS_GUI, scaleY - BODY_RADIUS_GUI, BODY_RADIUS_GUI * 2, BODY_RADIUS_GUI * 2);

            // draw label
            Text text = new Text(body.name);
            gc.fillText(body.name, scaleX - (text.getLayoutBounds().getWidth() / 2), scaleY - BODY_RADIUS_GUI - (text.getLayoutBounds().getHeight() / 2));
        }

        for (int i = 0; i < bodySystem.getBodies().size(); i++) {
            Body currBody = bodySystem.getBodies().get(i);
            currBody.updateLocation(currentState.getPosition(i));
        }

        timeLabel.setText(getElapsedTimeAsString());
        elapsedSeconds += TIME_STEP;

        for (ArrayList<Vector3D> al : expectedSystem) {
            // plot expected
        Vector3D curr = al.get(iterator);
        double scaleX = transformer.modelToScaleX(curr.getX());
        double scaleY = transformer.modelToScaleY(curr.getY());

        // draw object circle
        gc.setFill(Color.RED);
        gc.fillOval(scaleX - BODY_RADIUS_GUI, scaleY - BODY_RADIUS_GUI, BODY_RADIUS_GUI * 2, BODY_RADIUS_GUI * 2);
        }

    }

    private GraphicsContext createGui(Stage stage) {
        BorderPane border = new BorderPane();
        createTimeLabel();
        HBox hbox = createHBox();
        border.setBottom(hbox);
        Canvas canvas = createCanvas();
        border.setCenter(canvas);
        stage.setTitle("Simulation");
        Scene scene = new Scene(border);
        stage.setScene(scene);
        stage.setMaximized(true);

        // apparently this is important
        canvas.widthProperty().bind(stage.widthProperty());
        canvas.heightProperty().bind(stage.heightProperty().subtract(BOTTOM_AREA_HEIGHT));
        return canvas.getGraphicsContext2D();
    }

    private Canvas createCanvas() {
        Canvas canvas = new ResizableCanvas();

        // zooming (scaling)
        canvas.setOnScroll((event) -> {
            if (event.getDeltaY() > 0) {
                transformer.setScale(transformer.getScale() * 0.9);
            } else {
                transformer.setScale(transformer.getScale() * 1.1);
            }
        });

        //dragging
		canvas.setOnMouseDragged((event) -> {
			transformer.setOriginXForScale(event.getScreenX());
			transformer.setOriginYForScale(event.getScreenY());
		});
        return canvas;
    }

    private HBox createHBox() {
        HBox hbox = new HBox();
        hbox.setPadding(new Insets(15, 12, 15, 12));
        hbox.setSpacing(10);
        hbox.setStyle("-fx-background-color: #336699;");
        hbox.setFillHeight(true);
        hbox.getChildren().add(this.timeLabel);
        return hbox;
    }

    private void createTimeLabel() {
        timeLabel = new Label();
        timeLabel.setPrefSize(500, 20);
    }

    private State[] createTrajectories() {
	    ODEMidpointSolver solve = new ODEMidpointSolver();
	    State[] stateSystem = (state.State[]) solve.solve(new AccelerationFunction(), APIReader2.read(), MAX_TIME, TIME_STEP/stepsPerDay);
        return stateSystem;
    }

    public String getElapsedTimeAsString() {
        long years = elapsedSeconds / SEC_IN_YEAR;
        long days = (elapsedSeconds % SEC_IN_YEAR) / SEC_IN_DAY;
        long hours = ( (elapsedSeconds % SEC_IN_YEAR) % SEC_IN_DAY) / SEC_IN_HOUR;
        return String.format("Years: %02d,   Days: %03d,   Hours: %02d", years, days, hours);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
