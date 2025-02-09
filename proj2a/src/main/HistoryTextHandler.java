package main;

import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;
import java.util.List;
import ngrams.NGramMap;

public class HistoryTextHandler extends NgordnetQueryHandler {
    private NGramMap ngm;
    public HistoryTextHandler(NGramMap ngm){
        this.ngm=ngm;
    }
    @Override
    public String handle(NgordnetQuery q){
        List<String> word=q.words();
        int startYear=q.startYear();
        int endYear=q.endYear();
        String response="";
        for(String w:word){
            if(ngm.weightHistory(w)!=null){
                response+=w+": ";
                response+=ngm.weightHistory(w, startYear, endYear).toString()+"\n";
            }
            
        }
        // response += "Start Year: " + q.startYear() + "\n";
        // response += "End Year: " + q.endYear() + "\n";
        return response;
    }
    
}
