package AlgoPars.Metier;

import AlgoPars.AlgoPars;
import AlgoPars.Metier.Types.*;


import java.util.ArrayList;
import java.util.Scanner;

import javax.lang.model.util.ElementScanner6;

import java.io.FileInputStream;

import java.lang.reflect.Method ;

public class Programme {
	private AlgoPars ctrl;
	private Primitives primitives;

	private int ligneActive;
	private ArrayList<String> lignesFichier;
	private ArrayList<String> lignesFichierColorie;

	private Donnee donnees;
	private ArrayList<String> listeVarSuivies;
	private ArrayList<Instruction> listeInstructions;

	private ArrayList<Integer> listeBreakPoints;

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

		this.listeBreakPoints = new ArrayList<Integer>();

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

/*test 
		System.out.println(executerFonction("aujourdhui",null));
		System.out.println(executerFonction("ord",new Typable[]{new Caractere("i",true,'b')}));
*/
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

	public ArrayList<Integer> getListeBreakPoints() {
		return this.listeBreakPoints;
	}

	public String getValeur(String nom) {
		Typable var = this.donnees.rechercheParNom( nom );
		if (var != null)
		{
			if ( (var instanceof Booleen) || (var instanceof Reel) || (var instanceof Tableau) )
				return var.toString();
			else return var.getValeur().toString();
		}
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


				// Méthode : "L" + numLigne + Entrée ( aller à la ligne numLigne )
				if( msg.matches("^L\\d+") ) {
					int ecart = this.ligneActive - Integer.parseInt(msg.substring(1));

					int i = Math.abs(ecart);

					int x = ecart / i;

					for( int cpt = 0; cpt < i; cpt++ ) {
						this.ligneActive = this.ligneActive - x;
						
						if( x < 0 ) this.listeInstructions.get(this.ligneActive).interpreterLigne();
					}
				} else

				// Méthode : "+ bk" + numLigne ( ajout d'un point d'arrêt )
				if( msg.matches("^\\+ bk \\d+") ) {
					int val = Integer.parseInt(msg.substring(5));

					if( !this.listeBreakPoints.contains(val) && val < this.lignesFichier.size() )
						this.listeBreakPoints.add(val);
					
					this.listeBreakPoints.sort(null); // tri
				} else

				// Méthode : "- bk" + numLigne ( suppression d'un point d'arrêt )
				if( msg.matches("^\\- bk \\d+") ) {
					int indice = this.listeBreakPoints.indexOf(Integer.parseInt(msg.substring(5)));
					this.listeBreakPoints.remove(indice);
				} else

				// Méthode : "go bk" ( déplacement jusqu'au prochain point d'arrêt )
				if( msg.equals("go bk") ) {
					int prochainBK = this.lignesFichier.size() - 1;

					for( int i : this.listeBreakPoints )
						if( i > this.ligneActive ) {
							prochainBK = i;
							break;
						}

					if( prochainBK == this.lignesFichier.size() - 1 )
						System.out.println("Pas de point d'arrêt trouvé après votre position. Vous êtes envoyés au bout du programme");

					for( int cpt = this.ligneActive; cpt < prochainBK; cpt++ )
						this.listeInstructions.get(++this.ligneActive).interpreterLigne();
				}

				switch(msg) {
					case "b": { // Méthode : "b" + Entrée ( reculer d'une ligne )
						this.ligneActive = this.ligneActive == 0 ? 0 : this.ligneActive - 1;
						break;
					}
					case "": { // Méthode : Entrée ( avancer d'une ligne )
						if ( ++this.ligneActive < this.listeInstructions.size() )
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

/*----------------------------------------------------------------------------------*/
	/**
	 * Methode qui renvoie le resultat d'une methode de primitives executer 
	 * 
	 * @param nomFonction nom de la methodes a executer
	 * @param parametre   parametre que la methode prend habituellement
	 * @return Object que la fonction renvoie normalement
	 */
	public Object executerFonction(String nomFonction,Typable[] parametre)
	{

		for(Method m : primitives.listePrimitives)
		{
			if (m.getName().equals(nomFonction)) {
				try{
					return m.invoke(primitives, (Object[])parametre);					
				}catch(Exception e){e.printStackTrace();}
			}
		}

		return null ;
	}
}
