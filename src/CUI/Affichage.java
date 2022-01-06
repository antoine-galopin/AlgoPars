package AlgoPars.CUI;

import AlgoPars.AlgoPars;

import java.util.ArrayList;

import javax.lang.model.util.ElementScanner6;

public class Affichage {
    private AlgoPars ctrl;
    private ArrayList<String> traceExecution;

    int tailleAffichage = 40; // Variable qui gère le nombre de lignes de l'affichage du programme
    int margeAffichage  = 8;  // Variable qui gère la marge de l'affichage du programme
    int posDebut = 1;         // Variable qui gère la première ligne de l'affichage du programme

    /**
     * Constructeur de la classe Affichage
     * Chaque Affichage a un controleur et une trace d'execution propre
     * @param ctrl contrôleur de l'instance d'Affichage
     */
    public Affichage(AlgoPars ctrl) {
        this.ctrl = ctrl;
        this.traceExecution = new ArrayList<String>();
    }


    /**
     * Méthode pour ajouter une nouvelle ligne à la trace d'execution
     * @param trace ligne à ajouter à la trace d'execution
     */
    public void ajouterTraceExecution( String trace ) { this.traceExecution.add( trace ); }


    /**
     * Méthode qui lance l'affichage global ( entete + corps + trace d'execution )
     */
    public void afficher() {
        System.out.print(this.entete());
        System.out.print(this.corpsAlgo());
        System.out.print(this.afficherTraceExecution());
    }


    /**
     * Méthode de création de l'entete commun à chaque affichage
     * @return String
     */
    private String entete() {
        return String.format("%-80s", "¨".repeat(11)) + "¨".repeat(11) + "\n" // ligne 1
            + String.format("%-80s", "|  CODE   |") + "| DONNEES |" + "\n" // ligne 2
            + "¨".repeat(80) + " " + "¨".repeat(39) + "\n"; // ligne 3
    }


    /**
     * Méthode de création du corps de l'affichage
     * Le corps évolue en fonction de l'indice de la ligne courante
     * @return String
     */
    private String corpsAlgo() {
        ArrayList<String> fichier = this.ctrl.getLignesFichierColorie();
        int numLigne              = this.ctrl.getLigneActive();

        if( numLigne <= posDebut + margeAffichage ) // Début de l'affichage
            posDebut = posDebut == 0 ? 0 : posDebut - 1;
        else
            if( numLigne > posDebut + tailleAffichage - margeAffichage ) // Fin de l'affichage
                if( posDebut < fichier.size() - tailleAffichage - 1 ) posDebut++;

        String sRet = "";

        for( int cpt = posDebut; cpt <= ( fichier.size() > tailleAffichage ? posDebut + tailleAffichage : fichier.size() - 1 ); cpt++ ) {
            sRet += "|" + String.format("%3d", cpt) + ( cpt == numLigne ? ">" : " " ); // barre gauche + index ligne + curseur sur nécéssaire

            if( cpt < fichier.size() )
                sRet += String.format("%-75s", fichier.get(cpt)); // code de la ligne

            if( cpt == posDebut ) sRet += "|      NOM       |        VALEUR       |\n";
            else                  sRet += "|                |                     |\n"; // à modifier plus tard pour pouvoir afficher la trace des variables.
        }

        return sRet + "¨".repeat(120) + "\n\n";
    }


    /**
     * Méthode de création de la trace d'execution ( Partie "console" de l'affichage )
     * @return String
     */
    private String afficherTraceExecution() {
        String sRet = "¨".repeat( 11 ) + "\n" // ligne 1
                    + "| CONSOLE |\n" // ligne 2
                    + "¨".repeat( 120 ) + "\n"; // ligne 3

        // affichage des 3 dernières lignes de la trace
        int index = this.traceExecution.size() > 3 ? this.traceExecution.size() - 3 : 0;

        for( ; index < index + 3; index++ ) {
            if( index == this.traceExecution.size() ) break; // si on est arrivé au boût de la trace, on quitte

            sRet += "|" + String.format("%-118s", this.traceExecution.get(index)) + "|\n";
        }

        return sRet += "|>" + " ".repeat( 117 ) + "|\n" // avant dernière ligne de la "console"
            + "¨".repeat( 120 ); // dernière ligne de la "console" ( et de l'affichage global )
    }
}
