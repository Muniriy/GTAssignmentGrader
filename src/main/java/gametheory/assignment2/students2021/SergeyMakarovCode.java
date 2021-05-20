package gametheory.assignment2.students2021;

import gametheory.assignment2.Player;

import java.util.ArrayList;

/**
 * Smart greedy agent, "E" in the report
 */
public class SergeyMakarovCode implements Player {
    /**
     * Does nothing
     */
    @Override
    public void reset() {
    }

    /**
     * @param opponentLastMove last turn of oppnent, initially 0
     * @param xA               amount of grass in the first cell, initially 1
     * @param xB               amount of grass in the second cell, initially 1
     * @param xC               amount of grass in the third cell, initially 1
     * @return Turn that agent did
     */
    @Override
    public int move(int opponentLastMove, int xA, int xB, int xC) {
        // Contains values from which we randomly choose turn to do
        ArrayList<Integer> maxIndeces = new ArrayList<>();

        if (xA >= 3)
            maxIndeces.add(1);
        if (xB >= 3)
            maxIndeces.add(2);
        if (xC >= 3)
            maxIndeces.add(3);

        /**
         * If there is no cell where amount of grass is greater than 2 then just play
         * greedy and insert all indeces of maximal amount
         */
        if (maxIndeces.size() == 0) {
            int max = Math.max(xA, Math.max(xB, xC));

            if (xA == max)
                maxIndeces.add(1);
            if (xB == max)
                maxIndeces.add(2);
            if (xC == max)
                maxIndeces.add(3);
        }
        return maxIndeces.get((int) (Math.random() * maxIndeces.size()));
    }

    /**
     * Returns my Email
     */
    @Override
    public String getEmail() {
        return "s.makarov@innopolis.university";
    }
}
