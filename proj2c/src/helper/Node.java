package helper;
import java.util.HashSet;
 class Node{
    String [] synonym;
    String definition;
    int id;
    HashSet<Node> vertices;
    HashSet<Node> parent;

    public Node(String [] synonym,int id,String definition){
        this.synonym = synonym;
        this.id=id;
        this.definition =definition;
        vertices = new HashSet<Node>();
        parent = new HashSet<Node>();
    }
}