package utils;

import java.util.*;

public class TSPSolver {

    //Solves the TSP for a list of LeafCluster objects
    public static List<LeafCluster> solve(List<LeafCluster> clusters) {
        //store the final ordered path
        List<LeafCluster> path = new ArrayList<>();
        //set of clusters we have not yet visited
        Set<LeafCluster> unvisited = new HashSet<>(clusters);

        //If the input list is empty, return an empty path
        if (clusters.isEmpty()) return path;

        //Start from the first cluster
        LeafCluster current = clusters.get(0);
        path.add(current);
        unvisited.remove(current);

        //Continue until all clusters have been visited
        while (!unvisited.isEmpty()) {

            //Keep track of the nearest neighbor
            LeafCluster nearest = null;
            //Track the best distance found so far
            double bestDist = Double.MAX_VALUE;

            //Check all unvisited clusters to find the closest one
            for (LeafCluster c : unvisited) {
                double dist = distance(current, c);

                //If this cluster is closer than the current best, update it
                if (dist < bestDist) {
                    bestDist = dist;
                    nearest = c;
                }
            }
            //Add the nearest cluster to the path
            path.add(nearest);
            //Mark as visited
            unvisited.remove(nearest);
            //Move to new cluster
            current = nearest;
        }
        //Returns completed path
        return path;
    }

    //Calculates the distance between two leaf cluster centers
    private static double distance(LeafCluster a, LeafCluster b) {
        //Difference in x coords
        double dx = a.getCenterX() - b.getCenterX();
        //Difference in y coords
        double dy = a.getCenterY() - b.getCenterY();
        //Return straight line distance
        return Math.sqrt(dx * dx + dy * dy);
    }
}