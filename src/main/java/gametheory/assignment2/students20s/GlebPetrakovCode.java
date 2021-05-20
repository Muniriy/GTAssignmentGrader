package gametheory.assignment2.students20s;

import gametheory.assignment2.Player;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class GlebPetrakovCode implements Player {

    private static final String APPROACH_RANDOM = "random";
    private static final String APPROACH_GREEDY = "greedy";
    private static final String APPROACH_MINIMAL = "minimal";
    private static final String APPROACH_COPYCAT = "copycat";
    private static final String APPROACH_BETA = "beta";

    private final String approach;

    public GlebPetrakovCode() {
        this.approach = APPROACH_GREEDY;
    }

    GlebPetrakovCode(String approach) {
        this.approach = approach;
    }

    @Override
    public String getEmail() {
        return "g.petrakov@innopolis.ru";
    }

    @Override
    public void reset() {

    }

    @Override
    public int move(int opponentLastMove, int xA, int xB, int xC) {

        switch (approach) {
            case APPROACH_RANDOM:
                return randomMove(opponentLastMove, xA, xB, xC);
            case APPROACH_GREEDY:
                return greedyMove(opponentLastMove, xA, xB, xC);
            case APPROACH_MINIMAL:
                return minimalMove(opponentLastMove, xA, xB, xC);
            case APPROACH_COPYCAT:
                return copycatMove(opponentLastMove, xA, xB, xC);
            case APPROACH_BETA:
                return betaMove(opponentLastMove, xA, xB, xC);
        }

        return greedyMove(opponentLastMove, xA, xB, xC);
    }

    private int randomMove(int opponentLastMove, int xA, int xB, int xC) {
        return ThreadLocalRandom.current().nextInt(1, 4);
    }

    private int greedyMove(int opponentLastMove, int xA, int xB, int xC) {
        List<Integer> state = Arrays.asList(xA, xB, xC);
        return state.indexOf(Collections.max(state)) + 1;
    }

    private int minimalMove(int opponentLastMove, int xA, int xB, int xC) {
        List<Integer> state = Arrays.asList(xA, xB, xC);
        return state.indexOf(state.stream().filter(val -> val != 0).min(Integer::compareTo).orElse(xA)) + 1;
    }

    private int copycatMove(int opponentLastMove, int xA, int xB, int xC) {
        return opponentLastMove == 0 ? randomMove(opponentLastMove, xA, xB, xC) : opponentLastMove;
    }

    private int betaMove(int opponentLastMove, int xA, int xB, int xC) {
        List<Integer> state = Arrays.asList(xA, xB, xC);
        return state.indexOf(state.stream().sorted(Comparator.reverseOrder()).limit(2).skip(1).findFirst().orElse(xA)) + 1;
    }

}
