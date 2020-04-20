package com.company;

import java.util.ArrayList;
import java.util.Random;

public class AnastasiiaGromovaCode implements Player {
    @Override
    public void reset() {

    }

    @Override
    public int move(int opponentLastMove, int xA, int xB, int xC) {
        ArrayList<Integer> maxes = new ArrayList<>();
        Random random = new Random();

        int max = Math.max(Math.max(xA, xB), xC);
        if (xA == max) maxes.add(1);
        if (xB == max) maxes.add(2);
        if (xC == max) maxes.add(3);
        return maxes.get(random.nextInt(maxes.size()));
    }

    @Override
    public String getEmail() {
        return "a.gromova@innopolis.ru";
    }
}
