package AlgoPars.Metier.Types;

public class Booleen extends Typable<Boolean> 
{
	private boolean valeur;

	public Entier(String nom , boolean modifiable, boolean valeur)
	{
		super(nom, modifiable);

		this.valeur=valeur ;
	}

	public boolean getValeur() {
		return this.valeur;
	}

	public void setValeur(boolean v) {
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