package utils;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UnionFindTest {

    @Test
    public void testInitiallyAllSeparate() {
        UnionFind uf = new UnionFind(5);
        // Each element is its own root
        for (int i = 0; i < 5; i++) {
            assertEquals(i, uf.find(i));
        }
    }

    @Test
    public void testUnionConnectsTwoElements() {
        UnionFind uf = new UnionFind(5);
        uf.union(0, 1);
        assertEquals(uf.find(0), uf.find(1));
    }

    @Test
    public void testUnionIsTransitive() {
        UnionFind uf = new UnionFind(5);
        uf.union(0, 1);
        uf.union(1, 2);
        assertEquals(uf.find(0), uf.find(2));
    }

    @Test
    public void testUnconnectedElementsHaveDifferentRoots() {
        UnionFind uf = new UnionFind(5);
        uf.union(0, 1);
        assertNotEquals(uf.find(0), uf.find(2));
    }

    @Test
    public void testUnionSameElementDoesNothing() {
        UnionFind uf = new UnionFind(5);
        uf.union(3, 3);
        assertEquals(3, uf.find(3));
    }

    @Test
    public void testFindWithPathCompression() {
        UnionFind uf = new UnionFind(10);
        uf.union(0, 1);
        uf.union(1, 2);
        uf.union(2, 3);
        // After path compression, all should share the same root
        int root = uf.find(0);
        assertEquals(root, uf.find(1));
        assertEquals(root, uf.find(2));
        assertEquals(root, uf.find(3));
    }
}