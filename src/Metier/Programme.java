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
<<<<<<< HEAD
	private ArrayList<String> listeVarSuivies;
=======
	private ArrayList<String> listeNomVarSuivies;
>>>>>>> 4cfb2e90f82936f2f0f2ee42f7d69dca6034b7a2
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
<<<<<<< HEAD
		this.listeVarSuivies = new ArrayList<String>();
=======
		this.listeNomVarSuivies = new ArrayList<String>();
>>>>>>> 4cfb2e90f82936f2f0f2ee42f7d69dca6034b7a2
		this.listeInstructions = new ArrayList<Instruction>();

		try {
			// Lecture du programme.
			Scanner sc = new Scanner(new FileInputStream(cheminFichier), "UTF-8" );

			String ligne = "";
			while(sc.hasNextLine()) {
				ligne = sc.nextLine().replace("\t", "    ");
				this.lignesFichier.add(ligne);
				this.lignesFichierColorie.add( ColorationSyntaxique.colorierLigne(ligne) );
				this.listeInstructions.add(new Instruction(this.ctrl, this.primitives, ligne));
			}

			sc.close();

			// Lecture du fichier contenant les variables à suivre.
			sc = new Scanner( new FileInputStream( "../utilisateur/variables.var" ), "UTF-8" );

			while ( sc.hasNextLine() )
<<<<<<< HEAD
				this.listeVarSuivies.add( sc.next().strip() );
=======
				this.listeNomVarSuivies.add( sc.next().strip() );
>>>>>>> 4cfb2e90f82936f2f0f2ee42f7d69dca6034b7a2

	
		} catch( Exception e ) {
			e.printStackTrace();
		}
	}


	public int getLigneActive() { return this.ligneActive; }
	public ArrayList<String> getLignesFichier() { return this.lignesFichier; }
	public ArrayList<String> getLignesFichierColorie() { return this.lignesFichierColorie; }
	
<<<<<<< HEAD
	public ArrayList<String> getVariablesSuivies() { return this.listeVarSuivies; }

	public String getValeur(String nom) {
		if( this.donnees.rechercheParNom(nom) != null )
			return this.donnees.rechercheParNom(nom).getValeur().toString();
		return null;
=======
	public ArrayList<String> getVariablesSuivies() { return this.listeNomVarSuivies; }

	public String getValeur(String nom) { // TODO ajouter verif quand la var est nulle.
		return this.donnees.rechercheParNom(nom).getValeur().toString();
>>>>>>> 4cfb2e90f82936f2f0f2ee42f7d69dca6034b7a2
	}

	public void affecterValeur(String nom, String valeur) {
		this.donnees.affecterValeur(nom, valeur);
	}

	/**
	 * Exécution de l'algorithme.
	 */
	public void executerAlgo() {
		do 
		{
			this.listeInstructions.get(this.ligneActive).interpreterLigne();
			this.ctrl.afficher();
			
			try {
				Scanner sc = new Scanner(System.in); // ouverture du Scanne
				
				String msg = sc.nextLine();

				switch(msg) {
					case "b": {
						if( this.ligneActive != 0 )
							--this.ligneActive; // on recule d'une ligne si possible
						break;
					}
					case "" : ++this.ligneActive; // on avance d'une ligne
				}
				
				//sc.close(); // fermeture du Scanner
			} catch (Exception e) {
				e.printStackTrace();
			}
		}while (this.executionActive && this.ligneActive < this.lignesFichier.size() );
	}


	public void add(String nom, String type, String valeur) { this.donnees.add(nom, type, valeur); }


    public void add(String nom, String type) { this.donnees.add(nom, type); }
}
