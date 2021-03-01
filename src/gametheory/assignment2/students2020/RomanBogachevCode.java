package gametheory.assignment2.students2020;

import gametheory.assignment2.Player;

import java.util.concurrent.ThreadLocalRandom;

public class RomanBogachevCode implements Player {
    // chooses field randomly
    @Override
    public void reset() {
        ;
    }

    @Override
    public int move(int opponentLastMove, int xA, int xB, int xC) {
        return ThreadLocalRandom.current().nextInt(1, 3 + 1);
    }

    @Override
    public String getEmail(){
        return "r.bogachev@innopolis.ru";
    }
}