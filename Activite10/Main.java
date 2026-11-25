package Activite10;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Main {
    private static int reussis = 0;
    private static int echecs = 0;

    public static void main(String[] args) {
        ReseauRoutier reseau = new ReseauRoutier();
        Lieu A = new Lieu(100);
        Lieu B = new Lieu(50);
        Lieu C = new Lieu(130);
        Lieu D = new Lieu(150);
        Lieu E = new Lieu(90); // sommet isole

        // B est ajoute avant A pour verifier que le depart n'est pas l'indice 0.
        for (Lieu lieu : List.of(B, A, C, D, E)) {
            reseau.ajouterLieu(lieu);
        }
        Route AB = reseau.ajouterRoute(A, B, -50);
        Route BD = reseau.ajouterRoute(B, D, 100);
        Route AC = reseau.ajouterRoute(A, C, 30);
        Route CD = reseau.ajouterRoute(C, D, 20);

        System.out.println("=== Tests de Dijkstra : denivele positif cumule (m) ===");
        System.out.println("A(100) - B(50) - D(150) : cout 100 depuis A");
        System.out.println("A(100) - C(130) - D(150) : cout 50 depuis A\n");

        tester("Construction et associations des routes", () -> {
            verifier(5, reseau.getLieux().size());
            verifier(4, reseau.getRoutes().size());
            verifier(true, A.getRoutes().contains(AB) && B.getRoutes().contains(AB));
            verifier(true, A.getSuccesseurs().contains(B) && B.getSuccesseurs().contains(A));
            verifier(0, E.getRoutes().size());
        });
        tester("Poids selon le sens de deplacement", () -> {
            verifier(0, AB.getPoidsRoute(A));
            verifier(50, AB.getPoidsRoute(B));
            verifier(100, BD.getPoidsRoute(B));
            verifier(0, BD.getPoidsRoute(D));
        });
        tester("Refus d'une route vers un lieu hors reseau", () -> {
            try {
                reseau.ajouterRoute(A, new Lieu(120), 20);
            } catch (IllegalArgumentException exception) {
                verifier(4, reseau.getRoutes().size());
                return;
            }
            throw new AssertionError("La route aurait du etre refusee.");
        });
        tester("A -> D : relaxation et cout optimal 50", () -> {
            Trajet t = rechercher(A, D, reseau);
            verifier(50.0, coutInterne(t));
            // Controle du choix des routes independamment de leur ordre.
            // L'ordre est verifie dans un test distinct.
            verifierRoutesSansOrdre(t, AC, CD);
        });
        tester("A -> B : descente directe de cout nul", () -> {
            Trajet t = rechercher(A, B, reseau);
            verifier(List.of(AB), t.getTrajet());
            verifier(0.0, coutInterne(t));
        });
        tester("D -> A : cout optimal 0", () -> {
            Trajet t = rechercher(D, A, reseau);
            verifier(0.0, coutInterne(t));
            verifierRoutesSansOrdre(t, AC, CD);
        });
        tester("E -> A : depart isole, aucune route", () -> {
            Trajet t = rechercher(E, A, reseau);
            verifier(List.of(), t.getTrajet());
        });
        tester("A -> E : arrivee inaccessible, aucune route", () -> {
            Trajet t = rechercher(A, E, reseau);
            verifier(List.of(), t.getTrajet());
        });
        tester("A -> D : ordre des routes du depart vers l'arrivee", () -> {
            Trajet t = rechercher(A, D, reseau);
            verifier(true, t.getTrajet().equals(List.of(AC, CD)));
        });
        tester("Getters depart et arrivee", () -> {
            Trajet t = rechercher(A, D, reseau);
            verifier(true, t.getDepart() == A && t.getArrive() == D);
        });
        tester("A -> A : trajet valide sans route, contenant A", () -> {
            Trajet t = rechercher(A, A, reseau);
            verifier(0.0, coutInterne(t));
            verifier(List.of(), t.getTrajet());
            verifier(true, lireChamp(t, "parents").equals(List.of(A)));
        });
        tester("A -> E : cout infini pour signaler l'absence de chemin", () -> {
            Trajet t = rechercher(A, E, reseau);
            verifier(Double.POSITIVE_INFINITY, coutInterne(t));
        });
        tester("Deux recherches sur le meme objet Trajet", () -> {
            Trajet t = rechercher(A, D, reseau);
            t.rechercheDrijska(A, B, reseau);
            verifier(1, t.getTrajet().size());
            verifier(List.of(AB), t.getTrajet());
        });

        System.out.printf("%nBilan : %d reussis, %d echecs.%n", reussis, echecs);
        // Code de sortie non nul si un test echoue (utile en integration continue).
        if (echecs > 0) {
            System.exit(1);
        }
    }

    private static Trajet rechercher(Lieu depart, Lieu arrivee, ReseauRoutier reseau) {
        Trajet trajet = new Trajet(depart, arrivee);
        trajet.rechercheDrijska(depart, arrivee, reseau);
        return trajet;
    }

    // Lecture uniquement : permet de verifier les champs prives sans changer Trajet.
    // A remplacer par des getters quand ils seront ajoutes par l'auteur du projet.
    private static Object lireChamp(Trajet trajet, String nom) throws ReflectiveOperationException {
        Field champ = Trajet.class.getDeclaredField(nom);
        champ.setAccessible(true);
        return champ.get(trajet);
    }

    private static double coutInterne(Trajet trajet) throws ReflectiveOperationException {
        return (Double) lireChamp(trajet, "deniveleTotal");
    }

    private static void verifierRoutesSansOrdre(Trajet trajet, Route... attendues) {
        ArrayList<Route> routes = trajet.getTrajet();
        verifier(attendues.length, routes.size());
        verifier(true, routes.containsAll(List.of(attendues)));
    }

    private static void verifier(Object attendu, Object obtenu) {
        if (!Objects.equals(attendu, obtenu)) {
            throw new AssertionError("Attendu : " + attendu + " ; obtenu : " + obtenu);
        }
    }

    private static void tester(String nom, Test test) {
        try {
            test.executer();
            reussis++;
            System.out.println("[OK] " + nom);
        } catch (Exception | AssertionError erreur) {
            echecs++;
            System.out.println("[ECHEC] " + nom + " | " + erreur.getMessage());
        }
    }

    @FunctionalInterface
    private interface Test {
        void executer() throws Exception;
    }
}
