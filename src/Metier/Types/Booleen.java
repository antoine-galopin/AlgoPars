package AlgoPars.Metier.Types;

public class Booleen extends Typable<Boolean>
{
	/**
	 * Constructeur de la classe Booleen
	 * @param nom
	 * @param modifiable true -> variable, false -> constante
	 * @param valeur
	 */
	public Booleen(String nom, boolean modifiable, boolean valeur)
	{
		super(nom, modifiable, valeur);
	}

	/**
	 * toString de la classe Booleen
	 * @return Valeur du booléen sous une forme adaotée au pseudo-code
	 */
	public String toString()
	{
		return valeur ? "vrai" : "faux";
	}
}
