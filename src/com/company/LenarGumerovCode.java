package com.company;


import java.util.Random;

public class LenarGumerovCode implements Player {

    static Random randomNumGenerator = new Random();

    public static final int A = 1;
    public static final int B = 2;
    public static final int C = 3;

    private double score;


    public LenarGumerovCode() {
        reset();
    }

    public void reset() {
        this.score = 0;
    }

    public int move(int opponentLastMove, int xA, int xB, int xC) {
        return 1 + randomNumGenerator.nextInt(3);
    }

    public String getEmail() {
        return "l.gumerov@innopolis.ru";
    }

    public static double sigmoid(int x) {
        return 10 - 10 / (Math.exp(x) + 1);
    }

    public void gain(int vegetationsOnField) {
        this.score += sigmoid(vegetationsOnField) - sigmoid(0);
    }

    public double getScore() {
        return score;
    }
}