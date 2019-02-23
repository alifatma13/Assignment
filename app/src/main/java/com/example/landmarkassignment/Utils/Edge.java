package com.example.landmarkassignment.Utils;

import java.util.Vector;
// Tried to figure out the implementation of Bellman Ford
public class Edge {

    int source, dest;
    double weight;

    public Edge(int source, int dest, double weight) {
        this.source = source;
        this.dest = dest;
        this.weight = weight;
    }

    public static int UNDEFINED = Integer.MIN_VALUE;

    public static double BellmanFord(Vector<Vector<Edge>> adjList, int startNode,
                                     int endNode) {

        int n = adjList.size();

        // Let dist[i] be minimum distance from start to i.
        double[] dist = new double[n];

        // initialize dist[i]=0 and used[i]=false
        for (int i = 0; i < n; i++) {
            dist[i] = UNDEFINED;
        }
        dist[startNode] = 0;

        // Maximum path to take is n-1 steps.
        for (int i = 0; i < n - 1; i++) {
            // Iterate through nodes.
            for (int j = 0; j < n; j++) {
                // Iterate through neighbors of the node.
                for (int k = 0; k < adjList.get(j).size(); k++) {
                    // Only visit node if path is defined.
                    if (dist[j] == UNDEFINED) {
                        continue;
                    }
                    Edge e = adjList.get(j).get(k);
                    // If dist[e.source] has been used
                    if (dist[e.source] != UNDEFINED) {
                        // If new dist < cur dist or not used, then update node.
                        double newDist = dist[e.source] + e.weight;
                        if (newDist < dist[e.dest] || dist[e.dest] == UNDEFINED) {
                            dist[e.dest] = newDist;
                        }
                    }
                }
            }
        }

        // Check if negative cycle exists.
        for (int j = 0; j < n; j++) {
            for (int k = 0; k < adjList.get(j).size(); k++) {
                Edge e = adjList.get(j).get(k);
                // Check if edge can create negative cycle.
                if (dist[e.source] + e.weight < dist[e.dest]) {
                    System.out.println("Negative cycle exists.");
                }
            }
        }

        // Check if no path exists.
        if (dist[endNode] == UNDEFINED) {
            System.out.println("No path from start to end");
        }

        // Return distance from start to end
        return dist[endNode];
    }
}
