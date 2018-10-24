package Mastermind;

import Utils.Utils;
import Utils.Computer;

import java.util.*;

class ComputerMastermind extends Computer {
    private List <Integer> sureNumber;
    private GeneticAlgorithm geneticAlgorithm;

    private int loop;
    private int secretCodeLength;

    ComputerMastermind() {
        init();
    }

    @Override
    protected void init () {
        sureNumber = new ArrayList<>();
        geneticAlgorithm = new GeneticAlgorithm();
        loop = 0;
        secretCodeLength = Utils.getSecretCodeLength("Mastermind");
    }

    /**
     * Analyze the previous proposal and construct a table of the numbers present in the secret code and make a proposal
     * @param proposal The previous proposal
     * @param pattern The pattern of black and white pegs returned with the previous proposal
     * @return The proposal of the computer
     */
    @Override
    public int[] computerProcessing(int[] proposal, Object pattern) {
        int[] computerProposal = proposal;
        int[] patternInArray = (int[]) pattern;
        int goodNumber = patternInArray[0];
        int present = patternInArray[1];

        if (goodNumber != 0 && sureNumber.size() < secretCodeLength) {
            for (int i = 0; i < goodNumber; i++) {
                sureNumber.add(computerProposal[0]);
            }
        }

        if (sureNumber.size() < secretCodeLength) {
            if (present != -1) {
                int newValue = computerProposal[0] + 1;
                for (int i = 0; i < secretCodeLength; i++) {
                    computerProposal[i] = newValue;
                }
            }
        } else {
            computerProposal = geneticAlgorithm.geneticAlgorithm(sureNumber, computerProposal, (int[])pattern, loop);
            loop++;
        }

        return computerProposal;
    }
}

