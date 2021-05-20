package gametheory.assignment2.students20s;

import gametheory.assignment2.Player;

import java.util.Random;

public class TymurLysenkoCode implements Player {
    private Random rand = new Random();

    @Override
    public void reset() {
        rand = new Random();
    }

    @Override
    public int move(int opponentLastMove, int xA, int xB, int xC) {
        return calculateTheBestFieldMove(xA, xB, xC);
    }

    private int calculateTheBestFieldMove(int xA, int xB, int xC) {
        if (xA > xB && xA > xC) {
            // a field gives the best payoff
            return 1;
        }

        if (xB > xA && xB > xC) {
            // b field gives the best payoff
            return 2;
        }

        if (xC > xB && xC > xA) {
            // c field gives the best payoff
            return 3;
        }

        // There is a tie between some fields and the third is <= to the other two ties
        if (xA == xB && xB == xC) {
            // all 3 fields give the same payoff
            return calculateRandomMove();
        } else {
            // there is a tie between 2 fields, and the third gives less payoff than other two
            if (xA == xB) {
                // select random of the a and b
                return selectRandomValue(1, 2);
            }

            if (xB == xC) {
                // select random of the b and c
                return selectRandomValue(2, 3);
            }

            // select random of the a and c
            return selectRandomValue(1, 3);
        }
    }

    private int calculateRandomMove() {
        return rand.nextInt(3) + 1;
    }

    private int selectRandomValue(int a, int b) {
        return rand.nextInt(2) == 0 ? a : b;
    }

    @Override
    public String getEmail() {
        return "t.lyisenko@innopolis.university";
    }
}
