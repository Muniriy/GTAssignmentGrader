package gametheory.assignment2.students2020;

import gametheory.assignment2.Player;

import java.util.ArrayList;
import java.util.stream.IntStream;

public class AygulMalikovaCode implements Player {
    private int currentRound;

    private ArrayList myPreviousMoves = new ArrayList<>(1); //the storage for my previous moves
    private ArrayList opponentsPreviousMoves = new ArrayList<Integer>(1); //the storage for opponents moves

    private boolean sameStrategy = false; //the variable is true if opponent has the same strategy as me

    /**
     * This function is called in the beginning of each game to reset the round counter and storage for previous moves
     */
    @Override
    public void reset() {
        currentRound = 0;
        myPreviousMoves = new ArrayList(); // clearing the memory
        opponentsPreviousMoves = new ArrayList(); // clearing the memory
    }

    /**
     * This function is called during each round for making move based on opponent's last move and current values of the fields
     * @param opponentLastMove the move of opponent during the previous round
     * @param xA value of X in the field A
     * @param xB value of X in the field B
     * @param xC value of X in the field C
     * @return 1 or 2 or 3 (A, B or C respectively) - the field on which the Moose moves this round
     */
    @Override
    public int move(int opponentLastMove, int xA, int xB, int xC) {
        if (currentRound != 0) { //save all moves expect very first
            opponentsPreviousMoves.add(currentRound-1, opponentLastMove);
            // the number of moves which are checking for determining the similarity of my and opponent's strategies
            // e.g. checking the last 5 moves
            int step = 5;
            if (currentRound > step) {
                checkOnSameStrategy(step);
            }
        }
        int[] fields = new int[] {xA, xB, xC};

        int myCurrentMove = findBiggestField(fields); // the bigger current value X of the field the more outcome I will receive
        if (sameStrategy) { // but if our strategies are the same then I need to change my move for this round
            int finalMyCurrentMove = myCurrentMove; // temp variable of my current move
            int[] finalFields = fields; // temp variable of fields array
            //removing myCurrentMove from array of fields
            fields = IntStream.range(0, fields.length)
                    .filter(i -> i != finalMyCurrentMove -1)
                    .map(i -> finalFields[i])
                    .toArray();
            //find the new biggest field among the remaining
            myCurrentMove = findBiggestField(fields);
        }

        myPreviousMoves.add(currentRound, myCurrentMove);// save my move to the storage
        currentRound++;// increment the round counter
        return myCurrentMove;
    }

    @Override
    public String getEmail() {
        return "a.malikova@innopolis.ru";
    }

    /**
     * This function checks whether my N last moves and opponent's N last moves are the same
     * @param N - number of last moves which we need to check
     */
    private void checkOnSameStrategy(int N) {
        sameStrategy = myPreviousMoves.subList(currentRound - N, currentRound).equals(opponentsPreviousMoves.subList(currentRound - N, currentRound));
    }

    /**
     * Finding the field with biggest value and if there is more than one choose between them randomly
     * @param fields - array of values of the fields
     * @return 1, 2 or 3 -  the field with the maximum value
     */
    private int findBiggestField(int fields[]) {
        int max = Integer.MIN_VALUE;
        int maxId = 0;

        // firstEl and allElementsAreSame variables are needed for determining whether all fields have the same value
        int firstEl = fields[0];
        boolean allElementsAreSame = true;

        for (int i = 0; i < fields.length; i++) {
            if (fields[i] > max) {
                max = fields[i];
                maxId = i;
            }
            if (allElementsAreSame && fields[i] != firstEl) {
                allElementsAreSame = false;
            }
        }

        // The max value can be unchanged (equal to infinity) only if all Xs in the fields are equal.
        // In this case we just randomly choose new move
        if (max == Integer.MIN_VALUE || allElementsAreSame) {
            return (int)(Math.random() * ((3 - 1) + 1)) + 1;
        }
        // Otherwise choose the field with the max value.
        // +1 id done because the array begins with 0 index but our fields numeration are starting from 1
        else {
            return maxId+1;
        }
    }



}
