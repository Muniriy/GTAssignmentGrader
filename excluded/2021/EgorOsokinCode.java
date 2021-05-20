package gametheory.assignment2.students2021;

import gametheory.assignment2.Player;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

//interface Player {
//
//    /**
//     * This method is called to reset the agent before the match
//     * with another player containing several rounds
//     */
//    void reset();
//
//    /**
//     * This method returns the move of the player based on
//     * the last move of the opponent and X values of all fields.
//     * Initially, X for all fields is equal to 1 and last opponent
//     * move is equal to 0
//     *
//     * @param opponentLastMove the last move of the opponent
//     *                         varies from 0 to 3
//     *                         (0 – if this is the first move)
//     * @param xA               the argument X for a field A
//     * @param xB               the argument X for a field B
//     * @param xC               the argument X for a field C
//     * @return the move of the player can be 1 for A, 2 for B
//     * and 3 for C fields
//     */
//    int move(int opponentLastMove, int xA, int xB, int xC);
//
//    /**
//     * This method returns your IU email
//     *
//     * @return your email
//     */
//    String getEmail();
//}

public class EgorOsokinCode implements Player {
    Integer MAX_COLLISIONS = 5;
    /*
     this is calculated with the following reasons behind the algorithm:
     the t=4 is better than t=6 when N-X < 230 where
        N - number of overall rounds
        X - number of rounds spent for the cooperation
      for this, I calculated difference between f(6)-f(4) and f(4), which was nearly 63,
      and as the difference between agents on the long distance is calculated as (N-x)/2 * (f(6)-f(4)),
      got the number 126 for the N-X low bound. Hopefully, the tournament will be run with higher number
      of rounds
     */
    Integer THRESHOLD_FOR_EATING = 6;

    Boolean isCooperation = true;
    Boolean approvedCooperation = false;
    Integer collisionNumber = 0;

    Integer myLastMove = -1;
    Integer twicelyLastMove = -1;

    Integer eatingField = -1;
    Integer bufferField = -1;
    Integer otherEatingField = -1;

    /**
     * a void method reset() which will be called in order to ‘reset’ the agent before the match with
     * another player containing several rounds
     */
    @Override
    public void reset() {
        this.isCooperation = true;
        this.approvedCooperation = false;
        this.collisionNumber = 0;
        this.myLastMove = -1;
    }

    /**
     * Logic which should be done to remember new move
     */
    private void rememberMove(int currentMove) {
        this.twicelyLastMove = this.myLastMove;
        this.myLastMove = currentMove;
    }

    /**
     * calculates the move with random distribution
     */
    private int randomMove() {
        int currentMove = (new Random()).nextInt(3) + 1;
        this.rememberMove(currentMove);
        return currentMove;
    }

    /**
     * an integer method move() returning the move which is given the last opponent move (0 – if this
     * is the first move), and the current X values for the three fields. The move() method returns 1 for
     * A, 2 for B and 3 for C fields
     *
     * @param opponentLastMove the last move of the opponent
     *                         varies from 0 to 3
     *                         (0 – if this is the first move)
     * @param xA               the argument X for a field A
     * @param xB               the argument X for a field B
     * @param xC               the argument X for a field C
     * @return the next move
     */
    @Override
    public int move(int opponentLastMove, int xA, int xB, int xC) {
        List<Integer> xs = Arrays.asList(-1, xA, xB, xC);

        if (opponentLastMove == 0) {
            return this.randomMove();
        }

        // now knows that it's second turn or later
        if (!isCooperation) {
            return this.fallbackMove(opponentLastMove, xA, xB, xC);
        }

        if (approvedCooperation) {
            if (opponentLastMove == this.eatingField) {
                // opponent stepped on my field
                this.isCooperation = false;
                return this.move(opponentLastMove, xA, xB, xC);
            }

            if (this.myLastMove.equals(this.eatingField)) {
                this.rememberMove(this.bufferField);
                return this.bufferField;
            }

            if (this.myLastMove.equals(this.bufferField)) {
                if (xs.get(this.eatingField) >= this.THRESHOLD_FOR_EATING) {
                    this.rememberMove(this.eatingField);
                    return this.eatingField;
                }
                this.rememberMove(this.bufferField);
                return this.bufferField;
            }
        }

        // not approved cooperation
        if (this.myLastMove.equals(opponentLastMove)) {
            // collision
            this.collisionNumber += 1;
            if (this.collisionNumber > this.MAX_COLLISIONS) {
                this.isCooperation = false;
                return this.fallbackMove(opponentLastMove, xA, xB, xC);
            }

            return this.randomMove();
        }

        // approve cooperation
        this.approvedCooperation = true;
        this.eatingField = this.myLastMove;
        this.otherEatingField = opponentLastMove;
        this.bufferField = 6 - this.eatingField - this.otherEatingField;
        this.rememberMove(this.bufferField);
        return this.bufferField;
    }

    /**
     * Fallback strategy for the case of no cooperation
     */
    private int fallbackMove(int opponentLastMove, int xA, int xB, int xC) {
        if (opponentLastMove == 0) {
            return 1;
        }
        return Utils.choiceRandom(
                Utils.getHighIndices(Arrays.asList(xA, xB, xC))
        );
    }

    /**
     * @return my Innopolis email
     */
    @Override
    public String getEmail() {
        return "e.osokin@innopolis.university";
    }
}
