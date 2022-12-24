package exceptions;

import joueur.*;

public class VictoireException extends UnoException {
    public Joueur joueurGagnant;
    public VictoireException(Joueur joueurGagnant) {
        super("" + joueurGagnant.getNom() + " a gagné la partie");
        this.joueurGagnant = joueurGagnant;
    }
}
