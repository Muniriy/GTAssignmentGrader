package com.company;

class SusannaGimaevaCode implements Player
{
    @Override
    public void reset() {
    }

    @Override
    public int move(int opponentLastMove, int xA, int xB, int xC) {
        int maxX = Math.max(Math.max(xA, xB), xC);
        if (maxX == xA) return 1;
        else if (maxX == xB) return 2;
        else return 3;
    }

    public String getEmail(){
        return "s.gimaeva@innopolis.ru";
    }
}
