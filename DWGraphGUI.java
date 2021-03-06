package ex2;

import java.awt.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import javax.swing.*;
class ThreadforPrint extends Thread{
    private DWGraphGUI dwgui;
    private int start, end;
    public ThreadforPrint(DWGraphGUI dwgui, int start, int end){
        this.dwgui=dwgui;
        this.start=start;
        this.end=end;
    }
    public void run(){
        Graphics g= dwgui.getGraphics();
        for (int i=start;i<end;i++){
            EdgeDataClass temp= (EdgeDataClass) dwgui.dwgalgo.dwgraph.getEdgelist().get(i);
            g.setColor(Color.GREEN);
            GeoLocationClass location1= (GeoLocationClass) dwgui.dwgalgo.dwgraph.getNode(temp.getSrc()).getLocation();
            GeoLocationClass location2= (GeoLocationClass)  dwgui.dwgalgo.dwgraph.getNode(temp.getDest()).getLocation();
            g.drawLine((int)location1.x(), (int)location1.y()+80, (int)location2.x(), (int)location2.y()+80);
        }
    }
}
public class DWGraphGUI extends JFrame {
    public DirectedWeightedGraphAlgorithmsClass dwgalgo;
    private DirectedWeightedGraphAlgorithmsClass originalgraph;
    private JPanel panel;
    private JButton btn, centrebtn, clearbtn;
    private int width,height;
    private double xdiff,ydiff;
    public DWGraphGUI(DirectedWeightedGraphAlgorithmsClass dwgalgo) {
        this.dwgalgo = dwgalgo;
        originalgraph= dwgalgo;
        panel= new JPanel();
        clearbtn= new JButton("Clear board");
        clearbtn.addActionListener(e -> ClearAction(getGraphics()));
        centrebtn= new JButton("Show centre node of the graph");
        centrebtn.addActionListener(e -> CentreAction(getGraphics()));
        btn= new JButton("Show My Graph");
        btn.addActionListener(e -> ShowGraphAction(getGraphics()));
        panel.add(centrebtn);
        panel.add(btn);
        panel.add(clearbtn);
        panel.setBounds(0,0,750,500);
        panel.setBackground(Color.LIGHT_GRAY);
        this.setSize(750,500);
        this.setLayout(null);
        this.getContentPane().setBackground(Color.white);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.add(panel);
    }

    public DWGraphGUI(){
        this.dwgalgo = new DirectedWeightedGraphAlgorithmsClass();
        originalgraph= new DirectedWeightedGraphAlgorithmsClass();
        panel= new JPanel();
        panel.setBackground(Color.LIGHT_GRAY);
        panel.setBounds(0,0, width, 50);
        this.setTitle("Graphic graph grasper");
        clearbtn= new JButton("Clear board");
        clearbtn.addActionListener(e -> ClearAction(getGraphics()));
        centrebtn= new JButton("Show centre node of the graph");
        centrebtn.addActionListener(e -> CentreAction(getGraphics()));
        btn= new JButton("Show My Graph");
        btn.addActionListener(e -> ShowGraphAction(getGraphics()));
        panel.add(centrebtn);
        panel.add(btn);
        panel.add(clearbtn);
        this.setSize(750,500);
        this.setLayout(null);
        this.getContentPane().setBackground(Color.white);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.add(panel);
    }
    public void ClearAction(Graphics g){
        g.clearRect(0,80,width,height);
    }
    public void ChangeSize(int width, int height){
        this.setSize(width,height);
        panel.setBounds(0,0,width, 50);
        this.width=width;
        this.height=height;
    }
    public void Setxandydiff(double[] arr){
        xdiff=arr[0];
        ydiff=arr[1];
    }
    public void firstinit(DirectedWeightedGraph graph){
        dwgalgo.init(graph);
        originalgraph.init(graph);
    }
    public void init(DirectedWeightedGraph graph){
        dwgalgo.init(graph);
    }
    public void PrintGraph(){
        Graphics g= getGraphics();
        DirectedWeightedGraphClass graph=(DirectedWeightedGraphClass)dwgalgo.getGraph();
        if(graph.edgeSize()>300){
            ThreadforPrint t1= new ThreadforPrint(this, 0, (int)Math.ceil(graph.edgeSize()/4.0));
            ThreadforPrint t2= new ThreadforPrint(this, (int)Math.ceil(graph.edgeSize()/4.0), (int)Math.ceil(graph.edgeSize()/2.0));
            ThreadforPrint t3= new ThreadforPrint(this, (int)Math.ceil(graph.edgeSize()/2.0), (int)Math.ceil(graph.edgeSize()*3/4.0));
            t1.start();
            t2.start();
            t3.start();
            for (int i=(int)Math.ceil(graph.edgeSize()*3/4.0);i<graph.edgeSize();i++){
                EdgeDataClass temp= (EdgeDataClass)graph.getEdgelist().get(i);
                g.setColor(Color.GREEN);
                GeoLocationClass location1= (GeoLocationClass) graph.getNode(temp.getSrc()).getLocation();
                GeoLocationClass location2= (GeoLocationClass) graph.getNode(temp.getDest()).getLocation();
                g.drawLine((int)location1.x(), (int)location1.y()+80, (int)location2.x(), (int)location2.y()+80);
            }
        }
        else{
            for (int i=0;i<graph.edgeSize();i++){
                EdgeDataClass temp= (EdgeDataClass)graph.getEdgelist().get(i);
                g.setColor(Color.GREEN);
                GeoLocationClass location1= (GeoLocationClass) graph.getNode(temp.getSrc()).getLocation();
                GeoLocationClass location2= (GeoLocationClass) graph.getNode(temp.getDest()).getLocation();
                g.drawLine((int)location1.x(), (int)location1.y()+80, (int)location2.x(), (int)location2.y()+80);
            }
        }
        for (int i=0; i< graph.nodeSize();i++){
            Node temp= (Node)graph.getNode(i);
            GeoLocationClass location= (GeoLocationClass) temp.getLocation();
            g.setColor(Color.BLACK);
            g.drawLine((int)location.x(),(int)(location.y())+80, (int)location.x(), (int)(location.y())+80);
        }
    }
    public void CorrectGraph(){//this function corrects the graph according to the new window size and ratio.
        HashMap<Integer,NodeData> newnodelist=new HashMap<>();
        HashMap<Integer,EdgeData> newedgelist=new HashMap<>();
        for (int i=0; i<dwgalgo.getGraph().nodeSize();i++){
            Node temp= (Node)dwgalgo.getGraph().getNode(i);
            double x=temp.getLocation().x();
            double y=temp.getLocation().y();
            int count=1;
            while(x>xdiff){
                if(x<1){
                    x=Double.parseDouble("0."+Double.toString(x).substring(3));
                    x=x/Math.pow(10,count);
                    count++;
                }
                else{
                    if(Double.parseDouble(Double.toString(x).substring(1))==0){
                        x=x/10;
                    }
                    else{
                        x=Double.parseDouble(Double.toString(x).substring(1));
                    }
                }
            }
            count=1;
            while(y>ydiff){
                if(y<1){
                    y=Double.parseDouble("0."+Double.toString(y).substring(3));
                    y=y/Math.pow(10,count);
                    count++;
                }
                else{
                    if(Double.parseDouble(Double.toString(y).substring(1))==0){
                        y=y/10;
                    }
                    else{
                        y=Double.parseDouble(Double.toString(y).substring(1));
                    }
                }
            }
            while(x<(width/15)){
                x*=10;
            }
            while(y<(height/15)){
                y*=10;
            }
            temp.setLocation(new GeoLocationClass(x,y,dwgalgo.getGraph().getNode(i).getLocation().z()));
            newnodelist.put(i, temp);
        }
        for (int i=0; i<dwgalgo.getGraph().edgeSize();i++){
            int src= ((DirectedWeightedGraphClass)dwgalgo.getGraph()).getEdgelist().get(i).getSrc();
            int dest= ((DirectedWeightedGraphClass)dwgalgo.getGraph()).getEdgelist().get(i).getDest();
            double weight= ((DirectedWeightedGraphClass)dwgalgo.getGraph()).getEdgelist().get(i).getWeight();
            EdgeData temp= new EdgeDataClass(i,(Node)newnodelist.get(src), (Node)newnodelist.get(dest), weight , "", 0);
            newedgelist.put(i,temp);
        }
        dwgalgo.init(new DirectedWeightedGraphClass(newnodelist,newedgelist,0));
    }
    public void PrintTSP(List<NodeData> cities){
        List<NodeData> outputlist= dwgalgo.tsp(cities);
    }
    public void ShowGraphAction(Graphics g){
        g.clearRect(0,80,width,height);
        PrintGraph();
        if(dwgalgo.dwgraph.nodeSize()<15){
            for(int i=0; i<dwgalgo.dwgraph.nodeSize();i++){
                HighlightNode(i);
            }
        }
//        btn.setEnabled(false);
    }
    public void CentreAction(Graphics g){
        Node centre= (Node)dwgalgo.center();
        if(centre==null){
            g.setColor(Color.red);
            g.drawString("The graph isn't connected, therefore it has no centre.", 250, 250);
            centrebtn.setEnabled(false);
            return;
        }
        int x= (int)centre.getLocation().x();
        int y= (int)centre.getLocation().y();
        g.setColor(Color.magenta);
        g.drawString("The centre node is node number "+centre.getKey(), 250, 250);
//        g.drawOval(x, y, 3, 3);
        g.fillOval(x,y+80,5,5);
    }
    public void save(String filename){
        originalgraph.save(filename);
    }
    public void FirstSetup(){
        firstinit(dwgalgo.getGraph());
        double[] arr= XdiffandYdiff((DirectedWeightedGraphClass)dwgalgo.getGraph());
        Setxandydiff(arr);
        while(arr[0]<100){//finding the correct measurements required for the frame.
            arr[0]*=10;
        }
        while(arr[1]<100){
            arr[1]*=10;
        }
        if(arr[0]<750){
            arr[0]=750;
        }
        if(arr[1]<500){
            arr[1]=500;
        }
        else if(arr[1]>600){
            arr[1]=600;
        }
        ChangeSize((int)Math.round(arr[0]), (int)Math.round(arr[1]+80));
        CorrectGraph();
    }
    public void HighlightNode(int node){
        NodeData node1= dwgalgo.dwgraph.getNode(node);
        int x= (int) node1.getLocation().x();
        int y= (int) node1.getLocation().y();
        Graphics g= getGraphics();
        g.drawString(String.valueOf(node),x,y+80);
    }
    public static void main(String[]args){//Main function to Test the capabilities of Java Swing.
        DWGraphGUI x= new DWGraphGUI();
        AlgorithmsTests t= new AlgorithmsTests();
//        x.dwgalgo.load("resources/G3.json");
        x.dwgalgo.load("resources/Test.json");
        x.FirstSetup();
        List<NodeData> list= new LinkedList<>();
        list.add(x.dwgalgo.dwgraph.getNode(0));
        list.add(x.dwgalgo.dwgraph.getNode(2));
        list.add(x.dwgalgo.dwgraph.getNode(4));
        list.add(x.dwgalgo.dwgraph.getNode(3));
        List<NodeData> tsp= x.dwgalgo.tsp(list);
        for (NodeData node:tsp){
            System.out.println(node.getKey());
        }
//        if(x.dwgalgo.isConnected()){
//            x.save("Test.json");
//        }
//        AlgorithmsTests t= new AlgorithmsTests();
//        DirectedWeightedGraph graph= t.DWGraphMaker(10,20);
//        DirectedWeightedGraphAlgorithms g= new DirectedWeightedGraphAlgorithmsClass((DirectedWeightedGraphClass) graph);
    }
    public static double[] XdiffandYdiff(DirectedWeightedGraphClass graph ){
        double[] arr = {0.0,0.0};//arr[0] keeps the maximum difference between two nodes at the x axis, and arr[1] does the same for the y axis. The commented lines are another way to resize the graph, but by average difference and not maximum.
        for(int i=0;i<graph.nodeSize();i++){
            for(int j=0;j<graph.nodeSize();j++){
                if(Math.abs(graph.getNode(i).getLocation().x()-graph.getNode(j).getLocation().x())>arr[0]){
                    arr[0]= Math.abs(graph.getNode(i).getLocation().x()-graph.getNode(j).getLocation().x());
                }
                if (Math.abs(graph.getNode(i).getLocation().y()-graph.getNode(j).getLocation().y())>arr[1]){
                    arr[1]=Math.abs(graph.getNode(i).getLocation().y()-graph.getNode(j).getLocation().y());
                }
//                arr[0]+= Math.abs(graph.getNode(i).getLocation().x()-graph.getNode(j).getLocation().x());
//                arr[1]+= Math.abs(graph.getNode(i).getLocation().y()-graph.getNode(j).getLocation().y());
            }
        }
//        arr[0]= arr[0]/ graph.nodeSize();
//        arr[1]= arr[1]/ graph.nodeSize();
        return arr;
    }
}

