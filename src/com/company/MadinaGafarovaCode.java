package com.company;

public class MadinaGafarovaCode implements Player{

    public MadinaGafarovaCode() {}

    @Override
    public void reset() {}

    @Override
    public String getEmail() {
        return "m.gafarova@innopolis.ru";
    }

    @Override
    public int move(int opponentLastMove, int xA, int xB, int xC) {
        return what_max(xA, xB, xC);
    }

    /**
     * @param xA Field A's score
     * @param xB Field B's score
     * @param xC Filed C's score
     * @return maximum Field number [1-A; 2-B, 3-C]
     */
    private int what_max(int xA, int xB, int xC) {
        int max = Math.max(Math.max(xA, xB), xC);
        if (xA == max)
            return 1;
        if (xB == max)
            return 2;
        return 3;
    }

}
