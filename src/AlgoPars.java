package AlgoPars;

import AlgoPars.Metier.Programme;
import AlgoPars.CUI.Affichage;

import java.util.ArrayList;


public class AlgoPars
{
    private Programme prgm;
    private Affichage cui;


    public AlgoPars()
    {
        this.cui = new Affichage( this );
        this.prgm = new Programme( this, "../Test/Test.algo" );
        this.prgm.executerAlgo();
    }


    public ArrayList<String> getLignesFichier() { return this.prgm.getLignesFichier(); }

    public void afficher() { this.cui.afficher(); }

    public void ajouterTraceExecution( String trace ) {
        this.cui.ajouterTraceExecution( trace );
    }

    public static void main( String[] args )
    {
        new AlgoPars();
    }
}