package main.java;

import java.util.Scanner;

public class MoreOrLess {
    /**
     * Create a random number
     * @return the secret number
     */
    public int randomSecretCombination () {
        int [] digitSecretCombination = new int [4];
        int secretCombination = 0;
        String stringSecretCombination = "";

        for (int i = 0; i < 4; i++) {
            if (i == 0)
                digitSecretCombination[i] = 1 + (int)(Math.random() * 9);
            else
                digitSecretCombination[i] = (int) (Math.random() * 10);
            stringSecretCombination = stringSecretCombination + digitSecretCombination[i];
        }
        secretCombination = Integer.parseInt(stringSecretCombination);
        return secretCombination;
    }

    /**
     * Asks the user to enter a number
     * @return the number entered by the user
     */
    public int askNumber () {
        System.out.print("Donnez un chiffre : ");
        Scanner sc = new Scanner(System.in);
        int userNumber = sc.nextInt();
        return userNumber;
    }

    /**
     *Compare the number entered by the user and the secret number and display if it's more, less or equal
     * @param userNumber the number entered by the user
     * @param secretNumber the secret random number
     * @return boolean meaning the end of game or not
     */
    public boolean compareNumber (int userNumber, int secretNumber, int lapCounter) {
        boolean endGame = false;
        if (userNumber == secretNumber) {
            System.out.println("Bravo! Vous avez trouvez le nombre secret en seulement " + lapCounter + " tours");
            endGame = true;
        } else if (userNumber < secretNumber)
            System.out.println("C'est plus");
        else
            System.out.println("C'est moins");
        return endGame;
    }

    /**
     *Run asking process for one secret number
     */
    public void runMoreOrLess () {
        int secretNumber = randomSecretCombination();
        int lapCounter = 0;
        System.out.println("Bienvenue sur le jeu du plus ou moins");
        System.out.println("A vous de jouez :");
        boolean endGame;
        do {
            lapCounter++;
            int userNumber = askNumber();
            endGame = compareNumber(userNumber, secretNumber, lapCounter);
        } while (!endGame);
    }
}
