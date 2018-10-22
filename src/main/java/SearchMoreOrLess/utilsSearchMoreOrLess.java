package SearchMoreOrLess;

import Utils.Utils;

public class utilsSearchMoreOrLess extends Utils {
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
