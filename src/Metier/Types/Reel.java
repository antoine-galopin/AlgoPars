package AlgoPars.Metier.Types;

/**
 * class qui correspond au reel du pseudo code 
 * comme tout les typables elle se refere a son type de base pour implementer typable
 *
 */
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
	 * @return Valeur du booléen sous une forme adaptée au pseudo-code
	 */
	public String toString()
	{
		return valeur.toString().replace(".", ",");
	}
}
