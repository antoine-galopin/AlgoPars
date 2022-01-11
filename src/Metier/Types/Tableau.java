package AlgoPars.Metier.Types;

import java.util.Collection ;
import java.util.Iterator ;
import java.util.ArrayList ;

import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;


public class Tableau<T extends Typable>	//c'est un generique de typable
extends Typable<Collection<T>>			//c'est un typable
implements Collection<T>				//c'est une collection
{
	private final int taille;

    public Tableau(String nom, boolean modifiable, Collection<T> valeur)
    {
        super(nom, modifiable, valeur);
        this.taille = valeur.size();
    }

    /*------------Methodes de collection-----------*/

	public boolean 		add(T e)							{return valeur.add(e);		}
	public boolean		addAll(Collection<? extends T> c)	{return valeur.addAll(c);	}
	public void			clear()								{		valeur.clear();		}
	public boolean		contains(Object o)					{return valeur.contains(o);	}
	public boolean		containsAll(Collection<?> c)		{return valeur.contains(c);	}
	public boolean		equals(Object o)					{return valeur.equals(o);	}
	public int			hashCode()							{return valeur.hashCode();	}
	public boolean		isEmpty()							{return valeur.isEmpty();	}
	public Iterator<T>	iterator()							{return valeur.iterator();	}
	public boolean		remove(Object o)					{return valeur.remove(o);	}
	public boolean		removeAll(Collection<?> c)			{return valeur.removeAll(c);}
	public boolean		retainAll(Collection<?> c)			{return valeur.retainAll(c);}
	public int			size()								{return valeur.size();		}
	public Object[]		toArray()							{return valeur.toArray();	}
	public <T> T[]		toArray(T[] a)						{return valeur.toArray(a);	}

    public String toString()
    {
        String s = "{ ";

        for( T i : valeur ) s += i.toString() + ", ";

        s += "}";

		return s.replaceAll(", \\}", " }").replaceAll("\\}, ", "},\n  ");
    }

	public void versPressePapier() {
		StringSelection ss = new StringSelection( this.toString() );

        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(ss, null);
	}

    /*--------------Simplification des instanciation-------------*/

    public static Tableau<Typable> new_Tableau1D(String nom, boolean modifiable, Typable[]    T1)
    {
    	ArrayList<Typable> list1 = new ArrayList<Typable>();

    	for (Typable t : T1) {
    		list1.add(t);
    	}

		return new Tableau<Typable>(nom,modifiable,list1);
	}

    public static Tableau<Tableau<Typable>> new_Tableau2D(String nom, boolean modifiable, Typable[][]  T2)
    {
    	ArrayList<Tableau<Typable>> list2 = new ArrayList<Tableau<Typable>>();

    	int cpt = 0;

    	for( Typable[] t1 : T2 ) 
    	{
    		list2.add( new_Tableau1D( nom + cpt, modifiable, t1 ) );
    		cpt++;
    	}

		return new Tableau<Tableau<Typable>>(nom, modifiable, list2);
    }
    
    public static Tableau<Tableau<Tableau<Typable>>> new_Tableau3D(String nom, boolean modifiable, Typable[][][] T3)
    {
    	ArrayList<Tableau<Tableau<Typable>>> list3 = new ArrayList<Tableau<Tableau<Typable>>>();

    	int cpt=0;

    	for (Typable[][] t2 : T3) 
    	{
    		list3.add(new_Tableau2D(nom+cpt,modifiable,t2));
    		cpt++;
    	}

		return new Tableau<Tableau<Tableau<Typable>>>(nom,modifiable,list3);
    }

    public static void main(String[] args) 
    {
    	
    	/*----------------1 Dimension------------------*/
		ArrayList<Chaine> list1 = new ArrayList<Chaine>();

		for( int i = 0; i < 5; i++ )
			//list1.add(new Chaine("t" + i, true, i));
			list1.add(new Chaine("t" + i, true, "x".repeat( (int) (Math.random()*20) )));

		Tableau<Chaine> tab1 = new Tableau<Chaine>("tableau",true,list1);

    	System.out.println	("1D------------\n"+tab1);


    	/*----------------2 Dimension------------------*/
    	ArrayList<Tableau<Chaine>> list2 = new ArrayList<Tableau<Chaine>>();

		for( int j = 0; j < 5; j++ )
			list2.add(tab1);

		Tableau<Tableau<Chaine>> tab2 = new Tableau<Tableau<Chaine>>("tableau",true,list2);

    	System.out.println	("2D------------\n"+tab2);

		tab2.versPressePapier();
    	
    	/*----------------3 Dimension------------------*/
    	ArrayList<Tableau<Tableau<Chaine>>> list3 = new ArrayList<Tableau<Tableau<Chaine>>>();

		list3.add(tab2);
		list3.add(tab2);

		Tableau<Tableau<Tableau<Chaine>>> tab3 = new Tableau<Tableau<Tableau<Chaine>>>("tableau",true,list3);

    	System.out.println	("3D------------\n"+tab3);

    }
}