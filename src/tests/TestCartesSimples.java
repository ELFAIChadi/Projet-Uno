package tests;

import carte.*;
import exceptions.*;
import expert.*;
import joueur.*;
import partie.*;
import pile.*;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TestCartesSimples {
    private Carte vert8 = new CarteSimple(Carte.Couleur.VERT, 8);
    private Carte jaune6 = new CarteSimple(Carte.Couleur.JAUNE, 6);
    private Carte rouge4 = new CarteSimple(Carte.Couleur.ROUGE, 4);
    private Carte vert2 = new CarteSimple(Carte.Couleur.VERT, 2);
    private Carte bleu5 = new CarteSimple(Carte.Couleur.BLEU, 5);
    private Carte vert0 = new CarteSimple(Carte.Couleur.VERT, 0);
    private Carte rouge1 = new CarteSimple(Carte.Couleur.ROUGE, 1);
    private Carte bleu2 = new CarteSimple(Carte.Couleur.BLEU, 2);
    private Carte jaune4 = new CarteSimple(Carte.Couleur.JAUNE, 4);
    private Carte rouge9 = new CarteSimple(Carte.Couleur.ROUGE, 9);
    private Carte bleu9 = new CarteSimple(Carte.Couleur.BLEU, 9);
    private Carte bleu7 = new CarteSimple(Carte.Couleur.BLEU, 7);
    private Carte bleu0 = new CarteSimple(Carte.Couleur.BLEU, 0);

    @Test
    void coupsLegauxAvecCartesSimples() {
        try {
            Carte.setExpertCarte(new ExpertCarteSimple(null));

            Partie.reinitialiser();
            Tas.reinitialiser();
            Pioche.reinitialiser();

            Tas tas = Tas.getInstance();
            tas.empilerCarteDessus(vert8);

            Pioche pioche = Pioche.getInstance();
            pioche.empilerCartesDessus(vert0, bleu5, vert2, rouge4, jaune6);

            Joueur alice = new Joueur("Alice");
            alice.ajouterCartesDansLaMain(vert2, jaune6, rouge1);

            Joueur bob = new Joueur("Bob");
            bob.ajouterCartesDansLaMain(bleu2, jaune4, rouge9);

            Joueur charles = new Joueur("Charles");
            charles.ajouterCartesDansLaMain(bleu9, bleu7, bleu0);

            assertTrue(alice.estLeJoueurCourant());
            assertEquals(alice.getNombreCartes(), 3);
            assertDoesNotThrow(() -> alice.poser(vert2));
            assertEquals(alice.getNombreCartes(), 2);
            assertTrue(alice.possede(jaune6));
            assertTrue(alice.possede(rouge1));
            assertEquals(tas.getCarteAuSommet(), vert2);
            assertEquals(tas.getNombreCartes(), 2);
            assertDoesNotThrow(alice::finirSonTour);
            assertTrue(bob.estLeJoueurCourant());
            assertEquals(bob.getNombreCartes(), 3);
            assertDoesNotThrow(() -> bob.poser(bleu2));
            assertEquals(bob.getNombreCartes(), 2);
            assertTrue(bob.possede(jaune4));
            assertTrue(bob.possede(rouge9));
            assertEquals(tas.getCarteAuSommet(), bleu2);
            assertEquals(tas.getNombreCartes(), 3);
            assertDoesNotThrow(bob::finirSonTour);
            assertTrue(charles.estLeJoueurCourant());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void uneCarteIllegaleEstPosee() {
        try {
            Carte.setExpertCarte(new ExpertCarteSimple(null));

            Partie.reinitialiser();
            Tas.reinitialiser();
            Pioche.reinitialiser();

            Tas tas = Tas.getInstance();
            tas.empilerCarteDessus(vert8);

            Pioche pioche = Pioche.getInstance();
            pioche.empilerCartesDessus(vert0, bleu5, vert2, rouge4, jaune6);

            Joueur alice = new Joueur("Alice");
            alice.ajouterCartesDansLaMain(vert2, jaune6, rouge1);

            Joueur bob = new Joueur("Bob");
            bob.ajouterCartesDansLaMain(bleu2, jaune4, rouge9);

            Joueur charles = new Joueur("Charles");
            charles.ajouterCartesDansLaMain(bleu9, bleu7, bleu0);

            assertThrows(ActionIllegaleException.class, () -> alice.poser(jaune6));
            assertEquals(alice.getNombreCartes(), 3);
            assertTrue(alice.possede(jaune6));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void deuxCartesLegalesSontPoseesDeSuite() {
        try {
            Carte.setExpertCarte(new ExpertCarteSimple(null));

            Partie.reinitialiser();
            Tas.reinitialiser();
            Pioche.reinitialiser();

            Tas tas = Tas.getInstance();
            tas.empilerCarteDessus(vert8);

            Pioche pioche = Pioche.getInstance();
            pioche.empilerCartesDessus(vert0, bleu5, vert2, rouge4, jaune6);

            Joueur alice = new Joueur("Alice");
            alice.ajouterCartesDansLaMain(vert2, jaune6, rouge1);

            Joueur bob = new Joueur("Bob");
            bob.ajouterCartesDansLaMain(bleu2, jaune4, rouge9);

            Joueur charles = new Joueur("Charles");
            charles.ajouterCartesDansLaMain(bleu9, bleu7, bleu0);

            assertDoesNotThrow(() -> alice.poser(vert2));
            assertDoesNotThrow(alice::finirSonTour);
            assertDoesNotThrow(() -> bob.poser(bleu2));
            assertDoesNotThrow(bob::finirSonTour);
            assertDoesNotThrow(() -> charles.poser(bleu9));
            assertEquals(charles.getNombreCartes(), 2);
            assertThrows(ActionIllegaleException.class, () -> charles.poser(bleu7));
            assertEquals(charles.getNombreCartes(), 2);
            assertTrue(charles.possede(bleu7));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void unJoueurFinitSontTourSansRienFaire() {
        try {
            Carte.setExpertCarte(new ExpertCarteSimple(null));

            Partie.reinitialiser();
            Tas.reinitialiser();
            Pioche.reinitialiser();

            Tas tas = Tas.getInstance();
            tas.empilerCarteDessus(vert8);

            Pioche pioche = Pioche.getInstance();
            pioche.empilerCartesDessus(vert0, bleu5, vert2, rouge4, jaune6);

            Joueur alice = new Joueur("Alice");
            alice.ajouterCartesDansLaMain(vert2, jaune6, rouge1);

            Joueur bob = new Joueur("Bob");
            bob.ajouterCartesDansLaMain(bleu2, jaune4, rouge9);

            Joueur charles = new Joueur("Charles");
            charles.ajouterCartesDansLaMain(bleu9, bleu7, bleu0);

            assertThrows(ActionIllegaleException.class, alice::finirSonTour);
            assertEquals(alice.getNombreCartes(), 3);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void unJoueurJouePuisPioche() {
        try {
            Carte.setExpertCarte(new ExpertCarteSimple(null));

            Partie.reinitialiser();
            Tas.reinitialiser();
            Pioche.reinitialiser();

            Tas tas = Tas.getInstance();
            tas.empilerCarteDessus(vert8);

            Pioche pioche = Pioche.getInstance();
            pioche.empilerCartesDessus(vert0, bleu5, vert2, rouge4, jaune6);

            Joueur alice = new Joueur("Alice");
            alice.ajouterCartesDansLaMain(vert2, jaune6, rouge1);

            Joueur bob = new Joueur("Bob");
            bob.ajouterCartesDansLaMain(bleu2, jaune4, rouge9);

            Joueur charles = new Joueur("Charles");
            charles.ajouterCartesDansLaMain(bleu9, bleu7, bleu0);

            assertDoesNotThrow(() -> alice.poser(vert2));
            assertThrows(ActionIllegaleException.class, alice::piocher);
            assertEquals(alice.getNombreCartes(), 2);
            assertEquals(pioche.getCarteAuSommet(), jaune6);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}