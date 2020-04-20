package com.company;

import java.util.ArrayList;

public class FarhadKhakimovCode implements Player {
    // class X (pair, essentially) for easier time with selecting best field
    class X {
        int x;
        int fieldNo;

        public X(int x_, int no_) {
            x = x_;
            fieldNo = no_;
        }

        public int compare(X another) {
            if (another.x < x) return fieldNo;
            else if (another.x != x) return another.fieldNo;
            // return random if they are equal
            else if (Math.random() < 0.5) return fieldNo;
            else return another.fieldNo;
        }
    }

    // is the agent first in the round
    private boolean isFirst = false;

    public void reset() {
        isFirst = false;
    }

    public int move(int opponentLastMove, int xA, int xB, int xC) {
        // create a list of pairs (of X's and Field numbers) for easier selection
        ArrayList<X> xs = new ArrayList<>();
        xs.add(new X(xA, 1)); xs.add(new X(xB, 2)); xs.add(new X(xC, 3));
        // make sure to play the greedy card if the agent goes first
        if (opponentLastMove != 0 && !isFirst) {
            // save the field the opponent went to last time
            int thatField = xs.get(opponentLastMove - 1).fieldNo;
            // remove the field the opponent went to
            xs.remove(opponentLastMove - 1);
            // if the remaining X's are not equal, go to the better pasture
            if (xs.get(0).x != xs.get(1).x) return xs.get(0).compare(xs.get(1));
            // otherwise, go to battle with the opponent to let the grass grow elsewhere
            return thatField;
        } else isFirst = true;
        // in case this is the beginning of the play, play the greedy card
        // select the highest X among the fields - if they are all equal, return random field number
        if (xA > xB && xA > xC) return 1;
        else if (xB > xC) return 2;
        else if (xC != xB) return 3;
        else return (int)Math.ceil(Math.random() * 3);
        //int outstandingMove = ;
    }

    public String getEmail() {
        return "f.hakimov@innopolis.ru";
    }
}

class FarhadKhakimovHungry implements Player {
    public void reset() {
    }

    public int move(int opponentLastMove, int xA, int xB, int xC) {
        int outstandingMove;
        // select the highest X among the fields - if they are all equal, return random field number
        if (xA > xB && xA > xC) return 1;
        else if (xB > xC) return 2;
        else if (xC != xB) return 3;
        else return (int)Math.ceil(Math.random() * 3);
        //return outstandingMove;
    }

    public String getEmail() {
        return "f.hakimov@innopolis.ru";
    }
}

class FarhadKhakimovRandom implements Player {
    public void reset() {
    }

    public int move(int opponentLastMove, int xA, int xB, int xC) {
        return (int)Math.ceil(Math.random() * 3);
    }

    public String getEmail() {
        return "f.hakimov@innopolis.ru";
    }
}