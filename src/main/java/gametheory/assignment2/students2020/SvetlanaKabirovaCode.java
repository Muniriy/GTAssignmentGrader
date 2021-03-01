package gametheory.assignment2.students2020;

import gametheory.assignment2.Player;

import java.util.Random;

public class SvetlanaKabirovaCode implements Player {

    Random random = new Random();

    @Override
    public void reset() {
        //For testing
        //random = MyTournament.testRand ? new Random(MyTournament.currentSeed): new Random();
    }

    @Override
    public int move(int opponentLastMove, int xA, int xB, int xC){
        if(xA > xB){
            // b is not the best
            if (xA > xC){
                return 1;
            }else{
                // b is not the best
                // a is not better than c
                if(xA == xC){
                    //tft
                    return random.nextBoolean()?1:3;
                }
                return 3;
            }
        }else {
            // a is not better than b
            if(xB > xC){
                // c is not the best
                if(xA == xB){
                    return random.nextBoolean()?1:2;
                }
                return 2;
            }else{
                // a is not better than b
                // b is not better than c
                if(xB == xC){
                    if(xA == xB){
                        return random.nextInt(3)+1;
                    }else {
                        return random.nextBoolean()?2:3;
                    }
                }
                return 3;
            }
        }
    }

    @Override
    public String getEmail() {
        return "s.kabirova@innopolis.ru";
    }

}
