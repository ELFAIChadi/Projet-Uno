package pile;

import carte.*;
import exceptions.*;

public class Tas extends PileDeCartes {
    private static volatile Tas instance;

    private Tas() {
        super();
    }

    public static Tas getInstance() {
        Tas result = instance;
        if (result != null) {
            return result;
        }
        synchronized(Tas.class) {
            if (instance == null) {
                instance = new Tas();
            }
            return instance;
        }
    }

    public static void reinitialiser() {
        instance = new Tas();
    }

    public boolean peutPoserSurLeTas(Carte carte) throws CombinaisonCartesException, PileVideException {
        if (estVide()) {
            return true;
        }
        return getCarteAuSommet().peutPoserDessus(carte);
    }


}
