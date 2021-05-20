package gametheory.assignment2.students20s;

import gametheory.assignment2.Player;

import java.util.ArrayList;
import java.util.Random;

public class AlbertBadretdinovCode implements Player {
    int round;

    public AlbertBadretdinovCode() {
        this.round = 0;
    }

    public void reset() {
        this.round = 0;
    }

    public int move(int opponent_last_move, int A, int B, int C) {
        if (this.round == 0) {
            Random rand = new Random();
            return rand.nextInt(3) + 1;
        }
        this.round += 1;
        ArrayList<Integer> cur = new ArrayList<>();
        cur.add(A);
        cur.add(B);
        cur.add(C);
        int min = 10000000;
        for (int i = 0; i < 3; i++) {
            if (cur.get(i) < min) {
                min = cur.get(i);
            }
        }
        int max = 0;
        for (int i = 0; i < 3; i++) {
            if (cur.get(i) > max) {
                max = cur.get(i);
            }
        }
        for (int i = 0; i < 3; i++) {
            if (cur.get(i) > min & cur.get(i) < max) {
                return i + 1;
            }

        }
        for (int i = 0; i < 3; i++) {
            if (cur.get(i) == max) {
                return i + 1;
            }

        }
        return 0;
    }

    public String getEmail() {
        return "al.badretdinov@mail.ru";
    }
}
