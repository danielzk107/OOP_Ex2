package ex2;
import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
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
    void IsConnectedTest(){
        //Connected graphs:
        dwgalgo.load("resources/Test.json");
        assertTrue(dwgalgo.isConnected());
        dwgalgo.load("resources/Test2.json");
        assertTrue(dwgalgo.isConnected());
        dwgalgo.load("resources/G3.json");
        assertTrue(dwgalgo.isConnected());
        //Not a connected graph:
        dwgalgo.load("resources/Test1.json");
        assertFalse(dwgalgo.isConnected());
    }
    @Test
    void Centretest(){
        dwgalgo.load("resources/Test.json");
        assertEquals(dwgalgo.center(), dwgalgo.dwgraph.getNode(0));
        dwgalgo.load("resources/Test2.json");
        assertEquals(dwgalgo.center(), dwgalgo.dwgraph.getNode(2));
    }
    @Test
    void LoadTest(){
        assertTrue(dwgalgo.load("resources/G1.json"));
        assertTrue(dwgalgo.load("resources/G2.json"));
        assertTrue(dwgalgo.load("resources/G3.json"));
        assertTrue(dwgalgo.load("resources/Test.json"));
        assertTrue(dwgalgo.load("resources/Test2.json"));
        assertFalse(dwgalgo.load("resources/G4.json"));
        assertFalse(dwgalgo.load("resources/G5.json"));
    }
    @Test
    void shortestpathdisttest(){
        dwgalgo.load("resources/Test.json");
        assertEquals(dwgalgo.shortestPathDist(0,1),1);
        assertEquals(dwgalgo.shortestPathDist(0,3),3);
        assertEquals(dwgalgo.shortestPathDist(0,4),3);
        assertEquals(dwgalgo.shortestPathDist(1,0),3);
        assertEquals(dwgalgo.shortestPathDist(1,4),2);
        assertEquals(dwgalgo.shortestPathDist(2,1),3);
        assertEquals(dwgalgo.shortestPathDist(2,2),0);
        assertEquals(dwgalgo.shortestPathDist(2,4),1);
        assertEquals(dwgalgo.shortestPathDist(3,2),3);
        assertEquals(dwgalgo.shortestPathDist(3,4),4);
        assertEquals(dwgalgo.shortestPathDist(4,0),3);
        assertEquals(dwgalgo.shortestPathDist(4,1),4);
        assertEquals(dwgalgo.shortestPathDist(4,3),2);
    }
    @Test
    void ShortestPathTest(){
        dwgalgo.load("resources/Test.json");
        dwgalgo.init(dwgalgo.dwgraph);
        List<NodeData> expected= new LinkedList<>();
        expected.add(dwgalgo.dwgraph.getNode(0));
        expected.add(dwgalgo.dwgraph.getNode(1));
        expected.add(dwgalgo.dwgraph.getNode(2));
        expected.add(dwgalgo.dwgraph.getNode(3));
        List<NodeData> actual= dwgalgo.shortestPath(0,3);
        for(int i=0;i<4;i++){
            assertEquals(expected.get(i), actual.get(i));
        }
        expected= new LinkedList<>();
        expected.add(dwgalgo.dwgraph.getNode(3));
        expected.add(dwgalgo.dwgraph.getNode(0));
        expected.add(dwgalgo.dwgraph.getNode(1));
        expected.add(dwgalgo.dwgraph.getNode(2));
        expected.add(dwgalgo.dwgraph.getNode(4));
        actual= dwgalgo.NewshortestPath(3,4);
        for(int i=0;i<5;i++){
            assertEquals(expected.get(i).getKey(), actual.get(i).getKey());
        }
    }
}
