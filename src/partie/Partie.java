package partie;

import carte.*;
import exceptions.*;
import joueur.*;

import java.util.ArrayList;

public class Partie {
    private static volatile Partie instance;
    private ArrayList<Joueur> listeJoueurs = new ArrayList<>();
    private int indiceJoueurCourant = 0;
    private boolean peutEffectuerUneAction = true;
    private boolean doitDireUno = false;
    private CarteEffet carteEffetActivee;

    private Partie() {}

    public static Partie getInstance() {
        Partie result = instance;
        if (result != null) {
            return result;
        }
        synchronized(Partie.class) {
            if (instance == null) {
                instance = new Partie();
            }
            return instance;
        }
    }

    public static void reinitialiser() {
        instance = new Partie();
    }

    public void ajouterJoueur(Joueur joueur) throws UnoException {
        if (joueur == null) throw new IllegalArgumentException("Le joueur à ajouter à la partie ne doit pas être null");
        if (listeJoueurs.contains(joueur)) throw new UnoException("La partie contient déjà ce joueur");

        listeJoueurs.add(joueur);
    }

    public int getNombreJoueurs() {
        return listeJoueurs.size();
    }

    public Joueur getJoueurCourant() {
        if (getNombreJoueurs() == 0) return null;

        return listeJoueurs.get(indiceJoueurCourant);
    }

    public void changerJoueurCourant() {
        if (getNombreJoueurs() == 0) return;

        indiceJoueurCourant++;
        if (indiceJoueurCourant == getNombreJoueurs()) {
            indiceJoueurCourant = 0;
        }
        peutEffectuerUneAction = true;
        doitDireUno = false;
    }

    public boolean peutEffectuerUneAction() {
        return peutEffectuerUneAction;
    }

    public void autoriserLeJoueurCourantARejouer() {
        peutEffectuerUneAction = true;
    }

    public void interdireAuJoueurCourantDeJouer() {
        peutEffectuerUneAction = false;
    }

    public boolean doitDireUno() {
        return doitDireUno;
    }

    public void ajouterObligationDeDireUno() {
        doitDireUno = true;
    }

    public void enleverObligationDeDireUno() {
        doitDireUno = false;
    }

    public void activerEffet(CarteEffet carteAEffet) {
        carteEffetActivee = carteAEffet;
    }

    public void desactiverEffet() {
        carteEffetActivee = null;
    }

    public void notifierUnePose(Carte cartePosee) {
        if (cartePosee == null) throw new IllegalArgumentException("La carte ne doit pas être null");

        if (cartePosee instanceof CarteEffet) {
            activerEffet((CarteEffet) cartePosee);
        }
        peutEffectuerUneAction = false;
        if (carteEffetActivee != null) {
            carteEffetActivee.recevoirNotificationPose();
        }
    }

    public void notifierUnePioche(Carte cartePiochee) {
        if (cartePiochee == null) throw new IllegalArgumentException("La carte ne doit pas être null");

        peutEffectuerUneAction = false;
        if (carteEffetActivee != null) {
            carteEffetActivee.recevoirNotificationPioche(cartePiochee);
        }
    }

    public void notifierUnFinDeTour() {
        if (carteEffetActivee != null) {
            carteEffetActivee.recevoirNotificationFinDeTour();
        }
    }

    public void notifierUneActionIllegale() {
        if (carteEffetActivee != null) {
            carteEffetActivee.recevoirNotificationActionIllegale();
        }
    }
}
