import org.graphstream.algorithm.Toolkit;
import org.graphstream.graph.BreadthFirstIterator;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

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

    public void genererDistributionDistances() {
        int nombreDeNoeuds = graph.getNodeCount();
        int nombreIterations = 1000;
        double[] distributionProbabilites = new double[1000];

        for (int iteration = 0; iteration < nombreIterations; iteration++) {
            Node noeudAleatoire = Toolkit.randomNode(graph);
            BreadthFirstIterator bfs = new BreadthFirstIterator(noeudAleatoire);

            while (bfs.hasNext()) {
                int profondeur = bfs.getDepthOf(bfs.next());
                distributionProbabilites[profondeur] += 1;
            }
        }

        try {
            PrintWriter fichier = new PrintWriter(new FileWriter("C:\\Users\\othma\\OneDrive\\Bureau\\Files\\lhUniv\\MisTp\\reseaux-d-interaction-1\\src\\data\\distribution1.txt"));
            for (int i = 0; i < distributionProbabilites.length && distributionProbabilites[i] != 0; i++) {
                double probabilite = distributionProbabilites[i] / (nombreDeNoeuds * nombreIterations);
                fichier.write(i + " " + probabilite);
                fichier.println();
            }
            fichier.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
