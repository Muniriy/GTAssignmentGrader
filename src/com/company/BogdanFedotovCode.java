package com.company;

import java.util.Random;


// Probabilistic Greedy Round-Robin agent.
// Agent tries to not stay in the same place and to pick the most promising fields in a probabilistic fashion.
// This agent remembers it's previous move and assumes a previously visited field to have only half of it's real vegetation
public class BogdanFedotovCode implements Player {
    private int myLastMove = 0;
    Random rand = new Random();

    public void setSeed(int seed) {
        rand.setSeed(seed);
    }

    BogdanFedotovCode() {}
    BogdanFedotovCode(int seed) {
        rand.setSeed(seed);
        myLastMove = 0;
    }

    public void reset() {
        myLastMove = 0;
    }

    public int move(int opponentLastMove, int xA, int xB, int xC) {
        // Since probability depends on vegetation quantity we can decrease probability with a trick of "decreasing"
        // the amount of vegetation on our last visited field by a factor of 2.
        switch (myLastMove) {
            case 1:
                xA /= 2;
                break;
            case 2:
                xB /= 2;
                break;
            case 3:
                xC /= 2;
            default:
                break;
        }

        int total = xA + xB + xC;
        if (total == 0) {
            // If there is no vegetation left go somewhere random
            myLastMove = rand.nextInt(3) + 1;
            return myLastMove;
        }
        int randInt = rand.nextInt(total) + 1;
        // Probability to visit some field sums to 1.
        if (randInt <= xA) {
            myLastMove = 1;
            return 1;
        } else if (randInt <= xA + xB) {
            myLastMove = 2;
            return 2;
        } else {
            myLastMove = 3;
            return 3;
        }
    }

    public String getEmail() {
        return "b.fedotov@innopolis.ru";
    }
}