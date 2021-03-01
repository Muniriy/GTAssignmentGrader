package gametheory.assignment2.students2020;

import gametheory.assignment2.Player;

import java.util.Random;

public class RobertoChavezCode implements Player {


    @Override
    public void reset() {

    }

    @Override
    public int move(int opponentLastMove, int xA, int xB, int xC) {
        if (opponentLastMove == 0) {
           if (xA >= Math.max(xB, xC)) {
               return 1;
           } else if (xB >= Math.max(xA, xC)) {
               return 2;
           } else {
               return 3;
           }
        }
        return new Random().nextInt(4);
    }

    @Override
    public String getEmail(){
        return "r.chavez@innopolis.ru";
    }
}
