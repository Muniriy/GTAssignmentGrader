package gametheory.assignment2.students20s;

import gametheory.assignment2.Player;

public class RamilAskarovCode implements Player { //simple player with greedy algorithm, which chooses currently best move

    private String mail = "r.askarov@innopolis.ru";

    public void reset() {
    }

    public int move(int opponentLastMove, int xA, int xB, int xC) {
        if (xA >= xB && xA >= xC) {
            return 1;
        } else if (xB >= xA && xB >= xC) {
            return 2;
        } else {
            return 3;
        }
    }

    public String getEmail() {
        return this.mail;
    }
}
