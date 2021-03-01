package gametheory.assignment2.students2020;

import gametheory.assignment2.Player;

public class IliaProkopevCode implements Player {
    public double score = 0;

    @Override
    public String getEmail() {
        return "i.prokopev@innopolis.ru";
    }

    @Override
    public void reset() {
        this.score = 0;
    }

    @Override
    public int move(int opponentLastMove, int xA, int xB, int xC) {
        int result = 0;
        if(xA >= xB && xA >= xC)
            result = 1;
        if(xB >= xA && xB >= xC)
            result = 2;
        if(xC >= xA && xC >= xB)
            result = 3;
        return result;
    }
}