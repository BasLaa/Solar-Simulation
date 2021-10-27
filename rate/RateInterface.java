package rate;

import vector.Vector3D;

/**
 * An interface representing the time-derivative (rate-of-change) of the state of a system.
 *
 * The only uses of this interface are to be the output of the ODEFunctionInterface,
 * and to participate in the addMul method of StateInterface. A concrete State class
 * must cast the rate argument of addMul to a concrete Acceleration class of the expected type.
 *
 * For example, a Vector2d object might implement both StateInterface and RateInterface,
 * and define an addMul method taking and returning Vector2d object. The overriden addMul
 * from StateInterface would then be implemented by casting the rate to Vector2d, and
 * dispatching to the addMul method taking a Vector2d.
 */
public interface RateInterface {
    public Vector3D getRate(int index);
    public RateInterface add(RateInterface other);
    public RateInterface addMul(double scalar, RateInterface other);

}
