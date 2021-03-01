package gametheory.assignment2.students2020;

import gametheory.assignment2.Player;

public class AmadeyKuspakovCode implements Player {

    private static final int NO_LAST_MOVE= 0;

    private static final int FIELD_A = 0;
    private static final int FIELD_B = 1;
    private static final int FIELD_C = 2;

    private static final int MINIMAL_VEGETATION_LEVEL = 0;

    private static final double FIGHTING_PAYOFF = 0.0;

    private double myScore = 0.0;

    private static final String MY_EMAIL = "a.kuspakov@innopolis.ru";

    @Override
    public void reset() {
        myScore = 0.0;
    }

    private void setMyScore(double myScore){
        this.myScore = myScore;
    }

    @Override
    public int move(int opponentLastMove, int xA, int xB, int xC) {
        double[] possiblePayoffs = calculatePayoffs(opponentLastMove, xA, xB, xC);
        int myMove = chooseFieldWithMaxPayoff(possiblePayoffs) + 1; //Fields ids start from 0; Moves start from 1
        setMyScore(possiblePayoffs[myMove - 1]);
        return myMove;
    }

    private double[] calculatePayoffs(int opponentLastMove, int xA, int xB, int xC) {
        double[] possiblePayoffs = new double[3];
        possiblePayoffs[FIELD_A] = calculatePayoffAtField(xA);
        possiblePayoffs[FIELD_B] = calculatePayoffAtField(xB);
        possiblePayoffs[FIELD_C] = calculatePayoffAtField(xC);
        if (opponentLastMove != NO_LAST_MOVE) {
            possiblePayoffs[getFieldIndexOfOpponentsMove(opponentLastMove)] = FIGHTING_PAYOFF;
        }
        return possiblePayoffs;
    }

    private int getFieldIndexOfOpponentsMove(int opponentLastMove) {
        return opponentLastMove - 1;
    }

    private double calculatePayoffAtField(int fieldX) {
        return calculatePlantGrowthRate(fieldX) - calculatePlantGrowthRate(MINIMAL_VEGETATION_LEVEL);
    }

    private double calculatePlantGrowthRate(int fieldX) {
        return (10 * Math.exp(fieldX)) / (1 + Math.exp(fieldX));
    }

    private int chooseFieldWithMaxPayoff(double[] possiblePayoffs) {
        double max = possiblePayoffs[0];
        int indexOfMax = 0;
        for(int i = 0; i<possiblePayoffs.length; i++){
            if (possiblePayoffs[i] > max){
                max = possiblePayoffs[i];
                indexOfMax = i;
            }
        }
        return indexOfMax;
    }

    @Override
    public String getEmail() {
        return MY_EMAIL;
    }

    @Override
    public String toString() {
        return MY_EMAIL + ": " + myScore;
    }
}
