package AlgoPars.Metier.Types;

public class Reel {
    private double valeur;

    public double getValeur() {
        return this.valeur;
    }

    public void setValeur(double v) {
        if( this.modifiable )
            this.valeur = v;
        else
            throw new RuntimeException("La valeur d'une constante n'est pas modifiable");
    }
}