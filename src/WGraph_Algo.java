package ex1.src;

import java.io.*;
import java.util.*;


/**
 * This interface represents an Undirected (positive) Weighted Graph Theory algorithms including:
 * 0. clone(); (copy)
 * 1. init(graph);
 * 2. isConnected();
 * 3. double shortestPathDist(int src, int dest);
 * 4. List<node_data> shortestPath(int src, int dest);
 * 5. Save(file);
 * 6. Load(file);
 *
 * @author Afik.peretz
 *
 */

public class WGraph_Algo implements weighted_graph_algorithms {
    weighted_graph Graph = new WGraph_DS();


    /**
     * Init the graph on which this set of algorithms operates on.
     *
     * @param g
     */
    public void init(weighted_graph g) {
        this.Graph = g;
    }

    /**
     * Return the underlying graph of which this class works.
     *
     * @return
     */
    public weighted_graph getGraph() {
        return this.Graph;
    }

    /**
     * Compute a deep copy of this weighted graph.
     *
     * @return
     */
    public weighted_graph copy() {
        weighted_graph graphCopy = new WGraph_DS();
        if (this.Graph == null) {
            return null;
        }
        for (node_info node : this.Graph.getV()) {
            graphCopy.addNode(node.getKey());
        }
        for (node_info node : this.Graph.getV()) {
            for (node_info Kishke : this.Graph.getV(node.getKey())) {
                graphCopy.connect(node.getKey(), Kishke.getKey(), this.Graph.getEdge(node.getKey(), Kishke.getKey()));
            }
        }
        ((WGraph_DS) graphCopy).setCounterMc(this.Graph.getMC());
        return graphCopy;
    }

    /**
     * Returns true if and only if (iff) there is a valid path from EVREY node to each
     * other node. NOTE: assume ubdirectional graph.
     *
     * @return b
     */
    public boolean isConnected() {
        if (Graph == null || Graph.nodeSize() <= 1) {
            return true;
        }
        Queue<Integer> queue = new LinkedList<>();
        int nodeKey = Graph.getV().iterator().next().getKey();
        int prevNodes = 0;
        queue.add(nodeKey);
        for (node_info node : Graph.getV()) {
            node.setTag(0);
        }
        while (!queue.isEmpty()) {
            nodeKey = queue.poll();
            for (node_info node : Graph.getV(nodeKey)) {
                if (node.getTag() == 0) {
                    prevNodes++;
                    queue.add(node.getKey());
                    node.setTag(1);
                }
            }
        }
        boolean b;
        if (prevNodes == Graph.nodeSize()) {
            b = true;
        } else {
            b = false;
        }
        return b;
    }

    /**
     * returns the length of the shortest path between src to dest
     * Note: if no such path --> returns -1
     *
     * @param src  - start node
     * @param dest - end (target) node
     * @return
     */
    public double shortestPathDist(int src, int dest) {
        if (src == dest || Graph == null || Graph.getNode(src) == null || Graph.getNode(dest) == null) {
            return -1;
        }
        PriorityQueue<node_info> PQueue = new PriorityQueue<>();
        double shortestPathDist;
        node_info CurrentlyNode = Graph.getNode(src);
        PQueue.add(CurrentlyNode);
        CurrentlyNode.setTag(0);
        while (!PQueue.isEmpty()) {
            CurrentlyNode = PQueue.poll();
            if (!Objects.equals(CurrentlyNode.getInfo(), "A")) { //לדבר עם שמואל על זה שנוגעים בdata
                CurrentlyNode.setInfo("A");
                if (CurrentlyNode.getKey() == dest) {
                    break;
                }
                for (node_info node : Graph.getV(CurrentlyNode.getKey())) {
                    if (node.getTag() == -1) {
                        node.setTag(Double.MAX_VALUE);
                    }
                    double ProvisionalTag = CurrentlyNode.getTag() + Graph.getEdge(CurrentlyNode.getKey(), node.getKey());
                    if (ProvisionalTag < node.getTag()) {
                        node.setTag(ProvisionalTag);
                        PQueue.add(node);
                    }
                }
            }
        }
        CurrentlyNode = Graph.getNode(dest);
        shortestPathDist = CurrentlyNode.getTag();
        if (!Objects.equals(CurrentlyNode.getInfo(), "A")) {
            return -1;
        }
        for (node_info n : Graph.getV()) {
            if (n.getInfo() != null || n.getTag() != 0) {
                n.setTag(-1);
                n.setInfo(null);
            }
        }
        return shortestPathDist;
    }


    /**
     * returns the the shortest path between src to dest - as an ordered List of nodes:
     * src--> n1-->n2-->...dest
     * see: https://en.wikipedia.org/wiki/Shortest_path_problem
     * Note if no such path --> returns null;
     *
     * @param src  - start node
     * @param dest - end (target) node
     * @return
     */
    public List<node_info> shortestPath(int src, int dest) {
        if (Graph == null || src == dest || Graph.getNode(src) == null || Graph.getNode(dest) == null) {
            return null;
        }
        PriorityQueue<node_info> PQueue = new PriorityQueue<>();
        HashMap<node_info, node_info> parent = new HashMap<>();
        List<node_info> shortestPathDist = new ArrayList<>();
        node_info CurrentlyNode = Graph.getNode(src);
        PQueue.add(CurrentlyNode);
        CurrentlyNode.setTag(0);
        while (!PQueue.isEmpty()) {
            CurrentlyNode = PQueue.poll();
            if (!Objects.equals(CurrentlyNode.getInfo(), "A")) {
                CurrentlyNode.setInfo("A");
                if (CurrentlyNode.getKey() == dest) {
                    break;
                }
                for (node_info node : Graph.getV(CurrentlyNode.getKey())) {
                    if (node.getTag() == -1) {
                        node.setTag(Double.MAX_VALUE);
                    }
                    double ProvisionalTag = CurrentlyNode.getTag() + Graph.getEdge(CurrentlyNode.getKey(), node.getKey());
                    if (ProvisionalTag < node.getTag()) {
                        node.setTag(ProvisionalTag);
                        parent.put(node, CurrentlyNode);
                        PQueue.add(node);
                    }
                }
            }
        }
        CurrentlyNode = Graph.getNode(dest);
        if (!Objects.equals(CurrentlyNode.getInfo(), "A")) return null;
        shortestPathDist.add(0, CurrentlyNode);
        while (CurrentlyNode.getKey() != src) {
            shortestPathDist.add(0, parent.get(CurrentlyNode));
            CurrentlyNode = parent.get(CurrentlyNode);
        }
        for (node_info n : Graph.getV()) {
            if (n.getInfo() != null || n.getTag() != 0) {
                n.setTag(-1);
                n.setInfo(null);
            }
        }
        return shortestPathDist;
    }


    /**
     * Saves this weighted (undirected) graph to the given
     * file name
     *
     * @param file - the file name (may include a relative path).
     * @return true - iff the file was successfully saved
     */
    public boolean save(String file) {
        try {
            FileOutputStream f = new FileOutputStream(file, true);
            ObjectOutputStream graph = new ObjectOutputStream(f);
            graph.writeObject(Graph);
            f.close();
            graph.close();
            return true;
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }



    /**
     * This method load a graph to this graph algorithm.
     * if the file was successfully loaded - the underlying graph
     * of this class will be changed (to the loaded one), in case the
     * graph was not loaded the original graph should remain "as is".
     * @param file - file name
     * @return true - iff the graph was successfully loaded.
     */
    public boolean load(String file) {
        try {
            FileInputStream fi = new FileInputStream(file);
            ObjectInputStream gr = new ObjectInputStream(fi);
            this.Graph = (weighted_graph) gr.readObject();
            fi.close();
            gr.close();
            return true;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }

    }

    public boolean equals(Object obj) {
        weighted_graph g_obj = (WGraph_DS) obj;
        if (Graph.edgeSize() != g_obj.edgeSize() || Graph.nodeSize() != g_obj.nodeSize()) return false;
        return Objects.equals(Graph.toString(), g_obj.toString());
    }

}
