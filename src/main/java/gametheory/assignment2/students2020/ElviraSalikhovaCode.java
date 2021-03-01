package gametheory.assignment2.students2020;

import gametheory.assignment2.Player;

public class ElviraSalikhovaCode implements Player {

    @Override
    public void reset() {
    }

    @Override
    public int move(int opponentLastMove, int xA, int xB, int xC) {
        int mx = Math.max(xA, Math.max(xB, xC));
        int average = xA + xB + xC - mx - Math.min(xA, Math.min(xB, xC));
        double rand = Math.random();
        if (rand< 0.5){
            if (xC == mx)
                return 3;
            else if (xB == mx)
                return 2;
            else return 1;
        }
        else{
            if (xC == average)
                return 3;
            else if (xB == average)
                return 2;
            else return 1;
        }
    }

    @Override
    public String getEmail() {
        return "e.salihova@innopolis.ru";
    }

}