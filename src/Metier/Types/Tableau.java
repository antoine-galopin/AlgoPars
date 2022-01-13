package AlgoPars.Metier.Types;

import java.util.Collection;
import java.util.Iterator;
import java.util.ArrayList;

import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;


/**
 * class qui correspond au tableau du pseudo-code 
 * 
 * elle est plus complexe que les autre class car :
 * -c'est une classe générique 
 * -c'est un typable
 * -c'est une collection de typable
 * 
 * grace a ca on peut faire des tableaux de tableaux de caracteres par exemple
 * 
 */
public class Tableau<T extends Typable> // C'est un generique de typable
extends Typable<Collection<T>> // C'est un typable
implements Collection<T> // C'est une collection
{
    /**
     * TAILLE du tableau qui ne doit pas varier et doit retourner une erreur si on la dépasse
     * --> par encore implementer
     */
    private final int TAILLE;

    public Tableau(String nom, boolean modifiable, Collection<T> valeur) {
        super(nom, modifiable, valeur);
        this.TAILLE = valeur.size();
    }

    /**
     * on fait passer Tableau pour une collection pour faire des tableaux de tableaux
     *
     */
    public boolean add(T e) {
        return valeur.add(e);
    }

    /**
     * -->IDEM
     */
    public boolean addAll(Collection<? extends T> c) {
        return valeur.addAll(c);
    }

    /**
     * -->IDEM
     */
    public void clear() {
        valeur.clear();
    }

    /**
     * -->IDEM
     */
    public boolean contains(Object o) {
        return valeur.contains(o);
    }

    /**
     * -->IDEM
     */
    public boolean containsAll(Collection<?> c) {
        return valeur.contains(c);
    }

    /**
     * -->IDEM
     */
    public boolean equals(Object o) {
        return valeur.equals(o);
    }

    /**
     * -->IDEM
     */
    public int hashCode() {
        return valeur.hashCode();
    }

    /**
     * -->IDEM
     */
    public boolean isEmpty() {
        return valeur.isEmpty();
    }

    /**
     * -->IDEM
     */
    public Iterator<T> iterator() {
        return valeur.iterator();
    }

    /**
     * -->IDEM
     */
    public boolean remove(Object o) {
        return valeur.remove(o);
    }

    /**
     * -->IDEM
     */
    public boolean removeAll(Collection<?> c) {
        return valeur.removeAll(c);
    }

    /**
     * -->IDEM
     */
    public boolean retainAll(Collection<?> c) {
        return valeur.retainAll(c);
    }

    /**
     * -->IDEM
     */
    public int size() {
        return valeur.size();
    }

    /**
     * -->IDEM
     */
    public Object[] toArray() {
        return valeur.toArray();
    }

    /**
     * -->IDEM
     */
    public <T> T[] toArray(T[] a) {
        return valeur.toArray(a);
    }

    /**
     * Methode qui surcharge le toString originel pour faire un affichage plus agréable
     * @return String toString de valeur réarrangé
     */
    @Override
    public String toString() {
        String s = "{ ";

        for (T i : valeur)
            s += i.toString() + ", ";

        s += "}";

        return s.replaceAll(", \\}", " }").replaceAll("\\}, ", "},\n  ");
    }

    /**
     * copie le tableau actuel vers le presse papier
     * --> Pas encore implémenter mais tres simples d'utilisation
     */
    public void versPressePapier() {
        StringSelection ss = new StringSelection(this.toString());

        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(ss, null);
    }

    /*--------------Simplification des instanciation-------------*/

    /**
     * simplifie le constructeur de base car on devait instancier une collection
     * @return Tableau de Typable peut importe le type plus simplement un tableau a 1 dimension
     */
    public static Tableau<Typable> new_Tableau1D(String nom, boolean modifiable, Typable[] T1) {
        ArrayList<Typable> list1 = new ArrayList<Typable>();

        for (Typable t : T1) {
            list1.add(t);
        }

        return new Tableau<Typable>(nom, modifiable, list1);
    }

    /**
     * simplifie le constructeur de base car on devait instancier une collections de collections
     * @return Tableau de Tableau de Typable peut importe le type ou plus simplement un tableau a 2 dimension
     */
    public static Tableau<Tableau<Typable>> new_Tableau2D(String nom, boolean modifiable, Typable[][] T2) {
        ArrayList<Tableau<Typable>> list2 = new ArrayList<Tableau<Typable>>();

        int cpt = 0;

        for (Typable[] t1 : T2) {
            list2.add(new_Tableau1D(nom + cpt, modifiable, t1));
            cpt++;
        }

        return new Tableau<Tableau<Typable>>(nom, modifiable, list2);
    }

    /**
     * simplifie le constructeur de base car on devait instancier une collections de collections de collections
     * @return Tableau de Tableau de Tableau de Typable peut importe le type ou plus simplement un tableau a 3 dimension
     */
    public static Tableau<Tableau<Tableau<Typable>>> new_Tableau3D(String nom, boolean modifiable, Typable[][][] T3) {
        ArrayList<Tableau<Tableau<Typable>>> list3 = new ArrayList<Tableau<Tableau<Typable>>>();

        int cpt = 0;

        for (Typable[][] t2 : T3) {
            list3.add(new_Tableau2D(nom + cpt, modifiable, t2));
            cpt++;
        }

        return new Tableau<Tableau<Tableau<Typable>>>(nom, modifiable, list3);
    }


    /**
     * @deprecated cette methode ne fait pas de verification de type pour sa valeur attention au cast
     * @return un tableau<?> qui peut etre de 1 a 3 dimension 
     */
    public static Tableau new_TableauXD(String nom, boolean modifiable, int dimension) {
        switch (dimension) {
            case 1:
                return (Tableau) new_Tableau1D(nom, modifiable, new Typable[] {});
            case 2:
                return (Tableau) new_Tableau2D(nom, modifiable, new Typable[][] {});
            case 3:
                return (Tableau) new_Tableau3D(nom, modifiable, new Typable[][][] {});
        }

        return null;
    }
}