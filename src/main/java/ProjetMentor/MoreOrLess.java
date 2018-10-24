package ProjetMentor;

import Mastermind.utilsMastermind;
import Utils.Game;
import Utils.Utils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MoreOrLess extends Game {

    private MoreOrLessCore moreOrLess;

    public MoreOrLess (int mode, boolean dev) {
        init();
        runGame(mode, dev);
    }

    @Override
    protected void init () {
        Logger logger = LogManager.getLogger("main.java.MoreOrLess.MoreOrLess");
        int triesMax = Utils.getMaxTries("MoreOrLess");
        int secretNumberLength = Utils.getSecretCodeLength("MoreOrLess");
        int secretNumber = Utils.combinationToInt(Utils.randomCombination(secretNumberLength, 9));
        String pattern = "*";
        moreOrLess = new MoreOrLessCore(secretNumber, secretNumberLength, logger, triesMax, pattern);
    }

    @Override
    protected void runGame (int mode, boolean dev) {
        switch (mode) {
            case 1: // Challenger
                moreOrLess.challenger(dev);
                break;

            case 2: // Dual
                moreOrLess.dual(dev);
                break;

            case 3: // Defense
                moreOrLess.defense();
                break;
        }
    }
}
