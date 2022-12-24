package carte;

import exceptions.*;
import expert.*;

public abstract class Carte {
    public enum Couleur {BLEU, JAUNE, NOIR, ROUGE, VERT};

    private static ExpertCarte expertCarte = null;
    private Couleur couleur;

    public Carte(Couleur couleur) {
        this.couleur = couleur;
    }

    public Couleur getCouleur() {
        return couleur;
    }

    public boolean peutPoserDessus(Carte carte) throws CombinaisonCartesException {
        if (expertCarte == null) throw new CombinaisonCartesException(this, carte);
        return expertCarte.verifier(this, carte);
    }

    public static void setExpertCarte(ExpertCarte expertCarte) {
        Carte.expertCarte = expertCarte;
    }
}
