package gametheory.assignment2.students20s;

import gametheory.assignment2.Player;

import java.util.Random;

/**
 * Impemenation of the Player interface
 * for competition in the Assignment #2.
 *
 * @author Alexander Krivonosov
 * @version 1.0.0
 * See more detailed comments on the implementaion of the methods
 * in the report or in the testign file.
 */

public class AlexanderKrivonosovCode implements Player {

    boolean opponentIsTrusted = true;
    int myCell = 0;
    int opponentsCell = 0;
    int freeCell = 0;
    int gameFase = 1;
    int myLastMoove = 0;
    int waitingCount = 0;
    Random rand = new Random();

    @Override
    public void reset() {
        this.opponentIsTrusted = true;
        this.myCell = 0;
        this.opponentsCell = 0;
        this.freeCell = 0;
        this.gameFase = 1;
        this.myLastMoove = 0;
        this.waitingCount = 0;
    }

    @Override
    public int move(int opponentLastMove, int xA, int xB, int xC) {
        if (opponentIsTrusted) {
            if (gameFase == 1) {
                if (opponentLastMove == myLastMoove) {
                    myLastMoove = rand.nextInt(3) + 1;
                    return myLastMoove;
                } else {
                    gameFase = 2;
                    myCell = myLastMoove;
                    opponentsCell = opponentLastMove;
                    freeCell = 1 + 2 + 3 - myCell - opponentsCell;
                    waitingCount += 1;
                    myLastMoove = freeCell;
                    return freeCell;
                }
            } else if (gameFase == 2) {
                if (opponentLastMove == myCell) {
                    opponentIsTrusted = false;
                    myLastMoove = opponentsCell;
                    if (xA > xB) {
                        if (xA > xC) return 1;
                        else if (xA == xC) return rand.nextDouble() > 0.5 ? 1 : 3;
                        else return 3;
                    } else {
                        if (xB > xC) return 2;
                        else if (xB == xC) return rand.nextDouble() > 0.5 ? 2 : 3;
                        else return 3;
                    }
                }
                if (waitingCount == 7) {
                    gameFase = 3;
                    myLastMoove = myCell;
                    return myCell;
                } else {
                    waitingCount += 1;
                    myLastMoove = freeCell;
                    return freeCell;
                }
            } else {
                if (myLastMoove == myCell) {
                    myLastMoove = freeCell;
                    return freeCell;
                } else {
                    myLastMoove = myCell;
                    return myCell;
                }
            }
        } else {
            if (xA > xB) {
                if (xA > xC) return 1;
                else if (xA == xC) return rand.nextDouble() > 0.5 ? 1 : 3;
                else return 3;
            } else {
                if (xB > xC) return 2;
                else if (xB == xC) return rand.nextDouble() > 0.5 ? 2 : 3;
                else return 3;
            }
        }
    }

    @Override
    public String getEmail() {
        return "a.krivonosov@innopolis.university";
    }
}
