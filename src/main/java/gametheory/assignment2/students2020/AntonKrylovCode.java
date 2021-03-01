package gametheory.assignment2.students2020;

import gametheory.assignment2.Player;

import java.util.ArrayList;

public class AntonKrylovCode implements Player {

    private ArrayList<Integer> greedyHistory = new ArrayList<Integer>();
    private ArrayList<Integer> opponentHistory = new ArrayList<Integer>();

    private int moveA = 1;
    private int moveB = 2;
    private int moveC = 3;
    private double randomChance = 0.2;

    @Override
    public String getEmail() {
        return "a.krylov@innopolis.ru";
    }

    @Override
    public void reset() {
        greedyHistory.clear();
        opponentHistory.clear();
    }

    @Override
    public int move(int opponentLastMove, int xA, int xB, int xC) {
        int greedy = greedy(xA, xB, xC);

        if (greedyHistory.isEmpty()) {
            return randomMove(greedy);
        }

        if (makeRandomMove()) {
            return random();
        }

        opponentHistory.add(opponentLastMove);

        if (Math.random() > opponentGreedy()) {
            return greedyMove(greedy);
        }

        if (makeRandomMove()) {
            return randomMove(greedy);
        }

        return smartMove(xA, xB, xC, greedy);
    }

    private double opponentGreedy() {
        int n = greedyHistory.size();
        double greedyProb = 0;
        for (int i = 0; i < greedyHistory.size(); i++) {
            double prob = greedyHistory.get(i) != opponentHistory.get(i) ? 0 : 1;
            greedyProb += prob / n;
        }
        return greedyProb;
    }

    private int greedy(int xA, int xB, int xC) {
        if (xA > xB) {
            if (xA > xC) return moveA;
            else return moveC;
        } else {
            if (xB > xC) return moveB;
            else return moveC;
        }
    }

    private int random() {
        return (int) Math.round(Math.random()*2) + 1;
    }

    private boolean makeRandomMove() {
        return Math.random() < randomChance;
    }

    private int smartMove(int xA, int xB, int xC, int greedy) {
        greedyHistory.add(greedy);
        int variant = 1;
        int[] x = {xA, xB, xC};
        for (int i = 0; i < x.length; i++) {
            if (x[i] != greedy && x[i] != 0 && x[i] > x[variant]) {
                variant = i;
            }
        }
        if (x[variant] == 0) {
            return greedy;
        }

        return variant;
    }

    private int greedyMove(int greedy) {
        greedyHistory.add(greedy);
        return greedy;
    }

    private int randomMove(int greedy) {
        greedyHistory.add(greedy);
        return random();
    }
}
