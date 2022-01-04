package AlgoPars.Metier.Types;

public class Reel extends Typable<Double>
{
	public Reel(String nom, boolean modifiable, double valeur)
	{
		super(nom, modifiable, valeur);
	}

	public String toString()
	{
		return valeur.toString().replace(".", ",");
	}
}
