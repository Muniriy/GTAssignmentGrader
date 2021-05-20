package gametheory.assignment2.students20s;

import gametheory.assignment2.Player;

import java.util.LinkedList;
import java.util.Random;

public class IrekNazmievCode implements Player {
    public String name;
    protected LinkedList<Integer> moves;
    protected LinkedList<Integer> opponentMoves;

    public IrekNazmievCode() {
        this.name = "IrekNazmiev";
        this.reset();
    }

    @Override
    public void reset() {
        this.moves = new LinkedList<>();
        this.opponentMoves = new LinkedList<>();
    }

    @Override
    public int move(int opponentLastMove, int xA, int xB, int xC) {
        int move;

        // save opponent's move
        this.opponentMoves.push(opponentLastMove);

        // make move (first one is random)
        move = opponentLastMove == 0 ? new Random().nextInt(3) + 1 : this.decide(xA, xB, xC);

        // save current move
        this.moves.push(move);

        return move;
    }

    @Override
    public String getEmail() {
        return "i.nazmiev@innopolis.ru";
    }

    public String getName() {
        return this.name;
    }

    protected int decide(int xA, int xB, int xC) {
        int[] fields = {xA, xB, xC};
        int indexMax = 0;

        for (int i = 0; i < fields.length; i++) {
            if (fields[i] > fields[indexMax]) {
                indexMax = i;
            }
        }

        // if there're multiple fields with max gain, choose one of them randomly

        boolean maxFieldEqualToNext = fields[indexMax] == fields[(indexMax + 1) % 3];
        boolean maxFieldEqualToPrev = fields[indexMax] == fields[(indexMax + 2) % 3];

        if (maxFieldEqualToNext & maxFieldEqualToPrev) {
            indexMax = new Random().nextInt(3);
        } else if (maxFieldEqualToNext) {
            indexMax = new Random().nextBoolean() ? indexMax : (indexMax + 1) % 3;
        } else if (maxFieldEqualToPrev) {
            indexMax = new Random().nextBoolean() ? indexMax : (indexMax + 2) % 3;
        }

        // choose the second highest gain

        if (fields[(indexMax + 1) % 3] == fields[(indexMax + 2) % 3]) {
            if (fields[(indexMax + 1) % 3] != 0) {
                indexMax = new Random().nextBoolean() ? (indexMax + 1) % 3 : (indexMax + 2) % 3;
            }
        } else if (fields[(indexMax + 1) % 3] > fields[(indexMax + 2) % 3]) {
            indexMax = (indexMax + 1) % 3;
        } else {
            indexMax = (indexMax + 2) % 3;
        }

        return indexMax + 1;
    }

    ;
}
