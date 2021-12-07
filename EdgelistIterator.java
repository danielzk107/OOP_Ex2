package ex2;

import java.util.HashMap;
import java.util.Iterator;

public class EdgelistIterator implements Iterator {
    private int current;
    private int modcount;
    private DirectedWeightedGraphClass graph;
    private HashMap<Integer,EdgeData> list;
    public EdgelistIterator(int start, DirectedWeightedGraphClass graph){
        current=start;
        this.graph=graph;
        modcount= graph.getMC();
        this.list= graph.getEdgelist();
    }
    public EdgelistIterator(int start, DirectedWeightedGraphClass graph, HashMap<Integer, EdgeData> list){
        current=start;
        this.graph=graph;
        modcount= graph.getMC();
        this.list= list;
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
        return list.isEmpty();
    }
    @Override
    public boolean hasNext() {
        Isoutdated();
        return list.containsKey(current+1);
    }

    @Override
    public Object next() {
        Isoutdated();
        EdgeData temp= list.get(current++);
        return temp;
    }

    @Override
    public void remove() {
        Isoutdated();
        list.remove(current-1);
        current++;
    }
}
