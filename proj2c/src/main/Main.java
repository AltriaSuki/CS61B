package main;

import browser.NgordnetServer;
import helper.WordNet;

import org.slf4j.LoggerFactory;
import ngrams.NGramMap;
import helper.WordNet;

public class Main {
    static {
        LoggerFactory.getLogger(Main.class).info("\033[1;38mChanging text color to white");
    }
    public static void main(String[] args) {
        NgordnetServer hns = new NgordnetServer();
        
        // The following code might be useful to you.

        String wordFile = "./data/ngrams/top_14377_words.csv";
        String countFile = "./data/ngrams/total_counts.csv";
        String hyponymFile = "./data/wordnet/hyponyms.txt";
        String synsetFile = "./data/wordnet/synsets.txt";
        NGramMap ngm = new NGramMap(wordFile, countFile);
        WordNet wn =new WordNet(synsetFile,hyponymFile);
        HyponymsHandler hh = new HyponymsHandler(wn,ngm);
        hns.startUp();
        hns.register("history", new HistoryHandler(ngm));
        hns.register("historytext", new HistoryTextHandler(ngm));
        hns.register("hyponyms",hh);
        hns.register("hypohist", new HypohistHandler(hh,ngm));

        System.out.println("Finished server startup! Visit http://localhost:4567/ngordnet.html");
    }
}
