package gametheory.assignment2.students20s;

import gametheory.assignment2.Player;

public class VladislavKalmykovCode implements Player {
    @Override
    public void reset() {

    }

    double f(int x) {
        double e = Math.E;
        double payoff = (10 * Math.pow(e, x)) / (1 + Math.pow(e, x));
        return payoff - 5; //f(0) = 5
    }

    @Override
    public int move(int opponentLastMove, int xA, int xB, int xC) {

        double pA, pB, pC;

        if (xC == 0) {
            pA = f(xA) / (f(xB) + f(xA));
            pB = 1d - pA;
            pC = 0;
        } else if (xB == 0) {
            pA = f(xA) / (f(xC) + f(xA));
            pB = 0;
            pC = 1d - pA;
        } else if (xA == 0) {
            pA = 0;
            pB = f(xB) / (f(xC) + f(xB));
            pC = 1d - pB;
        } else {

            pC = (f(xC) * f(xB) - f(xA) * f(xB) + f(xA) * f(xC)) / (f(xA) * f(xC) + f(xA) * f(xB) + f(xA) * f(xC));
            pB = (f(xB) - f(xC) + f(xC) * pC) / f(xB);
            pA = 1d - pC - pB;
        }
        System.out.print("pA = " + pA);
        System.out.print(" pB = " + pB);
        System.out.println(" pC = " + pC + "\n");

        double roll = Math.random();
        if (roll <= pA)
            return 1;
        else if (roll <= pA + pB)
            return 2;
        else
            return 3;
    }

    @Override
    public String getEmail() {
        return "v.kalmyikov@innopolis.university";
    }
}
