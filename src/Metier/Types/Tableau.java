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


    /**
     * Le constructeur du tableau.
     * @param nom Le nom du tableau.
     * @param modifiable indique si le tableau est une constante.
     * @param valeur la valeur du tableau.
     */
    public Tableau(String nom, boolean modifiable, Collection<T> valeur) {
        super(nom, modifiable, valeur);
        this.TAILLE = valeur.size();
    }

    /**
     * On ajoute un élément au tableau.
     * @param e L'élément à ajouté.
     */
    public boolean add(T e) {
        return valeur.add(e);
    }

    /**
     * Ajoute tout les éléments d'une collection dans le tableau.
     * @param c La Collection contenant les éléments à ajoutés.
     */
    public boolean addAll(Collection<? extends T> c) {
        return valeur.addAll(c);
    }

    /**
     * Supprime tout les éléments du tableau.
     */
    public void clear() {
        valeur.clear();
    }

    /**
     * Regarde si le tableau contient un élément.
     * @param o L'objet dont il faut vérifier la présence dans le tableau.
     * @return Un booléen indiquant si l'objet se trouve dans le tableau.
     */
    public boolean contains(Object o) {
        return valeur.contains(o);
    }

    /**
     * Regarde si le tableau contient tout les éléments de la Collection passée en paramètre.
     * @param c La Collection dont il faut comparer le contenu avec le tableau.
     * @return Un booléen indiquant si le tableau contient tout les éléments de la Collection.
     */
    public boolean containsAll(Collection<?> c) {
        return valeur.contains(c);
    }

    /**
     * Compare deux tableaux entre eux.
     * @param o Le tableau à comparer avec le tableau de l'instance.
     * @return Un booléen indiquant si les tableaux sont égaux.
     */
    public boolean equals(Object o) {
        return valeur.equals(o);
    }

    /**
     * Retourne un booléen indiquant si le tableau est vide.
     * @return Le booléen.
     */
    public boolean isEmpty() {
        return valeur.isEmpty();
    }

    /**
     * Retourne l'Iterator du tableau.
     * @return L'objet Iterator du tableau. 
     */
    public Iterator<T> iterator() {
        return valeur.iterator();
    }

    /**
     * supprime un élément du tableau.
     * @return Un booléen indiquant si l'élément a été supprimé.
     */
    public boolean remove(Object o) {
        return valeur.remove(o);
    }

    /**
     * Supprime tout les éléments présent dans la Collection du tableau.
     * @return Un booléen indiquant si l'opération a été réali
     */
    public boolean removeAll(Collection<?> c) {
        return valeur.removeAll(c);
    }

    /**
     * Retourne la taille du tableau.
     * @return Un int contenant la taille du tableau.
     */
    public int size() {
        return valeur.size();
    }

    /**
     * Retourne un tableau contenant les valeurs du tableau.
     * @return Un tableau contenant les valeurs du tableau.
     */
    public Object[] toArray() {
        return valeur.toArray();
    }

    /**
     * Hérité de Collection.
     */
    public boolean retainAll(Collection<?> c) {
        return valeur.retainAll(c);
    }


    /**
     * Met les éléments du tableau dans le tableau passé en paramètre.
     * @param a Le tableau de destination.
     * @return La valeur de retour.
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