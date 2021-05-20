package gametheory.assignment2.students2021;

import gametheory.assignment2.Player;

import java.util.concurrent.ThreadLocalRandom;

public class NikitaTihonovCode implements Player {
    /**
     * This method is called to reset the agent before the match
     * with another player containing several rounds
     */

    @Override

    public void reset() {

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
    @Override

    public int move(int opponentLastMove, int xA, int xB, int xC) {

        if (Math.min(Math.min(xA, xB), xC) > 2) {
            return ThreadLocalRandom.current().nextInt(1, 4);
        } else {
            int max = Math.max(Math.max(xA, xB), xC);

            if (xA == xB && xB == xC) {
                return ThreadLocalRandom.current().nextInt(1, 4);
            }

            if (xA == max && xB == max) {
                return ThreadLocalRandom.current().nextInt(1, 3);
            }

            if (xB == max && xC == max) {
                return ThreadLocalRandom.current().nextInt(2, 4);
            }

            if (xA == max && xC == max) {
                int temp = ThreadLocalRandom.current().nextInt(1, 3);
                if (temp == 1)
                    return 1;
                else return 3;
            }

            if (xA == max) {
                return 1;
            }

            if (xB == max) {
                return 2;
            } else return 3;
        }
    }

    ;

    /**
     * This method returns your IU email
     *
     * @return your email
     */
    @Override

    public String getEmail() {
        return "n.tihonov@innopolis.university";
    }

    ;
}
