package gametheory.assignment2.students2020;

import gametheory.assignment2.Player;

import java.util.ArrayList;
import java.util.Random;

public class IlshatFatkhullinCode implements Player {
    private Random random;

    public IlshatFatkhullinCode() {
        random = new Random();
    }

    @Override
    public void reset() {

    }

    @Override
    public int move(int opponentLastMove, int xA, int xB, int xC) {
        // choose randomly between areas with maximum score
        int[] x = new int[] {xA, xB, xC};
        ArrayList<Integer> maxes = new ArrayList<>();
        int max = 0;
        for (int i = 0; i < x.length; i++) {
            if (x[i] > max) {
                max = x[i];
                maxes.clear();
                maxes.add(i);
            }
            else if (x[i] == max) {
                maxes.add(i);
            }
        }
        return maxes.get(random.nextInt(maxes.size())) + 1;
    }

    @Override
    public String getEmail() {
        return "i.fathullin@innopolis.ru";
    }
}
