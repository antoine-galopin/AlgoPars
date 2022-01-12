package AlgoPars.CUI;

import AlgoPars.AlgoPars;

import java.util.ArrayList;
import iut.algo.Console;
import iut.algo.CouleurConsole;


public class Affichage {
    private AlgoPars          ctrl;
    private ArrayList<String> traceExecution;
    private ArrayList<String> variablesSuivies;

    private int posDebut; // Première ligne de l'affichage du programme ( pas besoin de valeur initiale )

    /*---------------------------*/
    /* Paramètres de l'affichage */
    /*---------------------------*/

    private int tailleAffichage = 40; // Nombre de lignes du programme affichées
    private int margeAffichage  = 8;  // Marge de l'affichage du programme
    private int tailleConsole   = 3;  // Nombre de lignes de la console affichées

    /**
     * Constructeur de la classe Affichage
     * Chaque Affichage a un controleur et une trace d'execution propre
     * @param ctrl Contrôleur de l'instance d'Affichage
     */
    public Affichage(AlgoPars ctrl) {
        this.ctrl = ctrl;
        this.traceExecution = new ArrayList<String>();
    }


    /**
     *  Méthode d'initialisation de l'ArrayList contenant les noms des variables suivies
     */
	public void initialiserVariablesSuivies() {
        this.variablesSuivies = ctrl.getVariablesSuivies();
    }


    /**
     * Méthode pour ajouter une nouvelle ligne à la trace d'execution
     * @param trace ligne à ajouter à la trace d'execution
     */
    public void ajouterTraceExecution( String trace ) {
        this.traceExecution.add( trace );
    }


    /**
     * Méthode qui lance l'affichage global ( entete + corps + trace d'execution )
     */
    public void afficher() {
        // Réinitialisation de la console ( plus de colorations )
        Console.normal();

        // Récupération des Strings générés par les diverses parties de l'affichage global
        Console.print( this.entete () );
        Console.print( this.corps  () );
        Console.print( this.console() );
    }


    /**
     * Méthode de création de l'entete commun à chaque affichage
     * @return String entête de l'affichage
     */
    private String entete() {
        return String.format("%-80s", "┌" + "─".repeat(9) + "┐") + "┌" + "─".repeat(9) + "┐\n" // Ligne 1
            + String.format("%-80s", "│  CODE   │") + "│ DONNEES │" + "\n" // Ligne 2
            + "├" + "─".repeat(9) + "┴" + "─".repeat(69) + "┼" + "─".repeat(9) + "┴" + "─".repeat(6) + "┬" + "─".repeat(21) + "┐\n"; // Ligne 3
    }


    /**
     * Méthode de création du corps de l'affichage
     * Le corps évolue en fonction de l'indice de la ligne courante
     * @return String corps de l'affichage
     */
    private String corps() {
        ArrayList<String > fichier  = this.ctrl.getLignesFichierColorie();
        ArrayList<Integer> listeBk  = this.ctrl.getListeBreakPoints();    
        int                numLigne = this.ctrl.getLigneActive();    

        // Début de l'affichage ( de la ligne [posDebut] à la ligne [posDebut + margeAffichage - 1] )
        if( numLigne < posDebut + margeAffichage ) {
            int i = margeAffichage + posDebut - numLigne;

            for( int cpt = 0; cpt < i; cpt++ )
                posDebut = posDebut == 0 ? 0 : posDebut - 1;
        }
        // Fin de l'affichage ( de la ligne [tailleAffichage - margeAffichage + posDebut] à la ligne [tailleAffichage + posDebut - 1] )
        else {
            if( numLigne > posDebut + tailleAffichage - margeAffichage - 1 ) {
                int j = Math.abs(posDebut + tailleAffichage - margeAffichage - 1 - numLigne);

                for( int cpt = 0; cpt < j; cpt++ )
                    if( posDebut < fichier.size() - tailleAffichage ) posDebut++;
            }
        }

        String sRet = "";

        // Construction et ajout de chaque ligne à sRet dans l'ordre
        for( int cpt = posDebut; cpt < ( fichier.size() > tailleAffichage ? posDebut + tailleAffichage : fichier.size() ); cpt++ ) {

            /*------------------------*/
            /* Affichage du programme */
            /*------------------------*/

            // Barre gauche
            sRet += "│";

            // Indice de ligne ( coloré en rouge si breakpoint sur la ligne observée )
            if( listeBk.contains( cpt ) ) sRet += CouleurConsole.ROUGE.getFont() + String.format("%3d", cpt) + "\033[0m";
            else                          sRet +=                                  String.format("%3d", cpt);

            // Présence de curseur ou non ( selon la ligne courante )
            sRet += cpt == numLigne ? ">" : " ";

            // Code de la ligne
            sRet += String.format("%-75s", fichier.get(cpt));

            /*---------------------*/
            /* Trace des variables */
            /*---------------------*/

            if     ( cpt == posDebut     ) sRet += "│      NOM       │        VALEUR       │\n"; // Ligne 1
            else if( cpt == posDebut + 1 ) sRet += "├────────────────┼─────────────────────┤\n"; // Ligne 2
            else {
                if( cpt - posDebut <= variablesSuivies.size() ) {
                    if( this.ctrl.getValeur( this.variablesSuivies.get(cpt - posDebut - 2) ) != null ) {
                        sRet += "│ "
                            + String.format( "%-14s", this.variablesSuivies.get(cpt - posDebut - 2) )
                            + " │ "
                            + String.format( "%-19s", this.ctrl.getValeur( this.variablesSuivies.get(cpt - posDebut - 2) ) )
                            + " │\n";
                    }
                    // Variable observée pas encore instanciée, on ne la suit pas
                    else sRet += "│                │                     │\n";
                }
                // Plus de variables à suivre, on n'affiche rien
                else sRet += "│                │                     │\n";
            }
        }

        // Dernière ligne du corps de l'affichage
        return sRet + "└" + "─".repeat(79) + "┴" + "─".repeat(16) + "┴" + "─".repeat(21) + "┘\n\n";
    }


    /**
     * Méthode de création de la partie console de l'affichage
     * @return String console de l'affichage
     */
    private String console() {
        // Entête de la console
        String sRet = "┌" + "─".repeat(9) + "┐\n" // Ligne 1
                    + "│ CONSOLE │\n" // Ligne 2
                    + "├" + "─".repeat(9) + "┴" + "─".repeat(108) + "┐\n"; // Ligne 3

        // Recherche de l'indice auquel commencer à afficher les lignes pour respecter la variable [tailleConsole]
        int index = this.traceExecution.size() > tailleConsole ? this.traceExecution.size() - tailleConsole : 0;

        // Affichage des [tailleConsole] dernières lignes de la trace
        while( index < this.traceExecution.size() )
            sRet += "│" + String.format("%-118s", this.traceExecution.get(index++)) + "│\n";

        // Deux dernières lignes de la console
        return sRet += "│>" + " ".repeat( 117 ) + "│\n"
                    +  "└"  + "─".repeat( 118 ) + "┘\n";
    }
}
