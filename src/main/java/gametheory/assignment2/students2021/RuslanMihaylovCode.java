package gametheory.assignment2.students2021;

import gametheory.assignment2.Player;

import java.util.ArrayList;
import java.util.Random;

/**
 * Class of player that implements my selected strategy
 * of aggressive/balanced behaviour
 */
public class RuslanMihaylovCode implements Player {
    /**
     * Generator of random numbers
     */
    Random RNG = new Random();

    /**
     * Function to determine which field with maximum value to go to
     *
     * @param fields array of <b>Field</b> structures which represent the state of game now
     * @return returns a number of random field with maximum value
     * @see Field#Field(int, int)
     */
    public int randomMax(Field[] fields) {
        int maxField = -1;
        ArrayList<Field> maxCandidates = new ArrayList<>();
        // going through all fields
        for (Field field : fields) {
            // adding field to the list if it contains the maximum value
            if (field.value == maxField) maxCandidates.add(field);
                // discarding the whole list if we find a greater value
            else if (field.value > maxField) {
                maxField = field.value;
                maxCandidates.clear();
                maxCandidates.add(field);
            }
        }
        // returning random of fields with maximum values
        return maxCandidates.get(RNG.nextInt(maxCandidates.size())).number;
    }

    /**
     * Function to determine which field with all values but maximum value to go to
     *
     * @param fields array of <b>Field</b> structures which represent the state of game now
     * @return returns a number of random field which doesn't contain the maximum value
     * @see Field#Field(int, int)
     */
    public int randomNotMax(Field[] fields) {
        int maxField = -1;
        // finding maximum value
        for (Field field : fields) {
            if (field.value > maxField) {
                maxField = field.value;
            }
        }
        ArrayList<Field> notMaxCandidates = new ArrayList<>();
        // going through fields again to exclude fields with maximums from the list
        for (Field field : fields) if (field.value < maxField) notMaxCandidates.add(field);
        // if all values are maximums we don't care which value to return
        if (notMaxCandidates.size() == 0) return RNG.nextInt(3) + 1;
            // else we just return number of any field in the list
        else return notMaxCandidates.get(RNG.nextInt(notMaxCandidates.size())).number;
    }

    /**
     * Function to determine which field with minimum value to go to
     *
     * @param fields array of <b>Field</b> structures which represent the state of game now
     * @return returns a number of random field with minimum value
     * @see Field#Field(int, int)
     */
    public int randomMin(Field[] fields) {
        int minField = 2147483647;
        ArrayList<Field> minCandidates = new ArrayList<>();
        // going through fields
        for (Field field : fields) {
            // if values coincide we add it to our list
            if (field.value == minField) minCandidates.add(field);
                // if we find a better minimum we drop the whole list and start again
            else if (field.value < minField) {
                minField = field.value;
                minCandidates.clear();
                minCandidates.add(field);
            }
        }
        // returning number of any fields with minimum value of field
        return minCandidates.get(RNG.nextInt(minCandidates.size())).number;
    }

    /**
     * Function to determine which field with second maximum value to go to
     *
     * @param fields array of <b>Field</b> structures which represent the state of game now
     * @return returns a number of random field with second maximum value
     * @see Field#Field(int, int)
     */
    public int randomSecondMax(Field[] fields) {
        int curMax = -1;
        // detecting maximum value of field
        for (Field field : fields) if (field.value > curMax) curMax = field.value;

        ArrayList<Field> secondCandidates = new ArrayList<>();
        // eliminating fields which contain maximum value
        for (Field field : fields) if (field.value < curMax) secondCandidates.add(field);

        // if all values are maximums then we don't have a middle value
        // hence, we're indifferent in our field choice
        if (secondCandidates.size() == 0) return RNG.nextInt(3) + 1;
        // if we have only one value less than maximum, then it's the one we need
        if (secondCandidates.size() == 1) return secondCandidates.get(0).number;
        // if we have two equaL values less than maximum we return either of them
        if (secondCandidates.get(0).value == secondCandidates.get(1).value) {
            return secondCandidates.get(RNG.nextInt(2)).number;
        }
        // if those two values are not equal we return the value of a bigger one
        if (secondCandidates.get(0).value > secondCandidates.get(1).value) {
            return secondCandidates.get(0).number;
        }
        return secondCandidates.get(1).number;
    }

    /**
     * Function resets the player so it is ready to participate in a new game
     */
    public void reset() {
        RNG = new Random();
    }

    /**
     * Function determines a move for a player agent
     *
     * @param opponentLastMove number of field that opponent has chosen last time
     * @param xA               value on field A
     * @param xB               value on field B
     * @param xC               value on field C
     * @return move of a player given current situation on fields and opponent's last move
     */
    public int move(int opponentLastMove, int xA, int xB, int xC) {
        Field[] fields = {
                new Field(1, xA),
                new Field(2, xB),
                new Field(3, xC)
        };

        int grown = 4;
        ArrayList<Field> grownFields = new ArrayList<>();
        // adding to the list only fields which have grown more than 4 in value
        for (Field field : fields) if (field.value >= grown) grownFields.add(field);

        // if there's not fields which have grown more than 4 in value
        // then agent just goes for the random maximum value
        if (grownFields.size() == 0) return randomMax(fields);
            // else we take random of those grown fields as our next move
        else return grownFields.get(RNG.nextInt(grownFields.size())).number;
    }

    /**
     * @return Innopolis email
     */
    public String getEmail() {
        return "r.mihaylov@innopolis.university";
    }

    /**
     * @return name of the player
     */
    public String getName() {
        return "Balanced Aggressor";
    }

    /**
     * Class of field for representing which number it has
     * and what value is associated with that field
     *
     * @see Field#Field(int, int)
     */
    static class Field {
        /**
         * number {1, 2, 3} of field
         */
        int number;
        /**
         * value which a field has
         */
        int value;

        /**
         * Constructor for <b>Field</b> class
         *
         * @param number number of field (1, 2, 3)
         * @param value  value of that field in concrete moment
         */
        public Field(int number, int value) {
            this.number = number;
            this.value = value;
        }
    }
}