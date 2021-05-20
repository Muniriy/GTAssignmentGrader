package gametheory.assignment2.students2021;

import gametheory.assignment2.Player;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;

/**
 * Try to collide with opponent until payoff in field with middle X is around 5 (X >= 5)
 */
public class IskanderSalimzhanovCode implements Player {
    boolean seeking; // should we try to collide with opponent or not
    private Integer[] sortedLastFields = new Integer[3]; // sorted fields of last rounds at the previous round


    @Override
    public void reset() {
        sortedLastFields = new Integer[3];
    }

    //f(x) function from the problem description
    private double f(int x) {
        return 10 * Math.exp(x) / (1 + Math.exp(x));
    }

    //payoff function from the problem description
    private double payoff(int x) {
        return f(x) - f(0);
    }

    @Override
    public int move(int opponentLastMove, int xA, int xB, int xC) {
        int[] x = new int[]{xA, xB, xC};
        int currentMove;
        Integer[] sortedFields = {1, 2, 3};
        Arrays.sort(sortedFields, (f1, f2) -> -1);
        Arrays.sort(sortedFields,
                Comparator.comparingInt(field -> x[field - 1])); // sort fields by X
        seeking = x[sortedFields[1] - 1] < 5; // if there aren't two fields with X >= 5 then we should try to collide
        if (opponentLastMove == 0) {
            currentMove = 1; //start with A field
        } else {
            if (seeking) {
                if (opponentLastMove == sortedLastFields[0] || x[sortedFields[1] - 1] == 0) {
                    currentMove = sortedFields[2]; //we assume that opponent is rational and for sure won't go the field with payoff 0
                } else if (opponentLastMove == sortedLastFields[2]) {
                    currentMove = sortedFields[2];
                } else if (opponentLastMove == sortedLastFields[1]) {
                    currentMove = sortedFields[1];
                } else {
                    currentMove = sortedFields[0];
                }
            } else {
                double prob = 0.5; // with 50/50 chances decide whether to go to maximum payoff field or middle payoff field
                double randomVal = new Random().nextDouble();
                if (randomVal > prob) {
                    currentMove = sortedFields[1];
                } else {
                    currentMove = sortedFields[2];
                }
            }
        }
        sortedLastFields = sortedFields;
        return currentMove;
    }

    @Override
    public String getEmail() {
        return "i.salimzhanov@innopolis.university";
    }
}

