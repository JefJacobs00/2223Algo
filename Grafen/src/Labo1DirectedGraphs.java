import com.google.common.graph.*;

import java.io.File;
import java.io.IOException;
import java.util.List;


public class Labo1DirectedGraphs {

    public static void main(String[] args) throws IOException {

        // gerichte graaf inlezen (je mag er van uit gaan dat dit een gerichte graaf is, ie. graph.isDirected() == true
        // bestanden: tinyDG.txt, tinyDAG.txt, mediumDG.txt, mediumDAG.txt onder de folder data
        Graph<String> graph = Util.loadDiGraphFromFile(new File("data-lab1/tinyDG.txt"));

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
    public static boolean hasCycle(Graph<String> graph) {
        throw new UnsupportedOperationException("Nog te implementeren");
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
