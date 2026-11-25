package Activite10;
import java.util.*;

public class Trajet {
    private int deniveleTotal;
    Lieu depart;
    Lieu arrive;
    ArrayList<Route> trajet;

    Trajet(Lieu depart, Lieu arrivee ){

    }
    
    public void rechercheDrijska(Lieu depart, Lieu arrivee,ReseauRoutier reseau){
        //Utilisation de la classe temporaire implémentée
        ArrayList<tempDriska> liste_marques_parents = new ArrayList<>();
        for (Lieu buffer : reseau.getLieux()){
            liste_marques_parents.add(new tempDriska(buffer));
        }
        liste_marques_parents.get(0).setMarqueTrue();
        tempDriska s0 = liste_marques_parents.get(0);
        tempDriska dernier_marque = liste_marques_parents.get(0);
        do{
            tempDriska.marquerSMin(dernier_marque, liste_marques_parents);

        }while (tempDriska.conditionDriszka(liste_marques_parents));


    }

    public Lieu getArrive() {
        return arrive;
    }
    
    public Lieu getDepart() {
        return depart;
    }

    public ArrayList<Route> getTrajet() {
        return trajet;
    }
}
