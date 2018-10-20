package ErrorHandler;

import org.apache.logging.log4j.Logger;

public class ErrorHandler {

    public static void badInputNumbersError (Logger logger) {
        System.err.println("Choisissez un des chiffres de la liste !");
        logger.warn("Bad input numbers : numbers must be in list");
    }
}
