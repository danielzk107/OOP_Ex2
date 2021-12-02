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
        EdgeData temp= outedgelist.remove(dest);
        return temp;
    }
    public EdgeData RemoveInEdge(int src){
        EdgeData temp= inedgelist.remove(src);
        return temp;
    }

    public List<Integer> getConnected() {
        return connected;
    }

    public boolean isconnected(int other){
        return outedgelist.containsKey(other) || inedgelist.containsKey(other);
    }
    public EdgeData GetOutEdge(int dest){
        return outedgelist.get(dest);
    }
    public EdgeData GetInEdge(int src){
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
        this.location= new GeoLocation() {
            @Override
            public double x() {
                return p.x();
            }

            @Override
            public double y() {
                return p.y();
            }

            @Override
            public double z() {
                return p.z();
            }

            @Override
            public double distance(GeoLocation g) {
                return Math.sqrt(Math.pow((p.x()-g.x()),2)+Math.pow((p.y()-g.y()), 2)+Math.pow((p.z()-g.z()),2));
            }
        };
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
