/*
  Zarubin Iurii,
  BS18-SE-01,
  Game Theory,
  Assignment 2.
  12.03.2021
 */

package gametheory.assignment2.students20s;

import gametheory.assignment2.Player;

import java.util.Random;

/**
 * Class for my Moose game agent that uses "Don't go to the opponent's last move" strategy.
 * Also there is developed greedy approach code, but it is not used.
 */
public class IuriiZarubinCode implements Player {

    /**
     * This method is empty because it is not needed because I have not used any global vars and etc.
     */
    @Override
    public void reset() {
        //  nothing to reset, no global vars used & etc.
    }

    /**
     * I have chosen "Don't go to the opponent's last move" strategy,
     * so my method generates random moves until it is different from opponent's last move.
     * Explanation why I have chosen such algorithm in IuriiZarubinReport.pdf
     *
     * @param opponentLastMove the last move of the opponent varies from 0 to 3.
     *                         (0 – if this is the first move)
     * @param xA               the argument X for a field A;
     * @param xB               the argument X for a field B;
     * @param xC               the argument X for a field C;
     * @return move of the agent based on some calculations;
     */
    @Override
    public int move(int opponentLastMove, int xA, int xB, int xC) {
        Random rand = new Random();

        int move = rand.nextInt(3) + 1;

        while (move == opponentLastMove) {
            move = rand.nextInt(3) + 1;
        }

        return move;
    }

    /**
     * @return my Innopolis e-mail;
     */
    @Override
    public String getEmail() {
        return "y.zarubin@innopolis.university";
    }

    /**
     * Some useless code for testing greedy algorithm
     *
     * @param opponentLastMove the last move of the opponent varies from 0 to 3.
     *                         (0 – if this is the first move)
     * @param xA               the argument X for a field A;
     * @param xB               the argument X for a field B;
     * @param xC               the argument X for a field C;
     * @return move of the agent based on some calculations;
     */
    public int move_useless(int opponentLastMove, int xA, int xB, int xC) {
        int bestValue = findBestFieldValue(xA, xB, xC);
        int worstValue = findWorstFieldValue(xA, xB, xC);

        int move = findFieldIndexByValue(bestValue, xA, xB, xC);

        int bound = 5;

        if (xA >= bound && xB >= bound && xC >= bound) {

            if (xA == xB && xB == xC) {
                Random rand = new Random();

                return rand.nextInt(3) + 1;
            }
            //  choose minimum from this
            return findFieldIndexByValue(worstValue, xA, xB, xC);
        } else if (xA >= bound && xB >= bound) {
            if (xA == xB) {
                Random rand = new Random();

                return rand.nextInt(2) + 1;
            } else if (xA > xB) {
                return 2;
            } else {
                return 1;
            }
        } else if (xB >= bound && xC >= bound) {
            if (xB == xC) {
                Random rand = new Random();

                return rand.nextInt(2) + 2;
            } else if (xB > xC) {
                return 3;
            } else {
                return 2;
            }
        } else if (xA >= bound && xC >= bound) {
            if (xA == xC) {
                Random rand = new Random();
                if (rand.nextInt(2) == 0) {
                    return 1;
                } else {
                    return 3;
                }
            } else if (xA > xC) {
                return 3;
            } else {
                return 1;
            }
        }

        return move;
    }

    /**
     * This method is used for finding field index (1-3) by given value.
     * If there are two or more fields with the same value - returns index of the last field with given value.
     * I assume, that a lot of classmates would use random choosing in such situations or just the first one field.
     *
     * @param value we find fields value's that are equal to the value given;
     * @param xA    the argument X for a field A;
     * @param xB    the argument X for a field B;
     * @param xC    the argument X for a field C;
     * @return index of the last found field with given value;
     */
    private int findFieldIndexByValue(int value, int xA, int xB, int xC) {
        if (value == xC) {
            return 3;
        } else if (value == xB) {
            return 2;
        }

        return 1;
    }

    /**
     * @param xA the argument X for a field A;
     * @param xB the argument X for a field B;
     * @param xC the argument X for a field C;
     * @return maximum value between xA, xB, xC;
     */
    private int findBestFieldValue(int xA, int xB, int xC) {
        return findMax(xA, findMax(xB, xC));
    }

    /**
     * @param xA the argument X for a field A;
     * @param xB the argument X for a field B;
     * @param xC the argument X for a field C;
     * @return minimum value between xA, xB, xC;
     */
    private int findWorstFieldValue(int xA, int xB, int xC) {
        return findMin(xA, findMin(xB, xC));
    }

    /**
     * @param x first value;
     * @param y second value;
     * @return maximum between x and y;
     */
    private int findMax(int x, int y) {
        if (x > y) {
            return x;
        }

        return y;
    }

    /**
     * @param x first value;
     * @param y second value;
     * @return minimum between x and y;
     */
    private int findMin(int x, int y) {
        if (x > y) {
            return y;
        }

        return x;
    }
}
