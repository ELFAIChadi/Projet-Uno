package expert;

import carte.*;
import exceptions.*;

public abstract class ExpertCarte {
    private ExpertCarte suivant = null;

    public ExpertCarte(ExpertCarte suivant) {
        this.suivant = suivant;
    }

    public ExpertCarte getSuivant() {
        return suivant;
    }

    public abstract boolean peutVerifierValidite(Carte carteAuSommetDuTas, Carte carteAPoserDessus);
    public abstract boolean estValide(Carte carteAuSommetDuTas, Carte carteAPoserDessus);

    public boolean verifier(Carte carteAuSommetDuTas, Carte carteAPoserDessus) throws CombinaisonCartesException {
        if (peutVerifierValidite(carteAuSommetDuTas, carteAPoserDessus)) {
            return estValide(carteAuSommetDuTas, carteAPoserDessus);
        }
        else if (suivant != null) {
            return suivant.verifier(carteAuSommetDuTas, carteAPoserDessus);
        }
        else {
            throw new CombinaisonCartesException(carteAuSommetDuTas, carteAPoserDessus);
        }
    }
}
