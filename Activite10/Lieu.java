package Activite10;
import java.util.*;

public class Lieu {
    int z;
    private ArrayList<Route> routes;
    private boolean marque;
    private ArrayList<Lieu> successeurs;

    public Lieu(int z,ArrayList<Route> routes){
        this.marque = false;
        this.z = z;
        this.routes = routes;
        //Rajout des successeurs a partir de l'autre point d'une route
        for (Route tempRoute : routes){
            successeurs.add(tempRoute.getOppose(this));
        }
    }

    public void setMarque(){
        this.marque=true;
    }

    public void removeMarque(){
        this.marque=false;
    }

    public ArrayList<Lieu> getSuccesseurs() {
        return successeurs;
    }
}
