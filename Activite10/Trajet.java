package Activite10;
import java.util.*;

public class Trajet {
    private double deniveleTotal;
    private Lieu depart;
    private Lieu arrive;
    private ArrayList<Route> trajet;
    private ArrayList<Lieu> parents;
    Trajet(Lieu depart, Lieu arrivee ){
        trajet = new ArrayList<>();
        parents = new ArrayList<>();
    }
    
    public void rechercheDrijska(Lieu depart, Lieu arrivee,ReseauRoutier reseau){
        //Utilisation de la classe temporaire implémentée
        ArrayList<tempDriska> liste_marques_parents = new ArrayList<>();
        HashMap<Lieu,tempDriska> lienDriskaLieu = new HashMap<>();
        for (Lieu buffer : reseau.getLieux()){
            liste_marques_parents.add(new tempDriska(buffer));
            lienDriskaLieu.put(buffer,liste_marques_parents.getLast());
        }
        //phase 1 : Initialisation
        //Marquage : tous les sommets sont non marqués
        //Distance : s0 est a 0 de lui meme
              //s0 est marqué
        tempDriska s0 = lienDriskaLieu.get(depart);
        s0.setMarqueTrue();
        
        s0.setDistance(0);
        //mise a jour des distances de tous les lieux qui sont reliés directement a s0
        tempDriska.miseAJourDistance(s0, liste_marques_parents);
        
        while (tempDriska.conditionDriszka(liste_marques_parents)){
            //Selectionner et marquer le sommet smin non marqué ayant la plus petite distance au sommet initial s0
            tempDriska sMin = tempDriska.marquerSMin(liste_marques_parents);
            //Pour chaque successeur s non marqué d smin 
            //(mettre a jour la dsitance provisoire de s
            //noterr que la parent de s est smin
            tempDriska.miseAJourDistance(sMin, liste_marques_parents);



        };

        //Si il y a bien un chemin depuis le depart jusqu'a l'arrivée
        if (lienDriskaLieu.get(arrivee).getParent() != null){
            tempDriska bufferParent = lienDriskaLieu.get(arrivee).getParent();
            //ajout de l'arrivée en premier dans la liste des lieux
            parents.add(arrivee);
            //Ajout du trajet qui relie l'arrivee jusqu'a son parent dans la liste des routes
            trajet.add (arrivee.getRouteDepuisSucc(bufferParent.getLieu()));

            //Tant qu'il y a un parent et donc que bufferParent n'est pas égal au départ
            while (bufferParent.getParent()!= null){
                //Ajout  du dernier lieu dans la liste des lieux
                parents.addFirst(bufferParent.getLieu());

                //Ajout de la route que relie le dernier lieu jusqu'a son parent
                trajet.addFirst(bufferParent.getLieu().getRouteDepuisSucc(bufferParent.getParent().getLieu()));
                bufferParent = bufferParent.getParent();

            }
            //Ajout de l'arrivée 
            parents.add(bufferParent.getLieu());
            deniveleTotal = lienDriskaLieu.get(arrivee).getDistance();


        }



    }
    

    public Lieu getArrive() {
        return arrive;
    }
    
    public Lieu getDepart() {
        return depart;
    }

    public ArrayList<Route> getTrajet() {
        return this.trajet;
    }

}
