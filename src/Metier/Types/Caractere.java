package AlgoPars.Metier.Types;

public class Caractere extends Typable<Character> {
	public Caractere(String nom, boolean modifiable, char valeur) {
		super(nom, modifiable, valeur);
	}

	public String toString() {
		return "'" + super.toString() + "'";
	}
}
