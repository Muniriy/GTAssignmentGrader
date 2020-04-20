package com.company;

import java.util.ArrayList;

public class ElizavetaKolchanovaCode implements Player {

    @Override
    public void reset() {
    }

    @Override
    public int move(int opponentLastMove, int xA, int xB, int xC) {

        ArrayList<Integer> optMoves = new ArrayList<Integer>();

        if (xA > xB){
            optMoves.add(1);
            if (xB > xC){
                optMoves.add(2);
            } else {
                optMoves.add(3);
            }
        } else {
            if (xA < xC){
                optMoves.add(2);
                optMoves.add(3);
            } else{
                optMoves.add(1);
                optMoves.add(2);
            }
        }

        int index = (int)(Math.random() * optMoves.size());

        return optMoves.get(index);
    }

    @Override
    public String getEmail() {
        return "e.kolchanova@innopolis.ru";
    }
}
