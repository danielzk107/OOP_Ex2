package ex2;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;
import javax.swing.*;

//class NodeGUI extends JComponent{
//    private Node node;
//    public NodeGUI(Node node){
//        this.node=node;
//    }
//    public NodeGUI(){
//        node= new Node(0,0,new GeoLocationClass(100,57,153)," ");
//    }
//    public void paint(Graphics g){
//        g.setColor(Color.CYAN);
//        GeoLocation location= node.getLocation();
////        g.drawLine((int)location.x(), (int)location.x(), (int)location.y(), (int)location.y());
//        g.fillRect(30,30,100,100);
//    }
//}
//class MyGraphics extends Canvas{
//    private Graphics g;
//    public void paint(Graphics g){
//        setBackground(Color.WHITE);
//        g.fillRect(130,30,100,80);
//        setForeground(Color.CYAN);
//        this.g=g;
//    }
//    public void paintNode(Node node){
//        if(node==null){
//            g.drawLine(50,50,150,150);
//            return;
//        }
//        GeoLocation location= node.getLocation();
//        g.drawLine((int)location.x(),(int)location.x(), (int)location.y(), (int)location.y());
//    }
//}
public class DWGraphGUI extends JFrame {
    private DirectedWeightedGraphAlgorithmsClass dwgalgo;
    private JPanel panel;
    private int width,height;
    private double xdiff,ydiff;
    public DWGraphGUI(DirectedWeightedGraphAlgorithmsClass dwgalgo) {
        this.dwgalgo = dwgalgo;
        panel= new JPanel();
        JButton btn= new JButton("AAAAAAA");
        btn.addActionListener(e -> CentreAction(getGraphics()));
        panel.add(btn);
        this.setSize(750,500);
        panel.setBounds(0,0,750,500);
        panel.setBackground(Color.black);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.add(panel);
    }

    public DWGraphGUI(){
        this.dwgalgo = new DirectedWeightedGraphAlgorithmsClass();
        panel= new JPanel();
        panel.setBackground(Color.LIGHT_GRAY);
        panel.setBounds(0,0, 750, 50);
        this.setTitle("Frame");
        JButton btn= new JButton("AAAAAAA");
        btn.addActionListener(e -> CentreAction(getGraphics()));
        panel.add(btn);
        this.setSize(750,500);
        this.setLayout(null);
        this.getContentPane().setBackground(Color.white);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.add(panel);
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
    public void init(DirectedWeightedGraph graph){
        dwgalgo.init(graph);
    }
    public void PrintGraph(){
        Graphics g= getGraphics();
        DirectedWeightedGraphClass graph=(DirectedWeightedGraphClass)dwgalgo.getGraph();
        HashMap<Integer,Integer> edgesdone=new HashMap<>();
        for (int i=0;i<graph.edgeSize();i++){
            EdgeDataClass temp= (EdgeDataClass)graph.getEdgelist().get(i);
            if(edgesdone.containsKey(temp.getDest())){
                if(edgesdone.get(temp.getDest())==temp.getSrc()){
                    g.setColor(Color.BLUE);
                }
                else{
                    g.setColor(Color.GREEN);
                }
            }
            else{
                g.setColor(Color.GREEN);
            }
            edgesdone.put(temp.getSrc(), temp.getDest());
            GeoLocationClass location1= (GeoLocationClass) graph.getNode(temp.getSrc()).getLocation();
            GeoLocationClass location2= (GeoLocationClass) graph.getNode(temp.getDest()).getLocation();
            g.drawLine((int)location1.x(), (int)location1.y()+50, (int)location2.x(), (int)location2.y()+50);
        }
        for (int i=0; i< graph.nodeSize();i++){
            Node temp= (Node)graph.getNode(i);
            GeoLocationClass location= (GeoLocationClass) temp.getLocation();
            g.setColor(Color.BLACK);
            g.drawLine((int)location.x(),(int)(location.y())+50, (int)location.x(), (int)(location.y())+50);
        }
    }
    public void CorrectGraph(){//this function corrects the graph according to the new window size and ratio.
        HashMap<Integer,NodeData> newnodelist=new HashMap<>();
        for (int i=0; i<dwgalgo.getGraph().nodeSize();i++){
            double x=dwgalgo.getGraph().getNode(i).getLocation().x();
            double y=dwgalgo.getGraph().getNode(i).getLocation().y();
            int count=1;
            while(x>xdiff){
                if(x<1){
                    x=Double.parseDouble("0."+Double.toString(x).substring(3));
                    x=x/Math.pow(10,count);
                    count++;
                }
                else{
                    x=Double.parseDouble(Double.toString(x).substring(1));
                }
            }
            count=1;
            while(y>xdiff){
                if(y<1){
                    y=Double.parseDouble("0."+Double.toString(y).substring(3));
                    y=y/Math.pow(10,count);
                }
                else{
                    y=Double.parseDouble(Double.toString(y).substring(1));
                }
            }
            while(x<(width/15)){
                x*=10;
            }
            while(y<(height/15)){
                y*=10;
            }
            System.out.println("Node number "+i+", x= "+x+", y= "+y);
            newnodelist.put(i, new Node(i,0,new GeoLocationClass(x,y,dwgalgo.getGraph().getNode(i).getLocation().z())," "));
        }
        init(new DirectedWeightedGraphClass(newnodelist,((DirectedWeightedGraphClass)dwgalgo.getGraph()).getEdgelist(),0));
    }
    public void PrintTSP(List<NodeData> cities){
        List<NodeData> outputlist= dwgalgo.tsp(cities);
    }
    public void CentreAction(Graphics g){
        PrintGraph();
    }
    public static void main(String[]args){//Main function to Test the capabilities of Java Swing.
        DWGraphGUI x= new DWGraphGUI();
        AlgorithmsTests t= new AlgorithmsTests();
//        x.init(t.DWGraphMaker(10,90));
//        x.dwgalgo.load("resources/G1.json");
        double[] arr= XdiffandYdiff((DirectedWeightedGraphClass)x.dwgalgo.getGraph());
        x.Setxandydiff(arr);
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
        x.ChangeSize((int)Math.round(arr[0]), (int)Math.round(arr[1]+50));
        x.CorrectGraph();
    }
    public static double[] XdiffandYdiff(DirectedWeightedGraphClass graph ){
        double[] arr = {0.0,0.0};//arr[0] keeps the maximum difference between two nodes at the x axis, and arr[1] does the same for the y axis.
        for(int i=0;i<graph.nodeSize();i++){
            for(int j=0;j<graph.nodeSize();j++){
                if(Math.abs(graph.getNode(i).getLocation().x()-graph.getNode(j).getLocation().x())>arr[0]){
                    arr[0]= Math.abs(graph.getNode(i).getLocation().x()-graph.getNode(j).getLocation().x());
                }
                if (Math.abs(graph.getNode(i).getLocation().y()-graph.getNode(j).getLocation().y())>arr[1]){
                    arr[1]=Math.abs(graph.getNode(i).getLocation().y()-graph.getNode(j).getLocation().y());
                }
            }
        }
        return arr;
    }
}

