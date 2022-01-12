package AlgoPars.Metier;

import AlgoPars.AlgoPars;
import AlgoPars.Metier.Programme;
import java.util.ArrayList;
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


    /**
     * Correspond à l'appel de la fonction lire en pseudo-code.
     * @param nom Le nom de la variable qui prendar la variable.
     */
    public void lire(String nom) {
        System.out.println("Lire " + nom + " : ");

        String valeur = new Scanner(System.in).nextLine();

        this.ctrl.affecterValeur(nom, valeur);
    }


    /**
     * Correspond à l'appel de la fonction écrire en pseudo-code.
     * Elle peut afficher des valeurs brutes ou des variables.
     * @param msg Le message contenant les valeurs et variables à afficher.
     */
    public void ecrire(String msg) {
        String result = "";

        Pattern ptrn = Pattern.compile("(\"(.+?)\")|(\\d+(\\.\\d*)*)|(\\w+)");
        Matcher matcher = ptrn.matcher(msg);

        String param = "";
        while (matcher.find()) {
            param = matcher.group();

            if (param.contains("\""))
                result += param.replace("\"", "") + " ";
            else if (param.equals("vrai") || param.equals("faux"))
                result += param + " ";
            else if (this.ctrl.getValeur(param) != null)
                result += this.ctrl.getValeur(param) + " ";
            else
                result += param + " ";
        }

        this.ctrl.ajouterTraceExecution(result);
    }


    /**
     * Permet d'exécuter un si.
     * @param msg La condition à vérifier.
     */
    public void si(String msg) {
        ArrayList<Boolean> result = new ArrayList<Boolean>();
        if (this.ctrl.getAlSi() != null) {
            switch (msg) {
                case "vrai":
                    this.addValAlSi(Boolean.TRUE);
                    this.ctrl.setBSi(true);
                    break;
                case "faux":
                    this.addValAlSi(Boolean.FALSE);
                    this.ctrl.setBSi(false);
                    break;
            }
        } else {
            switch (msg) {
                case "vrai":
                    result.add(Boolean.TRUE);
                    this.ctrl.setAlSi(result);
                    this.ctrl.setBSi(true);
                    break;
                case "faux":
                    result.add(Boolean.FALSE);
                    this.ctrl.setAlSi(result);
                    this.ctrl.setBSi(false);
                    break;
            }

        }
    }


    /**
     * Retourne le caractère correspondant à l'entier passé en paramètre selon 
     * la table ASCII.
     * @param entier L'entier à convertir en caractère.
     * @return Le caractère correspondant à l'entier de la table ASCII.
     */ 
    public static String car(String entier) {
        return String.valueOf((char)Integer.parseInt(entier));
    }


    /**
     * Retourne un entier correspondant au caractère passé en paramètre selon
     * la table ASCII.
     * @param caractere Le caractère à convertir en entier.
     * @return L'entier correspondant au caractère dans la table ASCII.
     */
    public static String ord(String caractere) {
        return String.valueOf( (int)caractere.charAt( 0 ) );
    }


    /**
     * Convertie une valeur d'un type en chaîne de caractères.
     * @param t La valeur à convertir.
     * @return La chaine de caractère de la valeur.
     */
    public static String enChaine(Typable t) {
        return t.toString();
    }


    /**
     * Retourne une chaîne de caractères convertie en entier.
     * @param chaine La chaîne à convertir.
     * @return La valeur entière convertie.
     */
    public static String enEntier(Chaine chaine) {
        return chaine.getValeur().toString();
    }


    /**
     * Retourne une chaîne de caractères convertie en réel.
     * @param chaine La chaîne à convertir.
     * @return La valeur réelle convertie.
     */
    public static String enReel(Chaine chaine) {
        return chaine.getValeur().toString();
    }


    /**
     * Retourne l'entier le plus proche d'un réel vers 0.
     * @param reel Le réel de départ.
     * @return L'entier contenant la valeur plancher de ce réel.
     */
    public static String plancher(Reel reel) {
        return String.valueOf(Math.floor(reel.getValeur()));
    }


    /**
     * Retourne l'entier le plus proche d'un réel vers +oo.
     * @param reel Le réel de départ.
     * @return L'entier contenant la valeur plafond de ce réel.
     */
    public static String plafond(Reel reel) {
        return String.valueOf(Math.ceil(reel.getValeur()));
    }


    /**
     * Convertie un réel en entier en l'arrondissant à la valeur supérieur. 
     * @param reel Le réel à arrondir.
     * @return L'entier résultant de cet arrondi.
     */
    public static String arrondi(Reel reel) {
        return String.valueOf(Math.round(reel.getValeur()));
    }


    /**
     * Retourne une chaîne de caractères contenant la date actuelle.
     * La date est sous la forme 'jj/mm/aaaa'.
     * @return La chaîne de caractères contenant la date.
     */
    public static String aujourdhui() {
        LocalDateTime date = LocalDateTime.now();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return (date.format(format));
    }


    /**
     * Retourne la partie 'jour' d'une date 'jj/mm/aaaa' passée en paramètre dans
     * une chaîne de caractères.
     * @param chaine La chaîne de caractères contenant la date.
     * @return La chaîne contenant le jour extrait de la date.
     */
    public static String jour(Chaine chaine) {
        return String.valueOf(chaine.getValeur().substring(0, 2));
    }


    /**
     * Retourne la partie 'mois' d'une date 'jj/mm/aaaa' passée en paramètre dans
     * une chaîne de caractères.
     * @param chaine La chaîne de caractères contenant la date.
     * @return La chaîne contenant le mois extrait de la date.
     */
    public static String mois(Chaine chaine) {
        return String.valueOf(chaine.getValeur().substring(3, 5));
    }


    /**
     * Retourne la partie 'année' d'une date 'jj/mm/aaaa' passée en paramètre dans
     * une chaîne de caractères.
     * @param chaine La chaîne de caractères contenant la date.
     * @return La chaîne contenant l'année extrait de la date.
     */
    public static String annee(Chaine chaine) {
        return String.valueOf(chaine.getValeur().substring(6, 10));
    }

    /**
     * Indique si la chaîne peut être convertie en réel.
     * @param chaine La chaîne à vérifier.
     * @return Un booléen indiquant si elle peut être convertie en réel ou non.
     */
    public static String estReel(Chaine chaine) {
        return chaine.getValeur().matches("^\\d+,\\d+$") == true ? "vrai" : "faux";
    }

    /**
     * Indique si la chaîne peut être convertie en entier.
     * @param chaine La chaîne à vérifier.
     * @return Un booléen indiquant si elle peut être convertie en entier ou non.
     */
    public static String estEntier(Chaine chaine) {
        return chaine.getValeur().matches("^\\d+$") == true ? "vrai" : "faux";
    }

    /**
     * Retourne une valeur aléatoire dans l'intervalle [ 0; paramètre ].
     * @param entier L'entier pour la borne supérieure de l'intervalle.
     * @return L'entier aléatoire.
     */
    public static String hasard(Entier entier) {
        return String.valueOf(Math.random() * entier.getValeur());
    }

    
    public void addValAlSi(Boolean val) {
        this.ctrl.addValAlSi(val);
    }
}