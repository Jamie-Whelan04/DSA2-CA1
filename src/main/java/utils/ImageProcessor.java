package utils;

import javafx.scene.image.*;
import javafx.scene.paint.Color;

import java.util.*;

public class ImageProcessor {

    private Image image; //Original Image
    private boolean[][] binary; //Binary of the image
    private List<LeafCluster> clusters = new ArrayList<>(); // List of Clusters found
    //Hue range when filtering clusters
    private double hueMin = 10;
    private double hueMax = 60;
    //Constructor
    public ImageProcessor(Image image) {
        this.image = image;
        binary = new boolean[(int)image.getHeight()][(int)image.getWidth()];
    }
    //Sets hue range used for filtering clusters
    public void setHueRange(double min, double max) {
        hueMin = min;
        hueMax = max;
    }
    //Converts image to binary, finds clusters and sorts them
    public void process() {
        convert();
        findClusters();
        sort();
    }
    //Returns binary image
    public boolean[][] getBinary() {
        return binary;
    }
    //Converts image to binary matrix and then if a pixel is found to be within hue range it is marked true
    private void convert() {
        PixelReader pr = image.getPixelReader();
        //Loops through every pixel
        for (int y = 0; y < binary.length; y++) {
            for (int x = 0; x < binary[0].length; x++) {
                //Get pixel colour
                Color c = pr.getColor(x, y);
                //Sets binary value based on hue range
                binary[y][x] =
                        c.getHue() >= hueMin &&
                                c.getHue() <= hueMax;
            }
        }
    }
    //Finds connected clusters of "true" pixels using Union-Find
    private void findClusters() {
        int w = binary[0].length;
        int h = binary.length;
        //Union-Find for grouping connected pixels
        UnionFind uf = new UnionFind(w * h);
        Map<Integer, LeafCluster> map = new HashMap<>();
        //First pass: union adjacent "true" pixels
        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                //Skip if pixel is not part of the binary mask
                if (!binary[y][x]) continue;

                int id = y * w + x;
                //Check right neighbour
                if (x + 1 < w && binary[y][x + 1])
                    uf.union(id, y * w + x + 1);
                //Check bottom neighbour
                if (y + 1 < h && binary[y + 1][x])
                    uf.union(id, (y + 1) * w + x);
            }
        }
        //Second pass: group pixels by root into clusters
        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                if (!binary[y][x]) continue;
                int id = y * w + x;
                int root = uf.find(id);
                // Create cluster if it doesn't exist
                map.putIfAbsent(root, new LeafCluster());
                // Add pixel to its cluster
                map.get(root).add(x, y);
            }
        }
        //Stores cluster in List
        clusters = new ArrayList<>(map.values());
    }
    //Sorts clusters and assigns a rank
    private void sort() {
        MergeSort.sort(clusters);

        for (int i = 0; i < clusters.size(); i++) {
            clusters.get(i).setRank(i + 1);
        }
    }
    //Returns list of clusters found
    public List<LeafCluster> getClusters() {
        return clusters;
    }
}