package ex2;

import java.io.*;
import java.util.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

class ThreadforShortestPath extends Thread{
    private DirectedWeightedGraphAlgorithmsClass dwgalgo;
    private int start, end;
    public ThreadforShortestPath(DirectedWeightedGraphAlgorithmsClass dwalgo, int start, int end){
        this.dwgalgo= dwalgo;
        this.start=start;
        this.end=end;
    }
    public void run(){
        for(int i=start;i< end;i++){
            for(int j=start;j< dwgalgo.dwgraph.nodeSize();j++){
                if(((Node)dwgalgo.dwgraph.getNode(i)).GetOutEdgeList().containsKey(j)){
                    dwgalgo.shortestdistarr[i][j]=((Node)dwgalgo.dwgraph.getNode(i)).GetOutEdgeList().get(j).getWeight();
                }
                else{
                    dwgalgo.shortestdistarr[i][j]=Double.MAX_VALUE/2;
                }
            }
        }
        for(int k=start; k< end;k++){
            for(int i=start; i< end;i++){
                for(int j=start; j< end;j++){
                    if(dwgalgo.shortestdistarr[i][j]>dwgalgo.shortestdistarr[i][k]+dwgalgo.shortestdistarr[k][j]){
                        dwgalgo.shortestdistarr[i][j]=dwgalgo.shortestdistarr[i][k]+dwgalgo.shortestdistarr[k][j];
                    }
                }
            }
        }
    }
}
public class DirectedWeightedGraphAlgorithmsClass implements DirectedWeightedGraphAlgorithms {
    public double[][] shortestdistarr;//matrix that keeps the shortest distance from every two nodes
    private boolean shortestdistrun;//keeps track of whether the shortestpathdist function has ran or not
    public DirectedWeightedGraphClass dwgraph;
    private boolean isconnected, isconnectedrun;//keeps track of whether the graph is connected or not (set to false before running the Isconnected() funct)
    public DirectedWeightedGraphAlgorithmsClass(DirectedWeightedGraphClass g){
        DirectedWeightedGraphClass thecoolerg= (DirectedWeightedGraphClass)g;
        dwgraph = new DirectedWeightedGraphClass(thecoolerg.getNodelist(), thecoolerg.getEdgelist(), g.getMC());
        shortestdistarr= new double[g.nodeSize()][g.nodeSize()];
        shortestdistrun=false;
        isconnected=false;
        isconnectedrun=false;
    }
    public DirectedWeightedGraphAlgorithmsClass(){
        shortestdistrun=false;
        isconnectedrun= false;
        isconnected= false;
    }
    @Override
    public void init(DirectedWeightedGraph g) {
        DirectedWeightedGraphClass thecoolerg= (DirectedWeightedGraphClass)g;
        dwgraph = new DirectedWeightedGraphClass(thecoolerg.getNodelist(), thecoolerg.getEdgelist(), g.getMC());
        shortestdistarr= new double[g.nodeSize()][g.nodeSize()];
        shortestdistrun=false;
        isconnectedrun=false;
        isconnected=false;
    }
    public DirectedWeightedGraphClass ReverseGraph(){
        DirectedWeightedGraphClass output= new DirectedWeightedGraphClass(dwgraph.getNodelist(), dwgraph.getEdgelist(), dwgraph.getMC());
        for(int i=0;i< output.edgeSize();i++){
            EdgeData temp= output.getEdgelist().remove(i);
            EdgeData temp2= new EdgeDataClass(i, (Node)output.getNode(temp.getDest()), (Node)output.getNode(temp.getSrc()), temp.getWeight(),temp.getInfo(), temp.getTag());
            output.getEdgelist().put(i,temp2);
            ((Node) output.getNode(temp.getSrc())).RemoveOutEdge(temp.getDest());
            ((Node) output.getNode(temp.getDest())).AddOutEdge(temp.getSrc(),temp);
        }
        return output;
    }

    @Override
    public DirectedWeightedGraph getGraph() {
        return dwgraph;
    }

    @Override
    public DirectedWeightedGraph copy() {
        return new DirectedWeightedGraphClass(dwgraph.getNodelist(), dwgraph.getEdgelist(), dwgraph.getMC());
    }
    public void DFS(int a){
        dwgraph.getNode(a).setTag(1);
        for(int n: ((Node)dwgraph.getNode(a)).getOutconnected()){
            if(dwgraph.getNode(n).getTag()!=1){
                DFS(n);
            }
        }
    }
    @Override
    public boolean isConnected(){
        if(isconnectedrun){
            return isconnected;
        }
        isconnectedrun=true;
        DFS(0);
        for (int i=0; i< dwgraph.nodeSize();i++){
            if(dwgraph.getNode(i).getTag()!=1){
                isconnected=false;
                return false;
            }
            dwgraph.getNode(i).setTag(0);
        }
        DirectedWeightedGraphClass temp= dwgraph;
        dwgraph=ReverseGraph();
        DFS(0);
        for (int i=0; i< dwgraph.nodeSize();i++){
            if(dwgraph.getNode(i).getTag()!=1){
                isconnected=false;
                dwgraph=temp;
                return false;
            }
            dwgraph.getNode(i).setTag(0);
        }
        isconnected=true;
        dwgraph=temp;
        return true;
    }
    @Override
    public double shortestPathDist(int src, int dest) {
        if(src==dest){
            return 0;
        }
        if(shortestdistarr[src][dest]>0){
            return shortestdistarr[src][dest];
        }
        else if(shortestdistrun){
            return -1;//the nodes are not connected
        }
        shortestdistrun=true;
        if(dwgraph.nodeSize()>50){
            ThreadforShortestPath t1= new ThreadforShortestPath(this,0,(int) Math.ceil(dwgraph.nodeSize()/4.0));
            ThreadforShortestPath t2= new ThreadforShortestPath(this,(int) Math.ceil(dwgraph.nodeSize()/4.0), (int) Math.ceil(dwgraph.nodeSize()/2.0));
            ThreadforShortestPath t3= new ThreadforShortestPath(this,(int) Math.ceil(dwgraph.nodeSize()/2.0),(int) Math.ceil(dwgraph.nodeSize()*(3.0/4)));
            t1.start();
            t2.start();
            t3.start();
            for(int i=(int) Math.ceil(dwgraph.nodeSize()*(3.0/4));i< dwgraph.nodeSize();i++){
                for(int j=(int) Math.ceil(dwgraph.nodeSize()*(3.0/4));j< dwgraph.nodeSize();j++){
                    if(((Node)dwgraph.getNode(i)).GetOutEdgeList().containsKey(j)){
                        shortestdistarr[i][j]=((Node)dwgraph.getNode(i)).GetOutEdgeList().get(j).getWeight();
                    }
                    else{
                        shortestdistarr[i][j]=Double.MAX_VALUE/2;
                    }
                }
            }
            for(int k=(int) Math.ceil(dwgraph.nodeSize()*(3.0/4)); k< dwgraph.nodeSize();k++){
                for(int i=(int) Math.ceil(dwgraph.nodeSize()*(3.0/4)); i< dwgraph.nodeSize();i++){
                    for(int j=(int) Math.ceil(dwgraph.nodeSize()*(3.0/4)); j< dwgraph.nodeSize();j++){
                        if(shortestdistarr[i][j]>shortestdistarr[i][k]+shortestdistarr[k][j]){
                            shortestdistarr[i][j]=shortestdistarr[i][k]+shortestdistarr[k][j];
                        }
                    }
                }
            }
        }
        else{
            for(int i=0;i< dwgraph.nodeSize();i++){
                for(int j=0;j< dwgraph.nodeSize();j++){
                    if(((Node)dwgraph.getNode(i)).GetOutEdgeList().containsKey(j)){
                        shortestdistarr[i][j]=((Node)dwgraph.getNode(i)).GetOutEdgeList().get(j).getWeight();
                    }
                    else{
                        shortestdistarr[i][j]=Double.MAX_VALUE/2;
                    }
                }
            }
            for(int k=0; k< dwgraph.nodeSize();k++){
                for(int i=0; i< dwgraph.nodeSize();i++){
                    for(int j=0; j< dwgraph.nodeSize();j++){
                        if(shortestdistarr[i][j]>shortestdistarr[i][k]+shortestdistarr[k][j]){
                            shortestdistarr[i][j]=shortestdistarr[i][k]+shortestdistarr[k][j];
                        }
                    }
                }
            }
        }

        return shortestdistarr[src][dest];
    }
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
            //reset all the node weight to infinite
            for (NodeData i:dwgraph.getNodelist().values()){
                i.setWeight(Double.POSITIVE_INFINITY/2);//setting it to (max number)/2 so when we add it to itself it doesnt turn into a negative number
            }
            dwgraph.getNode(src).setWeight(0.0);

            HashSet<NodeData> settled= new HashSet<>();
            HashSet<NodeData> unsettled= new HashSet<>();

            unsettled.add(dwgraph.getNode(src));
            while (unsettled.size()!=0) {
                Node curr = getLow(unsettled);
                unsettled.remove(curr);
                curr.setTag(1);
                for (Integer i : curr.getOutconnected()) {
                    Node adj = (Node) dwgraph.getNode(i);
                    adj.setTag(1);
                    double edgeweight = dwgraph.getEdge(curr.getKey(), i).getWeight();

                    if (!settled.contains(adj)) {
                        calculatMinCost(adj, edgeweight, curr);
                        if (i != dest) {
                            unsettled.add(adj);
                        }else {
                            ans.add(dwgraph.getNode(dest));
                            return ans;
                        }
                    }
                    settled.add(curr);
                    ans.add(curr);
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
    public List<NodeData> NewshortestPath(int src, int dest){
        List<NodeData> list= new LinkedList<>();
        if(shortestPathDist(src, dest)==Double.MAX_VALUE/2|| shortestPathDist(src, dest)==Double.MAX_VALUE){
            return null;
        }
        return Newhelper(src, dest, list);
    }
    public List<NodeData> Newhelper(int src, int dest, List<NodeData> list){
        list.add(dwgraph.getNode(src));
        if(src==dest){
            return list;
        }
        Node srcnode= (Node) dwgraph.getNode(src);
        double closest= Double.MAX_VALUE;
        int closestindex= 0;
        for(int i: srcnode.getOutconnected()){
            if(shortestPathDist(i,dest)+shortestPathDist(src,i)<closest){
                closest=shortestPathDist(i,dest)+shortestPathDist(src,i);
                closestindex= i;
            }
        }
        return Newhelper(closestindex,dest, list);
    }
    @Override
    public NodeData center() {//The function calculates the distances from every node to every other node and returns the node which is closest to its furthest node (the node where the path from it to the node furthest away from it is the shortest).
        long starttime= System.currentTimeMillis();
        if(!this.isConnected()){
            System.out.println("Graph is not connected");
            System.out.println("Start time= "+starttime);
            System.out.println("Stop time= "+System.currentTimeMillis());
            return null;
        }
        System.out.println("Graph is connected");
        double[] maximumshortestpath= new double[dwgraph.nodeSize()];//for every i, maximumshortestpath[i] keeps the length of the path from it to the node furthest away from it.
        shortestPathDist(0,1);
        for(int i=0; i< dwgraph.nodeSize();i++){
            double maxdist=Double.MIN_VALUE;
            for(int j=0; j< dwgraph.nodeSize();j++){
                if(j!=i){
                    if(shortestdistarr[i][j]>maxdist){
                        maxdist=shortestdistarr[i][j];
                    }
                }
            }
            maximumshortestpath[i]= maxdist;
        }
        int minnode=0;
        double mindist=Double.MAX_VALUE;
        for(int i=0; i< dwgraph.nodeSize();i++){
            if(maximumshortestpath[i]<mindist){
                mindist=maximumshortestpath[i];
                minnode=i;
            }
        }
        System.out.println("Start time= "+starttime);
        System.out.println("Stop time= "+System.currentTimeMillis());
        return dwgraph.getNode(minnode);
    }
    @Override
    public List<NodeData> tsp(List<NodeData> cities) {
        List<NodeData>[] pathsarr= new List[cities.size()];
        int arrindex=0;
        NodeData first= cities.remove(0);
        for(NodeData currnode: cities){
            boolean passedall=true;
            List<NodeData> path= NewshortestPath(first.getKey(), currnode.getKey());
            if(path!=null && path.size()>= cities.size()){
                for(NodeData x: cities){
                    if(x.getTag()!=1){
                        passedall=false;
                    }
                }
                if(passedall){
                    pathsarr[arrindex]= path;
                    arrindex++;
                }
            }
        }
        int shortestpath=Integer.MAX_VALUE;
        int index=0;
        for(int i=0; i<arrindex;i++){
            if(shortestpath>pathsarr[i].size()){
                shortestpath=pathsarr[i].size();
                index=i;
            }
        }
        return pathsarr[index];
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
                double weight;
                try{
                    weight= ((Long)newedge.get("w")).doubleValue();
                }
                catch (ClassCastException e){
                    weight= (double)newedge.get("w");
                }

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
                dwgraph = new DirectedWeightedGraphClass(nodelist,edgelist,0);
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
