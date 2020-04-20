package com.company;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class DmitryKochetovCode implements Player {
    private Random rand = new Random();
    private int myLastMove;

    public String getEmail() {
        return "d.kochetov@innopolis.ru";
    }

    public void reset() {
        myLastMove = 0;
    }

    public int move(int opponentLastMove, int xA, int xB, int xC) {
        int fields[] = {-0, xA, xB, xC};

        if (myLastMove == 0 || myLastMove == opponentLastMove) {
            myLastMove = rand.nextInt(3) + 1;
            return myLastMove;
        }

        if (fields[myLastMove] == 0) {
            List<Integer> ls = new ArrayList<>(Arrays.asList(1, 2, 3));
            ls.remove(Integer.valueOf(myLastMove));
            ls.remove(Integer.valueOf(opponentLastMove));
            myLastMove = ls.get(0);
            return myLastMove;
        }
        return myLastMove;
    }
}
