package AlgoPars.Metier;

import java.util.Scanner;


public class Primitives {

    public static String Lire() {
        String msg = "";
        try { msg = new Scanner(System.in).nextLine(); } catch (Exception e) {}
        return msg;
    }

    // public static String Ecrire() {
    // }
}