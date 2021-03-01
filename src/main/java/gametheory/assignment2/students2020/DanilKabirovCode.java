package gametheory.assignment2.students2020;

import gametheory.assignment2.Player;

import java.util.Random;

public class DanilKabirovCode implements Player { // the same as AdvancedHistoryBasedAgent
    Random rn = new Random();
    private int numberOfRounds = 0; // number of runned rounds
    private int opponentKindness = 0; // number of rounds in which opponent leaved the best field for us
    private int lastxA, lastxB, lastxC; // X-es on the previous round

    @Override
    public String getEmail() {
        return "d.kabirov@innopolis.ru";
    }

    @Override
    public void reset() {
        numberOfRounds = 0; // reset all the values
        opponentKindness = 0;
        lastxA = 0;
        lastxB = 0;
        lastxC = 0;
    }

    @Override
    public int move(int opponentLastMove, int xA, int xB, int xC) {
        if (opponentLastMove != 0) { // it this is not the first round
            boolean opponentIsKind = false; // determine if the opponent was "kind" to us in the previous round
            // (if it didn't choose the best field in the previous step)
            if (lastxA >= lastxB && lastxA >= lastxC)
                opponentIsKind = opponentLastMove != 1;
            else if (lastxB >= lastxA && lastxB >= lastxC)
                opponentIsKind = opponentLastMove != 2;
            else
                opponentIsKind = opponentLastMove != 3;

            if (opponentIsKind)
                opponentKindness += 1;
            numberOfRounds += 1;
        }

        lastxA = xA; // update lastX-s for the next round
        lastxB = xB;
        lastxC = xC;

        if (opponentKindness * 2 >= numberOfRounds) { // if the opponent leaved the best field for us most of the time before
            if (xA >= xB && xA >= xC) // take the best field
                return 1;
            else if (xB >= xA && xB >= xC)
                return 2;
            else
                return 3;
        }
        else { // opponent is not kind
            // do like AdvancedRandomAgent
            if (rn.nextInt(2) == 0) // take the best field with the probability 0.5
                if (xA >= xB && xA >= xC)
                    return 1;
                else if (xB >= xA && xB >= xC)
                    return 2;
                else
                    return 3;
            else { // return the second best
                if (xB >= xA && xA >= xC || xC >= xA && xA >= xB)
                    return 1;
                else if (xA >= xB && xB >= xC || xC >= xB && xB >= xA)
                    return 2;
                else
                    return 3;
            }
        }
    }
}