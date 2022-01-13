package AlgoPars.Metier.Types;

import java.util.Collection ;
import java.util.Iterator ;
import java.util.ArrayList ;

import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;


public class Tableau<T extends Typable>	// C'est un generique de typable
extends Typable<Collection<T>>			// C'est un typable
implements Collection<T>				// C'est une collection
{
	private final int taille;

    public Tableau(String nom, boolean modifiable, Collection<T> valeur)
    {
        super(nom, modifiable, valeur);
        this.taille = valeur.size();
        System.out.println(this);
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

    public static Tableau new_TableauXD(String nom, boolean modifiable, int dimension)
    {   
        switch (dimension) {
            case 1 : return (Tableau)new_Tableau1D(nom,modifiable,new Typable[]{});
            case 2 : return (Tableau)new_Tableau2D(nom,modifiable,new Typable[][]{});
            case 3 : return (Tableau)new_Tableau3D(nom,modifiable,new Typable[][][]{});
        }

        return null ;
    }

    public static void main(String[] args) 
    {
    	
    	/*----------------1 Dimension------------------*/
        Booleen test = new Booleen("test",true,false);

        System.out.println(new_Tableau3D("tab3",true,
            new Booleen[][][]{
                    {
                        {test,test},{test,test},{test,test}
                    },
                    {
                        {test,test},{test,test},{test,test}
                    },
                    {
                        {test,test},{test,test},{test,test}
                    }
                }
            )
        );

        System.out.println(new_Tableau2D("tab3",true,
            new Booleen[][] {
                                {test,test},
                                {test,test},
                                {test,test}
                            }
            )
        );
        
        Tableau<Tableau<Typable>> tab = new_Tableau2D("tab3", true, new Booleen[][] { {test,test}, {test,test}, {test,test} });
        System.out.println("-->" + tab.toString());
        tab.versPressePapier();
    }
}