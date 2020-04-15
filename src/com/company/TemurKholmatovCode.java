package com.company;

import java.util.Random;

/**
 * The player always moves to a field with the maximum $X$ value. In case there are several choices with maximal $X$,
 * it selects a random one of them.
 */
public class TemurKholmatovCode implements Player {
    Random random;

    public TemurKholmatovCode() {
        reset();
    }

    @Override
    public void reset() {
        random = new Random();
    }

    @Override
    public String getEmail() {
        return "t.kholmatov@innopolis.ru";
    }

    private int randomChoiceBetweenTwo(int choice1, int choice2) {
        double randDouble = random.nextDouble();
        if (randDouble < 0.5) {
            return choice1;
        } else {
            return choice2;
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

    @Override
    public int move(int opponentLastMove, int xA, int xB, int xC) {
        return bestChoice(xA, xB, xC);
    }
}
