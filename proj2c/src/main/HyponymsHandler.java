package main;

import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;
import browser.NgordnetQueryType;

import java.sql.Time;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.TreeSet;
import java.util.TreeMap;
import helper.WordNet;
import ngrams.NGramMap;
import ngrams.TimeSeries;
import java.util.Set;

public class HyponymsHandler extends NgordnetQueryHandler{
    private WordNet wn;
    private NGramMap ngm;
    public HyponymsHandler(WordNet wn,NGramMap ngm){
        this.wn=wn;
        this.ngm=ngm;
    }

    @Override
    public String handle(NgordnetQuery q){
        TreeSet<String> result=new TreeSet<>();
        if(q.ngordnetQueryType()==NgordnetQueryType.HYPONYMS)
            result.addAll(handleHyponyms(q));
        else if(q.ngordnetQueryType()==NgordnetQueryType.ANCESTORS)
            result.addAll(handleAncestors(q));
        String res="";
        res+="[";
        res+=String.join(", ",result);
        res+="]";
        return res;
    }

    public Set<String> handleHyponyms(NgordnetQuery q){
        List<String> words=q.words();
        TreeSet<String> result=new TreeSet<>();
        int startYear=q.startYear();
        int endYear=q.endYear();
        TreeMap<String,Integer> map=new TreeMap<>();
        PriorityQueue<String> pq=new PriorityQueue<>(new Comparator<String>(){
            public int compare(String a,String b){
                return map.get(a)-map.get(b);
            }
        });
        int k=q.k();
        result.addAll(wn.hyponyms(words));
        if(k==0||result.size()<=k){
        }else{
            for(String word:result){
                int count=0;
                TimeSeries ts=ngm.countHistory(word,startYear,endYear);
                if(ts==null)continue;
                for(double d:ts.data()){
                    count+=(int) d;
                }
                map.put(word,count);
                if(pq.size()<k)pq.offer(word);
                else{
                    pq.offer(word);
                    pq.poll();
                }
            }
            result=new TreeSet<>();
            while(!pq.isEmpty())result.add(pq.poll());
        }
        return result;

    }

    private Set<String> handleAncestors(NgordnetQuery q){
        List<String> words=q.words();
        TreeSet<String> result=new TreeSet<>();
        result.addAll(wn.ancestor(words));
        return result;
    }
}
