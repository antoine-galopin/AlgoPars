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
	private ArrayList<String> listeVarSuivies;
	private ArrayList<Instruction> listeInstructions;

	private boolean executionActive;
	private boolean bConstante;
	private boolean bVariable;

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
		this.listeVarSuivies = new ArrayList<String>();
		this.listeInstructions = new ArrayList<Instruction>();

		this.bConstante = false;
		this.bVariable = false;

		try {
			// Lecture du programme.
			Scanner sc = new Scanner(new FileInputStream( "../utilisateur/" + cheminFichier + ".algo" ), "UTF-8");

			String ligne = "";
			while (sc.hasNextLine()) {
				ligne = sc.nextLine().replace("\t", "    ");
				this.lignesFichier.add(ligne);
				this.lignesFichierColorie.add(ColorationSyntaxique.colorierLigne(ligne));
				this.listeInstructions.add(new Instruction(this.ctrl, this.primitives, ligne));
			}

			sc.close();

			// Lecture du fichier contenant les variables à suivre.
			sc = new Scanner(new FileInputStream("../utilisateur/" + cheminFichier + ".var"), "UTF-8");

			while (sc.hasNextLine())
				this.listeVarSuivies.add(sc.next().strip());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean getBConstante() {
		return this.bConstante;
	}

	public boolean getBVariable() {
		return this.bVariable;
	}

	public void setBConstante(boolean bConstante) {
		this.bConstante = bConstante;
	}

	public void setBVariable(boolean bVariable) {
		this.bVariable = bVariable;
	}

	public int getLigneActive() {
		return this.ligneActive;
	}

	public ArrayList<String> getLignesFichier() {
		return this.lignesFichier;
	}

	public ArrayList<String> getLignesFichierColorie() {
		return this.lignesFichierColorie;
	}

	public ArrayList<String> getVariablesSuivies() {
		return this.listeVarSuivies;
	}

	public String getValeur(String nom) {
		if (this.donnees.rechercheParNom(nom) != null)
			return this.donnees.rechercheParNom(nom).getValeur().toString();
		return null;
	}

	public void affecterValeur(String nom, String valeur) {
		this.donnees.affecterValeur(nom, valeur);
	}

	/**
	 * Exécution de l'algorithme.
	 */
	public void executerAlgo() {
		do {
			//this.listeInstructions.get(this.ligneActive).interpreterLigne();
			this.ctrl.afficher();

			try {
				Scanner sc = new Scanner(System.in); // ouverture du Scanner

				String msg = sc.nextLine();

				if( msg.matches("^L\\d+") ) {
					int ecart = this.ligneActive - Integer.parseInt(msg.substring(1));

					int i = Math.abs(ecart);

					int x = ecart / i;

					for( int cpt = 0; cpt < i; cpt++ ) {
						this.ligneActive = this.ligneActive - x;
						
						if( x < 0 ) this.listeInstructions.get(this.ligneActive).interpreterLigne();
					}
				}

				switch (msg) {
					case "b": {
						if( this.ligneActive != 0 )
							--this.ligneActive; // on recule d'une ligne si possible
						break;
					}
					case "": {
						++this.ligneActive; // on avance d'une ligne
						this.listeInstructions.get(this.ligneActive).interpreterLigne();
					}
				}

				// sc.close(); // fermeture du Scanner
			} catch (Exception e) {
				e.printStackTrace();
			}
		} while (this.executionActive && this.ligneActive < this.lignesFichier.size());
	}

	public void add(String nom, String type, String valeur) {
		this.donnees.add(nom, type, valeur);
	}

	public void add(String nom, String type) {
		this.donnees.add(nom, type);
	}
}
