package gametheory.assignment2.students2021;

import gametheory.assignment2.Player;


public class KamilKamalievCode implements Player {
    int my_last_position;
    int[] food;

    @Override
    public void reset() {
        this.my_last_position = 0;
        this.food = new int[4];
    }

    @Override
    public int move(int opponentLastMove, int xA, int xB, int xC) {
        double sum = xA + xB + xC;
        double probA = xA / sum;
        double probB = xB / sum;
        double n = Math.random();

        if (n < probA) {
            return 1;
        } else if (n < probA + probB) {
            return 2;
        } else {
            return 3;
        }
    }

    @Override
    public String getEmail() {
        return "k.kamaliev@innopolis.university";
    }
}
