package tp2;

import org.graphstream.algorithm.Toolkit;
import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.DefaultGraph;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.stream.file.FileSourceEdge;
import tp1.Mesur;

import java.io.IOException;

public class Main {

    private static SingleGraph sg;
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
//
//        System.out.println("degre moyen dblp : " + Toolkit.averageDegree(graph));
//
        sg = new SingleGraph("sg");
        Mesur m = new Mesur(sg);
//        Graph graphAleatoire = m.genererGrapheAleatoire(10000, 6.62208890914917);
//
//        System.out.println("degre moyen du graphe aleatoire: " + Toolkit.averageDegree(graphAleatoire));
//
//        Propagation p = new Propagation(graphAleatoire);
//        Propagation p1 = new Propagation(graph);
//        System.out.println("dispersion des degrés du graphe aleatoire : " + p.dispersionDegre());
//        System.out.println("dispersion des degrés du graphe dblp : " + p1.dispersionDegre());
//
//        System.out.println("λc du graphe aleatoire : " + Toolkit.averageDegree(graphAleatoire)/p.dispersionDegre());
//        System.out.println("λc du graphe dblp : " + Toolkit.averageDegree(graph)/p1.dispersionDegre());
//cree un graphe aleatoire
       // Graph graphAleatoire = m.genererGrapheAleatoire(317080, 6.62208890914917);
        //SimulationEpidemie se = new SimulationEpidemie(graph);
       // SimulationEpidemie seRandom = new SimulationEpidemie(graphAleatoire);
       // se.scinario1();
        //se.scenario2();
       //se.scinario3();

       // seRandom.scinario1("src/scenarios/sc1Rand.txt");
        //seRandom.scenario2("src/scenarios/sc2Rand.txt");
       // seRandom.scinario3("src/scenarios/sc3Rand.txt");

        Graph graphBarabasi = m.genererGrapheBarabasi(10000, (int)6.62208890914917);
        SimulationEpidemie seBarabasi = new SimulationEpidemie(graphBarabasi);
        //seBarabasi.scinario1("src/scenarios/sc1Barabasi.txt");
        //seBarabasi.scenario2("src/scenarios/sc2Barabasi.txt");
        seBarabasi.scinario3("src/scenarios/sc3Barabasi.txt");

     }
}
