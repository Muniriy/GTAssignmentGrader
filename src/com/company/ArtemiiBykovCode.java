package com.company;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static java.lang.Math.exp;

// NarutoUzumaki in report
public class ArtemiiBykovCode implements Player {

    private int numberOfRounds = 0;

    @Override
    public String getEmail() {
        return "ar.bykov@innopolis.ru";
    }

    /**
     * Reset player state
     */
    @Override
    public void reset() {
        numberOfRounds = 0;
    }

    /**
     * Method performs move. How choice is made you can find in report
     * @param opponentLastMove Opponent last move
     * @param xA A field value
     * @param xB B field value
     * @param xC C field value
     * @return Move
     */
    @Override
    public int move(int opponentLastMove, int xA, int xB, int xC) {
        Map<Integer, Integer> moveToX = new HashMap<>();
        moveToX.put(1, xA);
        moveToX.put(2, xB);
        moveToX.put(3, xC);
        Map<Integer, Integer> xToMove = new HashMap<>();
        xToMove.put(xA, 1);
        xToMove.put(xB, 2);
        xToMove.put(xC, 3);

        int max = xA >= xB && xA >= xC ? 1 : xB >= xC ? 2 : 3;
        if (numberOfRounds++ <= 2) {
            return max;
        } else {
            return xToMove.get(randomField(moveToX.get(max), moveToX.get(opponentLastMove)));
        }
    }

    /**
     * Method calculates sigmoid value
     * @param x sigmoid argument
     * @return sigmoid value
     */
    private static double calculateF(int x) {
        double eToX = exp(x);
        return (10 * eToX) / (1 + eToX);
    }

    /**
     * Method picks random field using weighted random based on sigmoid value
     * @param x1 First value
     * @param x2 Second value
     * @return Choice
     */
    private int randomField(int x1, int x2) {
        double p1 = calculateF(x1);
        double p2 = calculateF(x2);
        double probSum = p1 + p2;
        double generated = new Random().nextDouble() * probSum;
        if (generated < x1) return x1;
        else return x2;
    }
}
