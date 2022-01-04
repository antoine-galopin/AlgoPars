package AlgoPars.Metier;

import AlgoPars.Metier.Types.*;

import java.util.ArrayList;


public class Donnee
{
    private ArrayList<Typable> donnees ;

    public Donnee()
    {
        donnees = new ArrayList<Typable>();
    }

    public void add(String nom,String type )
    {
        switch (type) {
            case "booleen"   -> this.donnees.add(new Booleen  (nom,true,false));
            case "caractere" -> this.donnees.add(new Caractere(nom,true,'\0' ));
            case "chaine"    -> this.donnees.add(new Chaine   (nom,true,""   ));
            case "entier"    -> this.donnees.add(new Entier   (nom,true,0    ));
            case "reel"      -> this.donnees.add(new Reel     (nom,true,0.0  ));
            //case "tableau"   -> this.donnees.add(new Reel     (nom,true,0.0  ));
        }
    }

    //constantes
    public void add(String nom,String type ,String valeur)
    {
        switch (type) {
            case "booleen"   -> this.donnees.add(new Booleen  (nom,false,valeur.equals("vrai")?true:false));
            case "caractere" -> this.donnees.add(new Caractere(nom,false,valeur.charAt( 1 )) );
            case "chaine"    -> this.donnees.add(new Chaine   (nom,false,valeur   ));
            case "entier"    -> this.donnees.add(new Entier   (nom,false,Integer.parseInt(valeur)    ));
            case "reel"      -> this.donnees.add(new Reel     (nom,false,Double.parseDouble(valeur)  ));
            //case "tableau"   -> this.donnees.add(new Reel     (nom,true,0.0  ));
        }
            if (type == null) {
                if (valeur.matches("'.'"          )) {add(nom,"caractere",valeur); return ;}
                if (valeur.matches("\"[^\\\"]*\"" )) {add(nom,"chaine"   ,valeur); return ;}
                if (valeur.matches(","            )) {add(nom,"reel"     ,valeur); return ;}
                if (valeur.matches("vrai")||valeur.matches("faux")) {add(nom,"booleen",valeur); return ;}
                if (valeur.matches("\\d+"          )) {add(nom,"entier"   ,valeur); return ;}
            
        }
    }

    public Typable rechercheParNom(String nom)
    {
        for (Typable t : donnees) {
            if (t.getNom().equals(nom)) {
                return t ;
            }
        }

        return null ;
    }

    public void affecterValeur(String nom , String valeur)
    {
        Typable var = rechercheParNom(nom);

        if (valeur.matches("'.'"          )) {((Caractere)(var)).setValeur(valeur.charAt(1)); return ;}
        if (valeur.matches("\"[^\\\"]*\"" )) {((Chaine   )(var)).setValeur(valeur)          ; return ;}
        if (valeur.matches(","            )) {((Reel     )(var)).setValeur(Double.parseDouble(valeur)); return ;}
        if (valeur.matches("vrai")||valeur.matches("faux")) {((Booleen)(var)).setValeur(valeur.matches("vrai")); return ;}
        if (valeur.matches("\\d+"          )) {((Entier   )(var)).setValeur(Integer.parseInt(valeur)); return ;}
        //if (valeur.matches("\w+(\w*)"))


    }

    public static void main(String[] args) 
    {
        /*
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
        );*/
    }
}