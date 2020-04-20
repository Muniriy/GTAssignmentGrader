package com.company;


import java.util.Random;

public class RuslanSabirovCode implements Player { // Probablistic X agent
    Random random = new Random();

    private boolean isNotEmpty(int choice, int xA, int xB, int xC) {
        if (choice == 1)
            return xA > 0;
        if (choice == 2)
            return xB > 0;
        if (choice == 3)
            return xC > 0;
        return false;
    }

    @Override
    public void reset() {
        this.random = new Random();
    }

    @Override
    public int move(int opponentLastMove, int xA, int xB, int xC) {
        if (xA + xB + xC == 0)
            return random.nextInt(3) + 1;

        double[] proportion = new double[3];
        proportion[0] = 1. * xA / (xA + xB + xC);
        proportion[1] = 1. * xB / (xA + xB + xC);
        proportion[2] = 1. * xC / (xA + xB + xC);
        double prob = Math.random();
        if (prob < proportion[0])
            return 1;
        if (prob < proportion[1])
            return 2;
        return 3;
    }

    @Override
    public String getEmail() {
        return "r.sabirov@innopolis.ru";
    }
}