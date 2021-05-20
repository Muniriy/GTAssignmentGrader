package gametheory.assignment2.students2020;

import gametheory.assignment2.Player;

import java.util.NavigableMap;
import java.util.Random;
import java.util.TreeMap;

public class DariaVaskovskayaCode implements Player {
    public String strategy;
    public int id;

    public DariaVaskovskayaCode() {
        this.reset();
        this.strategy = "Move with probability by formula";
        this.id = 0;
    }

    public DariaVaskovskayaCode clone() {
        DariaVaskovskayaCode player = new DariaVaskovskayaCode();
        player.strategy = this.strategy;
        return player;
    }

    public void reset() {
        this.recentOpponentMaxMoves = 0;
        this.prevFieldValues = new int[]{0, 0, 0};
    }

    public String getEmail(){
        return "d.vaskovskaya@innopolis.ru";
    }

    public int move(int opponentLastMove, int xA, int xB, int xC) {
        int move = 0;
        int[] field_scores = {xA, xB, xC};

        switch (this.strategy) {
            case "Always max":
                move = getMaxIndex(field_scores) + 1;
                break;
            case "Always min":
                move = getMinIndex(field_scores) + 1;
                break;
            case "Greedily move with probability by formula":
                return this.moveWithProbability(
                        this.getFieldScore(xA),
                        this.getFieldScore(xB),
                        this.getFieldScore(xC)
                );
            case "Move with probability by formula":
                double a = this.getFieldScore(xA);
                double b = this.getFieldScore(xB);
                double c = this.getFieldScore(xC);
                return this.moveWithProbability(a * a, b * b, c * c);

            case "Copycat":
                if (opponentLastMove == 0){
                    return 1;
                }
                return copyLastStrategy(opponentLastMove, xA, xB, xC);

            case "Move with probability by X":
                return this.moveWithProbability(xA, xB, xC);

        }

        return move;
    }

    private int recentOpponentMaxMoves = 0;
    private int[] prevFieldValues;


    /**
     *
     * @param a a field score
     * @param b b field score
     * @param c c field score
     * @return move according to the provided scores
     */
    private int moveWithProbability(double a, double b, double c) {
        double sum = a + b + c;
        if (sum == 0) {
            double uniform = 1.0 / 3;
            return new RandomCollection<Integer>()
                    .add(uniform, 1)
                    .add(uniform, 2)
                    .add(uniform, 3)
                    .next();
        }
        double aProb = 100 * a / sum;
        double bProb = 100 * b / sum;
        double cProb = 100 * c / sum;
        return new RandomCollection<Integer>()
                .add(aProb, 1)
                .add(bProb, 2)
                .add(cProb, 3)
                .next();
    }


    /**
     * Get field score according to the formula provided in the assignment
     * @param x field X
     * @return field score
     */
    private double getFieldScore(double x) {
        double result = (10 * java.lang.Math.exp(x)) / (1 + java.lang.Math.exp(x));
        double result0 = (10 * java.lang.Math.exp(0)) / (1 + java.lang.Math.exp(0));
        return result - result0;
    }


    /**
     * Copycat: The classical one is better known in game theory as Tit For Tat. It was created by Anatol Rapoport
     * in 1980, for Robert Axelrod's game theory tournament.Tit for tat is a game-theory strategy in which each
     * participant mimics the action of their opponent after cooperating in the first round.The implemented copycat does
     * not just copy the previous opponent's move, it analyzes opponent's last move in terms of maximum, minimum or middle
     * (nor maximum, nor minimum). And then, makes the same move (maximum, minimum or middle). If it can not copy the
     * opponent's strategy on last move, then it chooses field A.
     * @param opponentLastMove
     * @param xA
     * @param xB
     * @param xC
     * @return copycat move
     */
    private int copyLastStrategy(int opponentLastMove, int xA, int xB, int xC) {

        int[] currentValues = new int[]{xA, xB, xC};
        if (prevFieldValues[this.getMaxIndex(prevFieldValues)] == prevFieldValues[opponentLastMove-1]) {
            this.prevFieldValues = new int[]{xA, xB, xC};
            return getMaxIndex(currentValues) + 1;
        }
        else if (prevFieldValues[this.getMinIndex(prevFieldValues)] == prevFieldValues[opponentLastMove-1]){
            this.prevFieldValues = new int[]{xA, xB, xC};
            return this.getMinIndex(currentValues)+1;
        }
        else{
            this.prevFieldValues = new int[]{xA, xB, xC};
            return this.mex(new int[]{this.getMaxIndex(currentValues), this.getMinIndex(currentValues)});
        }
    }

    private int getMaxIndex(int[] numbers) {
        int maxValue = numbers[0];
        int maxIndex = 0;
        for (int i = 1; i < numbers.length; i++) {
            if (numbers[i] > maxValue) {
                maxValue = numbers[i];
                maxIndex = i;
            }
        }
        return maxIndex;
    }

    private int getMinIndex(int[] numbers) {
        int minValue = numbers[0];
        int minIndex = 0;
        for (int i = 1; i < numbers.length; i++) {
            if (numbers[i] < minValue) {
                minValue = numbers[i];
                minIndex = i;
            }
        }
        return minIndex;
    }

    /**
     * Provides functionality for the weighted random choice agents
     * @param <E>
     */
    private static class RandomCollection<E> {
        private final NavigableMap<Double, E> map = new TreeMap<Double, E>();
        private final Random random;
        private double total = 0;

        public RandomCollection() {
            this(new Random());
        }

        public RandomCollection(Random random) {
            this.random = random;
        }

        public RandomCollection<E> add(double weight, E result) {
            if (weight <= 0) return this;
            total = total + weight;
            map.put(total, result);
            return this;
        }

        public E next() {
            double value = random.nextDouble() * total;
            return map.higherEntry(value).getValue();
        }
    }

    /**
     * Searches for the value in the middle (needed for the copycat strategy)
     * @param values
     * @return copycat move, copying last opponent "middle" strategy. If it is not possible to find "middle" move, return 1
     */
    private int mex(int[] values) {
        int i = 1;
        while (true) {
            boolean ok = true;
            for (int value : values) {
                if (value == i) {
                    ok = false;
                    break;
                }
            }
            if (ok) {
                return i;
            }
            i++;
        }
    }
}

