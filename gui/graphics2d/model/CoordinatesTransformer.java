package gui.graphics2d.model;

/**
 * Transforms coordinates from the gui.graphics2d.model to a Scale coordinatesystem
 */
public class CoordinatesTransformer {

    private  double scale;
    private double originXForScale;
    private double originYForScale;

    public double getScale() {
        return scale;
    }

    public void setScale(double scale) {
        this.scale = scale;
    }

    public double getOriginXForScale() {
        return originXForScale;
    }

    public void setOriginXForScale(double originXForScale) {
        this.originXForScale = originXForScale;
    }

    public double getOriginYForScale() {
        return originYForScale;
    }

    public void setOriginYForScale(double originYForScale) {
        this.originYForScale = originYForScale;
    }

    /**
     * Converts a gui.graphics2d.model x coordinate to a x coordinate in a Scale coordinate system.
     * @param x
     * @return
     */
    public double modelToScaleX(double x) {
        return this.originXForScale + getModelToScaleDistance(x);
    }

    /**
     * Converts a gui.graphics2d.model y coordinate to a y coordinate in anScale coordinate system.
     *
     * @param y
     * @return
     */
    public double modelToScaleY(double y) {
        return this.originYForScale + getModelToScaleDistance(y);
    }

    /**
     * Scales a distance in the gui.graphics2d.model to a number of units in the Scale coordinate system.
     *
     * @param distance
     * @return
     */
    public double getModelToScaleDistance(double distance) {
        return distance / scale;
    }

}
