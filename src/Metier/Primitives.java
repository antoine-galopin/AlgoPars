package AlgoPars.Metier;

import AlgoPars.AlgoPars;
import AlgoPars.Metier.Programme;
import AlgoPars.Metier.Types.*;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

import java.util.Scanner;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.lang.reflect.Method ;

public class Primitives {
    private AlgoPars ctrl;
    private Programme prgm;
    public String[] listePrimitives ;

    public Primitives(AlgoPars ctrl, Programme prgm) {
        this.ctrl = ctrl;
        this.prgm = prgm;

        /*------------------le tableau des nom de toute les primitives----------------*/
        //on prend la classe, on prend toute ses primitives et on retient la taille
        listePrimitives = new String[this.getClass().getDeclaredMethods().length];

        int cpt=0 ;

        //on prend tout les noms 
        for (Method m : this.getClass().getDeclaredMethods()) {
            listePrimitives[cpt]=m.getName();
            cpt++ ;
        }
        /*
        for (String s : listePrimitives) {
            System.out.print(s+"\n");
        }*/
    }

    public void lire(String var) {
        System.out.println("Lire:");
        String msg = new Scanner(System.in).nextLine();
        this.ctrl.affecterValeur(var, msg);
    }


    public void ecrire(String msg) {
        String result = "";

        // Cette regex permet de 
        Pattern ptrn = Pattern.compile( "(\"(.+?)\")|(\\d+(\\.\\d*)*)|(\\w+)" );
        Matcher matcher = ptrn.matcher( msg );

        String param = "";
        while ( matcher.find() )
        {
            param = matcher.group();
            if ( param.contains( "\"" ) )
                result += param.replace( "\"", "" ) + " ";
            else if ( param.equals( "vrai" ) || param.equals( "faux" ) )
                result += param + " ";
            else if ( this.ctrl.getValeur( param ) != null )
                result += this.ctrl.getValeur( param ) + " ";
            else
                result += param + " ";
        }

        this.ctrl.ajouterTraceExecution( result );
    }

    //retourne le caractère correspond à la valeur entière de la table ASCII
    public static char car (Entier entier )
    { return (char)(entier.getValeur().intValue()); }

    //retourne la valeur ASCII d'un caractère
    public static int ord (Caractere caractere )
    { return (int )(caractere.getValeur().charValue()); } 

    //convertie un réel en Chaine
    public static String enChaine ( Typable t )
    { return t.toString(); }

    //convertie une Chaine en entier,la chaine doit être composée que de caractères ['0';'9'] 
    //et éventuellement d'un caractère signe '-' ou '+' en début de chaine
    public static int enEntier ( Chaine chaine )
    { return Integer.parseInt(chaine.getValeur());}

    //convertie une Chaine en réel,la chaine doit être composée que de caractères  ['0';'9'] 
    //un et un seul caractère ',' et éventuellement d'un caractère signe '-' ou '+' en début de chaine
    public static double enRéel ( Chaine chaine )
    { return Double.parseDouble(chaine.getValeur());}

    //retourne la valeur plancher d'un réel
    //plancher ( 2,3 ) donne 2 plafond (2,7) donne 2
    public static int plancher (Reel reel )
    { return (int)Math.floor(reel.getValeur());}

    //retourne la valeur plafond d'un réel
    //plafond ( 2,3 ) donne 3 plafond (2,7) donne 3
    public static int plafond (Reel reel )
    { return (int)Math.ceil(reel.getValeur());}

    //retourne la valeur arrondie d'un réel
    //arrondi ( 2,3 ) donne 2 arrondi (2,7) donne 3 arrondi (2,5) donne 3
    public static int arrondi (Reel reel )
    { return Math.round(Math.round(reel.getValeur())); }
/*
    L'affectation d'un entier dans un réel est possible, des 0 sont ajoutés dans la partie décimale
    L'affectation d'un réel dans un entier est possible, la partie décimale est perdue

    On peut concaténer (en pseudo-code à l'aide de l'opérateur ©) des types de la catégorie textuelle entre
    eux.

*/
    //retourne sous forme de chaine la date du jour au format jj/mm/aaaa
    public static String aujourdhui()
    {
        LocalDateTime date = LocalDateTime.now();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return(date.format(format));
    }

    //retourne la partie jour d'une chaine correspondant à une date au format jj/mm/aaaa
    public static int jour(Chaine chaine)
    { return Integer.parseInt(chaine.getValeur().substring(0,2));}

    //retourne la partie mois d'une chaine correspondant à une date au format jj/mm/aaaa
    public static int mois(Chaine chaine)
    { return Integer.parseInt(chaine.getValeur().substring(3,5));}

    //retourne la partie jour d'une chaine correspondant à une date au format jj/mm/aaaa
    public static int annee(Chaine chaine)
    { return Integer.parseInt(chaine.getValeur().substring(6,10));}

    //indique si la chaine peut être convertie en réel
    public static boolean estReel (Chaine chaine)
    {
        return chaine.getValeur().matches("^\\d+,\\d+$");
    }

    //indique si la chaine peut être convertie en entier
    public static boolean estEntier (Chaine chaine)   
    {
        return chaine.getValeur().matches("^\\d+$");
    }
    //retourne une valeur entière prise au hasard sur l'intervalle [ 0; valeur du paramètre [ 
    public static int hasard (Entier entier)
    {
        return (int)(Math.random() * entier.getValeur());
    }

    public static void main(String[] args) {
        System.out.println(Primitives.aujourdhui());
        System.out.println(Primitives.plafond(new Reel("r1",false,2.1)));

    }
}