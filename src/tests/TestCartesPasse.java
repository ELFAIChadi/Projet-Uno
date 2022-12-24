package tests;

import carte.*;
import exceptions.*;
import expert.*;
import joueur.*;
import partie.*;
import pile.*;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TestCartesPasse {
    private Carte rouge9 = new CarteSimple(Carte.Couleur.ROUGE, 9);
    private Carte bleu0 = new CarteSimple(Carte.Couleur.BLEU, 0);
    private Carte vert8 = new CarteSimple(Carte.Couleur.VERT, 8);
    private Carte vert2 = new CarteSimple(Carte.Couleur.VERT, 2);
    private Carte rouge4 = new CarteSimple(Carte.Couleur.ROUGE, 4);
    private Carte rougePasse = new CartePasse(Carte.Couleur.ROUGE);
    private Carte bleu9 = new CarteSimple(Carte.Couleur.BLEU, 9);
    private Carte jaune4 = new CarteSimple(Carte.Couleur.JAUNE, 4);
    private Carte jaune6 = new CarteSimple(Carte.Couleur.JAUNE, 6);
    private Carte vert6 = new CarteSimple(Carte.Couleur.VERT, 6);
    private Carte bleu7 = new CarteSimple(Carte.Couleur.BLEU, 7);
    private Carte bleu1 = new CarteSimple(Carte.Couleur.BLEU, 1);
    private Carte vertPasse = new CartePasse(Carte.Couleur.VERT);
    private Carte rouge1 = new CarteSimple(Carte.Couleur.ROUGE, 1);

    @Test
    void coupsLegauxAvecCartesPasse() {
        try {
            Carte.setExpertCarte(new ExpertCarteSimple(new ExpertCartePasse(new ExpertCartePlus2(null))));

            Partie.reinitialiser();
            Tas.reinitialiser();
            Pioche.reinitialiser();

            Tas tas = Tas.getInstance();
            tas.empilerCarteDessus(rouge9);

            Pioche pioche = Pioche.getInstance();
            pioche.empilerCartesDessus(vert2, rouge4, vert2, vert8, bleu0);

            Joueur alice = new Joueur("Alice");

            alice.ajouterCartesDansLaMain(rougePasse, bleu9, jaune4);

            Joueur bob = new Joueur("Bob");
            bob.ajouterCartesDansLaMain(jaune6, vert6, bleu7);

            Joueur charles = new Joueur("Charles");
            charles.ajouterCartesDansLaMain(bleu1, vertPasse, rouge1);

            assertTrue(alice.estLeJoueurCourant());
            assertDoesNotThrow(() -> alice.poser(rougePasse));
            assertDoesNotThrow(alice::finirSonTour);
            assertTrue(charles.estLeJoueurCourant());
            assertEquals(tas.getCarteAuSommet(), rougePasse);
            assertDoesNotThrow(() -> charles.poser(vertPasse));
            assertDoesNotThrow(charles::finirSonTour);
            assertTrue(bob.estLeJoueurCourant());
            assertEquals(tas.getCarteAuSommet(), vertPasse);
            assertDoesNotThrow(() -> bob.poser(vert6));
            assertDoesNotThrow(bob::finirSonTour);
            assertTrue(charles.estLeJoueurCourant());
            assertEquals(tas.getCarteAuSommet(), vert6);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void uneCarteSimpleIllegleEstPoseeSurUneCartePasse() {
        try {
            Carte.setExpertCarte(new ExpertCarteSimple(new ExpertCartePasse(new ExpertCartePlus2(null))));

            Partie.reinitialiser();
            Tas.reinitialiser();
            Pioche.reinitialiser();

            Tas tas = Tas.getInstance();
            tas.empilerCarteDessus(rouge9);

            Pioche pioche = Pioche.getInstance();
            pioche.empilerCartesDessus(vert2, rouge4, vert2, vert8, bleu0);

            Joueur alice = new Joueur("Alice");

            alice.ajouterCartesDansLaMain(rougePasse, bleu9, jaune4);

            Joueur bob = new Joueur("Bob");
            bob.ajouterCartesDansLaMain(jaune6, vert6, bleu7);

            Joueur charles = new Joueur("Charles");
            charles.ajouterCartesDansLaMain(bleu1, vertPasse, rouge1);

            try {
                assertTrue(alice.estLeJoueurCourant());
                assertDoesNotThrow(() -> alice.poser(rougePasse));
                assertDoesNotThrow(alice::finirSonTour);
                assertTrue(charles.estLeJoueurCourant());
                assertEquals(charles.getNombreCartes(), 3);
                assertThrows(ActionIllegaleException.class, () -> charles.poser(bleu1));
                throw new ActionIllegaleException("La pose est illégale", charles);
            } catch (ActionIllegaleException e) {
                e.joueurFautif.punir();
                assertEquals(charles.getNombreCartes(), 5);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void uneCartePasseIllegaleEstPoseeSurUneCarteSimple() {
        try {
            Carte.setExpertCarte(new ExpertCarteSimple(new ExpertCartePasse(new ExpertCartePlus2(null))));

            Partie.reinitialiser();
            Tas.reinitialiser();
            Pioche.reinitialiser();

            Tas tas = Tas.getInstance();
            tas.empilerCarteDessus(rouge9);

            Pioche pioche = Pioche.getInstance();
            pioche.empilerCartesDessus(vert2, rouge4, vert2, vert8, bleu0);

            Joueur alice = new Joueur("Alice");

            alice.ajouterCartesDansLaMain(rougePasse, bleu9, jaune4);

            Joueur bob = new Joueur("Bob");
            bob.ajouterCartesDansLaMain(jaune6, vert6, bleu7);

            Joueur charles = new Joueur("Charles");
            charles.ajouterCartesDansLaMain(bleu1, vertPasse, rouge1);

            try {
                assertTrue(alice.estLeJoueurCourant());
                assertDoesNotThrow(() -> alice.poser(bleu9));
                assertDoesNotThrow(alice::finirSonTour);
                assertTrue(bob.estLeJoueurCourant());
                assertDoesNotThrow(() -> bob.poser(bleu7));
                assertDoesNotThrow(bob::finirSonTour);
                assertTrue(charles.estLeJoueurCourant());
                assertEquals(charles.getNombreCartes(), 3);
                assertThrows(ActionIllegaleException.class, () -> charles.poser(vertPasse));
                throw new ActionIllegaleException("La pose est illégale", charles);
            } catch (ActionIllegaleException e) {
                e.joueurFautif.punir();
                assertEquals(charles.getNombreCartes(), 5);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
