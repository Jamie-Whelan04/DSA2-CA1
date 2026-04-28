package utils;
import java.util.ArrayList;
import java.util.List;

public class LeafCluster {
    //Minimum X and Y values
    private int minX = Integer.MAX_VALUE;
    private int minY = Integer.MAX_VALUE;
    //Maximum X and Y values
    private int maxX = 0;
    private int maxY = 0;
    //Total number of pixels in cluster
    private int size = 0;
    //Rank assigned after sorting clusters
    private int rank;
    //List of all pixel coordinates in the cluster
    private List<int[]> pixels = new ArrayList<>();

    //Adds a pixel to the cluster and updates its size, list and box
    public void add(int x, int y) {
        size++;
        //Store pixel coords
        pixels.add(new int[]{x, y});
        //Update box values
        minX = Math.min(minX, x);
        minY = Math.min(minY, y);
        maxX = Math.max(maxX, x);
        maxY = Math.max(maxY, y);
    }

    //Checks if a point is within the bounding box
    public boolean contains(double x, double y) {
        return x >= minX && x <= maxX &&
                y >= minY && y <= maxY;
    }

    //Returns the number of pixels in the cluster
    public int getSize() { return size; }

    //Returns the list of all pixels in the cluster
    public List<int[]> getPixels() {
        return pixels;
    }

    //Sets Cluster Rank
    public void setRank(int r) { rank = r; }

    //Gets Cluster Rank
    public int getRank() { return rank; }

    //Gets the X coords of the clusters Centre
    public int getCenterX() { return (minX + maxX) / 2; }

    //Gets the Y coords of the clusters Centre
    public int getCenterY() { return (minY + maxY) / 2; }

    //Gets the minimum X value
    public int getMinX() { return minX; }

    //Gets the minimum Y value
    public int getMinY() { return minY; }

    //Returns width of the cluster
    public int getWidth() { return maxX - minX; }

    //Returns Height of the cluster
    public int getHeight() { return maxY - minY; }
}