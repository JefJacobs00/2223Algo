import com.google.common.graph.*;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.IntStream;

public class Util {

    public static Graph<String> loadDiGraphFromFile(File file) throws IOException {

        try(Scanner sc = new Scanner(file)) {
            int V = Integer.parseInt(sc.nextLine());
            int E = Integer.parseInt(sc.nextLine());

            GraphBuilder graphBuilder = GraphBuilder.directed();
            graphBuilder.expectedNodeCount(V);
            graphBuilder.allowsSelfLoops(true);
            graphBuilder.nodeOrder(ElementOrder.insertion());
            MutableGraph<String> graph = graphBuilder.build();

            for(int i = 1; i<=V; i++) {
                graph.addNode(Integer.toString(i));
            }

            String line = null;
            while(sc.hasNextLine() && !(line = sc.nextLine().trim()).isEmpty()) {

                Scanner sc2 = new Scanner(line);

                int v1 = sc2.nextInt();
                int v2 = sc2.nextInt();

                graph.putEdge(Integer.toString(v1),Integer.toString(v2));
            }

            return graph;
        }
    }

    public static <T> void writeDiGraphToFile(Graph<T> graph, File file) throws IOException {

        try(PrintWriter pw = new PrintWriter(file)) {

            pw.println(graph.nodes().size());
            pw.println(graph.edges().size());

            Map<Object,Integer> idMap = new HashMap<>();
            int id = 1;
            for(T node : graph.nodes()) {
                idMap.put(node,id++);
            }
            for(EndpointPair<T> edge : graph.edges()) {
                pw.printf("%d %d\n",idMap.get(edge.source()),idMap.get(edge.target()));
            }
        }
    }
}