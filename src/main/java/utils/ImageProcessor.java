package utils;

import javafx.scene.image.*;
import javafx.scene.paint.Color;

import java.util.*;

public class ImageProcessor {

    private Image image;
    private boolean[][] binary;
    private List<LeafCluster> clusters = new ArrayList<>();

    private double hueMin = 10;
    private double hueMax = 60;

    public ImageProcessor(Image image) {
        this.image = image;
        binary = new boolean[(int)image.getHeight()][(int)image.getWidth()];
    }

    public void setHueRange(double min, double max) {
        hueMin = min;
        hueMax = max;
    }

    public void process() {
        convert();
        findClusters();
        sort();
    }

    public boolean[][] getBinary() {
        return binary;
    }

    private void convert() {
        PixelReader pr = image.getPixelReader();

        for (int y = 0; y < binary.length; y++) {
            for (int x = 0; x < binary[0].length; x++) {

                Color c = pr.getColor(x, y);

                binary[y][x] =
                        c.getHue() >= hueMin &&
                                c.getHue() <= hueMax;
            }
        }
    }

    private void findClusters() {
        int w = binary[0].length;
        int h = binary.length;

        UnionFind uf = new UnionFind(w * h);
        Map<Integer, LeafCluster> map = new HashMap<>();

        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {

                if (!binary[y][x]) continue;

                int id = y * w + x;

                if (x + 1 < w && binary[y][x + 1])
                    uf.union(id, y * w + x + 1);

                if (y + 1 < h && binary[y + 1][x])
                    uf.union(id, (y + 1) * w + x);
            }
        }

        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {

                if (!binary[y][x]) continue;

                int id = y * w + x;
                int root = uf.find(id);

                map.putIfAbsent(root, new LeafCluster());
                map.get(root).add(x, y);
            }
        }

        clusters = new ArrayList<>(map.values());
    }

    private void sort() {
        MergeSort.sort(clusters);

        for (int i = 0; i < clusters.size(); i++) {
            clusters.get(i).setRank(i + 1);
        }
    }

    public List<LeafCluster> getClusters() {
        return clusters;
    }
}