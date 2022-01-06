package AlgoPars.Metier;

import AlgoPars.Metier.Types.*;

import java.util.ArrayList;

public class Donnee {
    private ArrayList<Typable> donnees;


    /**
     * Constructeur de la classe Donnee
     */
    public Donnee() {
        donnees = new ArrayList<Typable>();
    }


    /**
     * Méthode qui instancie une variable
     * @param nom
     * @param type
     */
    public void add(String nom, String type) {
        switch(type) {
            case "booleen"   : { this.donnees.add(new Booleen  (nom, true, false)); break; }
            case "caractere" : { this.donnees.add(new Caractere(nom, true, ' '  )); break; }
            case "chaine"    : { this.donnees.add(new Chaine   (nom, true, ""   )); break; }
            case "entier"    : { this.donnees.add(new Entier   (nom, true, 0    )); break; }
            case "reel"      : { this.donnees.add(new Reel     (nom, true, 0.0  )); break; }
            //case "tableau"   : { this.donnees.add(new Reel     (nom, true, 0.0  )); break; }
            default          : break;
        }
    }


    /**
     * Méthode qui instancie une constante
     * @param nom
     * @param type
     * @param valeur
     */
    public void add(String nom, String type, String valeur) {
        switch(type) {
            case "booleen"   : { this.donnees.add(new Booleen  (nom, false, valeur.equals("vrai") ? true : false)); break; }
            case "caractere" : { this.donnees.add(new Caractere(nom, false, valeur.charAt(1)                    )); break; }
            case "chaine"    : { this.donnees.add(new Chaine   (nom, false, valeur                              )); break; }
            case "entier"    : { this.donnees.add(new Entier   (nom, false, Integer.parseInt(valeur)            )); break; }
            case "reel"      : { this.donnees.add(new Reel     (nom, false, Double.parseDouble(valeur)          )); break; }
            //case "tableau"   : { this.donnees.add(new Reel     (nom, true , 0.0                                 )); break; }
            default          : break;
        }


        if( type == null ) {
            if (valeur.matches("'.'")) {
                add(nom, "caractere", valeur);
                return;
            }
            if (valeur.matches("\"[^\\\"]*\"")) {
                add(nom, "chaine", valeur);
                return;
            }
            if (valeur.matches(",")) {
                add(nom, "reel", valeur);
                return;
            }
            if (valeur.matches("vrai") || valeur.matches("faux")) {
                add(nom, "booleen", valeur);
                return;
            }
            if (valeur.matches("\\d+")) {
                add(nom, "entier", valeur);
                return;
            }
        }
    }

    public Typable rechercheParNom(String nom) {
        for (Typable t : donnees) {
            if (t.getNom().equals(nom)) {
                return t;
            }
        }

        return null;
    }

    public void affecterValeur(String nom, String valeur) {
        Typable var = rechercheParNom(nom);

        if (valeur.matches("'.'")) {
            ((Caractere) (var)).setValeur(valeur.charAt(1));
            return;
        }
        if (valeur.matches("\"[^\\\"]*\"")) {
            ((Chaine) (var)).setValeur(valeur.substring(1, valeur.length()-1));
            return;
        }
        if (valeur.matches(",")) {
            ((Reel) (var)).setValeur(Double.parseDouble(valeur));
            return;
        }
        if (valeur.matches("vrai") || valeur.matches("faux")) {
            ((Booleen) (var)).setValeur(valeur.matches("vrai"));
            return;
        }
        if (valeur.matches("\\d+")) {
            ((Entier) (var)).setValeur(Integer.parseInt(valeur));
            return;
        }

        // if (valeur.matches("\w+(\w*)"))
        //

    }
}