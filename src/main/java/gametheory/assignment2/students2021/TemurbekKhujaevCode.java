package gametheory.assignment2.students2021;


import gametheory.assignment2.Player;

import java.util.Random;
//Greedy distribution
//Assume that variables for 3 fields are A,B,C respectively
//select first field with probability A/(A+B+C)
//select second field with probability B/(A+B+C)
//select third field with probability C/(A+B+C)

public class TemurbekKhujaevCode implements Player {
    //random number generator
    private Random rng;

    public TemurbekKhujaevCode() {
        reset();
    }

    @Override
    public void reset() {
        rng = new Random();
    }

    @Override
    public int move(int opponentLastMove, int xA, int xB, int xC) {
        if (xA + xB + xC == 0) {
            int result = rng.nextInt(3) + 1;
            return result;
        }
        int rand = rng.nextInt(xA + xB + xC);
        if (rand >= xA) {
            if (rand < xA + xB) return 2;
            else return 3;
        } else return 1;
    }

    @Override
    public String getEmail() {
        return "t.xojayev@innopolis.university";
    }
}
