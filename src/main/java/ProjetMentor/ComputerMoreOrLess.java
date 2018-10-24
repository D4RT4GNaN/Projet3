package ProjetMentor;

import Utils.Computer;
import Utils.Utils;

public class ComputerMoreOrLess extends Computer {
    private int maxValue;
    private int minValue;
    private int secretNumberLength;

    public ComputerMoreOrLess (int secretNumberLength) {
        this.secretNumberLength = secretNumberLength;
        init();
    }

    @Override
    protected void init() {
        maxValue = maxValue(secretNumberLength);
        minValue = minValue(secretNumberLength);
    }

    /**
     * Check the pattern of the last proposal and with min value and max value of his memory, it returned a proposal
     * @param proposal The last proposal (player in dual mode, itself in defense mode)
     * @param pattern The sentence returned by the game (More or Less)
     * @return The next proposal
     */
    @Override
    public int[] computerProcessing(int[] proposal, Object pattern) {
        if (proposal.length > 0) {
            int integerProposal = Utils.combinationToInt(proposal);

            switch ((String) pattern) {
                case "C'est plus":
                    minValue = integerProposal;
                    break;
                case "C'est moins":
                    maxValue = integerProposal;
                    break;
            }
        }

        return Utils.intToCombination((maxValue + minValue) / 2);
    }

    /**
     * Calculate the initial value for the maximum
     * @param length The length of the secret code
     * @return The maximum value
     */
    private int maxValue (int length) {
        int[] returnedValue = new int[length];
        for (int i = 0; i < length; i++)
            returnedValue[i] = 9;

        return Utils.combinationToInt(returnedValue);
    }

    /**
     * Calculate the initial value for the minimum
     * @param length The length of the secret code
     * @return The minimum value
     */
    private int minValue (int length) {
        int[] returnedValue = new int[length];
        for (int i = 0; i < length; i++)
            if (i == 0)
                returnedValue[i] = 1;
            else
                returnedValue[i] = 0;

        return Utils.combinationToInt(returnedValue);
    }
}
