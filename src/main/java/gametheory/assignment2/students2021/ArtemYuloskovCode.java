package gametheory.assignment2.students2021;

import gametheory.assignment2.Player;

import java.util.ArrayList;
import java.util.Random;

/**
 * Player for the game Feed The Moose.
 * This player implements GreedyRandom strategy.
 * It takes a random move, if the lowest X is
 * more than 2, and takes a maximal cell otherwise.
 */
public class ArtemYuloskovCode implements Player {
    // Initialize random
    Random random = new Random();

    /**
     * Reset method, should be called before new game.
     * In my strategy it is empty,
     * as I'm not utilizing previous game states.
     */
    @Override
    public void reset() {

    }

    /**
     * Make a move based on the state of the game and last opponent move.
     *
     * @param opponentLastMove - Last move of the opponent.
     * @param xA               - X value in the first cell.
     * @param xB               - X value in the second cell.
     * @param xC               - X value in the third cell.
     * @return 1, 2 or 3 - the cell of choice.
     */
    @Override
    public int move(int opponentLastMove, int xA, int xB, int xC) {
        // Initialize an array of X values
        int[] field = new int[]{xA, xB, xC};

        int lowestX = Math.min(Math.min(xA, xB), xC);
        if (lowestX > 2) {
            // Return random if lowest X is more than 2
            return random.nextInt(3) + 1;
        } else {
            // Return random of maximums otherwise

            // Index of maximal element
            int maxAt = 0;
            for (int i = 1; i < field.length; i++) {
                maxAt = field[i] > field[maxAt] ? i : maxAt;
            }

            // ArrayList of maximal indexes
            ArrayList<Integer> maxs = new ArrayList<>();

            // Add all maximal elements to ArrayList
            for (int i = 0; i < field.length; i++) {
                if (field[i] == field[maxAt]) {
                    maxs.add(i);
                }
            }

            // Choose random of maximums
            int randIndex = random.nextInt(maxs.size());
            return maxs.get(randIndex) + 1;
        }
    }

    /**
     * @return my email.
     */
    @Override
    public String getEmail() {
        return "a.yuloskov@innopolis.university";
    }
}
