package main.java;

import java.util.Scanner;

public class Menu {

    int gameSelected;

    /**
     * Asks the user what game mode they want
     */
    public void askGame (int choice) {
        System.out.println("Bienvenue sur le multi jeux");
        System.out.println("Choississez votre jeu");
        System.out.println("1 - Plus ou Moins");
        switch (choice) {
            case 1 :
                MoreOrLess moreOrLess = new MoreOrLess();
                gameSelected = 1;
                break;
            case 3 :
                MoreOrLess moreOrLessDev = new MoreOrLess(true);
                gameSelected = 3;
                break;
        }
    }

    /**
     *Asking player to replay the same game
     * @param choice the player's answer to replay
     */
    public void askReplay (String choice) {
        switch (choice.toUpperCase()) {
            case "Y" :
                this.askGame(gameSelected);
                break;
            case "N" :
                System.exit(0);
                break;
        }
    }

    /**
     *run the menu to choose the game mode
     */
    public void runMenu () {
        Scanner sc = new Scanner(System.in);
        int choiceGame = sc.nextInt();
        askGame(choiceGame);
        String choiceReplay;
        do {
            System.out.println("Voulez vous rejouez (Y/N) ?");
            choiceReplay = sc.next();
            askReplay(choiceReplay);
        } while (choiceReplay.equalsIgnoreCase("Y"));
    }
}
