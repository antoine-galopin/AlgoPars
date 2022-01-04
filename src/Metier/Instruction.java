package AlgoPars.Metier;

import AlgoPars.AlgoPars;
import AlgoPars.Metier.Primitives;

public class Instruction {
    public static boolean bconstante = false;
    public static boolean bvariable = false;

    private AlgoPars ctrl;
    private Primitives primit;
    private String[] ligne;
    private String[] var;
    private String type;
    private String valeur;

    public Instruction(AlgoPars ctrl, Primitives primit, String ligne) {
        this.ctrl = ctrl;
        this.primit = primit;

        if (ligne.contains("ecrire") || ligne.contains("lire")) {
            this.ligne = ligne.split("\\(");
            this.ligne[1] = this.ligne[1].replace("\\(", "").replace(")", "").strip();
        } else if (ligne.contains(":")) {
            this.ligne = ligne.strip().split(":");

            if (this.ligne[0].contains(",")) {
                this.var = this.ligne[0].split(",");
            } else {
                this.var = new String[] { this.ligne[0] };
            }
            if (this.ligne[1].contains("<--")) {
                this.ligne = this.ligne[1].split("<--");
                this.type = this.ligne.length == 2 ? this.ligne[0] : null;
                this.valeur = this.ligne[this.ligne.length];
            }
        } else if (ligne.contains("<--")) {
            this.ligne = ligne.strip().split("<--");
            if (this.ligne[0].contains(",")) {
                this.var = this.ligne[0].split(",");
            } else {
                this.var = new String[] { this.ligne[0] };
            }
            this.valeur = this.ligne[1];
        }
    }

    public void interpreterLigne() {

        switch (this.ligne[0].strip()) {
            case "ALGORITHME":
                break;
            case "constante:":
                bconstante = true;
                break;
            case "variable:":
                bconstante = false;
                bvariable = true;
                break;
            case "DEBUT":
                bconstante = false;
                bvariable = false;
                System.out.println("Debut");
                break;
            case "ecrire":
                this.primit.ecrire(this.ligne[1]);
                break;
            case "lire":
                this.primit.lire(this.ligne[1]);
                break;
            case "si":
                System.out.println("si");
                break;
            case "sinon":
                System.out.println("sinon");
                break;
            case "fsi":
                System.out.println("fsi");
                break;
            default:
                this.declare();
                break;
        }
    }

    public void declare() {
        if (bvariable) {
            for (String var : this.var) {
                this.ctrl.add(var, this.type);
            }
        } else if (bconstante) {
            for (String var : this.var) {
                this.ctrl.add(var, this.type, this.valeur);
            }
        }
        for (String var : this.var) {
            this.ctrl.affecterValeur(var, this.valeur);
        }
    }
}