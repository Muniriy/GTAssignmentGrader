package gametheory.assignment2.students20s;

import gametheory.assignment2.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

/**
 * Player class.
 *
 * @author Max Kureykin
 * @version 1.0
 */
public class MaxKureykinCode implements Player {
    String name = "ME"; // default value
    Random random = new Random();
    double score;

    /**
     * Player class constructor.
     */
    public MaxKureykinCode() {
        score = 0;
    }

    /**
     * This method is called to reset the agent before the match
     * with another player containing several rounds
     */
    public void reset() {
    }

    /**
     * This method returns the move of the player based on
     * the last move of the opponent and X values of all fields.
     * Initially, X for all fields is equal to 1 and last opponent
     * move is equal to 0
     *
     * @param opponentLastMove the last move of the opponent
     *                         varies from 0 to 3
     *                         (0 – if this is the first move)
     * @param xA               the argument X for a field A
     * @param xB               the argument X for a field B
     * @param xC               the argument X for a field C
     * @return the move of the player can be 1 for A, 2 for B
     * and 3 for C fields
     */
    public int move(int opponentLastMove, int xA, int xB, int xC) {
        ArrayList<Integer> fields = new ArrayList<>(Arrays.asList(xA, xB, xC));
        ArrayList<Integer> readyFields = getIndexesOfSecondTopBiggerTreshold(fields);
        if (readyFields.size() == 0) {
            return unbiasedTop1Strategy(fields);
        } else
            return randomStrategy(readyFields);
    }

    /**
     * This method returns the move of the player based on
     * values of fields and unbiased top 1 strategy (UT1S)
     * UT1S chooses the biggest value of fields
     *
     * @param fields - ArrayList with values of fields
     * @return the move of the player can be 1 for A, 2 for B
     * and 3 for C fields
     */
    private int unbiasedTop1Strategy(ArrayList<Integer> fields) {
        int currentMax = Collections.max(fields);
        ArrayList<Integer> maxFieldsCandidates = new ArrayList<>();
        for (int i = 0; i < fields.size(); i++) {
            if (fields.get(i) == currentMax)
                maxFieldsCandidates.add(i + 1);
        }
        return randomStrategy(maxFieldsCandidates);
    }

    /**
     * This method returns the move of the player based on
     * random, it chooses random field in array of fields,
     * which value is greater than 4
     *
     * @param fields - ArrayList with values of fields
     * @return the move of the player can be 1 for A, 2 for B
     * and 3 for C fields
     */
    private int randomStrategy(ArrayList<Integer> fields) {
        return fields.get(random.nextInt(fields.size()));
    }

    /**
     * This method returns finds indexes of fields with value >= 4
     *
     * @param fields - ArrayList with values of fields
     * @return indexes of fields with value >= 4
     */
    private ArrayList<Integer> getIndexesOfSecondTopBiggerTreshold(ArrayList<Integer> fields) {
        ArrayList<Integer> result = new ArrayList<Integer>();
        for (int i = 0; i < fields.size(); i++) {
//            if (fields.get(i) != maxValue && fields.get(i) >= 4) {
            if (fields.get(i) >= 4) {
                result.add(i + 1);
            }
        }
        return result;
    }

    /**
     * This method returns your IU email
     *
     * @return your email
     */
    public String getEmail() {
        return "m.kureykin@innopolis.university";
    }

    /**
     * This method is getter for attribute score,
     * It returns player's score, it is used in tournament
     *
     * @return player's score
     */
    public double getScore() {
        return score;
    }

    /**
     * This method is setter for attribute score,
     * It sets player's score, it is used in tournament
     */
    public void setScore(double _score) {
        score = _score;
    }
}
