package MorpionMinMax;

import java.util.Scanner;

public interface Player {
    int getPlayerId();
    void playMove(Board board, Scanner scanner);
}
