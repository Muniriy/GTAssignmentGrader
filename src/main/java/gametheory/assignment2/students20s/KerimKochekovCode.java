package gametheory.assignment2.students20s;

import gametheory.assignment2.Player;

public class KerimKochekovCode implements Player {
    final double alpha = Math.exp(1);
    double H;

    public void reset() {
        this.H = 1.0;
    }

    public int move(int opponentLastMove, int xA, int xB, int xC) {
        int[] arr = {xA, xB, xC};
        int[] idx = {0, 1, 2};
        for (int i = 0; i < 3; i++)
            for (int j = i + 1; j < 3; j++)
                if (arr[idx[i]] > arr[idx[j]]) {
                    int tmp = idx[i];
                    idx[i] = idx[j];
                    idx[j] = tmp;
                }
        double any = Math.random();
        int res = 1;
        if (any < H / 0.01)
            res = idx[0];
        else {
            any -= H;
            if (any < 1.0 / (H + alpha))
                res = idx[1];
            else
                res = idx[2];
        }
        H /= (H + alpha);
        return res + 1;
    }

    public String getEmail() {
        return "k.kochekov@innopolis.university";
    }
}
