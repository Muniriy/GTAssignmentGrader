package com.company;
import java.util.Random;

public class TimerlanNasyrovCode implements Player {

    @Override
    public void reset() {
    }

    @Override
    public int move(int opponentLastMove, int xA, int xB, int xC) {
        Random rand = new Random();
        int index = rand.nextInt(xA + xB + xC);
        if (index < xA){
            return 1;
        } else if (index < xB) {
            return 2;
        } else {
            return 3;
        }
    }

    @Override
    public String getEmail() {
        return "t.nasyrov@innopolis.ru";
    }
}
