package gametheory.assignment2.students2021;

import gametheory.assignment2.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Implementation of Player for Moose's game.
 * It chooses the best field as frequent as his
 * opponent does, until all fields have
 * coefficient bigger than 5. In this case it
 * chooses randomly between the fields taking
 * into consideration their coefficients.
 *
 * @author Maxim Evgrafov
 * @version 1.0 11 March 2021
 */
public class MaximEvgrafovCode implements Player {

    List<Integer> opponentsMoves = new ArrayList<>();
    List<Integer> opponentsBest = new ArrayList<>();

    List<Integer> prev_xs = new ArrayList<>();

    /**
     * This method is called to reset the agent before the match
     * with another player containing several rounds
     */
    @Override
    public void reset() {
        this.opponentsMoves.clear();
        this.opponentsBest.clear();
        this.prev_xs.clear();
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
        if (opponentLastMove != 0) {
            this.opponentsMoves.add(opponentLastMove - 1);
            if (IsInside(opponentLastMove - 1, max_index(this.prev_xs))) {
                this.opponentsBest.add(1);
            } else {
                this.opponentsBest.add(0);
            }

            this.prev_xs.set(0, xA);
            this.prev_xs.set(1, xB);
            this.prev_xs.set(2, xC);

        } else {
            this.prev_xs.add(xA);
            this.prev_xs.add(xB);
            this.prev_xs.add(xC);

            return new Random().nextInt(3) + 1;
        }

        double rate = 0;
        for (Integer integer : this.opponentsBest) {
            if (integer == 1) {
                rate += 1;
            }
        }

        rate /= this.opponentsBest.size();
        rate *= 100;

        if (xA > 5 && xB > 5 && xC > 5) {
            rate /= 2;
        }

        long int_rate = Math.round(rate);

        Random random = new Random();
        int random_choice = random.nextInt(100);

        if (random_choice < int_rate) {
            return max_index(this.prev_xs).get(random.nextInt(max_index(this.prev_xs).size())) + 1;
        } else {

            double x_sum = xA + xB + xC;

            long A_weight = Math.round((xA / x_sum) * 100);
            long B_weight = Math.round((xB / x_sum) * 100);

            int random_choice_second = random.nextInt(100);

            if (random_choice_second < A_weight) {
                return 1;
            } else if (A_weight + B_weight > random_choice_second) {
                return 2;
            } else {
                return 3;
            }
        }
    }

    /**
     * This method returns your IU email
     *
     * @return your email
     */
    @Override
    public String getEmail() {
        return "m.evgrafov@innopolis.university";
    }

    /**
     * This method returns indexes of items with the biggest
     * value from the list.
     *
     * @param xs list in which the search will be made
     * @return list that contains integers - indexes
     * where the biggest numbers from
     * the list are
     */
    private List<Integer> max_index(List<Integer> xs) {
        int max_x = Collections.max(xs);
        List<Integer> indexes = new ArrayList<>();
        for (int i = 0; i < xs.size(); i++) {
            if (xs.get(i) == max_x) {
                indexes.add(i);
            }
        }
        return indexes;
    }

    /**
     * This method checks if an integer is the the list
     *
     * @param item  the item that we want to find in the list (Integer)
     * @param items the list in which we want to find the item (List < Integer > )
     * @return true if item is in the list, false if not.
     */
    private boolean IsInside(Integer item, List<Integer> items) {
        for (Integer integer : items) {
            if (integer.equals(item)) {
                return true;
            }
        }
        return false;
    }
}

