package AlgoPars.Metier;

import AlgoPars.Metier.Types.*;

import java.util.ArrayList;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class Donnee {
    private ArrayList<Typable> donnees;
    /*
     * private final String[][] tabRegex = new String[][] {
     * { "'.'", "\"[^\\\"]*\"", "\\d,\\d+", "vrai", "faux", "\\d+" },
     * { "caractere", "chaine", "reel", "booleen", "entier" }
     * };
     */

    Typable var;

    /**
     * Constructeur de la classe Donnee
     */
    public Donnee() {
        donnees = new ArrayList<Typable>();
    }

    /**
     * Méthode qui instancie une variable
     * 
     * @param nom
     * @param type
     */
    public void add(String nom, String type) {
        switch (type) {
            case "booleen": {
                this.donnees.add(new Booleen(nom, true, false));
                break;
            }
            case "caractere": {
                this.donnees.add(new Caractere(nom, true, ' '));
                break;
            }
            case "chaine": {
                this.donnees.add(new Chaine(nom, true, ""));
                break;
            }
            case "entier": {
                this.donnees.add(new Entier(nom, true, 0));
                break;
            }
            case "reel": {
                this.donnees.add(new Reel(nom, true, 0.0));
                break;
            }
            default:

                int dimension = 0;

                while (type.indexOf("tableaude") != -1) {
                    dimension++;
                    type = type.replaceFirst("tableaude", "");
                }

                switch (type) {
                    case "booleen": {
                        this.donnees.add(Tableau.new_TableauXD(nom, true, dimension));
                        break;
                    }
                    case "caractere": {
                        this.donnees.add(Tableau.new_TableauXD(nom, true, dimension));
                        break;
                    }
                    case "chaine": {
                        this.donnees.add(Tableau.new_TableauXD(nom, true, dimension));
                        break;
                    }
                    case "entier": {
                        this.donnees.add(Tableau.new_TableauXD(nom, true, dimension));
                        break;
                    }
                    case "reel": {
                        this.donnees.add(Tableau.new_TableauXD(nom, true, dimension));
                        break;
                    }
                }
        }
    }

    /**
     * Méthode qui instancie une constante
     * 
     * @param nom
     * @param type
     * @param valeur
     */
    public void add(String nom, String type, String valeur) {
        if (type == null)
            type = Calculateur.getType(valeur);

        switch (type) {
            case "booleen": {
                this.donnees.add(new Booleen(nom, false, valeur.equals("vrai") ? true : false));
                break;
            }
            case "caractere": {
                this.donnees.add(new Caractere(nom, false, valeur.charAt(1)));
                break;
            }
            case "chaine": {
                this.donnees.add(new Chaine(nom, false, valeur));
                break;
            }
            case "entier": {
                this.donnees.add(new Entier(nom, false, Integer.parseInt(valeur)));
                break;
            }
            case "reel": {
                valeur = valeur.replaceAll(",", ".");
                this.donnees.add(new Reel(nom, false, Double.parseDouble(valeur)));
                break;
            }
            // case "tableau" : { this.donnees.add(new Reel (nom, true , 0.0 )); break; }
            default:

                Pattern pattern = Pattern.compile("tableaude");
                Matcher matcher = pattern.matcher(type);

                int dimension = matcher.groupCount();

                type = type.replaceAll("tableaude", "");

                switch (type) {
                    case "booleen": {
                        this.donnees.add(Tableau.new_TableauXD(nom, true, dimension));
                        break;
                    }
                    case "caractere": {
                        this.donnees.add(Tableau.new_TableauXD(nom, true, dimension));
                        break;
                    }
                    case "chaine": {
                        this.donnees.add(Tableau.new_TableauXD(nom, true, dimension));
                        break;
                    }
                    case "entier": {
                        this.donnees.add(Tableau.new_TableauXD(nom, true, dimension));
                        break;
                    }
                    case "reel": {
                        this.donnees.add(Tableau.new_TableauXD(nom, true, dimension));
                        break;
                    }
                }
        }

    }

    /**
     * Méthode de recherche d'un Typable par son nom
     * 
     * @param nom
     * @return Typable
     */
    public Typable rechercheParNom(String nom) {
        for (Typable t : donnees)
            if (t.getNom().equals(nom))
                return t;

        return null;
    }

    /**
     * Méthode qui affecte une valeur à une variable en se basant sur le nom de
     * cette variable
     * 
     * @param nom
     * @param valeur
     */
    public void affecterValeur(String nom, String valeur) {
        this.var = rechercheParNom(nom);

        if (this.var.getType().equals("Caractere")) {
            ((Caractere) (this.var)).setValeur(valeur.charAt(0));
        } else if (this.var.getType().equals("Chaine")) {
            ((Chaine) (this.var)).setValeur(valeur);
        } else if (this.var.getType().equals("Reel")) {
            ((Reel) (this.var)).setValeur(Double.parseDouble(valeur));
        } else if (this.var.getType().equals("Booleen")) {
            ((Booleen) (this.var)).setValeur(valeur.matches("vrai"));
        } else if (this.var.getType().equals("Entier")) {
            ((Entier) (this.var)).setValeur(Integer.parseInt(valeur));
        }

        // if(valeur.matches("\w+(\w*)"))
        //

    }
}