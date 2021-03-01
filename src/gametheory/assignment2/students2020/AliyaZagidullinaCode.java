package gametheory.assignment2.students2020;

import gametheory.assignment2.Player;

import java.util.Random;

public class AliyaZagidullinaCode implements Player {

    @Override
    public void reset() {

    }

    @Override
    public int move(int opponentLastMove, int xA, int xB, int xC) {
        return Math.abs(new Random().nextInt()) % 3 + 1;
    }

    @Override
    public String getEmail() {
        return "a.zagidullina@innopolis.ru";
    }
}
