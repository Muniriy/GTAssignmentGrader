package gametheory.assignment2.students2021;

import gametheory.assignment2.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class MaximStepanovCode implements Player {
    private State state;
    private Random random = new Random();


    public MaximStepanovCode() {
        reset();
    }

    @Override
    public void reset() {
        state = new State();
    }

    public int xAt(int index, int xA, int xB, int xC) {
        if (index == 1)
            return xA;
        if (index == 2)
            return xB;
        return xC;
    }

    public int indexOfMax(int xA, int xB, int xC) {
        if (xA > xB && xA > xC)
            return xA;
        if (xB > xA && xB > xC)
            return xB;
        return xC;
    }

    @Override
    public int move(int opponentLastMove, int xA, int xB, int xC) {
        state.currentTurn++;
        state.updateState(opponentLastMove, xA, xB, xC);

        if (state.isCooperating) {
            if (!state.handshakeCompleted) {
                // Abort the cooperation if we couldn't manage to negotiate in 6 first turns
                if (state.currentTurn > 6) {
                    state.isCooperating = false;
                    return move(opponentLastMove, xA, xB, xC);
                }

                // If this is not the first move and our current position doesn't match opponent's current position,
                // finish the handshake process using the random strategy (so we still get something out of this)
                if (state.currentField != -1 && opponentLastMove != state.currentField) {
                    state.handshakeCompleted = true;
                    state.ownField = state.currentField;

                    if (state.currentField == 1 && opponentLastMove == 2)
                        state.holdingField = 3;
                    else if (state.currentField == 1 && opponentLastMove == 3)
                        state.holdingField = 2;
                    else if (state.currentField == 2 && opponentLastMove == 1)
                        state.holdingField = 3;
                    else if (state.currentField == 2 && opponentLastMove == 3)
                        state.holdingField = 1;
                    else if (state.currentField == 3 && opponentLastMove == 1)
                        state.holdingField = 2;
                    else if (state.currentField == 3 && opponentLastMove == 2)
                        state.holdingField = 1;

                    // Fallback in case there's something wrong with tester
                    if (state.holdingField == -1) {
                        System.out.println("WARNING: opponentLastMove was invalid and no handshake is possible");
                        state.holdingField = 1;
                        state.isCooperating = false;
                    }

                    state.currentField = state.holdingField;
                    return state.currentField;
                }

                int nextField = random.nextInt(3) + 1;
                state.currentField = nextField;
                return nextField;
            }

            // Check if another player stepped on our own field. This means break of the strategy and leads to immediate
            // de-cooperation.
            if (state.ownField == opponentLastMove) {
                state.isCooperating = false;
                return move(opponentLastMove, xA, xB, xC);
            }

            // If own field has enough grass, go and get it
            if (xAt(state.ownField, xA, xB, xC) >= 6) {
                state.currentField = state.ownField;
                return state.currentField;
            }

            // If not, hold and wait while grass grows
            return state.holdingField;
        } else {
            float greedyProb = state.getGreedyStrategyProbability();
            float randomProb = state.getRandomStrategyProbability();
            float constantProb = state.getConstantStrategyProbability();

            // Handle greedy strategy
            if (greedyProb >= randomProb && greedyProb >= constantProb) {
                state.currentField = indexOfMax(xA, xB, xC);
                return state.currentField;
            }

            // Handle random strategy
            if (randomProb >= greedyProb && randomProb >= constantProb) {
                state.currentField = indexOfMax(xA, xB, xC);
                return state.currentField;
            }

            // Handle constant strategy
            if (constantProb >= greedyProb && constantProb >= randomProb) {
                // A bit dirty code for that, but hey, still, it works
                if (opponentLastMove == 1) {
                    int maxFieldIndex = xB > xC ? 2 : 3;
                    int notMaxFieldIndex = xB > xC ? 3 : 2;

                    if (xAt(maxFieldIndex, xA, xB, xC) >= 6)
                        state.currentField = maxFieldIndex;
                    else
                        state.currentField = notMaxFieldIndex;
                    return state.currentField;
                }
                if (opponentLastMove == 2) {
                    int maxFieldIndex = xB > xC ? 1 : 3;
                    int notMaxFieldIndex = xB > xC ? 3 : 1;

                    if (xAt(maxFieldIndex, xA, xB, xC) >= 6)
                        state.currentField = maxFieldIndex;
                    else
                        state.currentField = notMaxFieldIndex;
                    return state.currentField;
                }
                if (opponentLastMove == 3) {
                    int maxFieldIndex = xB > xC ? 1 : 2;
                    int notMaxFieldIndex = xB > xC ? 2 : 1;

                    if (xAt(maxFieldIndex, xA, xB, xC) >= 6)
                        state.currentField = maxFieldIndex;
                    else
                        state.currentField = notMaxFieldIndex;
                    return state.currentField;
                }
            }

            // As a fallback, use greedy strategy
            state.currentField = indexOfMax(xA, xB, xC);
            return state.currentField;
        }
    }

    @Override
    public String getEmail() {
        return "m.stepanov@innopolis.university";
    }


    private static class State {
        public List<Integer> opponentMoves = new ArrayList<>();
        public boolean isCooperating = true;
        public boolean handshakeCompleted = false;
        public int ownField = -1;
        public int holdingField = -1;

        private int currentField = -1;
        private int currentTurn = 0;

        private int greedyStrategyMatches = 0;


        /**
         * @param opponentMove previous opponent's move
         * @param xA           current X of field A
         * @param xB           current X of field B
         * @param xC           current X of field C
         */
        public void updateState(int opponentMove, int xA, int xB, int xC) {
            opponentMoves.add(opponentMove);

            // Check if opponent's move correspond to the greedy strategy
            if (opponentMove == 1 && xA >= xB && xA >= xC)
                greedyStrategyMatches++;
            else if (opponentMove == 2 && xB >= xA && xB >= xC)
                greedyStrategyMatches++;
            else if (opponentMove == 3 && xC >= xA && xC >= xB)
                greedyStrategyMatches++;
        }

        /**
         * Computes the amount of times each field was chosen by the opponent.
         *
         * @return the array which has elements 1, 2 and 3 storing amount of appropriate moves of the opponent.
         */
        private int[] getMovesDistribution() {
            int[] movesDist = new int[4];

            for (Integer opponentMove : opponentMoves) {
                movesDist[opponentMove]++;
            }

            return movesDist;
        }


        /**
         * @return (greedyMoves / totalMoves) ^ 5
         */
        public float getGreedyStrategyProbability() {
            return (float) Math.pow((float) greedyStrategyMatches / opponentMoves.size(), 5);
        }

        /**
         * @return the probability that opponent has a random strategy. Refer to report for more details.
         */
        public float getRandomStrategyProbability() {
            int[] movesDist = getMovesDistribution();
            float movesCount = opponentMoves.size();

            float res = 1;

            for (int i = 1; i < movesDist.length; i++) {
                res -= Math.pow((movesCount / 3 - movesDist[i]), 2);
            }

            return res;
        }

        /**
         * This function assumes that player that plays constant strategy has the most frequently used field equal to
         * the total move count. Still, it has some room for deviation, but lowered using power of 3 function to
         * suppress deviation's impact.
         *
         * @return (max / total) ^ 5.
         */
        public float getConstantStrategyProbability() {
            int[] movesDist = getMovesDistribution();
            int movesCount = opponentMoves.size();

            float linearProb = ((float) Math.max(Math.max(movesDist[1], movesDist[2]), movesDist[3]) / movesCount);
            return (float) Math.pow(linearProb, 5);
        }
    }
}
