package gametheory.assignment2.students20s;

import gametheory.assignment2.Player;

public class DinarZayahovCode implements Player {

    /*
     * simple bubble sorting
     * takes two arrays: coefficients and field numbers
     * sorts both on increasing order of coefficients
     * returns the sorted field numbers
     */
    private static int[] bubbleSort(int[] coefficients, int[] fields) {
        boolean sorted = false;
        int temp1;
        int temp2;
        while (!sorted) {
            sorted = true;
            for (int i = 1; i < 3; i++) {
                if (coefficients[i] > coefficients[i + 1]) {
                    temp1 = coefficients[i];
                    coefficients[i] = coefficients[i + 1];
                    coefficients[i + 1] = temp1;

                    temp2 = fields[i];
                    fields[i] = fields[i + 1];
                    fields[i + 1] = temp2;

                    sorted = false;
                }
            }
        }
        return fields;
    }

    // method for getting the random value in range [min, max)
    private int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

    // random move method
    private int moveRandom() {
        return getRandomNumber(1, 4);
    }

    // moving to the field with maximum coefficient
    private int moveToMax(int xA, int xB, int xC) {
        int[] coefficients = {0, xA, xB, xC};
        int[] fields = {0, 1, 2, 3};
        int[] arr = bubbleSort(coefficients, fields);
        return arr[arr.length - 1];
    }

    // mixed strategy of random moves and moves to the field with maximum value
    private int moveRandomMax(int xA, int xB, int xC) {
        double rand = Math.random();
        if (rand < 0.5) {
            return moveRandom();
        } else {
            return moveToMax(xA, xB, xC);
        }
    }

    // I don't store any data about the game, so nothing to reset
    public void reset() {

    }

    // method which calls one of the move methods and returns the move
    public int move(int opponentLastMove, int xA, int xB, int xC) {
        return moveRandomMax(xA, xB, xC);
    }

    // returns the mail
    public String getEmail() {
        return "d.zayahov@innopolis.university";
    }

}
