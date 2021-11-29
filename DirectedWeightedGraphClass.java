package ex2;

import org.w3c.dom.NodeList;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class DirectedWeightedGraphClass implements DirectedWeightedGraph {
    private HashMap<Integer,NodeData> nodeList;
    private HashMap<Integer, EdgeData> edgelist;
    private int modecounter;
    public DirectedWeightedGraphClass() {
        this.nodeList = new HashMap<>();
        this.edgelist = new HashMap<>();
        this.modecounter = 0;
    }
    public DirectedWeightedGraphClass(HashMap<Integer,NodeData> nodeList, HashMap<Integer,EdgeData> edgelist, int modecounter) {
        this.nodeList = nodeList;
        this.edgelist = edgelist;
        this.modecounter = modecounter;
    }
    public void setNodeList(HashMap<Integer, NodeData> nodeList) {
        this.nodeList = nodeList;
    }
    public HashMap<Integer, NodeData> getNodeList() {
        return nodeList;
    }
    public void setEdgelist(HashMap<Integer, EdgeData> edgelist) {
        this.edgelist = edgelist;
    }
    public HashMap<Integer, EdgeData> getEdgelist() {
        return edgelist;
    }
    @Override
    public NodeData getNode(int key) {
        return nodeList.get(key);
    }

    @Override
    public EdgeData getEdge(int src, int dest) {
        Node temp= (Node)nodeList.get(src);
        return temp.GetEdge(dest);
    }

    @Override
    public void addNode(NodeData n) {
        nodeList.put(n.getKey(), n);
    }

    @Override
    public void connect(int src, int dest, double w) {
        Node srcnode = (Node)nodeList.get(src);
        Node destnode= (Node)nodeList.get(dest);
        EdgeDataClass temp= new EdgeDataClass(srcnode, destnode, w, "", 0);
        srcnode.AddEdge(dest,temp);
    }

    @Override
    public Iterator<NodeData> nodeIter() {
        return null;
    }

    @Override
    public Iterator<EdgeData> edgeIter() {
        return null;
    }

    @Override
    public Iterator<EdgeData> edgeIter(int node_id) {
        return null;
    }

    @Override
    public NodeData removeNode(int key) {
        Node temp= (Node)nodeList.remove(key);
        return temp;
    }

    @Override
    public EdgeData removeEdge(int src, int dest) {
        Node srcnode = (Node)nodeList.get(src);
        EdgeData temp = srcnode.GetEdge(dest);
        return temp;
    }

    @Override
    public int nodeSize() {
        return nodeList.size();
    }

    @Override
    public int edgeSize() {
        return edgelist.size();
    }

    @Override
    public int getMC() {
        return modecounter;
    }
}
