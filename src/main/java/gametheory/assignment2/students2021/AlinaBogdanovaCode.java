/*
 * AlinaBogdanova
 * Random strategy implementation
 * Created: 03.03.2021
 * Last update: 12.03.2021 */

package gametheory.assignment2.students2021;

import gametheory.assignment2.Player;

import java.util.Random;

public class AlinaBogdanovaCode implements Player {
    /* Other player's stratagy detetction */
    private final int MAX_THRESHOLD = 14;   // theshold to define the opponent 
    // as max player

    private int opponentMaxCounter;         // counter of the steps, which your
    // oponent made sequentially, and
    // they were max

    private int lastMaxMove;                // index of the cell, which has max
    // value during the previous move

    private int[] previousField = {0, 0, 0};// state of the fileds
    // during the previous move
    /* Variables for cooperation */
    private int WAIT_CYCLES;                // the number of moves
    // to the wait-cell

    private int counter;                    // counter of cycles 

    private int myLastMove;                 // used at the beginning of the 
    // cooperation to define your cell

    private int myField;                    // index of my filed
    private int waitField;    // index of shared filed


    /**
     * State of the player
     * 0 - wait if cooperation
     * 1 - cooperation
     * 2 - max
     * 3 - ramdom
     */
    private int state;

    @Override
    public void reset() {
        this.state = 0;
        this.myLastMove = -1;
        this.counter = 0;
        this.opponentMaxCounter = 0;
        this.WAIT_CYCLES = 5;
        this.myField = 3;
        this.waitField = 2;
        this.lastMaxMove = -2;
        this.previousField[0] = 0;
        this.previousField[1] = 0;
        this.previousField[2] = 0;
    }

    /**
     * Cooperation starts, when your previous move is not the
     * same, as your opponent's one.
     *
     * @param opponentLastMove the last move of the opponent
     *                         varies from 0 to 3
     *                         (0 – if this is the first move)
     * @return index of the shared filed
     */
    private int getWaitField(int opponentLastMove) {
        for (int i = 1; i < 4; i++) {
            if (i != opponentLastMove && i != this.myLastMove) {
                return i;
            }
        }

        /* should never happen */
        return 0;
    }

    /**
     * Generated new value for WAIT_CYCLES variable as side effect
     */
    private void resetWaitCycles() {
        Random r = new Random();

        this.counter = 0;
        this.WAIT_CYCLES = r.nextInt(10) + 20;
    }

    /**
     * Returns coopretive move is the cooperation strats,
     * returns random move, if the cooperation haven't started yet
     *
     * @param opponentLastMove the last move of the opponent
     *                         varies from 0 to 3
     *                         (0 – if this is the first move)
     * @param xA               the argument X for a field A
     * @param xB               the argument X for a field B
     * @param xC               the argument X for a field C
     * @return the move of the player can be 1 for A, 2 for B
     * and 3 for C fields
     */
    private int cooperationWait(int opponentLastMove, int xA, int xB, int xC) {
        if (myLastMove != opponentLastMove) {       /* set up the cooperation values */
            this.state = 1;
            this.myField = myLastMove;
            this.waitField = getWaitField(opponentLastMove);

            /* set the threshold */
            resetWaitCycles();

            /* start cooperation */
            return cooperativeMove(opponentLastMove, xA, xB, xC);
        } else {                                    /* wait for cooperation */
            return randomMove(opponentLastMove, xA, xB, xC, false);
        }
    }

    /**
     * Move, which is either on your own cell, or to the shared filed.
     * In the wrost case, player stops trust to the opponent and
     * runs max strategy.
     *
     * @param opponentLastMove the last move of the opponent
     *                         varies from 0 to 3
     *                         (0 – if this is the first move)
     * @param xA               the argument X for a field A
     * @param xB               the argument X for a field B
     * @param xC               the argument X for a field C
     * @return the move of the player can be 1 for A, 2 for B
     * and 3 for C fields
     */
    private int cooperativeMove(int opponentLastMove, int xA, int xB, int xC) {
        if (opponentLastMove == this.myField) {         /* stop trust */
            this.state = 2;
            return maxMove(opponentLastMove, xA, xB, xC);
        }
        this.counter++;
        if (this.counter < WAIT_CYCLES) {               /* wait */
            this.myLastMove = this.waitField;
            return this.myLastMove;

        } else if (this.counter < 2 * WAIT_CYCLES) {    /* eat */
            this.myLastMove = this.myField;
            return this.myLastMove;

        } else { /* reset counter and threshold */
            resetWaitCycles();
            return cooperativeMove(opponentLastMove, xA, xB, xC);
        }
    }

    /**
     * Returns index of the max from parameters
     *
     * @param a the argument X for a field A
     * @param b the argument X for a field B
     * @param c the argument X for a field C
     * @return 0 if the max is a
     * 1 if the max is b
     * 2 if the max is c
     */
    private int getIndexMaxOf3(int a, int b, int c) {
        int[] field = {a, b, c};                        // filed simulation
        int max = a;                                    // current max value

        /* find the value of max*/
        max = max > b ? max : b;
        max = max > c ? max : c;

        /* return index */
        for (int i = 0; i < 3; i++) {
            if (field[i] == max) {
                return i;
            }
        }

        /* should never happen */
        return 0;
    }

    /**
     * Atempt to find out the startagy of the opponent
     *
     * @param opponentLastMove the last move of the opponent
     *                         varies from 0 to 3
     *                         (0 – if this is the first move)
     * @param xA               the argument X for a field A
     * @param xB               the argument X for a field B
     * @param xC               the argument X for a field C
     */
    private void checkOpponentMax(int opponentLastMove, int xA, int xB, int xC) {

        if (this.lastMaxMove == -2) {                       /* if the first update */
            this.lastMaxMove = getIndexMaxOf3(xA, xB, xC);
            return;
        }

        /* compare the value of the opponnent step with max */
        if (this.previousField[this.lastMaxMove]
                == this.previousField[opponentLastMove - 1]) {
            this.opponentMaxCounter++;
        } else {
            this.opponentMaxCounter = 0;
        }

        /* compute the max for this step */
        this.lastMaxMove = getIndexMaxOf3(xA, xB, xC);
        /* update the previous state */
        this.previousField[0] = xA;
        this.previousField[1] = xB;
        this.previousField[2] = xC;
    }

    /**
     * Returns the index, where the value is max from A, B, C
     *
     * @param opponentLastMove the last move of the opponent
     *                         varies from 0 to 3
     *                         (0 – if this is the first move)
     * @param xA               the argument X for a field A
     * @param xB               the argument X for a field B
     * @param xC               the argument X for a field C
     * @return the move of the player can be 1 for A, 2 for B
     * and 3 for C fields
     */
    private int maxMove(int opponentLastMove, int xA, int xB, int xC) {

        /* check if opponent is max stratagy */
        checkOpponentMax(opponentLastMove, xA, xB, xC);

        /* if the max stratagy of the opponent counter > 14 - do random */
        if (this.opponentMaxCounter > this.MAX_THRESHOLD) {
            this.state = 3;
            return randomMove(opponentLastMove, xA, xB, xC, false);
        }
        this.myLastMove = getIndexMaxOf3(xA, xB, xC) + 1;
        return this.myLastMove;

    }

    /**
     * Returns random move
     *
     * @param opponentLastMove the last move of the opponent
     *                         varies from 0 to 3
     *                         (0 – if this is the first move)
     * @param xA               the argument X for a field A
     * @param xB               the argument X for a field B
     * @param xC               the argument X for a field C
     * @return the move of the player can be 1 for A, 2 for B
     * and 3 for C fields
     */
    private int randomMove(int opponentLastMove, int xA, int xB, int xC, boolean maxCheck) {
        Random r = new Random();

        if (maxCheck) {                         /* check if opponent is max stratagy */
            checkOpponentMax(opponentLastMove, xA, xB, xC);

            if (this.opponentMaxCounter == 0) { /* to win the "chicken", max stratagy */
                this.state = 2;
                return maxMove(opponentLastMove, xA, xB, xC);
            }
        }

        /* random move */
        this.myLastMove = r.nextInt(3) + 1;
        return this.myLastMove;
    }

    @Override
    public int move(int opponentLastMove, int xA, int xB, int xC) {
        if (opponentLastMove == 0) {
            return randomMove(opponentLastMove, xA, xB, xC, false);
        }
        switch (state) {
            case 0:
                return cooperationWait(opponentLastMove, xA, xB, xC);
            case 1:
                return cooperativeMove(opponentLastMove, xA, xB, xC);
            case 2:
                return maxMove(opponentLastMove, xA, xB, xC);
            default:
                return randomMove(opponentLastMove, xA, xB, xC, true);
        }
    }

    @Override
    public String getEmail() {
        return "a.bogdanova@innopolis.university";
    }
}
