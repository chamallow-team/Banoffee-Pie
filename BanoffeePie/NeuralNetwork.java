package BanoffeePie;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This class represents a neural network
 */
public class NeuralNetwork {
    /**
     * The neural network is composed of 6 neurons:
     * 3 input nodes, 2 hidden nodes and 1 output node
     */
    List<Neuron> neurons = Arrays.asList(
            new Neuron(4), new Neuron(4), new Neuron(4), // input nodes
            new Neuron(2), new Neuron(2),                               // hidden nodes network
            new Neuron(2)                                                              // output node
    );
    /**
     * If debug is true, the neural network will print the loss every 10 epochs
     */
    private boolean debug = false;


    public NeuralNetwork(boolean debug){
        this.debug = debug;
    }
    public NeuralNetwork(){}


    /**
     * Predict the output of the neural network
     * <br>
     * The inputs must be a contains the bias in first position and the inputs in the following positions.
     * <br>
     * <h2>Example:</h2>
     *
     * <pre><code>BanoffeePie.NeuralNetwork nn = new BanoffeePie.NeuralNetwork();<br>Double prediction = nn.predict(Arrays.asList(1.0, 25, 46, 32));</code></pre>
     *
     * @param inputs the inputs of the neural network
     * @return the output of the neural network
     *
     * @throws IllegalArgumentException if the number of inputs does not match the number of input nodes
     */
    public Double predict(List<Double> inputs) {
        return neurons.get(5).compute(
                Arrays.asList(
                        neurons.get(4).compute(
                                Arrays.asList(
                                        neurons.get(2).compute(inputs),
                                        neurons.get(1).compute(inputs)
                                )
                        ),
                        neurons.get(3).compute(
                                Arrays.asList(
                                        neurons.get(1).compute(inputs),
                                        neurons.get(0).compute(inputs)
                                )
                        )
                )
        );
    }

    /**
     * Train the neural network with the given data
     * @param data the data to train the neural network
     * @param answers the answers of the data
     *
     * @throws IllegalArgumentException if the number of answers does not match the number of data
     */
    public void train(List<List<Integer>> data, List<Double> answers) {
        Double bestEpochLoss = null;
        for (int epoch = 0; epoch < 1000; epoch++) {
            // adapt neuron
            Neuron epochNeuron = neurons.get(epoch % this.neurons.size());
            epochNeuron.mutate();

            List<Double> predictions = new ArrayList<>();
            for (int i = 0; i < data.size(); i++) {
                Integer red = data.get(i).get(0);
                Integer green = data.get(i).get(1);
                Integer blue = data.get(i).get(2);

                // normalize the data
                double normalizedRed = red / 255.0;
                double normalizedGreen = green / 255.0;
                double normalizedBlue = blue / 255.0;

                List<Double> inputs = Arrays.asList(1.0, normalizedRed, normalizedGreen, normalizedBlue);

                predictions.add(i, this.predict(inputs));
            }


            Double thisEpochLoss = Util.meanSquareLoss(answers, predictions);

            if (debug && epoch % 10 == 0)
                System.out.printf(
                        "Epoch: %s | bestEpochLoss: %.15f | thisEpochLoss: %.15f%n",
                        epoch, bestEpochLoss, thisEpochLoss
                );

            if (bestEpochLoss == null) {
                bestEpochLoss = thisEpochLoss;
                epochNeuron.remember();
            } else {
                if (thisEpochLoss < bestEpochLoss) {
                    bestEpochLoss = thisEpochLoss;
                    epochNeuron.remember();
                } else {
                    epochNeuron.forget();
                }
            }
        }
    }
}
