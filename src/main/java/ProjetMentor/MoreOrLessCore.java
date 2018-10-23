package ProjetMentor;

import Utils.Utils;
import org.apache.logging.log4j.Logger;

import java.util.Scanner;

public class MoreOrLessCore {
    private int secretNumber;
    private Logger logger;
    private int triesMax;
    private int secretNumberLength;
    private Scanner sc = new Scanner(System.in);
    private String pattern;

    public MoreOrLessCore (int secretNumber, int secretNumberLength, Logger logger, int triesMax, String pattern) {
        this.secretNumber = secretNumber;
        this.logger = logger;
        this.triesMax = triesMax;
        this.secretNumberLength = secretNumberLength;
        this.pattern = pattern;
    }

    public void challenger (boolean dev) {
        int lapCounter = 0;
        int userProposal;

        System.out.println("Bienvenue sur le jeu du plus ou moins");
        if (dev)
            System.out.println("(nombre secret : " + secretNumber + ")");

        System.out.println("A vous de jouez (" + secretNumberLength + ") :");

        while (true) {
            lapCounter++;
            userProposal = askNumber();
            if (compareNumber(userProposal, secretNumber, lapCounter)) {
                logger.info("Player found the secret code in " + lapCounter + " move(s)");
                break;
            }
        }
    }

    public void dual (boolean dev) {

    }

    public void defense () {
        secretNumber = askNumber();
        ComputerMoreOrLess computer = new ComputerMoreOrLess(Utils.intToCombination(secretNumber).length);
        int[] proposal = new int[0];
        int lapCounter = 0;

        while (true) {
            lapCounter++;
            proposal = computer.computerProcessing(proposal, pattern);
            System.out.println(Utils.combinationToString(proposal));
            if(compareNumber(Utils.combinationToInt(proposal), secretNumber, lapCounter))
                break;
        }
    }

    /**
     * Asks the user to enter a number
     * @return the number entered by the user
     */
    private int askNumber () {
        System.out.println("Donnez un chiffre : ");
        return sc.nextInt();
    }

    /**
     * Compare the number entered by the user and the secret number and display if it's more, less or equal
     * @param userNumber the number entered by the user
     * @param secretNumber the secret random number
     * @return boolean meaning the end of game or not
     */
    public boolean compareNumber (int userNumber, int secretNumber, int lapCounter) {
        boolean endGame = false;
        if (lapCounter == triesMax) {
            System.out.println("Perdu!! le nombre secret était " + secretNumber);
            endGame = true;
        } else if (userNumber == secretNumber) {
            System.out.println("Bravo! Vous avez trouvez le nombre secret en seulement " + lapCounter + " tours");
            endGame = true;
        } else if (userNumber < secretNumber) {
            pattern = "C'est plus";
            System.out.println("C'est plus");
        } else {
            pattern = "C'est moins";
            System.out.println("C'est moins");
        }

        return endGame;
    }
}
