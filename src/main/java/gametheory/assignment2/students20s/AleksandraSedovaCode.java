package gametheory.assignment2.students20s;

import gametheory.assignment2.Player;

import java.util.Random;

// min algorithm which always chooses any non-zero, non-maximum value on the field

public class AleksandraSedovaCode implements Player {

    @Override
    public void reset() {
    }

    @Override
    public int move(int opponentLastMove, int xA, int xB, int xC) {
        int min = 0;
        if (xA != 0 && xB != 0 && xC != 0) {
            min = Math.min(xA, Math.min(xB, xC));
        }
        if (xA == 0 && xB == 0 && xC == 0) {
            return new Random().nextInt(3) + 1;
        }
        if (xA != 0 && xB != 0 && xC == 0) {
            min = Math.min(xA, xB);
        }
        if (xA != 0 && xB == 0 && xC != 0) {
            min = Math.min(xA, xC);
        }
        if (xA == 0 && xB != 0 && xC != 0) {
            min = Math.min(xB, xC);
        }
        if (xA != 0 && xB == 0 && xC == 0) {
            min = xA;
        }
        if (xA == 0 && xB != 0 && xC == 0) {
            min = xB;
        }
        if (xA == 0 && xB == 0 && xC != 0) {
            min = xC;
        }
        if (min == xA) {
            return 1;
        } else if (min == xB) {
            return 2;
        } else {
            return 3;
        }
    }

    @Override
    public String getEmail() {
        String email = "a.sedova@innopolis.ru";
        return email;
    }

}