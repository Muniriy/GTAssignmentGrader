/*
 * AlinaPaukovaCode
 *
 * This class implements an agent for the Moose that will participate in the tournament
 */
package gametheory.assignment2.students2021;

import gametheory.assignment2.Player;

public class AlinaPaukovaCode implements Player {

    private int lastMax; //number(1,2 or 3) of the field that during the previous round had the largest X
    private int lastMove; //number(1,2 or 3) of the field that my player moved in previous round

    /**
     * public constructor
     * initially sets lastMax and lastMove to 0s
     */
    public AlinaPaukovaCode() {
        lastMax = 0;
        lastMove = 0;
    }

    @Override
    public void reset() {
        lastMax = 0;
        lastMove = 0;
    }

    @Override
    public int move(int opponentLastMove, int xA, int xB, int xC) {
        int largest_X = Math.max(Math.max(xA, xB), xC); // the largest X
        int max; //number(1,2 or 3) of the field with the largest X
        //find field with largest X
        if (largest_X == xB) max = 2;
        else if (largest_X == xA) max = 1;
        else max = 3;
        int move;
        //move to random field in the first turn
        if (lastMax == 0) move = (int) (Math.random() * 3 + 1);
            //go to the last round empty field if opponent's last move was the maximum
        else if (opponentLastMove == lastMax) {
            //last round empty field is C
            if (lastMove == 1 && opponentLastMove == 2 || opponentLastMove == 1 && lastMove == 2) move = 3;
                //last round empty field is B
            else if (lastMove == 1 && opponentLastMove == 3 || opponentLastMove == 1 && lastMove == 3) move = 2;
                //last round empty field is A
            else if (lastMove == 2 && opponentLastMove == 3 || opponentLastMove == 2 && lastMove == 3) move = 1;
                //last round empty fields are B and C
            else if (lastMove == 1 && opponentLastMove == 1) {
                int m = Math.max(xB, xC);
                if (m == xB) move = 3;
                else move = 2;
            }
            //last round empty fields are A and C
            else if (lastMove == 2 && opponentLastMove == 2) {
                int m = Math.max(xA, xC);
                if (m == xA) move = 3;
                else move = 1;
            }
            //last round empty fields are A and B
            else {
                int m = Math.max(xA, xB);
                if (m == xA) move = 2;
                else move = 1;
            }
        }
        //move to the maximum if opponent's last move was not the maximum
        else move = max;
        //set lastMax and lastMove values
        lastMove = move;
        lastMax = max;
        return move;
    }

    @Override
    public String getEmail() {
        return "a.paukova@innopolis.university";
    }
}
