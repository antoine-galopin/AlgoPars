package AlgoPars.Metier;

public class Primitives {

    public static String Lire() {
        try (Scanner sc = new Scanner(System.in)) {
            return sc.nextLine();
        } catch (Exception e) {
        }
    }

    // public static String Ecrire() {
    // }
}