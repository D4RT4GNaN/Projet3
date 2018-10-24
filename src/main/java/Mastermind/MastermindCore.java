package Mastermind;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.apache.logging.log4j.Logger;

public class MastermindCore {

    private int present;
    private int goodNumber;
    private int secretCombinationLength;
    private int maxTries;

    private int[] secretCombination;

    private Scanner sc = new Scanner(System.in);
    private Logger logger;

    public MastermindCore (int[] secretCombination, int secretCombinationLength, int maxTries, Logger logger) {
        this.secretCombination = secretCombination;
        this.secretCombinationLength = secretCombinationLength;
        this.maxTries = maxTries;
        this.logger = logger;
    }

    /**
     * Challenger mode : The player try to found the secret code created by the computer
     * @param dev if true : allow developer mode
     */
    public void challenger (boolean dev) {
        int loop = 0;

        if (dev)
            System.out.println("(Combinaison secrète : " + utilsMastermind.combinationToString(secretCombination) + ")");

        while (true) {
            loop++;
            int[] playerProposal = askPlayer();
            if (compareResponse(playerProposal, secretCombination)) {
                logger.info("Player found the secret code in " + loop + " move(s)");
                break;
            }
        }
    }

    /**
     * Dual mode : The player and the computer try to found the secret code of the other
     * @param dev if true : allow developer mode
     */
    public void dual (boolean dev) {
        int loop = 0;

        ComputerMastermind computer = new ComputerMastermind();

        System.out.println("Entrez une combinaison secrète !");
        int[] secretCombinationDual = askPlayer();
        logger.info("Player set his secret code to " + secretCombinationDual);
        int[] computerProposal = new int[secretCombinationLength];

        int presentComputer = -1;
        int goodNumberComputer = 0;

        if (dev)
            System.out.println("(Combinaison secrète : " + utilsMastermind.combinationToString(secretCombination) + ")");

        while (true) {
            loop++;
            System.out.print("\nJoueur : ");
            int[] playerProposal = askPlayer();
            if(endGame(playerProposal, secretCombination, loop, "Joueur"))
                break;

            System.out.print("\nOrdinateur : ");
            computerProposal = computer.computerProcessing(computerProposal, utilsMastermind.intArrayToObject(goodNumberComputer, presentComputer));
            if(endGame(computerProposal, secretCombinationDual, loop, "Ordinateur"))
                break;

            presentComputer = present;
            goodNumberComputer = goodNumber;
        }
    }

    /**
     * Display the end game sentence and break the game
     * if the player or the computer found the secret code of the other
     * @param proposal The proposal of the player or the proposal of the computer
     * @param secretCode The secret code of the player or the secret code of the computer
     * @param loop the number of attempts
     * @param player Player or Computer
     * @return True if the player or the computer found the secret code of the other
     */
    private boolean endGame (int[] proposal, int[] secretCode, int loop, String player) {
        if (compareResponse(proposal, secretCode)) {
            logger.info("" + player + " win in " + loop + " move(s)");
            System.out.println("" + player + " WIN");
            return true;
        }
        return false;
    }

    /**
     * Defense mode : computer try to found your secret code
     */
    public void defense () {
        ComputerMastermind computer = new ComputerMastermind();
        int[] computerProposal = new int[secretCombinationLength];
        present = -1;
        int loop = 0;

        System.out.println("Entrez une combinaison secrète !");
        int[] secretCombination = askPlayer();
        logger.info("Player set the secret code to " + utilsMastermind.combinationToString(secretCombination));

        while (true) {
            computerProposal = computer.computerProcessing(computerProposal, utilsMastermind.intArrayToObject(goodNumber, present));
            loop++;
            if (compareResponse(computerProposal, secretCombination) || loop == maxTries) {
                if (loop == maxTries) {
                    System.out.println("Perdu le bon chiffre était : " + utilsMastermind.combinationToString(secretCombination));
                    break;
                }
                logger.info("ComputerMastermind found the secret code in " + loop + " move(s)");
                System.out.println("En seulement " + loop + " tours !!");
                break;
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
            System.out.println("Donnez un nombre de " + secretCombinationLength + " chiffres :");
            scanner = sc.next();
        } while (!(utilsMastermind.isNumber(scanner) && utilsMastermind.hasSameLength(scanner, "Mastermind")));
        playerProposal = utilsMastermind.stringToCombination(scanner);

        return playerProposal;
    }

    /**
     * Compare each digit of the number entered with each digit of the secret code
     * and display there are number present or in the right place
     * @param proposal The number entered by the player or the computer
     * @param secretCombination The secret code
     * @return True if the number entered is the same as the secret code
     */
    private boolean compareResponse (int[] proposal, int[] secretCombination) {
        int proposalLength = proposal.length;
        int[] secretCombinationTemp = secretCombination.clone();
        String outDisplay = "Proposition : " + utilsMastermind.combinationToString(proposal) + " -> Réponse : ";

        present = 0;
        goodNumber = 0;

        for (int i = 0; i < proposalLength; i++) {
            if (utilsMastermind.tableContains(secretCombination, proposal[i]) && secretCombination[i] == proposal[i]) {
                goodNumber++;
                secretCombinationTemp[i] = -1;
            }
        }

        List<Integer> listTemp = new ArrayList<>();
        for (int item : proposal) {
            if (utilsMastermind.tableContains(secretCombinationTemp,item) && !(listTemp.contains(item)))
                listTemp.add(item);
        }

        for (int item : listTemp)
            present += utilsMastermind.countOccurrences(secretCombinationTemp, item);

        if (present != 0 && goodNumber != 0) {
            outDisplay += present + " présent(s)" + ", " + goodNumber + " bien placé(s)";
        } else if (goodNumber != 0) {
            outDisplay += goodNumber + " bien placé(s)";
            if (goodNumber == proposalLength)
                outDisplay += "\nBravo!!! Vous avez gagné... le droit de recommencer !!!";
        } else
            outDisplay += present + " présent !";
        System.out.println(outDisplay);

        return goodNumber == proposalLength;
    }
}
