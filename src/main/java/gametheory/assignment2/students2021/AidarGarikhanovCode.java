package gametheory.assignment2.students2021;

import gametheory.assignment2.Player;

import java.util.Random;

import static java.lang.Math.exp;


public class AidarGarikhanovCode implements Player {
    private static final int TARGET_VALUE = 6; // Target amount of vegetation
    private boolean coop, init;
    private int myField, opponentField, waitingField;

    public AidarGarikhanovCode() {
        reset();
    }

    /**
     * Calculate the gain of agent if it chooses the region with the specified X value
     *
     * @param x - X value of the region
     * @return gain
     */
    public static double getGain(int x) {
        return f(x) - f(0);
    }

    /**
     * Calculate the amount of vegetation in the region with the specified X value
     *
     * @param x - X value of the region
     * @return f(x)
     */
    public static double f(int x) {
        return (10 * exp(x)) / (1 + exp(x));
    }

    @Override
    public void reset() {
        coop = true;  // if cooperating
        init = true;  // in init stage
        myField = -1;
        opponentField = -1;
        waitingField = -1;
    }

    @Override
    public int move(int opponentLastMove, int xA, int xB, int xC) {
        // cooperation
        if (coop) { // cooperating with opponent
            if (init) {  // init stage
                if (myField == -1) { // first move, just choose random field
                    myField = new Random().nextInt(3) + 1;
                    return myField;
                } else if (myField == opponentLastMove) {  // we've chose the same field
                    myField = new Random().nextInt(3) + 1;
                    return myField;
                } else {  // we've passed init stage
                    opponentField = opponentLastMove;
                    waitingField = 6 - myField - opponentField;
//                    System.out.println("myField = " + myField);
//                    System.out.println("opponentField = " + opponentField);
//                    System.out.println("waitingField = " + waitingField);
                    init = false;
                    return waitingField;
                }
            } else if (opponentLastMove == myField) {  // friendship ended
                coop = false;
            } else {
                // value at my field
                int myValue = 0;
                if (myField == 1)
                    myValue = xA;
                else if (myField == 2)
                    myValue = xB;
                else if (myField == 3)
                    myValue = xC;

                if (myValue == TARGET_VALUE) {
                    return myField;
                } else {
                    return waitingField;
                }
            }
        }

        // Random greedy strategy
        // Calculate probabilities to choose each field
        double[] gains = new double[3];
        gains[0] = getGain(xA);
        gains[1] = getGain(xB);
        gains[2] = getGain(xC);
        double total_gain = 0;
        for (int field = 0; field < 3; field++)
            total_gain += gains[field];
        double[] chances = new double[3];
        for (int field = 0; field < 3; field++)
            chances[field] = gains[field] / total_gain;

        double r = new Random().nextDouble();

        if (r <= chances[0])
            return 1;
        else if (r <= chances[0] + chances[1])
            return 2;
        else
            return 3;
    }

    @Override
    public String getEmail() {
        return "a.garihanov@innopolis.university";
    }
}
