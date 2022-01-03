package AlgoPars.Metier.Types;

public class Entier extends Typable<Integer> 
{
	private int valeur;

	public Entier(String nom , boolean modifiable, int valeur)
	{
		super(nom, modifiable);

		this.valeur=valeur ;
	}

	public int getValeur() {
		return this.valeur;
	}

	public void setValeur(int v) {
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