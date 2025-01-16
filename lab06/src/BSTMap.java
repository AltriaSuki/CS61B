import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

public class BSTMap<K extends Comparable<K>,V>implements Map61B<K,V>{
    private class Node{
        K key;
        V value;
        Node left;
        Node right;
        private Node(K key,V value){
            this.key=key;
            this.value=value;
            left=null;
            right=null;
        }
    }
    private Node root;
    private int size;
    public BSTMap(){
        size=0;
    }

    private Node put(Node n,K key,V value){
        if(n==null){
            return new Node(key,value);
        }else if(key.compareTo(n.key)<0){
            n.left=put(n.left, key, value);
        }else if(key.compareTo(n.key)>0){
            n.right=put(n.right, key, value);
        }else{
            n.value=value;
        }
        return n;
    }
    @Override
    public void put(K key,V value){
        if(!containsKey(key)){
            size++;
        }
        root=put(root,key,value);
  
    }

    private Node get(Node n,K key){
        if(n==null){
            return null; 
        }else if(key.compareTo(n.key)<0){
            return get(n.left,key);
        }else if(key.compareTo(n.key)>0){
            return get(n.right,key);
        }else{
            return n;
        }
    }
    @Override
    public V get(K key){
        if(!containsKey(key)){
            return null;
        }
        return get(root,key).value;
    }
    
    @Override
    public boolean containsKey(K key){
        return get(root,key)!=null;
    }

    @Override
    public int size(){
        return size;
    }

    @Override
    public void clear(){
        root=null;
        size=0;
    }

    private void addKeySet(Node n,Set<K> keySet){
        if(n==null)return;
        addKeySet(n.left, keySet);
        keySet.add(n.key);
        addKeySet(n.right, keySet);
    }

    @Override
    public Set<K> keySet(){
        Set<K> keySet=new TreeSet<>();
        addKeySet(root, keySet);
        return keySet;
    }

    private Node min(Node n){
        if(n.left==null){
            return n;
        }
        return min(n.left);
    }

    private V remove(Node n,K key){
        if(n==null){
            return null;
        }
        if(key.compareTo(n.key)<0){
            return remove(n.left,key);
        }else if(key.compareTo(n.key)>0){
            return remove(n.right,key);
        }else{
            if(n==root){
                if(n.left==null){
                    V value=n.value;
                    root=n.right;
                    return value;
                }else if(n.right==null){
                    V value=n.value;
                    root=n.left;
                    return value;
                }else{
                    V value=n.value;
                    Node temp=min(n.right);
                    n.key=temp.key;
                    n.value=temp.value;
                    remove(n.right,temp.key);
                    return value;
                }
            }else{
                if(n.left==null){
                    V value=n.value;
                    n=n.right;
                    return value;
                }else if(n.right==null){
                    V value=n.value;
                    n=n.left;
                    return value;
                }else{
                    V value=n.value;
                    Node temp=min(n.right);
                    n.key=temp.key;
                    n.value=temp.value;
                    remove(n.right,temp.key);
                    return value;
                }
            }
            
        }
    }

    public V remove(K key){
        size--;
        return remove(root,key);
    }

    public Iterator<K> iterator(){
        return new BSTMapIter();
    }

    private class BSTMapIter implements Iterator<K>{
        public boolean hasNext(){
            return false;
        }
        public K next(){
            return null;
        }

    }
}
