package gametheory.assignment2.students2020;

import gametheory.assignment2.Player;

/**
 * Implementation of Player for Game Theory Assignment-2. That is Greedy player. It means that it always goes to
 * the field whose value is greater than others. If there are few such fields it simply goes to first it found.
 *
 * @author Igor Vakhula
 */
public class IgorVakhulaCode implements Player {

    @Override
    public void reset() {
    }

    @Override
    public int move(int opponentLastMove, int xA, int xB, int xC) {
        if (xA >= xB && xA >= xC) {
            return 1;
        } else if (xB >= xA && xB >= xC) {
            return 2;
        } else {
            return 3;
        }
    }

    @Override
    public String getEmail() {
        return "i.vahula@innopolis.ru";
    }

}
