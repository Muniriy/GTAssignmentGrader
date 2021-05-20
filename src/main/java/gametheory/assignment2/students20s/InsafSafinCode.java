package gametheory.assignment2.students20s;

import gametheory.assignment2.Player;

import java.util.ArrayList;
import java.util.Random;

/**
 * This agent implements an algorithm that that tries to cooperate by taking one field to himself and giving
 * other field to his opponent. The third field is a waiting field. The agent sits in waiting field till
 * the amount of food on his field reaches 6. After that it alters to his own field and waiting field, so that each
 * 2 rounds the agent gets almost 5 points.
 * <p>
 * In case the opponent touches his field, the agent starts behaving like random, but chance to go to field is
 * proportional to the amount of food on that field.
 */


public class InsafSafinCode implements Player {

    private String state;
    private int my_field;
    private int op_field;
    private int buff_field;
    private boolean fields_divided;
    private int my_last;


    public InsafSafinCode() {
        state = "Collab";
        my_field = 0;
        op_field = 0;
        buff_field = 0;
        fields_divided = false;
        my_last = 0;
    }

    @Override
    public void reset() {
        state = "Collab";
        my_field = 0;
        op_field = 0;
        buff_field = 0;
        fields_divided = false;
        my_last = 0;
    }


    @Override
    public int move(int opponentLastMove, int xA, int xB, int xC) {

        if (state.equals("Collab")) {
            if (!fields_divided && my_last == 0) {
                int randint = randomAmong(new int[]{1, 2, 3});
                my_last = randint;
                return randint;
            } else if (!fields_divided && my_last != 0) {
                if (my_last == opponentLastMove) {
                    int randint = randomAmong(new int[]{1, 2, 3});
                    my_last = randint;
                    return randint;
                } else {
                    fields_divided = true;
                    my_field = my_last;
                    op_field = opponentLastMove;
                    buff_field = 6 - my_field - op_field;
                    return buff_field;
                }
            } else if (fields_divided && (opponentLastMove == op_field || opponentLastMove == buff_field)) {
                int treshold = 6;
                if (my_field == 1 && xA >= treshold) {
                    return my_field;
                } else if (my_field == 2 && xB >= treshold) {
                    return my_field;
                } else if (my_field == 3 && xC >= treshold) {
                    return my_field;
                } else {
                    return buff_field;
                }
            } else if (fields_divided && opponentLastMove == my_field) {
                state = "Greedy";
            }
        }
        if (state.equals("Greedy")) {
            int max = Math.max(Math.max(xA, xB), xC);
            ArrayList<Integer> temp_choice = new ArrayList<Integer>();

            for (int i = 0; i < xA; i++) {
                temp_choice.add(1);
            }
            for (int i = 0; i < xB; i++) {
                temp_choice.add(2);
            }
            for (int i = 0; i < xC; i++) {
                temp_choice.add(3);
            }
            int[] arr = temp_choice.stream().mapToInt(i -> i).toArray();
            return randomAmong(arr);
        }
        return 0;
    }

    @Override
    public String getEmail() {
        return "i.safin@innopolis.university";
    }

    /**
     * @param arr is the array of integer values
     * @return one of the values in from arr randomly returned
     */

    public int randomAmong(int[] arr) {
        int len = arr.length;
        Random rand = new Random();
        int val = rand.nextInt((len));
        return arr[val];
    }
}