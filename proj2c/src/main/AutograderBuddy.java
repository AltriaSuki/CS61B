package main;

import browser.NgordnetQueryHandler;
import helper.WordNet;
import ngrams.NGramMap;


public class AutograderBuddy {
    /** Returns a HyponymHandler */
    public static NgordnetQueryHandler getHyponymsHandler(
            String wordFile, String countFile,
            String synsetFile, String hyponymFile) {

        return new HyponymsHandler(new WordNet(synsetFile, hyponymFile), new NGramMap(wordFile, countFile));
    }
}
