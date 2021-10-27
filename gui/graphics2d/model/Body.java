package gui.graphics2d.model;

import vector.Vector3D;

/**
 * Implementation of body moving in coordinate system.
 *
 */
public class Body {
    public Vector3D location;
    public Vector3D velocity;
    public Vector3D acceleration;

    /** mass in kilograms */
    public double mass;

    /** radius in meters */
    public double radius;

    /** name of the body*/
    public String name;


    public Body() {
        if (location == null) {
            location = new Vector3D();
        }
    }

    public Body(Vector3D location, Vector3D velocity, double radius, double mass) {
        this();
        this.location = location;
        this.radius = radius;
        this.mass = mass;
    }

    public Vector3D getLocation() {
        return location;
    }

    public void updateLocation(Vector3D newLocation) {
        location = newLocation;
    }
}
