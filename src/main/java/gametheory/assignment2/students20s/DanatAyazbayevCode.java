/**
 * "move" method described thoroughly in the report
 *
 * @author Danat Ayazbayev
 */

package gametheory.assignment2.students20s;

import gametheory.assignment2.Player;

public class DanatAyazbayevCode implements Player {

    /** phase parameter (0 or 1) */
    private int phase;

    /** number of moves passed starting from some event (0 or 1) */
    private int moveNumber;

    /** the move made by agent in the last round*/
    private int myLastMove;

    /**
     * Constructor
     * @see DanatAyazbayevCode#reset()
     * when creating object resetting the attributes for the next match
     */
    public DanatAyazbayevCode() {
        reset();
    }

    /**
     * @see DanatAyazbayevCode#phase initial phase is 0
     * @see DanatAyazbayevCode#moveNumber initially 0 moves are made
     * @see DanatAyazbayevCode#myLastMove initially 1 because when match starts agent goes to field 1
     */
    @Override
    public void reset() {
        phase = 0;
        moveNumber = 0;
        myLastMove = 1;
    }

    /**
     * This method was thoroughly documented in the report
     * @param opponentLastMove
     * @param xA
     * @param xB
     * @param xC
     * @return
     */
    @Override
    public int move(int opponentLastMove, int xA, int xB, int xC) {
        if (opponentLastMove == 0) {
            return 1;
        }
        if (phase == 0) {
            moveNumber++;
        }
        if (phase == 0 && moveNumber < 20) {
            myLastMove = opponentLastMove;
            return myLastMove;
        }
        if (phase == 0) {
            moveNumber = 0;
        }
        phase = 1;
        if (myLastMove == opponentLastMove) {
            moveNumber++;
        } else {
            moveNumber = 0;
        }
        int midField = Math.max(xA, Math.min(xB, xC));
        int maxField = Math.max(xA, Math.max(xB, xC));
        if (maxField <= 3) {
            moveNumber = 0;
            phase = 0;
            myLastMove = opponentLastMove;
            return myLastMove;
        }
        if (moveNumber == 10) {
            moveNumber = 0;
            if (xA == midField) {
                myLastMove = 1;
            } else if (xB == midField) {
                myLastMove = 2;
            } else {
                myLastMove = 3;
            }
            return myLastMove;
        }
        if (xA == maxField) {
            myLastMove = 1;
        } else if (xB == maxField) {
            myLastMove = 2;
        } else {
            myLastMove = 3;
        }
        return myLastMove;
    }

    /**
     * @return
     */
    @Override
    public String getEmail() {
        return "d.ayazbaev@innopolis.university";
    }
}
