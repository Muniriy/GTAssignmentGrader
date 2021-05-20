package gametheory.assignment2.students20s;

import gametheory.assignment2.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ShamilHastievCode implements Player {
    public int last_move = 0; //my previous move
    public int my_field = -1; // field for eating if we communicate
    public int sleep_field = 0;
    public boolean coop = true; //are we communicating

    @Override
    public void reset() {
        last_move = 0;
        my_field = -1;
        sleep_field = 0;
        coop = true;
    }

    @Override
    public int move(int opponentLastMove, int xA, int xB, int xC) {
        int[] weight = {xA, xB, xC};
        if (opponentLastMove == my_field) { // check if opponent breaks cooperation
            coop = false;
        }
        if (coop) {
            return cooperate(opponentLastMove, weight); // use cooperation if we are friends
        }
        return greedy(weight); // use greedy strategy if opponent do not want to cooperate

    }

    /**
     * This method returns the move of the player based on
     * the last move of the opponent and X values of all fields
     * using cooperate strategy
     *
     * @param opponentLastMove the last move of the opponent
     *                         varies from 0 to 3
     *                         (0 – if this is the first move)
     * @param weight           array of the argument X for a fields A,B,C
     * @return the move of the player can be 1 for A, 2 for B
     * and 3 for C fields
     */
    public int cooperate(int opponentLastMove, int[] weight) {
        if (last_move != opponentLastMove && my_field == -1) {
            my_field = last_move;
            sleep_field = (6 - my_field) - opponentLastMove;    //finding fields for feeding and sleeping
        }
        if (my_field == -1) {
            last_move = (int) (Math.random() * (2)) + 1;
            return last_move;
        }
        if (weight[my_field - 1] >= 6) { // if eating field has enough big value we eat else sleep
            return my_field;
        }
        return sleep_field;
    }

    /**
     * This method returns the move of the player based on
     * X values of all fields using random greedy strategy
     *
     * @param weight array of the argument X for a fields A,B,C
     * @return the move of the player can be 1 for A, 2 for B
     * and 3 for C fields
     */
    public int greedy(int[] weight) {
        int min = 999;
        int index = 1;
        List<Integer> indexes = new ArrayList() {{
            add(1);
            add(2);
            add(3);
        }};
        for (int i = 0; i < 3; i++) { //finding field with the smallest value
            if (weight[i] < min) {
                min = weight[i];
                index = i;
            }
        }
        indexes.remove(index);
        Random rand = new Random();
        index = indexes.get(rand.nextInt(indexes.size()));//choosing random field from the two biggest one
        return index;
    }

    @Override
    public String getEmail() {
        return "s.hastiev@innopolis.university";
    }
}
