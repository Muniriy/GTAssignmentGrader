package gametheory.assignment2.students2021;

import gametheory.assignment2.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 * The KamilRizatdinovCode class is the final submission agent.
 * It implements the logical Moose which cooperates with the instances of the
 * same class. It makes it's first move randomly, if both players move to the
 * same field - start over and make random field.
 * If players move to different fields for the first time - remember your
 * field and find out the field that is called commonField (field there no
 * player moved this time). Stick to this commonField while xValue on your
 * filed is less than 6. If xValue on your field is 6 - start going back and
 * forth from your field to commonField.
 * If your opponent moves to your field - start playing as SmartAlwaysBestAgent
 *
 * @author Kamil Rizatdinov
 */
public class KamilRizatdinovCode implements Player {
    Random randomNumberGenerator = new Random();
    int myField = 0;
    int opponentField = 0;
    int commonField = 0;
    int xThreshHold = 6;
    int collisionPatience = 3;
    int myLastMove = 0;
    boolean consensusFound = false;
    boolean playGreedy = false;

    public KamilRizatdinovCode() {

    }

    /**
     * This method is called to reset the agent before the match
     * with another player containing several rounds
     */
    @Override
    public void reset() {
        this.myField = 0;
        this.opponentField = 0;
        this.commonField = 0;
        this.xThreshHold = 6;
        this.collisionPatience = 3;
        this.myLastMove = 0;
        this.consensusFound = false;
        this.playGreedy = false;
        this.randomNumberGenerator = new Random();
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
    @Override
    public int move(int opponentLastMove, int xA, int xB, int xC) {
        HashMap<Integer, Integer> fields = this.convertFieldsToHashMap(xA, xB, xC);
        int randomMove = this.getRandomField(fields);
        int bestMove = this.getRandomField(this.getBestFields(fields));

        // If this is the first move
        if (opponentLastMove == 0) {
            this.myLastMove = randomMove;
            return randomMove;
        }

        if (this.playGreedy) {
            if (fields.get(bestMove) > this.xThreshHold) {
                if (this.randomNumberGenerator.nextInt(2) == 0) {
                    return bestMove;
                }
                return this.getSecondBestField(this.getNonZeroFields(fields));
            } else {
                return bestMove;
            }
        }

        // If this is not the first move
        // If we took different fields
        if (opponentLastMove != this.myLastMove) {
            if (this.consensusFound) {
                if (opponentLastMove == this.myField) {
                    this.playGreedy = true;
                    return bestMove;
                } else {
                    if (fields.get(this.myField) < this.xThreshHold) {
                        this.myLastMove = this.commonField;
                        return this.commonField;
                    } else {
                        this.myLastMove = this.myField;
                        return this.myField;
                    }
                }
            } else {
                this.consensusFound = true;
                this.myField = this.myLastMove;
                this.opponentField = opponentLastMove;
                this.commonField = 6 - (this.myLastMove + opponentLastMove);

                this.myLastMove = this.commonField;

                return this.commonField;
            }
        }
        // If we took same field
        else {
            if (this.consensusFound) {
                if (opponentLastMove == this.myField) {
                    this.playGreedy = true;
                    return bestMove;
                } else {
                    if (fields.get(this.myField) < this.xThreshHold) {
                        this.myLastMove = this.commonField;
                        return this.commonField;
                    } else {
                        this.myLastMove = this.myField;
                        return this.myField;
                    }
                }
            } else {
                this.myLastMove = randomMove;
                return randomMove;
            }
        }
    }

    /**
     * convertFieldsToHashMap method is used for xValues of the fields to
     * HashMap conversion.
     *
     * @param xA - xValue on first field
     * @param xB - xValue on second field
     * @param xC - xValue on third field
     * @return - resulting hashmap describing fields
     */
    private HashMap<Integer, Integer> convertFieldsToHashMap(int xA, int xB, int xC) {
        HashMap<Integer, Integer> fields = new HashMap<Integer, Integer>();
        fields.put(1, xA);
        fields.put(2, xB);
        fields.put(3, xC);
        return fields;
    }

    /**
     * getRandomField method is used to get random field number out of HashMap
     * which describes the fields to choose from
     *
     * @param fields - hashmap describing fields
     * @return - number of chosen field
     */
    private int getRandomField(HashMap<Integer, Integer> fields) {
        ArrayList<Integer> possibleMoves = new ArrayList<>(fields.keySet());
        return possibleMoves.get(this.randomNumberGenerator.nextInt(possibleMoves.size()));
    }

    /**
     * getBestFields method is used to get fields with greatest xValues
     *
     * @param fields - hashmap describing fields
     * @return - resulting hashmap describing fields best xValues
     */
    private HashMap<Integer, Integer> getBestFields(HashMap<Integer, Integer> fields) {
        int maxValue = -1;
        HashMap<Integer, Integer> bestFields = new HashMap<Integer, Integer>();

        for (HashMap.Entry<Integer, Integer> field : fields.entrySet()) {
            if (field.getValue() > maxValue) {
                bestFields.clear();
                maxValue = field.getValue();
                bestFields.put(field.getKey(), field.getValue());
            } else if (field.getValue() == maxValue) {
                bestFields.put(field.getKey(), field.getValue());
            }
        }

        return bestFields;
    }

    /**
     * getSecondBestField method is used to get second field number with
     * greatest xValue from the HashMap which describes the fields to choose
     * from
     *
     * @param fields - hashmap describing fields
     * @return - number of chosen field
     */
    private int getSecondBestField(HashMap<Integer, Integer> fields) {
        HashMap<Integer, Integer> bestFields = this.getBestFields(fields);
        HashMap<Integer, Integer> middleFields = this.getMiddleFields(fields);

        if (bestFields.size() >= 2) {
            return this.getRandomField(bestFields);
        } else {
            return this.getRandomField(middleFields);
        }
    }

    /**
     * getMiddleFields method is used to get fields with middle xValues
     *
     * @param fields - hashmap describing fields
     * @return - resulting hashmap describing fields with middle xValues
     */
    private HashMap<Integer, Integer> getMiddleFields(HashMap<Integer, Integer> fields) {
        HashMap<Integer, Integer> bestFields = this.getBestFields(fields);
        HashMap<Integer, Integer> notBestFields = new HashMap<>(fields);
        notBestFields.entrySet().removeAll(bestFields.entrySet());

        if (notBestFields.size() == 0) {
            return bestFields;
        }
        return this.getBestFields(notBestFields);
    }

    /**
     * getNonZeroFields method is used to get fields with non-zero xValues
     *
     * @param fields - hashmap describing fields
     * @return - resulting hashmap describing fields with non-zero xValues
     */
    private HashMap<Integer, Integer> getNonZeroFields(
            HashMap<Integer, Integer> fields
    ) {
        HashMap<Integer, Integer> nonZeroFields = new HashMap<>();

        for (HashMap.Entry<Integer, Integer> field : fields.entrySet()) {
            if (field.getValue() != 0) {
                nonZeroFields.put(field.getKey(), field.getValue());
            }
        }

        return nonZeroFields;
    }

    /**
     * This method returns my IU email
     *
     * @return my email
     */
    @Override
    public String getEmail() {
        return "k.rizatdinov@innopolis.university";
    }
}
