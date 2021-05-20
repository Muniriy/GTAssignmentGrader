package gametheory.assignment2.students20s;

import gametheory.assignment2.Player;

/**
 * Coop strategy that can be divided into several stages:
 * 1: Coop player try to identify whether other player is also use coop strategy
 * 2: Coop player wait untill X is at least as constant variable WAITING
 * 3: Coop player go to his field, return to waiting field and repeat until match is over
 * If coop player identify user as non-coop player, it turns into AutoAdjustedRandomGreedy
 */
public class RuslanMuravevCode implements Player {

    private static final int PATIENCE = 7;
    private static final int WAITING = 6;

    private int rounds = 100;

    private int stage;
    private int substage;
    private int spentPatience;
    private int previousMove;
    private int currentRound;

    private int mainPosition;
    private int waitPosition;

    public RuslanMuravevCode() {
        currentRound = 0;
        stage = 1;
        spentPatience = 0;
        previousMove = 0;
    }

    /**
     * @see Player
     */
    @Override
    public void reset() {
        if (currentRound != 0 && currentRound != rounds)
            rounds = currentRound;
        currentRound = 0;
        stage = 1;
        spentPatience = 0;
        previousMove = 0;
    }

    private int maxIndex(int xA, int xB, int xC) {
        if (xA > xB && xA > xC) {
            return 1;
        } else if (xB > xC) {
            return 2;
        } else {
            return 3;
        }
    }

    private int inter(int xA, int xB, int xC) {
        if (xA > xB) {
            if (xB > xC) {
                return 2;
            } else if (xA > xC) {
                return 3;
            } else {
                return 1;
            }
        } else {
            if (xA > xC) {
                return 1;
            } else if (xB > xC) {
                return 3;
            } else {
                return 2;
            }
        }
    }

    private int antiCoop(int opponentLastMove, int xA, int xB, int xC) {
        java.util.ArrayList<Integer> xs = new java.util.ArrayList<>();
        xs.add(-42);
        xs.add(xA);
        xs.add(xB);
        xs.add(xC);

        java.util.Random rng = new java.util.Random();
        int maxCell = maxIndex(xA, xB, xC);
        int middleCell = inter(xA, xB, xC);
        int val = rng.nextInt(xs.get(maxCell) + xs.get(middleCell));
        if (val < xs.get(maxCell))
            return maxCell;
        else
            return middleCell;
    }

    /**
     * @param opponentLastMove the last move of the opponent
     *                         varies from 0 to 3
     *                         (0 – if this is the first move)
     * @param xA               the argument X for a field A
     * @param xB               the argument X for a field B
     * @param xC               the argument X for a field C
     * @return index of the field in accordance to specified strategy
     */
    @Override
    public int move(int opponentLastMove, int xA, int xB, int xC) {
        currentRound++;
        java.util.ArrayList<Integer> xs = new java.util.ArrayList<>();
        xs.add(-420);
        xs.add(xA);
        xs.add(xB);
        xs.add(xC);
        if (stage == -1) { // Opponent is not cooperator
            spentPatience += 1;
            return antiCoop(opponentLastMove, xA, xB, xC);
        }
        if (stage == 1) { //Handshake
            if (opponentLastMove == 0) { // First move
                previousMove = new java.util.Random().nextInt(3) + 1;
                return previousMove;
            } else if (previousMove == opponentLastMove) { // Unsuccessful handshake
                if (spentPatience == PATIENCE) { // Patience is over
                    stage = -1;
                    return antiCoop(opponentLastMove, xA, xB, xC);
                } else { //Retry handshake
                    previousMove = new java.util.Random().nextInt(3) + 1;
                    spentPatience += 1;
                    return previousMove;
                }
            } else { //Successful handshake
                stage = 2;
                mainPosition = previousMove;
                waitPosition = 6 - mainPosition - opponentLastMove;
                return waitPosition;
            }
        }
        if (stage == 2) { // Waiting
            if (opponentLastMove == mainPosition) {
                stage = -1;
                return antiCoop(opponentLastMove, xA, xB, xC);
            } else {
                if (xs.get(mainPosition) >= WAITING) {
                    stage = 3;
                    substage = 2;
                    return mainPosition;
                } else {
                    return waitPosition;
                }
            }
        }
        if (stage == 3) {
            if (rounds - currentRound < WAITING && rounds <= currentRound) { //finish eating when match is close to end
                return mainPosition;
            }
            if (rounds > currentRound) {
                stage = 2;
                return waitPosition;
            }
            if (substage == 1) {
                if (opponentLastMove == mainPosition) {
                    stage = -1;
                    return antiCoop(opponentLastMove, xA, xB, xC);
                }
                substage++;
                return mainPosition;
            } else if (substage == 2) {
                if (opponentLastMove == mainPosition) {
                    stage = -1;
                    return antiCoop(opponentLastMove, xA, xB, xC);
                }
                substage = 1;
                return waitPosition;
            }
        }
        reset(); // Class is not initialized through constructor
        return 2;
    }

    /**
     * @return Ruslan Muravev email
     * @see Player
     */
    @Override
    public String getEmail() {
        return "r.muravev@innopolis.university";
    }


}