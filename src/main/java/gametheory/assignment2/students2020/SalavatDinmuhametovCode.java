package gametheory.assignment2.students2020;

import gametheory.assignment2.Player;

public class SalavatDinmuhametovCode implements Player {
    @Override
    public void reset() {
    }

    @Override
    public int move(int opponentLastMove, int xA, int xB, int xC) {
        double a = (double) xA / (xA + xB + xC);
        double b = (double) xB / (xA + xB + xC);

        double rand = Math.random();
        if (rand < a){
            return 1;
        }else if (rand > a && rand < a + b){
            return 2;
        }else {
            return 3;
        }
    }

    @Override
    public String getEmail(){
        return "s.dinmuhametov@innopolis.ru";
    }
}
