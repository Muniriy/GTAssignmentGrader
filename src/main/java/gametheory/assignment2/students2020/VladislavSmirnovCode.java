package gametheory.assignment2.students2020;

import gametheory.assignment2.Player;

import java.util.Random;

public class VladislavSmirnovCode implements Player {
    int cheatsInRow = 0;
    int lastRoundBest = 0; // 0 means either there is no best or it's the first turn
    int myLastRound = 0;
    boolean randomMode = true;

    public VladislavSmirnovCode() {
        // There is no need for this empty constructor, but if this class is
        // put inside some testing tournament, you need this to be public
    }

    @Override
    public void reset() {
        cheatsInRow = 0;
        lastRoundBest = 0;
        myLastRound = 0;
        randomMode = true;
    }

    @Override
    public int move(int opponentLastMove, int xA, int xB, int xC) {
        /* Random mode is on in the beginning of the game to determine who is first to feast */
        if (randomMode) {
            // Determine who is first if did not collide
            if (myLastRound != 0 && opponentLastMove != myLastRound) {
                randomMode = false;
                if (myLastRound == 1) return chooseBest(xA, xB, xC);
                else return chooseMiddle(xA, xB, xC);
            }

            // If collided, try again
            if (new Random().nextInt(4) % 2 == 0) {
                myLastRound = 1;
            } else {
                myLastRound = 3;
            }
            return myLastRound;
        }

        // see if it's the first time they cheated
        if (isCheatedByOpponent(opponentLastMove)) {
            cheatsInRow += 1;
            if (cheatsInRow > 1) {
                return chooseMiddle(xA, xB, xC);
            }
        } else {
            cheatsInRow = 0;
        }

        if (isMyTurnToBest()) {
            return chooseBest(xA, xB, xC);
        }

        return chooseMiddle(xA, xB, xC);
    }

    @Override
    public String getEmail() {
        return "v.smirnov@innopolis.ru";
    }

    private int getBest(int a, int b, int c) {
        return a < b ? (b < c ? 3 : 2) : (a < c ? 3 : 1); // left best
    }

    private int getMiddle(int a, int b, int c) { // left second best, if A=1, B=1, C=1, it will be B
        int best = getBest(a, b, c);
        switch (best) {
            case 1:
                return getBest(-1, b, c);
            case 2:
                return getBest(a, -1, c);
            case 3:
                return getBest(b, c, -1);
        }
        return -1; // shouldn't ever come to this point but if it does, something is wrong
    }

    private boolean isCheatedByOpponent(int opponentLastMove) {
        return lastRoundBest == opponentLastMove && opponentLastMove == myLastRound;
    }

    private boolean isMyTurnToBest() {
        return myLastRound != lastRoundBest;
    }

    private int makeBest(int a, int b, int c) {
        lastRoundBest = getBest(a, b, c);
        return lastRoundBest;
    }

    private int chooseBest(int a, int b, int c) {
        myLastRound = makeBest(a, b, c);
        return myLastRound;
    }

    private int chooseMiddle(int a, int b, int c) {
        myLastRound = getMiddle(a, b, c);
        makeBest(a, b, c);
        return myLastRound;
    }
}
