package gametheory.assignment2.students2020;

import gametheory.assignment2.Player;

import java.util.concurrent.ThreadLocalRandom;

public class SemenKiselevCode implements Player {
    /**
     * copy-last: choose the field, that was chosen by your opponent on the previous move,
     * choose random (uniformly) field as the first move;
     */
    @Override
    public void reset() {
    }

    @Override
    public int move(int opponentLastMove, int xA, int xB, int xC) {

        return opponentLastMove > 0 ? opponentLastMove : ThreadLocalRandom.current().nextInt(1, 3 + 1);
    }

    @Override
    public String getEmail() {
        return "s.kiselev@innopolis.ru";
    }
}