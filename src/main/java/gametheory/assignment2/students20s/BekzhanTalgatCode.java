package gametheory.assignment2.students20s;

import gametheory.assignment2.Player;

import java.util.Random;

public class BekzhanTalgatCode implements Player {

    private int roundId;                    // to keep track of what round is going on
    private boolean cooperativeMode;        // true if agent is cooperative; true in the beginning
    private boolean defectiveMode;          // true if agent is defecting; turns on if opponent is not cooperative
    private int fieldToChoose;              // return value for move() method
    private int lastChoice;                 // previous field choice
    private int eatingRnd;                  // it is for cooperative mode, number of rounds to wait after which agent gonna eat
    private boolean isEating;               // true if agent is eating
    private double grassAmount;             // to keep track of grass eaten by agent
    private int grassToLeave;               // limit for the agent, to stop overeating

    private Random rnd;                     // random for random decisions

    /**
     * Main constructor
     * calls method reset()
     */
    public BekzhanTalgatCode() {             // standard constructor
        reset();
    }

    /**
     * constructor for testing cases
     * to compare cooperative and defective modes
     *
     * @param isCooperative to set modes, cooperative if true. Otherwise if false
     */
    public BekzhanTalgatCode(boolean isCooperative) {
        reset();
        cooperativeMode = isCooperative;
        defectiveMode = !isCooperative;
    }

    /**
     * function to keep track of grass eaten by agent
     *
     * @param x, grass growth factor
     * @return grass amount according to the given function in the assignment f(x) - f(0)
     * }
     */
    private double growthFunction(int x) {
        return (10 * Math.exp(x)) / (1 + Math.exp(x)) - 5;
    }

    /**
     * initial conditions
     * to set all the initial values for the global variables
     */
    @Override
    public void reset() {
        roundId = 0;
        cooperativeMode = true;
        defectiveMode = false;
        fieldToChoose = 1;                              // if cooperative, then wait N rounds in field A
        lastChoice = 0;                                 //
        eatingRnd = 7;                                  // here initially N == 7;
        isEating = false;                               // then go to eat from field B or C
        grassAmount = 0.0;                              //
        grassToLeave = 3;                               // eat until x == 3 in the fields
        // cause: if x is greater, then outcome is greater
        rnd = new Random();
    }

    /**
     * This method returns field number according to the agents mode and the last decision of the opponent
     *
     * @param opponentLastMove the last move of the opponent
     *                         varies from 0 to 3
     *                         (0 – if this is the first move)
     * @param xA               the argument X for a field A
     * @param xB               the argument X for a field B
     * @param xC               the argument X for a field C
     * @return fieldToChoose
     */
    @Override
    public int move(int opponentLastMove, int xA, int xB, int xC) {
        int waitingRound = 7;                                   // to detect the first round
        if (opponentLastMove == 0) {                             // to wait exactly 7 is not effective
            roundId = 1;                                        // if opponent is cooperative and will wait 7, too
            eatingRnd = rnd.nextInt(4) + waitingRound;   // then both of them might go the the same field
        }                                                       // so it is better to wait randomly 7 - 10+ rounds
        else roundId++;

        if (cooperativeMode) {                                  // eatingRnd is chosen only when both cooperative
            if (!isEating) {                                     // agents come to field A to wait
                fieldToChoose = 1;

                if (roundId % eatingRnd == 0) {                 // then roundId is checked if dividable by eatingRnd
                    if (xC >= xB) fieldToChoose = 3;            // then go the field with larger amount of grass
                    else fieldToChoose = 2;                     // but, still there is a small chance for both agents
                    isEating = true;                            // to go to the same field
                }
            }

            int checkRound = 30;                        // this is to detect if opponent is defective agent
            double patienceCoef = (double) 3 / 2;          // every 30 rounds check if agent gained enough amount of grass
            if (roundId % checkRound == 0 && grassAmount < (double) roundId * patienceCoef) {
                cooperativeMode = false;                // if not, then turn on defective mode
                defectiveMode = true;
            }
        }

        if (defectiveMode) {                             // defective mode, is greedy algorithm
            if (xA == xB && xA == xC) fieldToChoose = rnd.nextInt(3) + 1;
            // go to the field with large amount of grass
            if (xA == xB && xC < xA) fieldToChoose = rnd.nextInt(2) + 1;
            if (xA == xC && xB < xC) {                    // if there are same amount in several
                int rrr = rnd.nextInt(2);        // then choose randomly
                fieldToChoose = rrr + (int) Math.pow(2, rrr);
            }
            if (xB == xC && xA < xB) fieldToChoose = rnd.nextInt(2) + 2;

            if (xA > xB && xA > xC) fieldToChoose = 1;
            if (xB > xA && xB > xC) fieldToChoose = 2;
            if (xC > xA && xC > xB) fieldToChoose = 3;
        }

        if (roundId > 1) {                               // this is too keep track of grass eaten by agent
            if (fieldToChoose == 1) {
                if (lastChoice != opponentLastMove) grassAmount += growthFunction(xA);
                if (xA < grassToLeave + 2 || (lastChoice == opponentLastMove && lastChoice != 1)) {
                    isEating = false;
                }
            }
            if (fieldToChoose == 2) {
                if (lastChoice != opponentLastMove) grassAmount += growthFunction(xB);
                if (xB < grassToLeave + 2 || (lastChoice == opponentLastMove && lastChoice != 1)) {
                    isEating = false;
                    eatingRnd = rnd.nextInt(4) + waitingRound;
                }
            }
            if (fieldToChoose == 3) {
                if (lastChoice != opponentLastMove) grassAmount += growthFunction(xC);
                if (xC < grassToLeave + 2 || (lastChoice == opponentLastMove && lastChoice != 1)) {
                    isEating = false;
                    eatingRnd = rnd.nextInt(4) + waitingRound;
                }
            }
        }
        lastChoice = fieldToChoose;
        return fieldToChoose;
    }

    /**
     * @return email
     */
    @Override
    public String getEmail() {
        return "b.talgat@innopolis.university";
    }

    /**
     * @return grass amount eaten by agent
     */
    public double getGrassAmount() {
        return grassAmount;
    }
}
