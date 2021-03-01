package gametheory.assignment2.students2020;

import gametheory.assignment2.Player;

import java.util.ArrayList;

public class MuhammadMavlyutovCode implements Player {
    private ArrayList<Log> history = new ArrayList<>();
    private boolean opEgoistOnce = false; // True if OP has ever chosen the best field

    /**
     * Strategy of a move.
     */
    public enum MoveStrategy {
        KIND, EGOIST
    }

    @Override
    public void reset() {
        this.history.clear();
        this.opEgoistOnce = false;
    }

    /**
     * Misc function. Return field indexes that have the highest score
     *
     * @param fields current fields scores
     * @return list of indexes of fields
     */
    private static ArrayList<Integer> bestPlaces(int[] fields) {
        int bestScore = -1;
        ArrayList<Integer> bestPlaces = new ArrayList<>();

        for (int i = 0; i < 3; i++)
            if (fields[i] > bestScore)
                bestScore = fields[i];

        for (int i = 0; i < 3; i++)
            if (fields[i] == bestScore)
                bestPlaces.add(i + 1);

        return bestPlaces;
    }

    /**
     * Find the best place. If there are multiple of them, analyze previous moves.
     * Guess if opponent mostly chooses leftmost greatest or not. If so, select rightmost greatest
     *
     * @param fields current fields scores
     * @return # of the chosen field
     */
    private int egoistWithMemory(int[] fields) {
        ArrayList<Integer> bestPlaces = bestPlaces(fields);

        if (bestPlaces.size() == 2) {
            if (this.history.size() > 3) {
                int lefts = 0; // How many times OP has chosen leftmost best field
                double leftScore; // Leftmost ratio
                ArrayList<Integer> tmpBestPlaces;

                for (Log log : this.history) {
                    tmpBestPlaces = bestPlaces(log.field);
                    if (log.getOpMoveClassification() == MoveStrategy.EGOIST && tmpBestPlaces.get(0) == log.opMove)
                        lefts++;
                }

                leftScore = 1 - 1.0 * lefts / history.size();

                // If OP has chosen mostly leftmost greatest field, the probability of choosing it will be low
                return Math.random() > leftScore ? bestPlaces.get(1) : bestPlaces.get(0);
            } else {
                // Not enough evidence, choose any
                return Math.random() > 0.5 ? bestPlaces.get(0) : bestPlaces.get(1);
            }
        } else if (bestPlaces.size() == 3) {
            return randField();
        }

        return bestPlaces.get(0);
    }

    /**
     * Main move logic. Given the agent's history and current fields' scores, decide the next move
     *
     * @param fields current fields scores
     * @return # of the chosen field
     */
    private int decideMove(int[] fields) {
        int bestScore = -1;
        if (this.opEgoistOnce) {
            // If opponent ever was an egoist, stop trying to cooperate
            return egoistWithMemory(fields);
        } else {
            // Find the best field. Take it at once, if its score is >= 2
            for (int i = 0; i < 3; i++)
                if (fields[i] > bestScore)
                    bestScore = fields[i];

            if (bestScore < 2) {
                // otherwise, wait until it grows up
                return kindMove(fields);
            } else {
                return egoistWithMemory(fields);
            }
        }
    }

    /**
     * Misc function. Decide which of the 2 fields should kind agent choose.
     *
     * @param field1 index of field 1
     * @param field2 index of field 2
     * @param fields fields current fields scores
     * @return # of the chosen field
     */
    private static int kindDecide(int field1, int field2, int[] fields) {
        int rand = (int) (2 * Math.random());

        // If both of the fields are dry, choose any
        if (fields[field1 - 1] == fields[field2 - 1] && fields[field1 - 1] == 0)
            return rand == 0 ? field1 : field2;

        // If any of the fields is dry, select another one
        if (fields[field1 - 1] == 0)
            return field2;

        if (fields[field2 - 1] == 0)
            return field1;

        // Otherwise, choose any
        return rand == 0 ? field1 : field2;
    }

    /**
     * Choose either of the 3 fields randomly
     *
     * @return # of the chosen field
     */
    private static int randField() {
        return (int) (3 * Math.random()) + 1;
    }

    /**
     * Kindly give away a better place and randomly choose one of the remaining 2.
     *
     * @param fields current fields scores
     * @return # of the chosen field
     */
    private static int kindMove(int[] fields) {
        int bestPlace = bestPlaces(fields).get(0);
        if (bestPlace == 1)
            return kindDecide(2, 3, fields);
        else if (bestPlace == 2)
            return kindDecide(1, 3, fields);
        else if (bestPlace == 3)
            return kindDecide(1, 2, fields);
        else
            return 0;
    }

    @Override
    public int move(int opponentLastMove, int xA, int xB, int xC) {
        int[] fields = {xA, xB, xC};
        Log last;

        if (opponentLastMove != 0) {
            last = history.get(history.size() - 1);
            last.setOpMove(opponentLastMove); // Update last Log entry
            if (last.getOpMoveClassification() == MoveStrategy.EGOIST)
                this.opEgoistOnce = true;
        }

        history.add(new Log(xA, xB, xC));
        return decideMove(fields);
    }

    @Override
    public String getEmail() {
        return "m.mavlyutov@innopolis.ru";
    }

    /**
     * A class describing agent's history log entry.
     */
    private static class Log {

        private int opMove;
        private MoveStrategy opMoveClassification;
        private int[] field = new int[3];

        Log(int xA, int xB, int xC) {
            this.field[0] = xA;
            this.field[1] = xB;
            this.field[2] = xC;
        }

        /**
         * Classify opponent's move as KIND or EGOIST
         */
        private void classifyOpMove() {
            ArrayList<Integer> bestPlaces = bestPlaces(field);

            if (bestPlaces.size() == 1) {
                if (bestPlaces.contains(opMove))
                    this.opMoveClassification = MoveStrategy.EGOIST;
                else
                    this.opMoveClassification = MoveStrategy.KIND;
            } else
                this.opMoveClassification = MoveStrategy.KIND;
        }

        MoveStrategy getOpMoveClassification() {
            return this.opMoveClassification;
        }

        void setOpMove(int opMove) {
            this.opMove = opMove;
            classifyOpMove();
        }
    }
}
