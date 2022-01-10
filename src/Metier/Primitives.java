package AlgoPars.Metier;

import AlgoPars.AlgoPars;
import AlgoPars.Metier.Programme;
import AlgoPars.Metier.Types.*;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

import java.util.Scanner;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.lang.reflect.Method;

public class Primitives {
    private AlgoPars ctrl;
    public Method[] listePrimitives;

    public Primitives(AlgoPars ctrl) {
        this.ctrl = ctrl;

        // tableau des méthodes primitives
        this.listePrimitives = this.getClass().getDeclaredMethods();
    }

    public void lire(String var) {
        System.out.println("Lire " + var + " : " );
        String msg = new Scanner(System.in).nextLine();
        this.ctrl.affecterValeur(var, msg);
    }


    public void ecrire(String msg) {
        String result = "";

        Pattern ptrn = Pattern.compile( "(\"(.+?)\")|(\\d+(\\.\\d*)*)|(\\w+)" );
        Matcher matcher = ptrn.matcher( msg );

        String param = "";
        while(matcher.find()) {
            param = matcher.group();

            if( param.contains( "\"" ) )
                result += param.replace( "\"", "" ) + " ";
            else if( param.equals( "vrai" ) || param.equals( "faux" ) )
                result += param + " ";
            else if( this.ctrl.getValeur( param ) != null )
                result += this.ctrl.getValeur( param ) + " ";
            else
                result += param + " ";
        }

        this.ctrl.ajouterTraceExecution(result);
    }

    // retourne le caractère correspondant à sa valeur dans la table ASCII
    public static char car(Entier entier) {
        return (char) (entier.getValeur().intValue());
    }

    // retourne la valeur ASCII d'un caractère
    public static int ord(Caractere caractere) {
        return (int) (caractere.getValeur().charValue());
    } 

    // convertit un réel en chaine
    public static String enChaine(Typable t) {
        return t.toString();
    }

    // convertit une chaine en entier
    public static int enEntier(Chaine chaine) {
        return Integer.parseInt(chaine.getValeur());
    }

    // convertit une chaine en réel
    public static double enReel(Chaine chaine) {
        return Double.parseDouble(chaine.getValeur());
    }

    // retourne l'entier le plus proche d'un réel vers le bas
    public static int plancher(Reel reel) {
        return (int) Math.floor(reel.getValeur());
    }

    // retourne l'entier le plus proche d'un réel vers le haut
    public static int plafond(Reel reel) {
        return (int) Math.ceil(reel.getValeur());
    }

    // retourne l'entier le plus proche d'un réel ( par convention, arrondi de x,5 vaut x+1 )
    public static int arrondi(Reel reel) {
        return (int) Math.round(reel.getValeur());
    }


    // retourne sous forme de chaine la date du jour au format jj/mm/aaaa
    public static String aujourdhui() {
        LocalDateTime date = LocalDateTime.now();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return(date.format(format));
    }

    // retourne la partie jour d'une chaine correspondant à une date au format jj/mm/aaaa
    public static int jour(Chaine chaine) {
        return Integer.parseInt(chaine.getValeur().substring(0, 2));
    }

    // retourne la partie mois d'une chaine correspondant à une date au format jj/mm/aaaa
    public static int mois(Chaine chaine) {
        return Integer.parseInt(chaine.getValeur().substring(3, 5));
    }

    // retourne la partie année d'une chaine correspondant à une date au format jj/mm/aaaa
    public static int annee(Chaine chaine) {
        return Integer.parseInt(chaine.getValeur().substring(6, 10));
    }

    // indique si la chaine peut être convertie en réel
    public static boolean estReel(Chaine chaine) {
        return chaine.getValeur().matches("^\\d+,\\d+$");
    }

    // indique si la chaine peut être convertie en entier
    public static boolean estEntier(Chaine chaine) {
        return chaine.getValeur().matches("^\\d+$");
    }

    // retourne une valeur entière prise au hasard sur l'intervalle [ 0; paramètre ]
    public static int hasard(Entier entier) {
        return (int) (Math.random() * entier.getValeur());
    }
}