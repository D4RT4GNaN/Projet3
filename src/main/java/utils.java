package main.java;

import java.awt.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Properties;

public class utils {
    /**
     *Return a random int between min value and max value
     * @param min The minimum int you want
     * @param max The maximum int you want
     * @return A random int between min and max
     */
    public static int randomInt(int min, int max) {
        int randomInt = min + (int) (Math.random() * (max - min));
        return randomInt;
    }

    /**
     *Return a random int between min value and max value
     * @param min The minimum int you want
     * @param max The maximum int you want
     * @return A random int between min and max
     */
    public static int randomIntWithout(int min, int max, int[] withoutValue) {
        int randomInt;
        ArrayList withoutList = new ArrayList(Arrays.asList(withoutValue));
        do {
            randomInt = min + (int) (Math.random() * (max - min));
        } while (withoutList.contains(randomInt));
        return randomInt;
    }

    /**
     *Create randomly a combination with variable size
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
     *Convert the secret code from table to int cast
     * @param randomCombination The table of the secret code
     * @return The secret code casted into int
     */
    public static int combinationToInt (int[] randomCombination) {
        int combinationInt;
        int combinationLength = randomCombination.length;
        String combinationTemp = "";

        for (int i = 0; i < combinationLength; i++) {
            combinationTemp += Integer.toString(randomCombination[i]);
        }
        combinationInt = Integer.parseInt(combinationTemp);
        return combinationInt;
    }

    /**
     *Convert the secret code from table to String cast
     * @param randomCombination The table of the secret code
     * @return The secret code casted into String
     */
    public static String combinationToString (int[] randomCombination) {
        int combinationInt;
        int combinationLength = randomCombination.length;
        String combinationOut = "";

        for (int i = 0; i < combinationLength; i++) {
            combinationOut += Integer.toString(randomCombination[i]);
        }

        return combinationOut;
    }

    /**
     *Convert the secret code from int to table of int
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
     *Convert the secret code from table to Color cast
     * @param randomCombination The table of the secret code
     * @return The secret code casted into Color
     */
    public static Color[] combinationToColor (int[] randomCombination) {
        int combinationLength = randomCombination.length;
        Color[] combinationColor = new Color[combinationLength];
        for (int i = 0; i < combinationLength; i++) {
            switch (i) {
                case 0:
                    combinationColor[i] = Color.GRAY;
                    break;
                case 1:
                    combinationColor[i] = Color.BLUE;
                    break;
                case 2:
                    combinationColor[i] = Color.RED;
                    break;
                case 3:
                    combinationColor[i] = Color.GREEN;
                    break;
                case 4:
                    combinationColor[i] = Color.YELLOW;
                    break;
                case 5:
                    combinationColor[i] = Color.PINK;
                    break;
                case 6:
                    combinationColor[i] = Color.MAGENTA;
                    break;
                case 7:
                    combinationColor[i] = Color.ORANGE;
                    break;
                case 8:
                    combinationColor[i] = Color.CYAN;
                    break;
                case 9:
                    combinationColor[i] = Color.DARK_GRAY;
                    break;
            }
        }
        return combinationColor;
    }

    /**
     *Get the value of case number in the property's file
     * @param game The name of game you play
     * @return The number of case contained in the secret code
     */
    public static int getSecretCodeLength (String game) {
        Properties properties = new Properties();
        int secretCodeLength;

        try {
            FileInputStream config = new FileInputStream("src/main/resources/config.properties");
            properties.load(config);
            config.close();
        } catch (FileNotFoundException e) {
            System.err.println("Le fichier n'existe pas !");
        } catch (IOException e) {
            System.err.println("Impossible de lire ou d'écrire dans le fichier.");
        }

        secretCodeLength = Integer.parseInt(properties.getProperty("number-case-" + game));

        return secretCodeLength;
    }

    /**
     *Get the value of max tries in the property's file
     * @param game The name of game you play
     * @return The number tries you have to found the secret code
     */
    public static int getMaxTries (String game) {
        Properties properties = new Properties();
        int maxTries;

        try {
            FileInputStream config = new FileInputStream("src/main/resources/config.properties");
            properties.load(config);
            config.close();
        } catch (FileNotFoundException e) {
            System.err.println("Le fichier n'existe pas !");
        } catch (IOException e) {
            System.err.println("Impossible de lire ou d'écrire dans le fichier.");
        }

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
        int tableLength = table.length;
        for (int i = 0; i < tableLength; i++) {
            if (table[i] == value)
                return true;
        }
        return false;
    }

    /**
     * Search the occurence of one value in the table and remove it
     * @param table the table where it search all occurrences of the value
     * @param value the value whose occurrences we are looking for
     * @return the table without the occurrences found
     */
    public static int[] removeOccurrences(int[] table, int value){
        int[] tempTable = table;
        for (int i = 0; i < tempTable.length; i++) {
            if (tempTable[i] == value)
                tempTable[i] = -1;
        }
        return tempTable;
    }

    /**
     *Allows access to developer mode
     * @param paramUser the keyword for the developer mode
     * @return true if it's the correct keyword
     */
    public static boolean hasDevMode (String[] paramUser) {
        if (paramUser.length == 2 && paramUser[1].equals("dev"))
            return true;
        return false;
    }

}
