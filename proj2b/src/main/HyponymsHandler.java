package main;

import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;

import java.util.HashSet;
import java.util.List;
import java.util.TreeSet;
import helper.WordNet;

public class HyponymsHandler extends NgordnetQueryHandler{
    private WordNet wn;
    public HyponymsHandler(WordNet wn){
        this.wn=wn;
    }

    @Override
    public String handle(NgordnetQuery q){
        List<String> words=q.words();
        TreeSet<String> result=new TreeSet<>();
        // for(String word:words){
        //     result.addAll(wn.hyponyms(word));
        // }
        result.addAll(wn.hyponyms(words));
        String res="";
        res+="[";
        res+=String.join(", ",result);
        res+="]";
        return res;
    }
}
