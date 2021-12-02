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
        if(graph.getMC()!=modcount){
            Exception e= new RuntimeException("This Iterator is outdated");
            try {
                throw e;
            } catch (Exception exception) {
                exception.printStackTrace();
            }
            return false;
        }
        return true;
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
        graph.getNodelist().remove(current-1);
    }
}
