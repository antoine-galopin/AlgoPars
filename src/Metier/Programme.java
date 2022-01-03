package AlgoPars.Metier;

import AlgoPars.AlgoPars;

import java.util.ArrayList;
import java.util.Scanner;

import java.io.FileInputStream;


public class Programme 
{
	private AlgoPars ctrl;
	private int ligneActive;
	private ArrayList<String> lignesFichier;


	private boolean executionActive;


	public Programme( AlgoPars ctrl, String cheminFichier )
	{
		this.ctrl = ctrl;
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
	}


	public int getLigneActive() { return this.ligneActive; }
	public ArrayList<String> getLignesFichier() { return this.lignesFichier; }


	/**
	 * Exécution de l'algorithme.
	 */
	public void executerAlgo()
	{
		while( this.executionActive )
		{
			this.ctrl.afficher();
			// Remplacer le sysout par l'exécution d'une ligne.

			++this.ligneActive;
			if ( this.ligneActive == this.lignesFichier.size() ) return;
			try { new Scanner( System.in ).nextLine(); }
			catch( Exception e ) { e.printStackTrace(); }
		}
	}
}
