package AlgoPars.Metier.Types;

public class Typable<T>
{
	protected String  nom;
	protected boolean modifiable;
	protected T       valeur;
	protected String  type ;

	/**
	 * Constructeur de la classe Typable
	 * @param nom
	 * @param modifiable
	 * @param valeur
	 */
	public Typable(String nom, boolean modifiable, T valeur) {
		this.nom        = nom;
		this.modifiable = modifiable;
		this.valeur     = valeur;
		this.type       = this.getClass().getSimpleName();
	}


	/**
	 * Méthode renvoyant le nom du Typable courant
	 * @return
	 */
	public String getNom() { return this.nom; }


	/**
	 * Méthode renvoyant la valeur du Typable courant, quel qu'en soit le type
	 * @return T
	 */
	public T getValeur() { return this.valeur; }

	/**
	 * Méthode qui renvoie le nom du types courant quel que soit le type 
	 * @return
	 */
	public String getTypes()
	{
		return this.type ;
	}
	
	/**
	 * Méthode qui change la valeur du Typable courant
	 * @param v
	 */
	public void setValeur(T v) {
		if( this.modifiable )
			this.valeur = v;
		else
			throw new RuntimeException("La valeur d'une constante n'est pas modifiable");
	}


	/**
	 * Méthode renvoyant la valeur du Typable courant sous forme de String
	 */
	public String toString() { return valeur.toString(); }
}
