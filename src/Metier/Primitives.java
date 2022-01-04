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
        try {
            msg = new Scanner(System.in).nextLine();
        } catch (Exception e) {
        }
        System.out.println(msg);
        return msg;
    }

    public void ecrire(String msg) {
        this.ctrl.ajouterTraceExecution(msg);
    }
}