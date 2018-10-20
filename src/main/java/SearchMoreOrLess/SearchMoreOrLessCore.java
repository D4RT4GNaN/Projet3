package SearchMoreOrLess;

import org.apache.logging.log4j.Logger;

import Utils.Utils;

import java.util.Scanner;

public class SearchMoreOrLessCore {

    private Scanner sc = new Scanner(System.in);
    private int secretCodeLength;
    private int[] secretCombination;
    private int numberTriesMax;
    private Logger logger;
    private String pattern;
    private ComputerSearchMoreOrLess computer;

    public SearchMoreOrLessCore (int secretCodeLength, int[] secretCombination, int numberTriesMax, Logger logger, String pattern) {
        this.secretCodeLength = secretCodeLength;
        this.secretCombination = secretCombination;
        this.numberTriesMax = numberTriesMax;
        this.logger = logger;
        this.pattern = pattern;
        computer = new ComputerSearchMoreOrLess();
    }

    /**
     * Challenger mode : The player try to found the secret code created by the computer
     */
    public void challenger (boolean dev) {
        boolean endGame;
        int numberTries = 0;

        if (dev)
            System.out.println("Combinaison secrète : " + Utils.combinationToInt(secretCombination));

        do {
            int[] playerProposal = askPlayer();
            endGame = compareResponse(playerProposal);
            numberTries++;
            if (numberTries == numberTriesMax) {
                logger.info("Player don't found the secret code that was " + Utils.combinationToInt(secretCombination));
                System.out.println("Désolé c'est perdu ! Le nombre secret était : " + Utils.combinationToInt(secretCombination));
                break;
            }
        } while (!endGame);
        if (endGame) {
            logger.info("Player found the secret code in " + numberTries + " move(s)");
            System.out.println("Bravo !!! Le nombre secret était bien : " + Utils.combinationToInt(secretCombination));
        }
    }

    /**
     * Dual mode : The player and the computer try to found the same secret code
     */
    public void dual (boolean dev) {
        if (dev)
            System.out.println("Combinaison secrète : " + Utils.combinationToInt(secretCombination));

        int numberTries = 0;

        while (true) {
            numberTries++;

            int[] playerProposal = askPlayer();
            if (endGame(playerProposal, numberTries, "Player"))
                break;

            int[] computerProposal = computer.computerProcessing(playerProposal,pattern);
            if (endGame(computerProposal, numberTries, "Computer"))
                break;

            computer.updateLimit(computerProposal, pattern);
        }
    }

    /**
     * Display the end game sentence and break the program
     * @param proposal The proposal (player and computer)
     * @param numberTries The number of attempts
     * @param player Player or Computer
     * @return True if Player or Computer found the secret code
     */
    private boolean endGame (int[] proposal, int numberTries, String player) {
        if (compareResponse(proposal)) {
            logger.info("" + player + " win and found the secret code in " + numberTries + " move(s)");
            System.out.println("Yahah !!! J'ai gagné en " + numberTries + " coups, t'as perdu et le nombre secret était bien : " + Utils.combinationToInt(secretCombination));
            return true;
        }
        return false;
    }

    /**
     * Defense mode : computer try to found your secret code
     */
    public void defense () {
        logger.info("Initialized computer");
        int computerLoop = 0;

        int[] secretCombination = askPlayer();
        logger.info("Player set the secret code to " + Utils.combinationToString(secretCombination));

        int[] computerProposal = null;
        logger.info("ComputerSearchMoreOrLess starting research");

        while (true) {
            computerLoop++;
            computerProposal = computer.computerProcessing(computerProposal,pattern);
            if (endGame(computerProposal, computerLoop, "Computer"))
                break;
        }
    }

    /**
     * Asking player to enter a number
     * @return int table with each digit of the number entered by the player
     */
    private int[] askPlayer () {
        String scanner;

        do {
            System.out.println("Donnez un nombre de " + secretCodeLength + " chiffres :");
            scanner = sc.next();
        } while (!(Utils.isNumber(scanner) && Utils.hasSameLength(scanner, "SearchMoreOrLess")));

        return Utils.intToCombination(Integer.parseInt(scanner));
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
        StringBuilder outDisplay = new StringBuilder("Proposition : " + Utils.combinationToInt(proposal) + " -> Réponse : ");
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
