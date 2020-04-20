package com.company;

import java.util.Random;

/* Greedy agent - goes for a max payoff */
public class RomanSolovevCode implements Player {

    public String getEmail(){
        return "r.solovev@innopolis.ru";
    }

    public void reset() {
    }

    public int move(int opponentLastMove, int xA, int xB, int xC) {
        int mv;

        /* First move or all fields equal -> random move */
        if ((opponentLastMove == 0) || ((xA == xB) && (xA == xC))) {
            Random random = new Random();
            mv = random.nextInt(3) + 1;

        }
        /* Else always go for largest */
        else {

            /* Find all fields with largest payoff (1 or 2 fields) */
            int[] largest = {0, 0, 0};
            if ((xA >= xB) && (xA >= xC)) {
                largest[0] = 1;
            }
            if ((xB >= xA) && (xB >= xC)) {
                largest[1] = 1;
            }
            if ((xC >= xB) && (xC >= xA)) {
                largest[2] = 1;
            }

            /* Find random field with largest payoff */
            Random pick = new Random();
            mv = pick.nextInt(3) + 1;
            while (largest[mv - 1] != 1) {
                mv = pick.nextInt(3) + 1;
            }

        }
        return mv;

    }
}