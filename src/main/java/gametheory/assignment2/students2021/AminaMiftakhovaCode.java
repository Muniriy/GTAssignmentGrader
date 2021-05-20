package gametheory.assignment2.students2021;

import gametheory.assignment2.Player;

public class AminaMiftakhovaCode implements Player {

    public AminaMiftakhovaCode() {
    }

    public double calculateVegetation(int X) {
        return (10.0 * Math.pow(Math.E, X)) / (1 + Math.pow(Math.E, X));
    }

    @Override
    public void reset() {
    }

    @Override
    public int move(int opponentLastMove, int xA, int xB, int xC) {
        // calculate the vegetation values for each field
        double foodA = calculateVegetation(xA);
        double foodB = calculateVegetation(xB);
        double foodC = calculateVegetation(xC);
        double all = foodA + foodB + foodC;

        // normalize vegetation value to get probabilities
        double[] probs = new double[3];
        probs[0] = foodA / all;
        probs[1] = foodB / all;
        probs[2] = foodC / all;

        // cumulative probabilities
        for (int i = 1; i < 3; i++) {
            probs[i] = probs[i - 1] + probs[i];
        }

        // choose randomly field with obtained probabilities
        double prob = Math.random();
        int i = 0;
        while (probs[i] < prob) {
            i++;
        }

        return i + 1;
    }

    @Override
    public String getEmail() {
        return "a.miftahova@innopolis.university";
    }
}
