package helper;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.HashMap;
import java.util.Set;
import java.util.Queue;

import org.checkerframework.checker.units.qual.h;

public class DirectedGraph {
    private HashMap<Integer,Node> nodes;
    private HashMap<String,HashSet<Integer>> StringToId;
    private int vertices;
    private int edge;

    public DirectedGraph(){
        nodes=new HashMap<>();
        StringToId=new HashMap<>();
        vertices=0;
        edge=0;
    }

    public Set<Integer> StringToId(String s){
        return StringToId.get(s);
    }

    public void addNode(String[] synonym,int id,String definition){
        Node n = new Node(synonym,id,definition);
        nodes.put(id,n);
        vertices++;
        for(String s:synonym){
            if(StringToId.containsKey(s)){
                StringToId.get(s).add(id);
            }else{
                HashSet<Integer> temp=new HashSet<>();
                temp.add(id);
                StringToId.put(s,temp);
            }
        }
        
    }

    public void addEdge(int from,int to){
        Node n1 =nodes.get(from);
        Node n2=nodes.get(to);
        if(n1!=null && n2!=null){
            n1.vertices.add(n2);
            n2.parent.add(n1);
            edge++;
        }
    }


    public int V(){
        return vertices;
    }

    public int E(){
        return edge;
    }

    public Set<String> TranverseDown(int id){
        Set<Node> result=Child(id);
        Set<String> res=new HashSet<>();
        for(Node n:result){
            for(String s:n.synonym){
                res.add(s);
            }
        }
        return res;
    }

    public Set<Node> Child(int id){
        Node n=nodes.get(id);
        Set<Node> result=new HashSet<>();
        if(n!=null){
            Queue<Node> q=new LinkedList<>();
            q.add(n);
            while(!q.isEmpty()){
                Node temp=q.poll();
                for(Node p:temp.vertices){
                    q.add(p);

                }

                result.add(temp);
            }
            return result;
        }
        return null;
    }

    public Set<Node> Ancestor(int id){
        Node n=nodes.get(id);
        Set<Node> result=new HashSet<>();
        if(n!=null){
            Queue<Node> q=new LinkedList<>();
            q.add(n);
            while(!q.isEmpty()){
                Node temp=q.poll();
                for(Node p:temp.parent){
                    q.add(p);

                }

                result.add(temp);
            }
            return result;
        }
        return null;
        
    }

}
