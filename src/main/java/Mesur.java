import org.graphstream.algorithm.Toolkit;
import org.graphstream.algorithm.generator.BarabasiAlbertGenerator;
import org.graphstream.algorithm.generator.Generator;
import org.graphstream.algorithm.generator.RandomGenerator;
import org.graphstream.graph.BreadthFirstIterator;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;

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
    public Graph genererGrapheAleatoire(int nombreNoeuds, int degreMoyen) {
        Graph grapheAleatoire = new SingleGraph("GrapheAleatoire");
        Generator generateur = new RandomGenerator(degreMoyen, false, false);
        generateur.addSink(grapheAleatoire);
        generateur.begin();

        for (int i = 0; i < nombreNoeuds; i++) {
            generateur.nextEvents();
        }

        generateur.end();

        System.out.printf("Nombre de nœuds dans le graphe aléatoire : %d%n", grapheAleatoire.getNodeCount());
        System.out.printf("Nombre d'arêtes dans le graphe aléatoire : %d%n", grapheAleatoire.getEdgeCount());
        System.out.printf("Degré moyen dans le graphe aléatoire : %f%n", Toolkit.averageDegree(grapheAleatoire));
        System.out.println("Coefficient moyen de regroupement dans le graphe aléatoire : " + Toolkit.averageClusteringCoefficient(grapheAleatoire));
        System.out.println("Le graphe est connexe : " + Toolkit.isConnected(grapheAleatoire));
        System.out.println("Degré moyen pour un graphe connexe de même taille : " + Math.log(grapheAleatoire.getNodeCount()));

        return grapheAleatoire;
    }
    public Graph genererGrapheBarabasi(int nombreNoeuds, int degreMoyen) {
        System.setProperty("org.graphstream.ui", "swing");
        SingleGraph grapheBarabasi = new SingleGraph("GrapheBarabasi");
        Generator generateur = new BarabasiAlbertGenerator(degreMoyen);
        generateur.addSink(grapheBarabasi);
        generateur.begin();

        for (int i = 0; i < nombreNoeuds; i++) {
            generateur.nextEvents();
        }

        generateur.end();

        System.out.printf("Nombre de nœuds dans le graphe Barabasi : %d%n", grapheBarabasi.getNodeCount());
        System.out.printf("Nombre d'arêtes dans le graphe Barabasi : %d%n", grapheBarabasi.getEdgeCount());
        System.out.printf("Degré moyen dans le graphe Barabasi : %f%n", Toolkit.averageDegree(grapheBarabasi));
        System.out.println("Coefficient moyen de regroupement dans le graphe Barabasi : " + Toolkit.averageClusteringCoefficient(grapheBarabasi));
        System.out.println("Le graphe est connexe : " + Toolkit.isConnected(grapheBarabasi));
        System.out.println("Degré moyen pour un graphe connexe de même taille : " + Math.log(grapheBarabasi.getNodeCount()));

        return grapheBarabasi;
    }

}
