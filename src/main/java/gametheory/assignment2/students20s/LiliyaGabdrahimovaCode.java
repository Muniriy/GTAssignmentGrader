package gametheory.assignment2.students20s;

import gametheory.assignment2.Player;

public class LiliyaGabdrahimovaCode implements Player {

    public void reset() {
    }

    /*
    Logic of move action:
    Moose see what move did the opponent in last round, and focus on 2 other fields.
    Among them he chooses the field with the bigger amount of vegetation.
    If it is the first round and there is no opponent's last move, he acts like that:
    Select the field with the bigger amount of vegetation.
    If the amount on all fields is the same -- he chooses third field.
    */
    public int move(int opponentLastMove, int xA, int xB, int xC) {
        if (opponentLastMove == 1) {
            if (xB > xC) {
                return 2;
            } else {
                return 3;
            }
        } else if (opponentLastMove == 2) {
            if (xA > xC) {
                return 1;
            } else {
                return 3;
            }
        } else if (opponentLastMove == 0) {
            if (xA >= xB & xA >= xC) {
                return 1;
            } else if (xB >= xA & xB >= xC) {
                return 2;
            } else {
                return 3;
            }
        } else {
            {
                if (xA > xB) {
                    return 1;
                } else {
                    return 2;
                }
            }
        }

    }

    public String getEmail() {
        return "l.gabdrahimova@innopolis.ru";
    }
}
