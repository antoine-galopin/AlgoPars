package AlgoPars;

import AlgoPars.Metier.Programme;


public class AlgoPars
{
    private Programme prgm;
    private Affichage cui;


    public AlgoPars()
    {
        this.cui = new Affichage( this );
        this.prgm = new Programme( this, "test.algo" );
    }


    public static void main( String[] args )
    {
        new AlgoPars();
    }
}