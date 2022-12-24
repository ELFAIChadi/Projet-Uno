import carte.*;
import exceptions.*;
import expert.*;
import joueur.*;
import partie.*;
import pile.*;

public class Main {
    public static void main(String[] args) throws UnoException {
        try {
            Partie partie = Partie.getInstance();
            partie.changerJoueurCourant();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

/*
* Faire des singletons thread-safe avec instançiation parresseuse pour la pioche et le tas
* remplacer les fonctions vientDePoser et vientDePiocher par notifierUnePose et notifierUnePioche
* Simplifier carteEffet
 */