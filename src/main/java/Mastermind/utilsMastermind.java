package Mastermind;

import Utils.Utils;

import java.util.Properties;

public class utilsMastermind extends Utils {
    /**
     * Convert the secret code from string to table of int
     * @param randomCombination The string value of the secret code
     * @return The secret code spliced into table of int
     */
    public static int[] stringToCombination (String randomCombination) {
        int randomCombinationLength = randomCombination.length();
        int[] combinationTable = new int[randomCombinationLength];

        for (int i = 0; i < randomCombinationLength; i++)
            combinationTable[i] = Integer.parseInt("" + randomCombination.charAt(i));

        return combinationTable;
    }

    /**
     * Create int table converted in object
     * @param goodNumber The number of black pegs
     * @param present The number of white pegs
     * @return The int table casted in object
     */
    public static Object intArrayToObject (int goodNumber, int present) {
        return new int[]{goodNumber, present};
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
     * Calculate the factorial for obtain the number of possibility
     * @param caseLength The number of case
     * @return The number of possibility
     */
    private static int factorial(int caseLength) {
        int factorial = 1;
        for(int i = 0; i < caseLength; i++) {
            factorial *= (caseLength - i);
        }
        return factorial;
    }

    /**
     * Calculate the number of possible combinations
     * @param caseLength The size of the combination
     * @param numberColors The number of different colors in the combination
     * @param countRepeatedNumber The number of repeated numbers
     * @return The number of possible combination
     */
    public static int numberPossibleCombination (int caseLength, int numberColors, int countRepeatedNumber) {
        int numberPossibleCombination;
        if ((countRepeatedNumber * factorial(caseLength - numberColors)) != 0)
            numberPossibleCombination = (factorial(caseLength) / (countRepeatedNumber * factorial(caseLength - numberColors)));
        else
            numberPossibleCombination = factorial(caseLength);

        return numberPossibleCombination;
    }

    /**
     * Get the number of colours available for the secret code defined in the config file
     * @return the number of colours available for the secret code
     */
    public static int getNumberColours () {
        Properties properties = loadConfigFile();
        logger.info("Recovered the number of colours available for the secret code");

        return Integer.parseInt(properties.getProperty("number-colours-Mastermind"));
    }
}
