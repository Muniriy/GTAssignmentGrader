package gametheory.assignment2.students2021;

import gametheory.assignment2.Player;

import java.util.Arrays;
import java.util.Random;
import java.util.Vector;

/**
 * PlayerBase is an abstract implementation of the Player interface. It provides basic implemenation of Player except for the move function.
 * <p>
 * This class maintains several pieces of information that might be needed for its concrete extensions to implement the move function, such as the history of the gameplay.
 */
abstract class PlayerBase implements Player {
    /**
     * Random number generator to be used throughout the lifetime of the player
     */
    protected Random rand;

    /**
     * History of the fields' x-values; the amount of vegetation.
     */
    protected Vector<int[]> x;

    /**
     * History of both players' moves;
     */
    protected Vector<Integer> myMoves;
    protected Vector<Integer> opponentMoves;

    /**
     * Both players' current scores. It is probably also userful to include the history of scores, but it is unnecessary for our current purposes.
     */
    protected double myScore;
    protected double opponentScore;

    /**
     * Constructor, simply initializes the random number generator and resets the player.
     */
    public PlayerBase() {
        rand = new Random();
        reset();
    }

    /**
     * Determines what the next move is bases on the information present in the data members.
     *
     * @return 1, 2, or 3; the index of the field to choose for the current round.
     */
    protected abstract int move();

    /**
     * Payoff as a sigmoid function as specified in the game description.
     *
     * @param X the field's X value.
     * @return floating point number representing the payoff.
     */
    private double payoffFunction(int X) {
        return 10.0 / (1.0 + Math.exp(X)) - 5.0;
    }

    /**
     * Gives the outcome of a round given both players' moves as specified in the game description.
     *
     * @param x            the fields' X values.
     * @param myMove       1, 2, or 3; the field I chose.
     * @param opponentMove 1, 2, or 3; the field my opponent chose.
     * @return the outcome of the round.
     */
    private roundOutcome roundSimulation(int[] x, int myMove, int opponentMove) {
        roundOutcome ret = new roundOutcome();
        ret.newX = x.clone();
        {
            boolean[] occupied = {false, false, false};
            occupied[myMove - 1] = true;
            occupied[opponentMove - 1] = true;
            for (int i = 0; i < 3; i++) {
                if (occupied[i])
                    ret.newX[i] = Math.max(x[i] - 1, 0);
                else
                    ret.newX[i] = x[i] + 1;
            }
        }
        if (myMove == opponentMove) {
            ret.myPayoff = 0.0;
            ret.opponentPayoff = 0.0;
        } else {
            ret.myPayoff = payoffFunction(x[myMove - 1]);
            ret.opponentPayoff = payoffFunction(x[opponentMove - 1]);
        }
        return ret;
    }

    /**
     * Updates the data members after knowing the opponent's last move (which is the missing piece of information for simulating the previous round).
     *
     * @param opponentLastMove 1, 2, or 3; the field chosen by the opponent.
     */
    private void processOpponentLastMove(int opponentLastMove) {
        if (opponentLastMove == 0)
            return;
        opponentMoves.add(opponentLastMove);
        roundOutcome lastRoundOutcome = roundSimulation(x.lastElement(), myMoves.lastElement(),
                opponentMoves.lastElement());
        myScore += lastRoundOutcome.myPayoff;
        opponentScore += lastRoundOutcome.opponentPayoff;
    }

    /**
     * Updates the data members after knowing the current X values of the fields.
     *
     * @param x current X values of the fields.
     */
    private void processCurrentFieldX(int[] x) {
        this.x.add(x);
    }

    /**
     * Updates the data members after knowing my current move.
     *
     * @param myMove the move I just played.
     */
    private void processMyCurrentMove(int myMove) {
        myMoves.add(myMove);
    }

    @Override
    public void reset() {
        x = new Vector<int[]>();
        myMoves = new Vector<Integer>();
        opponentMoves = new Vector<Integer>();
        myScore = 0.0;
        opponentScore = 0.0;
    }

    @Override
    public int move(int opponentLastMove, int xA, int xB, int xC) {
        int[] x = {xA, xB, xC};
        processOpponentLastMove(opponentLastMove);
        processCurrentFieldX(x);
        int myMove = move();
        processMyCurrentMove(myMove);
        return myMove;
    }

    @Override
    public String getEmail() {
        return "k.ismaeel@innopolis.university";
    }

    /**
     * Simple class for holding results for one round; will be needed with the simulation below.
     */
    public class roundOutcome {
        public double myPayoff;
        public double opponentPayoff;
        public int[] newX;
    }
}

/**
 * Simplest implementation of Player; chooses fields randomly. Do not be fooled though, it is a fairly decent strategy.
 */
class RandomPlayer extends PlayerBase {
    @Override
    public int move() {
        return 1 + rand.nextInt(3);
    }
}

/**
 * Greedy player; always chooses the field with the largest vegetation.
 */
class GreedyPlayer extends PlayerBase {
    @Override
    protected int move() {
        int[] x = this.x.lastElement().clone();
        int startIndex = rand.nextInt(3);
        for (int i = 0; i < 3; i++) {
            int trialIndex = (startIndex + i) % 3;
            if (x[trialIndex] >= Math.max(x[(trialIndex + 1) % 3], x[(trialIndex + 2) % 3]))
                return trialIndex + 1;
        }
        return 0;
    }
}

/**
 * Cooperative player. If the player has a larger cumulative sum than their opponent then they voluntarily take the second largest vegetation field. Otherwise they take the largest field.
 * <p>
 * There are several variations on this strategy, such as allowing a certain amount of tolerance and a randomization, which is presented here.
 */
class FairPlayer extends PlayerBase {
    @Override
    public int move() {
        int[] x = this.x.lastElement().clone();
        int bestChoice, secondBestChoice;
        {
            Integer[] y = {0, 1, 2};
            Arrays.sort(y, (l, r) -> x[r] - x[l]);
            bestChoice = y[0] + 1;
            secondBestChoice = y[1] + 1;
        }
        if (myScore > opponentScore + 0.05)
            return secondBestChoice;
        else if (opponentScore > myScore + 0.05)
            return bestChoice;
        else {
            if (rand.nextInt(2) == 0)
                return bestChoice;
            else
                return secondBestChoice;
        }
    }
}


/**
 * Our competition entry; the RandomPlayer.
 */
public class KhaledIsmaeelCode extends RandomPlayer {
    ;
}