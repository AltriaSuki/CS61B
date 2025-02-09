package helper;
import edu.princeton.cs.algs4.In;

import java.util.HashSet;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Set;
import java.util.List;
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

    private Set<Node> StringChild(String word){
        Set<Integer> ids=graph.StringToId(word);
        Set<Node> result=new HashSet<>();
        for(int id:ids){
            result.addAll(graph.Child(id));
        }
        return result;
    }

    public Set<String> hyponyms(List<String> word){
        // Set<Integer> ids=graph.StringToId(word);
        // Set<String> result=new HashSet<>();
        // for(int id:ids){
        //     result.addAll(graph.TranverseDown(id));
        // }
        // return result;
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
            boolean first=true;
            Set<Node> temp=new HashSet<>();
            for(String s:word){
                if(first){
                    temp=StringChild(s);
                    first=false;
                }else{
                    temp.retainAll(StringChild(s));
                }
            }
            for(Node n:temp){
                for(String s:n.synonym){
                    result.add(s);
                }
            }

        }
        return result;
    }
}
