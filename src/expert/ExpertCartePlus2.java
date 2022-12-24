package expert;

import carte.*;

public class ExpertCartePlus2 extends ExpertCarte {
    public ExpertCartePlus2(ExpertCarte suivant) {
        super(suivant);
    }

    @Override
    public boolean peutVerifierValidite(Carte carteAuSommetDuTas, Carte carteAPoserDessus) {
        if (carteAuSommetDuTas == null || carteAPoserDessus == null) throw new IllegalArgumentException("Les cartes ne doivent pas être null");
        return carteAuSommetDuTas instanceof CartePlus2;
    }

    @Override
    public boolean estValide(Carte carteAuSommetDuTas, Carte carteAPoserDessus) {
        if (carteAuSommetDuTas == null || carteAPoserDessus == null) throw new IllegalArgumentException("Les cartes ne doivent pas être null");

        switch (CartePlus2.getModeDeJeu()) {
            case PLUS2_DESACTIVE:
                if (carteAPoserDessus.getCouleur() == Carte.Couleur.NOIR) {
                    return true;
                }
                else if (carteAPoserDessus instanceof CartePlus2) {
                    return true;
                }
                else {
                    return carteAuSommetDuTas.getCouleur() == carteAPoserDessus.getCouleur();
                }
            case ENCHAINEMENT_DE_PLUS2:
                return carteAPoserDessus instanceof CartePlus2;
            default:
                return false;
        }
    }
}
