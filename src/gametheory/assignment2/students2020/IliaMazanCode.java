package gametheory.assignment2.students2020;

import gametheory.assignment2.Player;

public class IliaMazanCode implements Player {
    @Override
    public void reset() {
    }

    @Override
    public int move(int opponentLastMove, int xA, int xB, int xC) {
        if (opponentLastMove == 0 && xA == xB && xB == xC)
            return (int) (Math.random() * 2) + 1;
        if (xA >= xB && xA >= xC)
            return 1;
        else if (xB >= xA && xB >= xC)
            return 2;
        else
            return 3;
    }

    @Override
    public String getEmail() {
        return "i.mazan@innopolis.ru";
    }
}
