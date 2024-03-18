package MorpionMinMax.MinMax.NeuralNetwork;

import java.util.ArrayList;
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
}
