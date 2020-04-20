package com.company;
import java.util.*;

class RinatMullahmetovCode implements Player {
    private int round;
    private int myLastMove;
    private int[] weights;
    private Random randomer;
    public RinatMullahmetovCode(){
        this.reset();
        this.randomer = new Random();
    }
    public void reset(){
        this.round = 0;
        this.weights = new int[]{-1, 1, 1, 1};
        this.myLastMove = 0;
    }

    public int move(int opponentLastMove, int xA, int xB, int xC){
        if (opponentLastMove == 0) {
            this.myLastMove = this.randomer.nextInt(3) + 1;
            return this.myLastMove;
        }

        if (!this.skipUpdate()) this.update(opponentLastMove);

        this.myLastMove = argmax(this.weights[1] * xA,
                this.weights[2] * xB,
                this.weights[3] * xC) + 1;
        return this.myLastMove;
    }

    public String getEmail(){
        return "r.mullahmetov@innopolis.ru";
    }


    void update(int opponentLastMove){
        if (opponentLastMove == this.myLastMove){
            this.weights[opponentLastMove] += 1;
        }
        else {
            this.weights[opponentLastMove] += 1;
            this.weights[this.myLastMove] -= 1;
        }
    }
    boolean skipUpdate(){
        int rand = this.randomer.nextInt(10);
        return rand > 7;
    }
    public static int argmax(int xA, int xB, int xC){
        Integer[] scores = new Integer[]{xA, xB, xC};
        List<Integer> list = new ArrayList<>(Arrays.asList(scores));
        int argmax = list.indexOf(Collections.max(list));
        list.remove(argmax);
        int argmax2 = list.indexOf(Collections.max(list));
        if (argmax != argmax2){
            return argmax;
        }
        else {
            if (new Random().nextInt(2) == 0){
                return argmax;
            }
            else {
                return argmax2;
            }
        }
    }
}