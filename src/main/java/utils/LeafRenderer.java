package utils;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.util.List;
import java.util.Random;

public class LeafRenderer {

    //Draws the original image and overlays bounding boxes around each cluster
    public static void drawClusters(Canvas canvas, Image img, List<LeafCluster> clusters) {
        //Gets the graphics used for drawing
        GraphicsContext gc = canvas.getGraphicsContext2D();
        //Draws original image onto the canvas
        gc.drawImage(img, 0, 0);
        //Loop through each cluster and draw its bounding box
        for (LeafCluster c : clusters) {
            gc.setStroke(Color.BLUE);
            //Draw Rectangle around bounding box
            gc.strokeRect(c.getMinX(), c.getMinY(),
                    c.getWidth(), c.getHeight());
        }
    }

    //Displays the Ranks of each cluster on the canvas
    public static void showRanks(Canvas canvas, List<LeafCluster> clusters) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        //Sets text colour to black
        gc.setFill(Color.BLACK);
        //Draw rank on the top left of each bounding box
        for (LeafCluster c : clusters) {
            gc.fillText("" + c.getRank(),
                    c.getMinX(), c.getMinY());
        }
    }

    //Draws a random colour on each cluster
    public static void drawRandom(Canvas canvas, List<LeafCluster> clusters) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        //Random generator for selecting colour
        Random r = new Random();
        //Loops through each cluster
        for (LeafCluster c : clusters) {
            //Generate a random colour for this cluster
            Color color = Color.color(r.nextDouble(), r.nextDouble(), r.nextDouble());
            gc.setFill(color);  //set fill colour once per cluster
            //Draw each pixel belonging to that cluster
            for (int[] p : c.getPixels()) {
                //Draws pixel
                gc.fillRect(p[0], p[1], 1, 1);  // colour only the actual leaf pixel
            }
        }
    }
}