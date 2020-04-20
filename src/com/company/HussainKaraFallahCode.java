package com.company;

import java.util.Random;

//Greedy distribution
//Assume that variables for 3 fields are A,B,C respectively
//select first field with probabilty A/(A+B+C)
//select second field with probabilty B/(A+B+C)
//select third field with probabilty C/(A+B+C)

class HussainKaraFallahCode implements Player{
    //random number generator
    private Random rng;
    HussainKaraFallahCode(){
        reset();
    }
    public void reset(){
        rng = new Random();
    }
    public int move(int opponentLastMove, int xA, int xB, int xC){
        if(xA + xB + xC == 0) 
            return rng.nextInt(3) + 1;
        int rand = rng.nextInt(xA + xB + xC);
        if(rand < xA) return 1;
        else if(rand < xA + xB) return 2;
        else return 3;
    }   
    public String getEmail(){
        return "h.k.fallah@innopolis.ru";
    }
}


