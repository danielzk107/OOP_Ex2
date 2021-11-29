package ex2;

import java.util.HashMap;

public class Node implements NodeData {
    private int key;
    private int tag;
    private GeoLocation location;
    private double weight;
    private String info;
    private HashMap<Integer, EdgeData> edgelist;
    public Node(int key, int tag, GeoLocation location, String info){
        this.key=key;
        this.tag=tag;
        setLocation(location);
        this.info=info;
        this.edgelist=new HashMap<>();
    }
    public void AddEdge(int dest, EdgeData edge){
        edgelist.put(dest, edge);
    }
    public boolean isconnected(int dest){
        return edgelist.containsKey(dest);
    }
    public EdgeData GetEdge(int dest){
        return edgelist.get(dest);
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
