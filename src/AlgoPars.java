package AlgoPars;

import AlgoPars.Metier.Programme;
import AlgoPars.CUI.Affichage;

import java.util.ArrayList;


public class AlgoPars {
    private Programme prgm;
    private Affichage cui;


    /**
     * Constructeur de la classe AlgoPars
     * @param chemin nom du fichier passé en paramètre
     */
    public AlgoPars(String chemin) {
        this.cui = new Affichage(this);
        this.prgm = new Programme(this, "../utilisateur/" + chemin + ".algo");
        this.prgm.executerAlgo();
    }


    /**
     * Méthode d'accès à l'ArrayList contenant les lignes du programme interprété colorées
     * @return ArrayList<String>
     */
    public ArrayList<String> getLignesFichierColorie() { return this.prgm.getLignesFichierColorie(); }


    /**
     * Méthode renvoyant l'indice de la ligne courante
     * @return int
     */
    public int getLigneActive() { return this.prgm.getLigneActive(); }


    /**
     * Méthode qui nettoie la console et affiche la nouvelle étape de l'interprétation
     */
    public void afficher() {
        System.out.print("\033[H\033[2J"); // Réinitialisation de l'affichage de la console
        System.out.flush();
        this.cui.afficher();
    }


    /**
     * Méthode qui ajoute une nouvelle ligne à la trace d'execution
     * @param trace ligne à ajouter
     */
    public void ajouterTraceExecution(String trace) { this.cui.ajouterTraceExecution(trace); }


    /**
     * Méthode qui appelle le constructeur de constantes
     * @param nom nom de la constante
     * @param type type de la constante
     * @param valeur une constante prend sa valeur à sa création
     */
    public void add(String nom, String type, String valeur) { this.prgm.add(nom, type, valeur); }

    /**
     * Méthode qui appelle le constructeur de variables
     * @param nom nom de la variable
     * @param type type de la variable
     */
    public void add(String nom, String type) { this.prgm.add(nom, type); }


    /**
     * Méthode appelant le modificateur des valeurs de variables
     * @param nom nom de la variable à modifier
     * @param valeur nouvelle valeur de la variable
     */
    public void affecterValeur(String nom, String valeur) { this.prgm.affecterValeur( nom, valeur ); }

    public static void main(String[] args) {
        if(args[0] == null) System.out.println("Le nom du programme à interpréter doit être passé en paramètre");

        new AlgoPars(args[0]);
    }
}