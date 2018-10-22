package SearchMoreOrLess;

import Utils.Game;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SearchMoreOrLess extends Game{

    private String pattern;
    private Logger logger = LogManager.getLogger("main.java.SearchMoreOrLess.SearchMoreOrLess");
    private SearchMoreOrLessCore searchMoreOrLess;


    public SearchMoreOrLess (int mode, boolean dev) {
        init();
        runGame(mode, dev);
    }

    @Override
    /**
     * Initialization method
     */
    protected void init () {
        int numberTriesMax = utilsSearchMoreOrLess.getMaxTries("SearchMoreOrLess");
        int secretCodeLength = utilsSearchMoreOrLess.getSecretCodeLength("SearchMoreOrLess");
        int[] secretCombination = utilsSearchMoreOrLess.randomCombination(secretCodeLength, 9);
        searchMoreOrLess = new SearchMoreOrLessCore(secretCodeLength, secretCombination, numberTriesMax, logger, pattern);
        for (int i = 0; i < secretCodeLength; i++)
            pattern += "*";
        logger.info("Creating the secret code");
    }


    @Override
    /**
     * Run the good game mode
     */
    protected void runGame (int mode, boolean dev) {
        switch (mode) {
            case 1:// challenger
                searchMoreOrLess.challenger(dev);
                break;

            case 2:// dual
                searchMoreOrLess.dual(dev);
                break;

            case 3:// defense
                searchMoreOrLess.defense();
                break;
        }
    }
}
