package gametheory.assignment2.students20s;

import gametheory.assignment2.Player;

/**
 * This class implements a player who cooperates with
 * an opponent as follows:
 * first, the player randomly chooses one of the three fields.
 * If the fields on which the player and the opponent stand
 * do not coincide, then the player remembers the current field
 * as the main one, on which he will periodically stand up
 * when f(x) approaches the maximum.
 * And that field, which neither the player nor the opponent has entered,
 * the player remembers as a buffer field for waiting
 * until f(x) on the main field approaches the maximum.
 * If, in the first round, the player and the opponent stood
 * on the same fields, then the players proceed to
 * the next round until they randomly choose different fields.
 * If the opponent does not adhere to this tactic,
 * then the player acts as a GreedyPlayer
 */
public class MarinaNikolaevaCode implements Player {
    /**
     * variable for storing the value of player's current move;
     * varies from 0 to 3 (0 – if this is the first move)
     */
    int myMove = 0;
    /**
     * variable for storing the value of player's last move;
     * varies from 0 to 3 (0 – if this is the first move)
     */
    int myLastMove = 0;
    /**
     * variable for storing the value  of player's main field;
     * varies from 0 to 3 (0 – if this is the first move)
     */
    int myRegion = 0;
    /**
     * variable for storing the value of player's buffer field;
     * varies from 0 to 3 (0 – if this is the first move)
     */
    int bufferRegion = 0;
    /**
     * variable for checking which tactics to choose for the player;
     * it varies from 0 to 2
     * (0 - until the player and the opponent are on different fields,
     * 1 - player adheres to cooperative tactic,
     * 2 - player switches to GreedyPlayer tactic)
     */
    int check = 0;
    /**
     * the argument X for the main field
     */
    int xMyRegion = 0;

    /**
     * This method is called to reset the agent before the match
     * with another player containing several rounds
     */
    @Override
    public void reset() {

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

        //This condition is necessary in order to determine
        //the main field and the buffer field for the player
        if ((myLastMove != opponentLastMove) && (check == 0)) {
            check = 1;
            if (myLastMove == 1) {
                if (opponentLastMove == 2) {
                    myRegion = 1;
                    bufferRegion = 3;
                } else if (opponentLastMove == 3) {
                    myRegion = 1;
                    bufferRegion = 2;
                }
            }
            if (myLastMove == 2) {
                if (opponentLastMove == 1) {
                    myRegion = 2;
                    bufferRegion = 3;
                } else if (opponentLastMove == 3) {
                    myRegion = 2;
                    bufferRegion = 1;
                }
            }
            if (myLastMove == 3) {
                if (opponentLastMove == 1) {
                    myRegion = 3;
                    bufferRegion = 2;
                    ;
                } else if (opponentLastMove == 2) {
                    myRegion = 3;
                    bufferRegion = 1;
                }
            }
        }

        //This condition is necessary in order to randomly
        //choose one of the three fields in the beginning
        if (check == 0) {
            myMove = (int) ((Math.random() * (2) + 1));
            myLastMove = myMove;
            return myMove;
        }

        //This condition is necessary in order to know argument X
        //for the player's main field
        else if ((check == 1) && (opponentLastMove != myRegion)) {
            if (myLastMove == 1) {
                xMyRegion = xA;
            } else if (myLastMove == 2) {
                xMyRegion = xB;
            } else if (myLastMove == 3) {
                xMyRegion = xC;
            }

            //This condition is necessary for player to move
            //to the buffer field if X less than 6
            if (xMyRegion < 6) {
                return bufferRegion;
            } else {
                return myRegion;
            }
        }
        //This condition is necessary in order to check if opponent
        //does not follow cooperate tactic
        else if ((check == 1) && (opponentLastMove == myRegion)) {
            check = 2;
        }

        //This condition is necessary to change tactic to GreedyPlayer
        //if opponent does not follow cooperate tactic
        if (check == 2) {
            if ((xA == xB) && (xB == xC)) {
                int myMove = (int) (Math.random() * (2) + 1);
                return myMove;
            } else if ((xA == xB) && (xA > xC)) {
                int[] moves = new int[]{1, 2};
                int rnd = (int) ((Math.random()) + 1);
                return moves[rnd];
            } else if ((xA == xC) && (xA > xB)) {
                int[] moves = new int[]{1, 3};
                int rnd = (int) ((Math.random()) + 1);
                return moves[rnd];
            } else if ((xB == xC) && (xB > xA)) {
                int[] moves = new int[]{2, 3};
                int rnd = (int) ((Math.random()) + 1);
                return moves[rnd];
            } else if ((xA >= xB) && (xA >= xC)) {
                return 1;
            } else if ((xB >= xA) && (xB >= xC)) {
                return 2;
            } else if ((xC >= xA) && (xC >= xB)) {
                return 3;
            }
        }


        return 0;
    }

    /**
     * This method returns your IU email
     *
     * @return your email
     */
    @Override
    public String getEmail() {
        return "m.nikolaeva@innopolis.university";
    }
}
