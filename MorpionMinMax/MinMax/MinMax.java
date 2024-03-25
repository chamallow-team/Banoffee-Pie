package MorpionMinMax.MinMax;

import MorpionMinMax.Board;
import MorpionMinMax.Coordinate;
import MorpionMinMax.MinMax.NeuralNetwork.NeuralNetwork;
import MorpionMinMax.MinMax.NeuralNetwork.Neuron;
import MorpionMinMax.MinMax.NeuralNetwork.Trainer;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Stack;

public class MinMax {
    private NeuralNetwork nn;
    private static final int DEPTH = 5;
    private static final double LEARNING_RATE = 0.1;
    public MinMax(){
        this.initNeuralNetwork();
    }

    public void train(){
        Trainer trainer = new Trainer(this.nn, LEARNING_RATE);
        trainer.train(10000);
    }

    private void initNeuralNetwork(){
        this.nn = new NeuralNetwork();
        Random rand = new Random();

        // On a 9 neurones d'entrée
        // 1 neurone de sortie
        // -1 quand c'est l'ennemi, 1 quand c'est nous
        // On a 11 neurones cachés dans le layer 1
        // et 5 dans le layer 2
        // on a un neurone de sortie où on attend i/9, qui représente la case où on va jouer

        for (int i = 0; i < 9; i++)
            this.nn.addNeuron("input" + i, new Neuron(rand.nextDouble(-1, 1)));

        this.nn.addNeuron("output", new Neuron(rand.nextDouble(-1, 1)), true);

        for(int i = 0; i < 11; i++)
            this.nn.addNeuron("hidden1_" + i, new Neuron(rand.nextDouble(-1, 1)));

        for(int i = 0; i < 5; i++)
            this.nn.addNeuron("hidden2_" + i, new Neuron(rand.nextDouble(-1, 1)));

        // create links

        // output to second hidden layer
        for (int i = 0; i < 5; i++)
            this.nn.addNeuronLink("output", "hidden2_" + i, rand.nextDouble(-1, 1));

        // second hidden layer to first hidden layer
        for (int i = 0; i < 5; i++) {
            String id = "hidden2_" + i;
            for (int j = 0; j < 3; j++)
                this.nn.addNeuronLink(id, "hidden1_" + (i + j), rand.nextDouble(-1, 1));
        }

        // first hidden layer to input layer
        for (int i = 0; i < 11; i++) {
            String id = "hidden1_" + i;
            for (int j = 0; j < 3; j++)
                this.nn.addNeuronLink(id, "input" + (i + j), rand.nextDouble(-1, 1));
        }
    }

    public Coordinate predict(Board board, int botPlayerId) {
        long start = System.currentTimeMillis();

        System.out.println("Predicting...");

        WeightNode tree = new WeightNode();
        this.buildTree(board, tree, DEPTH, botPlayerId);

//        tree.debugPrint();

        System.out.println("Tree built in " + (System.currentTimeMillis() - start) + "ms");
        start = System.currentTimeMillis();

        List<NodePath> possiblePaths = new ArrayList<>();
        this.calculatePossiblePaths(tree, possiblePaths);
        possiblePaths.forEach(NodePath::calculateWeight);

        System.out.println(possiblePaths.size() + " possible paths calculated in " + (System.currentTimeMillis() - start) + "ms");

        NodePath bestPath = this.getBestPath(possiblePaths);

        System.out.println("Best path weight: " + bestPath.getCalculatedWeight() + " with " + bestPath.getNodes().size() + " nodes");

        return bestPath.getNodes().get(1).getMove();
    }

    private void buildTree(Board board, WeightNode root, int depth, int currentPlayer) {
        this.buildTree(board, root, depth, currentPlayer, currentPlayer);
    }

    private double predict(Board board, int currentPlayer, int botId){
        List<Double> inputs = new ArrayList<>();
        int[][] boardCases = board.getBoard();

        for (int[] row: boardCases) {
            for (int col: row) {
                inputs.add((double) (col == 0 ? 0 : (col == botId ? 1 : -1)));
            }
        }

        List<Double> predicted = this.nn.compute(inputs);
        return predicted.get(0);
    }

    private void buildTree(Board board, WeightNode root, int depth, int currentPlayer, int botId) {
        if (depth < 1) return;

        // iter
        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 3; y++) {
                if (board.isMoveAllowed(x, y)) {
                    Coordinate m = new Coordinate(x, y);

                    Board boardCopy = board.copy();
                    boardCopy.play(currentPlayer, m.x, m.y);

                    double weight = this.predict(board, currentPlayer, botId);
                    // On applique la simplification "négamax"
                    if (currentPlayer != botId)
                        weight = -weight;

                    WeightNode child = new WeightNode(weight, m);

                    if (boardCopy.isWin() != botId) {
                        buildTree(
                                boardCopy,
                                child,
                                depth - 1,
                                currentPlayer == 2 ? 1 : 2,
                                botId
                        );
                    } else {
                        // if we win, we don't need to iter more in depth
                        child.setWin(true);
                    }

                    root.addNode(child);
                }
            }
        }
    }

    public void calculatePossiblePaths(WeightNode root, List<NodePath> nodesPaths) {
        Stack<WeightNode> currentPath = new Stack<>();

        calculatePossiblePathsHelper(root, currentPath, nodesPaths);
    }

    private void calculatePossiblePathsHelper(WeightNode node, Stack<WeightNode> currentPath, List<NodePath> nodesPaths) {
        currentPath.push(node);

        if (node.getAllChildren().isEmpty()) {
            NodePath path = new NodePath();
            path.getNodes().addAll(currentPath);
            nodesPaths.add(path);
        } else {
            for (WeightNode child : node.getAllChildren().values()) {
                calculatePossiblePathsHelper(child, currentPath, nodesPaths);
            }
        }

        currentPath.pop();
    }

    /**
     * Get the best path from a list of paths
     * The weights must be previously calculated
     *
     * @param paths List of paths
     * @return The best path
     */
    private NodePath getBestPath(List<NodePath> paths) {
        NodePath bestPath = paths.get(0);
        for (NodePath path : paths) {
            if (path.getCalculatedWeight() > bestPath.getCalculatedWeight()) {
                bestPath = path;
            }
        }
        return bestPath;
    }
}
