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
        System.out.println( this.entete() );
        System.out.println( this.afficherTraceExecution() );
    }


    private String entete() {
        String str = "¨".repeat(11);
        String sret = String.format("%-80s", str) + str + "\n";
        sret += String.format("%-80s", "|  CODE   |") + "| DONNEES |" + "\n";
        sret += "¨".repeat(80) + " " + "¨".repeat(39) + "\n";
        sret += "|" + String.format("%-79s", this.ctrl.getLignesFichier().get(0)) + "|" + String.format("%8s", "NOM")
                + String.format("%9s", "|") + String.format("%14s", "VALEUR") + String.format("%8s", "|");
        return sret;
    }


    private String afficherTraceExecution()
    {
        String sRet = "¨".repeat( 11 ) + "\n| CONSOLE |\n" + "¨".repeat( 120 ) + "\n";
        return sRet;
    }
}
