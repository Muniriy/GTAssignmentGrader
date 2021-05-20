/*
 * AnnaBoroninaCode
 *
 * v1.0.0
 *
 * No license. All rights reserved.
 */

package gametheory.assignment2.students2021;

import gametheory.assignment2.Player;

import java.util.Random;


public class AnnaBoroninaCode implements Player {
    @Override
    /**
     * resets the agent
     */
    public void reset() {
        return;
    }

    /**
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
        Random random = new Random();
        if (xA > xB & xA > xC)
            return 1;
        else if (xB > xA & xB > xC)
            return 2;
        else if (xC > xB & xC > xA)
            return 3;
        else if (xA == xC)
            return (random.nextInt(2) == 0) ? 1 : 3;
        else if (xA == xB)
            return (random.nextInt(2) == 0) ? 1 : 2;
        else if (xB == xC)
            return (random.nextInt(2) == 0) ? 2 : 3;
        else {
            int rand = random.nextInt(3);
            return (rand == 0) ? 1 : ((rand == 1) ? 2 : 3);
        }
    }

    /**
     * @return email address of the author of the code
     */
    @Override
    public String getEmail() {
        return "a.boronina@innopolis.university";
    }
}
