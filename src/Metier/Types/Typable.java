package AlgoPars.Metier.Types;

public class Typable
{
	protected String  nom;
	protected boolean modifiable;

	public Typable(String nom ,boolean modifiable)
	{
		this.nom = nom ;
		this.modifiable = modifiable ;
	}

	public String getNom() {
		return this.nom;
	}
}