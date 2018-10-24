package Main;

import ErrorHandler.ErrorHandler;
import Mastermind.Mastermind;
import SearchMoreOrLess.SearchMoreOrLess;
import ProjetMentor.MoreOrLess;
import Utils.Utils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Scanner;

class Menu {

    private Scanner sc = new Scanner(System.in);
    private String game;
    private String mode;
    private boolean dev;
    private Logger logger = LogManager.getLogger("main.java.Main.Menu");

    /**
     * Asks the user what game and mode they want
     */
    private void askGame() {
        System.out.println("\nChoisissez votre jeu :");
        System.out.println("1 - Plus ou moins");
        System.out.println("2 - Recherche +/-");
        System.out.println("3 - Mastermind");
        System.out.println("0 - Quitter");

        do {
            game = sc.next();
            exitProgram(game);
        }
        while (!selectNumber(game, "0123"));

        System.out.println("\nChoisissez votre mode jeu :");
        System.out.println("1 - Challenger");
        System.out.println("2 - Duel");
        System.out.println("3 - Défenseur");

        do {
            sc = new Scanner(System.in);
            String entryUser = sc.nextLine();
            String[] paramUser = entryUser.split(" ");
            mode = paramUser[0];
            dev = Utils.hasDevMode(paramUser);
        } while (!selectNumber(mode, "123"));

    }

    /**
     * Asking player to replay the same game, change game or exit program
     */
    private boolean askReplay() {
        String choice;

        System.out.println("\nVoulez vous rejouez ?");
        System.out.println("1 - Rejouer");
        System.out.println("2 - Retour au menu");
        System.out.println("0 - Quitter");

        do {
            choice = sc.next();
            exitProgram(choice);
        } while (!selectNumber(choice, "012"));

        switch (choice) {
            case "1":
                return true;
            case "2":
                return false;
        }

        return false;
    }

    /**
     * run the menu to choose the game mode
     */
    void runMenu() {
        System.out.println("Bienvenue sur le multi jeux");
        while (true) {
            askGame();
            int gameInt = Integer.parseInt(game);
            int intMode = Integer.parseInt(mode);
            do {
                switch (gameInt) {
                    case 1:
                        logger.info("More or less game started in " + (intMode == 1? "challenger mode" : intMode == 2? "dual mode" : "defense mode") + (dev? " and developer mode" : ""));
                        new MoreOrLess(intMode, dev);
                        break;
                    case 2:
                        logger.info("Search more or less game started in " + (intMode == 1? "challenger mode" : intMode == 2? "dual mode" : "defense mode") + (dev? " and developer mode" : ""));
                        new SearchMoreOrLess(intMode, dev);
                        break;
                    case 3:
                        logger.info("Mastermind game started in " + (intMode == 1? "challenger mode" : intMode == 2? "dual mode" : "defense mode") + (dev? " and developer mode" : ""));
                        new Mastermind(intMode, dev);
                        break;
                }
            } while (askReplay());
        }
    }

    /**
     * Stop the program when the player enter 0
     * @param input The player input
     */
    private void exitProgram (String input) {
        if (input.equals("0")) {
            System.out.println("Thank's For Playing !!");
            logger.info("Stopping program");
            System.exit(0);
        }
    }

    /**
     * Check if the player input is a number and if it contain in the list of possibility
     * @param input The player input
     * @param availableInput The available input
     * @return True if the player input is a number contained in the available input list
     */
    private boolean selectNumber (String input, String availableInput) {
        int availableInputLength = availableInput.length();
        boolean inputIsValid = false;
        for (int i = 0; i < availableInputLength; i++) {
            if (input.equals("" + availableInput.charAt(i)))
                inputIsValid = true;
        }
        if(!inputIsValid)
            ErrorHandler.badInputNumbersError(logger);

        return Utils.isNumber(input) && inputIsValid;
    }

}
