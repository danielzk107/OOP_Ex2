package ex2;
import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class AlgorithmsTests {
    DirectedWeightedGraphAlgorithmsClass dwgalgo= new DirectedWeightedGraphAlgorithmsClass();
    public DirectedWeightedGraphClass DWGraphMaker(int size, int numofedges){
        HashMap<Integer, NodeData> nodelist= new HashMap<>();
        HashMap<Integer, EdgeData> edgelist= new HashMap<>();
        HashMap<Integer,Integer> edgesdone= new HashMap<>();
        HashMap<Double, HashMap<Double,Double>> locationkeeper= new HashMap<>();//keeping track of all the locations we have so we do not have duplicates(no two nodes in the same place)
        for (int i=0; i<size;i++){
            Random rnd= new Random();
            Node temp;
            do {
                temp = new Node(i, 0, new GeoLocationClass(rnd.nextDouble(), rnd.nextDouble(), rnd.nextDouble()),"");
            } while(locationkeeper.containsKey(temp.getLocation().x()) && locationkeeper.get(temp.getLocation().x()).containsKey(temp.getLocation().y()) && locationkeeper.get(temp.getLocation().x()).get(temp.getLocation().y())==temp.getLocation().z());
            if(!locationkeeper.containsKey(temp.getLocation().x())){
                locationkeeper.put(temp.getLocation().x(), new HashMap<>());
            }
            locationkeeper.get(temp.getLocation().x()).put(temp.getLocation().y(),temp.getLocation().z());
            nodelist.put(i, temp);
        }
        for(int i=0; i<numofedges;i++){
            int src=0,dest=0;
            Node srcnode,destnode;
            Random rnd =new Random();
            do {
                src= rnd.nextInt(size);
                srcnode= (Node)nodelist.get(src);
                dest= rnd.nextInt(size);
                destnode= (Node)nodelist.get(dest);
            }while (src==dest || srcnode.isconnected(dest)==2 || srcnode.GetOutEdgeList().containsKey(dest));
            double weight= rnd.nextDouble();
            edgesdone.put(src,dest);
            EdgeDataClass temp = new EdgeDataClass(i, srcnode, destnode, weight, "", 0);
            edgelist.put(i, temp);
            srcnode.AddOutEdge(dest, temp);
            destnode.AddInEdge(src, temp);
        }
        return new DirectedWeightedGraphClass(nodelist, edgelist, 0);
    }
    @Test
    void IsCompleteTest() throws InterruptedException {
        dwgalgo.init(DWGraphMaker(10,20));
        DWGraphGUI gui= new DWGraphGUI(dwgalgo);
        assertEquals(dwgalgo.isConnected(), true);
        Thread.sleep(1000);
    }
    @Test
    void LoadTest(){
        boolean actual= dwgalgo.load("resources/G1.json");
        assertEquals(true,actual );
    }
    @Test
    void shortestpathtest(){
        dwgalgo.load("resources/G1.json");

    }
}
