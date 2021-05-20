/**
 * OlgaChernukhinaCode.java
 * <p>
 * Version 1.0, March 2021
 * <p>
 * Copyrights: Olga Chernukhina, BS-18-SB-01
 * o.chernuhina@innopolis.university
 */
package gametheory.assignment2.students20s;

import gametheory.assignment2.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * The class implements the Moose agent behavior in a competitive environment
 *
 * @version 1.0 March 2021
 * @author Olga Chernukhina
 */
public class OlgaChernukhinaCode implements Player {

    /** flag is used to track the strategy: 1 - Greedy | -1 - SecondBest*/
    public int flag;

    /** cooperation denotes the current strategy used
     * 1 - Cooperator | 0 - Individual */
    public int cooperation = 0;

    /** these variables store field values in Cooperative strategy*/
    public int myField = 0, opponentField = 0, waitingField = 0;
    /** Used to generate random moves and random numbers in range [0;1]*/
    public Random random = new Random();
    /** This variable denote maximum and further used for random generation*/
    int MAX_FIELD = 3;
    /** Used to store the moves of opponent player*/
    List<Integer> opponentMoves;
    /** Used to store the moves of my agent player*/
    List<Integer> myMoves;

    /**
     * This method resets the agent before each match
     * with another player
     */
    @Override
    public void reset() {
        opponentMoves = new ArrayList<>();
        myMoves = new ArrayList<>();
        myMoves.add(0); // initially add my first move that is reported to the opponent
        flag = 1;
        myField = 0;
        opponentField = 0;
        waitingField = 0;
        cooperation = 0;
    }

    /**
     * This method returns the field with the maximum possible gain
     * if there are several fields with X=maximum gain - returns random of those fields
     *
     * @param xA               the argument X for a field A
     * @param xB               the argument X for a field B
     * @param xC               the argument X for a field C
     *
     * @return the field number: can be 1 for A, 2 for B
     *      *         and 3 for C
     */
    public int maxGainField(int xA, int xB, int xC) {
        int[] gain = {xA, xB, xC};
        Arrays.sort(gain);
        int maxGain = gain[2]; // chooses maximum value among xA, xB, xC
        return CompareAndChoose(maxGain, xA, xB, xC);
    }

    /**
     * This method returns randomly chosen field
     *
     * @return the field number: can be 1 for A, 2 for B
     *      *         and 3 for C
     */
    public int randomField() {
        return random.nextInt(MAX_FIELD) + 1;
    }

    /**
     * This method returns the field with the second best gain
     * if the second best gain is 0 - return the field with the best gain
     * if there are several fields with X=second best gain or X's are equal
     * for all fields - return random of those fields
     *
     * @param xA               the argument X for a field A
     * @param xB               the argument X for a field B
     * @param xC               the argument X for a field C
     *
     * @return the field number: can be 1 for A, 2 for B
     *      *         and 3 for C
     */
    public int secondGainField(int xA, int xB, int xC) {
        int[] gain = {xA, xB, xC};
        Arrays.sort(gain);
        int secondMaxGain = gain[1];
        return (secondMaxGain == 0 ? maxGainField(xA, xB, xC)
                : CompareAndChoose(secondMaxGain, xA, xB, xC));
    }

    /**
     * This method returns the field that provides the target gain
     * that is chosen according to the strategy
     * If there are several fields with X=targetGain - choose randomly
     *
     * @param targetGain       is the field X that is chosen according
     *                         to the strategy currently used in the tournament
     * @param xA               the argument X for a field A
     * @param xB               the argument X for a field B
     * @param xC               the argument X for a field C
     *
     * @return the field number: can be 1 for A, 2 for B
     *      *         and 3 for C
     */
    public int CompareAndChoose(int targetGain, int xA, int xB, int xC) {
        int move;
        if ((xA == targetGain) && (xB == targetGain)
                && (xC == targetGain)) {
            move = randomField();
        } else if ((xA == targetGain) && (xB == targetGain)) {
            move = random.nextInt(2) + 1;
        } else if ((xB == targetGain) && (xC == targetGain)) {
            move = random.nextInt(2) + 2;
        } else if ((xA == targetGain) && (xC == targetGain)) {
            move = random.nextFloat() > 0.5 ? 1 : 3;
        } else if (xA == targetGain) {
            move = 1;
        } else if (xB == targetGain) {
            move = 2;
        } else {
            move = 3;
        }
        return move;
    }

    /**
     * This method returns the flag that is used to identify the strategy
     * 1 stands for Greedy strategy
     * -1 stands for the SecondBestStrategy
     *
     * initially the flag is 1
     *
     * @param flag the strategy currently used in the tournament
     *
     * @return the flag can be 1 for Greedy and -1 for SecondBest strategies
     */
    public int flagChange(int flag) {
        /*used to count number of continuous clashes among the last moves*/
        int clashesInARow = 0;
        int i = myMoves.size() - 1;
        while (i >= 0 && myMoves.get(i).equals(opponentMoves.get(i))) {
            clashesInARow++;
            i--;
        }

        /*if at least 1 clash is detected among the last moves*/
        if (clashesInARow > 0) {
            /*if the current strategy is Greedy*/
            if (flag == 1) {
                //the probability of switching the strategy
                //is directly related to the number of clashes
                return (random.nextDouble() < (-2 / (0.8 * clashesInARow)) + 1.1)
                        ? flag * -1 : flag;
                /*if the current strategy is SecondBest*/
            } else {
                //the probability of switching the strategy
                //is directly related to the number of clashes
                return (random.nextDouble() < (-1.0 / (clashesInARow + 1)) + 1)
                        ? flag * -1 : flag;
            }
            /*if no clashes were detected do not switch the strategy*/
        } else {
            return flag;
        }
    }

    /**
     * This method chooses between the algorithms according to
     * opponent's moves and fields' X and returns the next move of the agent
     *
     * @param opponentLastMove the last move of the opponent
     *                         varies from 0 to 3
     *                         (0 – if this is the first move)
     * @param xA               the argument X for a field A
     * @param xB               the argument X for a field B
     * @param xC               the argument X for a field C
     * @return the move of the player can be 1 for A, 2 for B
     *         and 3 for C fields
     */
    @Override
    public int move(int opponentLastMove, int xA, int xB, int xC) {
        int move;
        opponentMoves.add(opponentLastMove);
        //if cooperation did not start yet
        if (myField == 0) {
            //if the condition for starting cooperation is met
            if (myMoves.get(myMoves.size() - 1) != opponentLastMove) {
                myField = myMoves.get(myMoves.size() - 1);
                opponentField = opponentLastMove;
                waitingField = 6 - myField - opponentField;
                cooperation = 1;
            }
        }
        //choosing the strategy based on opponent's move
        cooperation = ((cooperation == 1)
                && (opponentLastMove != myField) && (myField != 0)) ? 1 : 0;
        //choosing the move according to the strategy
        move = (cooperation == 1) ? cooperativeAlgo(xA, xB, xC)
                : nonCooperativeAlgo(xA, xB, xC);

        /*if an error occurred, throw an exception*/
        if (move > 0 && move < 4) {
            myMoves.add(move);
            return move;
        } else {
            System.out.println("OlgaChernukhinaCode value error: move is not in the range [1;3]");
            throw new RuntimeException();
        }
    }

    /**
     * This method returns the move of the player based on X value of myField.
     * If X on myField is less than 5 (this value is reasoned in the report),
     * then my agent chooses to wait for vegetation to grow
     *
     * @param xA               the argument X for a field A
     * @param xB               the argument X for a field B
     * @param xC               the argument X for a field C
     * @return the move of the player can be 1 for A, 2 for B
     *         and 3 for C fields
     */
    public int cooperativeAlgo(int xA, int xB, int xC) {
        int[] gain = {xA, xB, xC};
        return (gain[myField - 1] < 5) ? waitingField : myField;
    }

    /**
     * This method returns the move of the player based on
     * list of last opponent's and its own moves and X values of all fields.
     *
     * @param xA               the argument X for a field A
     * @param xB               the argument X for a field B
     * @param xC               the argument X for a field C
     * @return the move of the player can be 1 for A, 2 for B
     *         and 3 for C fields
     */
    public int nonCooperativeAlgo(int xA, int xB, int xC) {
        flag = flagChange(flag);
        return (flag == 1) ? maxGainField(xA, xB, xC)
                : secondGainField(xA, xB, xC);
    }

    /**
     * This method returns my IU email
     *
     * @return my university email
     */
    @Override
    public String getEmail() {
        return "o.chernuhina@innopolis.university";
    }
}