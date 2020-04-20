package com.company;


// Weighted Random Player
public class AmirSubaevCode implements Player {
    private static int getRandomNumberInRange(int max) {
        return (int) (Math.random() * (max + 1));
    }

    @Override
    public void reset() {

    }

    @Override
    public String getEmail() {
        return "a.subaev@innopolis.ru";
    }

    @Override
    public int move(int opponentLastMove, int xA, int xB, int xC) {
        // add more weight to larger field
        float a = 1, b = 1, c = 1;
        int maxMulti = 20, midMulti = 5;
        if (xA >= xB && xA >= xC) {
            a = maxMulti;
            if (xB > xC) {
                b = midMulti;
            } else {
                c = midMulti;
            }
        } else if (xB >= xA && xB >= xC) {
            b = maxMulti;
            if (xA > xC) {
                a = midMulti;
            } else {
                c = midMulti;
            }
        } else {
            c = maxMulti;
            if (xA > xB) {
                a = midMulti;
            } else {
                b = midMulti;
            }
        }

        int sum = (int) (a * xA + b * xB + c * xC);
        int field = getRandomNumberInRange(sum);
        if (field < (int) (a * xA)) {
            return 1;
        } else if (field < (int) (a * xA + b * xB)) {
            return 2;
        } else {
            return 3;
        }
    }
}

