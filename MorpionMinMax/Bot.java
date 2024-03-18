package MorpionMinMax;

import MorpionMinMax.MinMax.MinMax;

import java.util.Scanner;

public class Bot implements Player {
    private final int id;
    private MinMax minMax;

    public Bot(int id) {
        this.id = id;

        this.minMax = new MinMax();
    }

    @Override
    public int getPlayerId() {
        return this.id;
    }

    @Override
    public void playMove(Board board, Scanner _scanner) {
        Coordinate prediction = this.minMax.predict(board, this.getPlayerId());
    }

}
