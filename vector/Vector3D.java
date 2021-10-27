package vector;

public class Vector3D implements Vector3dInterface{
    private double x;
    private double y;
    private double z;

    /**
     * Constructs a zero vector
     */
    public Vector3D(){
        x = 0;
        y = 0;
        z = 0;
    }

    /**
     * Constructs a vector
     * @param x the delta x described by the vector
     * @param y the delta y described by the vector
     * @param z the delta z described by the vector
     */
    public Vector3D(double x, double y, double z){
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getZ() {
        return z;
    }

    public void setZ(double z) {
        this.z = z;
    }



    /**
     * Returns an addition of 2 vectors
     * @param other the vector to be added to this one
     * @return the result of the addition
     */
    public Vector3D add(Vector3dInterface other) {
        return new Vector3D(x+other.getX(), y+other.getY(), z+other.getZ());
    }

    /**
     * To get the result from a subtraction
     * @param other the vector to be subtracted
     * @return the result of the subtraction
     */
    public Vector3D sub(Vector3dInterface other) {
        return new Vector3D(x-other.getX(), y-other.getY(), z-other.getZ());
    }

    /**
     * To multiply this vector with a value, self applying
     * @param scalar the value to be multiplied with this vector
     * @return the result form the multiplication
     */
    public Vector3D mul(double scalar) {
        return new Vector3D(x*scalar, y*scalar, z*scalar);
    }

    /**
     * To get a clone form the object, a deep clone
     * @return the clone
     */
        public Vector3D clone(){
        return new Vector3D(x, y, z);
    }

    /**
     * To get the result of the addition of a scaled vector
     * @param scalar the double used in the multiplication step
     * @param other  the vector used in the multiplication step
     * @return the result (this + other*scalar)
     */
    public Vector3D addMul(double scalar, Vector3dInterface other) {
        return this.add(other.mul(scalar));
    }

    /**
     * @return the Euclidean norm of a vector
     */
    public double norm() {
        return Math.sqrt(Math.pow(x,2)+Math.pow(y,2)+Math.pow(z,2));
    }

    /**
     * @return the Euclidean distance between two vectors
     */
    public double dist(Vector3dInterface other) {
        return other.sub(this).norm();
    }
    public double theta()
    {
        return Math.atan2(this.y,this.x)*(180/Math.PI);
    }
    public double phi()
    {
        return Math.acos(this.z/norm()*(180/Math.PI));
    }

    /**
     * @return A string representation of the vector in the format (x-cor, y-cor, z-cor)
     */
    public String toString(){
        return ("("+x+","+y+","+z+")");
    }
}
