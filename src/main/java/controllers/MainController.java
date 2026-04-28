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

//Main Controller for handling all of the UI interactions
public class MainController {

    //Canvas where the image and visuals are drawn
    @FXML private Canvas canvas;
    //Sliders for selecting hue range
    @FXML private Slider hueMinSlider, hueMaxSlider;

    private Image image; //Currently loaded image
    private ImageProcessor processor; //Image processing util
    private List<LeafCluster> clusters; //List of clusters
    private Timeline tspAnimation; // for stopping animation

    //Opens a file explorer and loads image onto canvas
    @FXML
    public void loadImage() {
        //Open file
        File file = new FileChooser().showOpenDialog(null);

        //If file was selected
        if (file != null) {

            //Load image from file
            Image original = new Image(file.toURI().toString());
            //Resize the image to fit within the 512px
            image = resizeImage(original, 512);
            //Resize canvas to match image dimensions
            canvas.setWidth(image.getWidth());
            canvas.setHeight(image.getHeight());
            //Draw image onto canvas
            canvas.getGraphicsContext2D().drawImage(image, 0, 0);
        }
    }

    //Process the loaded image and find the clusters based on a hue range
    @FXML
    public void processImage() {
        //Ensure the image was loaded
        if (image == null) {
            System.out.println("No image loaded");
            return;
        }

        //Create the processor and set hue filter range
        processor = new ImageProcessor(image);
        processor.setHueRange(
                hueMinSlider.getValue(),
                hueMaxSlider.getValue()
        );

        //Perform processing
        processor.process();
        //Retrieve clusters
        clusters = processor.getClusters();
        //Draw clusters
        LeafRenderer.drawClusters(canvas, image, clusters);
        System.out.println("Processing complete. Clusters found: " + clusters.size());
    }

    //Handles when mouse is clicked on a cluster to highlight said cluster
    @FXML
    public void handleClick(MouseEvent e) {
        for (LeafCluster c : clusters) {
            //Check if the click was within cluster bounds
            if (c.contains(e.getX(), e.getY())) {
                System.out.println("Cluster size: " + c.getSize());
                //Fill the cluster with a random colour
                Random r = new Random();
                GraphicsContext gc = canvas.getGraphicsContext2D();
                gc.setFill(Color.color(r.nextDouble(), r.nextDouble(), r.nextDouble()));
                //Draw each pixel belonging to the cluster
                for (int[] p : c.getPixels()) {
                    gc.fillRect(p[0], p[1], 1, 1);
                }
            }
        }
    }

    //Draws all clusters in random colours
    @FXML
    public void randomColors() {
        LeafRenderer.drawRandom(canvas, clusters);
    }

    //Runs the TSP solver on the cluster centers
    @FXML
    public void runTSP() {
        //Ensure the cluster exists
        if (clusters == null || clusters.isEmpty()) return;
        //Compute the path using the TSPSolver
        List<LeafCluster> path = TSPSolver.solve(clusters);
        var gc = canvas.getGraphicsContext2D();
        //Create the animation
        tspAnimation = new Timeline();
        //Create the animation frames for each segment in the path
        for (int i = 0; i < path.size() - 1; i++) {

            LeafCluster a = path.get(i);
            LeafCluster b = path.get(i + 1);
            //Each keyframe draws one line segment after a delay
            KeyFrame kf = new KeyFrame(Duration.millis(i * 40), e -> {
                gc.setStroke(Color.YELLOW);
                gc.strokeLine(
                        a.getCenterX(), a.getCenterY(),
                        b.getCenterX(), b.getCenterY()
                );
            });

            tspAnimation.getKeyFrames().add(kf);
        }
        //Start animation
        tspAnimation.play();
    }

    //Resets the UI and restores the image
    @FXML
    public void resetImage() {
        //Resets the hue sliders
        hueMinSlider.setValue(10);
        hueMaxSlider.setValue(60);
        //If no image was loaded there is nothing to reset
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

    //Resizes an image while keeping aspect ratio
    private Image resizeImage(Image original, int maxSize) {

        double width = original.getWidth();
        double height = original.getHeight();
        //Chooses scaling factor
        double scale = Math.min(maxSize / width, maxSize / height);

        // If the image is already small return it
        if (scale >= 1) return original;
        //Compute new dimensions
        int newWidth = (int)(width * scale);
        int newHeight = (int)(height * scale);
        //Use ImageView to resize the image
        ImageView iv = new ImageView(original);
        iv.setFitWidth(newWidth);
        iv.setFitHeight(newHeight);
        iv.setPreserveRatio(true);
        //Snapshot of the resized image
        return iv.snapshot(null, null);
    }

    //Draws the clusters in black and white
    private void drawBlackWhiteClusters() {

        if (clusters == null) return;

        var gc = canvas.getGraphicsContext2D();
        //Clears the canvas
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        //Background black
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        //Draw only cluster pixels
        gc.setFill(Color.WHITE);

        for (LeafCluster c : clusters) {
            for (int[] p : c.getPixels()) {
                gc.fillRect(p[0], p[1], 1, 1);
            }
        }
    }

    //Displays clusters in BW mode
    @FXML
    public void showBW() {
        drawBlackWhiteClusters();
    }

    //Displays the clusters ranked
    @FXML
    public void showRanks() {
        if (clusters == null) return;
        LeafRenderer.showRanks(canvas, clusters);
    }

}