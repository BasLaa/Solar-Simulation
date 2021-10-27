package gui.graphics2d.model;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BodySystem {

    private List<Body> bodies;

    public BodySystem() {
        bodies = new ArrayList<>();
    }

    public List<Body> getBodies() {
        return bodies;
    }

    public void addBody(Body body) {
        bodies.add(body);
    }

    public Optional<Body> getBody(String name) {
        return bodies.stream().filter(i -> i.name.equals(name)).findFirst();
    }

}
