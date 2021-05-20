package gametheory.assignment2.students20s;

import gametheory.assignment2.Player;

import java.util.Random;

/**
 * The implementation of the cooperation tactic described in the report
 *
 * @author VItaliy Korbashov
 */
public class VitaliyKorbashovCode implements Player {

    //Hyper parameters to tune
    public static final int GRASS_SIZE = 6;
    public static final int PATIENCE = 7;
    public static final int MOVES_UTIL_EAT = 7500;
    int EXPECTED_NUMBER_OF_ROUNDS = 99999999;
    int movesMade = 0;
    int lastMove;
    int mainPosition;
    int waitingPosition;
    int currentPosOnlyForEat;
    boolean cooperator;
    boolean collisionAvoided;
    boolean lastServing;
    boolean growingGrass;
    int[] fields;

    Random randomer = new Random();

    public VitaliyKorbashovCode() {
        this.reset();
    }


    @Override
    public void reset() {

        if (movesMade != 0) {
//            if (EXPECTED_NUMBER_OF_ROUNDS != 99999999) {
            EXPECTED_NUMBER_OF_ROUNDS = movesMade;
//            }
        } else {
            EXPECTED_NUMBER_OF_ROUNDS = 99999999;
        }

//        EXPECTED_NUMBER_OF_ROUNDS = movesMade == 0? 99999999: movesMade;

//        System.out.println(EXPECTED_NUMBER_OF_ROUNDS);

        movesMade = 0;
        lastMove = -1;
        mainPosition = -3;
        waitingPosition = -4;
        cooperator = true;
        collisionAvoided = false;
        lastServing = false;
        fields = new int[4];
        growingGrass = true;
    }

    /**
     * @param a the argument X for a field A
     * @param b the argument X for a field B
     * @param c the argument X for a field C
     * @return index of first encountered field with best value
     */
    int indexOfLargest(int a, int b, int c) {
        int max1 = Math.max(a, b);
        int max = Math.max(max1, c);

        if (max == a) {
            return 1;
        } else if (max == b) {
            return 2;
        } else {
            return 3;
        }
    }

    /**
     * @param current the current field
     * @param fields  all other field values as an array
     * @return index of the best field to choose
     */
    private int changePlace(int current, int[] fields) {

        switch (current) {
            case 1:
                return indexOfLargest(-999, fields[2], fields[3]);
            case 2:
                return indexOfLargest(fields[1], -999, fields[3]);
            case 3:
                return indexOfLargest(fields[1], fields[2], -999);
            default:
                return 3;
        }
    }

    /**
     * Basically the eatUntilFull tactic from the report
     *
     * @param xA the argument X for a field A
     * @param xB the argument X for a field B
     * @param xC the argument X for a field C
     * @return the next field to choose
     */
    int eatUntilFull(int xA, int xB, int xC) {
        movesMade++;
        int[] fields = {-1488, xA, xB, xC};

        if (fields[currentPosOnlyForEat] <= 0) {
            currentPosOnlyForEat = changePlace(currentPosOnlyForEat, fields);
        }
        return currentPosOnlyForEat;

    }

    /**
     * The best tactic to use in case cooperation cannot happen; In my case Random-Greedy
     *
     * @param xA the argument X for a field A
     * @param xB the argument X for a field B
     * @param xC the argument X for a field C
     * @return the next field to choose
     */
    int bestTactic(int opponentLastMove, int xA, int xB, int xC) {


        if (lastMove != 0) {
            int nextMove = randomer.nextBoolean() ? findFreeField(opponentLastMove, lastMove) : lastMove;
            lastMove = randomer.nextBoolean() ? indexOfLargest(xA, xB, xC) : nextMove;
        } else {
            lastMove = randomer.nextInt(3) + 1;
        }


        return lastMove;

//        int nextMove = 1 + randomer.nextInt(3);
//
//        return randomer.nextBoolean() ? indexOfLargest(xA, xB, xC) : nextMove;
    }

    /**
     * @param pos1 field occupied by player 1
     * @param pos2 field occupied by player 2
     * @return
     */
    int findFreeField(int pos1, int pos2) {
        int[] arr = {1, 2, 3};

        for (int i = 0; i < arr.length; i++) {
            if ((arr[i] == pos1) || (arr[i] == pos2)) {
                arr[i] = -1;
            }
        }

        return indexOfLargest(arr[0], arr[1], arr[2]);
    }

    /**
     * The
     *
     * @param opponentLastMove the last move of the opponent
     * @param xA               the argument X for a field A
     * @param xB               the argument X for a field B
     * @param xC               the argument X for a field C
     * @return
     */
    int cooperateTactic(int opponentLastMove, int xA, int xB, int xC) {

        // To try to eat up all the grass before the game ends
        lastServing = movesMade != 0 && (movesMade > (EXPECTED_NUMBER_OF_ROUNDS - GRASS_SIZE));

        if (lastServing) {
            movesMade++;
            return mainPosition;
        }

        if (!collisionAvoided && movesMade >= PATIENCE) {
            cooperator = false;
            return bestTactic(opponentLastMove, xA, xB, xC);
        }

        if (movesMade == 0) { // First attempt at establishing the agent role in cooperation
            collisionAvoided = false;
            lastMove = 1 + randomer.nextInt(3);
            movesMade++;
            return lastMove;
        } else if (opponentLastMove != lastMove && !collisionAvoided) { // In case the collision was avoided
            collisionAvoided = true;
            mainPosition = lastMove;
            waitingPosition = findFreeField(lastMove, opponentLastMove);
            lastMove = waitingPosition;
            movesMade++;
            return waitingPosition;
        } else if (opponentLastMove == lastMove && !collisionAvoided) { // In case collision happens
            lastMove = 1 + randomer.nextInt(3);
            movesMade++;
            return lastMove;
        } else if (opponentLastMove == lastMove && lastMove != waitingPosition) {  // Resolving collisions

            //Stop being nice
            cooperator = false;
            return bestTactic(opponentLastMove, xA, xB, xC);

        } else { // Making normal moves

            collisionAvoided = true; //Make Collision avoidance only happen once
            movesMade++;
            if (lastMove != waitingPosition) { // To make the agent oscillate
                lastMove = waitingPosition;
                return waitingPosition;
            } else {

                if ((fields[mainPosition] >= GRASS_SIZE)) {
                    growingGrass = false;
                }

                if (growingGrass) { // Waiting for grass to grow so that gains are maximized in the end

                    if (opponentLastMove == mainPosition) { // Stop being nice if the opponent is not cooperating
                        cooperator = false;
                        return bestTactic(opponentLastMove, xA, xB, xC);
                    }

                    return waitingPosition;
                }

                lastMove = mainPosition;
                return mainPosition;

            }
        }
    }

    @Override
    public int move(int opponentLastMove, int xA, int xB, int xC) {
        fields[0] = -1488;
        fields[1] = xA;
        fields[2] = xB;
        fields[3] = xC;


        if (cooperator) {
            return cooperateTactic(opponentLastMove, xA, xB, xC);
        } else if (movesMade > MOVES_UTIL_EAT) {
            return eatUntilFull(xA, xB, xC);
        } else {
            movesMade++;
            return bestTactic(opponentLastMove, xA, xB, xC);
        }
    }

    @Override
    public String getEmail() {
        return "v.korbashov@innopolis.university";
    }
}
