package AlgoPars.Metier.Types;

import java.util.List ;
/**
 * classe generique tableau -> on peut avoir un tableau de tout les typables
 * tableau par heritage est une liste du type donnee
 */
public class Tableau<T extends Typable> extends Typable<T[]>
{

    public Tableau(String nom, boolean modifiable, T[] valeur)
    {
        super(nom, modifiable, valeur);
    }

/*
    public T getValeur(int... index)
    {
        for (int i : index) {
            valeur[i].getValeur();
        }
    }*/

    /*public String toString()
    {
        String s="[";

        for (t : valeur) 
        {
            s+=valeur[i].toString();
        }

        s+="]";

        return s ;
    }*/
}