package rate;

import vector.Vector3D;

public class Acceleration implements RateInterface {
    private Vector3D[] rates;

    /**
     * creates an array, each representing the rate of an object
     * @param size number of objects
     */
    public Acceleration(int size){
        rates = new Vector3D[size];
        for(int i = 0; i < rates.length; i++){
            rates[i] = new Vector3D();
        }
    }
    
    public Acceleration(Vector3D[] rates){
        this.rates = rates;

    }

    public Vector3D getRate(int index){
        return rates[index];
    }

    public void set(int index, Vector3D in){
        rates[index] = in;
    }

    public int size(){
        return rates.length;
    }

    @Override
    public RateInterface add(RateInterface other) {
        Vector3D[] returnRates = new Vector3D[rates.length];

        for(int i = 0;i<rates.length;i++){
            returnRates[i] = rates[i].add(other.getRate(i));
        }
        RateInterface toReturn = (RateInterface) new Acceleration(returnRates);
        return toReturn;
    }

    public RateInterface mul(double scalar){
        Vector3D[] ans = new Vector3D[size()];
        for(int i=0; i < ans.length; i++){
            ans[i] = getRate(i).clone().mul(scalar);
        }
        return new Acceleration(ans);
    }

    @Override
    public RateInterface addMul(double scalar, RateInterface other) {
        Vector3D[] returnRates = new Vector3D[rates.length];

        for(int i = 0; i<rates.length;i++){
            returnRates[i] = rates[i].addMul(scalar,other.getRate(i));
        }
        RateInterface toReturn = (RateInterface) new Acceleration(returnRates);
        return toReturn;

    }

    public Vector3D[] getRates(){
        return rates.clone();
    }

}
