package solvers;

import functions.ODEFunctionInterface;
import rate.RateInterface;
import state.*;
import vector.Vector3D;

/**
 * Velocity Verlet Solver
 * Uses the previous velocity to calculate new velocity
 * v(t+1/2Δt) = v(t)+1/2*a(t)Δt
 * where a(t) = f(t, y)
 * x(t+Δt) = x(t) + v(t+1/2Δt)Δt
 * a(t+Δt) = f(x(t+Δt))^2
 * v(t+Δt) = v(t+1/2Δt)+1/2*a(t+Δt)Δt
 */

public class ODEVelocityVerletSolver implements ODESolverInterface{

    public ODEVelocityVerletSolver(){}

    @Override
    public StateInterface[] solve(ODEFunctionInterface f, StateInterface y0, double[] ts) {
        State[] ans = new State[ts.length];
        ans[0] = (State) y0;
        for(int i = 1; i < ts.length; i++){
            ans[i] = (State) step(f, ts[i], ans[i-1], (ts[i]-ts[i-1]));
        }
        return ans;
    }

    @Override
    public StateInterface[] solve(ODEFunctionInterface f, StateInterface y0, double tf, double h) {
        State[] ans = new State[(int)(Math.round(tf/h)+1)];
        ans[0] = (State) y0;
        double t = 0;
        for(int i = 1; i < ans.length; i++){
            ans[i] = (State) step(f, ans[i-1].getT(), ans[i-1], h);
        }
        return ans;
    }

    @Override
    public StateInterface step(ODEFunctionInterface f, double t, StateInterface y, double h)
    {
        Vector3D[] nextPositions = new Vector3D[y.getSize()];
        Vector3D[] nextVelocities = new Vector3D[y.getSize()];
        RateInterface acc = f.call(t,y);

        Vector3D[] halfStepV = new Vector3D[y.getSize()];
        Vector3D[] stepX = new  Vector3D[y.getSize()];
        for (int i = 0; i < y.getSize(); i++)
        {   
            //v(t+1/2Δt) = v(t)+1/2*a(t)Δt
            halfStepV[i] = y.getVelocity(i).addMul(0.5*h, acc.getRate(i));

            //x(t+Δt) = x(t) + v(t+1/2Δt)Δt
            stepX[i] = y.getPosition(i).addMul(h,halfStepV[i]);

            //x(t+Δt)
        }
        State positionSteppedY = new State(y.getNames(), stepX, y.getVelocities(),y.getMasses(),y.getRadii());
        RateInterface acc2 = f.call(t+h,positionSteppedY);
        for (int i = 0; i < y.getSize(); i++) {

            //v(t+Δt) = v(t+1/2Δt)+1/2*a(t+Δt)Δt
            Vector3D stepV = halfStepV[i].addMul(0.5*h, acc2.getRate(i));
            nextPositions[i] = stepX[i];
            nextVelocities[i] = stepV;

        }
        return new State(t+h,nextVelocities,nextPositions);
    }
}
