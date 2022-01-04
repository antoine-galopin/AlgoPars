package AlgoPars.Metier.Types;

import java.util.Collection;
import java.util.Iterator;
/**
 * classe generique tableau -> on peut avoir un tableau de tout les typables
 * tableau par heritage est une liste du type donnee
 */
public class Tableau<T extends Typable>
extends Typable<Collection<T>>
implements Collection<T>
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

    public String toString() {return valeur.toString();}
}