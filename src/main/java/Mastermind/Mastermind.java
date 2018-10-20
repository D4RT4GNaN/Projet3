package Mastermind;

import Utils.Utils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Mastermind {

    private int secretCombinationLength;
    private Scanner sc = new Scanner(System.in);
    private int present;
    private int goodNumber;
    private int maxTries;
    private Logger logger = LogManager.getLogger("main.java.Mastermind.Mastermind");
    private int[] secretCombination;

    private void init () {
        maxTries = Utils.getMaxTries("Mastermind");
        secretCombinationLength = Utils.getSecretCodeLength("Mastermind");
        secretCombination = Utils.randomCombination(secretCombinationLength);
        logger.info("Creating the secret code");
    }

    public Mastermind (int mode, boolean dev) {
        init();
        int loop;
        if (dev)
            System.out.println("(Combinaison secrète : " + Utils.combinationToString(secretCombination) + ")");
        switch (mode) {
            case 1: // Challenger
                loop = 0;
                boolean endGame;
                do {
                    loop++;
                    int[] playerProposal = askPlayer();
                    endGame = compareResponse(playerProposal, secretCombination);
                    if (endGame)
                        logger.info("Player found the secret code in " + loop + " move(s)");
                } while(!endGame);
                break;
            case 2: // Dual
                Computer computer = new Computer();
                System.out.println("Entrez une combinaison secrète !");
                int[] secretCombinationDual = askPlayer();
                logger.info("Player set his secret code to " + secretCombinationDual);
                int[] computerProposal = new int[secretCombinationLength];
                int presentComputer = -1;
                int goodNumberComputer = 0;
                loop = 0;


                while (true) {
                    loop++;
                    System.out.print("\nJoueur : ");
                    int[] playerProposal = askPlayer();
                    if (compareResponse(playerProposal, secretCombination)) {
                        logger.info("Player win in " + loop + " move(s)");
                        System.out.println("Joueur WIN");
                        break;
                    }

                    System.out.print("\nOrdinateur : ");
                    computerProposal = computer.computerProcessing(computerProposal, presentComputer, goodNumberComputer);
                    if (compareResponse(computerProposal, secretCombinationDual)) {
                        logger.info("Computer win in " + loop + " move(s)");
                        System.out.println("Ordinateur WIN");
                        break;
                    }
                    presentComputer = present;
                    goodNumberComputer = goodNumber;
                }
                break;
            case 3: // Defense
                computer = new Computer();
                computerProposal = new int[secretCombinationLength];
                present = -1;
                loop = 0;

                System.out.println("Entrez une combinaison secrète !");
                secretCombination = askPlayer();
                logger.info("Player set the secret code to " + Utils.combinationToString(secretCombination));

                while (true) {
                    computerProposal = computer.computerProcessing(computerProposal, present, goodNumber);
                    loop++;
                    if (compareResponse(computerProposal, secretCombination) || loop == maxTries) {
                        if (loop == maxTries) {
                            System.out.println("Perdu le bon chiffre était : " + Utils.combinationToString(secretCombination));
                            break;
                        }
                        logger.info("Computer found the secret code in " + loop + " move(s)");
                        System.out.println("En seulement " + loop + " tours !!");
                        break;
                    }
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
        present = 0;
        goodNumber = 0;
        String outDisplay = "Proposition : " + Utils.combinationToString(proposal) + " -> Réponse : ";
        int[] secretCombinationTemp = secretCombination.clone();

        for (int i = 0; i < proposalLength; i++) {
            if (Utils.tableContains(secretCombination, proposal[i])) {
                if (secretCombination[i] == proposal[i]) {
                    goodNumber++;
                    secretCombinationTemp[i] = -1;
                }
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
