package main.java.Mastermind;

import main.java.utils;

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
                    endGame = compareResponse(playerProposal);
                } while(!endGame);
                break;
            case 2: // Dual
                break;
            case 3: // Defense
                Computer computer = new Computer();
                int[] computerProposal = new int[secretCombinationLength];
                present = -1;
                int loop = 0;

                System.out.println("Entrez une combinaison secrète !");
                secretCombination = askPlayer();

                while (true) {
                    computerProposal = computer.computerProcessingDefense(computerProposal, present, goodNumber);
                    loop++;
                    if (compareResponse(computerProposal) || loop == utils.getMaxTries("Mastermind")) {
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
        playerProposal = utils.intToCombination(Integer.parseInt(scanner));

        return playerProposal;
    }

    /**
     * Compare each digit of the number entered with each digit of the secret code
     * and display there are number present or in the right place
     * @param proposal The number entered by the player or the computer
     * @return True if the number entered is the same as the secret code
     */
    private boolean compareResponse (int[] proposal) {
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

        for (int i = 0; i < proposalLength; i++) {
            if (utils.tableContains(secretCombinationTemp,proposal[i])) {
                secretCombinationTemp = utils.removeOccurrences(secretCombinationTemp, proposal[i]);
                present++;
            }
        }

        if (present != 0) {
            outDisplay += present + " présent(s)";
            if (goodNumber != 0)
                outDisplay += ", " + goodNumber + " bien placé(s)";
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
