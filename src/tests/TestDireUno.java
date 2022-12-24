package tests;

import carte.*;
import exceptions.*;
import expert.*;
import joueur.*;
import partie.*;
import pile.*;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TestDireUno {
    private Carte vert8 = new CarteSimple(Carte.Couleur.VERT, 8);
    private Carte jaune6 = new CarteSimple(Carte.Couleur.JAUNE, 6);
    private Carte vert2 = new CarteSimple(Carte.Couleur.VERT, 2);
    private Carte bleu5 = new CarteSimple(Carte.Couleur.BLEU, 5);
    private Carte vert0 = new CarteSimple(Carte.Couleur.VERT, 0);
    private Carte bleu3 = new CarteSimple(Carte.Couleur.BLEU, 3);
    private Carte bleu2 = new CarteSimple(Carte.Couleur.BLEU, 2);
    private Carte jaune4 = new CarteSimple(Carte.Couleur.JAUNE, 4);
    private Carte bleu9 = new CarteSimple(Carte.Couleur.BLEU, 9);
    private Carte bleu7 = new CarteSimple(Carte.Couleur.BLEU, 7);

    @Test
    void unJoueurDitUnoAuBonMoment() {
        try {
            Carte.setExpertCarte(new ExpertCarteSimple(new ExpertCartePasse(null)));

            Partie.reinitialiser();
            Tas.reinitialiser();
            Pioche.reinitialiser();

            Tas tas = Tas.getInstance();
            tas.empilerCarteDessus(vert8);

            Pioche pioche = Pioche.getInstance();
            pioche.empilerCartesDessus(bleu3, vert0, bleu5, vert2, jaune6);

            Joueur alice = new Joueur("Alice");
            alice.ajouterCartesDansLaMain(vert2, jaune6);

            Joueur bob = new Joueur("Bob");
            bob.ajouterCartesDansLaMain(bleu2, jaune4);

            Joueur charles = new Joueur("Charles");
            charles.ajouterCartesDansLaMain(bleu9, bleu7);

            assertEquals(alice.getNombreCartes(), 2);
            assertDoesNotThrow(() -> alice.poser(vert2));
            assertDoesNotThrow(alice::direUno);
            assertDoesNotThrow(alice::finirSonTour);
            assertEquals(alice.getNombreCartes(), 1);
            assertEquals(tas.getCarteAuSommet(), vert2);
            assertTrue(bob.estLeJoueurCourant());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void unJoueurOublieDeDireUno() {
        try {
            Carte.setExpertCarte(new ExpertCarteSimple(new ExpertCartePasse(null)));

            Partie.reinitialiser();
            Tas.reinitialiser();
            Pioche.reinitialiser();

            Tas tas = Tas.getInstance();
            tas.empilerCarteDessus(vert8);

            Pioche pioche = Pioche.getInstance();
            pioche.empilerCartesDessus(bleu3, vert0, bleu5, vert2, jaune6);

            Joueur alice = new Joueur("Alice");
            alice.ajouterCartesDansLaMain(vert2, jaune6);

            Joueur bob = new Joueur("Bob");
            bob.ajouterCartesDansLaMain(bleu2, jaune4);

            Joueur charles = new Joueur("Charles");
            charles.ajouterCartesDansLaMain(bleu9, bleu7);

            try {
                assertDoesNotThrow(() -> alice.poser(vert2));
                assertThrows(ActionIllegaleException.class, alice::finirSonTour);
                throw new ActionIllegaleException("Le joueur n'a pas dit Uno", alice);
            } catch (ActionIllegaleException e) {
                e.joueurFautif.punir();
                assertEquals(alice.getNombreCartes(), 3);
                assertEquals(tas.getCarteAuSommet(), vert2);
                assertTrue(bob.estLeJoueurCourant());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void unJoueurDitUnoQuandCeNEstPasSonTour() {
        try {
            Carte.setExpertCarte(new ExpertCarteSimple(new ExpertCartePasse(null)));

            Partie.reinitialiser();
            Tas.reinitialiser();
            Pioche.reinitialiser();

            Tas tas = Tas.getInstance();
            tas.empilerCarteDessus(vert8);

            Pioche pioche = Pioche.getInstance();
            pioche.empilerCartesDessus(bleu3, vert0, bleu5, vert2, jaune6);

            Joueur alice = new Joueur("Alice");
            alice.ajouterCartesDansLaMain(vert2, jaune6);

            Joueur bob = new Joueur("Bob");
            bob.ajouterCartesDansLaMain(bleu2, jaune4);

            Joueur charles = new Joueur("Charles");
            charles.ajouterCartesDansLaMain(bleu9, bleu7);

            try {
                assertTrue(alice.estLeJoueurCourant());
                assertThrows(ActionIllegaleException.class, bob::direUno);
                throw new ActionIllegaleException("Ce n'est pas le tour du joueur", bob);
            } catch (ActionIllegaleException e) {
                e.joueurFautif.punir();
                assertEquals(bob.getNombreCartes(), 4);
                assertTrue(alice.estLeJoueurCourant());
                assertEquals(tas.getCarteAuSommet(), vert8);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}