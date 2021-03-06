package Utils;

import ErrorHandler.ErrorHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class Utils {

    protected static Logger logger = LogManager.getLogger("main.java.Utils.Utils");

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
    public static int[] randomCombination(int combinationLength, int max) {
        int[] randomCombination = new int[combinationLength];
        for (int i = 0; i < combinationLength; i++) {
            switch (i) {
                case 0:
                    randomCombination[i] = randomInt(1, max);
                    break;
                default:
                    randomCombination[i] = randomInt(0, max);
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
     * Convert the secret code from table to int cast
     * @param randomCombination The table of the secret code
     * @return The secret code casted into int
     */
    public static int combinationToInt (int[] randomCombination) {
        int combinationInt;
        StringBuilder combinationTemp = new StringBuilder();

        for (int i : randomCombination) {
            combinationTemp.append(Integer.toString(i));
        }
        combinationInt = Integer.parseInt(combinationTemp.toString());

        return combinationInt;
    }

    /**
     * Convert the secret code from int to table of int
     * @param randomCombination The int value of the secret code
     * @return The secret code splited into table of int
     */
    public static int[] intToCombination (int randomCombination) {
        String temp = "" + randomCombination;
        int tempLength = temp.length();
        int[] combinationTable = new int[tempLength];

        for (int i = 0; i < tempLength; i++)
            combinationTable[i] = Integer.parseInt("" + temp.charAt(i));

        return combinationTable;
    }

    /**
     * Load the config file
     * @return properties contained in the config file
     */
    protected static Properties loadConfigFile () {
        Properties properties = new Properties();

        try {
            logger.info("Reading config file");
            FileInputStream config = new FileInputStream("src/main/resources/config.properties");
            properties.load(config);
            config.close();
        } catch (FileNotFoundException e) {
            ErrorHandler.fileNotFoundError(logger, e);
        } catch (IOException e) {
            ErrorHandler.cantWriteFileError(logger, e);
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
            ErrorHandler.inputNotNumbersError(logger);
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
            ErrorHandler.inputNotHaveRightLengthError(logger, secretCodeLength);
            return false;
        }
    }

    /**
     * Allows access to developer mode
     * @return true if it's the correct keyword
     */
    public static boolean hasDevMode() {
        Properties properties = loadConfigFile();
        return Boolean.parseBoolean(properties.getProperty("developer-mode"));
    }
}
