package Activite10;
import java.util.*;
//Classe nommé temp car elle n'est la que pour simplifier dans l'algorithme de driska la liste des parents et des marques tout en l'assignant directement a un lieu
//Ce qui permet de ne pas avoir a comparer l'id dans les tableaux a chaque fois

public class tempDriska {
    private Lieu lieu;
    private boolean marque;
    private Lieu parent;
    private double distance;

    public tempDriska(Lieu lieu){
        this.lieu=lieu;
        marque = false;
        parent = null;
        distance = Double.POSITIVE_INFINITY;
    }

    public Lieu getLieu() {
        return this.lieu;
    }
    public Lieu getParent() {
        return this.parent;
    }
    
    public boolean getMarque(){
        return this.marque;
    }

    public void setMarqueTrue(){
        this.marque=true;
    }

    public double getDistance() {
        return distance;
    }

    //Condition pour Driska tant qu'il reste des sommets non marqués a une distance provisoire non infinie
    public static boolean conditionDriszka(ArrayList<tempDriska> liste_marques_parents){
        for (tempDriska temp : liste_marques_parents){
            if (!temp.getMarque() && temp.getDistance()<Double.POSITIVE_INFINITY){
                return true;
            }
        }
        return false;
    }

    //Fonction qui renvoie le successeur avec le poids qui dans ce cas précis est l'altitude le plus petit
    public static tempDriska marquerSMin(tempDriska buffer, ArrayList<tempDriska> l_Driskas){
        ArrayList<Route> routes = buffer.getLieu().getRoutes();
        int poidsMin = routes.get(0).getPoidsRoute(buffer.getLieu());
        Lieu retour = new Lieu();
        tempDriska retourDriska; 
        for (Route route : routes){
            if (route.getPoidsRoute(buffer.getLieu())<poidsMin){
                poidsMin=route.getPoidsRoute(buffer.getLieu());
                retour = route.getOppose(buffer.getLieu());
            }
        }
        retourDriska = tempDriska.getDriskaDepuisLieu(retour, l_Driskas);
        retourDriska.setMarqueTrue();
        return retourDriska;
    }

    public static tempDriska getDriskaDepuisLieu(Lieu lieu,ArrayList<tempDriska> l_Driskas){
        for (tempDriska temp : l_Driskas){
            if( temp.getLieu() == lieu ){
                return temp;
            }
        }
        return null;
    }

}
