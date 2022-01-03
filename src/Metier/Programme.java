package AlgoPars.Metier;

import java.util.ArrayList;
import java.util.Scanner;

import java.io.FileInputStream;


public class Programme 
{
	private ArrayList<String> lignesFichier;


	public Programme( String cheminFichier )
	{
		this.lignesFichier = new ArrayList<String>();

		try
		{
			Scanner sc = new Scanner( new FileInputStream( cheminFichier ) );
			
			while ( sc.hasNextLine() )
				this.lignesFichier.add( sc.nextLine() );
		}
		catch( Exception e ) { e.printStackTrace(); }
	}
}
