package gametheory.assignment2.students20s;

import gametheory.assignment2.Player;

import java.util.Random;

public class MaximPopovCode implements Player {
    /**
     * Smart Greedy Player
     */

    private int previousOpponentMove = 0;
    private int maxLast = 0;

    public void reset() {
        previousOpponentMove = 0;
        maxLast = 0;
    }

    public int move(int opponentLastMove, int xA, int xB, int xC) {
        Random rand = new Random();

        if (opponentLastMove == 0) {
            // first move
            int m = Math.max(Math.max(xA, xB), xC);

            if (m == xA) maxLast = 1;
            if (m == xB) maxLast = 2;
            if (m == xC) maxLast = 3;

            return rand.nextInt(3) + 1;
        }

        if (opponentLastMove != maxLast && rand.nextInt(2) == 1) {
            // not greedy, we're gonna play greedy
            previousOpponentMove = opponentLastMove;
            int maxField = Math.max(Math.max(xA, xB), xC);
            if (maxField == xA) {
                maxLast = 1;
                return 1;
            }
            if (maxField == xB) {
                maxLast = 2;
                return 2;
            }
            maxLast = 3;
            return 3;
        }

        if (previousOpponentMove == opponentLastMove) {
            // opponent waits till his field is empty
            int m = Math.max(Math.max(xA, xB), xC);

            if (m == xA) maxLast = 1;
            if (m == xB) maxLast = 2;
            if (m == xC) maxLast = 3;

            if (opponentLastMove == 1 && xA > 0) {
                if (Math.max(xB, xC) == xB) return 2;
                return 3;
            }
            if (opponentLastMove == 2 && xB > 0) {
                if (Math.max(xA, xC) == xA) return 1;
                return 3;
            }
            if (opponentLastMove == 3 && xC > 0) {
                if (Math.max(xA, xB) == xA) return 1;
                return 2;
            }
            return rand.nextInt(3) + 1;
        }

        previousOpponentMove = opponentLastMove;
        return rand.nextInt(3) + 1;
    }

    @Override
    public String toString() {
        return "Smart1";
    }

    public String getEmail() {
        return "ma.popov@innopolis.ru";
    }
}
