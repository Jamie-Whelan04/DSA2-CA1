package utils;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class LeafClusterTest {

    @Test
    public void testAddIncreasesSize() {
        LeafCluster c = new LeafCluster();
        c.add(5, 10);
        c.add(6, 11);
        assertEquals(2, c.getSize());
    }

    @Test
    public void testBoundingBoxUpdates() {
        LeafCluster c = new LeafCluster();
        c.add(3, 7);
        c.add(10, 20);
        assertEquals(3, c.getMinX());
        assertEquals(7, c.getMinY());
        assertEquals(7, c.getWidth());   // maxX(10) - minX(3)
        assertEquals(13, c.getHeight()); // maxY(20) - minY(7)
    }

    @Test
    public void testContainsInsideBounds() {
        LeafCluster c = new LeafCluster();
        c.add(0, 0);
        c.add(10, 10);
        assertTrue(c.contains(5, 5));
    }

    @Test
    public void testContainsOutsideBounds() {
        LeafCluster c = new LeafCluster();
        c.add(0, 0);
        c.add(10, 10);
        assertFalse(c.contains(15, 15));
    }

    @Test
    public void testCenterCalculation() {
        LeafCluster c = new LeafCluster();
        c.add(0, 0);
        c.add(10, 20);
        assertEquals(5, c.getCenterX());
        assertEquals(10, c.getCenterY());
    }

    @Test
    public void testPixelsStored() {
        LeafCluster c = new LeafCluster();
        c.add(3, 4);
        c.add(5, 6);
        assertEquals(2, c.getPixels().size());
    }

    @Test
    public void testRankSetAndGet() {
        LeafCluster c = new LeafCluster();
        c.setRank(7);
        assertEquals(7, c.getRank());
    }
}