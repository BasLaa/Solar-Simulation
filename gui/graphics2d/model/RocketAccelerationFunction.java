package gui.graphics2d.model;
import functions.ODEFunctionInterface;
import rate.Acceleration;
import rate.RateInterface;
import state.*;
import vector.Vector3D;


public class RocketAccelerationFunction implements ODEFunctionInterface {
    private final static double G = 6.673015e-11;
    public RateInterface call(double t, StateInterface y, Vector3D position, Vector3D thrust){
        State x = (State) y;
        Acceleration ans = new Acceleration(1);
        ans.set(0,probeGravityAcceleration(x,position).add(thrust));
        return ans;
    }

    private final double dryMass = 78000.0;
    private final double maxFlowRate = 30000000.0/4500.0; // kg/s. This is the maximum mass flow rate we will use
    private final double exhaustVelocity = 4500.0; // m/s



    /**
     * Computes the acceleration of an object under the influence of gravity
     * @param y the current state
     * @param i the index of this object, to exclude
     * @param pi the position of this object
     * @return The acceleration under the influence of gravity of i in state y
     */
    // mi ai = FGi = sum_(j/=i) (G mi mj (xi-xj)/(||xi - xj||^3)
    // mi ai = G mi sum_(j/=i) (mj (xi-xj)/(||xi - xj||^3)
    // ai = G sum_(j/=i) (mj (xi-xj)/(||xi - xj||^3)
    // note xi = pi
    public Vector3D gravityAcceleration(State y, int i, Vector3D pi){
        Vector3D ai = new Vector3D();
        for(int j = 0; j < y.size(); j++){
            if(j != i) {
                ai = ai.addMul(G*y.getMass(j)/Math.pow(pi.dist(y.getPosition(j)), 3),y.getPosition(j).sub(pi));
            }
        }

        //System.out.println(ai.norm());
        return ai;
    }

    public Vector3D probeGravityAcceleration(State y, Vector3D pProbe){
        return gravityAcceleration(y, -1, pProbe);

        // i = -1 because probe is not in the system
    }

    @Override
    public RateInterface call(double t, StateInterface y) {
        return null;
    }
}
