package gui.graphics2d.model;

import vector.Vector3D;

public enum CelestialBody {

    //Name                  Mass            Radius         X                        Y                        Z
    SUN("Sun",              1.98847E+30,    14.2e9,        -6.807847851469811E+08,  1.079960529004681e+09,   6.577801896302961e+06),

    MERCURY("Mercury",      3.302e23,       2.440e6,       5.941270492988122e+06,  -6.801804551422262e+10,   5.702728562917408e+09),

    VENUS("Venus",          4.8685e24,      6.0518e6,     -9.435356118127899e+10,  5.350355062360455e+10,   6.131466820352264e+09),

    EARTH("Earth",          5.97219e24,     6.371e6,      -1.47192316663542e+11,   -2.861000299246477e+10,  8.291942464411259e+06),

    MOON("Moon",            7.349e22,       1.738e6,      -1.472344969594165e+11,  -2.822582844526653e+10,   1.054166983666271e07),

    MARS("Mars",            6.4171e23,      3389.9e3,     -3.615638921529161e+10,  -2.167633037046744e+11,  -3.687670305939779e+09),

    JUPITER("Jupiter",      1.8981722e27,   7.1492e7,      1.781303138592156e+11,  -7.551118436250294e+11,  -8.532838524470329e+08),

    SATURN("Saturn",        5.6834e26,     6.0268e7,     6.328646641500651e+11,   1.358172804527507e+12,   1.578520137930810e+09),

    TITAN("Titan",        1.34553e23,     2575.5e3,      6.332873118527889e+11,  -1.357175556995868e+12,  -2.134637041453660e+09);

    public final String celestialName;
    public final double mass;   // kg
    public final double radius; // meters
    public Vector3D location; // meters


    /**
     *
     * @param celestialName
     * @param mass      kg
     * @param radius    m
     * @param x         m
     * @param y         m
     * @param z         m
     */
    CelestialBody(String celestialName, double mass, double radius, double x, double y, double z) {
        this.celestialName = celestialName;
        this.mass = mass;
        this.radius = radius;
        this.location = new Vector3D(x, y, z);
    }

    public Body getAsBody() {
        Body body = new Body();
        body.location = new Vector3D(this.location.getX(), this.location.getY(), this.location.getZ());
        body.mass = this.mass;
        body.name = this.celestialName;
        body.radius = this.radius;
        return body;
    }
}
