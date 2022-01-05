package AlgoPars;

import AlgoPars.Metier.Programme;
import AlgoPars.CUI.Affichage;

import java.util.ArrayList;


public class AlgoPars {
    private Programme prgm;
    private Affichage cui;

    public AlgoPars(String chemin) {
        this.cui = new Affichage(this);
        this.prgm = new Programme(this, "../utilisateur/" + chemin + ".algo");
        this.prgm.executerAlgo();
    }


    public ArrayList<String> getLignesFichierColorie() { return this.prgm.getLignesFichierColorie(); }


    public int getLigneActive() { return this.prgm.getLigneActive(); }


    public void afficher() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
        this.cui.afficher();
    }


    public void ajouterTraceExecution(String trace) { this.cui.ajouterTraceExecution(trace); }


    public void add(String nom, String type, String valeur) { this.prgm.add(nom, type, valeur); } // Appel du constructeur de constantes
    public void add(String nom, String type               ) { this.prgm.add(nom, type        ); } // Appel du constructeur de variables


    public void affecterValeur(String nom, String valeur) { this.prgm.affecterValeur( nom, valeur ); }

    public static void main(String[] args) {
        if(args[0] == null) System.out.println("Le nom du programme à interpréter doit être passé en paramètre");

        new AlgoPars(args[0]);
    }
}