package gui.graphics2d.model;

import java.util.Arrays;

import static gui.graphics2d.model.CelestialBody.*;

public class SolarSystem extends BodySystem {

    private static CelestialBody[] CELESTIAL_BODIES_IN_SYSTEM = new CelestialBody[] {SUN, MERCURY, VENUS, EARTH, MOON, MARS, JUPITER, SATURN, TITAN};


    public SolarSystem() {
        super();
        createSolarSystem();
    }

    private void createSolarSystem() {

        Arrays.stream(CELESTIAL_BODIES_IN_SYSTEM).forEach((celestialBody) -> {
            final Body body = celestialBody.getAsBody();

            addBody(body);
        });
    }

}
