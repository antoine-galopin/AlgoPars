package AlgoPars;

import AlgoPars.Metier.Programme;
import AlgoPars.Metier.Types.Typable;
import AlgoPars.CUI.Affichage;

import java.util.ArrayList;

public class AlgoPars {
    private Programme prgm;
    private Affichage cui;
    private boolean estCommenter;

    /**
     * Constructeur de la classe AlgoPars
     * 
     * @param chemin nom du fichier passé en paramètre
     */
    public AlgoPars(String chemin) {
        this.cui = new Affichage(this);
        this.prgm = new Programme(this, chemin);
        this.cui.initialiserVariablesSuivies();
        this.prgm.executerAlgo();

    }

    /**
     * Méthode d'accès à l'ArrayList contenant les lignes du programme interprété
     * colorées
     * 
     * @return ArrayList<String>
     */
    public ArrayList<String> getLignesFichierColorie() {
        return this.prgm.getLignesFichierColorie();
    }

    /**
     * Méthode renvoyant l'indice de la ligne courante
     * 
     * @return int
     */
    public int getLigneActive() {
        return this.prgm.getLigneActive();
    }


    /**
     * Retourne l'ArrayList contenant les variables suivies.
     * @return Une ArrayList contenant les variables suivies.
     */
    public ArrayList<String> getVariablesSuivies() {
        return this.prgm.getVariablesSuivies();
    }


    /**
     * Retourne l'ArrayList contenant les breakpoints.
     * @return Une ArrayList contenant les breakpoints.
     */
    public ArrayList<Integer> getListeBreakPoints() {
        return this.prgm.getListeBreakPoints();
    }

    
    /**
     * Méthode qui nettoie la console et affiche la nouvelle étape de
     * l'interprétation
     */
    public void afficher() {

        System.out.print("\033[H\033[2J"); // Réinitialisation de l'affichage de la console
        System.out.flush();

        this.cui.afficher();
    }

    /**
     * Méthode qui ajoute une nouvelle ligne à la trace d'execution
     * 
     * @param trace ligne à ajouter
     */
    public void ajouterTraceExecution(String trace) {
        this.cui.ajouterTraceExecution(trace);
    }

    /**
     * Méthode qui appelle le constructeur de constantes
     * 
     * @param nom    nom de la constante
     * @param type   type de la constante
     * @param valeur une constante prend sa valeur à sa création
     */
    public void add(String nom, String type, String valeur) {
        this.prgm.add(nom, type, valeur);
    }

    /**
     * Méthode qui appelle le constructeur de variables
     * 
     * @param nom  nom de la variable
     * @param type type de la variable
     */
    public void add(String nom, String type) {
        this.prgm.add(nom, type);
    }

    /**
     * Méthode appelant le modificateur des valeurs de variables
     * 
     * @param nom    nom de la variable à modifier
     * @param valeur nouvelle valeur de la variable
     */
    public void affecterValeur(String nom, String valeur) {
        this.prgm.affecterValeur(nom, valeur);
    }

    /**
     * Retourne la valeur d'une variable.
     * @param nom Le nom de la variable.
     * @return La valeur de la variable comprise dans une chaîne de caractères.
     */
    public String getValeur(String nom) {
        return this.prgm.getValeur(nom);
    }


    /**
     * Équivalent de toString pour les variables.
     * @param nom Le nom de la variable.
     * @return La chaîne de caractères générée.
     */
    public String getString(String nom) {
        String sRet = this.prgm.getString(nom);

        if (sRet != null && sRet.startsWith("\"\""))
            return sRet.substring(1, sRet.length() - 1);

        return sRet;
    }


    /**
     * Retourne un booléen indiquant si l'interprétation est en train de lire les constantes.
     * @return Un booléen.
     */
    public boolean getBConstante() {
        return this.prgm.getBConstante();
    }


    /**
     * Retourne un booléen indiquant si l'interprétation est en train de lire les variables.
     * @return Un booléen.
     */
    public boolean getBVariable() {
        return this.prgm.getBVariable();
    }


    /**
     * Retourne un booléen indiquant si l'interprétation est en dans un SI.
     * @return Un booléen.
     */
    public boolean getBSi() {
        return this.prgm.getBSi();
    }


    /**
     * Permet de changer la valeur du booléen indiquant si on est dans les constantes.
     * @param bConstante La nouvelle valeur de la variable.
     */
    public void setBConstante(boolean bConstante) {
        this.prgm.setBConstante(bConstante);
    }


    /**
     * Permet de changer la valeur du booléen indiquant si on est dans les variables.
     * @param bConstante La nouvelle valeur de la variable.
     */
    public void setBVariable(boolean bVariable) {
        this.prgm.setBVariable(bVariable);
    }


    /**
     * Permet de changer la valeur de l'ArrayList contenant les booléens des expressions des SI.
     * @param bConstante La nouvelle ArrayList.
     */
    public void setAlSi(ArrayList<Boolean> alSi) {
        this.prgm.setAlSi(alSi);
    }


    /**
     * Permet de changer la valeur du booléen indiquant si on est dans un SI imbriqué.
     * @param bConstante La nouvelle valeur de la variable.
     */
    public void setBSi(boolean bSi) {
        this.prgm.setBSi(bSi);
    }


    /**
     * Permet de changer la valeur du int indiquant le nombre de SI actuel.
     * @param bConstante La nouvelle valeur de la variable.
     */
    public void setNbSi(int nbSi) {
        this.prgm.setNbSi(nbSi);
    }


    /**
     * Permet de changer la valeur du int indiquant le nombre de SI imbriqués actuel.
     * @param bConstante La nouvelle valeur de la variable.
     */
    public void setSiImbrique(int nbSi) {
        this.prgm.setSiImbrique(nbSi);
    }


    /**
     * Indique dans combien de SI l'interpréteur est.
     * @return Un int contenant le nombre de SI.
     */
    public int getNbSi() {
        return this.prgm.getNbSi();
    }


    /**
     * Retourne une ArrayList contenant les booléens des conditions des SI.
     * @return L'ArrayList des booléens.
     */
    public ArrayList<Boolean> getAlSi() {
        return this.prgm.getAlSi();
    }


    /**
     * Indique si l'interpréteur est actuellement dans des lignes qui sont commentées.
     * @return
     */
    public boolean estCommenter() {
        return this.estCommenter;
    }


    /**
     * Permet de changer la valeur de la variable indiquant si on est dans des lignes commentées.
     * @param estCommenter
     * @return
     */
    public boolean setCommenter(boolean estCommenter) {
        return this.estCommenter = estCommenter;
    }

    
    /**
     * Le main exécutant le programme.
     * @param args
     */
    public static void main(String[] args) {
        new AlgoPars(args[0]);
    }
}