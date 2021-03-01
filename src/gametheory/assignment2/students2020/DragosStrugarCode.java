package gametheory.assignment2.students2020;

import gametheory.assignment2.Player;

public class DragosStrugarCode implements Player {
    public DragosStrugarCode() {
    }

    @Override
    public void reset() {
    }

    @Override
    public int move(int opponentLastMove, int xA, int xB, int xC) {
        if (xA >= xB && xA >= xC) {
            if (opponentLastMove != 1) return 1;
            else {
                int diff1 = xA - xB;
                int diff2 = xA - xC;
                return diff1 > diff2 ? 3 : 2;
            }
        } else if (xB >= xA && xB >= xC) {
            if (opponentLastMove != 2) return 2;
            else {
                int diff1 = xB - xA;
                int diff2 = xB - xC;
                return diff1 > diff2 ? 3 : 2;
            }
        } else {
            return 3;
        }
    }

    @Override
    public String getEmail() {
        return "d.strugar@innopolis.ru";
    }
}