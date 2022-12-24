package carte;

import exceptions.UnoException;

public class CartePasse extends CarteEffet {
    public CartePasse(Couleur couleur) {
        super(couleur);
        if (couleur == Couleur.NOIR) throw new IllegalArgumentException("Une carte passe ne peut pas être noire");
    }

    @Override
    public void appliquerEffet() {
        getPartie().changerJoueurCourant();
    }

    @Override
    public void recevoirNotificationPioche(Carte cartePiochee) {}

    @Override
    public void recevoirNotificationPose() {}

    @Override
    public void recevoirNotificationFinDeTour() {
        getPartie().desactiverEffet();
        appliquerEffet();
    }

    @Override
    public void recevoirNotificationActionIllegale() {}

    @Override
    public String toString() {
        return "CartePasse[couleur=" + getCouleur() + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return getCouleur() == ((CartePasse) o).getCouleur();
    }
}
