package gametheory.assignment2.students20s;

import gametheory.assignment2.Player;

import java.util.Random;

public class ArturRakhmatullinCode implements Player {
    Random r = new Random();
    int i = this.r.nextInt(90) + 10;
    ;
    int[] previousFieldValues = new int[3];

    public void reset() {
        this.r = new Random();
        this.i = this.r.nextInt(90) + 10; // reset i - random number at the beginning
        previousFieldValues = new int[3]; // reset previous field values
    }

    public int move(int opponentLastMove, int xA, int xB, int xC) {
        if (this.i % 3 == 0 || this.i % 4 == 0) { // if our random number is a multiple of 3 or 4, then go to random field
            this.i++;
            return this.r.nextInt(1) + 1;
        }

        int choice; // chosen field to go
        // if there is a maximum value go to its field
        if (xA >= xB && xA >= xC)
            choice = 1;
        else if (xB >= xA && xB >= xC)
            choice = 2;
        else if (xC >= xA && xC >= xB)
            choice = 3;
        else
            choice = 1;

        // if previousFieldValues not 0 and opponent is also playing taking max each time, then go to the first field
        if (!isEmptyLastFields() && isLastMoveToMax(opponentLastMove)) {
            choice = 1;

        }

        // store current field values to access them later
        this.previousFieldValues[0] = xA;
        this.previousFieldValues[1] = xB;
        this.previousFieldValues[2] = xC;
        this.i++; // increment our random number
        return choice;
    }

    private boolean isEmptyLastFields() {
        boolean isEmpty = true;
        for (int j : this.previousFieldValues) {
            if (j != 0) {
                isEmpty = false;
            }
        }
        return isEmpty;
    }

    private boolean isLastMoveToMax(int lastMove) {
        if (this.previousFieldValues[0] == this.previousFieldValues[1] && this.previousFieldValues[1] == this.previousFieldValues[2])
            return false;
        int indexLargest = getIndexOfLargest(this.previousFieldValues);
        return indexLargest == lastMove;
    }

    private int getIndexOfLargest(int[] array) {
        if (array == null || array.length == 0) return -1; // null or empty

        int largest = 0;
        for (int i = 1; i < array.length; i++) {
            if (array[i] > array[largest]) largest = i;
        }
        return largest; // position of the first largest found
    }

    public String getEmail() {
        return "a.rahmatullin@innopolis.ru";
    }
}
