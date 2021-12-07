package ex2;

import java.util.Iterator;

public class NodelistIterator implements Iterator {
    private int current;
    private int modcount;
    private DirectedWeightedGraphClass graph;
    public NodelistIterator(int start, DirectedWeightedGraphClass graph){
        current=start;
        this.graph= graph;
        modcount= graph.getMC();
    }
    public boolean Isoutdated(){
        try {
            if(graph.getMC()!=modcount){
                throw new RuntimeException("This Iterator is outdated");
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return true;
    }
    public boolean isEmpty(){
        Isoutdated();
        return graph.getNodelist().isEmpty();
    }
    @Override
    public boolean hasNext() {
        Isoutdated();
        return graph.getNodelist().containsKey(current+1);
    }

    @Override
    public Object next() {
        Isoutdated();
        NodeData temp= graph.getNodelist().get(current++);
        return temp;
    }

    @Override
    public void remove() {
        Isoutdated();
        graph.removeNode(current);
        current++;
    }
}
