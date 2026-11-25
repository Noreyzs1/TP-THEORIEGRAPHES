package Activite10;

import java.util.ArrayList;

public class ReseauRoutier {
    private ArrayList<Route> routes;
    private ArrayList<Lieu> lieux;

    public ReseauRoutier() {
        routes = new ArrayList<>();
        lieux = new ArrayList<>();
    }

    public ArrayList<Lieu> getLieux() {
        return lieux;
    }

    public ArrayList<Route> getRoutes() {
        return routes;
    }

    public void ajouterLieu(Lieu lieu) {
        if (lieu == null || lieux.contains(lieu)) {
            throw new IllegalArgumentException("Lieu nul ou deja present.");
        }
        lieux.add(lieu);
    }

    // Le poids signe est fourni dans le sens A vers B.
    // On construit un graphe simple, sans boucle ni route parallele.
    public Route ajouterRoute(Lieu A, Lieu B, int poids) {
        if (A == null || B == null || !lieux.contains(A) || !lieux.contains(B)) {
            throw new IllegalArgumentException("Les deux lieux doivent appartenir au reseau.");
        }
        if (A == B || A.getRouteDepuisSucc(B) != null) {
            throw new IllegalArgumentException("Boucle ou route parallele non autorisee.");
        }
        if (poids == Integer.MIN_VALUE) {
            throw new IllegalArgumentException("Poids impossible a inverser avec un int.");
        }

        Route route = new Route(A, B, poids);
        routes.add(route);
        A.ajouterRoute(route);
        B.ajouterRoute(route);
        return route;
    }
}
