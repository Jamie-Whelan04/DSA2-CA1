package utils;

import java.util.List;

public class MergeSort {
    //Sorts list of clusters in descending order by size. i.e larger clusters will have smaller numbers attached
    public static void sort(List<LeafCluster> list) {
        list.sort((a, b) -> b.getSize() - a.getSize());
    }
}