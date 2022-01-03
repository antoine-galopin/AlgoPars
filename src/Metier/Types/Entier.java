package AlgoPars.Metier.Types;

public class Entier extends Typable {
    private int valeur;

    public int getValeur() {
        return this.valeur;
    }

    public void setValeur(int v) {
        if( this.modifiable )
            this.valeur = v;
        else
            throw new RuntimeException("La valeur d'une constante n'est pas modifiable");
    }
}