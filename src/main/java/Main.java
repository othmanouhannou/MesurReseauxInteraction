import org.graphstream.algorithm.Toolkit;
import org.graphstream.graph.BreadthFirstIterator;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.DefaultGraph;
import org.graphstream.stream.file.FileSourceEdge;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class Main {





    public static void main(String[] args) {
        String filePath = "src/data/com-dblp.ungraph.txt";
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
        Boolean connexe = Toolkit.isConnected(graph);
        //tester la connectivité du graphe
        System.out.println("connected graph : " + connexe);
        //un graph aleatoire peut etre connece ssi le degres moyen est supperieur au ln(nb neuds)
        System.out.println(Math.log(graph.getNodeCount()) + " > " + Toolkit.averageDegree(graph));
        Mesur m = new Mesur(graph);
        m.generateDegreeDistribution();


        //*******************************************
        List<Node> randomNodes = Toolkit.randomNodeSet(graph, 1000);
        BreadthFirstIterator bfsIterator = new BreadthFirstIterator(randomNodes.get(0));

        while (bfsIterator.hasNext())
            bfsIterator.next();

        double totalDist = 0;
        double[] distDistribution = new double[bfsIterator.getDepthMax() + 1];

        for (Node node : randomNodes) {
            if (bfsIterator.getDepthOf(node) != -1) {
                totalDist += bfsIterator.getDepthOf(node);
                distDistribution[bfsIterator.getDepthOf(node)]++;
            }
        }

        double avgDist = totalDist / 1000.0;
        System.out.println("\nGraph Name: " + graph.getId());
        System.out.println("Average Distance: " + avgDist);


        double petitMonde = (Math.log(graph.getNodeCount())/Math.log(Toolkit.averageDegree(graph)));
        System.out.println("ln(N)/ln(<k>) :" + petitMonde);


        //********************

    }

    }
