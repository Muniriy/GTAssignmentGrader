package gametheory.assignment2.students2020;

import gametheory.assignment2.Player;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;

/**
 * Interface for all agents
 */
//interface Player {
//    void reset();
//    String getEmail();
//    int move(int opponentLastMove, int xA, int xB, int xC);
//}

/**
 *  Class that contains 3 parameters.
 *  f - number of points for fields.
 *  s - index of field
 *  value - state of this field
 */
class Pair {
    public int f;
    public int s;
    public int value;

    Pair(int f, int s, int value) {
        this.f = f;
        this.s = s;
        this.value = value;
    }

    Pair(int f, int s) {
        this.f = f;
        this.s = s;
    }
}
// Comparator for AdvancedRandomMax Agent
class SortByCost2 implements Comparator<Pair> {
    public int compare(Pair a, Pair b) {
        if (( a.f < b.f ) || ( a.f == b.f && ( a.value > b.value ))) return -1;
        else if ( a.f == b.f ) return 0;
        else return 1;
    }
}

public class AnastassiyaBoikoCode implements Player {
    public String getEmail(){
        return "an.boiko@innopolis.ru";
    }
    // Contain last moves of his opponent
    private int lastMovesOp[] = new int[3];
    // Number of opponent moves to the field with particular index
    private int res[] = new int[4];
    public void reset() {
        this.lastMovesOp[0] = this.lastMovesOp[1] = this.lastMovesOp[2] = 0;
    }

    public int move(int opponentLastMove, int xA, int xB, int xC) {
        // Clear result array
        this.res[1] = this.res[2] = this.res[3] = 0;

        // Calculate result for each field
        for (int i = 0; i < 3; i++)
            this.res[this.lastMovesOp[i]]++;
        this.res[opponentLastMove]++;

        for (int i = 0; i < 2; i++)
            this.lastMovesOp[i] = this.lastMovesOp[i + 1];
        this.lastMovesOp[2] = opponentLastMove;

        // Create array of pairs
        Pair[] prob = new Pair[3];
        prob[0] = new Pair(this.res[1], 1, xA);
        prob[1] = new Pair(this.res[2], 2, xB);
        prob[2] = new Pair(this.res[3], 3, xC);
        Arrays.sort(prob, new SortByCost2());

        // Create random number
        Random random = new Random();
        if (prob[0].value + prob[1].value == 0)
            return prob[2].s;

        // This agent does not select first field, but select one of last 2 fields with certain probability.
        int num = random.nextInt(prob[0].value + prob[1].value);
        return (num < prob[1].value)? prob[1].s : prob[0].s;
    }
}