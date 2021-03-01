package gametheory.assignment2.students2020;

import gametheory.assignment2.Player;

import java.util.Random;

// This agent uses "Round Robin" approach, or simply go
// through all fields subsequently
public class PeterZakharkinCode implements Player {

    // Remember last move to calculate next one
    private int lastMove;

    @Override
    public void reset() {
        // First move is selected randomly to help when playing
        // with other Round Robin agent
        lastMove = (new Random()).nextInt(3);
    }

    @Override
    public int move(int opponentLastMove, int xA, int xB, int xC) {
        // Then, next move is last + 1 by modulo 3
        lastMove = (lastMove + 1) % 3;
        return lastMove + 1;
    }

    @Override
    public String getEmail() {
        return "p.zaharkin@innopolis.ru";
    }

}
