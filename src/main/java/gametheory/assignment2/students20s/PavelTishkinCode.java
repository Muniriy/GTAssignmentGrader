package gametheory.assignment2.students20s;

import gametheory.assignment2.Player;

import java.util.ArrayList;
import java.util.Random;

public class PavelTishkinCode implements Player {

    private boolean consensus = false;
    private int myField = 0;
    private int waitingField = 0;
    private int collisions = 0;

    public static int randomGreedyMove(int xA, int xB, int xC) {
        /*
        Same as in Advanced Greedy but in 50% returns random value
         */
        int[] fields = new int[]{0, xA, xB, xC};
        Random random = new Random();
        int index = IndexOfLargest.getIndexOfLargest(fields);
        ArrayList<Integer> indexes = new ArrayList<>();
        indexes.add(index);
        for (int i = 0; i < 4; i++) {
            if (fields[i] == fields[index] && i != index) {
                indexes.add(i);
            }
        }
        return random.nextBoolean() ? indexes.get(random.nextInt(indexes.size())) : random.nextInt(3) + 1;
    }

    @Override
    public void reset() {
        waitingField = 0;
        myField = 0;
        collisions = 0;
        consensus = false;
    }

    @Override
    public int move(int opponentLastMove, int xA, int xB, int xC) {
        int[] fields = new int[]{0, xA, xB, xC};
        if (!consensus) {
            /*
            From the start there is no consensus between coops
             */
            if (myField != opponentLastMove) {
                /*
                Consensus happened, there is no collision
                 */
                consensus = true;
                waitingField = 6 - myField - opponentLastMove;
            } else {
                Random random = new Random();
                int move = random.nextInt(3) + 1;
                myField = move;
                collisions += 1;
                /*
                They try to agree for 5 turns
                 */
                if (collisions == 5) {
                    consensus = true;
                    /*
                    myField = -1 denotes inability to cooperate
                     */
                    myField = -1;
                }
                return move;
            }
        }
        /*
        myField = -1 denotes someone breaking the alliance
        after consensus
         */
        if (myField != -1) {
            /*
            If there is a break in alliance on this turn
             */
            if (opponentLastMove == myField) {
                myField = -1;
            } else {
                /*
                Growing grass on myField
                 */
                if (fields[myField] == 6) {
                    return myField;
                } else {
                    return waitingField;
                }
            }
        }
        /*
        Greedy behaviour
         */
        return randomGreedyMove(xA, xB, xC);
    }

    @Override
    public String getEmail() {
        return "p.tishkin@innopolis.university";
    }
}

class IndexOfLargest {
    /*
    Copied from:
    https://stackoverflow.com/questions/22911722/how-to-find-array-index-of-largest-value/22911923
     */
    public static int getIndexOfLargest(int[] array) {
        if (array == null || array.length == 0) return -1; // null or empty

        int largest = 0;
        for (int i = 1; i < array.length; i++) {
            if (array[i] > array[largest]) largest = i;
        }
        return largest; // position of the first largest found
    }
}