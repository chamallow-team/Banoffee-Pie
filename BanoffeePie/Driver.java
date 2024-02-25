package BanoffeePie;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Driver {

    public static NeuralNetwork nn;
    public static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        // Generate data for the neural network
        List<List<Integer>> data = new ArrayList<>();
        data.add(Arrays.asList(0, 0, 0));
        data.add(Arrays.asList(255, 255, 255));
        data.add(Arrays.asList(115, 66, 200));
        data.add(Arrays.asList(175, 78, 150));
        data.add(Arrays.asList(205, 72, 80));
        data.add(Arrays.asList(120, 67, 220));
        // The answer is 0 if the color tend to black, 1 if the color tend to white
        List<Double> answers = Arrays.asList(0.0, 1.0, 1.0, 0.0, 0.0, 1.0);

        System.out.println("data generated");

        // Create the neural network
        nn = new NeuralNetwork(false);
        System.out.println("Training...");
        nn.train(data, answers);
        System.out.println("Trained");

        // Predict the output
//        Double prediction = nn.predict(Arrays.asList(1.0, red, green, blue)); // the 1.0 is the bias, it's very important
//        System.out.println("Want to predict: " + red + " " + green + " " + blue);
//        System.out.println("Prediction if this color tend to a white color: " + prediction);

        predict();
    }

    public static void predict(){
        System.out.print("Enter the red value: ");
        double red = scanner.nextDouble();
        System.out.print("Enter the green value: ");
        double green = scanner.nextDouble();
        System.out.print("Enter the blue value: ");
        double blue = scanner.nextDouble();

        System.out.println();

        // Predict the output
        Double prediction = nn.predict(Arrays.asList(1.0, red, green, blue)); // the 1.0 is the bias, it's very important
        System.out.println("Rgb code: rgb(" + red + ", " + green + ", " + blue + ")");
        System.out.println("Prediction if this color tend to a white color: " + prediction);
    }
}
