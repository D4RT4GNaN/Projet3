package SearchMoreOrLess;

import Utils.Utils;

public class utilsSearchMoreOrLess extends Utils {
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
}
