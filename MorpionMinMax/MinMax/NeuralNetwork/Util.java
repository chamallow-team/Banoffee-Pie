package MorpionMinMax.MinMax.NeuralNetwork;

import java.util.List;

public class Util {
    public static double sigmoid(double in){
        return 1 / (1 + Math.exp(-in));
    }

    public static Double meanSquareLoss(List<Double> correctAnswers,   List<Double> predictedAnswers){
        double sumSquare = 0;
        for (int i = 0; i < correctAnswers.size(); i++){
            double error = correctAnswers.get(i) - predictedAnswers.get(i);
            sumSquare += (error * error);
        }
        return sumSquare / (correctAnswers.size());
    }

    public double calculateLoss(double computedOutput, double correctOutput) {
        // Utilisation de la fonction Mean Squared Error (MSE)
        return Math.pow(computedOutput - correctOutput, 2) / 2.0;
    }

    public static double sigmoidDerivative(double x) {
        double exp = Math.exp(-x);
        return exp / ((1 + exp) * (1 + exp));
    }
}