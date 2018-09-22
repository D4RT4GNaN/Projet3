package main.java;

import java.util.Scanner;

public class SearchMoreOrLess {

    private int [] secretCombination;
    private Scanner sc = new Scanner(System.in);
    private String gameName = "SearchMoreOrLess";

    private void init () {
        secretCombination = utils.randomCombination(utils.getSecretCodeLength(gameName));
    }

    public SearchMoreOrLess (int mode) {
        boolean endGame = false;
        int numberTries = 0;
        int numberTriesMax = utils.getMaxTries(gameName);
        this.init();
        switch (mode) {
            case 1:// challenger
                do {
                    int[] playerProposal = askPlayer();
                    endGame = compareResponse(playerProposal);
                    numberTries++;
                    if (numberTries == numberTriesMax)
                        break;
                } while (!endGame);
                break;
            case 2:// duel
                break;
            case 3:// defenseur
                break;
        }
    }

    private int[] askPlayer () {
        int[] playerProposal;
        String scanner;

        do {
            System.out.println("Donnez un chiffre :");
            scanner = sc.next();
        } while (!utils.isNumber(scanner));
        playerProposal = utils.intToCombination(Integer.parseInt(scanner));

        return playerProposal;
    }

    private void askComputer () {

    }

    private boolean compareResponse (int[] proposal) {
        int proposalLength = proposal.length;
        int goodNumber = 0;
        String outDisplay = "Proposition : " + utils.combinationToInt(proposal) + " -> Réponse : ";

        for (int i = 0; i < proposalLength; i++) {
            if (proposal[i] == secretCombination[i]){
                outDisplay += "=";
                goodNumber++;
            } else if (proposal[i] > secretCombination[i])
                outDisplay += "-";
            else if (proposal[i] < secretCombination[i])
                outDisplay += "+";
        }
        System.out.println(outDisplay);

        return goodNumber == proposalLength;
    }
}
