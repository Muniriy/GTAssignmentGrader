package com.company;

import java.util.Random;

public class IlnurGaripovCode implements Player {

    private int rounds;
    private boolean roundsDetermined;
    private int currentMove;
    private int myCell;
    private int prevMove;
    Random random;

    IlnurGaripovCode() {
        this.rounds = 0;
        this.roundsDetermined = false;
        this.random = new Random();
        this.reset();
    }

    public void reset() {
        if (!this.roundsDetermined && this.rounds > 0) {
            this.roundsDetermined = true;
        }
        this.currentMove = 0;
        this.myCell = 0;
        this.prevMove = 0;
    }

    public int move(int opponentLastMove, int xA, int xB, int xC) {
        this.currentMove++;
        if (this.roundsDetermined) {
            if (this.currentMove < this.rounds / 2) {
                return 1;
            } 
            else {
                if (this.myCell != 0) {
                    return this.myCell;
                } 
                else {
                    if (this.prevMove == 0 || this.prevMove == opponentLastMove) {
                        this.prevMove = 2 + this.random.nextInt(2);
                        return this.prevMove;
                    } else {
                        this.myCell = this.prevMove;
                        return this.myCell;
                    }
                }
            }
        } 
        else {
            this.rounds++;
            return 1 + this.random.nextInt(3);
        }
    }

    public String getEmail() {
        return "i.garipov@innopolis.ru";
    }
}