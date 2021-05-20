/*
 * KamilKhairullinCode
 *
 * Kamil Khairullin BS18-SE-01
 */
package gametheory.assignment2.students20s;

import gametheory.assignment2.Player;

import java.util.Arrays;
import java.util.Random;


public class KamilKhairullinCode implements Player {

    Random random = new Random();
    private int patienceValue = 7;
    private int patience = patienceValue;
    private int lastMove;
    private Boolean isAgressive = true;
    private int lastA = 1, lastB = 1, lastC = 1;

    /**
     * This method returns number of vegetation on the field
     *
     * @param X the argument X
     * @return double   How much grass with this argument
     */
    private double gainOverallGross(int X) {
        return 10 * Math.exp(X) / (1 + Math.exp(X));
    }

    /**
     * This method returns number of vegetation moose can eat in one round
     *
     * @param X the argument X
     * @return double   How much grass will we obtain within one round
     */
    private double gainThisRoundGross(int X) {
        return gainOverallGross(X) - gainOverallGross(0);
    }

    /**
     * This method is called to reset the agent before the match
     * with another player containing several rounds
     */

    public void reset() {
        patience = patienceValue;
        isAgressive = true;
        lastA = 1;
        lastB = 1;
        lastC = 1;
    }

    ;

    /**
     * This method returns the move of the player based on
     * the last move of the opponent and X values of all fields.
     * Initially, X for all fields is equal to 1 and last opponent
     * move is equal to 0
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
    public int move(int opponentLastMove, int xA, int xB, int xC) {
        if (checkIfToChangeStrategy(opponentLastMove)) {
            isAgressive = !isAgressive;
        }
        // Save previous state
        lastA = xA;
        lastB = xB;
        lastC = xC;

        //Activate agressive mode
        if (isAgressive) {
            lastMove = bestMove(xA, xB, xC);
        }
        //Activate passive mode
        else {
            lastMove = secondBestMove(xA, xB, xC);
        }
        return lastMove;
    }

    ;


    /**
     * This method analyzes previous move of opponent and decides if to
     * decrease patience or not. If patience hits 0, returns true to change
     * strategy to opposite. Else returns false.
     *
     * @param opponentLastMove the last move of the opponent
     *                         varies from 0 to 3
     *                         (0 – if this is the first move)
     * @return Boolean              To switch strategy or not.
     */
    private Boolean checkIfToChangeStrategy(int opponentLastMove) {
        if (isAgressive) {
            // If you both doing aggressive strategy, decrease patience
            if (opponentLastMove == lastMove) {
                patience--;
            } else {
                patience = random.nextInt(patienceValue - 4) + 4;
            }
        } else {
            // If you'r opponent not aggressing, change mode to aggressive
            if (opponentLastMove != bestMove(lastA, lastB, lastC)) {
                patience--;
            } else {
                patience = 1;
            }
        }
        //Patience while aggressive is some number. Patience while passive is 1.
        if (patience == 0) {
            if (isAgressive) {
                patience = 1;
            } else {
                patience = random.nextInt(patienceValue - 4) + 4;
            }
            return true;
        }
        return false;

    }

    /**
     * This method returns the region with biggest amount of vegetations.
     *
     * @param a the argument X for a field A
     * @param b the argument X for a field B
     * @param c the argument X for a field C
     * @return int
     */
    private int bestMove(int a, int b, int c) {
        double tmp = Math.max(gainThisRoundGross(a), Math.max(gainThisRoundGross(b), gainThisRoundGross(c)));

        if (Double.compare(gainThisRoundGross(a), tmp) == 0) {
            return 1;
        } else if (Double.compare(gainThisRoundGross(b), tmp) == 0) {
            return 2;
        } else {
            return 3;
        }

    }

    /**
     * This method returns the region with second biggest amount of vegetations.
     *
     * @param a the argument X for a field A
     * @param b the argument X for a field B
     * @param c the argument X for a field C
     * @return int
     */
    private int secondBestMove(int a, int b, int c) {
        double[] tmp = new double[]{gainThisRoundGross(a), gainThisRoundGross(b), gainThisRoundGross(c)};
        Arrays.sort(tmp);
        double secondMaximal = tmp[1];

        if (Double.compare(gainThisRoundGross(a), secondMaximal) == 0) {
            return 1;
        } else if (Double.compare(gainThisRoundGross(b), secondMaximal) == 0) {
            return 2;
        } else {
            return 3;
        }
    }

    /**
     * This method returns your IU email
     *
     * @return your email
     */

    public String getEmail() {
        return "k.hayrullin@innopolis.university";
    }

    ;
}