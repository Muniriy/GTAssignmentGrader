package gametheory.assignment2.students20s;

import gametheory.assignment2.Player;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;

/**
 * Player that chooses randomly between fields with max value
 */
public class EvgeniaKivotovaCode implements Player {

    public EvgeniaKivotovaCode() {
        this.reset();
    }

    @Override
    public void reset() {

    }

    @Override
    public int move(int opponentLastMove, int xA, int xB, int xC) {
        int[][] fields = new int[][]{{1, xA}, {2, xB}, {3, xC}};
        //Sort fields by value, from highest to lowest
        Arrays.sort(fields, Comparator.comparingDouble(o -> o[1] * (-1)));

        // Pattern X X X
        if (fields[0][1] == fields[1][1] && fields[0][1] == fields[2][1]) {
            //Choose randomly
            Random r = new Random();
            return r.nextInt(3) + 1;
        }

        // Pattern X X Y, X>Y
        else if (fields[0][1] == fields[1][1] && fields[1][1] > fields[2][1]) {
            //Choose randomly from first and second
            Random r = new Random();
            return fields[r.nextInt(2)][0];
        }

        // Pattern X Y Y and X Y Z, X>Y>Z
        else {
            //Choose first field
            return fields[0][0];
        }
    }

    @Override
    public String getEmail() {
        return "e.kivotova@innopolis.ru";
    }
}

