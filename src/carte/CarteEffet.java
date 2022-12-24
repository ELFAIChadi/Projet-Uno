package carte;

import exceptions.UnoException;
import partie.*;
import pile.*;

public abstract class CarteEffet extends Carte {

    public CarteEffet(Couleur couleur) {
        super(couleur);
    }

    public Partie getPartie() {
        return Partie.getInstance();
    }

    public Tas getTas() {
        return Tas.getInstance();
    }

    public Pioche getPioche() {
        return Pioche.getInstance();
    }

    public abstract void appliquerEffet();
    public abstract void recevoirNotificationPioche(Carte cartePiochee);
    public abstract void recevoirNotificationPose();
    public abstract void recevoirNotificationFinDeTour();
    public abstract void recevoirNotificationActionIllegale();
}
