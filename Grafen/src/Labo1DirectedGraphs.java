import com.google.common.graph.Graph;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;


public class Labo1DirectedGraphs {

    public static void main(String[] args) throws IOException {

        // gerichte graaf inlezen (je mag er van uit gaan dat dit een gerichte graaf is, ie. graph.isDirected() == true
        // bestanden: tinyDG.txt, tinyDAG.txt, mediumDG.txt, mediumDAG.txt onder de folder data
        Graph<String> graph = Util.loadDiGraphFromFile(new File("data-lab1/tinyDAG.txt"));

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
    private static boolean[] visitedNodes; // zwarte nodes
    private static boolean[] beingVisited; // grijze nodes

    public static boolean hasCycle(Graph<String> graph) {
        visitedNodes = new boolean[graph.nodes().size()];
        beingVisited = new boolean[graph.nodes().size()];

        for (String node: graph.nodes()) {
            if(!visitedNodes[Integer.parseInt(node) - 1] && detectCycle(graph,node))
                return true;
        }
        return false;
    }

    private static boolean detectCycle(Graph<String> graph, String node){
        beingVisited[Integer.parseInt(node) - 1] = true;

        Set<String> children = graph.successors(node);
        for (String child : children) {
            if(beingVisited[Integer.parseInt(child) - 1]){
                return true;
            } else if (!visitedNodes[Integer.parseInt(child) - 1] && detectCycle(graph, child)) {
                return true;
            }
        }

        beingVisited[Integer.parseInt(node) - 1] = false;
        visitedNodes[Integer.parseInt(node) - 1] = true;

        return false;
    }


    // Implementeer hier opgave 2
    private static LinkedList<String> sortedNodes = new LinkedList<>();
    public static List<String> determinePrecedenceFeasibleOrdering(Graph<String> graph) {
        for (int i = 0; i < graph.nodes().size(); i++) {
            visitedNodes[i] = false;
            beingVisited[i] = false;
        }

        for (String node: graph.nodes()) {
            if(!visitedNodes[Integer.parseInt(node) - 1])
                sortedNodes.addFirst(DFS(graph,node));
        }
        return sortedNodes;
    }

    private static String DFS(Graph<String> graph, String node){
        beingVisited[Integer.parseInt(node) - 1] = true;
        Set<String> children = graph.successors(node);

        for (String child : children) {
            if(!beingVisited[Integer.parseInt(child) - 1] && !visitedNodes[Integer.parseInt(child) - 1]){
                sortedNodes.addFirst(DFS(graph, child));
            }
        }

        beingVisited[Integer.parseInt(node) - 1] = false;
        visitedNodes[Integer.parseInt(node) - 1] = true;

        return node;
    }
    
    // Hier kan je desgewenst opgave 3 implementeren. Andere methodes van benchmarking zijn ook toegestaan. 
    public static void benchmarkExperiment(){

    }
}
