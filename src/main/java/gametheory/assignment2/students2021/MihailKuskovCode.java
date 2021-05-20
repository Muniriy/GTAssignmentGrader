package gametheory.assignment2.students2021;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;

/**
 * The <code>MihailKuskovCode</code> class implements the main
 * strategy for participating in all the course tournament
 *
 * @author Mihail Kuskov
 */
public class MihailKuskovCode implements gametheory.assignment2.Player {

    static Random rnd = new Random();
    int lastMove, stage, myCell, oppCell, baseCell;
    boolean trusted;

    /**
     * The main and only nilary class constructor, which just calls according
     * {@link MihailKuskovCode#reset} method
     */
    public MihailKuskovCode() {
        reset();
    }

    /**
     * Resets the object internal state by setting all the private class fields
     * to the default values.
     *
     * @implNote Should be called each time before the start of the match with another <code>Player</code>
     */
    @Override
    public void reset() {
        lastMove = 0;
        trusted = true;
        stage = 0;
        myCell = -1;
        oppCell = -1;
        baseCell = -1;
    }

    /**
     * Main method of the strategy, which starts as a cooperative player and then either
     * continue to cooperate with the opponent or chooses the greedy strategy of picking
     * random game field cell out of two with the most amount
     *
     * @param opponentLastMove the last move of the opponent [0, 3]
     * @param xA               X value for a field A
     * @param xB               X value for a field B
     * @param xC               X value for a field C
     * @return Returns 1 for cell A, 2 for cell B, 3 for cell C
     * @implNote <code>opponentLastMove</code> equals 0 for the first move in the game
     */
    @Override
    public int move(int opponentLastMove, int xA, int xB, int xC) {
        int[] x = new int[]{-1, xA, xB, xC};
        if (trusted) {
            if (stage == 0) {
                if (opponentLastMove == lastMove) {
                    int ans = rnd.nextInt(3) + 1;
                    lastMove = ans;
                    return ans;
                } else {
                    stage = 1;
                    myCell = lastMove;
                    oppCell = opponentLastMove;
                    baseCell = 6 - oppCell - myCell;
                    return baseCell;
                }
            }
            if (stage == 1) {
                if (opponentLastMove == myCell) {
                    trusted = false;
                    return oppCell;
                }
                if (x[myCell] < 9) {
                    return baseCell;
                } else {
                    return myCell;
                }
            }
        } else {
            Integer[] y = {1, 2, 3};
            int[] fields = new int[]{xA, xB, xC};
            Arrays.sort(y, Comparator.comparingInt(a -> -fields[a - 1]));
            if (rnd.nextBoolean()) {
                return y[0];
            } else {
                return y[1];
            }
        }
        return -1;
    }

    /**
     * Constant method for identifying this class in the outer code
     *
     * @return the string with my corporate IU email
     */
    @Override
    public String getEmail() {
        return "m.kuskov@innopolis.university";
    }
}
