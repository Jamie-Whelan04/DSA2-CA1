package utils;

import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class TSPSolverTest {

    private LeafCluster clusterAt(int x, int y) {
        LeafCluster c = new LeafCluster();
        c.add(x, y);
        return c;
    }

    @Test
    public void testEmptyInputReturnsEmptyPath() {
        List<LeafCluster> result = TSPSolver.solve(List.of());
        assertTrue(result.isEmpty());
    }

    @Test
    public void testSingleClusterReturnsSelf() {
        LeafCluster c = clusterAt(5, 5);
        List<LeafCluster> result = TSPSolver.solve(List.of(c));
        assertEquals(1, result.size());
        assertEquals(c, result.get(0));
    }

    @Test
    public void testAllClustersVisited() {
        LeafCluster a = clusterAt(0, 0);
        LeafCluster b = clusterAt(10, 0);
        LeafCluster c = clusterAt(20, 0);
        List<LeafCluster> result = TSPSolver.solve(Arrays.asList(a, b, c));
        assertEquals(3, result.size());
        assertTrue(result.containsAll(Arrays.asList(a, b, c)));
    }

    @Test
    public void testNearestNeighbourOrder() {
        // a is at origin, b is close to a, c is far away
        // expected greedy order: a -> b -> c
        LeafCluster a = clusterAt(0, 0);
        LeafCluster b = clusterAt(1, 0);
        LeafCluster c = clusterAt(100, 0);
        List<LeafCluster> result = TSPSolver.solve(Arrays.asList(a, b, c));
        assertEquals(a, result.get(0));
        assertEquals(b, result.get(1));
        assertEquals(c, result.get(2));
    }

    @Test
    public void testNoDuplicatesInPath() {
        LeafCluster a = clusterAt(0, 0);
        LeafCluster b = clusterAt(5, 5);
        LeafCluster c = clusterAt(10, 10);
        List<LeafCluster> result = TSPSolver.solve(Arrays.asList(a, b, c));
        long distinct = result.stream().distinct().count();
        assertEquals(result.size(), distinct);
    }
}
