package ex1.src;

import java.io.Serializable;
import java.util.*;

public class WGraph_DS implements weighted_graph, Serializable {
    /**
     * Fields
     */
    private static final long serialVersionUID = 8597480894519396439L;
    private HashMap<Integer, node_info> HashNode; //this Hashmap Represents the Nodes
    private HashMap<Integer, HashMap<Integer, Double>> HashEdge; // this Hashmap Represents the Edges\Neibors
    private static int CounterMc; // counting how much change made up on this Graph
    private static int EdgeCounter; //How many edges are there at any given moment


    /**
     * constructor
     */
    public WGraph_DS () {
        HashNode = new HashMap<>();
        HashEdge = new HashMap<>();
        CounterMc = 0;
        EdgeCounter = 0;
    }


    public void setCounterMc(int counterMc) {
        CounterMc = counterMc;
    }

    /**
     * return the node_data by the node_id,
     * @param key - the node_id
     * @return the node_data by the node_id, null if none.
     */
    public node_info getNode(int key) {
        return HashNode.get(key);
    }


    /**
     * return true iff (if and only if) there is an edge between node1 and node2
     * Note: this method should run in O(1) time.
     * @param node1
     * @param node2
     */
    public boolean hasEdge(int node1, int node2) {
        if (!this.HashNode.containsKey(node1) || !this.HashNode.containsKey(node2)) return false;
        if (HashEdge.get(node2).containsKey(node1)){
            return true;
        }
        return false;
    }

    /**
     * return the weight if the edge (node1, node1). In case
     * there is no such edge - should return -1
     * Note: this method should run in O(1) time.
     * this methods check if node1 and node1 is the same node or if they
     * diffrent nodes but they have already edge - return -1, else :
     * get an edge between them.
     * @param node1
     * @param node2

     */
    public double getEdge(int node1, int node2) {
        if (!HashEdge.get(node2).containsKey(node1) || node1 == node2) {
            return -1;
        }
        return HashEdge.get(node1).get(node2);
    }

    /**
     * add a new node to the graph with the given key.
     * Note: this method should run in O(1) time.
     * Note2: if there is already a node with such a key -> no action should be performed.
     * @param key
     */
    public void addNode(int key) {
        if (HashEdge.containsKey(key)){
            return;
        }
        else {
            node_info rahamim = new Node_Info(key);
            HashEdge.put (key, new HashMap <Integer, Double>());
            HashNode.put(key, rahamim);
            CounterMc++;
        }
    }


    /**
     * Connect an edge between node1 and node2, with an edge with weight >=0.
     * Note: this method should run in O(1) time.
     * Note2: if the edge node1-node2 already exists - the method simply updates the weight of the edge.
     * if node1 = node2, do not do anything, if they are 2 nodes without edge between them, put an edge
     * between them and put w to be the Weight of it.
     * if they already have edge between them - if the edge weight already w - do not do anything, else :
     * update the weight to be w.
     */
    public void connect(int node1, int node2, double w) {
        if (node1 == node2){
            return;
        }
        else if (!hasEdge(node1,node2)) {
            HashEdge.get(node1).put(node2, w);
            HashEdge.get(node2).put(node1, w);
            CounterMc++;
            EdgeCounter++;
        }
        else {
            if (HashEdge.get(node1).get(node2) == w){
                return;
            }
            else{
                HashEdge.get(node1).put(node2, w);
                HashEdge.get(node2).put(node1, w);
                CounterMc++;
            }
        }
    }

    /**
     * This method return a pointer (shallow copy) for a
     * Collection representing all the nodes in the graph.
     * Note: this method should run in O(1) time
     * @return Collection<node_data>
     */
    public Collection<node_info> getV() {
        return HashNode.values();
    }


    /**
     *
     * This method returns a Collection containing all the
     * nodes connected to node_id
     * Note: this method can run in O(k) time, k - being the degree of node_id.
     * We built an ArrayList that stores all the keys we got by going through
     * a foreach loop on all Edges.keySet and called it Neighbors, we will return Neighbors.
     * @return Collection<node_data>
     */
    public Collection<node_info> getV(int node_id) {
        HashMap<Integer, Double> Edges = HashEdge.get(node_id);
        ArrayList<node_info> Neighbors = new ArrayList<>();
        for(Integer key: Edges.keySet()){
            Neighbors.add(HashNode.get(key));
        }
        return Neighbors;
    }


    /**
     * Delete the node (with the given ID) from the graph -
     * and removes all edges which starts or ends at this node.
     * This method should run in O(n), |V|=n, as all the edges should be removed.
     * Check: If the node does not exist in the hashmap at all, return null
     * If it exists :
     * go through the list of neighbors of the node and remove from them the edges that are linked to the removed node
     * Then remove the node itself and return the data of the node who removed - even calling RemovedNode.
     *
     */
    public node_info removeNode(int key) {
        if (!HashEdge.containsKey(key)) {
            return null;
        }
        for (Integer neighbor : HashEdge.get(key).keySet()){
            HashEdge.get(neighbor).remove(key);
        }
        EdgeCounter -= HashEdge.get(key).size();
        HashEdge.remove(key);
        CounterMc++;
        node_info RemovedNode = HashNode.remove(key);
        return RemovedNode;
    }


    /**
     * Delete the edge from the graph,
     * Note: this method should run in O(1) time.
     * if they do not have edge between them return, else remove from each of them
     * the edge.
     */
    public void removeEdge(int node1, int node2) {
        if (!hasEdge(node1,node2)) {
            return;
        }
        HashEdge.get(node1).remove(node2);
        HashEdge.get(node2).remove(node1);
        EdgeCounter--;
        CounterMc++;
    }


    /** return the number of vertices (nodes) in the graph.
     * Note: this method should run in O(1) time.
     * @return
     */
    public int nodeSize() {
        return HashEdge.size();
    }


    /**
     * return the number of edges (undirectional graph).
     * Note: this method should run in O(1) time.
     * @return
     */
    public int edgeSize() {
        return EdgeCounter;
    }


    /**
     * return the Mode Count - for testing changes in the graph.
     * Any change in the inner state of the graph should cause an increment in the ModeCount
     * @return
     */
    public int getMC() {
        return CounterMc;
    }


    /**
     * This class implements the set of operations applicable on a
     * node (vertex) in an (undirectional) weighted graph.
     * as described in the node_Info Interface
     *
     */
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("Nodes - ").append(this.nodeSize()).append(" Edges - ").append(this.EdgeCounter).append("\n");
        for (node_info n : this.getV()) {
            result.append("Node - ").append(n.getKey());
            result.append(" Neighbors - ").append(this.getV(n.getKey()).size());
            result.append("\n");
        }
        return result.toString();
    }

    /**
     * Indicates whether some other object is "equal to" this one.
     * <p>
     * The {@code equals} method implements an equivalence relation
     * on non-null object references:
     * <ul>
     * <li>It is <i>reflexive</i>: for any non-null reference value
     *     {@code x}, {@code x.equals(x)} should return
     *     {@code true}.
     * <li>It is <i>symmetric</i>: for any non-null reference values
     *     {@code x} and {@code y}, {@code x.equals(y)}
     *     should return {@code true} if and only if
     *     {@code y.equals(x)} returns {@code true}.
     * <li>It is <i>transitive</i>: for any non-null reference values
     *     {@code x}, {@code y}, and {@code z}, if
     *     {@code x.equals(y)} returns {@code true} and
     *     {@code y.equals(z)} returns {@code true}, then
     *     {@code x.equals(z)} should return {@code true}.
     * <li>It is <i>consistent</i>: for any non-null reference values
     *     {@code x} and {@code y}, multiple invocations of
     *     {@code x.equals(y)} consistently return {@code true}
     *     or consistently return {@code false}, provided no
     *     information used in {@code equals} comparisons on the
     *     objects is modified.
     * <li>For any non-null reference value {@code x},
     *     {@code x.equals(null)} should return {@code false}.
     * </ul>
     * <p>
     * The {@code equals} method for class {@code Object} implements
     * the most discriminating possible equivalence relation on objects;
     * that is, for any non-null reference values {@code x} and
     * {@code y}, this method returns {@code true} if and only
     * if {@code x} and {@code y} refer to the same object
     * ({@code x == y} has the value {@code true}).
     * <p>
     * Note that it is generally necessary to override the {@code hashCode}
     * method whenever this method is overridden, so as to maintain the
     * general contract for the {@code hashCode} method, which states
     * that equal objects must have equal hash codes.
     *
     * @param obj the reference object with which to compare.
     * @return {@code true} if this object is the same as the obj
     * argument; {@code false} otherwise.
     * @see #hashCode()
     * @see HashMap
     */
    @Override
    public boolean equals(Object obj) {
        weighted_graph wg = (weighted_graph) obj;
        return this.toString().equals(wg.toString());
    }

    public class Node_Info implements node_info, Serializable, Comparable {
        /**
         * Fields
         */
        private int key;
        private String Data;
        private double tag;

        /**
         * constructors
         */
        public Node_Info(int key){
            this.key = key;
            Data = null;
            tag = 0;
        }


        public Node_Info (Node_Info T){
            this.key = T.getKey();
            this.Data = T.getInfo();
            this.tag = T.getTag();
        }


        /**
         * Return the key (id) associated with this node.
         * each node_data have a unique key.
         *
         * @return key
         */
        public int getKey() {

            return this.key;
        }


        /**
         * return the remark (meta data) associated with this node.
         *
         * @return Data
         */
        public String getInfo() {

            return this.Data;
        }


        /**
         * Allows changing the remark (meta data) associated with this node.
         *
         *
         * @param s
         */
        public void setInfo(String s) {

            this.Data = s;
        }


        /**
         * Temporal data (aka color: e,g, white, gray, black)
         * which can be used be algorithms
         *
         * @return tag
         */
        public double getTag() {

            return this.tag;
        }


        /**
         * Allow setting the "tag" value for temporal marking an node - common
         * practice for marking by algorithms.
         *
         * @param t - the new value of the tag
         */
        public void setTag(double t) {

            this.tag = t;
        }


        /**
         * Compares this object with the specified object for order.  Returns a
         * negative integer, zero, or a positive integer as this object is less
         * than, equal to, or greater than the specified object.
         *
         * <p>The implementor must ensure
         * {@code sgn(x.compareTo(y)) == -sgn(y.compareTo(x))}
         * for all {@code x} and {@code y}.  (This
         * implies that {@code x.compareTo(y)} must throw an exception iff
         * {@code y.compareTo(x)} throws an exception.)
         *
         * <p>The implementor must also ensure that the relation is transitive:
         * {@code (x.compareTo(y) > 0 && y.compareTo(z) > 0)} implies
         * {@code x.compareTo(z) > 0}.
         *
         * <p>Finally, the implementor must ensure that {@code x.compareTo(y)==0}
         * implies that {@code sgn(x.compareTo(z)) == sgn(y.compareTo(z))}, for
         * all {@code z}.
         *
         * <p>It is strongly recommended, but <i>not</i> strictly required that
         * {@code (x.compareTo(y)==0) == (x.equals(y))}.  Generally speaking, any
         * class that implements the {@code Comparable} interface and violates
         * this condition should clearly indicate this fact.  The recommended
         * language is "Note: this class has a natural ordering that is
         * inconsistent with equals."
         *
         * <p>In the foregoing description, the notation
         * {@code sgn(}<i>expression</i>{@code )} designates the mathematical
         * <i>signum</i> function, which is defined to return one of {@code -1},
         * {@code 0}, or {@code 1} according to whether the value of
         * <i>expression</i> is negative, zero, or positive, respectively.
         *
         * @param o the object to be compared.
         * @return a negative integer, zero, or a positive integer as this object
         * is less than, equal to, or greater than the specified object.
         * @throws NullPointerException if the specified object is null
         * @throws ClassCastException   if the specified object's type prevents it
         *                              from being compared to this object.
         */
        @Override
        public int compareTo(Object o) {
            if (this.getTag() > ((node_info) o).getTag()) return 1;
            else if (this.getTag() < ((node_info) o).getTag()) return -1;
            return 0;
        }
    }
}

