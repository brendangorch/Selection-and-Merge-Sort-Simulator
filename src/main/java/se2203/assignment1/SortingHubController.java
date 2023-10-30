package se2203.assignment1;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.Random;

public class SortingHubController {
    // create UI objects as needed
    @FXML
    private ComboBox<String> algorithmComboBox;
    @FXML
    private Label arraySizeLabel;
    @FXML
    private Slider arraySizeSlider;
    @FXML
    private Button resetButton;
    @FXML
    private Button sortButton;
    @FXML
    private Pane rectanglePane;

    // define attributes
    private int[] intArray = new int[128];
    private SortingStrategy sortingMethod;

    // initialize method
    @FXML
    void initialize() {
        arraySizeSlider.setValue(64); // initialize value of the slider to 64
        arraySizeLabel.setText("64"); // initialize the value of the label for slider to 64
        algorithmComboBox.getItems().setAll("Merge Sort", "Selection Sort"); // set the sort options in the combo box
        algorithmComboBox.setValue("Merge Sort"); // initialize the sort in the combo box to merge sort
        generateRandomArray(); // call generate random array method to generate array of size 64
    }

    // method to generate a random array with no duplicate values
    @FXML
    void generateRandomArray() {
        // set the size of intArray to the slider value
        intArray = new int[(int) (Math.floor(arraySizeSlider.getValue()))];

        // populate the array with values 1 to the array size
        for (int i = 0; i < Math.floor(arraySizeSlider.getValue()); i++) {
            intArray[i] = i + 1;
        }
        // shuffle the values of the array
        Random rand = new Random();
        for (int i = 0; i < Math.floor(arraySizeSlider.getValue()); i++) {
            int randomIndex = rand.nextInt(((int) Math.floor(arraySizeSlider.getValue())));
            int temporary = intArray[randomIndex];
            intArray[randomIndex] = intArray[i];
            intArray[i] = temporary;
        }
        // call updateGraph with the generated random array
        updateGraph(intArray);
    }

    // updateGraph method
    void updateGraph(int[] data) {

        // define the width of each rectangle bar
        // subtracting 2 ensures space between each bar
        double width = (rectanglePane.getPrefWidth() / Math.floor(arraySizeSlider.getValue())) - 2;

        // runLater function to run the code in the for loop of updateGraph after sorting
        Platform.runLater(() -> {
            // clear pane of rectangles
            rectanglePane.getChildren().clear();
            // for loop to add rectangles to the pane
            for (int i = 0; i < Math.floor(arraySizeSlider.getValue()); i++) {
                // define the height of the rectangle
                double height = 0;
                // try-catch loop to ensure no index out of bounds in array at 'i'
                try {
                    // scale the height of the rectangles to fill the pane height
                    height = (data[i] * rectanglePane.getPrefHeight()) / Math.floor(arraySizeSlider.getValue());
                } catch (IndexOutOfBoundsException e) {
                }
                // define the x and y positions of the rectangle
                double xPosition = ((i) * (width + 2)); // adding 2 will ensure the whole pane width is filled
                double yPosition = rectanglePane.getPrefHeight() - height;
                // create a rectangle object with the defined x/y positions, width, and height
                Rectangle rec = new Rectangle(xPosition, yPosition, width, height);
                // set color of rectangle to red
                rec.setFill(Color.RED);
                // add the rectangle to the pane
                rectanglePane.getChildren().add(rec);
            }
        });
    }

    // event listener for the slider object
    @FXML
    void updateDisplay(MouseEvent event) {
        // clear the pane of rectangles when the slider is moved
        rectanglePane.getChildren().clear();
        // set the text of the label to the value of the slider
        arraySizeLabel.setText("" + (int) arraySizeSlider.getValue());
        // call generateRandomArray method
        generateRandomArray();
    }

    // method called when sort button is clicked
    @FXML
    void setSortStrategy(MouseEvent event) {
        // if the combo box is set to merge sort
        if (algorithmComboBox.getValue().equals("Merge Sort")) {
            // create new thread for merge sort
            new Thread(() -> {
                // create new MergeSort object
                sortingMethod = new MergeSort(this);
                // call merge sort's sort method
                sortingMethod.sort(intArray, 0, (int) arraySizeSlider.getValue() - 1);
                // call updateGraph
                updateGraph(intArray);
            }).start();

            // if the combo box is set to selection sort
        } else if (algorithmComboBox.getValue().equals("Selection Sort")) {
            // create new thread for selection sort
            new Thread(() -> {
                // create new SelectionSort object
                sortingMethod = new SelectionSort(this);
                // call selection sort's sort method
                sortingMethod.sort(intArray, 0, 0);
                // call updateGraph
                updateGraph(intArray);
            }).start();
        }
    }

    // method for reset button
    @FXML
    void onResetClicked(MouseEvent event) {
        // when reset button is clicked, reset the label and slider to 64
        arraySizeLabel.setText("64");
        arraySizeSlider.setValue(64);
        // reset combo box to merge sort
        algorithmComboBox.setValue("Merge Sort");
        // call generateRandomArray to reshuffle the array of 64 integers
        generateRandomArray();
    }

}

