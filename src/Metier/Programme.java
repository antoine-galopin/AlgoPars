package AlgoPars.Metier;

import AlgoPars.AlgoPars;

import java.util.ArrayList;
import java.util.Scanner;

import java.io.FileInputStream;


public class Programme 
{
	private AlgoPars algoPars;
	private ArrayList<String> lignesFichier;
	private int ligneActive;

	private boolean executionActive;


	public Programme( AlgoPars algoPars, String cheminFichier )
	{
		this.algoPars = algoPars;
		this.lignesFichier = new ArrayList<String>();
		this.ligneActive = 0;
		this.executionActive = true;

		try
		{
			Scanner sc = new Scanner( new FileInputStream( cheminFichier ) );
			
			while ( sc.hasNextLine() )
				this.lignesFichier.add( sc.nextLine() );
		}
		catch( Exception e ) { e.printStackTrace(); }


		this.executerAlgo();
	}


	public int getLigneActive() { return this.ligneActive; }
	public ArrayList<String> getLignesFichier() { return this.lignesFichier; }


	/**
	 * Exécution de l'algorithme.
	 */
	private void executerAlgo()
	{
		while( this.executionActive )
		{
			// Remplacer le sysout par l'exécution d'une ligne.
			System.out.print( this.lignesFichier.get( this.ligneActive ) );

			++this.ligneActive;
			if ( this.ligneActive == this.lignesFichier.size() ) return;
			try { new Scanner( System.in ).nextLine(); }
			catch( Exception e ) { e.printStackTrace(); }
		}
	}
}
