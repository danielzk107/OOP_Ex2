package ex2;

import java.io.*;
import java.util.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class DirectedWeightedGraphAlgorithmsClass implements DirectedWeightedGraphAlgorithms {
    List<Node>[] arr1, arr2;//arrays for the isConnected function
    private double[][] shortestdistarr;//matrix that keeps the shortest distance from every two nodes
    private boolean shortestdistrun;//keeps track of whether the shortestpathdist function has ran or not
    private DirectedWeightedGraphClass dwgraph;
    public DirectedWeightedGraphAlgorithmsClass(DirectedWeightedGraphClass g){
        this.dwgraph=g;
        shortestdistrun=false;
        arr1= new LinkedList[g.nodeSize()];
        arr2= new LinkedList[g.nodeSize()];
        for(int i=0; i<g.nodeSize();i++){
            arr1[i]= new LinkedList<>();
            arr2[i]= new LinkedList<>();
        }
    }
    public DirectedWeightedGraphAlgorithmsClass(){
        shortestdistrun=false;
    }
    @Override
    public void init(DirectedWeightedGraph g) {
        DirectedWeightedGraphClass thecoolerg= (DirectedWeightedGraphClass)g;
        dwgraph = new DirectedWeightedGraphClass(thecoolerg.getNodelist(), thecoolerg.getEdgelist(), g.getMC());
        shortestdistrun=false;
        arr1= new LinkedList[g.nodeSize()];
        arr2= new LinkedList[g.nodeSize()];
        for(int i=0; i<g.nodeSize();i++){
            Node temp= (Node)dwgraph.getNode(i);
            arr1[i]= new LinkedList<>();
            arr2[i]= new LinkedList<>();
            for(int j = temp.getOutconnected().size()-1, k=0; j>=0; j--, k++){
                arr1[i].add((Node) dwgraph.getNode(temp.getOutconnected().get(j)));
                arr2[i].add((Node) dwgraph.getNode(temp.getOutconnected().get(j)));
            }
        }
    }

    @Override
    public DirectedWeightedGraph getGraph() {
        return dwgraph;
    }

    @Override
    public DirectedWeightedGraph copy() {
        return new DirectedWeightedGraphClass(dwgraph.getNodelist(), dwgraph.getEdgelist(), dwgraph.getMC());
    }
    public void DFS1(int a){
        dwgraph.getNode(a).setTag(1);
        for(Node n: arr1[a]){
            if(n.getTag()!=1){
                DFS1(n.getKey());
            }
        }
    }
    public void DFS2(int b){
        dwgraph.getNode(b).setTag(1);
        for (Node n: arr2[b]){
            if(n.getTag()!=1){
                DFS2(n.getKey());
            }
        }
    }
    @Override
    public boolean isConnected() {
        DFS1(1);
        DFS2(1);
        for (int i=1; i< dwgraph.nodeSize();i++){
            if(dwgraph.getNode(i).getTag()!=1){
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
        dwgraph.getNode(src).setWeight(0.0);

        HashSet<NodeData> settled= new HashSet<>();
        HashSet<NodeData> unsettled= new HashSet<>();

        unsettled.add(dwgraph.getNode(src));
        while (unsettled.size()!=0){
            Node curr= getLow(unsettled);
            unsettled.remove(curr);
            for (Integer i : curr.getOutconnected()) {
                Node adj= (Node) dwgraph.getNode(i);
                System.out.println("src= "+curr.getKey()+", dest= "+i);
                double edgeweight= dwgraph.getEdge(curr.getKey(),i).getWeight();
                if(!settled.contains(adj)){
                  calculatMinCost(adj,edgeweight,curr);
                  unsettled.add(adj);
                }
            }
            settled.add(curr);
        }
        if (dwgraph.getNode(dest).getWeight() < Double.POSITIVE_INFINITY) {// if you get to the dest node return his weight
            return dwgraph.getNode(dest).getWeight();
        }
        return -1;
    }
//    public double NewShortestPathDist(int src, int dest){
//        if(shortestdistarr[src][dest]>0){
//            return shortestdistarr[src][dest];
//        }
//        else if(shortestdistrun){
//            return -1;//the nodes are not connected
//        }
//        for(int i=0;i< dwgraph.nodeSize();i++){
//            for(int j=0;j< dwgraph.nodeSize();j++){
//                if(((Node)dwgraph.getNode(i)).GetOutEdgeList().containsKey(j)){
//                    shortestdistarr[i][j]=((Node)dwgraph.getNode(i)).GetOutEdgeList().get(j).getWeight();
//                }
//                else{
//                    shortestdistarr[i][j]=Double.MAX_VALUE/2;
//                }
//            }
//        }
//        for(int k=0; k< dwgraph.nodeSize();k++){
//            for(int i=0; i< dwgraph.nodeSize();i++){
//                for(int j=0; j< dwgraph.nodeSize();j++){
//                    if(shortestdistarr[i][j]>shortestdistarr[i][k]+shortestdistarr[k][j]){
//                        shortestdistarr[i][j]=shortestdistarr[i][k]+shortestdistarr[k][j];
//                    }
//                }
//            }
//        }
//        return shortestdistarr[src][dest];
//    }
    
    private Node getLow(HashSet<NodeData> unsettled) {
        NodeData low=null;
        double lowestWeight= Integer.MAX_VALUE;
        for (NodeData n: unsettled) {
            double nodeW= n.getWeight();
            if (nodeW<lowestWeight){
                lowestWeight= nodeW;
                low= n;
            }
        }
        return (Node) low;
    }
    
      private void calculatMinCost(Node adj, double edgeweight, Node curr) {
        double srcW = curr.getWeight();
        if (srcW + edgeweight < adj.getWeight()) {
            adj.setWeight(srcW+edgeweight);
            LinkedList<NodeData> shortestPath= new LinkedList<>(curr.getShortestPath());
            shortestPath.add(curr);
            adj.setShortestPath(shortestPath);
        }
    }
    
    
    //helper function
    private List<NodeData> helper(int src, int dest) {
        List<NodeData> ans= new LinkedList<>();
        for (NodeData i:dwgraph.getNodelist().values()){
            i.setWeight(Double.POSITIVE_INFINITY);
        }

        Queue<NodeData> q = new LinkedList<>();
        q.add(dwgraph.getNode(src));//add src in the queue
        dwgraph.getNode(src).setWeight(0.0);

        while (!q.isEmpty()){
            Node node = (Node) q.poll();
            q.remove(node);//visited
            for (Integer i :node.getAllconnected()){
                //if w(src)+w(edge(src,neighbor))< w(neighbor)
                double weight=(node.getWeight()+dwgraph.getEdge(node.getKey(),i).getWeight());
                if ( weight < dwgraph.getNode(i).getWeight()){
                    if (i!=dest){
                        q.add(dwgraph.getNode(i));
                    }
                    dwgraph.getNode(i).setWeight(weight);
                    ans.add(dwgraph.getNode(i)); //add to the list of shortestpath map
                }

            }
        }
        return ans;
    }
    @Override
    public List<NodeData> shortestPath(int src, int dest) {
        List<NodeData> ans;
        ans=helper(src,dest);
        return ans;
    }
    @Override
    public NodeData center() {//The function calculates the distances from every node to every other node and returns the node which is closest to its furthest node (the node where the path from it to the node furthest away from it is the shortest).
        if(!this.isConnected()){
            return null;
        }
        double[] maximumshortestpath= new double[dwgraph.nodeSize()];//for every i, maximumshortestpath[i] keeps the length of the path from it to the node furthest away from it.
        for(int i=0; i< dwgraph.nodeSize();i++){
            double[] distance=new double[dwgraph.nodeSize()];//keeps the distance between the node i and the node distance[j]
            for(int j=0; j< dwgraph.nodeSize();j++){
                distance[j]=shortestPathDist(i,j);
            }
            Arrays.sort(distance);
            maximumshortestpath[i]= distance[0];
        }
        int minnode=0;
        double mindist=Double.MAX_VALUE;
        for(int i=0; i< dwgraph.nodeSize();i++){
            if(maximumshortestpath[i]<mindist){
                mindist=maximumshortestpath[i];
                minnode=i;
            }
        }
        return dwgraph.getNode(minnode);
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
        //UNFINISHED
        return null;
    }
  //helper function
     public JSONObject toJson(int src,double w, int dest){
        JSONObject json =new JSONObject();
        json.put("src",src);
        json.put("w",w);
        json.put("dest",dest);

        return json;
    }
  //helper function
    public JSONObject nodetoJ(String pos, int id){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("pos",pos);
        jsonObject.put("id",id);

        return jsonObject;
    }
    @Override
    public boolean save(String file) {
        JSONObject object = new JSONObject();
        JSONObject arr = new JSONObject();
        JSONArray nodes = new JSONArray();
        JSONArray edges = new JSONArray();
        for (EdgeData o : dwgraph.getEdgelist().values()) {
           JSONObject newEd= toJson(o.getSrc(),o.getWeight(),o.getDest());
           edges.add(newEd);
        }
        for (NodeData o : dwgraph.getNodelist().values()) {
            String x=String.valueOf(o.getLocation().x());
            String y=String.valueOf(o.getLocation().y());
            String z=String.valueOf(o.getLocation().z());
            String pos= x+","+y+","+z;
            JSONObject newnode= nodetoJ(pos,o.getKey());
            nodes.add(newnode);
        }
        arr.put("Edges", edges);
        arr.put("Nodes", nodes);
try {
    FileWriter fw= new FileWriter(file);
    fw.write(arr.toJSONString());
    fw.close();
}
   catch (FileNotFoundException e){
        e.printStackTrace();
        return false;
   } catch (Exception e){
        e.printStackTrace();
        return false;
   }

    return true;

    }
    @Override
    public boolean load(String file) {
        JSONParser parser= new JSONParser();
        boolean problem=false;
        String problemdescription="One of the given nodes has some data in a wrong format.";
        try{
            FileReader read= new FileReader(file);
            JSONObject arr= (JSONObject)parser.parse(read);
            JSONArray edges= (JSONArray)arr.get("Edges");
            JSONArray nodes= (JSONArray)arr.get("Nodes");
            HashMap<Integer,NodeData> nodelist= new HashMap<>();
            HashMap<Integer, EdgeData> edgelist= new HashMap<>();
            for (Object o: nodes){
                JSONObject newnode= (JSONObject)o;
                int id = Math.toIntExact((Long)newnode.get("id"));
                String position= (String)newnode.get("pos");
                String[] posarr= position.split(",");
                nodelist.put(id, new Node(id, 0, new GeoLocationClass(Double.parseDouble(posarr[0]),Double.parseDouble(posarr[1]),Double.parseDouble(posarr[2])),""));
            }
            int edgecounter=0;
            for(Object o: edges){
                JSONObject newedge= (JSONObject)o;
                int src= Math.toIntExact((Long)newedge.get("src"));
                int dest= Math.toIntExact((Long)newedge.get("dest"));
                double weight= (double)newedge.get("w");
                Node srcnode=(Node)nodelist.get(src);
                Node destnode= (Node)nodelist.get(dest);
                if(srcnode==null || destnode==null){
                    problemdescription="One of the given edges uses a non-existing node.";
                    problem=true;
                    break;
                }
                EdgeDataClass edge= new EdgeDataClass(edgecounter, srcnode, destnode, weight, "", 0);
                srcnode.AddOutEdge(dest,edge);
                destnode.AddInEdge(src, edge);
                edgelist.put(edgecounter, edge);
                edgecounter++;
            }
            if(problem){
                throw new Exception(problemdescription);
            }
            else{
                init(new DirectedWeightedGraphClass(nodelist,edgelist,0));
            }
        }
        catch (FileNotFoundException e){
            System.err.println("File not Found");
            return false;
        }
        catch (Exception e){
            System.err.println("File Found but has incorrect/inconsistent data:");
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
