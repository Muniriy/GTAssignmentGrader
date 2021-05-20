package gametheory.assignment2.students20s;

import gametheory.assignment2.Player;

import java.util.Random;

public class EvgeniyTrantsevCode implements Player {
    @Override
    public void reset() {
        //Nothing to do here
    }

    /**
     * This method returns the move of the player using greedy algorithm
     * with a 50% chance to switch maximum to other field if they both
     * hame maximum value of X
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
        int[] fields = {xA, xB, xC};
        int max = 0;
        for (int i = 0; i < fields.length; i++) {
            if (fields[i] > fields[max]) {//if better go there
                max = i;
            } else if (fields[i] == fields[max] && new Random().nextBoolean()) {// if same toss a coin
                max = i;
            }
        }

        int res = max + 1;//offset from [0,2] to [1,3]

        return res;
    }

    @Override
    public String getEmail() {
        return "e.trantsev@innopolis.university";
    }
}
