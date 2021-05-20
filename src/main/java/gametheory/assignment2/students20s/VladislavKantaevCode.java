package gametheory.assignment2.students20s;

import gametheory.assignment2.Player;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;

/**
 * Uses the same logic as WeightedMaximumPlayer
 */
public class VladislavKantaevCode implements Player {
    private static final int FIELDS_COUNT = 3;
    private final int[] weights = new int[FIELDS_COUNT];
    private Random random = new Random();

    public VladislavKantaevCode() {
        weights[0] = 1;
        weights[1] = 5;
        weights[2] = 10;
    }

    @Override
    public void reset() {
        random = new Random();
    }

    @Override
    public int move(int opponentLastMove, int xA, int xB, int xC) {
        ArrayList<Integer> moves = new ArrayList<>();
        moves.add(1);
        moves.add(2);
        moves.add(3);
        moves.sort(Comparator.comparingInt(m -> getX(m, xA, xB, xC)));

        return getMove(moves);
    }

    private int getX(int fieldNumber, int xA, int xB, int xC) {
        switch (fieldNumber) {
            case 1:
                return xA;
            case 2:
                return xB;
            case 3:
                return xC;
            default:
                throw new IllegalStateException("Invalid field number.");
        }
    }

    private int getMove(ArrayList<Integer> moves) {
        int weightsSum = getWeightsSum();
        if (weightsSum == 0) return getRandomMove();

        double value = getRandomValue01();
        double cumulativeProbability = 0;

        for (int i = 0; i < FIELDS_COUNT; i++) {
            cumulativeProbability += (double) weights[i] / weightsSum;
            if (cumulativeProbability >= value) {
                return moves.get(i);
            }
        }

        return getRandomMove();
    }

    private double getRandomValue01() {
        return random.nextDouble();
    }

    private int getRandomMove() {
        return random.nextInt(FIELDS_COUNT) + 1;
    }

    private int getWeightsSum() {
        int sum = 0;

        for (int weight : weights) {
            sum += weight;
        }

        return sum;
    }

    @Override
    public String getEmail() {
        return "v.kantaev@innopolis.university";
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
