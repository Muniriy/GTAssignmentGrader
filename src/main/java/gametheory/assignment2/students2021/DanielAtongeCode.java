package gametheory.assignment2.students2021;

import gametheory.assignment2.Player;

import java.util.Random;

public class DanielAtongeCode implements Player {

    /**
     * This method is called to reset the agent before the match
     * with another player containing several rounds
     */
    @Override
    public void reset() {
    }

    /**
     * This method implements the RandomExceptOpponentLastMoveStrategy
     * where it picks vegetation other than the one picked by the
     * opponent in the previous move
     *
     * @param opponentLastMove the last move of the opponent
     *                         varies from 0 to 3
     *                         (0 – if this is the first move)
     * @param xA               the argument X for a field A
     * @param xB               the argument X for a field B
     * @param xC               the argument X for a field C
     * @return returns the move picked according to the strategy
     */
    @Override
    public int move(int opponentLastMove, int xA, int xB, int xC) {
        Random randomizer = new Random();
        int move = 1 + randomizer.nextInt(3);
        while (move == opponentLastMove) {
            move = 1 + randomizer.nextInt(3);
        }
        return move;
    }

    /**
     * Returns my mail address
     *
     * @return my ui mail
     */
    @Override
    public String getEmail() {
        return "d.atonge@innopolis.university";
    }
}


