package utils;

import java.util.List;

public class MergeSort {

    public static void sort(List<LeafCluster> list) {
        list.sort((a, b) -> b.getSize() - a.getSize());
    }
}