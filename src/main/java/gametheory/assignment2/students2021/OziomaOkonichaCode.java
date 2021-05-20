/**
 * Title: Game Theory Assignment 2
 * Name: Okonicha Ozioma Nenubari
 * Group: B18-SE-01
 */

package gametheory.assignment2.students2021;

import gametheory.assignment2.Player;

import java.util.Random;

public class OziomaOkonichaCode implements Player {
    /**
     * Method to reset the agent before the match with another player
     */
    @Override
    public void reset() {

    }

    /**
     * Method to return the move choice of the player- random except the opponent's last move
     *
     * @param opponentLastMove the last move of the opponent
     *                         varying from 0 to 3
     * @param xA               the argument X for field A
     * @param xB               the argument X for field B
     * @param xC               the argument X for field C
     * @return the move of the player- 1 for A, 2 for B
     * and 3 for C fields
     */
    @Override
    public int move(int opponentLastMove, int xA, int xB, int xC) {
        Random rand = new Random();
        int number = 3;

        /* generate random values from 1-3 */
        int randomMove = rand.nextInt(number) + 1;

        /* while the random move generated is the same as the opponent's
         * last move, generate a new random move */
        while (randomMove == opponentLastMove) {
            randomMove = rand.nextInt(number) + 1;
        }

        return randomMove;
    }

    /**
     * Method to return my IU email
     *
     * @return email address
     */
    @Override
    public String getEmail() {
        return "o.okonicha@innopolis.university";
    }
}