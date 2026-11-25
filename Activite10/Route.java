package Activite10;

public class Route {
    private Lieu extremaA;
    private Lieu extremaB;
    private int poids;

    public Route(Lieu extremaA, Lieu extremaB, int poids){
        this.extremaA = extremaA;
        this.extremaB = extremaB;
        this.poids = poids;
    }

    //Fonction qui renvoie le lieu opposé d'une route par rapport a celui qui est mis en parametre
    public Lieu getOppose(Lieu lieu){
        if (lieu == extremaA){
            return extremaB;
        }
        else if (lieu==extremaB){
            return extremaA;
        }
        else {
            return null;
        }
    }

    protected int getPoidsRoute(Lieu lieu){
        if (lieu==this.extremaA){
            if (this.poids < 0){
                return 0;
            }
        return this.poids;
        }
        if (lieu==this.extremaB){
            if (-this.poids < 0){
                return 0;
            }
            return -this.poids;
        }
        return -1;
    }

}
