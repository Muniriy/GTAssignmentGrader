//package Agent;
package com.company;

import java.util.Random;

public class RishatMaksudovCode implements Player {
    @Override
    public void reset() {
        return;
    }

    @Override
    public int move(int opponentLastMove, int xA, int xB, int xC) {
        int max = Math.max(xA, Math.max(xB, xC));
        Random rn = new Random();

        if (xA == max && xB == max && xC == max)
            return rn.nextInt(3) + 1;
        if (xA == max && xB == max)
            return rn.nextBoolean() ? 1 : 2;
        if (xB == max && xC == max)
            return rn.nextBoolean() ? 1 : 3;
        if (xA == max && xC == max)
            return rn.nextBoolean() ? 2 : 3;

        if (xA == max)
            return 1;
        else if (xB == max)
            return 2;
        else return 3;
    }

    @Override
    public String getEmail() {
        return "r.maksudov@innopolis.ru";
    }
}
