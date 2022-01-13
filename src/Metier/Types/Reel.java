package AlgoPars.Metier.Types;

public class Reel extends Typable<Double>
{
	/**
	 * Constructeur de la classe Reel
	 * @param nom
	 * @param modifiable true -> variable, false -> constante
	 * @param valeur
	 */
	public Reel(String nom, boolean modifiable, double valeur)
	{
		super(nom, modifiable, valeur);
	}

	/**
	 * toString de la classe Booleen
	 * @return Valeur du booléen sous une forme adaotée au pseudo-code
	 */
	public String toString()
	{
		return valeur.toString().replace(".", ",");
	}
}
