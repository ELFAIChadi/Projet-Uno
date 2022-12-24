package exceptions;

import joueur.*;
import partie.*;

public class ActionIllegaleException extends UnoException {
    public Joueur joueurFautif;
    public ActionIllegaleException(String msg, Joueur joueurFautif) {
        super(msg);
        if (joueurFautif == null) throw new IllegalArgumentException("Le joueur fautif ne doit pas être null");
        this.joueurFautif = joueurFautif;
        Partie.getInstance().notifierUneActionIllegale();
    }
}
