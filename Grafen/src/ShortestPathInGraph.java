import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;


public class ShortestPathInGraph {

    public static void main(String[] args) throws IOException {

        //Graph<String, DefaultEdge> directedWeightedGraph = loadWeightedGraphFromFile(new File("data-lab1\\mediumDAG.txt"));
        Graph<String, DefaultEdge> directedWeightedGraph = loadWeightedGraphFromFile(new File("data-lab1\\dataset2\\160_1DAG.txt"));
        long start = System.nanoTime();
        List<String> jgraphtShortestPath = getJgraphtShortestPath(directedWeightedGraph,"102","25");
        long finish = System.nanoTime();
        long timeElapsed = finish - start;

        if (jgraphtShortestPath==null) {
            System.out.println("Path not found");
        }
        else {
            for (String s : jgraphtShortestPath) {
                System.out.print(s+"\t");
            }
        }
        long start2 = System.nanoTime();
        List<String> customShortestPath = getCustomShortestPath(directedWeightedGraph,"102","25");
        long finish2 = System.nanoTime();
        long timeElapsed2 = finish2 - start2;

        System.out.println("\nEigen uitwerking: ");
        for (String s : customShortestPath) {
            System.out.print(s+"\t");
        }



        System.out.println("\nImplementatie Jgrapht: \t"+((double) timeElapsed)/1000000+"ms");
        System.out.println("Eigen implementatie: \t"+((double) timeElapsed2) /1000000+"ms");
        benchmarkExperiment();


    }
    List<Double> jgraphTimes = new ArrayList<>();
    List<Double> ownTimes = new ArrayList<>();

    public static void benchmarkExperiment() throws IOException {
        String destination = "data-lab1/dataset2";
        File folder = new File(destination);
        String fileName = "data-lab1/dataset2/test.csv";

        for (File f : folder.listFiles()) {
            if(f.getName().contains("A")){
                String result = null;
                do{
                    result = benchmartFile(f);
                }while (result == null);


                writeToCsv(result,fileName);
            }

        }

    }

    private static void writeToCsv(String data, String fileName) throws IOException {
        if(data != null){
            BufferedWriter out = new BufferedWriter(new FileWriter(fileName, true));
            out.write(data);
            out.close();
        }
    }

    private static String benchmartFile(File f) throws IOException {
        Graph<String, DefaultEdge> directedWeightedGraph = loadWeightedGraphFromFile(f);
        Random rand = new Random();
        int source = rand.nextInt(directedWeightedGraph.vertexSet().size() - 1) + 1;
        int sink = rand.nextInt(directedWeightedGraph.vertexSet().size() - 1) + 1;

        long start = System.nanoTime();
        List<String> jgraphtShortestPath = getJgraphtShortestPath(directedWeightedGraph,source+"",sink+"");
        long finish = System.nanoTime();
        long timeElapsed = (finish - start);

        long start2 = System.nanoTime();
        List<String> customShortestPath = getCustomShortestPath(directedWeightedGraph,source+"",sink+"");

        long finish2 = System.nanoTime();
        long timeElapsed2 = finish2 - start2;
        if (jgraphtShortestPath != null){
            System.out.println("number of vertexes: "+directedWeightedGraph.vertexSet().size());
            System.out.println("number of edges: "+directedWeightedGraph.edgeSet().size());
            System.out.println("Jgrapht implementatie: \t"+((double) timeElapsed)/1000000+"ms");
            System.out.println("Eigen implementatie: \t"+((double) timeElapsed2) /1000000+"ms");
            System.out.println("source: "+source+"\n"+"sink: "+sink);
            System.out.println("custom path: "+customShortestPath);
            System.out.println("jgrapht path: "+jgraphtShortestPath);
            System.out.println("distance: "+distance[sink - 1]);
            System.out.println(jgraphtShortestPath.size() - 1 == distance[sink - 1]);
            System.out.println("\n");

            return  f.getName() + ";" +
                    directedWeightedGraph.vertexSet().size() + ";" +
                    directedWeightedGraph.edgeSet().size()+";" +
                    ((double) timeElapsed)/1000000 + ";" +
                    ((double) timeElapsed2) /1000000 + ";" +
                    source + ";" +
                    sink + ";" +
                    distance[sink - 1] + "\n";
        }

        return null;
    }

    private static List getJgraphtShortestPath(Graph<String, DefaultEdge> graph, String source, String sink) {
        GraphPath shortestPath = DijkstraShortestPath.findPathBetween(graph, source, sink);
        if (shortestPath==null) return null;
        return shortestPath.getVertexList();
    }

    private static List<String> getCustomShortestPath(Graph<String, DefaultEdge> graph, String source, String sink) {
        Dijkstra(graph,source,sink);
        if(distance[Integer.parseInt(sink) -1] == Integer.MAX_VALUE)
            return null;
        List<String> path = getRoute(Integer.parseInt(sink) - 1);
        path.add(sink);

        return path;
    }

    private static List<String> getRoute(int beginIndex){
        LinkedList<String> path = new LinkedList<>();
        int next = beginIndex;
        next = indexes[beginIndex];
        path.addFirst(""+(next+1));
        while(next != -1) {
            next = indexes[next];
            path.addFirst(""+(next+1));
        }
        path.pop();

        return path;
    }


    private static List<String> s;
    private  static PriorityQueue<String> queue;
    private static int[] distance;
    private static int[] indexes;

    private static void Dijkstra(Graph<String, DefaultEdge> graph, String source, String destination){
        s = new ArrayList<>();
        queue = new PriorityQueue<>(graph.vertexSet().size());
        distance = new int[graph.vertexSet().size()];
        indexes = new int[graph.vertexSet().size()];


        for (int i = 0; i < distance.length; i++) {
            distance[i] = Integer.MAX_VALUE;
        }

        distance[Integer.parseInt(source) - 1] = 0;
        indexes[Integer.parseInt(source) - 1] = -1;
        queue.add(source);

        while (s.size() != graph.vertexSet().size()){
            if(queue.isEmpty())
                return;
            String u = queue.remove();
            //String u = extractMin(queue);
            if(!s.contains(u)){
                s.add(u);
                relax(graph, u);
            }

        }
    }

    private static String extractMin(Queue q){
        String u = "";
        int dist = Integer.MAX_VALUE;
        for (int i = 0; i < queue.size(); i++) {
            String node = queue.remove();
            if (distance[Integer.parseInt(node) - 1] < dist){
                u = node;
                dist = distance[Integer.parseInt(node) - 1];
            }
            queue.add(node);
        }

        queue.remove(u);

        return u;
    }

    private static void relax(Graph<String, DefaultEdge> graph, String u){


        Set<DefaultEdge> edges = graph.outgoingEdgesOf(u);

        for (DefaultEdge e : edges){
            String child = graph.getEdgeTarget(e);
            String source = graph.getEdgeSource(e);
            if(!s.contains(child)){
                int newDistance =  distance[Integer.parseInt(source) - 1] + 1;
                if(newDistance < distance[Integer.parseInt(child) - 1]){
                    distance[Integer.parseInt(child) - 1] = newDistance;
                    indexes[Integer.parseInt(child) - 1] = Integer.parseInt(source) - 1;
                }

                queue.add(child);
            }
        }
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
