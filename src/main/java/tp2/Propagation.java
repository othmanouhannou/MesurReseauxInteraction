package tp2;

import org.graphstream.algorithm.Toolkit;
import org.graphstream.graph.Graph;

public class Propagation {

    Graph graph;
    public Propagation(Graph graph) {
        this.graph=graph;
    }

    public  double  dispersionDegre(){

        double k_square = 0.0;
        int[] distribution = Toolkit.degreeDistribution(graph);
         for (int i = 0; i < distribution.length; i++) {
            if (distribution[i] > 0) {
                k_square += Math.pow(i, 2) * ((double) distribution[i] / graph.getNodeCount());
            }
        }
        //System.out.println("dispersion des degrés : " + k_square);
        return k_square;
    }

}
