package AlgoPars.Metier;

import javax.lang.model.util.ElementScanner14;

import AlgoPars.AlgoPars;
import AlgoPars.Metier.Primitives;

public class Instruction {
    public static boolean bconstante = false;
    public static boolean bvariable = false;

    private AlgoPars ctrl;
    private Primitives primit;
    private String[] ligne;
    private String ligneComplete;

    public Instruction(AlgoPars ctrl, Primitives primit, String ligne) {
        this.ctrl = ctrl;
        this.primit = primit;
        this.ligneComplete = ligne;

        if (ligne.contains("ecrire") || ligne.contains("lire")) {
            this.ligne = ligne.split("\\(");
            this.ligne[1] = this.ligne[1].replace("\\(", "").replace(")", "").strip();
        } else {
            this.ligne = ligne.strip().split(" ");
        }
    }

    public void interpreterLigne() {
        if (this.ligne[0] != "") {

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
                    break;
                case "ecrire":
                    this.primit.ecrire(this.ctrl.getValeur(this.ligne[1]));
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
    }

    private void declare() {
        if (bvariable) {
            this.declareVar();
        } else if (bconstante) {
            this.declareConst();
        } else if (this.ligneComplete.contains("<--")) {
            this.affecterValeur();
        }
    }

    private void declareVar() {
        String[] var;
        String type;

        this.ligne = this.suppEspace(this.ligneComplete).split(":");

        var = this.separeVirgule(this.ligne[0]);

        type = this.ligne[1];

        for (String variable : var) {
            this.ctrl.add(variable, type);
        }
    }

    private void declareConst() {
        String[] var;
        String type;
        String valeur;

        this.ligneComplete = this.suppEspace(this.ligneComplete);

        if (this.ligneComplete.contains(":")) {
            this.ligne = this.ligneComplete.split(":");
            var = this.separeVirgule(this.ligne[0]);
            this.ligne = this.separeAffectation(this.ligne[1]);
            type = this.ligne[0];
        } else {
            type = null;
            this.ligne = this.separeAffectation(this.ligneComplete);
            var = this.separeVirgule(this.ligne[0]);
        }
        valeur = this.ligne[1];

        for (String variable : var) {
            this.ctrl.add(variable, type, valeur);
        }
    }

    private void affecterValeur() {
        String[] var;
        String valeur;

        this.ligneComplete = this.suppEspace(this.ligneComplete);
        this.ligne = this.separeAffectation(this.ligneComplete);
        var = this.separeVirgule(this.ligne[0]);
        valeur = this.ligne[1];

        for (String variable : var) {
            System.out.println(variable + " " + valeur);
            this.ctrl.affecterValeur(variable, valeur);
        }

    }

    /**
     * @param ligne
     * @return String[]
     */
    private String[] separeVirgule(String ligne) {
        if (ligne.contains(",")) {
            return ligne.split(",");
        } else {
            return new String[] { ligne };
        }
    }

    /**
     * @param ligne
     * @return String[]
     */
    private String[] separeAffectation(String ligne) {
        if (ligne.contains("<--")) {
            return ligne.split("<--");
        } else {
            return new String[] { ligne };
        }
    }

    /**
     * @param ligne
     * @return String
     */
    private String suppEspace(String ligne) {
        if (ligne.contains("\"")) {
            while ((ligne.indexOf("\"") > ligne.indexOf(" ") && ligne.indexOf(" ") != -1)
                    || (ligne.indexOf("\"") > ligne.indexOf("\t") && ligne.indexOf("\t") != -1)) {
                ligne = ligne.replaceFirst(" ", "");
                ligne = ligne.replaceFirst("\t", "");
            }
        } else {
            ligne = ligne.strip();
        }

        return ligne;
    }
}