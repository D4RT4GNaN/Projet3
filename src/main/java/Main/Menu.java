package Main;

import Mastermind.Mastermind;
import SearchMoreOrLess.SearchMoreOrLess;
import ProjetMentor.MoreOrLess;
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
            if (!game.equals("1") && !game.equals("2") && !game.equals("3") && !game.equals("0")) {
                System.err.println("Choisissez un des chiffres de la liste !");
                logger.warn("Bad input numbers : numbers must be in list");
            }
            if (game.equals("0")) {
                System.out.println("Thank's For Playing !!");
                logger.info("Stopping program");
                System.exit(0);
            }
        }
        while (!(utils.isNumber(game) && (game.equals("1") || game.equals("2") || game.equals("3") || game.equals("0"))));

        System.out.println("\nChoisissez votre mode jeu :");
        System.out.println("1 - Challenger");
        System.out.println("2 - Duel");
        System.out.println("3 - Défenseur");
        do {
            sc = new Scanner(System.in);
            String entryUser = sc.nextLine();
            String[] paramUser = entryUser.split(" ");
            mode = paramUser[0];
            dev = utils.hasDevMode(paramUser);
            if (!mode.contains("1") && !mode.contains("2") && !mode.contains("3")) {
                System.err.println("Choisissez un des chiffres de la liste !");
                logger.warn("Bad input numbers : numbers must be in list");
            }
        } while (!(utils.isNumber(mode) && (mode.contains("1") || mode.contains("2") || mode.contains("3"))));

    }

    /**
     * Asking player to replay the same game
     */
    private boolean askReplay() {
        String choice;

        System.out.println("\nVoulez vous rejouez ?");
        System.out.println("1 - Rejouer");
        System.out.println("2 - Retour au menu");
        System.out.println("0 - Quitter");
        do {
            choice = sc.next();
            if (!choice.equals("1") && !choice.equals("2") && !choice.equals("0")) {
                System.err.println("Choisissez un des chiffres de la liste !");
                logger.warn("Bad input numbers : numbers must be in list");
            }
            if (choice.equals("0")) {
                System.out.println("\nThank's For Playing !!");
                logger.info("Stopping program");
                System.exit(0);
            }
        } while (!(utils.isNumber(choice) && (choice.equals("1") || choice.equals("2") || choice.equals("0"))));
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
            System.out.println();
            int gameInt = Integer.parseInt(game);
            do {
                switch (gameInt) {
                    case 1:
                        logger.info("More or less game started");
                        new MoreOrLess();
                        break;
                    case 2:
                        int intMode = Integer.parseInt(mode);
                        logger.info("Search more or less game started in " + (intMode == 1? "challenger mode" : intMode == 2? "dual mode" : "defense mode") + (dev? " and developer mode" : ""));
                        new SearchMoreOrLess(intMode, dev);
                        break;
                    case 3:
                        intMode = Integer.parseInt(mode);
                        logger.info("Mastermind game started in " + (intMode == 1? "challenger mode" : intMode == 2? "dual mode" : "defense mode") + (dev? " and developer mode" : ""));
                        new Mastermind(intMode, dev);
                        break;
                }
            } while (askReplay());
        }
    }

}
