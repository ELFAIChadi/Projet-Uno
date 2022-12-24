package pile;

import carte.*;
import exceptions.*;

public class Pioche extends PileDeCartes {
    private static volatile Pioche instance;

    private Pioche() {
        super();
    }

    public static Pioche getInstance() {
        Pioche result = instance;
        if (result != null) {
            return result;
        }
        synchronized(Pioche.class) {
            if (instance == null) {
                instance = new Pioche();
            }
            return instance;
        }
    }

    public static void reinitialiser() {
        instance = new Pioche();
    }

    @Override
    public Carte recupererCarteAuSommet() throws PileVideException {
        try {
            return super.recupererCarteAuSommet();
        } catch (PileVideException e) {
            transfererTasVersLaPioche();
        }
        return super.recupererCarteAuSommet();
    }

    @Override
    public Carte getCarteAuSommet() throws PileVideException {
        try {
            return super.getCarteAuSommet();
        } catch (PileVideException e) {
            transfererTasVersLaPioche();
        }
        return super.getCarteAuSommet();
    }

    public void transfererTasVersLaPioche() throws PileVideException {
        while (!Tas.getInstance().estVide()) {
            empilerCartesDessus(Tas.getInstance().recupererCarteAuSommet());
        }
    }
}
