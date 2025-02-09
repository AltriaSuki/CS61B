package main;
import ngrams.NGramMap;
import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;

import java.util.ArrayList;
import java.util.List;

import org.knowm.xchart.XYChart;

import ngrams.TimeSeries;
import plotting.Plotter;

public class HistoryHandler extends NgordnetQueryHandler{
    private NGramMap ngm;
    public HistoryHandler(NGramMap ngm){
        this.ngm=ngm;
    }

    @Override
    public String handle(NgordnetQuery q){
        List<String> words=q.words();
        ArrayList<TimeSeries> lts =new ArrayList<>();
        for(String w:words){
            lts.add(ngm.weightHistory(w,q.startYear(),q.endYear()));
        }
        XYChart chart = Plotter.generateTimeSeriesChart(words, lts);
        String s = Plotter.encodeChartAsString(chart);
        return s;
    }
}
