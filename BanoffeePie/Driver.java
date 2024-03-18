package BanoffeePie;

import BanoffeePie.csv.ColorCsv;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Driver {

    public static NeuralNetwork nn;
    public static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws FileNotFoundException {
        // Generate data for the neural network
//        List<List<Integer>> data = new ArrayList<>();
//        data.add(Arrays.asList(0, 0, 0));
//        data.add(Arrays.asList(255, 255, 255));
//        data.add(Arrays.asList(115, 66, 200));
//        data.add(Arrays.asList(175, 78, 150));
//        data.add(Arrays.asList(205, 72, 80));
//        data.add(Arrays.asList(120, 67, 220));
        // The answer is 0 if the color tend to black, 1 if the color tend to white
//        List<Double> answers = Arrays.asList(0.0, 1.0, 1.0, 0.0, 0.0, 1.0);

        ColorCsv csv = createData();

        System.out.println("data generated");

        // Create the neural network
        nn = new NeuralNetwork(false);
        System.out.println("Training from csv file...");
        System.out.println("    Starting training...");
        long start = System.currentTimeMillis();

        File csvFile = csv.getFile();
        Scanner scan = new Scanner(csvFile);

        scan.nextLine(); // skip the first line
        List<List<Integer>> colors = new ArrayList<>();
        List<Double> answers = new ArrayList<>();

        int datasCount = 0;

        while (scan.hasNextLine()) {
            datasCount++;

            String[] data = scan.nextLine().split(",");

            List<Integer> color = new ArrayList<>();
            for (int i = 0; i < 3; i++)
                color.add(Integer.parseInt(data[i]));
            colors.add(color);
            answers.add(Double.parseDouble(data[3]));

            if (colors.size() >= 1000) {
                nn.train(colors, answers);
                colors.clear();
                answers.clear();

                System.out.println("        Trained " + datasCount + " data");
            }
        }

        scan.close();

        System.out.println("Trained");
        System.out.println("    Data size: " + datasCount);
        System.out.println("    Time: " + (System.currentTimeMillis() - start) + "ms");

        // Predict the output
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

    public static ColorCsv createData(){
        ColorCsv csv = new ColorCsv("saves/colors.csv");

        try {
            csv.init();
        } catch (IOException e) {
            System.err.println("Cannot read the csv file: " + e);
            e.printStackTrace();
        }

        // write the generated data to the csv file if it doesn't already exist
        if (!csv.getFile().exists() || csv.isEmpty) {
            for (int r = 0; r <= 255; r++) {
                for (int g = 0; g <= 255; g++) {
                    for (int b = 0; b <= 255; b++) {
                        int isWhiter = 0;
                        if (r + g + b > 255 * 3 / 2)
                            isWhiter = 1;

                        csv.addData(r, g, b, isWhiter);
                    }

                    csv.writeNewData();
                    csv.clearData();
                }
            }
        }

        return csv;
    }
}
