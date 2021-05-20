/*
 * RuslanSahibgareevCode
 *
 * Class represents a Player of a Game provided as the Assignment 2
 * of Innopolis University Game Theory course
 *
 * Author: Ruslan Sahibgareev
 * Date: 11.03.2021
 */

package gametheory.assignment2.students2021;

import gametheory.assignment2.Player;

import java.util.concurrent.ThreadLocalRandom;

/**
 * RuslanSahibgareevCode represents a Player of a Game provided as the Assignment 2
 * of Innopolis University Game Theory course.
 * <p>
 * It uses Coop tactic, to have better results, then there are same strategies in the game.
 * This algorithm can cooperate with itself, so it is very scalable algorithm.
 * It works firstly trying to synchronise with it's opponent and then start to wisely eat.
 * <p>
 * Then it detects. that opponent is not cooperating, it uses best found non-coop tactic:
 * randomly choosing between eating from best spot and eating from opponent's last spot
 * (choosing randomly then opponent's last spot is empty).
 */
public class RuslanSahibgareevCode implements Player {

    /* Spots bounds [1;3] */
    private final int MIN = 1;
    private final int MAX = 3;
    private final int STATUS_START = 1;
    private final int STATUS_COOP_INIT = 2;
    private final int STATUS_COOP = 3;
    private final int STATUS_ERROR = 4;
    /* Statuses of algorithm */
    private int status;
    /* Count how many moves skipped for vegetables to grow */
    private int count;

    /* My player's eating position then in coop */
    private int myPosition;

    /* Skipping position then in coop */
    private int coopPosition;

    /* My player's previous move */
    private int myPrevMove;


    /**
     * Initialising variables
     */
    public RuslanSahibgareevCode() {
        myPrevMove = 0;
        status = STATUS_START;
        myPosition = 0;
        coopPosition = 0;
        count = 0;
    }

    /**
     * @param x - amount of vegetables on spot
     * @return - amount of resources player will get from spot
     */
    private static double getResource(int x) {
        return (10 * Math.exp(x) / (1 + Math.exp(x))) - 5;
    }

    /**
     * Reset variables before match
     */
    @Override
    public void reset() {
        myPrevMove = 0;
        status = STATUS_START;
        myPosition = 0;
        coopPosition = 0;
        count = 0;
    }

    /**
     * @param opponentLastMove the last move of the opponent
     *                         varies from 0 to 3
     *                         (0 – if this is the first move)
     * @param xA               the argument X for a field A
     * @param xB               the argument X for a field B
     * @param xC               the argument X for a field C
     * @return - move according to copy previous opponent's move tactic
     */
    private int getCopy(int opponentLastMove, int xA, int xB, int xC) {
        int[] res = {0, xA, xB, xC};
        if (res[opponentLastMove] > 0) {
            return opponentLastMove;
        } else {
            int randomNum;
            if (opponentLastMove == 0) {
                randomNum = ThreadLocalRandom.current().nextInt(MIN, MAX + 1);
            } else {
                randomNum = ThreadLocalRandom.current().nextInt(MIN, MAX);
            }
            randomNum = (opponentLastMove + randomNum - 1) % MAX + 1;

            return randomNum;
        }
    }

    /**
     * @param xA the argument X for a field A
     * @param xB the argument X for a field B
     * @param xC the argument X for a field C
     * @return - move according to eat from best spot tactic
     */
    private int getBest(int xA, int xB, int xC) {
        double resA = getResource(xA);
        double resB = getResource(xB);
        double resC = getResource(xC);

        if (resA > resB) {
            if (resA > resC) {
                return 1;
            } else if (resC > resA) {
                return 3;
            } else {
                return Math.random() > 0.5 ? 1 : 3;
            }
        } else if (resB > resA) {
            if (resB > resC) {
                return 2;
            } else if (resC > resB) {
                return 3;
            } else {
                return Math.random() > 0.5 ? 2 : 3;
            }
        } else {
            if (resB > resC) {
                return Math.random() > 0.5 ? 2 : 1;
            } else if (resC > resB) {
                return 3;
            } else {
                return ThreadLocalRandom.current().nextInt(MIN, MAX + 1);
            }
        }
    }

    /**
     * @param opponentLastMove the last move of the opponent
     *                         varies from 0 to 3
     *                         (0 – if this is the first move)
     * @param xA               the argument X for a field A
     * @param xB               the argument X for a field B
     * @param xC               the argument X for a field C
     * @return - move chosen by player
     */
    @Override
    public int move(int opponentLastMove, int xA, int xB, int xC) {
        if (status == STATUS_START) {
            if (opponentLastMove != myPrevMove) {
                status = STATUS_COOP_INIT;
                myPosition = myPrevMove;
                coopPosition = 6 - myPrevMove - opponentLastMove;
                myPrevMove = coopPosition;
                return myPrevMove;
            } else {
                myPrevMove = ThreadLocalRandom.current().nextInt(MIN, MAX + 1);
                return myPrevMove;
            }
        } else if (status == STATUS_COOP_INIT) {
            if (opponentLastMove != myPrevMove) {
                status = STATUS_ERROR;
                myPrevMove = Math.random() > 0.5 ?
                        getBest(xA, xB, xC) : getCopy(opponentLastMove, xA, xB, xC);
                return myPrevMove;
            } else {
                count++;
                if (count >= 5) {
                    status = STATUS_COOP;
                    myPrevMove = myPosition;
                    return myPrevMove;
                } else {
                    myPrevMove = coopPosition;
                    return myPrevMove;
                }
            }
        } else if (status == STATUS_COOP) {
            if (opponentLastMove == myPrevMove && myPrevMove == myPosition) {
                status = STATUS_ERROR;
                myPrevMove = Math.random() > 0.5 ?
                        getBest(xA, xB, xC) : getCopy(opponentLastMove, xA, xB, xC);
                return myPrevMove;
            } else if (opponentLastMove != myPrevMove && myPrevMove == coopPosition) {
                status = STATUS_ERROR;
                myPrevMove = Math.random() > 0.5 ?
                        getBest(xA, xB, xC) : getCopy(opponentLastMove, xA, xB, xC);
                return myPrevMove;
            } else {
                myPrevMove = myPosition + coopPosition - myPrevMove;
                return myPrevMove;
            }
        } else {
            myPrevMove = Math.random() > 0.5 ?
                    getBest(xA, xB, xC) : getCopy(opponentLastMove, xA, xB, xC);
            return myPrevMove;
        }
    }

    /**
     * @return student's e-mail
     */
    @Override
    public String getEmail() {
        return "r.sahibgareev@innopolis.university";
    }
}
