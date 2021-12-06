package ex2;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class DirectedWeightedGraphAlgorithmsClass implements DirectedWeightedGraphAlgorithms {
    private DirectedWeightedGraphClass dwgraph;
    @Override
    public void init(DirectedWeightedGraph g) {
        DirectedWeightedGraphClass thecoolerg= (DirectedWeightedGraphClass)g;
        dwgraph=new DirectedWeightedGraphClass(thecoolerg.getNodelist(), thecoolerg.getEdgelist(), g.getMC());
    }

    @Override
    public DirectedWeightedGraph getGraph() {
        return dwgraph;
    }

    @Override
    public DirectedWeightedGraph copy() {
        return new DirectedWeightedGraphClass(dwgraph.getNodelist(), dwgraph.getEdgelist(), dwgraph.getMC());
    }
    public boolean Areconnected(Node a, Node b){
        Queue<Node> q= new LinkedList<>();
        LinkedList<Node> visited= new LinkedList<>();
        
    }
    @Override
    public boolean isConnected() {
        return false;
    }

    @Override
    public double shortestPathDist(int src, int dest) {
              if(src==dest) return 0;
        //reset all the node wegiht to infinite
        for (NodeData i:dwgraph.getNodelist().values()){
            i.setWeight(Double.POSITIVE_INFINITY);
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
