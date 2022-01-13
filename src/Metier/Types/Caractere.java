package AlgoPars.Metier.Types;

public class Caractere extends Typable<Character>
{
	/**
	 * Constructeur de la classe Caractere
	 * @param nom
	 * @param modifiable true -> variable, false -> constante
	 * @param valeur
	 */
	public Caractere(String nom, boolean modifiable, char valeur)
	{
		super(nom, modifiable, valeur);
	}

	/**
	 * toString de la classe Booleen
	 * @return Valeur du booléen sous une forme adaotée au pseudo-code
	 */
	public String toString()
	{
		return "'" + super.toString() + "'";
	}
}
