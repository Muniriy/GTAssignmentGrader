package com.company;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

public class RimRakhimovCode implements Player {
    private SecureRandom random;

    // used by cooperation scenario
    private int chosenField;
    private boolean updated;
    private int opponentsChosenField;
    private boolean protocolEstablished;
    private boolean protocolFail;

    private int totalStepsCounter;

    // used by positional scenario
    private int[] previousFieldsX = {0, 0, 0};
    private int[] stepsCounts = {0, 0, 0};

    public RimRakhimovCode() {
        reset();
    }

    @Override
    public void reset() {
        random = new SecureRandom();
        chosenField = chooseField();
        updated = false;
        opponentsChosenField = 0;
        protocolEstablished = false;
        protocolFail = false;
        totalStepsCounter = 0;

        // initialize arrays as zeros
        zeroArray(previousFieldsX);
        zeroArray(stepsCounts);
    }

    @Override
    public int move(int opponentLastMove, int xA, int xB, int xC) {
        // should count total number of rounds played
        ++totalStepsCounter;

        // the first move, nothing is known about the opponent
        if (opponentLastMove == 0) {
            return chosenField;
        }
        // save opponents choice
        if (!protocolEstablished) {
            opponentsChosenField = opponentLastMove;

            if (opponentsChosenField == chosenField) {  // both players moved on the same field
                chosenField = chooseField();    // new field is chosen
                return chosenField;
            } else {    // protocol has been established: may continue
                protocolEstablished = true;
            }
        }

        if (! protocolFail) {   // player still cooperates
            if (opponentLastMove == chosenField) {
                protocolFail = true;
            }
            // never reach these steps until the protocol is established
            int valueFromChosenField = 0;
            if (chosenField == 1) {
                valueFromChosenField = xA;
            } else if (chosenField == 2) {
                valueFromChosenField = xB;
            } else {
                valueFromChosenField = xC;
            }
            int border = 9;     // value of the field to be considered to go on it
            if (!updated && valueFromChosenField < border) {
                return 6 - chosenField - opponentsChosenField;
            } else {
                updated = (valueFromChosenField > border - 3);
                return chosenField;
            }
        } else {    // the opponent has been non-cooperative
            return nonProtocolMove(opponentLastMove, xA, xB, xC);
        }
    }

    // positional strategy move: choose the field that results in the most expected payoff
    private int nonProtocolMove(int opponentLastMove, int xA, int xB, int xC) {
        int[] fieldValues = {xA, xB, xC};

        // update steps taken by
        if (opponentLastMove > 0) { // at least one step was done before
            ++totalStepsCounter;
            stepsCounts[opponentLastMove - 1]++;

            // calculate expected payoffs and choose the field to go
            double[] expectedPayoffs = getExpectedPayoffs(fieldValues);
            int myMove = getFieldFromExpectedPayoffs(expectedPayoffs);

            // allows to get not too low result if the agent meets positional player
            if (random.nextDouble() > 0.8) {
                myMove = random.nextInt(3) + 1;
            }

            // save fields values
            System.arraycopy(fieldValues, 0, previousFieldsX, 0, 3);

            return myMove;
        } else {    // no steps have been done, choose randomly
            return random.nextInt(3) + 1;
        }
    }

    // choose one of the fields randomly
    private int chooseField() {
        return random.nextInt(3) + 1;
    }

    // zeros all values inside the array
    private void zeroArray(int[] arr) {
        int len = arr.length;
        for (int i = 0; i < len; ++i) {
            arr[i] = 0;
        }
    }

    // score function used in the tournament
    private double score(int x) {
        double baseScore = 5.0;
        return (10 * Math.exp(x) / (1 + Math.exp(x))) - baseScore;
    }

    // returns expected payoffs based on current field values and
    //  opponents previous decisions
    private double[] getExpectedPayoffs(int[] fieldValues) {
        assert totalStepsCounter > 0;  // no steps are done, thus, nothing to estimate
        double[] expectedPayoffs = new double[3];
        for (int i = 0; i < 3; ++i) {
            int stepsNotInField = 0;
            for (int j = 0; j < 3; ++j) {
                if (i != j) {
                    stepsNotInField += stepsCounts[j];
                }
            }
            // probability that opponent will not go to the field
            double probability = stepsCounts[i] / (float) totalStepsCounter;
            expectedPayoffs[i] = probability * score(fieldValues[i]);
        }
        return expectedPayoffs;
    }

    // returns the field based on expected payoffs
    private int getFieldFromExpectedPayoffs(double[] expectedValues) {
        assert expectedValues.length == 3;  // 3 fields are used in the game
        double maxExpectedValue = Math.max(expectedValues[0], Math.max(expectedValues[1], expectedValues[2]));

        // fields with maximal expected payoff
        List<Integer> maxFields = new ArrayList<>();
        for (int i = 0; i < 3; ++i) {
            // as real number are not compared by '=='
            if (Math.abs(expectedValues[i] - maxExpectedValue) < 0.000001) {
                maxFields.add(i + 1);
            }
        }
        int seed = random.nextInt(maxFields.size());
        return maxFields.get(seed);
    }

    @Override
    public String getEmail() {
        return "r.rahimov@innopolis.ru";
    }
}
