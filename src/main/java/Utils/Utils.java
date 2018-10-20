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
     * Convert the secret code from string to table of int
     * @param randomCombination The string value of the secret code
     * @return The secret code splited into table of int
     */
    public static int[] stringToCombination (String randomCombination) {
        int randomCombinationLength = randomCombination.length();
        int[] combinationTable = new int[randomCombinationLength];

        for (int i = 0; i < randomCombinationLength; i++)
            combinationTable[i] = Integer.parseInt("" + randomCombination.charAt(i));

        return combinationTable;
    }

    /**
     * Get the value of case number in the property's file
     * @param game The name of game you play
     * @return The number of case contained in the secret code
     */
    public static int getSecretCodeLength (String game) {
        Properties properties = new Properties();
        int secretCodeLength;

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

        logger.info("Recovered the length of the secret code");
        secretCodeLength = Integer.parseInt(properties.getProperty("number-case-" + game));

        return secretCodeLength;
    }

    /**
     * Get the value of max tries in the property's file
     * @param game The name of game you play
     * @return The number tries you have to found the secret code
     */
    public static int getMaxTries (String game) {
        Properties properties = new Properties();
        int maxTries;

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

        logger.info("Recovered the number of max tries");
        maxTries = Integer.parseInt(properties.getProperty("number-tries-" + game));

        return maxTries;
    }

    /**
     * Check if the string in parameter contain an int
     * @param string The text proposed by the player
     * @return True if it's a number, False if it's something else
     */
    public static boolean isNumber (String string) {
        try {
            Integer.parseInt(string);
            return true;
        } catch (NumberFormatException e) {
            logger.warn("Only numbers are accepted");
            System.err.println("Caractère(s) non accepté(s) : Veuillez entrer un nombre !");
            return false;
        }
    }

    /**
     * Check if the string in parameter have the same length with the secret code
     * @param string The text proposed by the player
     * @return True if it have the same length
     */
    public static boolean hasSameLength (String string, String game) {
        int secretCodeLength = getSecretCodeLength(game);
        if (string.length() == secretCodeLength)
            return true;
        else {
            logger.warn("Input length different from that expected (" + secretCodeLength + ")");
            System.err.println("Vous devez entrer un nombre à " + secretCodeLength + " chiffres !");
            return false;
        }
    }

    /**
     * Search if value is in table
     * @param table the table where it search the value
     * @param value the desired value
     * @return true if value is in table
     */
    public static boolean tableContains (int[] table, int value) {
        for (int i : table) {
            if (i == value)
                return true;
        }
        return false;
    }

    /**
     * Count the occurrence of one value in the table
     * @param table the table where it search all occurrences of the value
     * @param value the value whose occurrences we are looking for
     * @return the number of occurrences of the value
     */
    public static int countOccurrences(int[] table, int value){
        int count = 0;
        for (int i : table) {
            if (i == value) {
                count++;
            }
        }
        return count;
    }

    /**
     * Allows access to developer mode
     * @param paramUser the keyword for the developer mode
     * @return true if it's the correct keyword
     */
    public static boolean hasDevMode(String[] paramUser) {
        return paramUser.length == 2 && paramUser[1].equals("dev");
    }

    /**
     * Calculate the factorial for obtain the number of possibility
     * @param n The number of case
     * @return The number of possibility
     */
    private static int factorial(int n) {
        int factorial = 1;
        for(int i = 0; i < n; i++) {
            factorial *= (n - i);
        }
        return factorial;
    }

    /**
     * Calculate the number of possible combinations
     * @param n The size of the combination
     * @param k The number of different colors in the combination
     * @param j The number of repeated numbers
     * @return The number of possible combination
     */
    public static int numberPossibleCombination (int n, int k, int j) {
        int numberPossibleCombination;
        if ((j*factorial(n-k)) != 0)
            numberPossibleCombination = (factorial(n)/(j*factorial(n-k)));
        else
            numberPossibleCombination = factorial(n);

        return numberPossibleCombination;
    }
}
