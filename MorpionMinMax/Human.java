package MorpionMinMax;

import java.util.Scanner;

public class Human implements Player {
    private final int id;

    public Human(int id) {
        this.id = id;
    }

    @Override
    public int getPlayerId() {
        return this.id;
    }

    @Override
    public void playMove(Board board, Scanner scanner) {
        while (true) {
            System.out.print("Joueur " + this.getPlayerId() + ", votre coup: ");

            String l = scanner.nextLine().trim().toUpperCase();
            char x = l.charAt(0);
            int y = Integer.parseInt(String.valueOf(l.charAt(1)));
            if (board.isMoveAllowed(x, y)) {
                board.play(this.getPlayerId(), x, y);
                break;
            } else {
                System.out.println("\u001b[31mVotre coup n'est pas valide.\u001b[0m");
            }
        }
    }
}
