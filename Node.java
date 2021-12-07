package ex2;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class Node implements NodeData {
    private int key;
    private int tag;
    private List<Integer> connected;//Keeps track of all the nodes that are connected to this one.
    private GeoLocation location;
    private double weight;
    private String info;
    private HashMap<Integer, EdgeData> outedgelist;
    private HashMap<Integer, EdgeData> inedgelist;
    public Node(int key, int tag, GeoLocation location, String info){
        this.key=key;
        this.tag=tag;
        setLocation(location);
        this.info=info;
        this.outedgelist= new HashMap<>();
        this.inedgelist= new HashMap<>();
        connected= new LinkedList<>();
    }
    public void AddOutEdge(int dest, EdgeData edge){
        outedgelist.put(dest, edge);
        connected.add(dest);
    }
    public void AddInEdge(int src, EdgeData edge){
        inedgelist.put(src, edge);
        connected.add(src);
    }
    public EdgeData RemoveOutEdge(int dest){
        if(!inedgelist.containsKey(dest)){
            connected.remove(dest);
        }
        return outedgelist.remove(dest);
    }
    public EdgeData RemoveInEdge(int src){
        if(!outedgelist.containsKey(src)){
            connected.remove(src);
        }
        return inedgelist.remove(src);
    }
    public boolean RemoveEdge(int other){
        connected.remove(other);
        boolean condition= false;
        if(inedgelist.containsKey(other)){
            inedgelist.remove(other);
            condition= true;
        }
        if(outedgelist.containsKey(other)){
            outedgelist.remove(other);
            condition=true;
        }
        return condition;
    }
    public List<Integer> getConnected() {
        return connected;
    }

    public int isconnected(int other){//returns 0 if not connected, 1 if connected in one way, and 2 if connected in two ways
        int ans = 0;
        if(inedgelist.containsKey(other)){
            ans++;
        }
        if(outedgelist.containsKey(other)){
            ans++;
        }
        return ans;
    }
    public EdgeData GetOutEdge(int dest){
        if(!outedgelist.containsKey(dest)){
            return null;
        }
        return outedgelist.get(dest);
    }
    public EdgeData GetInEdge(int src){
        if(!inedgelist.containsKey(src)){
            return null;
        }
        return inedgelist.get(src);
    }
    public HashMap<Integer,EdgeData> GetOutEdgeList(){
        return outedgelist;
    }
    public HashMap<Integer,EdgeData> GetInEdgeList(){
        return inedgelist;
    }
    @Override
    public int getKey() {
        return this.key;
    }

    @Override
    public GeoLocation getLocation() {
        return this.location;
    }

    @Override
    public void setLocation(GeoLocation p) {
        this.location= new GeoLocationClass(p.x(),p.y(),p.z());
    }

    @Override
    public double getWeight() {
        return this.weight;
    }

    @Override
    public void setWeight(double w) {
        this.weight = w;
    }

    @Override
    public String getInfo() {
        return this.info;
    }

    @Override
    public void setInfo(String s) {
        this.info = s;
    }

    @Override
    public int getTag() {
        return this.tag;
    }

    @Override
    public void setTag(int t) {
        this.tag = t;
    }
}
