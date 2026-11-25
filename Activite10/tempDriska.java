package Activite10;
import java.util.*;
//Classe nommé temp car elle n'est la que pour simplifier dans l'algorithme de driska la liste des parents et des marques tout en l'assignant directement a un lieu
//Ce qui permet de ne pas avoir a comparer l'id dans les tableaux a chaque fois

public class tempDriska {
    private Lieu lieu;
    private boolean marque;
    private tempDriska parent;
    private double distance;
    private tempDriska successeurs;

    public tempDriska(Lieu lieu){
        this.lieu=lieu;
        marque = false;
        parent = null;
        //Fonction double pour représenter la distance infini de base jusqu'au sommet S0
        distance = Double.POSITIVE_INFINITY;
    }

    public Lieu getLieu() {
        return this.lieu;
    }
    public tempDriska getParent() {
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

    public void setDistance(double distance){
        this.distance=distance;
    }

    public void setParent(tempDriska parent) {
        this.parent = parent;
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

    //Fonction qui va mettre a jour la distance des sommets qui sont liés a smin et mettre en parent smin si le chemin est plus court
    public static void miseAJourDistance(tempDriska sMinDriska, ArrayList<tempDriska> l_Driskas){
        for (Route temp : sMinDriska.getLieu().getRoutes()){
            Lieu oppose = temp.getOppose(sMinDriska.getLieu());
            tempDriska opposeDriska = getDriskaDepuisLieu(oppose, l_Driskas);
            double distanceBuffer = sMinDriska.getDistance() + temp.getPoidsRoute(sMinDriska.getLieu());
            if (distanceBuffer < opposeDriska.getDistance()){
                opposeDriska.setDistance(distanceBuffer);
                opposeDriska.setParent(sMinDriska);
            }
        }
    }

    //Fonction qui marque et renvoie le tempDriska avec la plus petite distance et le marque
    public static tempDriska marquerSMin(ArrayList<tempDriska> l_Driskas){
        tempDriska retour = premierNonMarque(l_Driskas);
        double distanceBuffer = retour.getDistance();
        for (tempDriska d : l_Driskas ){
            if ((d.getDistance() < distanceBuffer) && !d.getMarque() ){
                retour = d;
                distanceBuffer = d.getDistance();
            }
        }
        if (retour != null){
            retour.setMarqueTrue();
        }
        return retour;
    }

    //Remarque : Chatgpt a remarqué qu'en fait il y avait juste un Hashmap à faire plutot que cette fonction qui fait tout le tour d'une arraylist a chaque fois,
    // à implémenter pour optimisation
    
    public static tempDriska getDriskaDepuisLieu(Lieu lieu,ArrayList<tempDriska> l_Driskas){
        for (tempDriska temp : l_Driskas){
            if( temp.getLieu() == lieu ){
                return temp;
            }
        }
        return null;
    }

    //Fonction qui retourne le premier non marque a une distance non infinie
    public static tempDriska premierNonMarque(ArrayList<tempDriska> l_Driskas){
        for (tempDriska retour : l_Driskas){
            if (retour.getMarque()==false && retour.getDistance()<Double.POSITIVE_INFINITY){
                return retour;
            }
        }
        return null;
    }

}
