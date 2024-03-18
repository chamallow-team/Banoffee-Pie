package MorpionMinMax.MinMax;

import MorpionMinMax.Board;
import MorpionMinMax.Coordinate;

public class MinMax {
    private static final int DEPTH = 4;
    public MinMax(){}

    public Coordinate predict(Board board, int botPlayerId) {
        long start = System.currentTimeMillis();

        System.out.println("Predicting...");

        WeightNode tree = new WeightNode();
        this.buildTree(board, tree, DEPTH, botPlayerId);

        tree.debugPrint();

        System.out.println("Tree built in " + (System.currentTimeMillis() - start) + "ms");

        return new Coordinate(0, 0);
    }

    private void buildTree(Board board, WeightNode root, int depth, int currentPlayer) {
        this.buildTree(board, root, depth, currentPlayer, currentPlayer);
    }

    private void buildTree(Board board, WeightNode root, int depth, int currentPlayer, int botId) {
        if (depth < 1) return;

        // iter
        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 3; y++) {
                if (board.isMoveAllowed(x, y)) {
                    Coordinate m = new Coordinate(x, y);
                    double weight = 0;

                    WeightNode child = new WeightNode(weight, m);

                    Board boardCopy = board.copy();
                    boardCopy.play(currentPlayer, m.x, m.y);


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
                        System.out.println("Win at depth " + (DEPTH - depth) + " for " + currentPlayer + " with move " + m);
                    }

                    root.addNode(child);
                }
            }
        }
    }
}
