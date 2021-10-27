package functions;

import rate.Acceleration;
import rate.RateInterface;
import state.*;
import vector.Vector3D;

public class AccelerationFunction implements ODEFunctionInterface {
    private final static double G = 6.6743015e-11;//-g

    public RateInterface call(double t, StateInterface y){
        State x = (State) y;
        Acceleration ans = new Acceleration(x.size());
        for(int i = 0; i < ans.size(); i++){
            ans.set(i, gravityAcceleration(x, i, x.getPosition(i)));
        }
        return ans;
    }

    /**
     * Computes the acceleration of an object under the influence of gravity
     * @param y the current state
     * @param i the index of this object, to exclude
     * @param pi the position of this object
     * @return The acceleration under the influence of gravity of i in state y
     */
    public Vector3D gravityAcceleration(State y, int i, Vector3D pi){
        Vector3D ai = new Vector3D();
        for(int j = 0; j < y.size(); j++){
            if(j != i) {
                ai = ai.addMul(G*y.getMass(j)/Math.pow(pi.dist(y.getPosition(j)), 3),y.getPosition(j).sub(pi));
            }
        }
        return ai;
    }
}
