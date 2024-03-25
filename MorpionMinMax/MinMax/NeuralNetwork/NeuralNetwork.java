package MorpionMinMax.MinMax.NeuralNetwork;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class NeuralNetwork {
    private final HashMap<String, Neuron> neurons;
    private final List<String> outputNeuronsIds;

    public NeuralNetwork(){
        this.neurons = new HashMap<>();
        this.outputNeuronsIds = new ArrayList<>();
    }

    public void addNeuron(String id, Neuron neuron){
        this.neurons.put(id, neuron);
    }
    public void addNeuron(String id, Neuron neuron, boolean isOutput){
        this.neurons.put(id, neuron);
        if (isOutput)
            this.outputNeuronsIds.add(id);
    }

    public void addNeuronLink(String from, String to, double weight){
        this.neurons.get(from).addNeuronLink(new NeuronLink(to, weight));
    }

    public List<Double> compute(List<Double> inputs){
        List<Double> outputs = new ArrayList<>();

        // on prend les neurones de sorties et on lance la méthode de calcul
        for (String neuronId: this.outputNeuronsIds)
            outputs.add(this.neurons.get(neuronId).compute(this.neurons, inputs));

        // À la fin, on réinitialise les neurones
        for (Neuron n: this.neurons.values())
            n.resetOutput();

        return outputs;
    }

    public void backpropagate(List<Double> inputs, double correctOutput, double computedOutput, double learningRate) {
        // Définir une liste pour stocker les gradients pour chaque neurone
        ArrayList<Double> gradients = new ArrayList<>(Collections.nCopies(neurons.size(), 0.0));

        // Calculer la dérivée de la perte par rapport à la sortie du neurone
        double deltaOutput = (computedOutput - correctOutput) * Util.sigmoidDerivative(computedOutput);
        gradients.set(neurons.size() - 1, deltaOutput);

        List<Neuron> neurons = this.neurons.values().stream().toList();

        // Itérer en sens inverse à travers les couches
        for (int i = neurons.size() - 1; i >= 0; i--) {
            double deltaWeight = 0.0;
            Neuron currentNeuron = neurons.get(i);

            // Calculer la dérivée de la perte par rapport au poids de chaque lien
            for (NeuronLink link : currentNeuron.getLinks()) {
                deltaWeight += deltaOutput * link.getWeight();

                // Stocker le gradient pour chaque lien
                gradients.set(i, deltaWeight);

                // Mettre à jour le gradient de la sortie du neurone lié
                String linkedNeuronId = link.getId();

                if (this.neurons.get(linkedNeuronId) == null)
                    continue;

                double linkedNeuronOutput = this.neurons.get(linkedNeuronId).getOutput();
                double deltaLinkedNeuronOutput = deltaWeight * linkedNeuronOutput * (1 - linkedNeuronOutput);
                gradients.set(neurons.size() - 1, deltaLinkedNeuronOutput);

                // add to gradients
                gradients.set(neurons.indexOf(this.neurons.get(linkedNeuronId)), deltaLinkedNeuronOutput);
            }

            // Mettre à jour la dérivée de la perte par rapport au biais du neurone
            deltaOutput = deltaWeight * currentNeuron.getBias() * (1 - currentNeuron.getBias());
        }

        // Mettre à jour les poids en utilisant les gradients et le taux d'apprentissage
        updateWeights(gradients, learningRate);
    }

    private void updateWeights(List<Double> gradients, double learningRate) {
        for (int i = 0; i < neurons.size() - 1; i++) {
            Neuron neuron = neurons.get(i);

            if (neuron == null)
                continue;

            double gradient = gradients.get(i);

            // Mettre à jour le biais du neurone
            neuron.setBias(neuron.getBias() - learningRate * gradient);

            // Mettre à jour les poids des liens du neurone
            for (NeuronLink link : neuron.getLinks())
                link.setWeight(link.getWeight() - learningRate * gradient * neuron.getOutput());
        }
    }

    public void printDebug(){
        for (Neuron n: this.neurons.values())
            n.printDebug();
    }
}
