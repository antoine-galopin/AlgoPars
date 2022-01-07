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
        if (msg.contains("\"")) {
            this.ctrl.ajouterTraceExecution(msg.replaceAll("\"", ""));
        } else if (msg.matches("^[0-9]+") || msg.equals("")) {
            this.ctrl.ajouterTraceExecution(msg);
        } else {
            this.ctrl.ajouterTraceExecution(this.ctrl.getValeur(msg));
        }
    }

    public void si(String msg) {
        switch (msg) {
            case "vrai":
                this.ctrl.setTabSi = this.getTabSi() + new boolean[] { true };
                break;
            case "false":
                this.ctrl.setTabSi = this.getTabSi() + new boolean[] { false };
                break;
        }

    }
}