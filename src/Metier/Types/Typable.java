package AlgoPars.Metier.Types;

public class Typable<T> {
	protected String nom;
	protected boolean modifiable;
	protected String type;
	protected T valeur;

	/**
	 * Constructeur de la classe Typable
	 * 
	 * @param nom
	 * @param modifiable true -> variable, false -> constante
	 * @param valeur
	 */
	public Typable(String nom, boolean modifiable, T valeur) {
		this.nom = nom;
		this.modifiable = modifiable;
		this.type = this.getClass().getSimpleName();
		this.valeur = valeur;
	}

	/**
	 * Accesseur du nom du Typable courant
	 * 
	 * @return Nom de la donnée sous forme de String
	 */
	public String getNom() {
		return this.nom;
	}

	/**
	 * Méthode renvoyant la valeur du Typable courant, quel qu'en soit le type
	 * 
	 * @return Valeur de la donnée sous forme de String
	 */
	public String getValeur() {
		return String.valueOf(this.valeur);
	}

	/**
	 * Accesseur du nom du type courant quel que soit le type
	 * 
	 * @return String
	 */
	public String getType() {
		return this.type;
	}

	/**
	 * Méthode qui change la valeur du Typable courant
	 * 
	 * @param valeur
	 */
	public void setValeur(T valeur) {
		if (!this.modifiable)
			throw new RuntimeException("La valeur d'une constante n'est pas modifiable");

		this.valeur = valeur;
	}

	/**
	 * toString de la classe Typable
	 * @return Valeur du Typable courant sous forme de String
	 */
	public String toString() {
		return valeur.toString();
	}
}
