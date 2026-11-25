package Activite10;

public class Route {
    Lieu extremaA;
    Lieu extremaB;

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
}
