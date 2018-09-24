package main.java.SearchMoreOrLess;

import main.java.utils;

import java.util.Scanner;

public class SearchMoreOrLess {

    private int [] secretCombination;
    private Scanner sc = new Scanner(System.in);
    private String gameName = "SearchMoreOrLess";
    private int secretCodeLength;
    private String pattern;

    private void init () {
        secretCodeLength = utils.getSecretCodeLength(gameName);
        secretCombination = utils.randomCombination(secretCodeLength);
    }

    /**
     * Running method for search more or less game with 3 different game modes
     * @param mode The chosen game mode
     */
    public SearchMoreOrLess (int mode, boolean devMode) {
        boolean endGame = false;
        int numberTries = 0;
        int numberTriesMax = utils.getMaxTries(gameName);
        this.init();
        if (devMode)
            System.out.println("Combinaison secrète : " + utils.combinationToInt(secretCombination));
        switch (mode) {
            case 1:// challenger
                do {
                    int[] playerProposal = askPlayer();
                    endGame = compareResponse(playerProposal);
                    numberTries++;
                    if (numberTries == numberTriesMax) {
                        System.out.println("Désolé c'est perdu ! Le nombre secret était : " + utils.combinationToInt(secretCombination));
                        break;
                    }
                } while (!endGame);
                if (endGame)
                    System.out.println("Bravo !!! Le nombre secret était bien : " + utils.combinationToInt(secretCombination));
                break;
            case 2:// duel
                Computer computer = new Computer(gameName);
                while (true) {
                    int[] playerProposal = askPlayer();
                    if (compareResponse(playerProposal)) {
                        System.out.println("Bravo !!! Le nombre secret était bien : " + utils.combinationToInt(secretCombination));
                        break;
                    }
                    int[] computerProposal = computer.computerProcessing(playerProposal,pattern);
                    if (compareResponse(computerProposal)) {
                        System.out.println("Yahah !!! J'ai gagné, t'as perdu et le nombre secret était bien : " + utils.combinationToInt(secretCombination));
                        break;
                    }
                    computer.updateLimit(computerProposal, pattern);
                }
                break;
        }
    }

    /**
     * Asking player to enter a number
     * @return int table with each digit of the number entered by the player
     */
    private int[] askPlayer () {
        int[] playerProposal;
        String scanner;

        do {
            System.out.println("Donnez un nombre de " + secretCodeLength + " chiffres :");
            scanner = sc.next();
        } while (!(utils.isNumber(scanner) && utils.hasSameLength(scanner, gameName)));
        playerProposal = utils.intToCombination(Integer.parseInt(scanner));

        return playerProposal;
    }

    private void askComputer () {

    }

    /**
     * Compare each digit of the number entered by the player with each digit of the secret code
     * and display a pattern based on the differences and similarities
     * @param proposal The number entered by the player
     * @return True if the number entered by the player is the same as the secret code
     */
    private boolean compareResponse (int[] proposal) {
        int proposalLength = proposal.length;
        int goodNumber = 0;
        String outDisplay = "Proposition : " + utils.combinationToInt(proposal) + " -> Réponse : ";
        pattern = "";

        for (int i = 0; i < proposalLength; i++) {
            if (proposal[i] == secretCombination[i]){
                outDisplay += "=";
                pattern += "=";
                goodNumber++;
            } else if (proposal[i] > secretCombination[i]) {
                outDisplay += "-";
                pattern += "-";
            } else if (proposal[i] < secretCombination[i]) {
                outDisplay += "+";
                pattern += "+";
            }
        }
        System.out.println(outDisplay);

        return goodNumber == proposalLength;
    }

    public String getGameName() {
        return gameName;
    }
}
