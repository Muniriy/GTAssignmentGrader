package gametheory.assignment2.students20s;

import gametheory.assignment2.Player;

public class MaximBurovCode implements Player {

    private int bound;

    public MaximBurovCode() {
        bound = 4;
    }

    @Override
    public void reset() {
    }

    @Override
    public int move(int opponentLastMove, int xA, int xB, int xC) {
        if (opponentLastMove == 0) {
            if (xA > xB) {
                if (xA > xC)
                    return 1;
                else
                    return 3;
            } else {
                if (xB > xC)
                    return 2;
                else
                    return 3;
            }
        } else {
            if (xA < bound && xB < bound && xC < bound) return opponentLastMove;
            opponentLastMove--;
            if (opponentLastMove == 0) {
                if (xB > xA)
                    return 2;
                if (xC > xA)
                    return 3;
                if (xB == xA)
                    return 2;
                if (xC == xA)
                    return 3;
                return 1;
            }
            if (opponentLastMove == 1) {
                if (xA > xB)
                    return 1;
                if (xC > xB)
                    return 3;
                if (xA == xB)
                    return 1;
                if (xC == xB)
                    return 3;
                return 2;
            }
            // Only value 2 is left
            if (xA > xC)
                return 1;
            if (xB > xC)
                return 2;
            if (xA == xC)
                return 1;
            if (xB == xC)
                return 2;
            return 3;
        }
    }

    @Override
    public String getEmail() {
        return "m.burov@innopolis.ru";
    }

    @Override
    public String toString() {
        return "SmartMoose";
    }

}
