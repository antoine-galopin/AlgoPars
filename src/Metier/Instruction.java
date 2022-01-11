package AlgoPars.Metier;

import AlgoPars.AlgoPars;
import AlgoPars.Metier.Primitives;
import AlgoPars.Metier.Calculateur;
import AlgoPars.Metier.Types.*;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class Instruction {
    private AlgoPars ctrl;
    private Primitives primit;
    private String[] ligne;
    private String ligneComplete;
    public boolean estFonction = false;// une fonction seule sur la ligne

    public Instruction(AlgoPars ctrl, Primitives primit, String ligneRecue) {
        this.ctrl = ctrl;
        this.primit = primit;
        this.ligneComplete = this.suppEspace(ligneRecue);

        // si c'est une fonction

        Pattern pattern = Pattern.compile("\\w+ ?\\(");
        Matcher matcher = pattern.matcher(ligneRecue);

        if (matcher.find()) {
            pattern = Pattern.compile("\"\\w+ *\\(.*\"");
            matcher = pattern.matcher(ligneRecue);

            // si ce n'est pas une chaine comme "fonc("
            if (!matcher.find()) {
                this.ligne = ligneRecue.split("\\(");
                this.ligne[1] = this.ligne[1].replace("\\(", "").replace(")", "").strip();
            }
        } else
            this.ligne = ligneRecue.strip().split(" ");
    }

    public void interpreterLigne() {
        if (this.ligne[0] != "") {
            switch (this.ligne[0].strip().toLowerCase()) {
                case "algorithme", "Algorithme":
                    this.ctrl.setNom(ligne[0].substring(ligne[0].indexOf("ALGORITHME ") + 11));
                    break;
                case "constante:":
                    this.ctrl.setBConstante(true);
                    break;
                case "variable:":
                    this.ctrl.setBConstante(false);
                    this.ctrl.setBVariable(true);
                    break;
                case "debut", "début":
                    this.ctrl.setBConstante(false);
                    this.ctrl.setBVariable(false);
                    break;
                case "ecrire", "écrire":
                    this.primit.ecrire(this.ligne[1]);
                    break;
                case "lire":
                    this.lire();
                    break;
                case "si":
                    this.si();
                    break;
                case "fsi":
                    System.out.println("fsi");
                    break;
                case "sinon":
                    System.out.println("sinon");
                    break;
                default:
                    this.declare();
                    break;
            }
        }
    }

    private void declare() {
        if (this.ctrl.getBVariable()) {
            this.declareVar();
        } else if (this.ctrl.getBConstante()) {
            this.declareConst();
        } else if (this.ligneComplete.contains("<--")) {
            this.affecterValeur();
        }
    }

    /**
     * Méthode de déclaration des variables
     */
    private void declareVar() {
        String[] noms = this.ligneComplete.split(":")[0].split(",");
        String type = this.ligneComplete.split(":")[1];

        for (String nom : noms)
            this.ctrl.add(nom, type);
    }

    /**
     * Méthode de déclaration des constantes
     */
    private void declareConst() {
        String[] noms;
        String type = null;
        String valeur = this.ligneComplete.split("<--")[1];

        this.ligne = this.ligneComplete.split(":");

        if (this.ligne.length != 1) { // avec type
            noms = this.ligne[0].split(",");
            type = this.ligne[1].split("<--")[0];
        } else { // sans type
            noms = this.ligneComplete.split("<--")[0].split(",");
        }

        for (String nom : noms)
            this.ctrl.add(nom, type, valeur);
    }

    /**
     * Méthode d'affectation de valeurs aux variables
     */
    private void affecterValeur() {
        String[] noms = this.ligneComplete.split("<--")[0].split(",");
        String valeur = this.ligneComplete.split("<--")[1].replace("\"", "").replace("'", "");

        for (String nom : noms)
            this.ctrl.affecterValeur(nom, valeur);
    }

    /**
     * Méthode de suppression des espaces sur une ligne
     * Ne supprime pas les espaces compris dans une chaine de caractère
     * 
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
        } else
            ligne = ligne.replaceAll("\\s", "");

        return ligne;
    }

    private void si() {
        // Pattern ptrn = Pattern.compile( "TA_REGEX" );
        // Matcher matcher = ptrn.matcher( "TA_STRING" );

        // while ( matcher.find() ) System.out.println( matcher.group() );
        String str = this.ligneComplete.substring(this.ligneComplete.indexOf("si") + 2,
                this.ligneComplete.indexOf("alors"));

        if (str.contains("\"")) {
            this.ligneComplete.indexOf("\"");
        } else if (str.contains("[0-9]+")) {

        } else if (str.contains("[A-Za-z]+")) {

        }
        this.primit.si(this.ligneComplete.substring(this.ligneComplete.indexOf("si") + 2,
                this.ligneComplete.indexOf("alors")));
    }

    private void lire() {
        String[] vars = this.suppEspace(this.ligne[1]).split(",");

        for (String nomVar : vars)
            this.primit.lire(nomVar);
    }

    /*--------------------------------------*/

    public void executerFonction(String varS, String nomFonction, String args) {
        // rajouté des test pour enlever les warnings
        ctrl.getTypable(varS).setValeur(ctrl.executerFonction(nomFonction, convertParam(args)));
    }

    /**
     * convertie une chaine en sont tableau de typable pour utiliser les fonctions
     * 
     * @param param chaine des caractere sans les parenthese exterieur
     */
    private Typable[] convertParam(String param) {
        // on separe la chaine
        String[] args = param.split(" *, *");
        Typable[] retour = new Typable[args.length];

        int index = 0;
        for (String s : args) {
            retour[index] = ctrl.getTypable(s);
            index++;
        }
        return retour;
    }

}