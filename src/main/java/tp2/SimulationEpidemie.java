package tp2;
import org.graphstream.algorithm.Toolkit;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;


public class SimulationEpidemie {
    Graph graph;
    public SimulationEpidemie(Graph graph) {
        this.graph=graph;
    }

    public void scinario1(String textFile) {
        int jours = 90;
        long[] infections = new long[jours + 1];

        // Initialisation du patient0 (premier patient infecté)
        Node patient0 = Toolkit.randomNode(graph);
        patient0.setAttribute("health", "infected");
        ArrayList<Node> malades = new ArrayList<>();
        malades.add(patient0);

        // Simulation de la propagation pendant 90 jours
        for (int jour = 1; jour <= jours; jour++) {
            // Nouveaux malades pour le jour actuel
            ArrayList<Node> nouveauxMalades = new ArrayList<>();

            // Transmission du virus aux voisins des malades existants
            for (Node n : malades) {
                if ("infected".equals(n.getAttribute("health"))) {
                    n.neighborNodes().forEach(neighbor -> {
                        if (!"infected".equals(neighbor.getAttribute("health")) && Math.random() < 1.0 / 7.0) {
                            neighbor.setAttribute("health", "infected");
                            nouveauxMalades.add(neighbor);
                        }
                    });
                }
            }

            // Ajouter les nouveaux malades à la liste des malades
            malades.addAll(nouveauxMalades);


            // Mise à jour du nombre d'infections pour ce jour
            // Ajouter le nombre d'infectés à la liste des infections
            ArrayList<Node> infectes = new ArrayList<>(malades);
            // Supprimer les immunisés de la liste des infectés
           // infectes.removeIf(node -> "immune".equals(node.getAttribute("health")));
            infections[jour] = infectes.size();
            // Afficher les résultats
            System.out.println("Jour " + jour + ":");
            System.out.println("   Nouveaux infectés : " + nouveauxMalades.size());
           // System.out.println("   Immunisés : " + nouveauxMalades.stream().filter(node -> "immune".equals(node.getAttribute("health"))).count());
            System.out.println("   Total infectés : " + infectes.size());
        }


        // Sauvegarde des résultats dans un fichier
        try (PrintWriter fichier = new PrintWriter(new FileWriter(textFile))) {
            for (int i = 0; i <= jours; i++) {
                fichier.println(i + "   " + infections[i]);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void scenario2(String textFile) {
        int jours = 90;
        long[] infections = new long[jours + 1];

        // Initialisation du patient0 (premier patient infecté) qui n'est pas immunisé

        immuniserAleatoire();

        AtomicReference<Double> degreMoyengrp1 = new AtomicReference<>(0.0);
        AtomicReference<Double> degreMoyengrp2 = new AtomicReference<>(0.0);

        for (Node node : graph) {
            if (node.hasAttribute("immunized")) {
                //parcourir les voisins
                node.neighborNodes().forEach(neighbor -> {
                    if (neighbor.hasAttribute("immunized")) {
                        degreMoyengrp1.updateAndGet(v -> v + 1);

                    }
                });
            } else {
                node.neighborNodes().forEach(neighbor -> {
                    if (!neighbor.hasAttribute("immunized")) {
                        degreMoyengrp2.updateAndGet(v -> v + 1);
                    }
                });
            }
        }
        int t = graph.getNodeCount()/2;
        degreMoyengrp1.updateAndGet(v -> v / t);
        degreMoyengrp2.updateAndGet(v -> v / t);
        System.out.println("degre moyen grp1 : " + degreMoyengrp1);
        System.out.println("degre moyen grp2 : " + degreMoyengrp2);

        Graph senario2 = graph;
        Propagation p = new Propagation(senario2);
        List<Node> immunisation = Toolkit.randomNodeSet(graph, graph.getNodeCount());
        //suprimer les noeuds immuniser du graphe senario2
        for (Node node : immunisation) {
            if (node.hasAttribute("immunized")) {
                senario2.removeNode(node);
            }
        }
        System.out.println("seill de la pidemie : " + Toolkit.averageDegree(senario2)/p.dispersionDegre());
        Node patient0 = Toolkit.randomNode(graph);
        patient0.setAttribute("health", "infected");
        patient0.removeAttribute("immunized");

        //afficher le seill de la pidemie
         // Simulation de la propagation pendant 90 jours
        for (int jour = 1; jour <= jours; jour++) {
            ArrayList<Node> malades = new ArrayList<>();

            graph.nodes().forEach(node -> {
                if ("infected".equals(node.getAttribute("health"))) {
                    // Transmission du virus aux voisins avec une probabilité de 1/7
                    node.neighborNodes().forEach(neighbor -> {
                        if (!neighbor.hasAttribute("immunized") && Math.random() < 1.0 / 7.0) {
                            neighbor.setAttribute("health", "infected");
                            malades.add(neighbor);
                        }
                    });
                }
            });



            // Mise à jour du nombre d'infections pour ce jour
            //ajouter que les infecter a une liste
            ArrayList<Node> infecter = new ArrayList<>();
            graph.nodes().forEach(node -> {
                if ("infected".equals(node.getAttribute("health"))) {
                    infecter.add(node);
                }
            });
            //supprimer les immuniser de la liste des infecter
            infecter.removeIf(node -> "immune".equals(node.getAttribute("health")));
            //ajouter le nombre d'infecter a la liste des infections
            infections[jour] = infecter.size();

            System.out.println("infected : " + infecter.size());
        }

        // Sauvegarde des résultats dans un fichier
        try (PrintWriter fichier = new PrintWriter(new FileWriter(  textFile))) {
            for (int i = 0; i <= jours; i++) {
                fichier.println(i + "   " + infections[i]);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void immuniserAleatoire() {
        List<Node> immunisation = Toolkit.randomNodeSet(graph, graph.getNodeCount() / 2);
        AtomicInteger immunizedCount = new AtomicInteger(0);


        // Immunize the selected nodes
        immunisation.forEach(node -> {
                 node.setAttribute("health", "immune");
                node.setAttribute("immunized", true);

                immunizedCount.incrementAndGet();
        });


        // Display the number of immunized nodes (including neighbors)
        System.out.println("Immunized: " + immunizedCount.get());
    }

    public void scinario3(String textFile) {
        int jours = 90;
        long[] infections = new long[jours + 1];
        immuniserSelective();

        AtomicReference<Double> degreMoyengrp1 = new AtomicReference<>(0.0);
        AtomicReference<Double> degreMoyengrp2 = new AtomicReference<>(0.0);

        for (Node node : graph) {
            if (node.hasAttribute("immunized")) {
               //parcourir les voisins
                 node.neighborNodes().forEach(neighbor -> {
                    if (neighbor.hasAttribute("immunized")) {
                        degreMoyengrp1.updateAndGet(v -> v + 1);

                    }
                 });
            } else {
                 node.neighborNodes().forEach(neighbor -> {
                    if (!neighbor.hasAttribute("immunized")) {
                         degreMoyengrp2.updateAndGet(v -> v + 1);
                     }
                });
            }
        }
        int t = graph.getNodeCount()/2;
        degreMoyengrp1.updateAndGet(v -> v / t);
        degreMoyengrp2.updateAndGet(v -> v / t);
        System.out.println("degre moyen grp1 : " + degreMoyengrp1);
        System.out.println("degre moyen grp2 : " + degreMoyengrp2);

        Graph senario3 = graph;
        Propagation p = new Propagation(senario3);
        List<Node> immunisation = Toolkit.randomNodeSet(graph, graph.getNodeCount());
        //suprimer les noeuds immuniser du graphe senario2
        for (Node node : immunisation) {
            if (!node.hasAttribute("immunized")) {
                senario3.removeNode(node);
            }
        }
         System.out.println("seill de la pidemie : " + Toolkit.averageDegree(senario3)/p.dispersionDegre());
        // Initialisation du patient0 (premier patient infecté)
        Node patient0 = Toolkit.randomNode(graph);
        patient0.setAttribute("health", "infected");
        patient0.removeAttribute("immunized");

        // Simulation de la propagation pendant 90 jours
        for (int jour = 1; jour <= jours; jour++) {
            ArrayList<Node> malades = new ArrayList<>();

            graph.nodes().forEach(node -> {
                if ("infected".equals(node.getAttribute("health"))) {
                    // Transmission du virus aux voisins avec une probabilité de 1/7
                    node.neighborNodes().forEach(neighbor -> {
                        if (!neighbor.hasAttribute("immunized")  && Math.random() < 1.0 / 7.0) {
                            neighbor.setAttribute("health", "infected");
                            malades.add(neighbor);
                        }
                    });
                }
            });

            // Immunisation aléatoire des individus tous les 15 jours
//            if (jour % 15 == 0) {
//                immuniserSelective();
//            }

            // Mise à jour du nombre d'infections pour ce jour
            //ajouter que les infecter a une liste
            ArrayList<Node> infecter = new ArrayList<>();
            graph.nodes().forEach(node -> {
                if ("infected".equals(node.getAttribute("health"))) {
                    infecter.add(node);
                }
            });
            //supprimer les immuniser de la liste des infecter
            infecter.removeIf(node -> "immune".equals(node.getAttribute("health")));
            //ajouter le nombre d'infecter a la liste des infections
            infections[jour] = infecter.size();

            System.out.println("infected : " + infecter.size());
        }

        // Sauvegarde des résultats dans un fichier
        try (PrintWriter fichier = new PrintWriter(new FileWriter(  textFile))) {
            for (int i = 0; i <= jours; i++) {
                fichier.println(i + "   " + infections[i]);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void immuniserSelective() {
        List<Node> immunisation = Toolkit.randomNodeSet(graph, graph.getNodeCount() / 2);
        AtomicInteger immunizedCount = new AtomicInteger(0);

        // Immunize the selected nodes
        immunisation.forEach(node -> {

            // Immunize one neighbor node and then move to the next selected node
            // Create a list of neighbors
            List<Node> neighbors =  new ArrayList<>();
            node.neighborNodes().forEach(neighbor -> {
                neighbors.add(neighbor);
            });

            // Immunize the first affected neighbor that is not already immune
            for (Node neighbor : neighbors) {
                if (!neighbor.hasAttribute("immunized")) {
                    neighbor.setAttribute("health", "immune");
                    neighbor.setAttribute("immunized", true);
                    immunizedCount.incrementAndGet();
                    break;
                }
            }
        });



        // Display the number of immunized nodes (including neighbors)
        System.out.println("Immunized: " + immunizedCount.get());
    }





}