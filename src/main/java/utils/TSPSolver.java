package utils;

import java.util.*;

public class TSPSolver {

    public static List<LeafCluster> solve(List<LeafCluster> clusters) {

        List<LeafCluster> path = new ArrayList<>();
        Set<LeafCluster> unvisited = new HashSet<>(clusters);

        if (clusters.isEmpty()) return path;

        LeafCluster current = clusters.get(0);
        path.add(current);
        unvisited.remove(current);

        while (!unvisited.isEmpty()) {

            LeafCluster nearest = null;
            double bestDist = Double.MAX_VALUE;

            for (LeafCluster c : unvisited) {
                double dist = distance(current, c);

                if (dist < bestDist) {
                    bestDist = dist;
                    nearest = c;
                }
            }

            path.add(nearest);
            unvisited.remove(nearest);
            current = nearest;
        }

        return path;
    }

    private static double distance(LeafCluster a, LeafCluster b) {
        double dx = a.getCenterX() - b.getCenterX();
        double dy = a.getCenterY() - b.getCenterY();
        return Math.sqrt(dx * dx + dy * dy);
    }
}