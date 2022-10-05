import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class ShortestPathInGraph {

    public static void main(String[] args) throws IOException {

        Graph<String, DefaultEdge> directedWeightedGraph = loadWeightedGraphFromFile(new File("data-lab1\\mediumDAG.txt"));
        List<String> jgraphtShortestPath = getJgraphtShortestPath(directedWeightedGraph,"2","56");
        if (jgraphtShortestPath==null) {
            System.out.println("Path not found");
        }
        else {
            for (String s : jgraphtShortestPath) {
                System.out.print(s+"\t");
            }
        }

        //List<String> customShortestPath = getCustomShortestPath(directedWeightedGraph,"2","56");
        //benchmarkExperiment();


    }
    private static List getJgraphtShortestPath(Graph<String, DefaultEdge> graph, String source, String sink) {
        GraphPath shortestPath = DijkstraShortestPath.findPathBetween(graph, source, sink);
        if (shortestPath==null) return null;
        return shortestPath.getVertexList();
    }

    private static List<String> getCustomShortestPath(Graph<String, DefaultEdge> graph, String source, String sink) {
        throw new UnsupportedOperationException("Not implemented!");
    }

    public static void benchmarkExperiment(){
        throw new UnsupportedOperationException("Not implemented!");
    }

    private static Graph<String, DefaultEdge> loadWeightedGraphFromFile(File file) throws IOException {

        try(Scanner sc = new Scanner(file)) {
            int V = Integer.parseInt(sc.nextLine());
            int E = Integer.parseInt(sc.nextLine());
            Graph<String, DefaultEdge> graph = new SimpleDirectedWeightedGraph<>(DefaultEdge.class);
            for (int i = 0; i < V; i++) graph.addVertex((i+1) + "");

            String line = null;
            while(sc.hasNextLine() && !(line = sc.nextLine().trim()).isEmpty()) {

                Scanner sc2 = new Scanner(line);
                String[] linesplit = line.split(" ");
                String v1 = linesplit[0].trim();
                String v2 = linesplit[1].trim();

                DefaultEdge e1 = graph.addEdge(v1, v2);
                graph.setEdgeWeight(e1, 1);
            }

            return graph;
        }
    }

}
