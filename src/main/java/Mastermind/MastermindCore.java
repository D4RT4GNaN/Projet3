package Mastermind;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import Utils.Utils;
import org.apache.logging.log4j.Logger;

public class MastermindCore {

    private int present;
    private int goodNumber;
    private int secretCombinationLength;
    private int maxTries;

    private Scanner sc = new Scanner(System.in);

    public MastermindCore (int secretCombinationLength, int maxTries) {
        this.secretCombinationLength = secretCombinationLength;
        this.maxTries = maxTries;
    }

    public void challenger (int[] secretCombination, boolean dev, Logger logger) {
        int loop = 0;

        if (dev)
            System.out.println("(Combinaison secrète : " + Utils.combinationToString(secretCombination) + ")");

        while (true) {
            loop++;
            int[] playerProposal = askPlayer();
            if (compareResponse(playerProposal, secretCombination)) {
                logger.info("Player found the secret code in " + loop + " move(s)");
                break;
            }
        }
    }

    public void dual (int[] secretCombination, boolean dev, Logger logger) {
        int loop = 0;

        ComputerMastermind computer = new ComputerMastermind();

        System.out.println("Entrez une combinaison secrète !");
        int[] secretCombinationDual = askPlayer();
        logger.info("Player set his secret code to " + secretCombinationDual);
        int[] computerProposal = new int[secretCombinationLength];

        int presentComputer = -1;
        int goodNumberComputer = 0;

        if (dev)
            System.out.println("(Combinaison secrète : " + Utils.combinationToString(secretCombination) + ")");

        while (true) {
            loop++;
            System.out.print("\nJoueur : ");
            int[] playerProposal = askPlayer();
            if(endGame(playerProposal, secretCombination, logger, loop, "Joueur"))
                break;

            System.out.print("\nOrdinateur : ");
            computerProposal = computer.computerProcessing(computerProposal, Utils.intArrayToObject(goodNumberComputer, presentComputer));
            if(endGame(computerProposal, secretCombinationDual, logger, loop, "Ordinateur"))
                break;

            presentComputer = present;
            goodNumberComputer = goodNumber;
        }
    }

    private boolean endGame (int[] proposal, int[] secretCode, Logger logger, int loop, String player) {
        if (compareResponse(proposal, secretCode)) {
            logger.info("" + player + " win in " + loop + " move(s)");
            System.out.println("" + player + " WIN");
            return true;
        }
        return false;
    }

    public void defense (Logger logger) {
        ComputerMastermind computer = new ComputerMastermind();
        int[] computerProposal = new int[secretCombinationLength];
        present = -1;
        int loop = 0;

        System.out.println("Entrez une combinaison secrète !");
        int[] secretCombination = askPlayer();
        logger.info("Player set the secret code to " + Utils.combinationToString(secretCombination));

        while (true) {
            computerProposal = computer.computerProcessing(computerProposal, Utils.intArrayToObject(goodNumber, present));
            loop++;
            if (compareResponse(computerProposal, secretCombination) || loop == maxTries) {
                if (loop == maxTries) {
                    System.out.println("Perdu le bon chiffre était : " + Utils.combinationToString(secretCombination));
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
        } while (!(Utils.isNumber(scanner) && Utils.hasSameLength(scanner, "Mastermind")));
        playerProposal = Utils.stringToCombination(scanner);

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
        String outDisplay = "Proposition : " + Utils.combinationToString(proposal) + " -> Réponse : ";

        present = 0;
        goodNumber = 0;

        for (int i = 0; i < proposalLength; i++) {
            if (Utils.tableContains(secretCombination, proposal[i]) && secretCombination[i] == proposal[i]) {
                goodNumber++;
                secretCombinationTemp[i] = -1;
            }
        }

        List<Integer> listTemp = new ArrayList<>();
        for (int item : proposal) {
            if (Utils.tableContains(secretCombinationTemp,item) && !(listTemp.contains(item)))
                listTemp.add(item);
        }

        for (int item : listTemp)
            present += Utils.countOccurrences(secretCombinationTemp, item);

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
