package main.java.Mastermind;

import main.java.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Computer {
    int[] memory;
    List <Integer> sureNumber;

    public Computer () {
        init();
    }

    public void init () {
        int secretNumberLength = utils.getSecretCodeLength("Mastermind");
        memory = new int[secretNumberLength];
        sureNumber = new ArrayList<>();
    }

    public int[] computerProcessingDual (int[] proposal, int present, int goodNumber) {
        return new int[utils.getSecretCodeLength("Mastermind")];
    }

    /**
     *Analyze the previous proposal and construct a table of the numbers present in the secret code and make a proposal
     * @param proposal The previous proposal
     * @param present The amount of proposal's number in the secret code
     * @param goodNumber The amount of proposal's number which are in the same place as those of the secret code
     * @return The proposal of the computer
     */
    public int[] computerProcessingDefense (int[] proposal, int present, int goodNumber) {
        int[] computerProposal = proposal;

        if (goodNumber != 0 && sureNumber.size() < (utils.getSecretCodeLength("Mastermind"))) {
            for (int i = 0; i < goodNumber; i++) {
                sureNumber.add(computerProposal[0]);
            }
        }

        if (sureNumber.size() < (utils.getSecretCodeLength("Mastermind"))) {
            if (present != -1) {
                int newValue = computerProposal[0] + 1;
                for (int i = 0; i < utils.getSecretCodeLength("Mastermind"); i++) {
                    computerProposal[i] = newValue;
                }
            }
        } else {
            Collections.shuffle(sureNumber);
            for (int i = 0; i < sureNumber.size(); i++)
                computerProposal[i] = sureNumber.get(i);
        }

        return computerProposal;
    }
}

