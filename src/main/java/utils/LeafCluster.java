package utils;
import java.util.ArrayList;
import java.util.List;

public class LeafCluster {

    private int minX = Integer.MAX_VALUE;
    private int minY = Integer.MAX_VALUE;
    private int maxX = 0;
    private int maxY = 0;
    private int size = 0;
    private int rank;
    private List<int[]> pixels = new ArrayList<>();

    public void add(int x, int y) {
        size++;
        pixels.add(new int[]{x, y}); // <-- STORE PIXEL
        minX = Math.min(minX, x);
        minY = Math.min(minY, y);
        maxX = Math.max(maxX, x);
        maxY = Math.max(maxY, y);
    }

    public boolean contains(double x, double y) {
        return x >= minX && x <= maxX &&
                y >= minY && y <= maxY;
    }

    public int getSize() { return size; }

    public List<int[]> getPixels() {
        return pixels;
    }

    public void setRank(int r) { rank = r; }
    public int getRank() { return rank; }

    public int getCenterX() { return (minX + maxX) / 2; }
    public int getCenterY() { return (minY + maxY) / 2; }

    public int getMinX() { return minX; }
    public int getMinY() { return minY; }
    public int getWidth() { return maxX - minX; }
    public int getHeight() { return maxY - minY; }
}