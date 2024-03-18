package MorpionMinMax.MinMax;

import MorpionMinMax.Coordinate;

import java.util.HashMap;
import java.util.Map;

public class WeightNode {
    private double weight;
    private Coordinate move;
    private boolean isWin = false;
    private final HashMap<Integer, WeightNode> children = new HashMap<>();

    public WeightNode(double weight, Coordinate move) {
        this.weight = weight;
        this.move = move;
    }
    public WeightNode(double weight) {
        this.weight = weight;
    }

    public WeightNode() {}
    public void setWin(boolean isWin) {
        this.isWin = isWin;
    }

    public double getWeight(){
        return this.weight;
    }

    public Coordinate getMove(){
        return this.move;
    }

    public void addNode(int id, WeightNode node) {
        this.children.put(id, node);
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public void addNode(WeightNode node) {
        this.children.put(this.children.size(), node);
    }

    public void debugPrint(int depth){
        String prefix = "  ".repeat(Math.max(0, depth));

        System.out.print("Node (" + this.weight + ", " + this.move + (isWin ? ", W" : "") + ") {");
        if (this.children.isEmpty()) {
            System.out.println("}");
        } else {
            System.out.println();
            for (Map.Entry<Integer, WeightNode> node: this.children.entrySet()) {
                System.out.print(prefix + "  " + node.getKey() +  ": ");
                node.getValue().debugPrint(depth + 1);
            }
            System.out.println(prefix + "}");
        }
    }

    public void debugPrint(){
        this.debugPrint(0);
    }
}
