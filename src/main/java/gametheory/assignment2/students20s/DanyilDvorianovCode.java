package gametheory.assignment2.students20s;

import gametheory.assignment2.Player;

import java.util.Random;

public class DanyilDvorianovCode implements Player {
    private int myLastMove;
    private Random generator;

    public DanyilDvorianovCode() {
        reset();
    }

    public String getEmail() {
        return "d.dvoryanov@innopolis.ru";
    }

    public void reset() {
        myLastMove = 0;
        this.generator = new Random();
    }

    @Override
    public int move(int opponentLastMove, int xA, int xB, int xC) {
        int myMove = getPreviousField(opponentLastMove);
        if (myMove == myLastMove) {
            myMove = getPreviousField(myMove);
        }

        myLastMove = myMove;

        return myMove;
    }

    /**
     * Get previous field of the specified one.
     *
     * @param field the field for which to find the previous one
     * @return a number (1, 2 or 3) which specifes previous field to the given one
     */
    private int getPreviousField(int field) {
        int nextMove = field - 1;
        if (nextMove == -1) {
            // first move
            nextMove = generator.nextInt(3) + 1;
        } else if (nextMove == 0) {
            nextMove = 3;
        }

        return nextMove;
    }
}
