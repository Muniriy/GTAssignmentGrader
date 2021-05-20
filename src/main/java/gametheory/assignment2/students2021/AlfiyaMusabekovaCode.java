package gametheory.assignment2.students2021;

import gametheory.assignment2.Player;

import java.util.Random;

public class AlfiyaMusabekovaCode implements Player {
    Random r = new Random();
    int N = -1;                         // number of rounds played
    Boolean is_second = false;          // variable to check second round of game
    double is_greedy = 0;               // in how many rounds opponent chose maximum
    int aLast = 0, bLast = 0, cLast = 0;// previous values of X on a, b, c fields

    // return field with second max X if it's not zero
    static int second_max(int max, int xA, int xB, int xC) {
        if (max == xA) {
            if (xB > xC)
                return 2;
            else if (xC == 0)
                return 1;
            else
                return 3;
        }
        if (max == xB) {
            if (xA > xC)
                return 1;
            else if (xC == 0)
                return 2;
            else
                return 3;
        }
        if (max == xC) {
            if (xA > xB)
                return 1;
            else if (xB == 0)
                return 3;
            else
                return 2;
        }
        return 0;
    }

    @Override
    public void reset() {
        N = -1;
        is_second = false;
        is_greedy = 0;
        aLast = 0;
        bLast = 0;
        cLast = 0;
    }

    @Override
    public int move(int opponentLastMove, int xA, int xB, int xC) {
        N++;
        // if it's first move then choose random
        if (opponentLastMove == 0) {
            is_second = true;       // next round is the second
            return r.nextInt(11) % 3 + 1;
        }
        // second move also random, collect information about the opponent
        if (is_second) {
            is_second = false;      // next round is not the second
            aLast = xA;
            bLast = xB;
            cLast = xC;
            return r.nextInt(11) % 3 + 1;
        }
        // find maximums
        int max = Math.max(xA, Math.max(xB, xC));
        int lastMax = Math.max(aLast, Math.max(bLast, cLast));
        // check whether the last move was greedy or no and update greedy coefficient
        if ((lastMax == aLast && opponentLastMove == 1) || (lastMax == bLast && opponentLastMove == 2) || (lastMax == cLast && opponentLastMove == 3))
            is_greedy++;
        aLast = xA;
        bLast = xB;
        cLast = xC;
        // case when moose is greedy
        if ((is_greedy / N) > 0.5)
            if (r.nextInt() % 2 == 0) {
                // return maximum
                if (max == xA)
                    return 1;
                if (max == xB)
                    return 2;
                if (max == xC)
                    return 3;
            } else
                return second_max(max, xA, xB, xC); // return second maximum if it's not zero
        else {
            // opponent is not greedy, return max
            if (max == xA)
                return 1;
            if (max == xB)
                return 2;
            if (max == xC)
                return 3;
        }
        return 0;
    }

    @Override
    public String getEmail() {
        return "a.musabekova@innopolis.university";
    }
}
