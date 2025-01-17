package hashmap;

import java.util.Collection;
import java.util.LinkedList;
import java.util.TreeSet;
import java.util.Set;
import java.util.Iterator;
/**
 *  A hash table-backed Map implementation.
 *
 *  Assumes null keys will never be inserted, and does not resize down upon remove().
 *  @author YOUR NAME HERE
 */
public class MyHashMap<K, V> implements Map61B<K, V> {

    /**
     * Protected helper class to store key/value pairs
     * The protected qualifier allows subclass access
     */
    protected class Node {
        K key;
        V value;

        Node(K k, V v) {
            key = k;
            value = v;
        }
    }

    /* Instance Variables */
    private Collection<Node>[] buckets;
    private int size;
    private double loadFactor;
    // You should probably define some more!

    /** Constructors */
    public MyHashMap() { 
        this(16,0.75);
    }

    public MyHashMap(int initialCapacity) { 
        this(initialCapacity,0.75);
    }

    /**
     * MyHashMap constructor that creates a backing array of initialCapacity.
     * The load factor (# items / # buckets) should always be <= loadFactor
     *
     * @param initialCapacity initial size of backing array
     * @param loadFactor maximum load factor
     */
    public MyHashMap(int initialCapacity, double loadFactor) { 
        this.loadFactor = loadFactor;
        buckets = (Collection<Node>[])new Collection[initialCapacity];

        for(int i=0;i<initialCapacity;i++){
            buckets[i]=createBucket();
        }


    }

    /**
     * Returns a data structure to be a hash table bucket
     *
     * The only requirements of a hash table bucket are that we can:
     *  1. Insert items (`add` method)
     *  2. Remove items (`remove` method)
     *  3. Iterate through items (`iterator` method)
     *  Note that that this is referring to the hash table bucket itself,
     *  not the hash map itself.
     *
     * Each of these methods is supported by java.util.Collection,
     * Most data structures in Java inherit from Collection, so we
     * can use almost any data structure as our buckets.
     *
     * Override this method to use different data structures as
     * the underlying bucket type
     *
     * BE SURE TO CALL THIS FACTORY METHOD INSTEAD OF CREATING YOUR
     * OWN BUCKET DATA STRUCTURES WITH THE NEW OPERATOR!
     */
    protected Collection<Node> createBucket() {
        // TODO: Fill in this method.
        return new LinkedList<>();
    }

    // TODO: Implement the methods of the Map61B Interface below
    // Your code won't compile until you do so!
    public void clear(){
        for(int i=0;i<buckets.length;i++){
            buckets[i].clear();
        }
        size=0;
    }

    public V get(K key){
        int index =key.hashCode();
        index = Math.floorMod(index,buckets.length);
        for(Node n:buckets[index]){
            if(n.key.equals(key)){
                return n.value;
            }
        }
        return null;
    }

    private void resize(){
        Collection<Node>[] newBuckets=(Collection<Node>[])new Collection[buckets.length*2];
        for(int i=0;i<newBuckets.length;i++){
            newBuckets[i]=createBucket();
        }
        for(int i=0;i<buckets.length;i++){
            for(Node n:buckets[i]){
                int index =n.key.hashCode();
                index=Math.floorMod(index,newBuckets.length);
                newBuckets[index].add(n);
            }
        }
        buckets=newBuckets;
    }

    public void put(K key,V value){
        if((1.0*size)/buckets.length>loadFactor){
            resize();
        }
        if(containsKey(key)){
            int index =key.hashCode();
            index=Math.floorMod(index,buckets.length);
            for(Node n:buckets[index]){
                if(n.key.equals(key)){
                    n.value=value;
                    return;
                }
            }
        }
        Node n=new Node(key,value);
        int index=key.hashCode();
        index=Math.floorMod(index,buckets.length);
        buckets[index].add(n);
        size++;
    }

    public boolean containsKey(K key){
        int index =key.hashCode();
        index = Math.floorMod(index,buckets.length);
        for(Node n:buckets[index]){
            if(n.key.equals(key)){
                return true;
            }
        }
        return false;
    }

    public int size(){
        return size;
    }

    public V remove(K key){
        if(!containsKey(key)){
            return null;
        }
        V value=null;
        int index =key.hashCode();
        index=Math.floorMod(index,buckets.length);
        for(Node n:buckets[index]){
            if(n.key.equals(key)){
                buckets[index].remove(n);
                size--;
                value=n.value;
            }
        }
        return value;
    }

    public Set<K> keySet(){
        Set<K> keySet = new TreeSet<>();
        for(int i=0;i<buckets.length;i++){
            for(Node n:buckets[i]){
                keySet.add(n.key);
            }
        }
        return keySet;
    }

    public Iterator<K> iterator(){
        throw new UnsupportedOperationException();
    }

}
