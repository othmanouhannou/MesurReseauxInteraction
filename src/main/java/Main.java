import org.graphstream.algorithm.Toolkit;
import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.DefaultGraph;
import org.graphstream.stream.file.FileSourceEdge;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        String filePath = "data/com-dblp.ungraph.txt";
        Graph graph = new DefaultGraph("graph");
        FileSourceEdge fs = new FileSourceEdge();
        fs.addSink(graph);
        try {
            // Read the graph from the file
            fs.readAll(filePath);

        } catch (IOException ioException) {
            ioException.printStackTrace();
        } finally {
            fs.removeSink(graph);
        }
        System.out.println("Nombre de noeuds : " + graph.getNodeCount());
        System.out.println("Nombre d'arcs : " + graph.getEdgeCount());
        System.out.println("Degree moyen : " + Toolkit.averageDegree(graph));
        System.out.println("coefficient de clustering : " + Toolkit.averageClusteringCoefficient(graph));
        }
    }
