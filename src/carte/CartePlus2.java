package carte;

import exceptions.UnoException;

public class CartePlus2 extends CarteEffet {
    public enum ModeDeJeu {PLUS2_DESACTIVE, ENCHAINEMENT_DE_PLUS2, ENCAISSEMENT};

    private static ModeDeJeu modeDeJeu = ModeDeJeu.PLUS2_DESACTIVE;
    private static int accumulationPlus2 = 0;

    public CartePlus2(Couleur couleur) {
        super(couleur);
        if (couleur == Couleur.NOIR) throw new IllegalArgumentException("Une carte plus 2 ne peut pas être noire");
    }

    public static ModeDeJeu getModeDeJeu() {
        return modeDeJeu;
    }

    @Override
    public void appliquerEffet() {
        if (accumulationPlus2 > 0) {
            accumulationPlus2--;
        }
        if (accumulationPlus2 > 0) {
            getPartie().autoriserLeJoueurCourantARejouer();
        }
        else {
            modeDeJeu = ModeDeJeu.PLUS2_DESACTIVE;
        }
    }

    @Override
    public void recevoirNotificationPioche(Carte cartePiochee) {
        modeDeJeu = ModeDeJeu.ENCAISSEMENT;
        appliquerEffet();
    }

    @Override
    public void recevoirNotificationPose() {
        modeDeJeu = ModeDeJeu.ENCHAINEMENT_DE_PLUS2;
        accumulationPlus2 += 2;
    }

    @Override
    public void recevoirNotificationFinDeTour() {
        if (modeDeJeu == ModeDeJeu.PLUS2_DESACTIVE) {
            accumulationPlus2 = 0;
            getPartie().desactiverEffet();
        }
    }

    @Override
    public void recevoirNotificationActionIllegale() {
        modeDeJeu = ModeDeJeu.PLUS2_DESACTIVE;
        accumulationPlus2 = 0;
        getPartie().desactiverEffet();
    }

    @Override
    public String toString() {
        return "CartePlus2[couleur=" + getCouleur() + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return getCouleur() == ((CartePlus2) o).getCouleur();
    }
}
