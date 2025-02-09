package ngrams;

import java.sql.Time;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static ngrams.TimeSeries.MAX_YEAR;
import static ngrams.TimeSeries.MIN_YEAR;

import edu.princeton.cs.algs4.In;

/**
 * An object that provides utility methods for making queries on the
 * Google NGrams dataset (or a subset thereof).
 *
 * An NGramMap stores pertinent data from a "words file" and a "counts
 * file". It is not a map in the strict sense, but it does provide additional
 * functionality.
 *
 * @author Josh Hug
 */
public class NGramMap {

    // TODO: Add any necessary static/instance variables.
    Map<String,TimeSeries> NGramMap;
    TimeSeries counts;

    /**
     * Constructs an NGramMap from WORDSFILENAME and COUNTSFILENAME.
     */
    public NGramMap(String wordsFilename, String countsFilename) {
        // TODO: Fill in this constructor. See the "NGramMap Tips" section of the spec for help.
        NGramMap=new HashMap<>();
        In in=new In(wordsFilename);
        while(in.hasNextLine()){
            String line=in.readLine();
            String[] word=line.split("\t");
            if(!NGramMap.containsKey(word[0])){
                NGramMap.put(word[0],new TimeSeries());
            }
            NGramMap.get(word[0]).put(Integer.parseInt(word[1]),Double.parseDouble(word[2]));
        }

        counts=new TimeSeries();
        in=new In(countsFilename);
        while(in.hasNextLine()){
            String line=in.readLine();
            String[] word=line.split(",");
            counts.put(Integer.parseInt(word[0]),Double.parseDouble(word[1]));
        }

    }

    /**
     * Provides the history of WORD between STARTYEAR and ENDYEAR, inclusive of both ends. The
     * returned TimeSeries should be a copy, not a link to this NGramMap's TimeSeries. In other
     * words, changes made to the object returned by this function should not also affect the
     * NGramMap. This is also known as a "defensive copy". If the word is not in the data files,
     * returns an empty TimeSeries.
     */
    public TimeSeries countHistory(String word, int startYear, int endYear) {
        // TODO: Fill in this method.
        if(NGramMap.containsKey(word)){
            return new TimeSeries(NGramMap.get(word),startYear,endYear);
        }
        return null;
    }

    /**
     * Provides the history of WORD. The returned TimeSeries should be a copy, not a link to this
     * NGramMap's TimeSeries. In other words, changes made to the object returned by this function
     * should not also affect the NGramMap. This is also known as a "defensive copy". If the word
     * is not in the data files, returns an empty TimeSeries.
     */
    public TimeSeries countHistory(String word) {
        // TODO: Fill in this method.
        if(NGramMap.containsKey(word)){
            return new TimeSeries(NGramMap.get(word),MIN_YEAR,MAX_YEAR);
        }
        return null;
    }

    /**
     * Returns a defensive copy of the total number of words recorded per year in all volumes.
     */
    public TimeSeries totalCountHistory() {
        // TODO: Fill in this method.
        if(counts!=null){
            return new TimeSeries(counts,MIN_YEAR,MAX_YEAR);
        }
        return null;
    }

    /**
     * Provides a TimeSeries containing the relative frequency per year of WORD between STARTYEAR
     * and ENDYEAR, inclusive of both ends. If the word is not in the data files, returns an empty
     * TimeSeries.
     */
    public TimeSeries weightHistory(String word, int startYear, int endYear) {
        // TODO: Fill in this method.
        if(NGramMap.containsKey(word)){
            TimeSeries ts=new TimeSeries(NGramMap.get(word),startYear,endYear);
            TimeSeries total=totalCountHistory();
            ts=ts.dividedBy(total);
            return ts;
        }
        return null;
    }

    /**
     * Provides a TimeSeries containing the relative frequency per year of WORD compared to all
     * words recorded in that year. If the word is not in the data files, returns an empty
     * TimeSeries.
     */
    public TimeSeries weightHistory(String word) {
        // TODO: Fill in this method.
        if(NGramMap.containsKey(word)){
            TimeSeries ts=new TimeSeries(NGramMap.get(word),MIN_YEAR,MAX_YEAR);
            TimeSeries total=totalCountHistory();
            ts=ts.dividedBy(total);
            return ts;
        }
        return null;
    }

    /**
     * Provides the summed relative frequency per year of all words in WORDS between STARTYEAR and
     * ENDYEAR, inclusive of both ends. If a word does not exist in this time frame, ignore it
     * rather than throwing an exception.
     */
    public TimeSeries summedWeightHistory(Collection<String> words,
                                          int startYear, int endYear) {
        // TODO: Fill in this method.
        TimeSeries ts=new TimeSeries();
        boolean flag=false;
        for(String word:words){
            if(NGramMap.containsKey(word)){
                ts=ts.plus(weightHistory(word, startYear, endYear));
                flag=true;
            }
        }
        if(flag){
            return ts;
        }
        return null;
    }

    /**
     * Returns the summed relative frequency per year of all words in WORDS. If a word does not
     * exist in this time frame, ignore it rather than throwing an exception.
     */
    public TimeSeries summedWeightHistory(Collection<String> words) {
        // TODO: Fill in this method.
        TimeSeries ts=new TimeSeries();
        boolean flag=false;
        for(String word:words){
            if(NGramMap.containsKey(word)){
                ts=ts.plus(weightHistory(word));
                flag=true;
            }
        }
        if(flag){
            return ts;
        }
        return null;
    }

    // TODO: Add any private helper methods.
    // TODO: Remove all TODO comments before submitting.
}
