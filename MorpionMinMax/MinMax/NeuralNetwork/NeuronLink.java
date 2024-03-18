package MorpionMinMax.MinMax.NeuralNetwork;

public class NeuronLink {
    private final String id;
    private double weight;

    public NeuronLink(String id, double weight){
        this.id = id;
        this.weight = weight;
    }

    public String getId() {
        return id;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }
}
