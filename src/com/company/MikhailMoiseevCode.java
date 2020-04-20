package com.company;


import java.util.ArrayList;
import java.util.Random;

public class MikhailMoiseevCode implements Player{
    int myLastMove = -1;
    int strategy = 1;


    @Override
    public void reset() {
        myLastMove = -1;
        strategy = 1;
    }

    public String getEmail(){
        return "m.moiseev@innopolis.ru";
    }

    @Override
    public int move(int opponentLastMove, int xA, int xB, int xC) {
        Random random = new Random();
        if (myLastMove == opponentLastMove && Math.random() > 0.5)
            strategy = (strategy + 1) % 2;

        if (strategy == 1) {
            ArrayList<Integer> choices = new ArrayList<>();
            int max = Math.max(Math.max(xA, xB), Math.max(xB, xC));
            if (xA == max)
                choices.add(1);
            if (xB == max)
                choices.add(2);
            if (xC == max)
                choices.add(3);
            myLastMove = choices.get(random.nextInt(choices.size()));
            return myLastMove;

        }
        else{
            ArrayList<Integer> choices = new ArrayList<>();
            int max2 = Math.min(Math.max(xA, xB), Math.max(xB, xC));
            if (xA == max2)
                choices.add(1);
            if (xB == max2)
                choices.add(2);
            if (xC == max2)
                choices.add(3);
            myLastMove = choices.get(random.nextInt(choices.size()));
            return myLastMove;


        }
    }
}
