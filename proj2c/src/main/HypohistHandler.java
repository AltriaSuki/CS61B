package main;

import browser.NgordnetQueryHandler;
import ngrams.NGramMap;
import ngrams.TimeSeries;
import plotting.Plotter;
import browser.NgordnetQuery;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.knowm.xchart.XYChart;

public class HypohistHandler extends NgordnetQueryHandler{

    private HyponymsHandler h;
    private NGramMap ngm;

    public HypohistHandler(HyponymsHandler h,NGramMap ngm){
        this.h=h;
        this.ngm=ngm;
    }
    @Override
    public String handle(NgordnetQuery q){
        if(q.k()==0){
            return "[]";
        }
        Set<String> result=new TreeSet<>();
        result.addAll(h.handleHyponyms(q));
        List<String> words = new ArrayList<>(result);
        ArrayList<TimeSeries> lts =new ArrayList<>();
        for(String w:words){
            lts.add(ngm.weightHistory(w,q.startYear(),q.endYear()));
        }
        XYChart chart = Plotter.generateTimeSeriesChart(words, lts);
        String s = Plotter.encodeChartAsString(chart);
        return s;
    }
    
}
