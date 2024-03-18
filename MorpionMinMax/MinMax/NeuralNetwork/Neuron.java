package MorpionMinMax.MinMax.NeuralNetwork;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Neuron {

    private final double bias;
    private double output; // Use a more descriptive name
    private final List<NeuronLink> links;

    public Neuron(double bias) {
        this.bias = bias;
        this.links = new ArrayList<>();
        this.output = 0.0; // Initialize output to 0
    }

    public double getBias() {
        return bias;
    }

    public void addNeuronLink(NeuronLink link) {
        this.links.add(link);
    }

    public List<NeuronLink> getLinks() {
        return new ArrayList<>(links); // Return a copy to avoid modification
    }

    public void resetOutput() {
        output = 0.0;
    }

    public double compute(HashMap<String, Neuron> neurons, List<Double> inputs) {
        if (output == 0.0)
            computeOutput(neurons, inputs);

        return output;
    }

    private void computeOutput(HashMap<String, Neuron> neurons, List<Double> inputs) {
        if (this.getLinks().isEmpty()) {
            // we are in an input neuron
            double weightedSum = bias;
            for (int i = 0; i < inputs.size(); i++) {
                weightedSum += inputs.get(i) * links.get(i).getWeight();
            }
            output = Util.sigmoid(weightedSum);
        } else {
            double weightedSum = bias;

            for (NeuronLink link : links) {
                double linkedNeuronOutput = neurons.get(link.getId()).getOutput();
                weightedSum += linkedNeuronOutput * link.getWeight();
            }

            output = Util.sigmoid(weightedSum);
        }
    }

    public double getOutput() {
        return output;
    }
}
