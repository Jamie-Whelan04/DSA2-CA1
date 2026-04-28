package utils;

import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class MergeSortTest {

    private LeafCluster clusterOfSize(int size) {
        LeafCluster c = new LeafCluster();
        for (int i = 0; i < size; i++) {
            c.add(i, 0);
        }
        return c;
    }

    @Test
    public void testSortDescendingBySize() {
        LeafCluster small = clusterOfSize(2);
        LeafCluster medium = clusterOfSize(5);
        LeafCluster large = clusterOfSize(10);

        List<LeafCluster> list = Arrays.asList(small, large, medium);
        MergeSort.sort(list);

        assertEquals(10, list.get(0).getSize());
        assertEquals(5, list.get(1).getSize());
        assertEquals(2, list.get(2).getSize());
    }

    @Test
    public void testSortSingleElement() {
        List<LeafCluster> list = Arrays.asList(clusterOfSize(3));
        MergeSort.sort(list);
        assertEquals(3, list.get(0).getSize());
    }

    @Test
    public void testSortEmptyList() {
        List<LeafCluster> list = Arrays.asList();
        assertDoesNotThrow(() -> MergeSort.sort(list));
    }

    @Test
    public void testSortAlreadySorted() {
        LeafCluster a = clusterOfSize(10);
        LeafCluster b = clusterOfSize(5);
        List<LeafCluster> list = Arrays.asList(a, b);
        MergeSort.sort(list);
        assertEquals(10, list.get(0).getSize());
        assertEquals(5, list.get(1).getSize());
    }

    @Test
    public void testSortEqualSizes() {
        LeafCluster a = clusterOfSize(5);
        LeafCluster b = clusterOfSize(5);
        List<LeafCluster> list = Arrays.asList(a, b);
        assertDoesNotThrow(() -> MergeSort.sort(list));
        assertEquals(5, list.get(0).getSize());
        assertEquals(5, list.get(1).getSize());
    }
}