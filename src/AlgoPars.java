package AlgoPars;

import AlgoPars.Metier.Programme;


public class AlgoPars
{
    private Programme prgm;


    public AlgoPars()
    {
        this.prgm = new Programme( this, "test.algo" );
    }


    public static void main( String[] args )
    {
        new AlgoPars();
    }
}