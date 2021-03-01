package gametheory.assignment2.students2020;

import gametheory.assignment2.Player;

import java.util.Random;

public class ArtemIurinCode implements Player {

    @Override
    public void reset() {
    }

    @Override
    public int move(int opponentLastMove, int xA, int xB, int xC) {
        if (xA == xB && xB == xC) {
            return (new Random()).nextInt(3) + 1; // if all fields are equal then random Index
        }

        // choose index of field with max value
        int firstIndex = indexOfGrater(xA, xB, 1, 2);
        if (firstIndex == 1) {
            return indexOfGrater(xA, xC, 1, 3);
        } else {
            return indexOfGrater(xC, xB, 3, 2);
        }
    }

    @Override
    public String getEmail() {
        return "a.yurin@innopolis.ru";
    }

    /**
     * util function for choose index
     * @param xA - value of first field
     * @param xB - value of second value
     * @param indexA - index of first field
     * @param indexB - index of second field
     * @return indexA or indexB
     */
    private int indexOfGrater(int xA, int xB, int indexA, int indexB) {
        if (xA > xB) {
            return indexA;
        } else if (xA < xB) {
            return indexB;
        } else {
            int d = (new Random()).nextInt(2);

            if (d == 0) {
                return indexA;
            }

            return indexB;
        }
    }
}
