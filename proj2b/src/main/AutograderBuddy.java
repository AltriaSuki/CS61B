package main;

import browser.NgordnetQueryHandler;
import helper.WordNet;


public class AutograderBuddy {
    /** Returns a HyponymHandler */
    public static NgordnetQueryHandler getHyponymsHandler(
            String wordFile, String countFile,
            String synsetFile, String hyponymFile) {
                return new HyponymsHandler(new WordNet(synsetFile, hyponymFile));
    }
}
