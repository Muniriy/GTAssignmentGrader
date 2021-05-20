package gametheory.assignment2.students20s;

import gametheory.assignment2.Player;

import java.util.Random;

/**
 * Cooperate until cheating is detected then switch to weighed greedy
 */
public class DaniilManakovskiyCode implements Player {
    /**
     * Each round starts with cooperation
     */
    Player currentStrategy = new Cooperating();

    @Override
    public void reset() {
        this.currentStrategy = new Cooperating();
    }

    @Override
    public int move(int opponentLastMove, int xA, int xB, int xC) {
        GameState gameState = new GameState(opponentLastMove, xA, xB, xC);
        if (hasCheatedMe(gameState)) {
            this.currentStrategy = new WeighedGreedy();
        }
        return this.currentStrategy.move(opponentLastMove, xA, xB, xC);
    }

    /**
     * @param gameState current game state
     * @return if last opponent move is considered as cheating by the current strategy
     */
    boolean hasCheatedMe(GameState gameState) {
        if (currentStrategy instanceof Cooperating) {
            return ((Cooperating) currentStrategy).currentState.hasCheatedMe(gameState);
        }
        // once we are not cooperating, no-one can cheat on me
        return false;
    }

    @Override
    public String getEmail() {
        return "d.manakovskiy@innopolis.university";
    }

    /**
     * A simple dataclass-wrapper for the game state
     */
    static class GameState {

        int[] field;
        int opponentLastMove;

        public GameState(int opponentLastMove, int xA, int xB, int xC) {
            this.field = new int[]{xA, xB, xC};
            this.opponentLastMove = opponentLastMove;
        }
    }

    /**
     * Cooperating strategy of our actor.
     * This strategy is implemented as a state machine
     * <FindCell> -> <EatWhenGrown>
     * ^ |             ^  |
     * |_|             |_|
     */
    static class Cooperating implements Player {

        final int TARGET_GRASS_SIZE = 6;
        Random random = new Random();
        /**
         * Initially we are in finding stage
         */
        State currentState = new FindCell(0);

        /**
         * Utility function to make a random move
         *
         * @return int in range [1; 3]
         */
        int randomMove() {
            return random.nextInt(3) + 1;
        }

        /**
         * Utility function to get the cell
         * besides the given ones
         *
         * @param first  int in range [1; 3]
         * @param second int in range [1; 3]; second != first
         * @return int in range [1; 3] != first; != second
         */
        int getThirdCell(int first, int second) {
            return 1 + 2 + 3 - first - second;
        }

        @Override
        public void reset() {
            currentState = new FindCell(0);
        }

        @Override
        public int move(int opponentLastMove, int xA, int xB, int xC) {
            GameState gameState = new GameState(opponentLastMove, xA, xB, xC);
            Step step = currentState.transition(gameState);
            currentState = step.nextState;
            return step.nextMove;
        }

        @Override
        public String getEmail() {
            return "d.manakovskiy@innopolis.university";
        }

        /**
         * Dataclass representing the output of transition function
         */
        private class Step {
            int nextMove;
            State nextState;

            public Step(int nextMove, State nextState) {
                this.nextMove = nextMove;
                this.nextState = nextState;
            }
        }

        /**
         * Abstract state for the state machine logic
         */
        abstract class State {
            int myLastMove;

            public State(int myLastMove) {
                this.myLastMove = myLastMove;
            }

            abstract Step transition(GameState gameState);

            abstract boolean hasCheatedMe(GameState gameState);

        }

        class FindCell extends State {
            public FindCell(int myLastMove) {
                super(myLastMove);
            }

            /**
             * Repeat random moves until
             * private property is not identified
             *
             * @param gameState last game state
             * @return the decision of the node
             */
            @Override
            Step transition(GameState gameState) {
                State nextState;
                int nextMove;

                if (gameState.opponentLastMove == this.myLastMove) {
                    // we ended up at the same spot - need to repeat
                    nextMove = randomMove();
                    nextState = new FindCell(nextMove);
                    return new Step(nextMove, nextState);
                } else {
                    // we landed on a different cell - use left for waiting as a common property
                    int eatingCell = this.myLastMove;
                    int waitingCell = getThirdCell(eatingCell, gameState.opponentLastMove);
                    // Go to utilizing stage
                    return new EatWhenGrown(this.myLastMove, eatingCell, waitingCell).transition(gameState);
                }
            }

            @Override
            boolean hasCheatedMe(GameState gameState) {
                // I can not be cheated until we really start coop
                return false;
            }
        }

        class EatWhenGrown extends State {

            int eatingCell;
            int waitingCell;

            public EatWhenGrown(int myLastMove, int eatingCell, int waitingCell) {
                super(myLastMove);
                this.eatingCell = eatingCell;
                this.waitingCell = waitingCell;
            }

            /**
             * Wait in waitingCell (common property) util eatingCell (private property)
             * is recovered
             *
             * @param gameState last game state
             * @return the decision of the node
             */
            @Override
            Step transition(GameState gameState) {
                State nextState;
                int nextMove;

                // go to private cell if it contain enough grass
                if (gameState.field[this.eatingCell - 1] < TARGET_GRASS_SIZE) {
                    nextMove = this.waitingCell;
                } else {
                    nextMove = this.eatingCell;
                }
                nextState = new EatWhenGrown(nextMove, this.eatingCell, this.waitingCell);
                return new Step(nextMove, nextState);
            }

            /**
             * Cheating is trespassing to our private property
             *
             * @param gameState current game state
             * @return if the opponent has cheated us
             */
            @Override
            boolean hasCheatedMe(GameState gameState) {
                return gameState.opponentLastMove == this.eatingCell;
            }
        }
    }

    /**
     * Solo strategy to be used once the opponent stopped cooperation
     * The cell is chosen at random proportionally to the amount of grass
     * in the cell
     */
    static class WeighedGreedy implements Player {

        Random random = new Random();

        @Override
        public void reset() {
        }

        @Override
        public int move(int opponentLastMove, int xA, int xB, int xC) {
            // proportionality is achieved by thresholds
            float sum = xA + xB + xC;
            float thresholdA = xA / sum;
            float thresholdB = thresholdA + xB / sum;
            float decision = random.nextFloat();
            if (decision < thresholdA) {
                return 1;
            } else if (decision < thresholdB) {
                return 2;
            } else {
                return 3;
            }
        }

        @Override
        public String getEmail() {
            return "d.manakovskiy@innopolis.university";
        }
    }
}
