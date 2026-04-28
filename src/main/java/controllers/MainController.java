package controllers;

import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.util.Duration;
import javafx.scene.image.ImageView;

import utils.*;

import java.io.File;
import java.util.*;

public class MainController {

    @FXML private Canvas canvas;
    @FXML private Slider hueMinSlider, hueMaxSlider;

    private Image image;
    private ImageProcessor processor;
    private List<LeafCluster> clusters;
    private Timeline tspAnimation; // for stopping animation

    @FXML
    public void loadImage() {

        File file = new FileChooser().showOpenDialog(null);

        if (file != null) {

            Image original = new Image(file.toURI().toString());

            image = resizeImage(original, 512);

            canvas.setWidth(image.getWidth());
            canvas.setHeight(image.getHeight());

            canvas.getGraphicsContext2D().drawImage(image, 0, 0);
        }
    }

    @FXML
    public void processImage() {
        if (image == null) {
            System.out.println("No image loaded");
            return;
        }
        processor = new ImageProcessor(image);
        processor.setHueRange(
                hueMinSlider.getValue(),
                hueMaxSlider.getValue()
        );
        processor.process();
        clusters = processor.getClusters();
        LeafRenderer.drawClusters(canvas, image, clusters);
        System.out.println("Processing complete. Clusters found: " + clusters.size());
    }

    @FXML
    public void handleClick(MouseEvent e) {
        for (LeafCluster c : clusters) {
            if (c.contains(e.getX(), e.getY())) {
                System.out.println("Cluster size: " + c.getSize());

                // Add this:
                Random r = new Random();
                GraphicsContext gc = canvas.getGraphicsContext2D();
                gc.setFill(Color.color(r.nextDouble(), r.nextDouble(), r.nextDouble()));
                for (int[] p : c.getPixels()) {
                    gc.fillRect(p[0], p[1], 1, 1);
                }
            }
        }
    }

    @FXML
    public void randomColors() {
        LeafRenderer.drawRandom(canvas, clusters);
    }

    @FXML
    public void runTSP() {

        if (clusters == null || clusters.isEmpty()) return;

        List<LeafCluster> path = TSPSolver.solve(clusters);
        var gc = canvas.getGraphicsContext2D();

        tspAnimation = new Timeline();

        for (int i = 0; i < path.size() - 1; i++) {

            LeafCluster a = path.get(i);
            LeafCluster b = path.get(i + 1);

            KeyFrame kf = new KeyFrame(Duration.millis(i * 40), e -> {
                gc.setStroke(Color.YELLOW);
                gc.strokeLine(
                        a.getCenterX(), a.getCenterY(),
                        b.getCenterX(), b.getCenterY()
                );
            });

            tspAnimation.getKeyFrames().add(kf);
        }

        tspAnimation.play();
    }
    @FXML
    public void resetImage() {
        hueMinSlider.setValue(10);
        hueMaxSlider.setValue(60);
        if (image == null) return;

        // Stop animation if running
        if (tspAnimation != null) {
            tspAnimation.stop();
        }

        // Clear clusters
        clusters = null;
        processor = null;

        // Redraw original image
        canvas.getGraphicsContext2D().clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        canvas.getGraphicsContext2D().drawImage(image, 0, 0);

        System.out.println("Reset complete");
    }

    private Image resizeImage(Image original, int maxSize) {

        double width = original.getWidth();
        double height = original.getHeight();

        double scale = Math.min(maxSize / width, maxSize / height);

        // If the image is already small, return it
        if (scale >= 1) return original;

        int newWidth = (int)(width * scale);
        int newHeight = (int)(height * scale);

        ImageView iv = new ImageView(original);
        iv.setFitWidth(newWidth);
        iv.setFitHeight(newHeight);
        iv.setPreserveRatio(true);

        return iv.snapshot(null, null);
    }

    private void drawBlackWhiteClusters() {

        if (clusters == null) return;

        var gc = canvas.getGraphicsContext2D();

        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        // Background black
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        // Draw ONLY cluster pixels
        gc.setFill(Color.WHITE);

        for (LeafCluster c : clusters) {
            for (int[] p : c.getPixels()) {
                gc.fillRect(p[0], p[1], 1, 1);
            }
        }
    }


    @FXML
    public void showBW() {
        drawBlackWhiteClusters();
    }

    @FXML
    public void showRanks() {
        if (clusters == null) return;
        LeafRenderer.showRanks(canvas, clusters);
    }

}