package main.java.ProjetMentor;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.Scanner;

public class MoreOrLess {

    Properties properties = new Properties();

    public MoreOrLess (boolean developMode) {
        runMoreOrLess(developMode);
    }

    public MoreOrLess () {
        runMoreOrLess(false);
    }

    /**
     * Create a random number
     * @return the secret number
     */
    public int randomSecretCombination () {
        try {
            FileInputStream config = new FileInputStream("src/main/resources/config.properties");
            properties.load(config);
            config.close();
        } catch (FileNotFoundException e) {
            System.out.println("Le fichier n'existe pas !");
        } catch (IOException e) {
            System.out.println("Impossible de lire ou d'écrire dans le fichier.");
        }
        int caseNumber = Integer.parseInt(properties.getProperty("number-case-MoreOrLess"));
        int [] digitSecretCombination = new int [caseNumber];
        int secretCombination;
        String stringSecretCombination = "";

        for (int i = 0; i < caseNumber; i++) {
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

    /**
     *Run asking process for one secret number
     */
    public void runMoreOrLess (boolean developMode) {
        int secretNumber = randomSecretCombination();
        int lapCounter = 0;
        int triesMax = Integer.parseInt(properties.getProperty("number-tries-MoreOrLess"));
        System.out.println("Bienvenue sur le jeu du plus ou moins");
        if (developMode)
            System.out.println("nombre secret : " + secretNumber);
        System.out.println("A vous de jouez (" + Integer.parseInt(properties.getProperty("number-case-MoreOrLess")) + ") :");
        boolean endGame;
        do {
            lapCounter++;
            int userNumber = askNumber();
            endGame = compareNumber(userNumber, secretNumber, lapCounter, triesMax);
        } while (!endGame);
    }
}
