package AlgoPars.Metier.Types;

public class Entier extends Typable<Integer>
{
	/**
	 * Constructeur de la classe Entier
	 * @param nom
	 * @param modifiable true -> variable, false -> constante
	 * @param valeur
	 */
	public Entier(String nom, boolean modifiable, int valeur)
	{
		super(nom, modifiable, valeur);
	}
}
