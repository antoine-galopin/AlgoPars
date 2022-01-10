package AlgoPars.Metier;

import AlgoPars.AlgoPars;
import AlgoPars.Metier.Primitives;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class Instruction {
    private AlgoPars   ctrl;
    private Primitives primit;
    private String[]   ligne;
    private String     ligneComplete;

    public Instruction(AlgoPars ctrl, Primitives primit, String ligne) {
        this.ctrl          = ctrl;
        this.primit        = primit;
        this.ligneComplete = this.suppEspace(ligne);

        Pattern pattern = Pattern.compile( "\\w+ ?\\(" );
        Matcher matcher = pattern.matcher( ligne       );

        if( matcher.find() ) {
            this.ligne = ligne.split("\\(");
            this.ligne[1] = this.ligne[1].replace("\\(", "").replace(")", "").strip();
        } else
            this.ligne = ligne.strip().split(" ");
    }

    public void interpreterLigne() {
        if( this.ligne[0] != "" ) {
            switch( this.ligne[0].strip() ) {
                case "ALGORITHME":
                    break;
                case "constante:":
                    this.ctrl.setBConstante(true);
                    break;
                case "variable:":
                    this.ctrl.setBConstante(false);
                    this.ctrl.setBVariable(true);
                    break;
                case "DEBUT":
                    this.ctrl.setBConstante(false);
                    this.ctrl.setBVariable(false);
                    break;
                case "écrire":
                    this.primit.ecrire(this.ligne[1]);
                    break;
                case "lire":
                    this.lire();
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
        if( this.ctrl.getBVariable() ) {
            this.declareVar();
        } else if( this.ctrl.getBConstante() ) {
            this.declareConst();
        } else if( this.ligneComplete.contains("<--") ) {
            this.affecterValeur();
        }
    }


    /**
     * Méthode de déclaration des variables
     */
    private void declareVar() {
        String[] noms = this.ligneComplete.split(":")[0].split(",");
        String   type = this.ligneComplete.split(":")[1];
        
        for( String nom : noms )
            this.ctrl.add(nom, type);
    }


    /**
     * Méthode de déclaration des constantes
     */
    private void declareConst() {
        String[] noms;
        String   type   = null;
        String   valeur = this.ligneComplete.split("<--")[1];

        this.ligne = this.ligneComplete.split(":");

        if( this.ligne.length != 1 ) { // avec type
            noms = this.ligne[0].split(",");
            type = this.ligne[1].split("<--")[0];
        } else { // sans type
            noms = this.ligneComplete.split("<--")[0].split(",");
        }

        for( String nom : noms )
            this.ctrl.add(nom, type, valeur);
    }


    /**
     * Méthode d'affectation de valeurs aux variables
     */
    private void affecterValeur() {
        String[] noms = this.ligneComplete.split("<--")[0].split(",");
        String valeur = this.ligneComplete.split("<--")[1].replace("\"", "").replace("'", "");

        for( String nom : noms ) 
            this.ctrl.affecterValeur(nom, valeur);
    }


    /**
     * Méthode de suppression des espaces sur une ligne
     * Ne supprime pas les espaces compris dans une chaine de caractère
     * @param ligne
     * @return String
     */
    private String suppEspace(String ligne) {
        if( ligne.contains("\"") ) {
            while( (ligne.indexOf("\"") > ligne.indexOf(" " ) && ligne.indexOf(" " ) != -1)
                || (ligne.indexOf("\"") > ligne.indexOf("\t") && ligne.indexOf("\t") != -1) ) {

                ligne = ligne.replaceFirst(" " , "");
                ligne = ligne.replaceFirst("\t", "");
            }
        } else ligne = ligne.replaceAll("\\s", "");

        return ligne;
    }


    private void lire() {
        String[] vars = this.suppEspace( this.ligne[1] ).split(",");

        for ( String nomVar: vars )
            this.primit.lire( nomVar );
    }
}