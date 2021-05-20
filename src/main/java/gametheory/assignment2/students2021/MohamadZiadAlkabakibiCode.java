package gametheory.assignment2.students2021;

import gametheory.assignment2.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * the student's solution class
 */
public class MohamadZiadAlkabakibiCode implements Player {

    private boolean greedyOp;
    private int lastX, lastY, lastZ;
    private double delta = 1.4;
    private Random gen;

    /**
     * Constructor
     */
    public MohamadZiadAlkabakibiCode() {
        this.reset();
    }

    /**
     * resets the attributes of the agent
     */
    public void reset() {
        greedyOp = true;
        lastX = lastY = lastZ = 1;
        gen = new Random();
    }

    /**
     * @param opponentLastMove last move the opponent did
     * @return if the opponent is acting greedy (if the opponent chose the max we assume they are greedy)
     */
    private boolean greedyOpponent(int opponentLastMove) {
        int[] lastInfo = new int[]{lastX, lastY, lastZ};
        int max = Arrays.stream(lastInfo).max().getAsInt();

        for (int i = 0; i < 3; i++) {
            if (lastInfo[i] == max && opponentLastMove == i + 1) {
                return true;
            }
        }

        return false;
    }

    /**
     * @param opponentLastMove last move the opponent did
     * @param xA               X for the first area
     * @param xB               X for the second area
     * @param xC               X for the third area
     * @return the chosen move at current round
     */
    public int move(int opponentLastMove, int xA, int xB, int xC) {
        if (opponentLastMove != 0 && this.greedyOp && !greedyOpponent(opponentLastMove)) {
            this.greedyOp = false;
        }

        lastX = xA;
        lastY = xB;
        lastZ = xC;
        int[] lastInfo = new int[]{lastX, lastY, lastZ};
        Arrays.sort(lastInfo);


        int max = lastInfo[2];
        int secondMax;

        // if all of them are equal then take any number since no second max exists
        if (lastInfo[1] != lastInfo[2])
            secondMax = lastInfo[1];
        else
            secondMax = lastInfo[0];

        List<Integer> maxes = new ArrayList<Integer>();
        List<Integer> secondMaxes = new ArrayList<Integer>();

        // reset the coords array to know the original positions
        lastInfo = new int[]{lastX, lastY, lastZ};

        for (int i = 0; i < 3; i++) {
            if (lastInfo[i] == max) {
                maxes.add(i + 1);
            }
            if (lastInfo[i] == secondMax) {
                secondMaxes.add(i + 1);
            }
        }
        if (!greedyOp || f(max) - f(secondMax) >= delta || maxes.size() > 1) {
            return maxes.get(gen.nextInt(maxes.size()));
        } else {
            return secondMaxes.get(gen.nextInt(secondMaxes.size()));
        }
    }

    /**
     * @return email of the student
     */
    public String getEmail() {
        return "m.alkabakibi@innopolis.university";
    }

    /**
     * @param x the current value of the area
     * @return the number of corpses available in the area according to the assignment's formula
     */
    public float f(int x) {
        return (float) (10.0 * Math.exp((double) x) / (1.0 + Math.exp((double) x)));
    }
}



