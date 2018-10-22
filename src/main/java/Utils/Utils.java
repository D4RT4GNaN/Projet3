package Utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class Utils {

    private static Logger logger = LogManager.getLogger("main.java.Utils.Utils");

    /**
     * Return a random int between min value and max value
     * @param min The minimum int you want
     * @param max The maximum int you want
     * @return A random int between min and max
     */
    private static int randomInt(int min, int max) {
        return min + (int) (Math.random() * (max - min));
    }

    /**
     * Create randomly a combination with variable size
     * @param combinationLength The size of the table
     * @return The table who contains the secret code
     */
    public static int[] randomCombination(int combinationLength) {
        int[] randomCombination = new int[combinationLength];
        for (int i = 0; i < combinationLength; i++) {
            switch (i) {
                case 0:
                    randomCombination[i] = randomInt(1, 9);
                    break;
                default:
                    randomCombination[i] = randomInt(0, 9);
                    break;
            }
        }
        return randomCombination;
    }

    /**
     * Convert the secret code from table to String cast
     * @param randomCombination The table of the secret code
     * @return The secret code casted into String
     */
    public static String combinationToString (int[] randomCombination) {
        StringBuilder combinationOut = new StringBuilder();

        for (int i : randomCombination) {
            combinationOut.append(Integer.toString(i));
        }

        return combinationOut.toString();
    }

    /**
     * Load the config file
     * @return properties contained in the config file
     */
    private static Properties loadConfigFile () {
        Properties properties = new Properties();

        try {
            logger.info("Reading config file");
            FileInputStream config = new FileInputStream("src/main/resources/config.properties");
            properties.load(config);
            config.close();
        } catch (FileNotFoundException e) {
            logger.error(e);
            System.err.println("Le fichier n'existe pas !");
        } catch (IOException e) {
            logger.error(e);
            System.err.println("Impossible de lire ou d'écrire dans le fichier.");
        }

        return properties;
    }

    /**
     * Get the value of case number in the property's file
     * @param game The name of game you play
     * @return The number of case contained in the secret code
     */
    public static int getSecretCodeLength (String game) {
        Properties properties = loadConfigFile();
        logger.info("Recovered the length of the secret code");

        return Integer.parseInt(properties.getProperty("number-case-" + game));
    }

    /**
     * Get the value of max tries in the property's file
     * @param game The name of game you play
     * @return The number tries you have to found the secret code
     */
    public static int getMaxTries (String game) {
        Properties properties = loadConfigFile();
        logger.info("Recovered the number of max tries");

        return Integer.parseInt(properties.getProperty("number-tries-" + game));
    }

    /**
     * Check if the string in parameter contain an int
     * @param proposal The text proposed by the player
     * @return True if it's a number, False if it's something else
     */
    public static boolean isNumber (String proposal) {
        try {
            Integer.parseInt(proposal);
            return true;
        } catch (NumberFormatException e) {
            logger.warn("Only numbers are accepted");
            System.err.println("Caractère(s) non accepté(s) : Veuillez entrer un nombre !");
            return false;
        }
    }

    /**
     * Check if the string in parameter have the same length with the secret code
     * @param proposal The proposal of the player
     * @param game The name of the game
     * @return True if it have the same length
     */
    public static boolean hasSameLength (String proposal, String game) {
        int secretCodeLength = getSecretCodeLength(game);
        if (proposal.length() == secretCodeLength)
            return true;
        else {
            logger.warn("Input length different from that expected (" + secretCodeLength + ")");
            System.err.println("Vous devez entrer un nombre à " + secretCodeLength + " chiffres !");
            return false;
        }
    }

    /**
     * Allows access to developer mode
     * @param paramUser the keyword for the developer mode
     * @return true if it's the correct keyword
     */
    public static boolean hasDevMode(String[] paramUser) {
        Properties properties = loadConfigFile();
        if (Boolean.parseBoolean(properties.getProperty("developer-mode")))
            return true;
        else
            return paramUser.length == 2 && paramUser[1].equals("dev");
    }
}
