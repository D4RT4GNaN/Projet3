package SearchMoreOrLess;

import Main.utils;

public class Computer {
    private int[] min;
    private int[] max;
    private int[] computerProposal;

    public Computer (String gameName) {
        init(gameName);
    }

    /**
     * Initialization method for the limit of each digit
     * @param gameName The name of the game
     */
    public void init (String gameName) {
        int secretCodeLength = utils.getSecretCodeLength(gameName);
        computerProposal = new int[secretCodeLength];
        min = new int[secretCodeLength];
        max = new int[secretCodeLength];
        for (int i = 0; i < secretCodeLength; i++) {
            min[i] = 0;
            max[i] = 9;
        }
    }

    /**
     * Update the max and min limit to decreased the scale of possibility
     * @param playerProposal The number entered by the player
     * @param playerPattern The pattern returned by the program and based on the player's proposal
     */
    public void updateLimit (int[] playerProposal, String playerPattern) {
        String[] splitedPlayerPattern = playerPattern.split("");
        for (int i = 0; i < splitedPlayerPattern.length; i++) {
            if (splitedPlayerPattern[i].equals("+") && min[i] < playerProposal[i])
                min[i] = playerProposal[i];
            else if (splitedPlayerPattern[i].equals("-")  && max[i] > playerProposal[i])
                max[i] = playerProposal[i];
            else if (splitedPlayerPattern[i].equals("="))
                computerProposal[i] = playerProposal[i];
        }
    }

    /**
     *Create a number based on the player's proposal and the limit of each digit
     * @param playerProposal The number entered by the player
     * @param playerPattern The pattern returned by the program and based on the player's proposal
     * @return The number proposed by the computer
     */
    public int[] computerProcessing (int[] playerProposal, String playerPattern) {
        updateLimit(playerProposal, playerPattern);
        String[] splitedPlayerPattern = playerPattern.split("");

        for (int i = 0; i < utils.getSecretCodeLength("SearchMoreOrLess"); i++) {
            if (splitedPlayerPattern[i].equals("="))
                computerProposal[i] = playerProposal[i];
            else
                computerProposal[i] = (min[i] + max[i]) / 2;
        }

        return computerProposal;
    }
}
