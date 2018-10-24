package ErrorHandler;

import org.apache.logging.log4j.Logger;

public class ErrorHandler {

    public static void badInputNumbersError (Logger logger) {
        System.err.println("Choisissez un des chiffres de la liste !");
        logger.warn("Bad input numbers : numbers must be in list");
    }

    public static void inputNotNumbersError (Logger logger) {
        logger.warn("Only numbers are accepted");
        System.err.println("Caractère(s) non accepté(s) : Veuillez entrer un nombre !");
    }

    public static void inputNotHaveRightLengthError (Logger logger, int length) {
        logger.warn("Input length different from that expected (" + length + ")");
        System.err.println("Vous devez entrer un nombre à " + length + " chiffres !");
    }

    public static void fileNotFoundError (Logger logger, Exception e) {
        logger.error(e);
        System.err.println("Le fichier n'existe pas !");
    }

    public static void cantWriteFileError (Logger logger, Exception e) {
        logger.error(e);
        System.err.println("Impossible de lire ou d'écrire dans le fichier.");
    }
}
