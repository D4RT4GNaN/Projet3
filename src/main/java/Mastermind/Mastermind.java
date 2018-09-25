package main.java.Mastermind;

import main.java.utils;

import java.util.Scanner;

public class Mastermind {

    private int secretCombinationLength;
    private Scanner sc = new Scanner(System.in);

    private int[] secretCombination;

    private void init () {
        secretCombinationLength = utils.getSecretCodeLength("Mastermind");
        secretCombination = utils.randomCombination(secretCombinationLength);
    }

    public Mastermind (int mode, boolean dev) {
        init();
        if (dev)
            System.out.println("(Combinaison secrète : " + utils.combinationToInt(secretCombination) + ")");
        switch (mode) {
            case 1: // Challenger
                boolean endGame;
                do {
                    int[] playerProposal = askPlayer();
                    endGame = compareResponse(playerProposal);
                } while(!endGame);
                break;
            case 2: // Dual
                break;
            case 3: // Defense
                break;
        }
    }

    private int[] askPlayer () {
        int[] playerProposal;
        String scanner;

        do {
            System.out.println("Donnez un nombre de " + secretCombinationLength + " chiffres :");
            scanner = sc.next();
        } while (!(utils.isNumber(scanner) && utils.hasSameLength(scanner, "Mastermind")));
        playerProposal = utils.intToCombination(Integer.parseInt(scanner));

        return playerProposal;
    }

    private boolean compareResponse (int[] proposal) {
        int proposalLength = proposal.length;
        int present = 0;
        int goodNumber = 0;
        String outDisplay = "Proposition : " + utils.combinationToInt(proposal) + " -> Réponse : ";

        for (int i = 0; i < proposalLength; i++) {
            if (utils.tableContains(secretCombination, proposal[i])) {
                if (secretCombination[i] == proposal[i])
                    goodNumber++;
                else
                    present++;
            }
        }
        System.out.println(outDisplay + present + " présent(s), " + goodNumber + " bien placé(s)");

        return goodNumber == proposalLength;
    }
}
