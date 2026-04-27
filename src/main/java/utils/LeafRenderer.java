package utils;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.util.List;
import java.util.Random;

public class LeafRenderer {

    public static void drawClusters(Canvas canvas, Image img, List<LeafCluster> clusters) {

        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.drawImage(img, 0, 0);

        for (LeafCluster c : clusters) {
            gc.setStroke(Color.BLUE);
            gc.strokeRect(c.getMinX(), c.getMinY(),
                    c.getWidth(), c.getHeight());

            gc.fillText("" + c.getRank(),
                    c.getMinX(), c.getMinY());
        }
    }

    public static void drawRandom(Canvas canvas, List<LeafCluster> clusters) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        Random r = new Random();

        for (LeafCluster c : clusters) {
            Color color = Color.color(r.nextDouble(), r.nextDouble(), r.nextDouble());
            gc.setFill(color);  // set once per cluster

            for (int[] p : c.getPixels()) {
                gc.fillRect(p[0], p[1], 1, 1);  // colour only the actual leaf pixel
            }
        }
    }
}