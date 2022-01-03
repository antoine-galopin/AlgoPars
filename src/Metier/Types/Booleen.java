package AlgoPars.Metier.Types;

public class Booleen extends Typable<Boolean>
{
	public Booleen(String nom, boolean modifiable, boolean valeur)
	{
		super(nom, modifiable, valeur);
	}

	public String toString()
	{
		return (valeur)?"vrai":"faux" ;
	}
}
