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

	private boolean executionActive;
	private boolean bConstante;
	private boolean bVariable;
	private boolean bSi;
	private boolean bSinon;
	private ArrayList<Boolean> alSi;
	private int nombreSi;
	private int siImbrique;
	private int sinonImbrique;

	private String nom;

	/**
	 * Constructeur de la classe Programme
	 * 
	 * @param ctrl
	 * @param cheminFichier
	 */
	public Programme(AlgoPars ctrl, String cheminFichier) {
		// Important car cela permet de charger le fichier XML des couleurs.
		ColorationSyntaxique.chargerCouleurs();

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
		this.sinonImbrique = 0;

		this.listeBreakPoints = new ArrayList<Integer>();

		this.bConstante = false;
		this.bVariable = false;
		this.bSi = true;
		this.bSinon = true;

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

	public void setBSi(boolean bsi) {
		this.bSi = bsi;
	}

	public void setNbSi(int nbSi) {
		this.nombreSi = nbSi;
	}

	public void setBSinon(boolean bSinon) {
		this.bSinon = bSinon;
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
		Typable var = this.donnees.rechercheParNom(nom);
		if (var != null) {
			// if ((var instanceof Booleen) || (var instanceof Reel) || (var instanceof
			// Tableau))
			return var.getValeur();
			/*
			 * else
			 * return var.getValeur().toString();
			 */
		}
		return null;
	}

	public String getString(String nom) {
		Typable var = this.donnees.rechercheParNom(nom);
		if (var != null) {
			// if ((var instanceof Booleen) || (var instanceof Reel) || (var instanceof
			// Tableau))
			return var.toString();
			/*
			 * else
			 * return var.getValeur().toString();
			 */

		}
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

				// Méthode : "L" + numLigne + Entrée ( aller à la ligne numLigne )
				if (msg.matches("^L\\d+")) {
					int ecart = this.ligneActive - Integer.parseInt(msg.substring(1));

					int i = Math.abs(ecart);

					int x = i == 0 ? 0 : ecart / i;

					for (int cpt = 0; cpt < i; cpt++) {
						this.ligneActive = this.ligneActive - x;

						if ( this.ligneActive == this.lignesFichier.size() ) break;

						if (x < 0)
							this.listeInstructions.get(this.ligneActive).interpreterLigne();
					}
				} else

				// Méthode : "+ bk" + numLigne ( ajout d'un point d'arrêt )
				if (msg.matches("^\\+ bk \\d+")) {
					int val = Integer.parseInt(msg.substring(5));

					if (!this.listeBreakPoints.contains(val) && val < this.lignesFichier.size())
						this.listeBreakPoints.add(val);

					this.listeBreakPoints.sort(null); // tri
				} else

				// Méthode : "- bk" + numLigne ( suppression d'un point d'arrêt )
				if (msg.matches("^\\- bk \\d+")) {
					int indice = this.listeBreakPoints.indexOf(Integer.parseInt(msg.substring(5)));
					this.listeBreakPoints.remove(indice);
				} else

				// Méthode : "go bk" ( déplacement jusqu'au prochain point d'arrêt )
				if (msg.equals("go bk")) {
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
			} 
			catch ( NoSuchElementException e ) { sc.close(); }
			catch (Exception e ) { e.printStackTrace();	}
		} while (this.executionActive && this.ligneActive < this.lignesFichier.size());
	}

	public void add(String nom, String type, String valeur) {
		this.donnees.add(nom, type, valeur);
	}

	public void add(String nom, String type) {
		this.donnees.add(nom, type);
	}

	/*
	 * public Typable getTypable(String s) {
	 * // si on donne le nom
	 * if (this.donnees.rechercheParNom(s) != null)
	 * return this.donnees.rechercheParNom(s);
	 * 
	 * // si on donne la valeur
	 * switch (Calculateur.getType(s)) {
	 * case "booleen":
	 * return new Booleen("@b", false, s.equals("vrai") ? true : false);
	 * case "caractere":
	 * return new Caractere("@c", false, s.charAt(1));
	 * case "chaine":
	 * return new Chaine("@ch", false, s);
	 * case "entier":
	 * return new Entier("@e", false, Integer.parseInt(s));
	 * case "reel":
	 * return new Reel("@r", false, Double.parseDouble(s.replaceAll(",", ".")));
	 * 
	 * // case "tableau" : { this.donnees.add(new Reel (nom, true , 0.0 )); break; }
	 * default:
	 * throw new RuntimeException("La valeur :" + s + " n'a pas été trouvé");
	 * }
	 * }
	 */
}

/*
 * System.out.println(this.ctrl.getNbSi() + " getNbSI");
 * System.out.println(this.ctrl.getAlSi() + " getal");
 * System.out.println(this.ligneActive + " nb Ligne");
 * System.out.println(this.siImbrique + " siImbrique avant");
 * if (this.alSi != null && this.alSi.get(nombreSi) == Boolean.FALSE /* /
 * this.bSi == false ) {
 * System.out.println(this.siImbrique + " siImbrique");
 * if (this.siImbrique == -1) {
 * this.bSi = true;
 * this.siImbrique++;
 * // this.nombreSi++;
 * System.out.println(this.ctrl.getNbSi() + " getNbSI si faux");
 * System.out.println(this.listeInstructions.get(++this.ligneActive));
 * this.listeInstructions.get(++this.ligneActive).interpreterLigne();
 * 
 * } else {
 * while (this.siImbrique != -1)
 * if ((this.siImbrique += this.listeInstructions.get(++this.ligneActive)
 * .interpreterLigne(this.siImbrique)) == -1) {
 * --this.ligneActive;
 * }
 * }
 * } else
 * 
 * if (/ this.bSinon == false /this.alSi != null && this.alSi.get(nombreSi) ==
 * Boolean.TRUE
 * && this.listeInstructions.get(this.ligneActive).getInstruction() == "sinon")
 * {
 * System.out.println("je suis la");
 * if (this.sinonImbrique == -1) {
 * this.bSinon = true;
 * this.sinonImbrique = 0;
 * this.listeInstructions.get(++this.ligneActive).interpreterLigne();
 * 
 * } else {
 * System.out.println("je suis la2");
 * while (this.sinonImbrique != -1) {
 * System.out.println("je suis la2.2 " + this.sinonImbrique);
 * 
 * if ((this.sinonImbrique += this.listeInstructions.get(++this.ligneActive)
 * .interpreterLigne(this.sinonImbrique)) == -1) {
 * --this.ligneActive;
 * System.out.println("je suis la3 " + this.sinonImbrique);
 * }
 * }
 * }
 * }
 */

