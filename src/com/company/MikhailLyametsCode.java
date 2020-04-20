package com.company;


//import com.company.players.Player;

import java.util.Random;

public class MikhailLyametsCode implements Player {

    int[] freq = new int[]{0, 0, 0};

    @Override
    public void reset() {
        freq = new int[]{0, 0, 0};
    }

    @Override
    public int move(int opponentLastMove, int xA, int xB, int xC) {
        ++freq[opponentLastMove % 3];

        int min_index = 0;
        for (int i = 0; i < freq.length; ++i) {
            if (freq[i] < freq[min_index])
                min_index = i;
        }
        ++freq[min_index];


        int[] grass = {xA, xB, xC};
        int index_max = 0;
        for (int i = 0; i < grass.length; ++i) {
            if (grass[i] > grass[index_max])
                index_max = i;
        }

        int move = index_max;

        if (index_max != min_index) {
            Random rand = new Random();
            if (rand.nextInt(1) == 0)
                move = index_max;
            else
                move = min_index;
        }

        return move + 1;
    }

    public String getEmail() {
        return "m.lyamets@innopolis.ru";
    }

    public String toString() {
        return "PlayerGreedyFreqRand";
    }
}
