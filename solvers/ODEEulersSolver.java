package solvers;


import functions.ODEFunctionInterface;
import rate.RateInterface;
import state.*;
import vector.Vector3D;

public class ODEEulersSolver implements ODESolverInterface{

    public ODEEulersSolver(){}

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
        for(int i = 1; i < ans.length; i++){
            ans[i] = (State) step(f,ans[i-1].getT(), ans[i-1], h);
        }
        return ans;
    }

    @Override
    public StateInterface step(ODEFunctionInterface f, double t, StateInterface y, double h)
    {
        Vector3D[] nextPositions = new Vector3D[y.getSize()];
        Vector3D[] nextVelocities = new Vector3D[y.getSize()];
        RateInterface acc = f.call(t,y);
        for (int i = 0; i < y.getSize(); i++)
        {
            nextPositions[i] = y.getPosition(i).addMul(h,y.getVelocity(i));
            nextVelocities[i] = y.getVelocity(i).addMul(h,acc.getRate(i));
        }
        return new State(t+h,nextVelocities,nextPositions);
    }
}
