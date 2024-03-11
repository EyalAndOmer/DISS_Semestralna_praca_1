package sk.majba.montecarlo;

/**
 * This class is required for the JAR building process to split the logic of JavaFX from the main class
 *  otherwise I was unable to produce a working JAR file
 */
public class Main {
    public static void main(String[] args) {
        MainGUI.main(args);
    }
}
