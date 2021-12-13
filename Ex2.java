package ex2;

public class Ex2 {
    public static void main(String[] args){
        DirectedWeightedGraphAlgorithmsClass aaa= new DirectedWeightedGraphAlgorithmsClass((DirectedWeightedGraphClass) getGrapg("Test2.json"));
        DWGraphGUI x= new DWGraphGUI(aaa);
        x.FirstSetup();
    }
    /**
     * This static function will be used to test your implementation
     * @param json_file - a json file (e.g., G1.json - G3.gson)
     * @return
     */
    public static DirectedWeightedGraph getGrapg(String json_file) {
        DirectedWeightedGraph ans = null;
        DirectedWeightedGraphAlgorithmsClass ansalgo= new DirectedWeightedGraphAlgorithmsClass();
        ansalgo.load(json_file);
        ans= ansalgo.dwgraph;
        return ans;
    }
    /**
     * This static function will be used to test your implementation
     * @param json_file - a json file (e.g., G1.json - G3.gson)
     * @return
     */
    public static DirectedWeightedGraphAlgorithms getGrapgAlgo(String json_file) {
        DirectedWeightedGraphAlgorithms ans = null;
        ans= new DirectedWeightedGraphAlgorithmsClass();
        ans.load(json_file);
        return ans;
    }
    /**
     * This static function will run your GUI using the json fime.
     * @param json_file - a json file (e.g., G1.json - G3.gson)
     *
     */
    public static void runGUI(String json_file) {
        DirectedWeightedGraphAlgorithms alg = getGrapgAlgo(json_file);
        DWGraphGUI gui= new DWGraphGUI((DirectedWeightedGraphAlgorithmsClass) alg);
        gui.FirstSetup();

    }
}
