package AlgoPars.Metier.Types;

public abstract class Typable<T> {
	protected String  nom;
	protected boolean modifiable;
	protected T       valeur;

	public String getNom() {
		return this.nom;
	}

	public T getValeur() {
		return this.valeur;
	}

	public void setValeur(T v) {
		if( this.modifiable )
			this.valeur = v;
		else
		throw new RuntimeException("La valeur d'une constante n'est pas modifiable");
	}
}