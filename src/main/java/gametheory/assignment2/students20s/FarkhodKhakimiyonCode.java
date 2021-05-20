package gametheory.assignment2.students20s;

import gametheory.assignment2.Player;

import java.util.Random;

public class FarkhodKhakimiyonCode implements Player {
    //generate a random number
    static Random rand = new Random();

    private static int random_dummy_move(int opponentLastMove, int xA, int xB, int xC) {
        int C = xA + xB + xC;

        if (C == 0) {
            return (rand.nextInt(Integer.MAX_VALUE) % 3 + 1);
        }

        int cur = (rand.nextInt(Integer.MAX_VALUE) % C);
        if (cur < xA) {
            return 1;
        } else if (cur < xA + xB) {
            return 2;
        }
        return 3;
    }

    @Override
    public String getEmail() {
        return "f.khakimiyon@innopolis.university";
    }

    @Override
    public void reset() {

    }

    @Override
    public int move(int opponentLastMove, int xA, int xB, int xC) {
        return random_dummy_move(opponentLastMove, xA, xB, xC);
    }

}
