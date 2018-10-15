package Mastermind;

import Main.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Mastermind {

    private int secretCombinationLength;
    private Scanner sc = new Scanner(System.in);
    private int present;
    private int goodNumber;

    private int[] secretCombination;

    private void init () {
        secretCombinationLength = utils.getSecretCodeLength("Mastermind");
        secretCombination = utils.randomCombination(secretCombinationLength);
    }

    public Mastermind (int mode, boolean dev) {
        init();
        if (dev)
            System.out.println("(Combinaison secrète : " + utils.combinationToString(secretCombination) + ")");
        switch (mode) {
            case 1: // Challenger
                boolean endGame;
                do {
                    int[] playerProposal = askPlayer();
                    endGame = compareResponse(playerProposal, secretCombination);
                } while(!endGame);
                break;
            case 2: // Dual
                Computer computer = new Computer();
                System.out.println("Entrez une combinaison secrète !");
                int[] secretCombinationDual = askPlayer();
                int[] computerProposal = new int[secretCombinationLength];
                int presentComputer = -1;
                int goodNumberComputer = 0;


                while (true) {
                    System.out.print("\nJoueur : ");
                    int[] playerProposal = askPlayer();
                    if (compareResponse(playerProposal, secretCombination)) {
                        System.out.println("Joueur WIN");
                        break;
                    }

                    System.out.print("\nOrdinateur : ");
                    computerProposal = computer.computerProcessing(computerProposal, presentComputer, goodNumberComputer);
                    if (compareResponse(computerProposal, secretCombinationDual)) {
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
                int loop = 0;

                System.out.println("Entrez une combinaison secrète !");
                secretCombination = askPlayer();

                while (true) {
                    computerProposal = computer.computerProcessing(computerProposal, present, goodNumber);
                    loop++;
                    if (compareResponse(computerProposal, secretCombination) || loop == utils.getMaxTries("Mastermind")) {
                        if (loop == utils.getMaxTries("Mastermind")) {
                            System.out.println("Perdu le bon chiffre était : " + utils.combinationToString(secretCombination));
                            break;
                        }
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
        } while (!(utils.isNumber(scanner) && utils.hasSameLength(scanner, "Mastermind")));
        playerProposal = utils.stringToCombination(scanner);

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
        String outDisplay = "Proposition : " + utils.combinationToString(proposal) + " -> Réponse : ";
        int[] secretCombinationTemp = secretCombination.clone();

        for (int i = 0; i < proposalLength; i++) {
            if (utils.tableContains(secretCombination, proposal[i])) {
                if (secretCombination[i] == proposal[i]) {
                    goodNumber++;
                    secretCombinationTemp[i] = -1;
                }
            }
        }

        List<Integer> listTemp = new ArrayList<>();
        for (int item : proposal) {
            if (utils.tableContains(secretCombinationTemp,item) && !(listTemp.contains(item)))
                listTemp.add(item);
        }

        for (int item : listTemp)
            present += utils.countOccurrences(secretCombinationTemp, item);

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
