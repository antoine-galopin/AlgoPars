package AlgoPars.Metier;

import AlgoPars.AlgoPars;

import java.util.ArrayList;
import java.util.Scanner;

import java.io.FileInputStream;

public class Programme {
	private AlgoPars ctrl;
	private Primitives primitives;

	private int ligneActive;
	private ArrayList<String> lignesFichier;
	private ArrayList<String> lignesFichierColorie;

	private Donnee donnees;
	private ArrayList<Instruction> listeInstructions;

	private boolean executionActive;


	public Programme(AlgoPars ctrl, String cheminFichier) {
        // Important car cela permet de charger le fichier XML des couleurs.
        ColorationSyntaxique.chargerCouleurs();

		this.ctrl = ctrl;
		this.primitives = new Primitives(this.ctrl, this);

		this.lignesFichier = new ArrayList<String>();
		this.lignesFichierColorie = new ArrayList<String>();
		this.ligneActive = 0;
		this.executionActive = true;

		this.donnees = new Donnee();
		this.listeInstructions = new ArrayList<Instruction>();

		try {
			Scanner sc = new Scanner(new FileInputStream(cheminFichier));

			String ligne = "";
			while (sc.hasNextLine()) {
				ligne = sc.nextLine().replace("\t", "    ");
				this.lignesFichier.add(ligne);
				this.lignesFichierColorie.add( ColorationSyntaxique.colorierLigne( ligne ) );
				this.listeInstructions.add( new Instruction( this.ctrl, this.primitives, ligne ) );
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	public int getLigneActive() { return this.ligneActive; }
	public ArrayList<String> getLignesFichier() { return this.lignesFichier; }
	public ArrayList<String> getLignesFichierColorie() { return this.lignesFichierColorie; }


	public void affecterValeur( String nom, String valeur )
	{
		this.donnees.affecterValeur( nom, valeur );
	}

	/**
	 * Exécution de l'algorithme.
	 */
	public void executerAlgo() {
		
		do 
		{
			this.listeInstructions.get(this.ligneActive).interpreterLigne();
			this.ctrl.afficher();

			++this.ligneActive;
			
			try {
				new Scanner(System.in).nextLine();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}while (this.executionActive && this.ligneActive < this.lignesFichier.size() );
	}


	public void add( String nom, String type, String valeur )
    {
        this.donnees.add( nom, type, valeur );
    }


    public void add( String nom, String type )
    {
        this.donnees.add( nom, type );
    }
}
