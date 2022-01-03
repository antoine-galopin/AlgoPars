package AlgoPars.CUI;

import AlgoPars.AlgoPars;

public class Affichage {
    private AlgoPars ctrl;

    public Affichage() {
        this.ctrl = ctrl;
        System.out.println(entete());
    }

    private String entete() {
        String str = "¨".repeat(11);
        String sret = String.format("%-80s", str) + str + "\n";
        sret += String.format("%-80s", "|  CODE   |") + "| DONNEES |" + "\n";
        sret += "¨".repeat(80) + " " + "¨".repeat(41) + "\n";
        sret += "|" + String.format("%-80s", this.ctrl.getLigne(0)) + "|" + String.format("%5s", "NOM")
                + String.format("%-9s", "|") + String.format("%9s", "VALEUR") + String.format("%-8s", "|");
        return sret;
    }

    public static void main(String[] args) {
        new Affichage();
    }

}
