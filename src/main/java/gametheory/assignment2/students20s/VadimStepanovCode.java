package gametheory.assignment2.students20s;

import gametheory.assignment2.Player;

import java.util.concurrent.ThreadLocalRandom;

public class VadimStepanovCode implements Player {
    private int SWITCH_TRESHOLD = 6;
    private int myLastMove = 0;
    private int baseCell = 0;
    private int waitingCell = 0;
    private boolean punishing = false;

    public String getEmail() {
        return "v.stepanov@innopolis.university";
    }

    public int move(int opponentLastMove, int xA, int xB, int xC) {
        if (baseCell == 0) {
            if (myLastMove != opponentLastMove && opponentLastMove != 0) {
                baseCell = myLastMove;
                waitingCell = 6 - myLastMove - opponentLastMove;
                return waitingCell;
            }

            int rand = ThreadLocalRandom.current().nextInt(1, 4);
            myLastMove = rand;
            return rand;
        } else {
            if (opponentLastMove == baseCell) {
                punishing = true;
            }

            if (punishing) {
                if (computeScore(xA) >= computeScore(xB) && computeScore(xA) >= computeScore(xC)) {
                    return 1;
                } else if (computeScore(xB) >= computeScore(xA) && computeScore(xB) >= computeScore(xC)) {
                    return 2;
                } else return 3;
            }

            int[] xs = {xA, xB, xC};
            if (xs[baseCell - 1] >= SWITCH_TRESHOLD) {
                return baseCell;
            } else return waitingCell;
        }
    }

    public void reset() {
        myLastMove = 0;
        baseCell = 0;
        waitingCell = 0;
        punishing = false;
    }

    double computeFood(int x) {
        return (10 * Math.exp(x)) / (1 + Math.exp(x));
    }

    double computeScore(int x) {
        return computeFood(x) - computeFood(0);
    }
}
