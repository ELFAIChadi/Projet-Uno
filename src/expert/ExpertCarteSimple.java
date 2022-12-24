package expert;

import carte.*;

public class ExpertCarteSimple extends ExpertCarte {
    public ExpertCarteSimple(ExpertCarte suivant) {
        super(suivant);
    }

    @Override
    public boolean peutVerifierValidite(Carte carteAuSommetDuTas, Carte carteAPoserDessus) {
        if (carteAuSommetDuTas == null || carteAPoserDessus == null) throw new IllegalArgumentException("Les cartes ne doivent pas être null");
        return carteAuSommetDuTas instanceof CarteSimple;
    }

    @Override
    public boolean estValide(Carte carteAuSommetDuTas, Carte carteAPoserDessus) {
        if (carteAuSommetDuTas == null || carteAPoserDessus == null) throw new IllegalArgumentException("Les cartes ne doivent pas être null");

        if (carteAPoserDessus.getCouleur() == Carte.Couleur.NOIR) {
            return true;
        }
        else if (carteAuSommetDuTas.getCouleur() == carteAPoserDessus.getCouleur()) {
            return true;
        }
        else if (carteAPoserDessus instanceof CarteSimple) {
            return ((CarteSimple)(carteAuSommetDuTas)).getValeur() == ((CarteSimple)(carteAPoserDessus)).getValeur();
        }
        else {
            return false;
        }
    }
}
