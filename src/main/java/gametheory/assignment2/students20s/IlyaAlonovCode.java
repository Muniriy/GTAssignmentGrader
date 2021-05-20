package gametheory.assignment2.students20s;

import gametheory.assignment2.Player;

import java.util.Random;

public class IlyaAlonovCode implements Player {
    @Override
    public String getEmail() {
        return "i.alonov@innopolis.ru";
    }

    @Override
    public void reset() {

    }

    @Override
    public int move(int opponentLastMove, int xA, int xB, int xC) {
        final Random random = new Random();
        return random.nextInt(3) + 1;
    }
}
