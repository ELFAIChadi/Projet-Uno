package joueur;

import carte.*;
import exceptions.*;
import partie.*;
import pile.*;

import java.util.ArrayList;

public class Joueur {
    private String nom;
    private ArrayList<Carte> mainDuJoueur = new ArrayList<>();
    private Partie partie = Partie.getInstance();
    private Tas tas = Tas.getInstance();
    private Pioche pioche = Pioche.getInstance();

    public Joueur(String nom) throws UnoException {
        if (nom == null || nom.trim().equals("")) throw new IllegalArgumentException("Le nom du joueur ne doit pas être null ou vide");
        this.nom = nom;
        partie.ajouterJoueur(this);
    }

    public void ajouterCarteDansLaMain(Carte carte) {
        if (carte == null) throw new IllegalArgumentException("La carte à ajouter dans main du joueur ne doit pas être null");
        mainDuJoueur.add(carte);
    }

    public void ajouterCartesDansLaMain(Carte... listeCartes) {
        for (Carte i : listeCartes)
            ajouterCarteDansLaMain(i);
    }

    public void enleverCarteDeLaMain(Carte carte) {
        if (carte == null) throw new IllegalArgumentException("La carte à enlever de la main du joueur ne doit pas être null");
        if (!possede(carte)) throw new IllegalArgumentException("Le joueur ne possède pas la carte à enlever");
        mainDuJoueur.remove(carte);
    }

    public String getNom() {
        return nom;
    }

    public Carte getCarte(int i) {
        if (i < 0 || i >= mainDuJoueur.size()) throw new IllegalArgumentException("Indice pas bon");
        return mainDuJoueur.get(i);
    }

    public int getNombreCartes() {
        return mainDuJoueur.size();
    }

    public boolean possede(Carte carte) {
        if (carte == null) throw new IllegalArgumentException("La carte ne doit pas être null");
        return mainDuJoueur.contains(carte);
    }

    public boolean estLeJoueurCourant() {
        return partie.getJoueurCourant().equals(this);
    }

    public boolean peutEffectuerUneAction() {
        return partie.peutEffectuerUneAction();
    }

    public void poser(Carte carte) throws UnoException {
        if (carte == null) throw new IllegalArgumentException("La carte à poser ne doit pas être null");
        if (!possede(carte)) throw new IllegalArgumentException("Le joueur ne possède pas la carte à poser");

        if (!estLeJoueurCourant()) throw new ActionIllegaleException("Ce n'est pas le tour du joueur", this);
        if (!peutEffectuerUneAction()) throw new ActionIllegaleException("Le joueur a déjà joué", this);
        if (!tas.peutPoserSurLeTas(carte)) throw new ActionIllegaleException("La pose est illégale", this);

        tas.empilerCarteDessus(carte);
        enleverCarteDeLaMain(carte);
        partie.notifierUnePose(carte);

        if (getNombreCartes() == 1) {
            partie.ajouterObligationDeDireUno();
        }
        else if (getNombreCartes() == 0) {
            throw new VictoireException(this);
        }
    }

    public void piocher() throws UnoException {
        if (!estLeJoueurCourant()) throw new ActionIllegaleException("Ce n'est pas le tour du joueur", this);
        if (!peutEffectuerUneAction()) throw new ActionIllegaleException("Le joueur a déjà joué", this);

        Carte cartePiochee = pioche.recupererCarteAuSommet();
        ajouterCarteDansLaMain(cartePiochee);
        partie.notifierUnePioche(cartePiochee);
    }

    public void finirSonTour() throws ActionIllegaleException {
        if (!estLeJoueurCourant()) throw new ActionIllegaleException("Ce n'est pas le tour du joueur", this);
        if (partie.peutEffectuerUneAction()) throw new ActionIllegaleException("Le joueur n'a pas encore joué", this);
        if (partie.doitDireUno()) throw new ActionIllegaleException("Le joueur n'a pas dit Uno", this);

        partie.notifierUnFinDeTour();
        partie.changerJoueurCourant();
    }

    public void punir() throws PileVideException {
        ajouterCarteDansLaMain(pioche.recupererCarteAuSommet());
        ajouterCarteDansLaMain(pioche.recupererCarteAuSommet());

        if (estLeJoueurCourant()) {
            partie.changerJoueurCourant();
        }
    }

    public void direUno() throws ActionIllegaleException {
        if (!estLeJoueurCourant()) throw new ActionIllegaleException("Ce n'est pas le tour du joueur", this);
        if (!partie.doitDireUno()) throw new ActionIllegaleException("Le joueur ne doit pas dire Uno", this);

        partie.enleverObligationDeDireUno();
    }

    @Override
    public String toString() {
        return "Joueur[" +
                "nom='" + nom + '\'' +
                ", mainDuJoueur=" + mainDuJoueur +
                ']';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return nom.equals(((Joueur) o).getNom());
    }
}
