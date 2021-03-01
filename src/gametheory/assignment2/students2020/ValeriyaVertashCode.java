package gametheory.assignment2.students2020;

import gametheory.assignment2.Player;

import java.util.Arrays;

public class ValeriyaVertashCode implements Player {

    /*
    My agent goes to the second priority field (with not the biggest and not the smallest amount of grass)
    until the opponent will go not to the field with the biggest amount of grass twice. And if the opponent
    went not to the field with the biggest amount of grass twice then my agent always goes to the field
    with the biggest amount of grass.
    */

    // counter if the opponent went not to the best field
    private int opponentcount = 0;

    // reset all values
    @Override
    public void reset() {
        opponentcount = 0;
    }

    @Override
    public int move(int opponentLastMove, int xA, int xB, int xC) {

        // number of grass on fields on the previous round
        int prevxA = 0, prevxB = 0, prevxC = 0;

        // defining fields with first and second priority
        int first = 3;
        int second = 3;
        if (xA >= xB && xA >= xC) first = 1;
        if (xB >= xA && xB >= xC) first = 2;
        if ((xA >= xB && xA <= xC) || (xA >= xC && xA <= xB)) second = 1;
        if ((xB >= xA && xB <= xC) || (xB >= xC && xB <= xA)) second = 2;

        // increse opponentcount variable if opponent went not to the best field
        if (opponentLastMove != 0) {
            int[] prevfields = new int[3];
            prevfields[0] = prevxA;
            prevfields[1] = prevxB;
            prevfields[2] = prevxC;
            Arrays.sort(prevfields);
            if (opponentLastMove != prevfields[0]) {
                opponentcount += 1;
            }
        }

        // updating number of grass on fields
        prevxA = xA;
        prevxB = xB;
        prevxC = xC;

        // if it is the first move then agent go to the second priority field
        if (opponentLastMove == 0) {
            return second;
        } else {
            // if opponent went to the field 0 or 1 times then agent go to the second priority field
            if (opponentcount < 2) {
                return second;
            } else {
                // if opponent went to the best field 2 or more times then agent go to the best field
                return first;
            }
        }
    }

    @Override
    public String getEmail() {
        return "v.vertash@innopolis.com";
    }
}
