package AlgoPars.CUI;

import AlgoPars.AlgoPars;

import java.util.ArrayList;


public class Affichage {
    private AlgoPars ctrl;

    private ArrayList<String> traceExecution;


    public Affichage( AlgoPars ctrl ) {
        this.ctrl = ctrl;
    }


    public void afficher()
    {
        System.out.print( this.entete() );
        System.out.print( this.corpsAlgo() );
        System.out.print( this.afficherTraceExecution() );
    }


    private String entete() {
        String str = "¨".repeat(11);
        String sret = String.format("%-80s", str) + str + "\n";
        sret += String.format("%-80s", "|  CODE   |") + "| DONNEES |" + "\n";
        sret += "¨".repeat(80) + " " + "¨".repeat(39) + "\n";
        return sret;
    }


    private String corpsAlgo()
    {
        ArrayList<String> fichier = this.ctrl.getLignesFichier();
        String sRet = "|" + "  0 " + String.format("%-75s", fichier.get(0)) + "|" + String.format("%8s", "NOM")
                + String.format("%9s", "|") + String.format("%14s", "VALEUR") + String.format("%9s", "|\n");
        
        for ( int cpt = 1; cpt < fichier.size(); cpt++ )
            sRet += "|" + String.format("%3d ", cpt) + String.format("%-75s", fichier.get( cpt )) + "|                |                     |\n";
        
        return sRet + "¨".repeat( 120 ) + "\n\n";
    }


    private String afficherTraceExecution()
    {
        String sRet = "¨".repeat( 11 ) + "\n| CONSOLE |\n" + "¨".repeat( 120 ) + "\n";
        return sRet;
    }
}
