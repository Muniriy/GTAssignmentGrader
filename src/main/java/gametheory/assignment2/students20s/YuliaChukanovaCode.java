package gametheory.assignment2.students20s;

import gametheory.assignment2.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class YuliaChukanovaCode implements Player {
    public YuliaChukanovaCode() {
    }

    public void reset() {
    }

    public int move(int opponentLastMove, int xA, int xB, int xC) {
        List<Integer> list = new ArrayList<>(3);
        int maxfood = Math.max(xA, xB);
        maxfood = Math.max(maxfood, xC);
        if (xA == maxfood)
            list.add(1);
        if (xB == maxfood)
            list.add(2);
        if (xC == maxfood)
            list.add(3);
        Random rand = new Random();
        return list.get(rand.nextInt(list.size()));
    }

    @Override
    public String getEmail() {
        return "y.chukanova@innopolis.university";
    }
}
