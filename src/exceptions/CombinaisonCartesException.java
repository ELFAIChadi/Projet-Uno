package exceptions;

import carte.*;

public class CombinaisonCartesException extends UnoException {
    public Carte carteAuDessusDuTasFautive;
    public Carte carteAPoserDessusFautive;

    public CombinaisonCartesException(Carte carteAuDessusDuTasFautive, Carte carteAPoserDessusFautive) {
        super("Combinaison inconnue avec " + carteAuDessusDuTasFautive + " et " + carteAPoserDessusFautive);
        if (carteAuDessusDuTasFautive == null || carteAPoserDessusFautive == null) throw new IllegalArgumentException("Les cartes ne doivent pas être null");
        this.carteAuDessusDuTasFautive = carteAuDessusDuTasFautive;
        this.carteAPoserDessusFautive = carteAPoserDessusFautive;
    }
}
