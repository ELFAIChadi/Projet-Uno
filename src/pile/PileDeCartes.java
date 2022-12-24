package pile;

import carte.*;
import exceptions.*;

import java.util.ArrayList;

public abstract class PileDeCartes {
    private ArrayList<Carte> pile = new ArrayList<>();

    public PileDeCartes() {}

    public void empilerCarteDessus(Carte carte) {
        if (carte == null) throw new IllegalArgumentException("La carte à ajouter à la pile ne doit pas être null");
        pile.add(0, carte);
    }

    public void empilerCartesDessus(Carte... listeCartes) {
        for (Carte i : listeCartes) {
            empilerCarteDessus(i);
        }
    }

    public int getNombreCartes() {
        return pile.size();
    }

    public boolean estVide() {
        return pile.isEmpty();
    }

    public Carte recupererCarteAuSommet() throws PileVideException {
        if(estVide()) throw new PileVideException();
        return pile.remove(0);
    }

    public Carte getCarteAuSommet() throws PileVideException {
        if(pile.isEmpty()) throw new PileVideException();
        return pile.get(0);
    }

    @Override
    public String toString() {
        String s = "";
        for (Carte i : pile) {
            s += i.toString() + "\n";
        }
        return s;
    }
}
