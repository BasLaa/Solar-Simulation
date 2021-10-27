package solvers;

import functions.ODEFunctionInterface;
import rate.RateInterface;
import state.*;
import vector.Vector3D;

public class ODERungeKuttaSolver implements ODESolverInterface{

    public ODERungeKuttaSolver(){};

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
            ans[i] = (State) step(f,ans[i-1].getT() , ans[i-1], h);
        }
        return ans;
    }
    /*
     * Update rule for one step.
     *
     * @param   f   the function defining the differential equation dy/dt=f(t,y)
     * @param   t   the time
     * @param   y   the state
     * @param   h   the step size
     * @return  the new state after taking one step
     */

    @Override
    public StateInterface step(ODEFunctionInterface f, double t, StateInterface y, double h)
    {
      /*
      k1 = f(x(i),y(i))
      k2 = f(x(i) + 0.5*h , y(i) + 0.5*h*k1);
      k3 = f(x(i) + 0.5*h , y(i) + 0.5*h*k2)
      k4 = f(x(i) + 0.5*h , y(i) + h*ak3)

      y(i+1) = y(i) + 1/6 * (ak1 + 2*ak2 + 2*ak3 + ak4)*h
      */
        RateInterface k1;
        RateInterface k2;
        RateInterface k3;
        RateInterface k4;

        StateInterface y2;
        StateInterface y3;
        StateInterface y4;

        Vector3D[] nextPositions = new Vector3D[y.getSize()];
        Vector3D[] nextVelocities = new Vector3D[y.getSize()];

        k1 = f.call(t,y);

        y2 = eulerStep(f,t,y,h/2.00000000000,k1);
        k2 = f.call(t+h/2.000000000000, y2);

        y3 = eulerStep(f,t,y,h/2.0000000000,k2);
        k3 = f.call(t + h/2.00000000000, y3);

        y4 = eulerStep(f,t,y,h,k3);
        k4 = f.call(t + h, y4);

        for (int i = 0; i < y.getSize(); i++)
        {
            nextPositions[i] = y.getPosition(i).addMul(h/6.00000000,y.getVelocity(i).addMul(2.00000000,y2.getVelocity(i)).addMul(2.0000000000,y3.getVelocity(i)).add(y4.getVelocity(i)));
            nextVelocities[i] = y.getVelocity(i).addMul(h/6.00000000,k1.getRate(i).addMul(2.00000000,k2.getRate(i)).addMul(2.00000000000,k3.getRate(i)).add(k4.getRate(i)));
        }
        //Necessary for the return : nextPositions and nextVelocities
        return new State(t+h,nextVelocities,nextPositions);
    }

    private StateInterface eulerStep(ODEFunctionInterface f, double t, StateInterface y, double h, RateInterface acc)
    {
        Vector3D[] nextPositions = new Vector3D[y.getSize()];
        Vector3D[] nextVelocities = new Vector3D[y.getSize()];

        for (int i = 0; i < y.getSize(); i++)
        {
            nextPositions[i] = y.getPosition(i).addMul(h,y.getVelocity(i));
            nextVelocities[i] = y.getVelocity(i).addMul(h,acc.getRate(i));
        }
        return new State(t+h,nextVelocities,nextPositions);
    }

}

