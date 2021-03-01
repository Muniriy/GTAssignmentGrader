package gametheory.assignment2.students2020;

import gametheory.assignment2.Player;

import java.util.LinkedList;
import java.util.Random;

class gameTree {
    float A;
    float B;
    float C;

    public void setTree(int givenGrassA, int givenGrassB, int givenGrassC) {
        //imagine we go A, then opponent can go A B or C. let me count all the possible payoffs payoffs dfference
        float AA = 0;
        float AB = (float) ((10 * Math.pow(Math.E, givenGrassA)) / (1 + Math.pow(Math.E, givenGrassA))) - (float) ((10 * Math.pow(Math.E, givenGrassB)) / (1 + Math.pow(Math.E, givenGrassB)));
        float AC = (float) ((10 * Math.pow(Math.E, givenGrassA)) / (1 + Math.pow(Math.E, givenGrassA))) - (float) ((10 * Math.pow(Math.E, givenGrassC)) / (1 + Math.pow(Math.E, givenGrassC)));

        //do the same for If I go B
        float BA = (float) ((10 * Math.pow(Math.E, givenGrassB)) / (1 + Math.pow(Math.E, givenGrassB))) - (float) ((10 * Math.pow(Math.E, givenGrassA)) / (1 + Math.pow(Math.E, givenGrassA)));
        float BB = 0;
        float BC = (float) ((10 * Math.pow(Math.E, givenGrassB)) / (1 + Math.pow(Math.E, givenGrassB))) - (float) ((10 * Math.pow(Math.E, givenGrassC)) / (1 + Math.pow(Math.E, givenGrassC)));

        //and the same for if I go C
        float CA = (float) ((10 * Math.pow(Math.E, givenGrassC)) / (1 + Math.pow(Math.E, givenGrassC))) - (float) ((10 * Math.pow(Math.E, givenGrassA)) / (1 + Math.pow(Math.E, givenGrassA)));
        float CB = (float) ((10 * Math.pow(Math.E, givenGrassC)) / (1 + Math.pow(Math.E, givenGrassC))) - (float) ((10 * Math.pow(Math.E, givenGrassB)) / (1 + Math.pow(Math.E, givenGrassB)));
        float CC = 0;

        //now we should get the best move based on which one gives the better sum
        A = AA + AB + AC;
        B = BA + BB + BC;
        C = CA + CB + CC;
    }
    public int getBest(){
        //we return the move with the best sum
        float result = Math.max(A, Math.max(B, C));
        if (result == A){
            return 1;
        }
        else if (result == B){
            return 2;
        }
        else{
            return 3;
        }
    }
}


public class OydinoyZufarovaCode implements Player {

    final Random random = new Random();
    private LinkedList<Integer> opponentMoves = new LinkedList<Integer>();
    private gameTree tree = new gameTree();

    @Override
    public void reset() {
        opponentMoves.clear();
    }

    @Override
    public int move(int opponentLastMove, int xA, int xB, int xC) {
        //if opponent move is 0 then the game just starts. let us put just random
        if (opponentLastMove == 0) {
            return random.nextInt(3);
        //if it is not 0 then just find the best move
        } else {
            opponentMoves.add(opponentLastMove);
            tree.setTree(xA, xB, xC);
            return tree.getBest();
        }
    }

    @Override
    public String getEmail() {
        return "o.zufarova@innopolis.ru";
    }
}


