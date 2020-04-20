package com.company;

import java.util.concurrent.ThreadLocalRandom;

import static java.lang.Math.exp;

public class MaximSaloCode implements Player {

    @Override
    public void reset() {
        // No reset required here
    }

    /**
     * Gets the weighted random field to go
     *
     * @param opponentLastMove opponent's last move value (0 if no last move)
     * @param xA               current value of X for field A
     * @param xB               current value of X for field B
     * @param xC               current value of X for field C
     * @return the field to move
     */
    @Override
    public int move(int opponentLastMove, int xA, int xB, int xC) {
        return randomField(calculatePayoff(xA), calculatePayoff(xB), calculatePayoff(xC));
    }

    @Override
    public String getEmail() {
        return "m.salo@innopolis.ru";
    }

    private static double calculatePayoff(int x) {
        double eToX = exp(x);
        return (10 * eToX) / (1 + eToX) - 5;
    }

    private int randomField(double pa, double pb, double pc) {
        double probSum = pa + pb + pc;
        double generated = ThreadLocalRandom.current().nextDouble(probSum);
        if (generated < pa) return 1;
        else if (generated < pa + pb) return 2;
        else return 3;
    }
}