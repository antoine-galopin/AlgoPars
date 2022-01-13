package AlgoPars.Metier.Types;

public class Chaine extends Typable<String>
{
	/**
	 * Constructeur de la classe Chaine
	 * @param nom
	 * @param modifiable true -> variable, false -> constante
	 * @param valeur
	 */
	public Chaine(String nom, boolean modifiable, String valeur) {
		super(nom, modifiable, valeur);
	}

	/**
	 * toString de la classe Booleen
	 * @return Valeur du booléen sous une forme adaotée au pseudo-code
	 */
	public String toString() {
		return "\"" + super.toString() + "\"";
	}
}
