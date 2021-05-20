package gametheory.assignment2.students20s;

import gametheory.assignment2.Player;

import java.util.ArrayList;
import java.util.Random;

public class MatveyPoltarykhinCode implements Player {
    private final double RICHER_FIELD_CHOOSE_PROBABILITY = 0.77;
    private final ArrayList<Field> fieldRichness = new ArrayList<>();
    private int _myLastMove;

    /**
     * This method is called to reset the agent before the match
     * with another player containing several rounds
     */
    @Override
    public void reset() {
        _myLastMove = 0;
    }

    /**
     * This method returns the move of the player based on
     * the last move of the opponent and X values of all fields.
     * Initially, X for all fields is equal to 1 and last opponent
     * move is equal to 0
     *
     * @param opponentLastMove the last move of the opponent
     *                         varies from 0 to 3
     *                         (0 – if this is the first move)
     * @param xA               the argument X for a field A
     * @param xB               the argument X for a field B
     * @param xC               the argument X for a field C
     * @return the move of the player can be 1 for A, 2 for B
     * and 3 for C fields
     */
    @Override
    public int move(int opponentLastMove, int xA, int xB, int xC) {
        fieldRichness.clear();
        fieldRichness.add(new Field(1, xA));
        fieldRichness.add(new Field(2, xB));
        fieldRichness.add(new Field(3, xC));

        Random random = new Random();

        if (opponentLastMove == 0) {
            _myLastMove = random.nextInt(3) + 1;
        } else {
            fieldRichness.remove(fieldRichness.get(_myLastMove - 1));

            if (random.nextDouble() <= RICHER_FIELD_CHOOSE_PROBABILITY) {
                if (fieldRichness.get(0).richness >= fieldRichness.get(1).richness) {
                    _myLastMove = fieldRichness.get(0).fieldCode;
                } else {
                    _myLastMove = fieldRichness.get(1).fieldCode;
                }
            } else {
                if (fieldRichness.get(0).richness < fieldRichness.get(1).richness) {
                    _myLastMove = fieldRichness.get(0).fieldCode;
                } else {
                    _myLastMove = fieldRichness.get(1).fieldCode;
                }
            }
        }

        return _myLastMove;
    }

    /**
     * This method returns your IU email
     *
     * @return your email
     */
    @Override
    public String getEmail() {
        return "m.poltaryihin@innopolis.university";
    }

    private static class Field {
        public int fieldCode;
        public int richness;

        public Field(int code, int richness) {
            fieldCode = code;
            this.richness = richness;
        }
    }
}
