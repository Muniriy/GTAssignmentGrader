package gametheory.assignment2.students20s;

import gametheory.assignment2.Player;

import java.util.Random;
import java.util.stream.DoubleStream;

public class LeonidLyginCode implements Player {
    private int myLastMove;
    private int lastSuccessfulMove;
    private int recentFailedMoves;

    public LeonidLyginCode() {
        this.reset();
    }

    public String getEmail() {
        return "l.lygin@innopolis.ru";
    }

    public void reset() {
        this.myLastMove = 0;
        this.lastSuccessfulMove = 0;
        this.recentFailedMoves = 0;
    }

    public int move(int opponentLastMove, int xA, int xB, int xC) {
        if (opponentLastMove == 0) {
            this.myLastMove = this.weightedRandom3(xA, xB, xC) + 1;
            return this.myLastMove;
        } else {
            if (this.myLastMove == opponentLastMove) {
                if (this.lastSuccessfulMove != 0) {
                    if (this.myLastMove == opponentLastMove) {
                        this.recentFailedMoves++;
                    } else {
                        this.recentFailedMoves = 0;
                    }
                    if (this.recentFailedMoves == 5) {
                        this.lastSuccessfulMove = 0;
                    } else {
                        this.myLastMove = this.lastSuccessfulMove;
                        return this.myLastMove;
                    }
                }
            }
            if (opponentLastMove != this.myLastMove) {
                this.lastSuccessfulMove = this.myLastMove;
                return this.myLastMove;
            } else {
                this.myLastMove = this.weightedRandom3(xA, xB, xC) + 1;
                return this.myLastMove;
            }
        }
    }

    private int weightedRandom(double[] weights) {
        double sum = DoubleStream.of(weights).sum();
        double[] normalizedWeights
                = DoubleStream.of(weights)
                .map((double x) -> x / sum)
                .toArray();

        double accumulator = 0;
        double rand = new Random().nextDouble();
        int idx = 0;

        while (rand > accumulator && idx <= weights.length) {
            accumulator += normalizedWeights[idx];
            idx++;
        }

        return idx - 1;
    }

    private int weightedRandom3(int a, int b, int c) {
        return this.weightedRandom(new double[]{a, b, c});
    }
}

