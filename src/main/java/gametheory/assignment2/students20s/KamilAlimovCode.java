package gametheory.assignment2.students20s;

import gametheory.assignment2.Player;

import java.util.concurrent.ThreadLocalRandom;

public class KamilAlimovCode implements Player {
    /**
     * do nothing for this algorithm.
     */
    @Override
    public void reset() {

    }

    /**
     * @param opponentLastMove it is last move of your opponent.
     * @param xA               It is first field with value.
     * @param xB               It is second field with value.
     * @param xC               It is third field with value.
     * @return It is return algorithm decision.
     * sum It is sum of all values for our fields.
     * outrandom It is random value in range of sum(1..sum)
     * <p>
     * There are three fields, and in order to choose the best, we consider each field as a probability.
     */
    @Override
    public int move(int opponentLastMove, int xA, int xB, int xC) {
        int sum = xA + xB + xC;
        int outrandom = ThreadLocalRandom.current().nextInt(1, sum + 1);
        if (outrandom <= xA)
            return 1;
        else if (outrandom <= xA + xB)
            return 2;
        else
            return 3;
    }

    /**
     * This function get Email from innopolis post.
     *
     * @return
     */
    @Override
    public String getEmail() {
        return "k.alimov@innopolis.ru";
    }
}
