package gametheory.assignment2.students20s;

import gametheory.assignment2.Player;

/**
 * Initially assumes that opponent is not greedy
 * random initialization, choosing 'my' and buffer cells,
 * monitoring opponent behaviour and switching to a CleverGreedy strategy if the opponent does not cooperate
 */
public class PolinaTurischevaCode implements Player {

    private int waitingConst = 6;
    private int myCell = -1;
    private int bufferCell = -1;
    private boolean first = true;
    private int waitingCounter = 0;
    private boolean opponentCooperates = true;
    private boolean opponentGreedy = false;
    private int maxStarvation = 3;
    private int curCollisions = 0;
    private int myLastMove = 0;
    private boolean firstWaiting = true;
    private int countRounds = 0;
    private int borderOne = 12;
    private int borderTwo = 30;
    private int borderThree = 70;
    private int borderFour = 180;
    private int borderFive = 475;
    private int borderSix = 1280;
    private int borderSeven = 3460;

    /**
     * Make all values as they were during initialization
     * takes into account number of games with the previous player
     */
    @Override
    public void reset() {
        myCell = -1;
        bufferCell = -1;
        first = true;
        waitingCounter = 0;
        opponentCooperates = true;
        opponentGreedy = false;
        maxStarvation = 3;
        curCollisions = 0;
        myLastMove = 0;
        if (countRounds >= borderSeven) {
            waitingConst = 9;
        }

        if (borderSeven > countRounds && countRounds >= borderSix) {
            waitingConst = 8;
        }

        if (borderSix > countRounds && countRounds >= borderFive) {
            waitingConst = 7;
        }

        if (borderFive > countRounds && countRounds >= borderFour) {
            waitingConst = 6;
        }

        if (borderFour > countRounds && countRounds >= borderThree) {
            waitingConst = 5;
        }

        if (borderThree > countRounds && countRounds >= borderTwo) {
            waitingConst = 4;
        }

        if (borderTwo > countRounds && countRounds >= borderOne) {
            waitingConst = 3;
        }
        if (borderOne > countRounds) {
            waitingConst = 2;
        }
        countRounds = 0;
    }

    /**
     * @return my innopolis email
     */
    @Override
    public String getEmail() {
        return "p.turischeva@innopolis.university";
    }


    /**
     * implements the strategy described in the report
     *
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
        countRounds += 1;
        if (opponentLastMove != myLastMove && first) {
            first = false;
            myCell = myLastMove;
            bufferCell = (myLastMove + opponentLastMove) == 3 ? 3 : ((myLastMove + opponentLastMove) == 4 ? 2 : 1);
            myLastMove = bufferCell;
            return bufferCell;
        } else {
            if (opponentLastMove == myLastMove && first) {

                int firstMax = (xA > Math.max(xB, xC)) ? 1 : ((xB > xC) ? 2 : 3);
                int secondMax = (firstMax == 1) ?
                        ((xB == Math.max(xB, xC)) ? 2 : 3) :
                        ((firstMax == 2 && xA == Math.max(xA, xC)) ? 1 : 3);
                int res = (Math.random() > 0.5) ? secondMax : firstMax;
                myLastMove = res;
                return res;
            } else {
                if (opponentLastMove == myCell || !opponentCooperates) {
                    opponentCooperates = false;
                    int res;
//                    starts non-cooperation
                    curCollisions = (myLastMove == opponentLastMove) ? curCollisions + 1 : 0;
                    opponentGreedy = curCollisions > maxStarvation;
                    if (!opponentGreedy) {
                        res = (xA > Math.max(xB, xC)) ? 1 : ((xB > xC) ? 2 : 3);
                    } else {
                        int firstMax = (xA > Math.max(xB, xC)) ? 1 : ((xB > xC) ? 2 : 3);
                        int secondMax = 3;
                        if (firstMax == 1) {
                            secondMax = (xB == Math.max(xB, xC)) ? 2 : 3;
                        } else {
                            if (firstMax == 2) {
                                secondMax = (xA == Math.max(xA, xC)) ? 1 : 3;
                            }
                        }
                        res = (Math.random() > 0.6) ? secondMax : firstMax;

                    }
                    myLastMove = res;
                    return res;

                } else {
                    if (myLastMove == bufferCell) {
                        if (waitingCounter < waitingConst && firstWaiting) {
                            waitingCounter += 1;
                            myLastMove = bufferCell;
                            return bufferCell;
                        } else {
                            firstWaiting = false;
                            waitingCounter = 0;
                            myLastMove = myCell;
                            return myCell;
                        }

                    } else {
                        myLastMove = myCell;
                        return myCell;
                    }
                }
            }
        }
    }

}
