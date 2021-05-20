package gametheory.assignment2.students2021;

import gametheory.assignment2.Player;

import java.util.Arrays;
import java.util.Random;

public class NataliaMatrosovaCode implements Player {
    public int[] fieldScore = new int[3];
    public int[] lastMoves = new int[2];
    public int[] playerScores = new int[2];


    /**
     * Resets the agent. Sets fields' values to 1.
     */
    @Override
    public void reset() {
        Arrays.fill(fieldScore, 1);
        Arrays.fill(lastMoves, 0);
        Arrays.fill(playerScores, 0);

    }

    /**
     * @param opponentLastMove the last move of the opponent
     *                         varies from 0 to 3
     *                         (0 – if this is the first move)
     * @param xA               the argument X for a field A
     * @param xB               the argument X for a field B
     * @param xC               the argument X for a field C
     * @return The field on which agent will step
     */
    @Override
    public int move(int opponentLastMove, int xA, int xB, int xC) {
        Random r = new Random();
        int p = r.nextInt(10);
        if (p < 8) {
            int maxFood = Math.max(xA, Math.max(xB, xC));
            if (xA == maxFood) {
                return 1;
            }
            if (xB == maxFood) {
                return 2;
            } else {
                return 3;
            }
        } else {
            return r.nextInt(3) + 1;
        }
    }

    /**
     * @return my innopolis email
     */
    @Override
    public String getEmail() {
        return "n.matrosova@innopolis.university";
    }


}
