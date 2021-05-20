package gametheory.assignment2.students20s;

import gametheory.assignment2.Player;

import java.util.Dictionary;
import java.util.Hashtable;

public class ArinaKuznetsovaCode implements Player {
    boolean oppChoseBest;
    int myLastMove;

    @Override
    public void reset() {  // initially player don't know if the opponent choose the best
        oppChoseBest = false;
        myLastMove = 0;
    }

    @Override
    public int move(int opponentLastMove, int xA, int xB, int xC) { // assuming on opponent's A=1, B=2, C=3

        Dictionary fields = new Hashtable();
        fields.put(1, xA);
        fields.put(2, xB);
        fields.put(3, xC);

        if (opponentLastMove == 0) {  // if it is the 1st move, move randomly
            int index = (int) Math.round(Math.random() * 2);
            myLastMove = sort(fields)[index];
            return myLastMove;
        }

        // if move is not the first, "imagine" the previous state of opponentLastMove field
        fields.put(opponentLastMove, (int) fields.get(opponentLastMove) + 1);

        if (opponentLastMove != myLastMove) {
            fields.put(myLastMove, (int) fields.get(myLastMove) + 1);
        }

        int a = (int) fields.get(1);
        int b = (int) fields.get(2);
        int c = (int) fields.get(3);

        // if flag is false and previous state of opponent last move is maximal, set flag to true
        if (!oppChoseBest & (int) fields.get(opponentLastMove) == max(a, b, c)) {
            oppChoseBest = true;
        }

        // return to current state
        fields.put(opponentLastMove, (int) fields.get(opponentLastMove) - 1);
        if (opponentLastMove != myLastMove) {
            fields.put(myLastMove, (int) fields.get(myLastMove) + 1);
        }

        // if minimal fields value >= 5, the payoff is the same, so it does not matter what to choose;
        // randomly choose worst or average option to minimize chance of going to the same field with opponent
        if (Math.min(Math.min(xA, xB), xC) >= 5) {
            int index = (int) Math.round(Math.random()) + 1;
            myLastMove = sort(fields)[index];
            return myLastMove;
        }

        if (!oppChoseBest) {  // if opponent haven't chosen best, my player will choose the best
            myLastMove = sort(fields)[0];
            return myLastMove;
        } else { // else my player chooses randomly;
            int index = (int) Math.round(Math.random());
            myLastMove = sort(fields)[index];
            return myLastMove;
        }
    }

    @Override
    public String getEmail() {
        return "a.kuznetsova@innopolis.ru";
    }

    private int max(int a, int b, int c) {
        return Math.max(Math.max(a, b), c);
    }

    private int[] sort(Dictionary dict) {  // method that sorts fields indexes by values in descending order
        // bubble sort
        int[] arr = new int[3];
        int[] numarr = new int[3];

        int n = 3;

        for (int i = 0; i < n; i++) {
            arr[i] = (int) dict.get(i + 1);
            numarr[i] = i + 1;
        }

        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (arr[j] < arr[j + 1]) {
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;

                    temp = numarr[j];
                    numarr[j] = numarr[j + 1];
                    numarr[j + 1] = temp;
                }
            }
        }

        return numarr;
    }

}
