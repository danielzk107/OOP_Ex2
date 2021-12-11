package ex2;

import java.util.HashMap;
import java.util.Iterator;

public class DirectedWeightedGraphClass implements DirectedWeightedGraph {
    private HashMap<Integer,NodeData> nodeList;
    private HashMap<Integer,EdgeData> edgelist;
    private int modcounter;
    public DirectedWeightedGraphClass() {
        this.nodeList = new HashMap<>();
        this.edgelist = new HashMap<>();
        this.modcounter = 0;
    }
    public DirectedWeightedGraphClass(HashMap<Integer,NodeData> nodeList, HashMap<Integer,EdgeData> edgelist, int modecounter) {
        this.nodeList = nodeList;
        this.edgelist = edgelist;
        this.modcounter = modecounter;
    }
    public void setNodelist(HashMap<Integer, NodeData> nodeList) {
        this.nodeList = nodeList;
        modcounter++;
    }
    public HashMap<Integer, NodeData> getNodelist() {
        return nodeList;
    }
    public void setEdgelist(HashMap<Integer, EdgeData> edgelist) {
        this.edgelist = edgelist;
        modcounter++;
    }
    public HashMap SetTagForNode(int node, int tag){
        NodeData temp= nodeList.remove(node);
        temp.setTag(tag);
        nodeList.put(node, temp);
        return  nodeList;
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
        return temp.GetOutEdge(dest);
    }

    @Override
    public void addNode(NodeData n) {
        nodeList.put(n.getKey(), n);
        modcounter++;
    }

    @Override
    public void connect(int src, int dest, double w) {
        Node srcnode = (Node)nodeList.get(src);
        Node destnode= (Node)nodeList.get(dest);
        EdgeDataClass temp= new EdgeDataClass(edgelist.size()+1,srcnode, destnode, w, "", 0);
        edgelist.put(temp.getId(), temp);
        srcnode.AddOutEdge(dest, temp);
        destnode.AddInEdge(src, temp);
        modcounter++;
    }

    @Override
    public Iterator<NodeData> nodeIter() {
        Iterator<NodeData> iterator= new NodelistIterator(0, this);
        return iterator;
    }

    @Override
    public Iterator<EdgeData> edgeIter() {
        Iterator<EdgeData> iterator= new EdgelistIterator(0, this);
        return iterator;
    }

    @Override
    public Iterator<EdgeData> edgeIter(int node_id) {
        Node temp= (Node)getNode(node_id);
        HashMap<Integer, EdgeData> list= new HashMap<>();
        HashMap<Integer, EdgeData> intemp= temp.GetInEdgeList();
        HashMap<Integer, EdgeData> outtemp= temp.GetOutEdgeList();
        EdgeDataClass edge;
        for(int i:temp.getAllconnected()){
            if(intemp.containsKey(i)){
                edge= (EdgeDataClass)intemp.remove(i);
            }
            else{
                edge= (EdgeDataClass)outtemp.remove(i);
            }
            list.put(edge.getId(), edge);
        }
        Iterator<EdgeData> iterator= new EdgelistIterator(0,this,list);
        return iterator;
    }

    @Override
    public NodeData removeNode(int key) {
        Node removed= (Node)nodeList.remove(key);
        EdgelistIterator iterator = (EdgelistIterator)edgeIter(key);
        for(int i: removed.getAllconnected()){
            Node temp = (Node)nodeList.get(i);
            temp.RemoveEdge(removed.getKey());
        }
        while(!iterator.isEmpty()){
            iterator.remove();
        }
        modcounter++;
        return removed;
    }

    @Override
    public EdgeData removeEdge(int src, int dest) {
        Node srcnode= (Node)nodeList.get(src);
        Node destnode= (Node)nodeList.get(dest);
        destnode.RemoveInEdge(src);
        modcounter++;
        return srcnode.RemoveOutEdge(dest);
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
        return modcounter;
    }
}
