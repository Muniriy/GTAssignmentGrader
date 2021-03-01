package gametheory.assignment2.students2020;

import gametheory.assignment2.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 * The player tries to predict the strategy of the opponent and classifies it either as a greedy or a random one.
 * First, it assumes the opponent as greedy player. Every round, the player analyzes the moves of the opponent and
 * once it makes a non-best choice, the player starts to consider the opponent as a random one.
 * However, if the opponent plays greedily in last $K$ rounds (in my implementation $K=10$), the player will start
 * considering the opponent as a greedy one, again. This logic is performed in loop. Regarding the strategy of moves:
 * \begin{itemize}
 * \item Case: the opponent is considered to be greedy. If the difference between the payoff from a best field and
 * the payoff from a field chosen with help of "weighted-random" strategy (refer to previous players) is
 * more than $\epsilon$ (in my implementation $\epsilon=1.5$) then it chooses greedily, otherwise it chooses
 * the field selected using the "weighted-random" strategy.
 * \item Case: the opponent is considered to be random. The player selects greedily.
 */
public class TemurKholmatovCode implements Player {
    private enum OpponentStrategies {GREEDY, RANDOM}

    private Random random;
    private OpponentStrategies opponentStrategy;
    private ArrayList<ArrayList<Integer>> opponentsMoveHistory;
    private int prevA, prevB, prevC;
    private int greedinessLimit;
    private double alpha;


    public TemurKholmatovCode() {
        reset();
    }

    @Override
    public void reset() {
        random = new Random();
        opponentStrategy = OpponentStrategies.GREEDY;
        prevA = prevB = prevC = 1;
        opponentsMoveHistory = new ArrayList<>();
        greedinessLimit = 10;
        alpha = 1.0;
    }

    private int randomChoiceBetweenTwo(int choice1, int choice2) {
        double randDouble = random.nextDouble();
        if (randDouble < 0.5) {
            return choice1;
        } else {
            return choice2;
        }
    }

    private int weightedRandomChoice(int xA, int xB, int xC) {
        // create weighted probabilities of fields and randomly choose one
        double denominator = xA + xB + xC + 3 * alpha;
        double ARightBorder = (xA + alpha) / denominator;
        double BRightBorder = ARightBorder + (xB + alpha) / denominator;

        double randDouble = random.nextDouble();

        if (randDouble < ARightBorder) {
            return 1;
        } else if (randDouble < BRightBorder) {
            return 2;
        } else {
            return 3;
        }
    }

    private int bestChoice(int xA, int xB, int xC) {
        if (xA == xB && xB == xC) {
            return random.nextInt(3) + 1;
        } else if (xA == xB && xA > xC) {
            return randomChoiceBetweenTwo(1, 2);
        } else if (xA == xC && xA > xB) {
            return randomChoiceBetweenTwo(1, 3);
        } else if (xB == xC && xB > xA) {
            return randomChoiceBetweenTwo(2, 3);
        } else if (xA > xB && xA > xC) {
            return 1;
        } else if (xB > xA && xB > xC) {
            return 2;
        } else {
            return 3;
        }
    }

    private boolean isNotGreedy(int opponentLastMove, int xA, int xB, int xC) {
        if (opponentLastMove != 0) {
            int[] Xs = new int[]{xA, xB, xC};
            for (int i = 0; i < 3; i++) {
                if ((i != opponentLastMove - 1) && (Xs[i] > Xs[opponentLastMove - 1])) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isGreedyLastNRounds() {
        if (opponentsMoveHistory.size() < greedinessLimit) {
            return false;
        }
        for (int i = opponentsMoveHistory.size() - 1; i>=opponentsMoveHistory.size()-greedinessLimit;i--) {
            ArrayList<Integer> arr = opponentsMoveHistory.get(i);
            if (isNotGreedy(arr.get(0), arr.get(1), arr.get(2), arr.get(3))) {
                return false;
            }
        }
        return true;
    }

    private void checkOpponentsStrategy(int opponentLastMove, int xA, int xB, int xC) {
        // check and change the opponents strategy if needed
        if (opponentLastMove != 0) {
            opponentsMoveHistory.add(new ArrayList<>(Arrays.asList(opponentLastMove, prevA, prevB, prevC)));
        }
        if (opponentStrategy == OpponentStrategies.GREEDY && isNotGreedy(opponentLastMove, prevA, prevB, prevC)) {
            opponentStrategy = OpponentStrategies.RANDOM;
        } else if (opponentStrategy == OpponentStrategies.RANDOM && isGreedyLastNRounds()) {
            opponentStrategy = OpponentStrategies.GREEDY;
        }
        prevA = xA;
        prevB = xB;
        prevC = xC;
    }

    private double getPayoff(int x) {
        if (x == 0)
            return 0.0;
        return (10.0 * Math.exp(x)) / (1.0 + Math.exp(x)) - 5.0;
    }

    private int nextStep(int xA, int xB, int xC) {
        // make a move according to the opponent's strategy
        if (opponentStrategy == OpponentStrategies.GREEDY) {
            int bestChoice = bestChoice(xA, xB, xC);
            int weightedRandomChoice = weightedRandomChoice(xA, xB, xC);
            int[] Xs = new int[]{xA, xB, xC};
            if (getPayoff(Xs[bestChoice - 1]) - getPayoff(Xs[weightedRandomChoice - 1]) > 1.5) {
                return bestChoice;
            } else {
                return weightedRandomChoice;
            }
        } else {
            return bestChoice(xA, xB, xC);
        }
    }

    @Override
    public int move(int opponentLastMove, int xA, int xB, int xC) {
        checkOpponentsStrategy(opponentLastMove, xA, xB, xC);
        return nextStep(xA, xB, xC);
    }

    @Override
    public String getEmail() {
        return "t.holmatov@innopolis.ru";
    }
}
