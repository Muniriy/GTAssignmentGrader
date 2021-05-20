package gametheory.assignment2.students2021;

import gametheory.assignment2.Player;

import java.util.*;


public class ArtemBakhanovCode implements Player {
    // turns for Identification process
    private final int WAIT = 3;
    private final int FIELD_WAIT = 5;

    // 0 - Identification; 1 - Cooperation; 2 - Default strategy
    private int mode = 0;

    // the field the agent will use
    private int myField = -1;
    private int commonField = -1;
    private int moves = 0;
    private int myLastMove = -1;

    /**
     * Default constructor. Initialize and reset.
     */
    public ArtemBakhanovCode() {
        this.reset();
    }

    /**
     * Reset agent in order to play with another opponent.
     * Only number moves is preserved.
     */
    @Override
    public void reset() {
        this.mode = 0;
        this.myField = -1;
        this.commonField = -1;
        this.moves = 0;
        this.myLastMove = -1;
    }

    /**
     * Make a move.
     *
     * @param opponentLastMove the last move of the opponent
     *                         varies from 0 to 3
     *                         (0 – if this is the first move)
     * @param xA               the argument X for a field A
     * @param xB               the argument X for a field B
     * @param xC               the argument X for a field C
     * @return
     */
    @Override
    public int move(int opponentLastMove, int xA, int xB, int xC) {
        if (opponentLastMove == 0) {
            // first move
            // select just random move
            this.myLastMove = this.randomMove();
            return this.myLastMove;
        }

        this.moves += 1;

        if (this.mode == 0) {
            // Identification
            if (opponentLastMove != this.myLastMove) {
                // Start coop
                this.mode = 1;
                this.myField = this.myLastMove;
                this.commonField = 6 - this.myLastMove - opponentLastMove;
                this.myLastMove = this.coopStrategy(xA, xB, xC);
            } else if (this.moves > this.WAIT) {
                // stop Identification
                this.mode = 2;
                this.myLastMove = this.defaultStrategy(opponentLastMove, xA, xB, xC);
            } else {
                this.myLastMove = this.randomMove();
            }
        } else if (this.mode == 1) {
            // if we already in coop mode
            if (opponentLastMove == this.myField && opponentLastMove != this.commonField) {
                // rules violation! stop cooperating
                this.mode = 2;
                this.myLastMove = this.defaultStrategy(opponentLastMove, xA, xB, xC);
            } else {
                this.myLastMove = this.coopStrategy(xA, xB, xC);
            }
        } else {
            // we play default strategy
            this.myLastMove = this.defaultStrategy(opponentLastMove, xA, xB, xC);
        }


        return this.myLastMove;
    }

    /**
     * @return email of the student
     */
    @Override
    public String getEmail() {
        return "a.bahanov@innopolis.university";
    }

    private int defaultStrategy(int opponentLastMove, int xA, int xB, int xC) {
        // greedy strategy
        return this.argmax(xA, xB, xC);
    }

    private int coopStrategy(int xA, int xB, int xC) {
        int[] fields = new int[]{xA, xB, xC};
        if (fields[this.myField - 1] > this.FIELD_WAIT) {
            return this.myField;
        } else {
            return this.commonField;
        }
    }

    /**
     * Makes a random move (1, 2 or 3)
     *
     * @return random move from 1 to 3
     */
    private int randomMove() {
        Random random = new Random();
        return random.nextInt(3) + 1;
    }


    /**
     * @param x first param
     * @param y second param
     * @param z third param
     * @return maximum among parameters
     */
    private int max(int x, int y, int z) {
        Integer[] arr = new Integer[]{x, y, z};
        Arrays.sort(arr, Collections.reverseOrder());

        return arr[0];
    }

    /**
     * @param x first param
     * @param y second param
     * @param z third param
     * @return index of maximum param (1..3); if there are several maxima, return random
     */
    public int argmax(int x, int y, int z) {
        List<Integer> maxes = new ArrayList<>(3);
        int max = this.max(x, y, z);
        if (max == x) maxes.add(1);
        if (max == y) maxes.add(2);
        if (max == z) maxes.add(3);

        Random random = new Random();

        return maxes.get(random.nextInt(maxes.size()));
    }
}
