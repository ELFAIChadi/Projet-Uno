package tests;

import carte.*;
import exceptions.*;
import expert.*;
import joueur.*;
import partie.*;
import pile.*;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TestCartesPlus2 {
    private Carte vert9 = new CarteSimple(Carte.Couleur.VERT, 9);
    private Carte bleu0 = new CarteSimple(Carte.Couleur.BLEU, 0);
    private Carte vert8 = new CarteSimple(Carte.Couleur.VERT, 8);
    private Carte vert2 = new CarteSimple(Carte.Couleur.VERT, 2);
    private Carte rouge4 = new CarteSimple(Carte.Couleur.ROUGE, 4);
    private Carte vertPlus2 = new CartePlus2(Carte.Couleur.VERT);
    private Carte bleu9 = new CarteSimple(Carte.Couleur.BLEU, 9);
    private Carte jaune4 = new CarteSimple(Carte.Couleur.JAUNE, 4);
    private Carte jaune6 = new CarteSimple(Carte.Couleur.JAUNE, 6);
    private Carte vert6 = new CarteSimple(Carte.Couleur.VERT, 6);
    private Carte bleu7 = new CarteSimple(Carte.Couleur.BLEU, 7);
    private Carte bleu1 = new CarteSimple(Carte.Couleur.BLEU, 1);
    private Carte vert1 = new CarteSimple(Carte.Couleur.VERT, 1);

    @Test
    void coupLegalAvecUneCartePlus2()  {
        try {
            Carte.setExpertCarte(new ExpertCarteSimple(new ExpertCartePasse(new ExpertCartePlus2(null))));

            Partie.reinitialiser();
            Tas.reinitialiser();
            Pioche.reinitialiser();

            Tas tas = Tas.getInstance();
            tas.empilerCarteDessus(vert9);

            Pioche pioche = Pioche.getInstance();
            pioche.empilerCartesDessus(vert2, rouge4, vert2, vert8, bleu0);

            Joueur alice = new Joueur("Alice");
            alice.ajouterCartesDansLaMain(vertPlus2, bleu9, jaune4);

            Joueur bob = new Joueur("Bob");
            bob.ajouterCartesDansLaMain(jaune6, vert6, bleu7);

            Joueur charles = new Joueur("Charles");
            charles.ajouterCartesDansLaMain(bleu1, vertPlus2, vert1);

            assertTrue(alice.estLeJoueurCourant());
            assertDoesNotThrow(() -> alice.poser(vertPlus2));
            assertDoesNotThrow(alice::finirSonTour);
            assertTrue(bob.estLeJoueurCourant());
            assertEquals(bob.getNombreCartes(), 3);
            assertDoesNotThrow(bob::piocher);
            assertDoesNotThrow(bob::piocher);
            assertDoesNotThrow(bob::finirSonTour);
            assertEquals(bob.getNombreCartes(), 5);
            assertTrue(charles.estLeJoueurCourant());
            assertDoesNotThrow(() -> charles.poser(vert1));
            assertDoesNotThrow(charles::finirSonTour);
            assertEquals(charles.getNombreCartes(), 2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void coupLegalAvecCumulDeCartesPlus2() {
        try {
            Carte.setExpertCarte(new ExpertCarteSimple(new ExpertCartePasse(new ExpertCartePlus2(null))));

            Partie.reinitialiser();
            Tas.reinitialiser();
            Pioche.reinitialiser();

            Tas tas = Tas.getInstance();
            tas.empilerCarteDessus(vert9);

            Pioche pioche = Pioche.getInstance();
            pioche.empilerCartesDessus(vert2, rouge4, vert2, vert8, bleu0);

            Joueur alice = new Joueur("Alice");
            alice.ajouterCartesDansLaMain(vertPlus2, bleu9, jaune4);

            Joueur bob = new Joueur("Bob");
            bob.ajouterCartesDansLaMain(jaune6, vert6, bleu7);

            Joueur charles = new Joueur("Charles");
            charles.ajouterCartesDansLaMain(bleu1, vertPlus2, vert1);

            assertTrue(alice.estLeJoueurCourant());
            assertDoesNotThrow(alice::piocher);
            assertDoesNotThrow(alice::finirSonTour);
            assertTrue(bob.estLeJoueurCourant());
            assertDoesNotThrow(bob::piocher);
            assertDoesNotThrow(bob::finirSonTour);
            assertTrue(charles.estLeJoueurCourant());
            assertDoesNotThrow(() -> charles.poser(vertPlus2));
            assertDoesNotThrow(charles::finirSonTour);
            assertTrue(alice.estLeJoueurCourant());
            assertDoesNotThrow(() -> alice.poser(vertPlus2));
            assertDoesNotThrow(alice::finirSonTour);
            assertTrue(bob.estLeJoueurCourant());
            assertEquals(bob.getNombreCartes(), 4);
            for (int i = 0 ; i < 4 ; i++) {
                assertDoesNotThrow(bob::piocher);
            }
            assertEquals(bob.getNombreCartes(), 8);
            assertDoesNotThrow(bob::finirSonTour);
            assertTrue(charles.estLeJoueurCourant());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
