package AlgoPars.Metier;

import AlgoPars.Metier.Primitives;

public class Instruction {
    public static boolean bconstante = false;
    public static boolean bvariable = false;
    private Primitives primit;
    private String[] ligne;

    public Instruction(Primitives primit, String ligne) {
        this.primit = primit;

        if (ligne.contains("ecrire") || ligne.contains("lire")) {
            this.ligne = ligne.split("\\(");
            this.ligne[1] = this.ligne[1].replace("\\(", "").replace(")", "").strip();
        } else
            this.ligne = ligne.split(" ");
    }

    public void interpreterLigne() {

        switch (this.ligne[0].strip()) {
            case "ALGORITHME":
                System.out.println("ALGORITHME");
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
            case "FIN":
                System.out.println("Fin");
                break;
            case "ecrire":
                this.primit.ecrire(this.ligne[1]);
                break;
            case "lire":
                // this.primit.lire(this.ligne[1]);
                break;
            case "si":
                System.out.println("si");
                break;
            case "sinon":
                System.out.println("si");
                break;
            case "fsi":
                System.out.println("si");
                break;
        }
    }

}