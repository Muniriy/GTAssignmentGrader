package gametheory.assignment2.students20s;

import gametheory.assignment2.Player;

public class VyacheslavGoreevCode implements Player {
    public int move(int opponentLastMove, int xA, int xB, int xC) {
        // Makes the first move by random
        if (opponentLastMove == 0) {
            return (int) Math.ceil(Math.random() * 3);
        }

        // Then select the field with the most of vegetables
        if (Math.max(xA, xB) == xA && Math.max(xA, xC) == xA) return 1;
        if (Math.max(xB, xA) == xB && Math.max(xB, xC) == xB) return 2;
        if (Math.max(xC, xA) == xC && Math.max(xC, xB) == xC) return 3;

        // Actually, unreachable code, but Copycat by default
        return opponentLastMove;
    }

    public void reset() {
    }

    public String getEmail() {
        return "v.goreev@innopolis.ru";
    }
}
