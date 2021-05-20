package gametheory.assignment2.students2021;

import gametheory.assignment2.Player;

import java.util.Random;


public class UtkarshKalraCode implements Player {
    public static int get_max2(int xA, int xB, int xC) {
        int min = Math.min(Math.min(xA, xB), xC);
        Random rand = new Random();
        if (xA == xB && xA == xC) {
            int rand_int1 = rand.nextInt(4);
            if (rand_int1 != 0) {
                return rand_int1;
            } else {
                return 2;
            }
        } else if (min == xA) {
            int randomOfTwoInts = new Random().nextBoolean() ? xB : xC;
            if (randomOfTwoInts == xB) {
                return 2;
            } else {
                return 3;
            }
        } else if (min == xB) {
            int randomOfTwoInts = new Random().nextBoolean() ? xA : xC;
            if (randomOfTwoInts == xA) {
                return 1;
            } else {
                return 3;
            }
        } else {
            int randomOfTwoInts = new Random().nextBoolean() ? xA : xB;
            if (randomOfTwoInts == xA) {
                return 1;
            } else {
                return 2;
            }
        }


    }

    @Override
    public void reset() {

    }

    @Override
    public int move(int opponentLastMove, int xA, int xB, int xC) {
        return get_max2(xA, xB, xC);
    }

    @Override
    public String getEmail() {
        String email = "u.kalra@innopolis.university";
        return email;
    }
}
