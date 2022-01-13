package AlgoPars.Metier;

import AlgoPars.AlgoPars;
import AlgoPars.Metier.Types.*;

import java.util.ArrayList;
import java.util.NoSuchElementException;
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

	private ArrayList<Integer> listeBreakPoints;
	private ArrayList<Boolean> alSi;
	private ArrayList<Integer> alTq;

	private boolean executionActive;
	private boolean commMultiLignes;
	private boolean bConstante;
	private boolean bVariable;
	private boolean bSi;

	private int nombreSi;
	private int siImbrique;
	private int nombreTq;
	private int tqImbrique;
	private String nom;

	/**
	 * Constructeur de la classe Programme
	 * 
	 * @param ctrl
	 * @param cheminFichier
	 */
	public Programme(AlgoPars ctrl, String cheminFichier) {
		// Important car cela permet de charger le fichier XML des couleurs.
		ColorationSyntaxique.chargerCouleursXML();

		this.ctrl = ctrl;
		this.primitives = new Primitives(this.ctrl);

		this.lignesFichier = new ArrayList<String>();
		this.lignesFichierColorie = new ArrayList<String>();
		this.ligneActive = 0;
		this.executionActive = true;

		this.donnees = new Donnee();
		this.listeVarSuivies = new ArrayList<String>();
		this.listeInstructions = new ArrayList<Instruction>();

		this.alSi = null;
		this.nombreSi = -1;
		this.siImbrique = 0;
		this.bSi = true;

		this.listeBreakPoints = new ArrayList<Integer>();

		this.commMultiLignes = false;
		this.bConstante = false;
		this.bVariable = false;

		this.nom = cheminFichier;// par defaut

		try {
			// Lecture du programme.
			Scanner sc = new Scanner(new FileInputStream("../utilisateur/" + cheminFichier + ".algo"), "UTF-8");

			String ligne = "";
			while (sc.hasNextLine()) {
				ligne = sc.nextLine().replace("\t", "    ");
				this.lignesFichier.add(ligne);
				this.lignesFichierColorie.add(ColorationSyntaxique.colorierLigne(ligne, true));
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

	/**
	 * Accesseur de BConstante
	 * 
	 * @return boolean
	 */
	public boolean getBConstante() {
		return this.bConstante;
	}

	/**
	 * Accesseur de BVariable
	 * 
	 * @return boolean
	 */
	public boolean getBVariable() {
		return this.bVariable;
	}

	public boolean getBSi() {
		return this.bSi;
	}

	/**
	 * Fonction changeant la valeur de BConstante
	 * 
	 * @param bConstante
	 */
	public void setBConstante(boolean bConstante) {
		this.bConstante = bConstante;
	}

	/**
	 * Fonction changeant la valeur de BConstante
	 * 
	 * @param bVariable
	 */
	public void setBVariable(boolean bVariable) {
		this.bVariable = bVariable;
	}

	/**
	 * Accesseur de AkSi
	 * 
	 * @return ArrayList<Boolean>
	 */
	public ArrayList<Boolean> getAlSi() {
		return this.alSi;
	}

	/**
	 * Fonction changeant la valeur de alSi
	 * 
	 * @param alSi
	 */
	public void setAlSi(ArrayList<Boolean> alSi) {
		this.alSi = alSi;
	}

	public void addValAlSi(Boolean val) {
		this.alSi.add(val);
	}

	public int getLigneActive() {
		return this.ligneActive;
	}

	/**
	 * Accesseur de lignesFichier
	 * 
	 * @return ArrayList<String>
	 */
	public ArrayList<String> getLignesFichier() {
		return this.lignesFichier;
	}

	/**
	 * Accesseur de lignesFichierColorie
	 * 
	 * @return ArrayList<String>
	 */
	public ArrayList<String> getLignesFichierColorie() {
		return this.lignesFichierColorie;
	}

	/**
	 * Accesseur de listeVarSuivies
	 * 
	 * @return ArrayList<String>
	 */
	public ArrayList<String> getVariablesSuivies() {
		return this.listeVarSuivies;
	}

	/**
	 * Accesseur de listeBreakPoints
	 * 
	 * @return ArrayList<Integer>
	 */
	public ArrayList<Integer> getListeBreakPoints() {
		return this.listeBreakPoints;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public void setBSi(boolean bSi) {
		this.bSi = bSi;
	}

	public void setNbSi(int nbSi) {
		this.nombreSi = nbSi;
	}

	public void setSiImbrique(int nbSi) {
		this.siImbrique = nbSi;
	}

	public int getNbSi() {
		return this.nombreSi;
	}

	/**
	 * Accesseur de valeur
	 * 
	 * @param nom
	 * @return String
	 */
	public String getValeur(String nom) {
		if( this.donnees.rechercheParNom(nom) == null ) return null;

		Typable var = this.donnees.rechercheParNom(nom);

		if( var.getValeur() != "true" && var.getValeur() != "false" ) return var.getValeur();

		return var.getValeur() == "true" ? "vrai" : "faux";
	}

	public String getString(String nom) {
		Typable var = this.donnees.rechercheParNom(nom);
		
		if (var != null) return var.toString();

		return null;
	}

	/**
	 * Fonction changeant la valeur de valeur
	 * 
	 * @param nom
	 * @param valeur
	 */
	public void affecterValeur(String nom, String valeur) {
		this.donnees.affecterValeur(nom, valeur);
	}

	/**
	 * Exécution de l'algorithme.
	 */
	public void executerAlgo() {
		Scanner sc = null;
		do {
			this.ctrl.afficher();

			try {
				sc = new Scanner(System.in); // ouverture du Scanner

				String msg = sc.nextLine();

				if ( this.lignesFichier.get( this.ligneActive ).contains( "/*" ) )
				{
					if ( !this.lignesFichier.get( this.ligneActive ).contains( "*/" ) )
						commMultiLignes = true;
				}

				if ( commMultiLignes )
				{
					while ( !this.lignesFichier.get( this.ligneActive + 1 ).contains( "*/" ) )
						this.ligneActive++;
					commMultiLignes = false;
					this.listeInstructions.get( this.ligneActive ).interpreterLigne();
				}

				else if (this.alSi != null) {
					if (!this.bSi || (!this.alSi.get(this.nombreSi)
							&& this.listeInstructions.get(this.ligneActive).getInstruction().equals("si"))) {
						this.siImbrique = 0;
						while (!this.bSi || (this.siImbrique > -1 && !this.alSi.get(this.nombreSi))) {
							this.siImbrique += this.listeInstructions.get(++this.ligneActive)
									.interpreterLigne(this.siImbrique);
						}
						if (this.nombreSi == -1) {
							this.nombreSi++;
							--ligneActive;
						} else if (this.siImbrique == -1) {
							this.nombreSi++;
							--ligneActive;
						}
					} else if ((this.alSi.get(this.nombreSi)
							&& this.listeInstructions.get(this.ligneActive).getInstruction().equals("sinon"))
							&& this.bSi) {
						this.siImbrique = 0;
						while (this.siImbrique > -1 && this.alSi.get(this.nombreSi)) {
							this.siImbrique += this.listeInstructions.get(++this.ligneActive)
									.interpreterLigne(this.siImbrique);
						}
						if (this.nombreSi == -1) {
							this.nombreSi++;
							--ligneActive;
						} else if (this.siImbrique == -1) {
							this.nombreSi++;
							--ligneActive;
						}

					}
				}
				// Méthode : "L" + numLigne + Entrée ( aller à la ligne numLigne )
				else if (msg.matches("^L\\d+")) {
					int ecart = this.ligneActive - Integer.parseInt(msg.substring(1));

					int i = Math.abs(ecart);

					int x = i == 0 ? 0 : ecart / i;

					for (int cpt = 0; cpt < i; cpt++) {
						this.ligneActive = this.ligneActive - x;

						if (this.ligneActive == this.lignesFichier.size())
							break;

						if (x < 0)
							this.listeInstructions.get(this.ligneActive).interpreterLigne();
					}
				}
				// Méthode : "+ bk" + numLigne ( ajout d'un point d'arrêt )
				else if (msg.matches("^\\+ bk \\d+")) {
					int val = Integer.parseInt(msg.substring(5));

					if (!this.listeBreakPoints.contains(val) && val < this.lignesFichier.size())
						this.listeBreakPoints.add(val);

					this.listeBreakPoints.sort(null); // tri
				}
				// Méthode : "- bk" + numLigne ( suppression d'un point d'arrêt )
				else if (msg.matches("^\\- bk \\d+")) {
					int indice = this.listeBreakPoints.indexOf(Integer.parseInt(msg.substring(5)));
					this.listeBreakPoints.remove(indice);
				}
				// Méthode : "go bk" ( déplacement jusqu'au prochain point d'arrêt )
				else if (msg.equals("go bk")) {
					int prochainBK = this.lignesFichier.size() - 1;

					for (int i : this.listeBreakPoints)
						if (i > this.ligneActive) {
							prochainBK = i;
							break;
						}

					if (prochainBK == this.lignesFichier.size() - 1)
						System.out.println(
								"Pas de point d'arrêt trouvé après votre position. Vous êtes envoyés au bout du programme");

					for (int cpt = this.ligneActive; cpt < prochainBK; cpt++)
						this.listeInstructions.get(++this.ligneActive).interpreterLigne();
				}

				switch (msg) {
					case "b": { // Méthode : "b" + Entrée ( reculer d'une ligne )
						this.ligneActive = this.ligneActive == 0 ? 0 : this.ligneActive - 1;
						break;
					}
					case "": { // Méthode : Entrée ( avancer d'une ligne )
						if (++this.ligneActive < this.listeInstructions.size())
							this.listeInstructions.get(this.ligneActive).interpreterLigne();
					}
				}

				// sc.close(); // fermeture du Scanner
			} catch (NoSuchElementException e) {
				sc.close();
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
