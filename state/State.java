package state;


import rate.RateInterface;
import vector.Vector3D;
import vector.Vector3dInterface;

/**
 * Represents a current state of multiple objects
 * They may have a position and velocity which can change
 */
public class State extends Positions implements StateInterface{
    private Vector3D[] velocities;
    private static int size;
    private static String[] names;
    private static double[] masses;
    private static double[] radii;

    public State(double t, int size){
        super(t, size);
        velocities = new Vector3D[size];
        this.size = size;
    }
    public State(double t, Vector3D[] velocities, Vector3D[] positions){
        super(t, positions);
        this.size = positions.length;
        this.velocities = velocities;
    }
    public State(String[] name, Vector3D[] positions, Vector3D[] velocities, double[] mass, double[] radius){
        super(0,positions);
        this.velocities = velocities;
        this.size = positions.length;
        names = name;
        State.masses = mass;
        State.radii = radius;
        // (!) exemption if the lengths of all these arrays does not match
    }


    public Vector3D getVelocity(int index){
        return velocities[index];
    }


    public void setVelocity(int index, Vector3D v){
        this.velocities[index] = v;
    }

    public StateInterface addMul(double step, RateInterface acceleration) {
        Vector3D[] p = positions.clone();
        Vector3D[] v = velocities.clone();
        for(int i = 0; i < getSize(); i++){
            p[i] = p[i].addMul(step, v[i]).addMul(0.5, acceleration.getRate(i).mul(step*step));
            v[i] = v[i].addMul(step, acceleration.getRate(i));
        }
        return new State(names, p, v, masses, radii);
     }
    
    public Positions getMinState(){
        return new Positions(t, super.positions);
    }

    public int getSize() {
        return size;
    }

    public Vector3D[] getVelocities(){
        return velocities;
    }

    public double getMass(int index) {
        return masses[index];
    }

    public double[] getMasses() {
        return masses;
    }

    public String[] getNames() {
        return names;
    }

    public double[] getRadii() {
        return radii;
    }

    @Override
    public Vector3D[] getPositions() {
        return super.getPositions();
    }

    public String toString()
    {
        String print = "System State at t = "+ t;
        for(int i = 0;i < names.length;i++)
        {
            print = print+"\n"+names[i]+"\n\tMass: "+masses[i]+"\n\tRadius: "+radii[i]+"\n\tPosition: "+positions[i].toString()+"\n\tVelocities: "+velocities[i].toString();
        }
        return print;
    }

    public void addProbe(Vector3dInterface p0, Vector3dInterface v0)
    {
        Vector3D[] pos = new Vector3D[positions.length+1];
        Vector3D[] vel = new Vector3D[velocities.length+1];
        String[] name = new String[names.length+1];
        double[] mass = new double[masses.length+1];
        double[] radius = new double[radii.length+1];
        for (int i = 0; i < positions.length; i++)
        {
            pos[i] = positions[i];
            vel[i] = velocities[i];
            name[i] = names[i];
            mass[i] = masses[i];
            radius[i] = radii[i];
        }
        pos[positions.length] = (Vector3D) p0;
        vel[velocities.length] = (Vector3D) v0;
        name[names.length] = "Probe";
        mass[masses.length] = 0;
        radius[radii.length] = 0;
        this.velocities = vel;
        this.positions = pos;
        this.names = name;
        this.masses = mass;
        this.radii = radius;
        size++;
    }
}
