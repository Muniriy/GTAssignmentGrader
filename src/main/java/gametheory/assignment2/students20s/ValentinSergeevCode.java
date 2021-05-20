package gametheory.assignment2.students20s;

import gametheory.assignment2.Player;

import java.util.Random;
import java.util.function.Supplier;

public class ValentinSergeevCode implements Player {

    private State currentState = new Initial();

    @Override
    public void reset() {
        currentState = new Initial();
    }

    @Override
    public int move(int opponentLastMove, int xA, int xB, int xC) {
        Integer opponentMove = opponentLastMove == 0 ? null : opponentLastMove;

        Event event = new Event(opponentMove, new int[]{xA, xB, xC});

        Transition transition = currentState.transition(event);

        currentState = transition.newState;

        return Utils.coerceIn(transition.move, 1, 3);
    }

    @Override
    public String getEmail() {
        return "v.sergeev@innopolis.university";
    }

    /**
     * Base class for describing states in a strategy's state machine
     */
    private static abstract class State {
        static Random random = new Random();

        /**
         * Move, corresponding to the transition to this state.
         * Can be null, if it is the initial state
         */
        public final Integer myLastMove;

        public State(Integer myLastMove) {
            this.myLastMove = myLastMove;
        }

        /**
         * Each state should provide implementation for this method,
         * describing all possible transitions from this state
         */
        abstract Transition transition(Event event);
    }

    private static class Utils {

        /**
         * Determines the third cell based on two others
         * For example:
         * thirdCell(3, 1) = 2
         */
        static int thirdCell(int first, int second) {
            return 1 + 2 + 3 - first - second;
        }

        /**
         * Bounds integer between two values
         * For example:
         * coerceIn(10, 0, 3) = 3
         * coerceIn(-1, 0, 3) = 0
         */
        static int coerceIn(int number, int min, int max) {
            return Math.max(min, Math.min(max, number));
        }
    }

    /**
     * Starting state in the state machine
     */
    private static class Initial extends State {

        public Initial() {
            super(null);
        }

        @Override
        Transition transition(Event event) {
            int move = FindPlace.startingMove();
            State newState = new FindPlace(move);

            return new Transition(newState, move);
        }
    }

    /**
     * State describing the fallback strategy for non-coop game
     * Currently, acts as "weighted greedy".
     * Meaning, during each move, weight equal to the score is assigned to each cell.
     * Then, some cell is randomly chosen based on the weights (bigger width -> higher chance to be chosen).
     */
    private static class NotCoop extends State {

        public NotCoop(Integer myLastMove) {
            super(myLastMove);
        }

        /**
         * Selects cell using weighted random
         * Taken and adopted from https://stackoverflow.com/a/6737362/7996129
         */
        static int notCoopMove(Event event) {

            double totalScore = 0.0;
            for (double score : event.cellsState) {
                totalScore += score;
            }

            int cellIndex = 0;
            for (double random = Math.random() * totalScore; cellIndex < event.cellsState.length - 1; ++cellIndex) {
                random -= event.cellsState[cellIndex];

                if (random <= 0.0) break;
            }

            return cellIndex + 1;
        }

        @Override
        Transition transition(Event event) {
            int move = notCoopMove(event);
            State newState = new NotCoop(move);

            return new Transition(newState, move);
        }
    }

    /**
     * State corresponding to `Division` phase in state machine
     */
    private static class FindPlace extends State {

        public FindPlace(Integer myLastMove) {
            super(myLastMove);
        }

        static int startingMove() {
            return random.nextInt(3) + 1; // random number in 1..3
        }

        @Override
        Transition transition(Event event) {
            State newState;
            int move;

            if (event.opponentLastMove.equals(myLastMove)) { // collision happened, repeat step
                move = startingMove();
                newState = new FindPlace(move);
            } else {
                int waitForGrowCell = Utils.thirdCell(myLastMove, event.opponentLastMove);
                int myFarmingCell = myLastMove;

                if (WaitingForGrow.shouldWaitForGrow(myFarmingCell, event.cellsState)) {
                    move = waitForGrowCell;
                    newState = new WaitingForGrow(move, waitForGrowCell, myFarmingCell);
                } else {
                    move = myFarmingCell;
                    newState = new Collecting(move, waitForGrowCell, myFarmingCell);
                }
            }

            return new Transition(newState, move);
        }
    }

    /**
     * Base class for describing the states, for which farming position was already found
     */
    private static abstract class PlaceFoundState extends State {
        int waitForGrowCell;
        int myFarmingCell;

        public PlaceFoundState(Integer myLastMove, PlaceFoundState copyPlaceFrom) {
            this(myLastMove, copyPlaceFrom.waitForGrowCell, copyPlaceFrom.myFarmingCell);
        }

        public PlaceFoundState(Integer myLastMove, int waitForGrowCell, int farmingCell) {
            super(myLastMove);

            this.waitForGrowCell = waitForGrowCell;
            this.myFarmingCell = farmingCell;
        }

        /**
         * Checks whether opponent is still playing coop or not
         */
        protected boolean isOpponentPlayingCoop(int opponentLastMove) {
            return opponentLastMove != myFarmingCell;
        }

        /**
         * Decorates reducer by ensuring that opponent is still cooperating.
         * Otherwise, transitions to non-coop (fallback) state.
         */
        protected Transition withCoopWatcher(Event event, Supplier<Transition> reducer) {
            if (isOpponentPlayingCoop(event.opponentLastMove)) {
                return reducer.get();
            } else {
                int move = NotCoop.notCoopMove(event);
                State newState = new NotCoop(move);

                return new Transition(newState, move);
            }
        }
    }

    /**
     * State, in which agent is collecting the grass from the farming cell
     */
    private static class Collecting extends PlaceFoundState {

        public Collecting(
                Integer myLastMove,
                int waitForGrowCell,
                int farmingCell
        ) {
            super(myLastMove, waitForGrowCell, farmingCell);

        }

        public Collecting(Integer myLastMove, PlaceFoundState copyPlaceFrom) {
            super(myLastMove, copyPlaceFrom);
        }

        @Override
        Transition transition(Event event) {
            return withCoopWatcher(event, () -> {
                int move = waitForGrowCell;
                State newState = new Recovering(move, this);

                return new Transition(newState, move);
            });
        }
    }

    /**
     * State, in which agent is waiting in waiting cell for grass to recover
     */
    private static class Recovering extends PlaceFoundState {

        public Recovering(
                Integer myLastMove,
                PlaceFoundState copyPlaceFrom
        ) {
            super(myLastMove, copyPlaceFrom);
        }

        @Override
        Transition transition(Event event) {
            return withCoopWatcher(event, () -> {
                int move = myFarmingCell;
                State newState = new Collecting(move, this);

                return new Transition(newState, move);
            });
        }
    }

    /**
     * State, in which player waits until grass grow up until desired level
     */
    private static class WaitingForGrow extends PlaceFoundState {

        static final int GRASS_NEEDED = 6;

        public WaitingForGrow(Integer myLastMove, int waitForGrowCell, int farmingCell) {
            super(myLastMove, waitForGrowCell, farmingCell);
        }

        public WaitingForGrow(Integer myLastMove, PlaceFoundState copyPlaceFrom) {
            super(myLastMove, copyPlaceFrom);
        }

        /**
         * @return true, if grass in both agent and opponent cells grew up till {@value #GRASS_NEEDED} level.
         * false - otherwise
         */
        static boolean shouldWaitForGrow(int myCell, int[] cellsX) {
            return cellsX[myCell - 1] < GRASS_NEEDED;
        }

        @Override
        Transition transition(Event event) {
            return withCoopWatcher(event, () -> {
                State newState;
                int move;

                if (shouldWaitForGrow(myFarmingCell, event.cellsState)) {
                    move = waitForGrowCell;
                    newState = new WaitingForGrow(move, this);
                } else {
                    move = myFarmingCell;
                    newState = new Collecting(move, this);
                }

                return new Transition(newState, move);
            });
        }
    }

    /**
     * Class, that describes transitions produced by state machine
     */
    private static class Transition {
        public final State newState;
        public final int move;

        public Transition(State newState, int move) {
            this.newState = newState;
            this.move = move;
        }
    }

    /**
     * Class, that describes events that arrive to state machine
     */
    private static class Event {
        public final Integer opponentLastMove;
        public final int[] cellsState;

        public Event(Integer opponentLastMove, int[] cellsState) {
            this.opponentLastMove = opponentLastMove;
            this.cellsState = cellsState;
        }
    }
}
