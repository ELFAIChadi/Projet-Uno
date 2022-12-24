package carte;

public class CarteSimple extends Carte {
    private int valeur;

    public CarteSimple(Couleur couleur, int valeur) {
        super(couleur);
        if (couleur == Couleur.NOIR) throw new IllegalArgumentException("Une carte simple ne peut pas être noire");
        if (valeur < 0) throw new IllegalArgumentException("La valeur d'une carte simple ne doit pas être inférieure à 0");
        this.valeur = valeur;
    }

    public int getValeur() {
        return valeur;
    }

    @Override
    public String toString() {
        return "CarteSimple[valeur=" + valeur + ", couleur=" + getCouleur() + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return getCouleur() == ((CarteSimple) o).getCouleur() && valeur == ((CarteSimple) o).getValeur();
    }
}
