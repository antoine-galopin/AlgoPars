package AlgoPars.CUI;

public class Affichage {

    public Affichage() {
        System.out.println(entete());
    }

    private String entete() {
        String str = "¨".repeat(11);
        String sret = String.format("%-80s", str) + str + "\n";
        sret += String.format("%-80s", "|  CODE   |") + "| DONNEES |" + "\n";
        sret += "¨".repeat(80) + " " + "¨".repeat(41);
        return sret;
    }

    public static void main(String[] args) {
        new Affichage();
    }

}
