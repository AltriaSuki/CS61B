package helper;
import edu.princeton.cs.algs4.In;

import java.util.HashSet;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Set;
import java.util.List;
import java.util.Map;

public class WordNet {
    private DirectedGraph graph;
    public WordNet(String synsetfilename,String hyponymfilename){
        graph = new DirectedGraph();
        In synset=new In(synsetfilename);
        In hyponym=new In(hyponymfilename);
        while(synset.hasNextLine()){
            String[] line=synset.readLine().split(",");
            String[] synonym=line[1].split(" ");
            graph.addNode(synonym,Integer.parseInt(line[0]),line[2]);
        }

        while(hyponym.hasNextLine()){
            String[] line=hyponym.readLine().split(",");
            for(int i=1;i<line.length;i++){
                graph.addEdge(Integer.parseInt(line[0]),Integer.parseInt(line[i]));
            }
        }
    }

    private Set<String> StringAncestor(String word){
        Set<Integer> ids=graph.StringToId(word);
        Set<Node> result=new HashSet<>();
        for(int id:ids){
            result.addAll(graph.Ancestor(id));
        }
        Set<String> res=new HashSet<>();
        for(Node n:result){
            for(String s:n.synonym)res.add(s);
        }
        return res;
    }

    public Set<String> ancestor(List<String> word){
        Set<String> result=new HashSet<>();
        if(word.size()==0){
            throw new IllegalArgumentException("No word is given");
        }else{
            Map<String,Integer> map=new HashMap<>();
            for(String w:word){
                Set<String> temp=StringAncestor(w);
                for(String s:temp){
                    map.put(s,map.getOrDefault(s, 0)+1);
                }
            }
            for(String s:map.keySet()){
                if(map.get(s)==word.size()){
                    result.add(s);
                }
            }
        }
        return result;
    }

    private Set<String> StringChild(String word){
        Set<Integer> ids=graph.StringToId(word);
        Set<Node> result=new HashSet<>();
        for(int id:ids){
            result.addAll(graph.Child(id));
        }
        Set<String> res=new HashSet<>();
        for(Node n:result){
            for(String s:n.synonym)res.add(s);
        }
        return res;
    }
    //需要字符串的集合
    public Set<String> hyponyms(List<String> word){
        int size=word.size();
        Set<String> result=new HashSet<>();
        if(size==0){
            throw new IllegalArgumentException("No word is given");
        }else if(size==1){
            Set<Integer> ids=graph.StringToId(word.get(0));
            for(int id:ids){
                result.addAll(graph.TranverseDown(id));
            }
        }else{
            Map<String,Integer> map=new HashMap<>();
            for(String w:word){
                Set<String> temp=StringChild(w);
                for(String s:temp){
                    map.put(s,map.getOrDefault(s, 0)+1);
                }
            }
            for(String s:map.keySet()){
                if(map.get(s)==word.size()){
                    result.add(s);
                }
            }
        }
        return result;
    }
}
