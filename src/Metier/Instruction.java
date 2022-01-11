package AlgoPars.Metier;

import AlgoPars.AlgoPars;
import AlgoPars.Metier.Primitives;
import AlgoPars.Metier.Calculateur;
import AlgoPars.Metier.Types.*;

import java.lang.reflect.Method;

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
                case "algorithme":
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

    public int interpreterLigne(int siImbrique) {
        if (this.ligne[0].equals("sinon") && siImbrique == 0) {
            return -1;
        }
        switch (this.ligne[0]) {
            case "si":
                return 1;
            case "fsi":
                return -1;
        }
        return 0;

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
        String str = this.ligneComplete.substring(this.ligneComplete.indexOf("si") + 2,
                this.ligneComplete.indexOf("alors"));

        if (this.containsComparateur(str)) {
            this.executerFonction(str);
            str = this.remplacerParValeur(str);
            System.out.println(str + " " + Calculateur.calculer(str) + "calcul");
            this.primit.si(Calculateur.calculer(str));

        } else {
            if (str.equals("vrai") || str.equals("faux"))
                this.primit.si(str);
            else
                this.primit.si(this.ctrl.getValeur(str));
        }
    }

    private String remplacerParValeur(String str) {
        Pattern ptrn = Pattern.compile("\\w+(?![^\"]*\"[^\"]*(?:\"[^\"]*\"[^\"]*)*$)");
        Matcher matcher = ptrn.matcher(str);
        while (matcher.find()) {
            if (!Pattern.compile("\\b(?<!\\.)\\d+(?!\\.)\\b").matcher(matcher.group()).find())
                str = str.replaceAll(matcher.group(), this.ctrl.getValeur(matcher.group()));
        }
        return str;
    }

    private boolean containsComparateur(String str) {
        return str.contains("<") || str.contains(">") || str.contains("=") || str.contains("ou") || str.contains("xou")
                || str.contains("et") || str.contains("non");
    }

    private void lire() {
        String[] vars = this.suppEspace(this.ligne[1]).split(",");

        for (String nomVar : vars)
            this.primit.lire(nomVar);
    }

    /*--------------------------------------*/

    private String executerFonction(String str) {
        Pattern ptrn = Pattern.compile("\\w+ ?\\(.*\\)");
        Matcher matcher = ptrn.matcher(str);
        while (matcher.find()) {
            String sRet = matcher.group();
            this.executerFonction(sRet.substring(sRet.indexOf("(") + 1, sRet.indexOf(")")));
            this.remplacerParValeur(
                    sRet.substring(sRet.indexOf("(") + 1, sRet.indexOf(")")));
            str = str.replace(
                    sRet, this.executerFonction(
                            sRet.substring(0, sRet.indexOf("(")),
                            sRet.substring(sRet.indexOf("(") + 1, sRet.indexOf(")"))));
        }
        return str;
    }

    /**
     * Méthode qui renvoie le resultat d'une méthode de primitive executée
     * 
     * @param nomFonction nom de la méthode à executer
     * @param parametre   paramètres que la méthode prend
     * @return Object que la fonction renvoie
     */

    public Object executerFonction(String nomFonction, String parametres) {
        for (Method m : primit.listePrimitives) {
            if (m.getName().equals(nomFonction)) {
                try {
                    return m.invoke(primit, (Object[]) parametres);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        return null;
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