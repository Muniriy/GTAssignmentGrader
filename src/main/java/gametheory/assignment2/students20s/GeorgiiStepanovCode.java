package gametheory.assignment2.students20s;

import gametheory.assignment2.Player;

import java.util.Random;

public class GeorgiiStepanovCode implements Player {

    // Random is used for probability moves:
    private Random random;

    public GeorgiiStepanovCode() {
        random = new Random();
    }

    /**
     * This method is called to reset the agent before the match
     * with another player containing several rounds
     */
    @Override
    public void reset() {
        random = new Random();
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
        int move;

        // Divide the interval (0, 1) into three parts:
        float p1 = (float) xA / (xA + xB + xC);
        float p2 = p1 + (float) xB / (xA + xB + xC);

        // Pick a random point between 0 and 1:
        float chance = random.nextFloat();

        // Agent chooses the move accordingly with the region
        // where the random chosen point is lying:
        if (chance < p1) {
            move = 1;
        } else if (chance < p2) {
            move = 2;
        } else {
            move = 3;
        }

        return move;
    }

    @Override
    public String toString() {
        return "GeorgiiStepanov";
    }

    // Returns my IU-mail address:
    @Override
    public String getEmail() {
        return "g.stepanov@innopolis.university";
    }
}