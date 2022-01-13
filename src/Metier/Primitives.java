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
     * 
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
     * 
     * @param msg Le message contenant les valeurs et variables à afficher.
     */
    public void ecrire(String msg) {
        String result = "";

        Pattern ptrn = Pattern.compile("(\"(.+?)\")|(\\d+(\\.\\d*)*)|(\\w+)");
        Matcher matcher = ptrn.matcher(msg);

        String param = "";
        while (matcher.find()) {
            param = matcher.group();
            System.out.println(param);
            if (param.contains("\""))
                result += param.replace("\"", "") + " ";
            else if (param.equals("vrai") || param.equals("faux"))
                result += param + " ";
            else if (this.ctrl.getValeur(param) != null)
                result += this.ctrl.getValeur(param).replace("\"", "") + " ";
                
            else
                result += param + " ";
        }

        this.ctrl.ajouterTraceExecution(result);
    }

    /**
     * Permet d'exécuter un si.
     * 
     * @param msg La condition à vérifier.
     */
    public void si(String msg) {
        ArrayList<Boolean> result = new ArrayList<Boolean>();
        if (this.ctrl.getAlSi() != null) {
            switch (msg) {
                case "vrai", "true":
                    if (this.ctrl.getAlSi().size() > this.ctrl.getNbSi() + 1)
                        this.ctrl.getAlSi().set(this.ctrl.getNbSi(), Boolean.TRUE);
                    else
                        this.ctrl.getAlSi().add(Boolean.TRUE);

                    this.ctrl.setNbSi(this.ctrl.getNbSi() + 1);
                    break;
                case "faux", "false":
                    if (this.ctrl.getAlSi().size() > this.ctrl.getNbSi() + 1)
                        this.ctrl.getAlSi().set(this.ctrl.getNbSi(), Boolean.FALSE);
                    else
                        this.ctrl.getAlSi().add(Boolean.FALSE);

                    this.ctrl.setNbSi(this.ctrl.getNbSi() + 1);
                    break;
            }
        } else {
            switch (msg) {
                case "vrai", "true":
                    result.add(Boolean.TRUE);
                    this.ctrl.setAlSi(result);
                    this.ctrl.setNbSi(0);
                    break;
                case "faux", "false":
                    result.add(Boolean.FALSE);
                    this.ctrl.setAlSi(result);
                    this.ctrl.setNbSi(0);
                    break;
            }

        }
    }

    /**
     * Retourne le caractère correspondant à l'entier passé en paramètre selon
     * la table ASCII.
     * 
     * @param entier L'entier à convertir en caractère.
     * @return Le caractère correspondant à l'entier de la table ASCII.
     */
    public static String car(String entier) {
        return String.valueOf((char) Integer.parseInt(entier));
    }

    /**
     * Retourne un entier correspondant au caractère passé en paramètre selon
     * la table ASCII.
     * 
     * @param caractere Le caractère à convertir en entier.
     * @return L'entier correspondant au caractère dans la table ASCII.
     */
    public static String ord(String caractere) {
        return String.valueOf((int) caractere.charAt(0));
    }

    /**
     * Convertie une variable en chaîne de caractères..
     * @param chaine La variable à convertir.
     * @return La chaîne de caractères contenant la valeur de la variable.
     */
    public static String enChaine(String str) {
        return "\"" + str + "\"";
    }

    /**
     * Convertie une chaîne de caractères en Entier.
     * @param chaine La chaîne de caractères à convertir.
     * @return L'Entier contenu dans une chaîne de caractères.
     */
    public static String enEntier(String chaine) {
        return chaine.replaceAll("\"", "");
    }

    /**
     * Convertie une chaîne de caractères en Réel.
     * @param chaine La chaîne de caractères à convertir.
     * @return Le Réel contenu dans une chaîne de caractères.
     */
    public static String enReel(String chaine) {
        return chaine.replaceAll("\"", "");

    }

    /**
     * Retourne l'entier le plus proche d'un réel vers le bas.
     * @param reel Le réel à partir duquel chercher le prochain entier inférieur.
     * @return Une chaîne de caractères contentant l'entier.
     */
    public static String plancher(String reel) {
        return String.valueOf(Math.floor(Double.parseDouble(reel)));
    }


    /**
     * Retourne l'entier le plus proche d'un réel vers le haut.
     * @param reel Le réel à partir duquel chercher le prochain entier supérieur.
     * @return Une chaîne de caractères contentant l'entier.
     */
    public static String plafond(String reel) {
        return String.valueOf(Math.ceil(Double.parseDouble(reel)));
    }

    /**
     * Retourne l'arrondi au supérieur d'un réel.
     * @param reel Le réel à arrondir.
     * @return La valeur arrondie dans une chaîne de caractères.
     */
    public static String arrondi(String reel) {
        return String.valueOf(Math.round(Double.parseDouble(reel)));
    }

    /**
     * Retourne une chaîne de caractères contenant la date actuelle.
     * La date est sous la forme 'jj/mm/aaaa'.
     * 
     * @return La chaîne de caractères contenant la date.
     */
    public static String aujourdhui() {
        LocalDateTime date = LocalDateTime.now();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return "\"" + (date.format(format)) + "\"";
    }

    
    /**
     * Retourne la partie jour de la date actuelle.
     * La date est sous la forme 'jj/mm/aaaa'.
     * 
     * @return Une chaîne de caractère contenant le jour.
     */
    public static String jour(String chaine) {
        return String.valueOf(chaine.substring(0, 2));
    }

    /**
     * Retourne la partie mois de la date actuelle.
     * La date est sous la forme 'jj/mm/aaaa'.
     * 
     * @return Une chaîne de caractère contenant le mois.
     */
    public static String mois(String chaine) {
        return String.valueOf(chaine.substring(3, 5));
    }

    /**
     * Retourne la partie année de la date actuelle.
     * La date est sous la forme 'jj/mm/aaaa'.
     * 
     * @return Une chaîne de caractère contenant l'année.
     */
    public static String annee(String chaine) {
        return String.valueOf(chaine.substring(6, 10));
    }

    /**
     * Indique si une chaîne de caractères peut être convertie en réel.
     * @param chaine La chaîne de caractères à vérifier.
     * @return Une chaîne de caractères contenant le booléen.
     */
    public static String estReel(String chaine) {
        return chaine.matches("^\\d+\\.\\d+$") == true ? "vrai" : "faux";
    }

    /**
     * Indique si une chaîne de caractères peut être convertie en entier.
     * @param chaine La chaîne de caractères à vérifier.
     * @return Une chaîne de caractères contenant le booléen.
     */
    public static String estEntier(String chaine) {
        return chaine.matches("^\\d+$") == true ? "vrai" : "faux";
    }

    // retourne une valeur entière prise au hasard sur l'intervalle [ 0; paramètre ]
    /**
     * Retourne une valeure entière prise au hasard dans l'intervalle [ 0; entier ].
     * @param entier La deuxième borne de l'intervalle.
     * @return Une chaîne de caractères contenant l'entier généré. 
     */
    public static String hasard(String entier) {
        return String.valueOf(Math.random() * Integer.parseInt(entier));
    }
}