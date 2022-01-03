package AlgoPars.Metier.Types;

public class Chaine extends Typable<String> 
{
	private String valeur;

	public Entier(String nom , boolean modifiable, String valeur)
	{
		super(nom, modifiable);

		this.valeur=valeur ;
	}

	public String getValeur() {
		return this.valeur;
	}

	public void setValeur(String v) {
		if( this.modifiable )
			this.valeur = v;
		else
		{//si une valeur n'a pas été encore attribué
			if(this.valeur == null)
				this.valeur = v;
			else
				throw new RuntimeException("La valeur d'une constante n'est pas modifiable");
		}
	}
}