package AlgoPars.Metier;

import AlgoPars.AlgoPars;
import AlgoPars.Metier.Programme;

import java.util.Scanner;

public class Primitives {
    private AlgoPars ctrl;
    private Programme prgm;

    public Primitives(AlgoPars ctrl, Programme prgm) {
        this.ctrl = ctrl;
        this.prgm = prgm;
    }

    public void lire(String var) {
        System.out.println("Lire:");
        String msg = new Scanner(System.in).nextLine();
        this.ctrl.affecterValeur(var, msg);
    }

    public void ecrire(String msg) {
        this.ctrl.ajouterTraceExecution(msg);
    }
}