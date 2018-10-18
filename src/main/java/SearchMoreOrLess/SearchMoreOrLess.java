package SearchMoreOrLess;

import Main.utils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Scanner;

public class SearchMoreOrLess {

    private int [] secretCombination;
    private Scanner sc = new Scanner(System.in);
    private String gameName = "SearchMoreOrLess";
    private int secretCodeLength;
    private String pattern;
    private Logger logger = LogManager.getLogger("main.java.SearchMoreOrLess.SearchMoreOrLess");

    /**
     * Initialization method the secret number and the pattern
     */
    private void init () {
        secretCodeLength = utils.getSecretCodeLength(gameName);
        secretCombination = utils.randomCombination(secretCodeLength);
        for (int i = 0; i < secretCodeLength; i++) {
            pattern += "*";
        }
        logger.info("Creating the secret code");
    }

    /**
     * Running method for search more or less game with 3 different game modes
     * @param mode The chosen game mode
     */
    public SearchMoreOrLess (int mode, boolean devMode) {
        boolean endGame;
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
                        logger.info("Player don't found the secret code that was " + utils.combinationToInt(secretCombination));
                        System.out.println("Désolé c'est perdu ! Le nombre secret était : " + utils.combinationToInt(secretCombination));
                        break;
                    }
                } while (!endGame);
                if (endGame) {
                    logger.info("Player found the secret code in " + numberTries + " move(s)");
                    System.out.println("Bravo !!! Le nombre secret était bien : " + utils.combinationToInt(secretCombination));
                }
                break;

            case 2:// duel
                Computer computer = new Computer(gameName);
                while (true) {
                    numberTries++;
                    int[] playerProposal = askPlayer();
                    if (compareResponse(playerProposal)) {
                        logger.info("Player win and found the secret code in " + numberTries + " move(s)");
                        System.out.println("Bravo !!! Le nombre secret était bien : " + utils.combinationToInt(secretCombination));
                        break;
                    }
                    int[] computerProposal = computer.computerProcessing(playerProposal,pattern);
                    if (compareResponse(computerProposal)) {
                        logger.info("Computer win and found the secret code in " + numberTries + " move(s)");
                        System.out.println("Yahah !!! J'ai gagné, t'as perdu et le nombre secret était bien : " + utils.combinationToInt(secretCombination));
                        break;
                    }
                    computer.updateLimit(computerProposal, pattern);
                }
                break;

            case 3://défenseur
                logger.info("Initialized computer");
                int computerLoop = 0;
                computer = new Computer(gameName);
                secretCombination = askPlayer();
                logger.info("Player set the secret code to " + secretCombination);
                int[] computerProposal = null;

                logger.info("Computer starting research");
                while (true) {
                    computerLoop++;
                    computerProposal = computer.computerProcessing(computerProposal,pattern);
                    if (compareResponse(computerProposal)) {
                        logger.info("Computer found the secret code in " + computerLoop + " move(s)");
                        System.out.println("Yahah !!! J'ai gagné en : " + computerLoop + " coup(s) et le nombre secret était bien : " + utils.combinationToInt(secretCombination));
                        break;
                    }
                }
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

    /**
     * Compare each digit of the number entered by the player with each digit of the secret code
     * and display a pattern based on the differences and similarities
     * @param proposal The number entered by the player
     * @return True if the number entered by the player is the same as the secret code
     */
    private boolean compareResponse (int[] proposal) {
        int proposalLength = proposal.length;
        int goodNumber = 0;
        StringBuilder outDisplay = new StringBuilder("Proposition : " + utils.combinationToInt(proposal) + " -> Réponse : ");
        pattern = "";

        for (int i = 0; i < proposalLength; i++) {
            if (proposal[i] == secretCombination[i]){
                outDisplay.append("=");
                pattern += "=";
                goodNumber++;
            } else if (proposal[i] > secretCombination[i]) {
                outDisplay.append("-");
                pattern += "-";
            } else if (proposal[i] < secretCombination[i]) {
                outDisplay.append("+");
                pattern += "+";
            }
        }
        System.out.println(outDisplay);

        return goodNumber == proposalLength;
    }
}
