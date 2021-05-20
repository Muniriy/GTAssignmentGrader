package gametheory.assignment2.students20s;

import gametheory.assignment2.Player;

import java.util.concurrent.ThreadLocalRandom;


/**
 * Player that always moves randomly
 */
public class DmitryPodpryatovCode implements Player {

    @Override
    public void reset() {
    }

    @Override
    public int move(int opponentLastMove, int xA, int xB, int xC) {
        return randomMove();
    }

    @Override
    public String getEmail() {
        return "d.podpryatov@innopolis.university";
    }

    /**
     * Generate random move
     *
     * @return random integer from 1 to 3
     * @see <a href="https://stackoverflow.com/a/363692">https://stackoverflow.com/a/363692</a>
     * for a reference
     */
    private int randomMove() {
        int min = 1;
        int max = 3;

        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }
}
