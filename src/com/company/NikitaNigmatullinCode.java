package com.company;

import java.util.Random;

/**
 * The RandomPlayer class. Each round the player returns random spot
 */
public class NikitaNigmatullinCode implements Player {
    @Override
    public void reset() {
    }

    /**
     * Return the random move
     * 
     * @param opponentLastMove
     * @param xA
     * @param xB
     * @param xC
     * @return
     */
    @Override
    public int move(int opponentLastMove, int xA, int xB, int xC) {
        Random r = new Random();
        return r.ints(1, 4).findFirst().getAsInt();
    }

    public String getEmail() {
        return "n.nigmatullin@innopolis.ru";
    }
}