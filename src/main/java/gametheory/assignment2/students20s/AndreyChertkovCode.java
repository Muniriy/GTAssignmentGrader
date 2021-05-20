package gametheory.assignment2.students20s;

import gametheory.assignment2.Player;

public class AndreyChertkovCode implements Player {

    String strategy = "Sum maximum payoff";

    public AndreyChertkovCode() {
    }

    public void reset() {
    }


    private double calculatePayoff(int x) {
        return (10 * Math.exp(x)) / (1 + Math.exp(x));
    }

    public int move(int opponentLastMove, int xA, int xB, int xC) {
        double[] probabilities = {0, 0, 0};
        double aPayoff = calculatePayoff(xA);
        double bPayoff = calculatePayoff(xB);
        double cPayoff = calculatePayoff(xC);
        probabilities[0] = aPayoff / (aPayoff + bPayoff + cPayoff);
        probabilities[1] = bPayoff / (aPayoff + bPayoff + cPayoff);
        probabilities[2] = cPayoff / (aPayoff + bPayoff + cPayoff);
        probabilities[0] += ((double) xA) / (xA + xB + xC);
        probabilities[1] += ((double) xB) / (xA + xB + xC);
        probabilities[2] += ((double) xC) / (xA + xB + xC);
        int result = 0;
        for (int i = 0; i < 3; i++) {
            if (probabilities[i] >= probabilities[result])
                result = i;
        }
        return result;
    }

    @Override
    public String getEmail() {
        return "a.chertkov@innopolis.ru";
    }

    public String toString() {
        return String.format("%s", strategy);
    }
}


