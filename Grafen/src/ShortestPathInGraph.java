import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import javax.print.DocFlavor;
import java.io.File;
import java.io.IOException;
import java.util.*;

public class ShortestPathInGraph {

    public static void main(String[] args) throws IOException {

        Graph<String, DefaultEdge> directedWeightedGraph = loadWeightedGraphFromFile(new File("data-lab1\\mediumDAG.txt"));
        List<String> jgraphtShortestPath = getJgraphtShortestPath(directedWeightedGraph,"2","12");
        if (jgraphtShortestPath==null) {
            System.out.println("Path not found");
        }
        else {
            for (String s : jgraphtShortestPath) {
                System.out.print(s+"\t");
            }
        }

        List<String> customShortestPath = getCustomShortestPath(directedWeightedGraph,"2","12");
        //benchmarkExperiment();


    }
    private static List getJgraphtShortestPath(Graph<String, DefaultEdge> graph, String source, String sink) {
        GraphPath shortestPath = DijkstraShortestPath.findPathBetween(graph, source, sink);
        if (shortestPath==null) return null;
        return shortestPath.getVertexList();
    }

    private static List<String> getCustomShortestPath(Graph<String, DefaultEdge> graph, String source, String sink) {
        Dijkstra(graph,source,sink);
        return null;
    }

    private static List<String> s;
    private  static PriorityQueue<String> queue;
    private static int[] distance;
    private static void Dijkstra(Graph<String, DefaultEdge> graph, String source, String destination){
        s = new ArrayList<>();
        queue = new PriorityQueue<>();
        distance = new int[graph.vertexSet().size()];

        for (int i = 0; i < distance.length; i++) {
            distance[i] = Integer.MAX_VALUE;
        }

        queue.add(source);

        while (s.size() != graph.vertexSet().size()){
            if(queue.isEmpty())
                return;
            String u = queue.remove();
            if(s.contains(u))
                continue;

            s.add(u);
            relax(graph, u);
        }
    }

    private static void relax(Graph<String, DefaultEdge> graph, String u){


        Set<DefaultEdge> children = graph.outgoingEdgesOf(u);
        for (DefaultEdge child : children){
            if(!s.contains(child)){
                //newDistance = parrent distance + 1
                //if newDistance < currentDistance
                //update distance

                //add node to the que
            }
        }
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
