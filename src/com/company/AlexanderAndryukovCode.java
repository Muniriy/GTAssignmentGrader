package com.company;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AlexanderAndryukovCode implements Player {
    public AlexanderAndryukovCode() {
    }

    public void reset() {
        // My strategy doesn't suppose to store anything.
        // So, it is just empty method.
    }

    public int move(int opponentLastMove, int xA, int xB, int xC) {
        // My strategy is simple.
        // Go randomly to a land where food exists.
        // If food is nowhere - go randomly in any place.
        List<Integer> list = new ArrayList<>(3);
        if (xA > 0)
            list.add(1);
        if (xB > 0)
            list.add(2);
        if (xC > 0)
            list.add(3);
        if(xA == 0 && xB == 0 && xC == 0){
            list.add(1);
            list.add(2);
            list.add(3);
        }
        Random rand = new Random();
        return list.get(rand.nextInt(list.size()));
    }

    @Override
    public String getEmail() {
        return "a.andryukov@innopolis.ru";
    }
}
