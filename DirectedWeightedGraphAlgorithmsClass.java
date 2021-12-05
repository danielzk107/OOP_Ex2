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
        int NS= dwgraph.nodeSize();
        boolean [] vis= new boolean[NS];
        for (int i=0 ; i<NS ; i++){
            vis[i]=false;
        }
        double [] dist=new double[NS];
        Arrays.fill(dist,Double.POSITIVE_INFINITY);
        dist[src]=0.0;

        PriorityQueue<NodeData> q = new PriorityQueue<>();
        q.add(dwgraph.getNode(src));
        dist[src]=0.0;
        dwgraph.getNode(src).setWeight(0.0);

        while (!q.isEmpty()){
            Node node = (Node)q.poll();
            int nodeKey = q.peek().getKey();
            vis[nodeKey]=true;

            if (dist[nodeKey] < node.getWeight()) continue;
            for (EdgeData i : dwgraph.getEdgelist().values()){

            }


        }
        return 0;
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
