package main.java;

import java.awt.*;

public class utils {
    /**
     *
     * @param min
     * @param max
     * @return
     */
    public int randomInt(int min, int max) {
        int randomInt = min + (int) (Math.random() * (max - min));
        return randomInt;
    }

    /**
     *
     * @param combinationLength
     * @return
     */
    public int[] randomCombination(int combinationLength) {
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
     *
     * @param randomCombination
     * @return
     */
    public int combinationToInt (int[] randomCombination) {
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
     *
     * @param randomCombination
     * @return
     */
    public Color[] combinationToColor (int[] randomCombination) {
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
}
