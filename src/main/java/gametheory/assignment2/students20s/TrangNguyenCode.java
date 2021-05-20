package gametheory.assignment2.students20s;

import gametheory.assignment2.Player;

/**
 * This class implements the interface "Player"
 * from "gametheory.assignment2" package, where its next move
 * is the result of random weighted selection between 3 fields.
 */
public class TrangNguyenCode implements Player {

    /**
     * This method is called to reset the agent before the match
     * with another player containing several rounds
     */
    public void reset() {
        return;
    }

    /**
     * This method returns the weighted random move of the player.
     * (the higher x value, the more likely it will be selected)
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
    public int move(int opponentLastMove, int xA, int xB, int xC) {
        double randomNumber = Math.random();
        double weightedA = (xA + 1.0) / (xA + xB + xC + 3.0);
        double weightedB = (xB + 1.0) / (xA + xB + xC + 3.0);
        if (randomNumber < weightedA) {
            return 1;
        } else if (randomNumber < (weightedA + weightedB)) {
            return 2;
        } else {
            return 3;
        }
    }

    /**
     * This method returns student's IU email: t.nguen@innopolis.university
     *
     * @return t.nguen@innopolis.university
     */
    public String getEmail() {
        return "t.nguen@innopolis.university";
    }
}
