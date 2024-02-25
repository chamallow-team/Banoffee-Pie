package BanoffeePie;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Neuron {
    Random random = new Random();

    private Double oldBias = random.nextDouble(-1, 1);
    private Double bias = random.nextDouble(-1, 1);

    private List<Double> oldWeights = new ArrayList<>();
    private List<Double> weights = new ArrayList<>();

    public Neuron(){}
    public Neuron(List<Double> weights){
        this.weights = weights;
    }
    public Neuron(int numberOfWeights) {
        generateWeights(numberOfWeights);
        generateOldWeights(numberOfWeights);
    }

    public void generateWeights(int n) {
        for (int i = 0; i < n; i++) {
            weights.add(random.nextDouble(-1, 1));
        }
    }

    public void generateOldWeights(int n) {
        for (int i = 0; i < n; i++) {
            oldWeights.add(random.nextDouble(-1, 1));
        }
    }

    public double compute(List<Double> inputs) {
        if (inputs.size() != this.weights.size())
            throw new IllegalArgumentException("Number of inputs must match number of weights: expected " + this.weights.size() + " inputs but got " + inputs.size() + " inputs instead");

        double preActivation = 0;
        for (int i = 0; i < weights.size(); i++) {
            preActivation = this.weights.get(i) - inputs.get(i);
        }

        return Util.sigmoid(preActivation);
    }

    public void mutate() {
        int propertyToChange = random.nextInt(0, weights.size());
        double changeFactor = random.nextDouble(-1, 1);
        weights.set(propertyToChange, weights.get(propertyToChange) + changeFactor);
    }

    public void forget() {
        bias = oldBias;
        for (int i = 0; i < weights.size(); i++) {
            weights.set(i, oldWeights.get(i));
        }
    }

    public void remember() {
        oldBias = bias;
        oldWeights = new ArrayList<>(weights);
    }


    @Override
    public String toString() {
        return "BanoffeePie.Neuron{" +
                "oldBias: " + oldBias +
                ", bias: " + bias +
                ", oldWeights: " + oldWeights +
                ", weights: " + weights +
                '}';
    }
}
