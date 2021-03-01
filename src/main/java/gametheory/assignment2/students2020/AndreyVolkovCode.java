package gametheory.assignment2.students2020;

import gametheory.assignment2.Player;

import java.util.ArrayList;
import java.util.Random;

public class AndreyVolkovCode implements Player {

    private int[] lastFields;

    private boolean atLeastTwoZeroes(int[] fields) {
        int zeroes = 0;
        for (int i = 0; i < fields.length; i++) {
            if (fields[i] == 0) zeroes++;
        }
        return zeroes >= 2;
    }

    private ArrayList<Integer> getMaximums(int[] fields) {
        ArrayList<Integer> indices = new ArrayList<Integer>();
        int maxValue = -1;
        for (int i = 0; i < fields.length; i++) {
            if (fields[i] > maxValue) {
                maxValue = fields[i];
                indices.clear();
                indices.add(i + 1);
            } else if (fields[i] == maxValue) {
                indices.add(i + 1);
            }
        }
        return indices;
    }

    private boolean isOpponentGreedy(int[] fields, int opponentLastMove) {
        return getMaximums(fields).get(0) == lastFields[opponentLastMove - 1];
    }

    private int randomMaximum(int[] fields) {
        ArrayList<Integer> maximums = getMaximums(fields);
        Random random = new Random();
        return maximums.get(random.nextInt(maximums.size()));
    }

    private int secondMaximum(int[] fields) {
        int first = 0;
        int second = 0;
        for (int i = 0; i < fields.length; i++) {
            if (fields[i] > fields[first]) {
                second = first;
                first = i;
            } else if (fields[i] > fields[second] && fields[i] < fields[first]) {
                second = i;
            }
        }
        return second + 1;
    }

    private boolean severalMaximums(int[] fields) {
        return getMaximums(fields).size() > 1;
    }

    private int chooseField(int[] fields, int opponentLastMove) {
        if (atLeastTwoZeroes(fields)
                || severalMaximums(fields)
                || opponentLastMove == 0
                || opponentLastMove != 0 && !isOpponentGreedy(fields, opponentLastMove)
                || lastFields != null && severalMaximums(lastFields)
        ) {
            return randomMaximum(fields);
        }
        return secondMaximum(fields);
    }

    public int move(int opponentLastMove, int xA, int xB, int xC) {
        int[] fields = {xA, xB, xC};
        int field = chooseField(fields, opponentLastMove);
        lastFields = fields;
        return field;
    }

    public void reset() {
        lastFields = null;
    }

    public String getEmail() {
        return "an.volkov@innopolis.ru";
    }
}
