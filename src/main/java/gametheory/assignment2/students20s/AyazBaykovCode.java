/* AyazBaykovCode
 * Version 1.0
 * Done by Ayaz Baykov a.baykov@gmail.com BS18-DS01
 */
package gametheory.assignment2.students20s;

import gametheory.assignment2.Player;

/**
 * This class is an implementation of an actor for Game Theory Assignment 2
 * tournament. It can be initialized with those available strategies {random, greedy, buffer, update_buffer, buffer_greedy}
 * With default constructor buffer_greedy agent will be created.
 * Random - will always chose random next mode
 * Greedy - will always chose field with maximum X
 * Buffer - naive coop strategy where we assume that field A - is "buffer" zone and we distribute other 2 between
 * Update Buffer - same as buffer, but it we met opponent on other field, we assume that this is new buffer
 * Greedy Buffer - at each time says that field with maximum is the "buffer" - this is made to beat greedy opponents
 */
public class AyazBaykovCode implements Player {

    private String strategy;
    private int bufferField; //for naive coop strategies
    private int myField; // for naive coop strategies
    private int curPos; // the current field
    private int fields[] = new int[3]; // the values of each field

    /**
     * Class constructor
     *
     * @param strategy - strategy to play. Default is "buffer_greedy". Others are needed for testing
     */

    public AyazBaykovCode(String strategy) {
        this.strategy = strategy;
        this.bufferField = 1;
        this.myField = this.newOwnField();
        this.curPos = 0;
        this.fields[0] = 1;
        this.fields[1] = 1;
        this.fields[2] = 1;
    }

    public AyazBaykovCode() {
        this("buffer_greedy");
    }

    // Needed for testing
    public String getStrategy() {
        return this.strategy;
    }

    //Resets parameters before next match
    public void reset() {
        this.bufferField = 1;
        this.fields[0] = 1;
        this.fields[1] = 1;
        this.fields[2] = 1;
        this.myField = this.newOwnField();
        this.curPos = 0;
    }

    /**
     * Method returns move based on strategy and opponentLastMove
     *
     * @param opponentLastMove the last move of the opponent
     *                         varies from 0 to 3
     *                         (0 – if this is the first move)
     * @param xA               the argument X for a field A
     * @param xB               the argument X for a field B
     * @param xC               the argument X for a field C
     * @return
     */
    public int move(int opponentLastMove, int xA, int xB, int xC) {

        int result = bufferField; //Cooperative players start at assumed "buffer" zone

        //update our values of fields
        this.fields[0] = xA;
        this.fields[1] = xB;
        this.fields[2] = xC;

        /*
          For greedy buffer strategy we need check each move.
          We make new "buffer" at each step and it is the field with maximum value
          This ruins life for "pure" greedy opponents and still makes quit a lot with coops
         */
        if (this.strategy == "buffer_greedy") this.bufferField = this.maxField();

        /* if we met opponent not in "buffer", then our distribution of other fields was wrong.
           - standart buffer player will still assume that first field is buffer and just re-random his own field
           - update buffer player will now assume that this position is new "buffer" and then re-random his own,
           theoretically update_buffer should show better against greedy opponents
         */
        if ((opponentLastMove != 0) && (this.curPos != this.bufferField) && (this.curPos == opponentLastMove)) {
            if (this.strategy.equals("update_buffer")) this.bufferField = this.curPos;
            this.myField = newOwnField();
        }

        /*
         * We go to our field and collect grass if X is greater than 2 there.
         * Otherwise we go to buffer
         * This increases average of our gains
         */
        if (this.fields[this.myField - 1] >= 2) result = this.myField;
        else result = this.bufferField;

        //random strategy just randoms move
        if (this.strategy.equals("random")) result = (int) (1 + Math.random() * 3);
        //pure greedy just takes max
        if (this.strategy.equals("greedy")) result = this.maxField();

        return result;

    }

    public String getEmail() {
        return "a.baykov@innopolis.university";
    }

    /*
    Utill function. Given buffer zone we random one from 2 others and set it as "mine"
     */
    private int newOwnField() {
        int shift = Math.random() > 0.5 ? 1 : -1;
        int result = this.bufferField + shift;
        if (result == 0) result = 3;
        else if (result == 4) result = 1;
        return result;
    }

    /*
    Utill function to get field with maximum amount of grass
     */
    private int maxField() {
        int maxValue = this.fields[0];
        int maxIndex = 0;
        for (int i = 1; i < 3; i++) {
            if (this.fields[i] > maxValue) {
                maxValue = this.fields[i];
                maxIndex = i;
            }
        }
        return maxIndex + 1;
    }
}

