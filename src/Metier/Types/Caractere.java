package AlgoPars.Metier.Types;

public class Caractere extends Typable<Character> 
{
	private char valeur;

	public Entier(String nom , boolean modifiable, char valeur)
	{
		super(nom, modifiable);

		this.valeur=valeur ;
	}

	public char getValeur() {
		return this.valeur;
	}

	public void setValeur(char v) {
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