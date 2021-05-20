package gametheory.assignment2.students20s;

import gametheory.assignment2.Player;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Random;


public class FaritGaleevCode implements Player {
    /**
     * This is a modified version of Smart Greedy algorithm with coin flip selection when probability equal to 1
     */

    int xA, xB, xC;
    int roundsPlayed = 1;
    int greedOpMoves = 0;
    HashMap<Integer, Integer> fields;

    public FaritGaleevCode() {
        this.reset();
    }

    /**
     * Put everything to the initial state
     */
    @Override
    public void reset() {
        xA = 1;
        xB = 1;
        xC = 1;
        fields = new HashMap<>();
        fields.put(1, xA);
        fields.put(2, xB);
        fields.put(3, xC);
        roundsPlayed = 1;
        greedOpMoves = 0;
    }

    @Override
    public int move(int opponentLastMove, int xA, int xB, int xC) {
        this.xA = xA;
        this.xB = xB;
        this.xC = xC;
        fields.put(1, xA);
        fields.put(2, xB);
        fields.put(3, xC);
        Integer[] sortedFields = sortByValue(fields); // Sort the fields in the descending order by its vegetation value
        // if the opponents last move is equal to ours then we should consider it as possible greedy move else choose the best field
        if (opponentLastMove == sortedFields[0] + 1) {
            greedOpMoves++;
            // calculate probability that the opponent's move is greedy
            double probability = greedOpMoves / roundsPlayed;
            Random random = new Random();
            roundsPlayed++;
            // if probability is near 1 that means, that we are playing against another greedy strategy, so that let's
            // add some randomness to break opponent's strategy :) else that this can be meant as stochastic error of the
            // another strategy (for ex. random)
            if (probability == 1.0) {
                double rand = random.nextDouble();
                if (rand < 0.5) {
                    return sortedFields[0] + 1;
                } else {
                    return sortedFields[1] + 1;
                }
            } else {
                if (random.nextDouble() > probability) {
                    return sortedFields[0] + 1;
                } else {
                    return sortedFields[1] + 1;
                }
            }
        } else {
            roundsPlayed++;
            return sortedFields[0] + 1;
        }

    }

    @Override
    public String getEmail() {
        return "f.galeev@innopolis.ru";
    }


    /**
     * @param fields hashmap of the fields' state
     * @return sorted in descending order keys
     */
    public Integer[] sortByValue(HashMap<Integer, Integer> fields) {
        ArrayIndexComparator comparator = new ArrayIndexComparator(fields);
        Integer[] indexes = comparator.createIndexArray();
        Arrays.sort(indexes, comparator);
        return indexes;
    }

}


/**
 * Implementation of the Comparator interface for the sortByValue function
 */
class ArrayIndexComparator implements Comparator<Integer> {
    private final HashMap<Integer, Integer> hm;

    public ArrayIndexComparator(HashMap<Integer, Integer> hm) {
        this.hm = hm;
    }

    public Integer[] createIndexArray() {
        Integer[] indexes = new Integer[hm.size()];
        for (int i = 0; i < hm.size(); i++) {
            indexes[i] = i; // Autoboxing
        }
        return indexes;
    }

    @Override
    public int compare(Integer index1, Integer index2) {
        // Autounbox from Integer to int to use as array indexes
        return hm.get(index2 + 1).compareTo(hm.get(index1 + 1));
    }


}
