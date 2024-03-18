package MorpionMinMax;

import java.util.Arrays;
import java.util.Objects;

public class Board {
    private int[][] board = new int[][]{
            {0, 0, 0},
            {0, 0, 0},
            {0, 0, 0}
    };

    public Board(){}

    public int[][] getBoard() {
        return board;
    }

    public void play(int playerId, char x, int y) {
        int rowIndex = x - 'A';
        board[rowIndex][y] = playerId;
    }

    public void play(int playerId, int x, int y) {
        board[x][y] = playerId;
    }


    public boolean isFull(){
        for (int[] col: board) {
            for (int r: col) {
                if (r == 0) return false;
            }
        }
        return true;
    }

    public boolean isMoveAllowed(char x, int y){
        if (y < 0 || y > 3)
            return false;

        return switch (x) {
            case 'A' -> board[0][y] == 0;
            case 'B' -> board[1][y] == 0;
            case 'C' -> board[2][y] == 0;
            default -> false;
        };
    }

    public boolean isMoveAllowed(int x, int y){
        if ((y < 0 || y > 3) || (x < 0 || x > 3))
            return false;

        return board[x][y] == 0;
    }

    public int isWin(){
        // return 0 if no one wins, 1 if player 1 wins, 2 if player 2 wins
        for (int[] i: this.board) {
            if (Arrays.equals(i, new int[]{1, 1, 1}) || Arrays.equals(i, new int[]{2, 2, 2}))
                return i[0];
        }

        for (int i = 0; i < 3; i++) {
            if (this.board[0][i] != 0 && this.board[0][i] == this.board[1][i] && this.board[1][i] == this.board[2][i])
                return this.board[0][i];
        }

        return 0;
    }

    public void draw() {
        int[][] board = this.getBoard();
        System.out.println("  A B C");
        for (int i = 0; i < 3; i++) {
            System.out.print(i + " ");
            for (int j = 0; j < 3; j++) {
                char c = switch (board[i][j]) {
                    case 1 -> 'x';
                    case 2 -> 'o';
                    default -> ' ';
                };

                System.out.print(c + " ");
            }
            System.out.println();
        }
    }

    public Board copy(){
        Board newBoard = new Board();
        newBoard.board = Arrays.stream(this.board).map(int[]::clone).toArray(int[][]::new);
        return newBoard;
    }
}
