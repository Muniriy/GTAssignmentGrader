package gametheory.assignment2.students2021;

import gametheory.assignment2.Player;

/**
 * The KirillFedoseevCode class provides the best achieved strategy for the Game Theory Assigment 2 competition.
 * It is a cooperative player with a trust limit.
 * If a player's opponent overflow this trust limit by not cooperating,
 * the player will switch to best possible local strategy which is based on maximising its local payoff.
 */
public class KirillFedoseevCode implements Player {
    private final int MAX_DEFECTIONS; // max allowed defections from the opponent (aka trust limit)
    private final int FARMING_ROUNDS; // max number of farming rounds to cooperate and wait on field with no food
    private final double SCALING_FACTOR; // scaling constant for choosing probabilities
    private int lastMove; // this player last move
    private int i; // index of the current move
    private int defections; // number of recorded "defections" from the opponent
    private int farmingMove; // move that players agreed to cooperate and wait on for N rounds

    /**
     * Initializes the player with the best found parameters.
     * MAX_DEFECTIONS = 4, FARMING_ROUNDS = 22, SCALING_FACTOR = 4.8
     */
    public KirillFedoseevCode() {
        this(4, 22, 4.8);
    }

    /**
     * Initializes the player with the best found parameters.
     *
     * @param MAX_DEFECTIONS max allowed defections from the opponent (aka trust limit)
     * @param FARMING_ROUNDS max number of farming rounds to cooperate and wait on field with no food
     * @param SCALING_FACTOR scaling constant for choosing probabilities
     */
    public KirillFedoseevCode(int MAX_DEFECTIONS, int FARMING_ROUNDS, double SCALING_FACTOR) {
        this.MAX_DEFECTIONS = MAX_DEFECTIONS;
        this.FARMING_ROUNDS = FARMING_ROUNDS;
        this.SCALING_FACTOR = SCALING_FACTOR;
    }

    /**
     * Erases all agent fields that remained from the previous game.
     */
    @Override
    public void reset() {
        farmingMove = lastMove = defections = i = 0;
    }

    /**
     * This method chooses the move to make on the current round.
     * It is a simple wrapper on top of the makeMove method to keep track of the moves history and round number.
     *
     * @param opponentLastMove the last move of the opponent
     *                         varies from 0 to 3
     *                         (0 – if this is the first move)
     * @param xA               the argument X for a field A
     * @param xB               the argument X for a field B
     * @param xC               the argument X for a field C
     * @return the desired player move, 1 for A, 2 for B, 3 for C
     */
    @Override
    public int move(int opponentLastMove, int xA, int xB, int xC) {
        i++;
        return lastMove = makeMove(opponentLastMove, xA, xB, xC);
    }

    /**
     * Tells player's author IU email address.
     *
     * @return email address of the author.
     */
    @Override
    public String getEmail() {
        return "k.fedoseev@innopolis.university";
    }

    /**
     * This method chooses the move to make on the current round.
     *
     * @param opponentLastMove the last move of the opponent
     *                         varies from 0 to 3
     *                         (0 – if this is the first move)
     * @param xA               the argument X for a field A
     * @param xB               the argument X for a field B
     * @param xC               the argument X for a field C
     * @return the desired player move, 1 for A, 2 for B, 3 for C
     */
    private int makeMove(int opponentLastMove, int xA, int xB, int xC) {
        // if opponent have not exceed the trust limit, then try to cooperate with him
        if (defections < MAX_DEFECTIONS) {
            int cooperativeMove = makeCooperativeMove(opponentLastMove);
            if (cooperativeMove > 0) {
                return cooperativeMove;
            }
        }

        // otherwise, just make a locally best move
        return makeLocallyBestMove(xA, xB, xC);
    }

    /**
     * This method makes a locally best move based on a probability heuristic.
     * If gains for all 3 fields are equal, then the strategy will uniformly select one of the fields.
     * The more the difference between some gains the more probability there is to choose the higher gain.
     * For example, for xA = 2, xB = 3, xC = 4, gains are  3.81, 4.53, 4.82 respectfully.
     * The probabilities to choose them will be then 0.6%, 19.5%, 79.9%.
     *
     * @param xA the argument X for a field A
     * @param xB the argument X for a field B
     * @param xC the argument X for a field C
     * @return the locally best move in the current situation
     */
    private int makeLocallyBestMove(int xA, int xB, int xC) {
        double probA = prob(xA, xA, xB, xC); // probability to choose A
        double probB = prob(xB, xA, xB, xC); // probability to choose B
        double r = Math.random();
        return 1 + (r > probA ? 1 : 0) + (r > probA + probB ? 1 : 0);
    }

    /**
     * This method tries to cooperate with the other player and makes a move assuming that opponent will also cooperate back.
     * Cooperation is done by simply holding the same field at the game start, for some number of rounds.
     * Then, the player tries to synchronize with the opponent and wait until
     * all fields will have approximately the same amount of food.
     *
     * @param opponentLastMove the last move of the opponent
     * @return the move suggested for cooperation strategies (0 if can't follow the cooperative strategy)
     */
    private int makeCooperativeMove(int opponentLastMove) {
        // on the first move, nothing can be analyzed, so no cooperative move can be made
        if (i == 1) {
            return 0;
        }

        // Idea for the first FARMING_ROUNDS is to hold on the same field with the opponent in order to
        // farm food on the remaining fields.
        // For that player should agree on the field to stay on.
        // It is assumed that a smart opponent might follow the similar strategy here.
        if (i <= FARMING_ROUNDS) {
            // if player was lucky on the last round, and opponent chose the same field,
            // then the player simply continues to choose that particular filed again.
            if (lastMove == opponentLastMove) {
                return farmingMove = lastMove;
            }

            // otherwise, increase the defection count and retry again on the next round
            defections++;
            return 0;
        }

        // at this point, we should have x values something like 0, 20, 20
        int otherMove1 = farmingMove == 1 ? 2 : 1;
        int otherMove2 = 6 - farmingMove - otherMove1; // 6 = 1 + 2 + 3;

        // Idea for the block of rounds is to achieve x values something like 10, 10, 10
        if (i <= FARMING_ROUNDS * 1.5) {
            // if player was unlucky on the last round, increase the defection count and retry again on the next round
            if (lastMove == opponentLastMove) {
                defections++;
                return 0;
            }

            // among 2 high spots, choose the one that was not chosen on the last round
            return lastMove == otherMove1 ? otherMove2 : otherMove1;
        }

        return 0;
    }

    /**
     * This methods estimates a probability to choose the value x out of xA, xB, xC values available.
     *
     * @param x  value to choose, should be equal to one of xA, xB, xC
     * @param xA option value 1
     * @param xB option value 2
     * @param xC option value 3
     * @return the suggested probability
     */
    private double prob(int x, int xA, int xB, int xC) {
        return Math.exp(gain(x) * SCALING_FACTOR) / (
                Math.exp(gain(xA) * SCALING_FACTOR) +
                        Math.exp(gain(xB) * SCALING_FACTOR) +
                        Math.exp(gain(xC) * SCALING_FACTOR)
        );
    }

    /**
     * Calculates the sigmoid function given in the assignment.
     *
     * @param x sigmoid function X argument
     * @return the function value
     */
    private double sigmoid(int x) {
        double e = Math.exp(x);
        return 10 * e / (1 + e);
    }

    /**
     * Calculates the gain/food for the particular field value X.
     *
     * @param x value X from one of the fields
     * @return the available food amount in that field
     */
    private double gain(int x) {
        return sigmoid(x) - sigmoid(0);
    }
}
