package MorpionMinMax.MinMax.NeuralNetwork;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Trainer {
    private final NeuralNetwork neuralNetwork;
    private final double learningRate;

    public Trainer(NeuralNetwork neuralNetwork){
        this.neuralNetwork = neuralNetwork;
        this.learningRate = 0;
    }

    public Trainer(NeuralNetwork neuralNetwork, double learningRate){
        this.neuralNetwork = neuralNetwork;
        this.learningRate = learningRate;
    }

    public void train(int iters){
        Random rand = new Random();
        for (int j = 0; j < iters; j++) {
            List<Double> inputs = new ArrayList<>();

            for (int i = 0; i < 9; i++) {
                double randomValue = rand.nextDouble();
                if (randomValue < 0.33) {
                    inputs.add(0.0);
                } else if (randomValue < 0.66) {
                    inputs.add(-1.0);
                } else {
                    inputs.add(1.0);
                }
            }

            double correctOutput = 0;
            for (int i = 0; i < 9; i++) {
                if (inputs.get(i) == 0) {
                    correctOutput = (double) i /9;
                    break;
                }
            }

            for (int i = 0; i < 100; i++) {
                this.trainWorker(inputs, correctOutput);
            }
        }

    }

    private void trainWorker(List<Double> inputs, double correctOutput){
        double computedOutput = this.neuralNetwork.compute(inputs).get(0);

//        System.out.println("Computed: " + computedOutput + " Correct: " + correctOutput);

        this.neuralNetwork.backpropagate(inputs, correctOutput, computedOutput, this.learningRate);
    }
}
