/*
 * Student: Rufina Sirgalina
 * Group: BS18-DS-02
 *
 * Game Theory. Assignment 2
 * Player part
 */

package gametheory.assignment2.students20s;

import gametheory.assignment2.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * RufinaSirgalinaCode class for the player that will be used for the rating tournament.
 * Player always chooses the field whose value is not the smallest.
 */
public class RufinaSirgalinaCode implements Player {

    /**
     * This method is needed to reset the agent before the match
     * with another player containing several rounds
     */
    @Override
    public void reset() {
    }

    /**
     * This method returns the move of the player based on
     * X values of all fields.
     *
     * @param opponentLastMove the last move of the opponent
     *                         varies from 0 to 3
     *                         (0 – if this is the first move)
     * @param xA               the argument X for a field A
     * @param xB               the argument X for a field B
     * @param xC               the argument X for a field C
     * @return the move to any field which X-value is not minimum
     * (if all fields has the same X-value, it chooses
     * randomly one of the fields)
     */
    @Override
    public int move(int opponentLastMove, int xA, int xB, int xC) {
        int move;                                               // the move for this round
        int[] fields = new int[]{xA, xB, xC};
        int min = Math.min(Math.min(xA, xB), xC);                 // minimum among X-values
        List<Integer> idxForNotMin = new ArrayList<Integer>();        // fields with not minimum X-value
        for (int i = 0; i < 3; i++) {
            if (fields[i] != min) {
                idxForNotMin.add(i + 1);
            }
        }
        if (idxForNotMin.size() == 0) {

            /* choose random field, if all fields have the same X-value */
            int idx = (int) Math.floor(Math.random() * 3);
            move = idx + 1;
        } else {

            /* choose fields with not minimum X-value */
            int idx = (int) Math.floor(Math.random() * idxForNotMin.size());
            move = idxForNotMin.get(idx);
        }
        return move;
    }

    /**
     * This method returns your IU email
     *
     * @return your email
     */
    @Override
    public String getEmail() {
        return "r.sirgalina@innopolis.university";
    }
}
