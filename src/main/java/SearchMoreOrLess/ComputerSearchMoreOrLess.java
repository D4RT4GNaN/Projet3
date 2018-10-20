package SearchMoreOrLess;

import Utils.Utils;
import Utils.Computer;

public class ComputerSearchMoreOrLess extends Computer {
    private int[] min;
    private int[] max;
    private int[] computerProposal;

    public ComputerSearchMoreOrLess() {
        init();
    }

    @Override
    protected void init () {
        int secretCodeLength = Utils.getSecretCodeLength("SearchMoreOrLess");
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
    @Override
    public int[] computerProcessing (int[] playerProposal, Object playerPattern) {
        String pattern = (String) playerPattern;
        updateLimit(playerProposal, pattern);
        String[] splicedPlayerPattern = pattern.split("");

        for (int i = 0; i < Utils.getSecretCodeLength("SearchMoreOrLess"); i++) {
            if (splicedPlayerPattern[i].equals("="))
                computerProposal[i] = playerProposal[i];
            else
                computerProposal[i] = (min[i] + max[i]) / 2;
        }

        return computerProposal;
    }
}
