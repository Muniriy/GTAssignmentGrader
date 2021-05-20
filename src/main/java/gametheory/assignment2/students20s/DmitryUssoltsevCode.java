package gametheory.assignment2.students20s;

import gametheory.assignment2.Player;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;

public class DmitryUssoltsevCode implements Player {
    public static final int INITIAL_THRESHOLD = 100;
    /**
     * Zone values in the previous match.
     *
     * @implNote Preserved between matches.
     */
    ArenaStatus lastStatus = new ArenaStatus();
    /**
     * We use an assumed number of rounds to calculate threshold
     * change speed.
     *
     * @implNote Preserved between matches.
     */
    private int assumedNumRounds = 125;
    /**
     * Total rounds played (i.e. total calls to {@link #move}).
     *
     * @implNote Preserved between matches.
     */
    private int roundsPlayedTotal = 0;
    /**
     * Ordinal of a current match (i.e. count of matches played before).
     *
     * @implNote Preserved between matches.
     */
    private int matchId = 0;
    /**
     * Previous decision made by this player.
     *
     * @implNote Preserved between matches.
     */
    private int lastMove = 0;
    /**
     * A coefficient for decision making.
     *
     * @see #decide
     */
    private double threshold = INITIAL_THRESHOLD;

    @Override
    public void reset() {
        matchId += 1;

        assumedNumRounds = roundsPlayedTotal / matchId;

        threshold = INITIAL_THRESHOLD;
        lastMove = 0;
        lastStatus = new ArenaStatus();
    }

    @Override
    public int move(int opponentLastMove, int xA, int xB, int xC) {
        ArenaStatus arenaStatus = new ArenaStatus(xA, xB, xC);

        if (opponentLastMove != 0) {
            analyzeLastMove(opponentLastMove, arenaStatus);
        }

        roundsPlayedTotal += 1;
        lastStatus = arenaStatus;

        return lastMove = decide(arenaStatus) + 1;
    }

    /**
     * Updates threshold with respect to current arena status.
     *
     * @param opponentLastMove Previous decision made by opponent.
     * @param arenaStatus      Arena status.
     */
    private void analyzeLastMove(int opponentLastMove, ArenaStatus arenaStatus) {
        Integer[] indexes = arenaStatus.getShuffledSortedIndexes();

        if (lastMove == opponentLastMove
                && lastMove == lastStatus.getMaxIndex()
                && lastStatus.get(lastStatus.getMedianIndex()) > 0
        ) {
            threshold -= 1. / assumedNumRounds;
        }
    }

    /**
     * Make the decision with respect to the current threshold.
     * Decision
     *
     * @param arenaStatus
     * @return
     */
    private int decide(ArenaStatus arenaStatus) {
        if (lastMove == 0) return arenaStatus.getMaxIndex();

        double pMax = arenaStatus.get(arenaStatus.getMaxIndex()) * threshold;
        double pMedian = arenaStatus.get(arenaStatus.getMedianIndex());

        return (pMax > pMedian) ? arenaStatus.getMaxIndex() : arenaStatus.getMedianIndex();
    }

    @Override
    public String getEmail() {
        return "d.usoltsev@innopolis.university";
    }

    private double f(int x) {
        return 10 * Math.exp(x) / (1 + Math.exp(x));
    }

    private static class ArenaStatus {
        private int[] arena = {-1, -1, -1};
        private Integer[] indexes;

        ArenaStatus(int xA, int xB, int xC) {
            arena = new int[]{xA, xB, xC};
        }

        ArenaStatus() {
        }

        Integer get(int zone) {
            return arena[zone] >= 0 ? arena[zone] : null;
        }

        int getMaxIndex() {
            return getShuffledSortedIndexes()[2];
        }

        int getMedianIndex() {
            return getShuffledSortedIndexes()[1];
        }

        /**
         * Zone indexes sorted by zone values.
         * <p>
         * Shuffling array before sorting allows to select randomly
         * between fields with equal values to avoid suboptimal solutions
         * in some cases.
         *
         * @return Sorted array of zone indexes, with shuffled indexes for equal zones.
         */
        Integer[] getShuffledSortedIndexes() {
            if (this.indexes != null) return this.indexes;

            this.indexes = new Integer[]{0, 1, 2};

            //noinspection ComparatorMethodParameterNotUsed
            Arrays.sort(indexes, (i1, i2) -> new Random().nextBoolean() ? 1 : -1);
            Arrays.sort(indexes, Comparator.comparingInt(this::get));

            return this.indexes;
        }
    }
}
