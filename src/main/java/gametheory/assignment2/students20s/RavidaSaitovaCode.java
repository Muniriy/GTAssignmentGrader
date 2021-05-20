package gametheory.assignment2.students20s;

import gametheory.assignment2.Player;

import java.util.Random;

/**
 * This code implements myself created strategy for the Game of Moose. The approach is based on weights of each
 * field(X/total_value_in_regions) and randomly generated probability . If total value in fields equals 0,
 * algorithm cannot make any predictions and choose region randomly.
 * Otherwise, strategy is to make move according randomly generated probability, if generated value is less than
 * weight/probability of the first field we select 1'st region. If expression doesn't hold we compare to cumulative
 * weights/probabilities of first two fields to choose 2'nd region or 3'rd accordingly.
 */
public class RavidaSaitovaCode implements Player {
    @Override
    public void reset() {
    }

    /**
     * @param opponentLastMove opponent's last move
     * @param xA               amount of food in A field
     * @param xB               amount of food in B field
     * @param xC               amount of food in C field
     * @return which field to go
     */
    @Override
    public int move(int opponentLastMove, int xA, int xB, int xC) {
        Random rand = new Random();
        int total = xA + xB + xC;
        if (total == 0) {
            return rand.nextInt(3) + 1;
        }

        // Random double from 0 to 1
        double probability = rand.nextDouble();
        if (probability < (double) xA / total) {
            return 1;
        }
        if (probability >= (double) xA / total && probability < (double) xA / total + (double) xB / total) {
            return 2;
        }
        return 3;
    }

    /**
     * @return email of the strategy creator
     */
    @Override
    public String getEmail() {
        return "r.saitova@innopolis.university";
    }
}