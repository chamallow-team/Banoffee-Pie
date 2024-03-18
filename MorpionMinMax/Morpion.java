package MorpionMinMax;

import java.util.Scanner;

public class Morpion {
    public final Scanner scanner = new java.util.Scanner(System.in);

    private final Player player1;
    private final Player player2;

    private int currentPlayer = 0;

    private final Board board;

    public Morpion(){
        this.player1 = new Human(1);
        this.player2 = new Bot(2);

        this.board = new Board();
    }

    private void run(){
        while (!board.isFull()) {
            this.board.draw();

            Player player = switch (currentPlayer) {
                case 0 -> this.player1;
                default-> this.player2;
            };

            player.playMove(this.board, this.scanner);

            currentPlayer = currentPlayer == 0 ? 1 : 0;

            int isWin = this.board.isWin();
            if (isWin != 0) {
                this.board.draw();
                System.out.println("Joueur " + isWin + " a gagné!");
                return;
            }
        }
    }

    public static void main(String[] args) {
        Morpion morpion = new Morpion();

        morpion.run();
    }
}
