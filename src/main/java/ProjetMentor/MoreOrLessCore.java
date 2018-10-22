package ProjetMentor;

import org.apache.logging.log4j.Logger;

import java.util.Scanner;

public class MoreOrLessCore {
    private int secretNumber;
    private Logger logger;
    private int triesMax;
    private int secretNumberLength;
    private Scanner sc = new Scanner(System.in);

    public MoreOrLessCore (int secretNumber, int secretNumberLength, Logger logger, int triesMax) {
        this.secretNumber = secretNumber;
        this.logger = logger;
        this.triesMax = triesMax;
        this.secretNumberLength = secretNumberLength;
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
            if (compareNumber(userProposal, secretNumber, lapCounter, triesMax)) {
                logger.info("Player found the secret code in " + lapCounter + " move(s)");
                break;
            }
        }
    }

    public void dual (boolean dev) {

    }

    public void defense () {

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
     *Compare the number entered by the user and the secret number and display if it's more, less or equal
     * @param userNumber the number entered by the user
     * @param secretNumber the secret random number
     * @return boolean meaning the end of game or not
     */
    public boolean compareNumber (int userNumber, int secretNumber, int lapCounter, int triesMax) {
        boolean endGame = false;
        if (lapCounter == triesMax) {
            System.out.println("Perdu!! le nombre secret était " + secretNumber);
            endGame = true;
        } else if (userNumber == secretNumber) {
            System.out.println("Bravo! Vous avez trouvez le nombre secret en seulement " + lapCounter + " tours");
            endGame = true;
        } else if (userNumber < secretNumber)
            System.out.println("C'est plus");
        else
            System.out.println("C'est moins");
        return endGame;
    }
}
