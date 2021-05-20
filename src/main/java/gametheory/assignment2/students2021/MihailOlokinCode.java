package gametheory.assignment2.students2021;

import gametheory.assignment2.Player;

import java.util.Random;

/**
 * This class contains some handy function I used during testing and implementation.
 * MihailOlokinCode class relies on function from this class, that's why this class is here.
 * If this class causes any issues, I would be really happy to remove it.
 */
class MihailOlokinUtils {
    private static final Random defaultRng = new Random();

    /**
     * A convenient wrapper arounds Java's standard Random.nextInt();
     *
     * @param a - first number;
     * @param b - second number;
     * @return a random integer in range [a, b];
     */
    public static int randRange(int a, int b) {
        return defaultRng.nextInt(b) + a;
    }

    /**
     * @return true or false with 50% probability;
     */
    public static boolean coinFlip() {
        return withProbability(0.5);
    }

    /**
     * @param chance - a probability that this function will return true;
     * @return true with a probability @chance, else false;
     */
    public static boolean withProbability(double chance) {
        return defaultRng.nextDouble() < chance;
    }

    /**
     * @param myField       - field of the agent;
     * @param opponentField - field of agent's opponent;
     * @return returns a field that is taken neither by the current agent,
     * * nor by his oponent.
     */
    public static int freeField(int myField, int opponentField) {
        assert myField != opponentField;
        int sum = myField + opponentField;
        if (sum == 3 || sum == 5) {
            return sum % 4;
        } else {
            return 2;
        }
    }

    /**
     * @param array - an input array;
     * @return returns the index of the first maximum element of the array;
     */
    public static int argmax(int[] array) {
        assert array.length == 4;
        int maxAt = 0;
        for (int i = 0; i < array.length; i++) {
            maxAt = array[i] > array[maxAt] ? i : maxAt;
        }
        return maxAt;
    }

    /**
     * @param current - given value
     * @return returns a random value in range [1, 3] that is not equal to @current
     */
    public static int chooseAnotherRandomly(int current) {
        int result = MihailOlokinUtils.randRange(1, 3);
        while (result == current) {
            result = MihailOlokinUtils.randRange(1, 3);
        }
        return result;
    }

    /**
     * @param current - some value;
     * @param fields  - an array of field values;
     * @return a field with the most grass that is not equal to @current;
     */
    public static int chooseAnotherMax(int current, int[] fields) {
        assert fields.length == 4;
        int maxAt = 0;
        for (int i = 0; i < fields.length; i++) {
            maxAt = fields[i] > fields[maxAt] && maxAt != current ? i : maxAt;
        }
        return maxAt;
    }

}

/**
 * Class that implements my Cooperator agent.
 * For more details concerning the implementation, see
 * MihailOlokinReport.pdf
 */
public class MihailOlokinCode implements Player {
    private static int NUMBER_OF_PLAYERS = 0;       // number of instances (used to maintain uniqueness of ID)
    private static int GRASS_GROWTH_THRESHOLD = 4;
    private final int id;                           // players unique identifier
    private double score;                           // player's current score
    private int lastMove = 0;                       // agent's last move
    private int myField = 0;
    private int waitingField = 0;
    private boolean cooperate = true;
    private int numberOfRounds = 0;
    private boolean thresholdDefined = false;       // when set to true, numberOfRounds equals the current number of rounds in the tournament
    private int staleCount = 0;

    public MihailOlokinCode() {
        this.score = 0.0;

        // I maintain the number of players,
        // so that every player has a unique ID
        NUMBER_OF_PLAYERS += 1;
        this.id = NUMBER_OF_PLAYERS;
    }

    private int estimateThreshold(int numberOfRounds) {
        double exp7 = Math.exp(numberOfRounds / 7.);
        double result = (7 * exp7 / (exp7 + 1)) + 2;

        // handling the floating point errors
        if (Double.isFinite(result)) {
            return (int) result;
        }
        return 9;

    }

    @Override
    public void reset() {
        if (!thresholdDefined && numberOfRounds > 0) {
            thresholdDefined = true;
            GRASS_GROWTH_THRESHOLD = estimateThreshold(numberOfRounds);
        }
        lastMove = myField = waitingField = 0;
        cooperate = true;
    }

    @Override
    public int move(int opponentLastMove, int xA, int xB, int xC) {
        int currentMove;
        int[] fieldValues = {-1, xA, xB, xC};

        numberOfRounds = (thresholdDefined ? numberOfRounds : numberOfRounds + 1);
        if (opponentLastMove == 0) {
            // the first move is arbitrary
            currentMove = MihailOlokinUtils.randRange(1, 3);
        } else {
            if (myField == 0 & waitingField == 0) {
                // if states are not determined yet
                if (opponentLastMove == lastMove) {
                    // if randomly-determined first states collided, random more
                    currentMove = MihailOlokinUtils.randRange(1, 3);
                } else {
                    // if not, remember the fields for later use and move to the waiting field
                    myField = lastMove;
                    waitingField = MihailOlokinUtils.freeField(myField, opponentLastMove);
                    currentMove = waitingField;
                }
            } else {
                // if states are determined,

                // if opponent is stale, this will grow fast
                if (opponentLastMove != myField && opponentLastMove != waitingField) {
                    staleCount += 1;
                } else {
                    staleCount = 0;
                }

                // if opponent went to my field,
                // it means he is not cooperating
                if (opponentLastMove == myField) {
                    cooperate = false;
                }
                // if opponent cooperates and is not stale
                if (cooperate && staleCount < 5) {
                    // move to the waiting field if grass on players field is less than GRASS_GROWTH_THRESHOLD,
                    // else move to my field;
                    currentMove = (fieldValues[myField] < GRASS_GROWTH_THRESHOLD ? waitingField : myField);
                } else {
                    // if the protocol failed at some point, proceed with ShyRandomGreedy strategy
                    currentMove = MihailOlokinUtils.withProbability(0.5) ?
                            MihailOlokinUtils.argmax(fieldValues) :
                            MihailOlokinUtils.chooseAnotherRandomly(opponentLastMove);

                }
            }
        }
        lastMove = currentMove;
        return currentMove;
    }

    @Override
    public String getEmail() {
        return "m.olokin@innopolis.university";
    }

    public void updateScore(double score) {
        if (Double.isFinite(score)) {
            this.score += score;
        } else {
            // in case there was an overflow/underflow
            // error while calculating payoffs
            this.score += 10.;
        }
    }

    public double getScore() {
        return score;
    }

    public Integer getId() {
        return id;
    }
}

