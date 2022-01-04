package AlgoPars.Metier;

import AlgoPars.Metier.Types.*;

import java.util.ArrayList;


public class Donnee
{
    public static ArrayList<Typable> donnees = new ArrayList<Typable>();

    public Donnee(ArrayList<String> fichier)
    {
    	
    }

    public static void main(String[] args) 
    {
    	System.out.println(new Booleen  ("b1",true,true    ));
    	System.out.println(new Caractere("c1",true,'c'     ));
    	System.out.println(new Chaine   ("c2",true,"coucou"));
    	System.out.println(new Entier   ("e1",true,50      ));
    	System.out.println(new Reel     ("r1",true,2.8     ));

    	donnees.add(new Booleen  ("b1",false,true    ));
    	donnees.add(new Caractere("c1",true,'c'     ));
    	donnees.add(new Chaine   ("c2",true,"coucou"));
    	donnees.add(new Entier   ("e1",true,50      ));
    	donnees.add(new Reel     ("r1",true,2.8     ));

    	System.out.println(donnees);

    	//(Booleen)((donnees.get(0)).setValeur(false);
    	((Caractere)(donnees.get(1))).setValeur('o');

    	System.out.println(donnees);

        ArrayList<Booleen> list = new ArrayList<Booleen>();
        list.add(new Booleen("b2",true,true  ));
        list.add(new Booleen("b2",true,false ));

        System.out.println(
            new Tableau<Booleen> ("t1",true,list)
        );
    }
}