package gametheory.assignment2.students2020;

import gametheory.assignment2.Player;

import java.util.ArrayList;
import java.util.Arrays;

public class AlmirMullanurovCode implements Player {

    public ArrayList<Integer> memorizedMoves, memorizedOpponentMoves;

    @Override
    public void reset() {
        memorizedMoves = new ArrayList<Integer>();
        memorizedOpponentMoves = new ArrayList<Integer>();
        // Add random move on the first step for Copy opponent handle
        memorizedMoves.add(1 + (int)(Math.random() * 3));
    }

    @Override
    public int move(int opponentLastMove, int xA, int xB, int xC) {
        memorizedOpponentMoves.add(opponentLastMove);
        double resultA = 0, resultB = 0, resultC = 0;

        // Sorted array of fields
        int a[] = {xA, xB, xC};
        Arrays.sort(a);

        // Max opponent (Assuming that if many mx, the left one will be chosen)
        if (xA == a[2]) {
            resultB += f(xB);
            resultC += f(xC);
        } else if (xB == a[2]) {
            resultA += f(xA);
            resultC += f(xC);
        } else if (xC == a[2]) {
            resultA += f(xA);
            resultB += f(xB);
        }

        // Middle opponent
        if (xA == a[1]) {
            resultB += f(xB);
            resultC += f(xC);
        } else if (xB == a[1]) {
            resultA += f(xA);
            resultC += f(xC);
        } else if (xC == a[1]) {
            resultA += f(xA);
            resultB += f(xB);
        }

        // Random opponent
        resultA += 2.0 / 3 * f(xA);
        resultB += 2.0 / 3 * f(xB);
        resultC += 2.0 / 3 * f(xC);

        // Random2Max opponent
        if (xA == a[0]) {
            resultA += f(xA);
            resultB += 1.0 / 2 * f(xB);
            resultC += 1.0 / 2 * f(xC);
        } else if (xB == a[0]) {
            resultA += 1.0 / 2 * f(xA);
            resultB += f(xB);
            resultC += 1.0 / 2 * f(xC);
        } else if (xC == a[0]) {
            resultA += 1.0 / 2 * f(xA);
            resultB += 1.0 / 2 * f(xB);
            resultC += f(xC);
        }

        // Copy opponent
        int copyMove = memorizedMoves.get(memorizedMoves.size() - 1);
        if (copyMove == 1) {
            resultB += f(xB);
            resultC += f(xC);
        } else if (copyMove == 2) {
            resultA += f(xA);
            resultC += f(xC);
        } else if (copyMove == 3) {
            resultA += f(xA);
            resultB += f(xB);
        }

        if (resultA >= resultB && resultA >= resultC) {
            memorizedMoves.add(1);
        } else if (resultB >= resultC) {
            memorizedMoves.add(2);
        } else {
            memorizedMoves.add(3);
        }
        return memorizedMoves.get(memorizedMoves.size() - 1);
    }

    @Override
    public String getEmail() {
        return "a.mullanurov@innopolis.ru";
    }

    private double f(int x){
        double e = 2.71828;
        return (10 * Math.pow(e, x)) / (1 + Math.pow(e, x)) - (10 * Math.pow(e, 0)) / (1 + Math.pow(e, 0));
    }
}
