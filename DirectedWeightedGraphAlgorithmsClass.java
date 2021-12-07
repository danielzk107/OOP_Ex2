package ex2;

import java.security.KeyPair;
import java.util.*;

public class DirectedWeightedGraphAlgorithmsClass implements DirectedWeightedGraphAlgorithms {
    private DirectedWeightedGraphClass dwgraph;
    public DirectedWeightedGraphAlgorithmsClass(DirectedWeightedGraphClass g){
        this.dwgraph=g;
    }
    @Override
    public void init(DirectedWeightedGraph g) {
        DirectedWeightedGraphClass thecoolerg= (DirectedWeightedGraphClass)g;
        dwgraph = new DirectedWeightedGraphClass(thecoolerg.getNodelist(), thecoolerg.getEdgelist(), g.getMC());
    }

    @Override
    public DirectedWeightedGraph getGraph() {
        return dwgraph;
    }

    @Override
    public DirectedWeightedGraph copy() {
        return new DirectedWeightedGraphClass(dwgraph.getNodelist(), dwgraph.getEdgelist(), dwgraph.getMC());
    }
    public boolean[] DFS(int a, boolean arr[]){
        arr[a]= true;
        Node anode= (Node)dwgraph.getNode(a);
        for(int i: anode.getConnected()){
            if(!arr[i]){
                DFS(i, arr);
            }
        }
        return arr;
    }
    @Override
    public boolean isConnected() {
        boolean arr1[]= new boolean[dwgraph.nodeSize()+1];
        boolean arr2[]= new boolean[dwgraph.nodeSize()];
        Arrays.fill(arr1, false);
        Arrays.fill(arr2, false);
        arr1= DFS(1, arr1);
        for (int i=1; i< dwgraph.nodeSize();i++){
            if(!arr1[i]){
                return false;
            }
        }
        return true;
    }

    @Override
    public double shortestPathDist(int src, int dest) {
              if(src==dest) return 0;
        //reset all the node weight to infinite
        for (NodeData i:dwgraph.getNodelist().values()){
            i.setWeight(Double.POSITIVE_INFINITY/2);//setting it to (max number)/2 so when we add it to itself it doesnt turn into a negative number
        }
        Queue<NodeData> q = new LinkedList<>();
        q.add(dwgraph.getNode(src));//add src in the queue
        dwgraph.getNode(src).setWeight(0.0);
        while (!q.isEmpty()){
            Node node = (Node) q.poll();
            q.remove(node);//visited
            for (Integer i :node.getConnected()){
                //if w(src)+w(edge(src,neighbor))< w(neighbor)
                double weight=(node.getWeight()+dwgraph.getEdge(node.getKey(),i).getWeight());
                if ( weight < dwgraph.getNode(i).getWeight()){
                    if (i!=dest){
                        q.add(dwgraph.getNode(i));
                    }
                    dwgraph.getNode(i).setWeight(weight);
                }
            }
        }
        if(dwgraph.getNode(dest).getWeight()<Double.POSITIVE_INFINITY){// if you get to the dest node return his weight
                return dwgraph.getNode(dest).getWeight();
        }
        return -1;
    }

    @Override
    public List<NodeData> shortestPath(int src, int dest) {
        return null;
    }

    @Override
    public NodeData center() {
        return null;
    }

    @Override
    public List<NodeData> tsp(List<NodeData> cities) {
//        HashMap<Integer,NodeData> nodelist= new HashMap<>();
//        HashMap<Integer,EdgeData> edgelist= new HashMap<>();
//        for(NodeData node: cities){
//            nodelist.put(node.getKey(), node);
//            Node temp= (Node)node;
//            for(int i:temp.getConnected()){
//                if(cities.contains(dwgraph.getNode(i))){
//                    if(temp.GetInEdge(i)!=null){
//                        EdgeDataClass tempedge =(EdgeDataClass)temp.GetInEdge(i);
//                        edgelist.put(temp.getKey(), tempedge);
//                    }
//                    if(temp.GetOutEdge(i)!=null){
//                        EdgeDataClass tempedge =(EdgeDataClass)temp.GetOutEdge(i);
//                        edgelist.put(temp.getKey(), tempedge);
//                    }
//                }
//            }
//        }
//        DirectedWeightedGraphAlgorithmsClass newalgo= new DirectedWeightedGraphAlgorithmsClass(new DirectedWeightedGraphClass(nodelist,edgelist,0));
//        if(!newalgo.isConnected()){
//            return null;
//        }
        HashMap<Integer, HashMap<Integer, List<Node>>> shortestPathmap = new HashMap<>();
        for(NodeData temp: cities){
            Node node= (Node)temp;
            for(int i=0; i< dwgraph.nodeSize();i++){
                if(shortestPathmap.containsKey(temp.getKey())){

                }
                else {
                    shortestPathmap.put(node.getKey(), new HashMap<>());
                }
            }
        }















        return null;
    }

    @Override
    public boolean save(String file) {
        return false;
    }

    @Override
    public boolean load(String file) {
        return false;
    }
}
