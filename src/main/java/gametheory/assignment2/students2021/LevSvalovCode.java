package gametheory.assignment2.students2021;

import gametheory.assignment2.Player;


public class LevSvalovCode implements Player {
    //  Constant thresholds
//  Maximum number of collisions of randomly picking the cell
    final int forgivenessThreshold = 7;

    //  Amount of rounds we wait until start to eat grass
    final int waitingThreshold = 7;


    //  index of the cell where we wait until the grass will grow on the eat cell
    int waitCell;

    //  index of the cell where we eat in cooperative sub-strategy
    int eatCell;

    //  Counter of current rounds we have already waited the growth of the grass
    int waitingCounter;

    //  index of the cell of our previous move
    int myPreviousMove;

    //  boolean var determining if cooperative strategy is still relevant of we switched to greedy strategy
    boolean cooperate;

    //  boolean var determining if we successfully determined eat and wait cells or colliding on the same cell
    boolean successfulHandshake;

    //  current number of collisions with opponent in the start of the game (part of the handshake)
    int numCollisions;


    /**
     * This method is called to reset the agent before the match
     * with another player containing several rounds
     */
    @Override
    public void reset() {
        myPreviousMove = 0;
        waitCell = -1;
        eatCell = -1;
        numCollisions = 0;
        waitingCounter = 0;
        cooperate = true;
        successfulHandshake = false;
    }


    /**
     * This method returns the move of the player based on the strategy: cooperative or greedy
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


        if (opponentLastMove == 0) {  // the first round
            cooperate = true;
            successfulHandshake = false;
            return myRandomMove();
        } else {  // 2+ round

//          check if we still on cooperative track
            if (cooperate) {
//              if we determined eat/wait cells, we can make move according to cooperative strategy
                if (successfulHandshake) {
                    return cooperateMove(opponentLastMove, xA, xB, xC);
                } else {
//                  if don't know eat/wait cells yet, check if we collided when randomly moved
                    if (opponentLastMove == myPreviousMove) { // collision
                        numCollisions++;
//                      check if collisions were too many and we start to play greedy strategy
                        if (numCollisions < forgivenessThreshold) {
                            return myRandomMove();
                        } else {
                            cooperate = false;
                            return selfishMove(opponentLastMove, xA, xB, xC);
                        }
                    } else {
//                      successfully went differently and determine eat and wait cells
                        successfulHandshake = true;
                        eatCell = myPreviousMove;
//                      wait cell is not which currently my player and opponent are standing on
                        waitCell = 6 - myPreviousMove - opponentLastMove;
                        myPreviousMove = waitCell;
//                      go to wait cell for waiting growth of grass
                        return waitCell;
                    }
                }
            } else {
//              we on greedy strategy
                return selfishMove(opponentLastMove, xA, xB, xC);
            }
        }
    }


    /**
     * This method determine move (if we are on cooperative strategy)
     * We know eat and wait cells already and now we need to wait for growth
     * and after that begin process of eating
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
    private int cooperateMove(int opponentLastMove, int xA, int xB, int xC) {
        // if opponent on my own eat cell, then stop cooperating and switch to greedy strategy
        if (opponentLastMove == eatCell) {
            cooperate = false;
            return selfishMove(opponentLastMove, xA, xB, xC);
        } else {
//          check if the grass on the eat cell have grown enough
            if (waitingCounter < waitingThreshold) {
//              still need to wait, keep standing on wait cell
                waitingCounter++;
                return waitCell;
            } else {
//              We have waited enough, so now start eat
//              1 round eat, 1 round wait and repeat this
                if (myPreviousMove == waitCell) {
                    myPreviousMove = eatCell;
                    return eatCell;
                } else {
                    myPreviousMove = waitCell;
                    return waitCell;
                }
            }
        }
    }


    /**
     * This method determine move (if we are on cooperative strategy)
     * it picks the cell:
     * - with 70% probability with the highest score
     * - with 30% probability cell with 2nd highest score
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
    private int selfishMove(int opponentLastMove, int xA, int xB, int xC) {

        int biggest = -1;
        int secondBiggest = -1;
        if (Math.max(xA, Math.max(xB, xC)) == xA) {
            biggest = 1;
            if (Math.max(xB, xC) == xB) {
                secondBiggest = 2;
            } else {
                secondBiggest = 3;
            }
        } else {
            if (Math.max(xB, xC) == xB) {
                biggest = 2;
                if (Math.max(xA, xC) == xA) {
                    secondBiggest = 1;
                } else {
                    secondBiggest = 3;
                }
            } else {
                biggest = 3;
                if (Math.max(xA, xB) == xA) {
                    secondBiggest = 1;
                } else {
                    secondBiggest = 2;
                }
            }
        }

//      roll the die to determine which we go: the 1st best or the 2nd best
        int roll = (int) (1 + Math.random() * 10);
        if (roll > 7) return secondBiggest;
        return biggest;

    }


    /**
     * This method makes random move without paying attention to anything
     * And saves cell index to myPreviousMove variable
     *
     * @return cell index - 1, 2 or 3
     */
    private int myRandomMove() {
        myPreviousMove = (int) (1 + Math.random() * 3);
        return myPreviousMove;
    }


    /**
     * This method returns my IU email
     *
     * @return your email
     */
    @Override
    public String getEmail() {
        return "l.svalov@innopolis.university";
    }
}