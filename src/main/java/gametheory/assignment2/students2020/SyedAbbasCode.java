package gametheory.assignment2.students2020;

import gametheory.assignment2.Player;

/**
 * This agent not only considers the maximum field value i-e the field which has maximum value, but also the possible gain.
 * The index for the field with max vegetation + possible gain is returned for the next move.
 * Thus, we not only consider the amount of vegetation but also takes into account the fact that the opponent might have reduced the vegetation in the last move.
 * This is our main agent, that will be used for the class tournament.
 */

public class SyedAbbasCode implements Player {

    /**
     *  The reset() method might be used to reset the player state after each game.
     */

    @Override
    public void reset() {

    }

    /**
     * The move() method returns the next move of the agent based on opponent's last move and field values.
     */

    @Override
    public int move(int opponentLastMove, int xA, int xB, int xC) {

        int[] f = {xA, xB, xC};
        double max = Integer.MIN_VALUE;
        int index = 0;

        for (int i = 0; i < f.length; i++) {

            double gain;

            if (opponentLastMove <= 0){
                gain = Math.abs(calculate_score(f[opponentLastMove]) - calculate_score(f[i]));
            } else {
                gain = Math.abs(calculate_score(f[opponentLastMove-1]) - calculate_score(f[i]));
            }

            if (max < f[i]){
                max = f[i] + gain;
                index = i;
            }
        }

        return index + 1;

    }

    /**
     *  The getEmail() method returns the student's email.
     */

    @Override
    public String getEmail() {
        return "s.abbas@innopolis.ru";
    }

    /**
     * Calculate score takes the value of field (amount available) and return the gains
     * @param x - The value of field i-e amount available
     * @return The gain / score
     */

    double calculate_score(int x){

        double score = (10 * Math.exp(x)) / (1 + Math.exp(x));
        score = score - 5; // f(0) is always = 5

        return score;
    }
}

/**
 * It's the interface for our agent. It's kept same for all the agents.
 */

//interface Player {
//
//    void reset();
//    int move(int opponentLastMove, int xA, int xB, int xC);
//    String getEmail();
//}