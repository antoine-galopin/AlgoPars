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

    public String lire(String var) {
        String msg = "";
        Scanner sc = null;
        try {
            sc = new Scanner(System.in);
            msg = sc.nextLine();
            sc.close();
        } catch (Exception e) {
        }
        return msg;
    }

    public void ecrire(String msg) {
        this.ctrl.ajouterTraceExecution(msg);
    }
}