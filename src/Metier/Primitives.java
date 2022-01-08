package AlgoPars.Metier;

import AlgoPars.AlgoPars;
import AlgoPars.Metier.Programme;
import java.util.ArrayList;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

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
        String result = "";

        // Cette regex permet de
        Pattern ptrn = Pattern.compile("(\"(.+?)\")|(\\d+(\\.\\d*)*)|(\\w+)");
        Matcher matcher = ptrn.matcher(msg);

        String param = "";
        while (matcher.find()) {
            param = matcher.group();
            if (param.contains("\""))
                result += param.replace("\"", "") + " ";
            else if (param.equals("vrai") || param.equals("faux"))
                result += param + " ";
            else if (this.ctrl.getValeur(param) != null)
                result += this.ctrl.getValeur(param) + " ";
            else
                result += param + " ";
        }

        this.ctrl.ajouterTraceExecution(result);
    }

    public void si(String msg) {
        if (this.ctrl.getAlSi() != null) {
            // switch (msg) {
            // case "vrai":
            // this.ctrl.setAlSi(this.ctrl.getAlSi().add(true));
            // break;
            // case "false":
            // this.ctrl.setAlSi(this.ctrl.getAlSi().add(false));
            // break;
            // }
        } else {
            ArrayList<Boolean> result = new ArrayList<Boolean>();

            switch (msg) {
                case "vrai":
                    result.add(Boolean.TRUE);
                    this.ctrl.setAlSi(result);
                    break;
                case "false":
                    result.add(Boolean.FALSE);
                    this.ctrl.setAlSi(result);
                    break;
            }

        }
    }
}