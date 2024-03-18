package MorpionMinMax.MinMax;

import java.util.ArrayList;
import java.util.List;

public class NodePath {
    private final List<WeightNode> nodes;
    private double calculatedWeight = 0;

    public NodePath(){
        this.nodes = new ArrayList<>();
    }

    public void addNode(WeightNode node){
        this.nodes.add(node);
    }

    public List<WeightNode> getNodes() {
        return this.nodes;
    }

    public void calculateWeight(){
        this.calculatedWeight = 0;
        for (WeightNode node: this.nodes) {
            this.calculatedWeight += node.getWeight();
        }
        this.calculatedWeight /= this.nodes.size();
    }

    public double getCalculatedWeight() {
        return this.calculatedWeight;
    }
}
