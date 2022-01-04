package AlgoPars.Metier;

import AlgoPars.AlgoPars;
import AlgoPars.Metier.Donnee;
import AlgoPars.Metier.Instruction;
import AlgoPars.Metier.Primitives;

import java.util.ArrayList;
import java.util.Scanner;

import java.io.FileInputStream;

public class Programme {
	private AlgoPars ctrl;
	private Primitives primitives;

	private int ligneActive;
	private ArrayList<String> lignesFichier;

	private Donnee donnees;
	private ArrayList<Instruction> listeInstructions;

	private boolean executionActive;


	public Programme(AlgoPars ctrl, String cheminFichier) {
		this.ctrl = ctrl;
		this.primitives = new Primitives(this.ctrl, this);

		this.lignesFichier = new ArrayList<String>();
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
				this.listeInstructions.add(new Instruction(this.primitives, ligne));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	public int getLigneActive() {
		return this.ligneActive;
	}

	public ArrayList<String> getLignesFichier() {
		return this.lignesFichier;
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
}
