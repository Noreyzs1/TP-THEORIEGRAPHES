package Activite10;
import java.util.*;

public class Lieu {
    int z;
    private ArrayList<Route> routes;
    private ArrayList<Lieu> successeurs;

    public Lieu(int z){
        this.z = z;
        this.successeurs = new ArrayList<>();
        this.routes = new ArrayList<>();
    }

    public ArrayList<Lieu> getSuccesseurs() {
        return successeurs;
    }

    public ArrayList<Route> getRoutes() {
        return routes;
    }

    //Fonction qui renvoie la route si elle existe a partir d'un successeur non valable dans un multigraphe
    public Route getRouteDepuisSucc(Lieu successeur){
        for (Route routeRecherchee : routes ){
            if (routeRecherchee.getOppose(successeur)!=null){
                return routeRecherchee;
            }
        }
        return null;
    }

    public void ajouterRoute(Route route){
        routes.add(route);
        successeurs.add(route.getOppose(this));
    }
}
