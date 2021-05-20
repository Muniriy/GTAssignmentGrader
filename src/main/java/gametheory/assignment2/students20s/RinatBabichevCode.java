package gametheory.assignment2.students20s;

import gametheory.assignment2.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class RinatBabichevCode implements Player {

    public int score = 0; // score is just for my testing
    ArrayList<Integer> opponentMoves = new ArrayList<Integer>();
    float[] probabilitiesMap;
    float minSquareGain, maxSquareGain;


    /**
     * Default values for probabilities map - are strictly increasing
     * probability to go to field with x = 1 is 0.6,
     * probability to go to field with x = 5 is 0.9
     * Probabilities are proceeded sequentially in the algorithm,
     * if one fails, try next
     * Gain to the probability of the square which opponent visits not often + 0.1
     * Loss to the probability of the square which opponent visits most often -0.1
     */
    public RinatBabichevCode() {

        this.probabilitiesMap = new float[]{0.0f, 0.6f, 0.7f, 0.8f, 0.85f, 0.9f};
        this.minSquareGain = 0.1f;
        this.maxSquareGain = -0.1f;

    }

    /**
     * Init moss with custom probabilities(for testing)
     *
     * @param probabilitiesMap - probabilities map list, list[3] - probability to go to point where x = 3
     * @param minSquareGain    -  Gain to the probability of the square which opponent visits not often
     * @param maxSquareGain    - Loss to the probability of the square which opponent visits most often
     */
    public RinatBabichevCode(float[] probabilitiesMap, float minSquareGain, float maxSquareGain) {

        assert probabilitiesMap.length == 6;
        assert minSquareGain >= 0 & minSquareGain <= 1;
        assert maxSquareGain >= 0 & maxSquareGain <= 1;

        this.probabilitiesMap = probabilitiesMap;
        this.minSquareGain = minSquareGain;
        this.maxSquareGain = maxSquareGain;

    }

    /**
     * Reset history for new round
     */
    public void reset() {
        opponentMoves.clear();
    }

    /**
     * Make move
     * return int - next square number
     */
    public int move(int opponentLastMove, int xA, int xB, int xC) {

        if (opponentLastMove != 0)
            opponentMoves.add(opponentLastMove);

        int[] values = {xA, xB, xC};
        float[] myProbs = {
                // normalize each x to be from 0 to 5(as function then converges)
                probabilitiesMap[Math.min(5, xA)],
                probabilitiesMap[Math.min(5, xB)],
                probabilitiesMap[Math.min(5, xC)]
        };

        int[] countVisited = {0, 0, 0};
        int mindex = 0;

        // increase prob for unvisited point by enemy
        // &&&
        // decrease prob for most visited poit by enemy
        for (int opMove : opponentMoves) {
            countVisited[opMove - 1] += 1;
        }
        if (countVisited[1] <= countVisited[2] & countVisited[1] <= countVisited[0])
            mindex = 1;
        if (countVisited[2] <= countVisited[1] & countVisited[2] <= countVisited[0])
            mindex = 2;

        myProbs[mindex] += this.minSquareGain;
        myProbs[(mindex + 1) % 3] += this.maxSquareGain;
        myProbs[(mindex + 2) % 3] += this.maxSquareGain;

        Random r = new Random();
        for (int i = 0; i < 3; i++) {
            float prob = r.nextFloat();
            // if current probability successes return its index (xA, xB, xC)
            if (prob <= myProbs[i] & values[i] != 0)
                return i + 1;
        }
        // if all fail return just random number
        return r.nextInt(3) + 1;
    }


    public String getEmail() {
        return "R.BABICHEV@INNOPOLIS.RU";
    }

    /**
     * Function used by tournament to visualize moose
     */
    public String text() {
        return "Probabilistic moss " + Arrays.toString(this.probabilitiesMap) + ", leastVisitedSquareGain " + this.minSquareGain + ", mostVisitedSquareLoss " + this.maxSquareGain;
    }

}
