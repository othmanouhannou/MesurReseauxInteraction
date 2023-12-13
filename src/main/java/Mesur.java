import org.graphstream.algorithm.Toolkit;
import org.graphstream.graph.Graph;
import java.io.FileWriter;
import java.io.PrintWriter;
public class Mesur {
    Graph graph;
    public Mesur(Graph graph) {
        this.graph=graph;
    }
    public void generateDegreeDistribution() {
        int numberOfNodes = graph.getNodeCount();
        int[] degreeDistribution = Toolkit.degreeDistribution(graph);

        try {
            PrintWriter file = new PrintWriter(new FileWriter("src/data/distribution.txt"));

            for (int i = 0; i < degreeDistribution.length; i++) {
                // Écriture dans le fichier avec la probabilité normalisée
                file.write(i + "   " + (double) degreeDistribution[i] / numberOfNodes);
                file.println();
            }
            file.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
