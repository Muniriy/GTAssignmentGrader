package gametheory.assignment2.students20s;

import gametheory.assignment2.Player;

import java.util.ArrayList;
import java.util.Random;


public class DaniilFrontsCode implements Player {

    Random rand;

    /**
     * This method is initial method, that creates
     * the new Random object for the agent.
     */
    public DaniilFrontsCode() {
        rand = new Random();
    }

    /**
     * This method is called to get random integer in range [a, b)
     */
    public int RandomInt(int min, int max) {
        return rand.nextInt(max - min) + min;
    }

    /**
     * This method is called to reset the Random object in agent.
     */
    @Override
    public void reset() {
        rand = new Random();
    }

    /**
     * This method is called to get all regions with maximimum
     * amount of food on them.
     */
    ArrayList<Integer> max(int[] areas) {
        int max = Integer.MIN_VALUE;
        ArrayList<Integer> max_indexes = new ArrayList<>();
        for (int i = 0; i < areas.length; i++) {
            if (areas[i] > max) {
                max = areas[i];
                max_indexes.clear();
                max_indexes.add(i);
            } else if (areas[i] == max) {
                max_indexes.add(i);
            }
        }
        return max_indexes;
    }

    /**
     * This method returns the move of Random Greedy player.
     * He is finding the regions with maximum amount of food.
     * If there are several such regions, he is choosing between by random.
     */
    @Override
    public int move(int opponentLastMove, int xA, int xB, int xC) {
        int[] areas = {-1, xA, xB, xC};
        int choice;
        ArrayList<Integer> choices = max(areas);
        if (choices.size() > 1) {
            choice = RandomInt(1, 4);
            while (!choices.contains(choice)) {
                choice = RandomInt(1, 4);
            }
        } else {
            choice = choices.get(0);
        }

        return choice;
    }

    @Override
    public String getEmail() {
        return "d.fronts@innopolis.university";
    }

}
