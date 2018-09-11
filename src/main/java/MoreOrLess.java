package main.java;

import java.util.Scanner;

public class MoreOrLess {

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

    public int askNumber () {
        Scanner sc = new Scanner(System.in);
        int userNumber = sc.nextInt();
        return userNumber;
    }

    public boolean compareNumber (int userNumber, int secretNumber) {
        boolean endGame = false;
        if (userNumber == secretNumber) {
            System.out.println("Bravo! Vous avez trouvez le nombre secret");
            endGame = true;
        } else if (userNumber < secretNumber)
            System.out.println("C'est plus");
        else
            System.out.println("C'est moins");
        return endGame;
    }

    public void runMoreOrLess () {
        int secretNumber = randomSecretCombination();
        System.out.println("Bienvenue sur le jeu du plus ou moins");
        System.out.println("A vous de jouez :");
        boolean endGame;
        do {
            int userNumber = askNumber();
            endGame = compareNumber(userNumber, secretNumber);
        } while (!endGame);
    }
}
