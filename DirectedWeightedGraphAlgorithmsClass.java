package ex2;

import java.io.*;
import java.util.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class DirectedWeightedGraphAlgorithmsClass implements DirectedWeightedGraphAlgorithms {
    private DirectedWeightedGraphClass dwgraph;
    public DirectedWeightedGraphAlgorithmsClass(DirectedWeightedGraphClass g){
        this.dwgraph=g;
    }
    public DirectedWeightedGraphAlgorithmsClass(){
        this.dwgraph=null;
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
            for (Integer i :node.getConnected()){
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
    public NodeData center() {
        if(!isConnected()){
            return null;
        }
        int[] numberofapereances= new int[dwgraph.nodeSize()];
        int[] minarr= new int[dwgraph.nodeSize()];//keeps the node that is closest(shortest path) to the node represented by minarr[i]
        for(int i=0; i< dwgraph.nodeSize();i++){
            double[] distance=new double[dwgraph.nodeSize()];//keeps the distance between the node i and the node distance[j]
            minarr[i]=0;//placeholder that would change shortly(unless it fits the requirements)
            for(int j=0; j< dwgraph.nodeSize();j++){
                distance[j]=shortestPathDist(i,j);
            }
            for(int j=0; j< dwgraph.nodeSize();j++){
                if(i!=j){
                    if(distance[minarr[i]]>distance[j]){
                        numberofapereances[j]++;
                        numberofapereances[minarr[i]]--;
                        minarr[i]=j;
                    }
                }
            }
        }
        int max=0, maxnum=Integer.MIN_VALUE;
        for(int i=0; i< dwgraph.nodeSize();i++){
            if(numberofapereances[i]>maxnum){
                max=i;
            }
        }
        return dwgraph.getNode(max);
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

    @Override
    public boolean save(String file) {
             try {
            FileOutputStream f = new FileOutputStream(file);
            ObjectOutputStream o = new ObjectOutputStream(f);
            o.writeObject(this.dwgraph);
            f.close();
            o.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean load(String file) {
        JSONParser parser= new JSONParser();
        boolean problem=false;
        try{
            System.out.println("A");
            FileReader read= new FileReader(file);
            JSONObject arr= (JSONObject)parser.parse(read);
            JSONArray edges= (JSONArray)arr.get("Edges");
            JSONArray nodes= (JSONArray)arr.get("Nodes");
            HashMap<Integer,NodeData> nodelist= new HashMap<>();
            HashMap<Integer, EdgeData> edgelist= new HashMap<>();
            for (Object o: nodes){
                JSONObject newnode= (JSONObject)o;
                int id = (int)newnode.get("id");
                String position= (String)newnode.get("pos");
                String[] posarr= position.split(",");
                nodelist.put(id, new Node(id, 0, new GeoLocationClass(Double.parseDouble(posarr[0]),Double.parseDouble(posarr[1]),Double.parseDouble(posarr[2])),""));
            }
            int edgecounter=0;
            for(Object o: edges){
                JSONObject newedge= (JSONObject)o;
                int src= (int)newedge.get("src");
                int dest= (int)newedge.get("dest");
                double weight= (double)newedge.get("w");
                Node srcnode=(Node)nodelist.get(src);
                Node destnode= (Node)nodelist.get(dest);
                if(srcnode==null || destnode==null){
                    System.out.println("One of the given edges uses a non-existing node");
                    problem=true;
                    break;
                }
                EdgeDataClass edge= new EdgeDataClass(edgecounter, srcnode, destnode, weight, "", 0);
                srcnode.AddOutEdge(dest,edge);
                destnode.AddInEdge(src, edge);
                edgelist.put(edgecounter, edge);
            }
            if(problem){
                System.out.println("The program has not initiated a new graph because it was given a faulty one.");
            }
            else{
                init(new DirectedWeightedGraphClass(nodelist,edgelist,0));
            }
        }
        catch (Exception e){
            System.err.println("File not Found");
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
