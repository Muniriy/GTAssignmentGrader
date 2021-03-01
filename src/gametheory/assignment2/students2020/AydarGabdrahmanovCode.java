package gametheory.assignment2.students2020;

import gametheory.assignment2.Player;

public class AydarGabdrahmanovCode implements Player {

    public AydarGabdrahmanovCode() {
    }

    @Override
    public void reset() {

    }

    @Override
    public int move(int opponentLastMove, int xA, int xB, int xC) {
        double maxx = Math.max(xA, Math.max(xB, xC));
        int result;
        if (maxx == xA){
            return 1;
        }
        else if (maxx == xB){
            return 2;
        }
        else {
            return 3;
        }
    }

    @Override
    public String getEmail(){
        return "a.gabdrahmanov@innopolis.ru";
    }
}
