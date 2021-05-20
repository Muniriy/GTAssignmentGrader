/*
 * Marina Ivanova, BS18-SE-01
 *
 * 6.03.2021-12.03.2021
 *
 * Game Theory Assignment2
 *
 * The MarinaIvanovaCode class contains Moose agent code that implements
 * the Player interface and chosen for game tournament strategy
 */
package gametheory.assignment2.students20s;

import gametheory.assignment2.Player;

import java.util.Random;

/**
 * This class implements the Moose player agent following the best available
 * field with random (randomised greedy) strategy. Each step he chooses the field with the greatest
 * value of x. If there is more than one maximum the best field is chosen randomly.
 * Stores agent's last move in lastMove argument.
 *
 * @author Marina Ivanova
 * @see #lastMove
 * @see #move
 * @see #reset
 * @see #getEmail
 * @see Player
 */
public class MarinaIvanovaCode implements Player {
    /**
     * Stores this agent's last move
     */
    private int lastMove;

    /**
     * This constructor creates new instance of the MarinaIvanovaCode class and
     * reset it to be ready for the new game
     *
     * @author Marina Ivanova
     * @see #reset
     * @see MarinaIvanovaCode
     * @see Player#reset()
     */
    public MarinaIvanovaCode() {
        this.reset();
    }

    /**
     * This method returns the move of the player based on the maximum of X
     * values of all fields. If X values of the fields are equal, agent
     * chooses from them randomly to reduce possibility of moving to the
     * same field with  agent.
     *
     * @param opponentLastMove the last move of the opponent
     *                         varies from 0 to 3
     *                         (0 – if this is the first move)
     * @param xA               the argument X for a field A
     * @param xB               the argument X for a field B
     * @param xC               the argument X for a field C
     * @return the move of the player can be 1 for A, 2 for B
     * and 3 for C fields
     * @see #f
     * @see MarinaIvanovaCode
     * @see Player#move
     */
    @Override
    public int move(int opponentLastMove, int xA, int xB, int xC) {
        double[] payoffs = {0, 0, 0};
        payoffs[0] = this.f(xA) - this.f(0);    // field A possible to gain score
        payoffs[1] = this.f(xB) - this.f(0);    // field B possible to gain score
        payoffs[2] = this.f(xC) - this.f(0);    // field C possible to gain score
        int max_i = 0;

        // max of fields A and B. If values are equal, the max is chosen randomly
        if (payoffs[0] > payoffs[1]) {
            max_i = 0;
        } else if (payoffs[0] == payoffs[1]) {
            max_i = new Random().nextBoolean() ? 0 : 1;
        } else {
            max_i = 1;
        }
        // max of max(A, B) and C. If values are equal, the max is chosen randomly
        if (payoffs[2] > payoffs[max_i]) {
            max_i = 2;
        } else if (payoffs[2] == payoffs[max_i]) {
            max_i = new Random().nextBoolean() ? 2 : max_i;
        }

        // move is 1 for A, 2 for B, 3 for C
        this.lastMove = max_i + 1;
        return max_i + 1;
    }

    /**
     * This method returns the payoff of the field based on the X
     * value of the field.
     *
     * @param x the argument X for the field
     * @return the payoff calculated by formula 10 * exp(x) / (1 + exp(x))
     * @see #move
     * @see MarinaIvanovaCode
     */
    private double f(int x) {
        return 10 * Math.exp(x) / (1 + Math.exp(x));
    }

    /**
     * This method reset the agent to be ready for the next game.
     * The lastMove is set to zero.
     *
     * @author Marina Ivanova
     * @see #lastMove
     * @see MarinaIvanovaCode
     * @see Player#reset()
     */
    @Override
    public void reset() {
        this.lastMove = 0;
    }

    @Override
    public String getEmail() {
        return "m.ivanova@innopolis.university";
    }

}

