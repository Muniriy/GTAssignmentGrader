package com.company;

public class KamilyaTimchenkoCode implements Player {
    @Override
    public void reset() {}

    double f(int x) {
        return (10 * Math.exp(x)) / (1 + Math.exp(x));
    }

    @Override
    public int move(int opponentLastMove, int xA, int xB, int xC) {
        double[] possiblePayoffs = {f(xA) - f(0), f(xB) - f(0), f(xC) - f(0)};

        int indexOfMax = -1;
        double max = -1;

        for (int i = 0; i < possiblePayoffs.length; i++) {
            if (possiblePayoffs[i] > max) {
                max = possiblePayoffs[i];
                indexOfMax = i;
            }
        }

        return indexOfMax + 1;
    }

    @Override
    public String getEmail() {
        return "k.timchenko@innopolis.ru";
    }
}
