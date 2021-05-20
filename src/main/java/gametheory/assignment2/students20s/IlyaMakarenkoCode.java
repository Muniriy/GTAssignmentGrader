/*
 * IlyaMakarenkoCode
 *
 * Made by Ilya Makarenko
 * Innopolis University
 * Group BS18-SB-01
 *
 * This file contains both submitted strategy and testing platform
 */

package gametheory.assignment2.students20s;

import gametheory.assignment2.Player;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class IlyaMakarenkoCode implements Player {

    /**
     * Email for {@link IlyaMakarenkoCode#getEmail()}
     */
    private static final String IU_EMAIL = "i.makarenko@innopolis.university";
    /**
     * Variables strategy uses in decisions
     */
    private Strategy currentStrategy = Strategy.UNKNOWN;
    private int myLastMove = 0;
    private int myFeedField = 0;
    private int restField = 0;
    private int movesWait = 0;

    /**
     * This method is used to reset the state of the strategy
     * before the game with a new opponent
     */
    @Override
    public void reset() {
        myLastMove = 0;

        myFeedField = 0;
        movesWait = 0;

        currentStrategy = Strategy.UNKNOWN;
    }

    /**
     * This method returns a move which strategy decides to do
     * based on current state of the game and strategy.
     *
     * @param opponentLastMove the last move of the opponent
     *                         varies from 0 to 3
     *                         (0 – if this is the first move)
     * @param xA               the argument X for a field A
     * @param xB               the argument X for a field B
     * @param xC               the argument X for a field C
     * @return number [1, 2, 3], the field in which strategy decides to move
     */
    @Override
    public int move(int opponentLastMove, int xA, int xB, int xC) {

        int result = 0;

        if (currentStrategy == Strategy.UNKNOWN) {
            if (opponentLastMove != myLastMove) {
                // assume that we cooperate and save information about it
                currentStrategy = Strategy.COOPERATION;

                myFeedField = myLastMove;
                restField = 6 - (myFeedField + opponentLastMove);
            } else {
                // random until we got different fields
                result = chooseRandom(xA, xB, xC);
            }
        }

        if (currentStrategy == Strategy.COOPERATION) {
            if (opponentLastMove == myFeedField) {
                // opponent don't cooperate, therefore change strategy to greed
                currentStrategy = Strategy.GREED_RANDOM;
            } else {
                if (movesWait < 5) {
                    // wait for area to grow
                    result = restField;
                    movesWait += 1;
                } else {
                    if (myLastMove == restField) {
                        // eat
                        result = myFeedField;
                    } else {
                        // wait
                        result = restField;
                    }
                }
            }
        }

        if (currentStrategy == Strategy.GREED_RANDOM) {
            // choose random non-zero field
            result = chooseRandom(xA, xB, xC);
        }

        myLastMove = result;
        return result;
    }

    /**
     * This method returns random integer which represents field to move,
     * Also this will never return a field with 0 food
     *
     * @return integer from 1 to 3
     */
    private int chooseRandom(int xA, int xB, int xC) {

        ArrayList<Integer> nonZeroFields = new ArrayList<Integer>();
        if (xA != 0) nonZeroFields.add(1);
        if (xB != 0) nonZeroFields.add(2);
        if (xC != 0) nonZeroFields.add(3);

        return nonZeroFields.get(ThreadLocalRandom.current().nextInt(nonZeroFields.size()));
    }

    /**
     * This method returns author's IU email
     *
     * @return author's email
     */
    @Override
    public String getEmail() {
        return IU_EMAIL;
    }

    /**
     * State of strategy
     */
    private enum Strategy {
        /**
         * Initial strategy, first several moves it checks opponent moves,
         * and then decides strategy to choose
         */
        UNKNOWN,

        /**
         * Cooperation strategy, when we both choose one field and let
         * other cells to grow
         */
        COOPERATION,

        /**
         * If we see that opponent don't want to cooperate, we decide
         * to choose greedy strategy
         */
        GREED_RANDOM,
    }

    ;
}
