package state;

import vector.Vector3D;

public class Positions {
    protected final double t; // the time of this state
    protected Vector3D[] positions; // the positions of the objects in this state

     public Positions(double t, Vector3D[] positions){
        this.t = t;
        this.positions = positions;
    }
    public Positions(double t, int size){
        this.t = t;
        this.positions = new Vector3D[size];
    }

    public Vector3D getPosition(int index){
        return positions[index];
    }

    public Vector3D[] getPositions() {
        return positions;
    }

    public void setP(int index, Vector3D p){
        positions[index] = p;
    }
    public double getT(){
        return t;
    }
    public String toString(){
        StringBuilder ans = new StringBuilder("t = " + t + " :{");
        for(int i = 0; i < positions.length; i++){
            ans.append(positions[i].toString());
            if(i < positions.length-1){
                ans.append(", ");
            }
        }
        ans.append("}");
        return ans.toString();
    }
    public int size(){
        return positions.length;
    }
}
