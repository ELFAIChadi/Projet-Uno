package tests;

import carte.*;
import exceptions.*;
import expert.*;
import joueur.*;
import partie.*;
import pile.*;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TestPunition {
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
    public void punitionPourUnePoseIllegale() {
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

            try {
                assertTrue(alice.estLeJoueurCourant());
                assertThrows(ActionIllegaleException.class, () -> alice.poser(jaune6));
                throw new ActionIllegaleException("La pose est illégale", alice);
            } catch (ActionIllegaleException e) {
                e.joueurFautif.punir();
                assertTrue(bob.estLeJoueurCourant());
                assertEquals(alice.getNombreCartes(), 5);
                assertTrue(alice.possede(jaune6));
                assertTrue(alice.possede(rouge4));
                assertEquals(pioche.getCarteAuSommet(), vert2);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void punitionPourUnJoueurQuiJoueQuandCestPasSonTour() {
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

            try {
                assertTrue(alice.estLeJoueurCourant());
                assertThrows(ActionIllegaleException.class, bob::piocher);
                throw new ActionIllegaleException("La pose est illégale", bob);
            } catch (ActionIllegaleException e) {
                e.joueurFautif.punir();
                assertEquals(bob.getNombreCartes(), 5);
                assertTrue(bob.possede(jaune6));
                assertTrue(bob.possede(rouge4));
                assertEquals(pioche.getCarteAuSommet(), vert2);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
