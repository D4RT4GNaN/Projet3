package Mastermind;

//import Utils.Utils;
import Utils.Game;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Mastermind extends Game {

    private int[] secretCombination;

    private Logger logger;
    private MastermindCore mastermind;

    public Mastermind (int mode, boolean dev) {
        init();
        runGame(mode, dev);
    }

    @Override
    /**
     * Initialization method
     */
    protected void init () {
        logger = LogManager.getLogger("main.java.Mastermind.Mastermind");
        int maxTries = utilsMastermind.getMaxTries("Mastermind");
        int secretCombinationLength = utilsMastermind.getSecretCodeLength("Mastermind");
        secretCombination = utilsMastermind.randomCombination(secretCombinationLength,utilsMastermind.getNumberColours());
        mastermind = new MastermindCore(secretCombination, secretCombinationLength, maxTries, logger);
        logger.info("Creating the secret code");
    }

    @Override
    /**
     * Run the good game mode
     */
    protected void runGame(int mode, boolean dev) {
        switch (mode) {
            case 1: // Challenger
                mastermind.challenger(dev);
                break;

            case 2: // Dual
                mastermind.dual(dev);
                break;

            case 3: // Defense
                mastermind.defense();
                break;
        }
    }


}
