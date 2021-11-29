package ex2;

public class EdgeDataClass implements EdgeData {
    private Node src;
    private Node dest;
    private double weight;
    private String info;
    private int tag;

    public EdgeDataClass(Node src, Node dest, double weight, String info, int tag) {
        this.src = src;
        this.dest = dest;
        this.weight = weight;
        this.info = info;
        this.tag = tag;
    }
    @Override
    public int getSrc() {
        return src.getKey();
    }

    @Override
    public int getDest() {
        return dest.getKey();
    }

    @Override
    public double getWeight() {
        return this.weight;
    }

    @Override
    public String getInfo() {
        return this.info;
    }

    @Override
    public void setInfo(String s) {
        this.info=s;
    }

    @Override
    public int getTag() {
        return this.tag;
    }

    @Override
    public void setTag(int t) {
        this.tag=t;
    }
}
