package gametheory.assignment2.students2021;

import gametheory.assignment2.Player;

/**
 * Best agent
 *
 * @autor Daniyar Galimzhanov
 */
public class DaniyarGalimzhanovCode implements Player {

    /**
     * waiting counter
     */
    private int waitTillAttack = 0;
    /**
     * how long to wait for leaving field when there is ally agent
     */
    private int waitLimit;
    /**
     * check if ally waited less
     */
    private boolean wentSoon = false;
    /**
     * check if agent should play aggressive
     */
    private boolean aggressionMode = false;
    /**
     * check if there is aggressive agent on your field
     */
    private boolean onOneCellWithAggressive = false;
    /**
     * limit for aggression
     */
    private int aggressionLimit = 0;
    /**
     * field to go
     */
    private int moveToWhenEat;
    /**
     * last aggressive move
     */
    private int myLastAggressiveMove = 0;

    /**
     * Constructor
     */
    public DaniyarGalimzhanovCode() {
        waitLimit = newWaitLimit();
    }

    /**
     * This method is called to reset the agent before the match
     * with another player containing several rounds
     */
    @Override
    public void reset() {
        aggressionMode = false;
        wentSoon = false;
        onOneCellWithAggressive = false;
        moveToWhenEat = 1;
        waitTillAttack = 0;
        waitLimit = newWaitLimit();
        myLastAggressiveMove = 0;
    }

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


        if (aggressionMode) {
            // play aggressive
            return aggression(opponentLastMove, xA, xB, xC);
        } else {
            // cooperate
            return cooperate(opponentLastMove, xA, xB, xC);
        }
    }

    /**
     * method for aggressive strategy
     *
     * @param opponentLastMove - last move of opponent
     * @param xA               value of field A
     * @param xB               value of field B
     * @param xC               value of field C
     * @return field to go
     */
    private int aggression(int opponentLastMove, int xA, int xB, int xC) {

        // fight for field with other player for particular number of rounds, if value below 3 leave
        if (myLastAggressiveMove == opponentLastMove || onOneCellWithAggressive) {
            onOneCellWithAggressive = aggressionLimit < 5;
            aggressionLimit++;

            // leave and go to highest payoff field
            if (myLastAggressiveMove == 1 && (xB >= 3 || xC >= 3)) {
                if (xA < 3) {
                    return highestField(xA, xB, xC);
                }
            } else if (myLastAggressiveMove == 2 && (xA >= 3 || xC >= 3)) {
                if (xB < 3) {
                    return highestField(xA, xB, xC);
                }
            } else if (myLastAggressiveMove == 3 && (xA >= 3 || xB >= 3)) {
                if (xC < 3) {
                    return highestField(xA, xB, xC);
                }
            }

            if (myLastAggressiveMove == 1) {
                if (xB > xC) {
                    myLastAggressiveMove = 2;
                    return 2;
                } else {
                    myLastAggressiveMove = 3;
                    return 3;
                }
            } else if (myLastAggressiveMove == 2) {
                if (xA > xC) {
                    myLastAggressiveMove = 1;
                    return 1;
                } else {
                    myLastAggressiveMove = 3;
                    return 3;
                }
            } else {
                if (xA > xB) {
                    myLastAggressiveMove = 1;
                    return 1;
                } else {
                    myLastAggressiveMove = 2;
                    return 2;
                }
            }

        } else {
            // reset aggression limit
            aggressionLimit = 0;
            // go for max payoff
            myLastAggressiveMove = highestField(xA, xB, xC);
            return myLastAggressiveMove;
        }
    }

    /**
     * This method returns your IU email
     *
     * @return my uni email
     */
    @Override
    public String getEmail() {
        return "d.galimzhanov@innopolis.university";
    }

    /**
     * generate limit
     *
     * @return limit
     */
    private int newWaitLimit() {
        return 7 + (int) (Math.random() * 3);
    }

    /**
     * cooperation strategy
     *
     * @param opponentLastMove - last move of opponent
     * @param xA               value of field A
     * @param xB               value of field B
     * @param xC               value of field C
     * @return field to go
     */
    private int cooperate(int opponentLastMove, int xA, int xB, int xC) {
        // check if there is ally
        if (waitTillAttack <= waitLimit) {

            if (waitTillAttack == 8 || waitTillAttack == 9) {
                if (opponentLastMove != 1) {
                    wentSoon = true;
                    if (opponentLastMove == 2) {
                        moveToWhenEat = 3;
                    } else {
                        moveToWhenEat = 2;
                    }
                    return moveToWhenEat;
                }
            } else {
                if (opponentLastMove != 1 && opponentLastMove != 0 && waitTillAttack != 7) {
                    // attack if it is not ally
                    aggressionMode = true;
                    if (xA > xB) {
                        return 1;
                    } else if (xB > xC) {
                        return 2;
                    } else {
                        return 3;
                    }
                }
            }
            waitTillAttack += 1;
            return 1;
        } else {

            // if ally went soon go random field
            if (!wentSoon) {
                moveToWhenEat = 2 + (int) (Math.random() * 2);
            }

            // check if field already occupied by ally to avoid fight
            int fieldX;
            if (moveToWhenEat == 2) {
                fieldX = xB;
            } else {
                fieldX = xC;
            }

            // return to fighting field when value of field is 3
            if (fieldX < 4) {
                waitTillAttack = 0;
                waitLimit = newWaitLimit();
                if (moveToWhenEat == 2) {
                    if (xA > xC) {
                        return 1;
                    } else {
                        waitTillAttack = 10;
                        return 3;
                    }
                } else {
                    if (xA > xB) {
                        return 1;
                    } else {
                        waitTillAttack = 10;
                        return 2;
                    }
                }
            }
            return moveToWhenEat;
        }
    }

    /**
     * find highest payoff field
     *
     * @param xA - value of field A
     * @param xB - value of field B
     * @param xC - value of field C
     * @return highest filed payoff
     */
    private int highestField(int xA, int xB, int xC) {
        int max = Math.max(Math.max(xA, xB), xC);
        if (max == xC) {
            return 3;
        } else if (max == xA) {
            return 1;
        } else {
            return 2;
        }
    }

}
