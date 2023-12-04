# Reseaux d interaction

Nous allons analyser un réseau de collaboration scientifique en informatique. Le réseau est extrait de DBLP et disponible sur SNAP.

**1. Lecture des données**

pour commencer nous allons télécharger dans un premier temps le fichier qui contient des données depuis [FileSourceEdge](https://data.graphstream-project.org/api/gs-core/current/org/graphstream/stream/file/FileSourceEdge.html).
ensuite nous allons lire les données à l'aide de la fonction `readAll()` qui se trouve dans *graphStream* après avoir instancier un `FileSourceEdge()`.

**2. mesures de base**

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

- nombre de nœuds : 317080
- nombre de liens : 1049866
- degré moyen : 6.62208890914917
- coefficient de clustering : 0.6324308280637396

La probabilité p est définie comme p=$`\frac {⟨k⟩}{N}`$
⟨k⟩ est le degré moyen et N est le nombre de nœuds.

En effet, le coefficient de clustering moyen dans un graphe aléatoire G(N,p) est la moyenne des coefficients de clustering de tous les nœuds ayant un degré supérieur ou égal à 2.

Dans notre cas, la moyenne du coefficient de clustering pour un réseau aléatoire de la même taille et du même degré moyen p=$`\frac {⟨k⟩}{N}`$ est calculée comme suit :
 
 $`C_{aléatoire} <=> p = \frac{⟨k⟩}{N} <=> \frac{6.62208890914917}{317080} \approx 2.0884 × 10 ^ {-5} `$ 
 
