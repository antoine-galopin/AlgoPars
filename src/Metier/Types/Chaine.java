package AlgoPars.Metier.Types;

public class Chaine extends Typable<String> {
	public Chaine(String nom, boolean modifiable, String valeur) {
		super(nom, modifiable, valeur);
	}

	public String toString() {
		return "\"" + super.toString() + "\"";
	}
}
