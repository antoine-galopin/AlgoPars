package AlgoPars.CUI;

import AlgoPars.AlgoPars;

import java.util.ArrayList;


public class Affichage {
    private AlgoPars ctrl;
    private ArrayList<String> traceExecution;


    public Affichage(AlgoPars ctrl) {
        this.ctrl = ctrl;
        this.traceExecution = new ArrayList<String>();
    }


    public void ajouterTraceExecution( String trace )
    {
        this.traceExecution.add( trace );
    }


    public void afficher() {
        System.out.print(this.entete());
        System.out.print(this.corpsAlgo());
        System.out.print(this.afficherTraceExecution());
    }


    private String entete() {
        String str = "¨".repeat(11);
        String sret = String.format("%-80s", str) + str + "\n";
        sret += String.format("%-80s", "|  CODE   |") + "| DONNEES |" + "\n";
        sret += "¨".repeat(80) + " " + "¨".repeat(39) + "\n";
        return sret;
    }
    

    private String corpsAlgo() {
        ArrayList<String> fichier = this.ctrl.getLignesFichierColorie();
        int posDebut = this.ctrl.getLigneActive() >= 39 ? this.ctrl.getLigneActive() - 39 : 0;
        String sRet = "";

        for ( int cpt = posDebut ; cpt <= posDebut + 39; cpt++ )
        {
            if ( cpt == fichier.size() )
                break;

            sRet += "|" + String.format( "%3d", cpt ) + ( cpt == this.ctrl.getLigneActive() ? ">" : " " ) +
                    String.format( "%-75s", fichier.get( cpt ) );

            if ( cpt == posDebut )
            {
                sRet += "|     NOM        |        VALEUR       |\n";
            }
            else if ( cpt < fichier.size() )
                sRet += "|                |                     |\n";  // à modifier plus tard pour pouvoir afficher les variables.
        }

        return sRet + "¨".repeat(120) + "\n\n";
    }


    private String afficherTraceExecution()
    {
        String sRet = "¨".repeat( 11 ) + "\n| CONSOLE |\n" + "¨".repeat( 120 ) + "\n";
        
        int indexDebut = this.traceExecution.size() > 3 ? this.traceExecution.size() - 3 : 0;
        for ( ; indexDebut < indexDebut + 3; indexDebut++ )
        {
            if ( indexDebut == this.traceExecution.size() ) break;
            sRet += "|" + String.format( "%-118s", this.traceExecution.get( indexDebut ) ) + "|\n";
        }
        
        sRet += "|>" + " ".repeat( 117 ) + "|\n";
        return sRet + "¨".repeat( 120 ) ;
    }
}
