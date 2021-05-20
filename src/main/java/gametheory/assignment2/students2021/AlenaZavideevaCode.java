package gametheory.assignment2.students2021;

import gametheory.assignment2.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static java.lang.Math.max;

public class AlenaZavideevaCode implements Player {
    /*
    The strategy Greedy and Flunky:
    Firstly, try to find cells that has a score more than 3,
    if there are no such cell, be greedy. Otherwise, choose one
    randomly among the cells.
    Be greedy means choose the cell with the max score, if there
    is more than one cell, choose one randomly among them.
     */

    Random random = new Random();

    public int greedy(int xA, int xB, int xC) {
        // Find max score
        int[] fields = new int[]{xA, xB, xC};
        int m = max(max(xA, xB), xC);
        List<Integer> maxCells = new ArrayList<>();

        // Find cells that max score
        for (int i = 0; i < fields.length; i++) {
            if (fields[i] == m) maxCells.add(i + 1);
        }
        // Return random cell among cells with the max score
        return maxCells.get(random.nextInt(maxCells.size()));
    }

    @Override
    public void reset() {
        random = new Random();
    }

    @Override
    public int move(int opponentLastMove, int xA, int xB, int xC) {
        // Find flunky cell - score more than 3
        int[] fields = new int[]{xA, xB, xC};
        List<Integer> flunkyCells = new ArrayList<>();
        for (int i = 0; i < fields.length; i++) {
            if (fields[i] > 3) flunkyCells.add(i + 1);
        }
        // If no flunky cell, be greedy
        if (flunkyCells.size() == 0) {
            return greedy(xA, xB, xC);
        }
        // Choose random flunky cell
        return flunkyCells.get(random.nextInt(flunkyCells.size()));

    }

    @Override
    public String getEmail() {
        return "a.zavideeva@innopolis.university";
    }
}
