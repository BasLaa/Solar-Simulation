
package state;

import rate.RateInterface;
import vector.Vector3D;

/**
 * An interface representing the state of a system described by a differential equation.
 */
public interface StateInterface {
    /**
     * Update a state to a new state computed by: this + step * rate
     *
     * @param step   The time-step of the update
     * @param rate   The average rate-of-change over the time-step. Has dimensions of [state]/[time].
     * @return The new state after the update. Required to have the same class as 'this'.
     */
    public StateInterface addMul(double step, RateInterface rate);

    public String toString();

    public Vector3D getVelocity(int index);


    public Vector3D getPosition(int index);

    public int getSize();

    public Vector3D[] getVelocities();
    public Vector3D[] getPositions();
    public String[] getNames();
    public double[] getMasses();
    public double[] getRadii();
}

