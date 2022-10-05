import com.google.common.graph.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import java.util.Set;


public class Labo1DirectedGraphs {

    public static void main(String[] args) throws IOException {

        // gerichte graaf inlezen (je mag er van uit gaan dat dit een gerichte graaf is, ie. graph.isDirected() == true
        // bestanden: tinyDG.txt, tinyDAG.txt, mediumDG.txt, mediumDAG.txt onder de folder data
        String p = Paths.get("").toAbsolutePath().toString();

        Graph<String> graph = Util.loadDiGraphFromFile(new File(p + "/data-lab1/tinyDG.txt"));

        boolean hasCycle = hasCycle(graph);

        if(!hasCycle) {
            System.out.println("Graaf heeft geen gerichte lus.");
            System.out.printf("Ordening van nodes volgens edges: %s\n",determinePrecedenceFeasibleOrdering(graph));
        } else {
            System.out.println("Graaf heeft een gerichte lus!");
        }
        
        //benchmarkExperiment();

    }

    // Implementeer hier opgave 1
    private static boolean[] visitedNodes;
    private static boolean[] beingVisited;

    public static boolean hasCycle(Graph<String> graph) {
        for (String node: graph.nodes()) {
            if(visitedNodes[Integer.parseInt(node)] && detectCycle(graph,node))
                return true;
        }
        return false;
    }

    private static boolean detectCycle(Graph<String> graph, String node){
        beingVisited[Integer.parseInt(node)] = true;

        Set<String> children = graph.successors(node);
        for (String child : children) {
            if(beingVisited[Integer.parseInt(child)]){
                return true;
            } else if (!visitedNodes[Integer.parseInt(child)] && detectCycle(graph, child)) {
                return true;
            }
        }

        beingVisited[Integer.parseInt(node)] = false;
        visitedNodes[Integer.parseInt(node)] = true;

        return false;
    }

    // Implementeer hier opgave 2
    public static List<String> determinePrecedenceFeasibleOrdering(Graph<String> graph) {
        throw new UnsupportedOperationException("Nog te implementeren");
    }
    
    // Hier kan je desgewenst opgave 3 implementeren. Andere methodes van benchmarking zijn ook toegestaan. 
    public static void benchmarkExperiment(){
    	throw new UnsupportedOperationException("Nog te implementeren");
    }
}
