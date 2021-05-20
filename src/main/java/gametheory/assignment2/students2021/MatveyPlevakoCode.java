package gametheory.assignment2.students2021;

import gametheory.assignment2.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * trickyGreedy class
 *
 * @autor Matvey Plevako
 */
public class MatveyPlevakoCode implements Player {

    @Override
    public void reset() {

    }

    /**
     * @param opponentLastMove the last move of the opponent
     *                         varies from 0 to 3
     *                         (0 – if this is the first move)
     * @param xA               the argument X for a field A
     * @param xB               the argument X for a field B
     * @param xC               the argument X for a field C
     * @return move by player
     */
    @Override
    public int move(int opponentLastMove, int xA, int xB, int xC) {
        List<Integer> list = new ArrayList<>();
        list.add(xA);
        list.add(xB);
        list.add(xC);

        List<Integer> maxNumbers = new ArrayList<>();
        int maxAt = 0;


        // Find the maximum numbers and their indexes
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).equals(list.get(maxAt))) {
                maxNumbers.add(i);
            } else if (list.get(i) > list.get(maxAt)) {
                maxNumbers = new ArrayList<>();
                maxNumbers.add(i);
                maxAt = i;
            }
        }

        // Find all fields values are greater than 10, pick the lowest one
        if (xA >= 10 && xB >= 10 && xC >= 10) {
            List<Integer> minNumbers = new ArrayList<>();
            int minAt = 0;

            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).equals(list.get(minAt))) {
                    minNumbers.add(i);
                } else if (list.get(i) < list.get(minAt)) {
                    minNumbers = new ArrayList<>();
                    minNumbers.add(i);
                    minAt = i;
                }
            }

            Random rand = new Random();
            return minNumbers.get(rand.nextInt(minNumbers.size())) + 1;
        } else {
            // Otherwise return maximum
            Random rand = new Random();
            return maxNumbers.get(rand.nextInt(maxNumbers.size())) + 1;
        }
    }

    /**
     * @return email of student
     */
    @Override
    public String getEmail() {
        return "m.plevako@innopolis.university";
    }
}

