package MorpionMinMax;

import MorpionMinMax.MinMax.MinMax;

import java.util.Scanner;

public class Bot implements Player {
    private final int id;
    private final MinMax minMax;

    public Bot(int id) {
        this.id = id;

        this.minMax = new MinMax();
        this.minMax.train();
    }

    @Override
    public int getPlayerId() {
        return this.id;
    }

    @Override
    public void playMove(Board board, Scanner _scanner) {
        Coordinate prediction = this.minMax.predict(board, this.getPlayerId());

        System.out.println("Bot played: " + prediction);

        board.play(this.getPlayerId(), prediction.x, prediction.y);
    }
}
