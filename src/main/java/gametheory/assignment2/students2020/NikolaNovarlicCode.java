package gametheory.assignment2.students2020;

import gametheory.assignment2.Player;

import java.util.Random;

//interface Player {
//    void reset();
//    int move(int opponentLastMove, int xA, int xB, int xC);
//    String getEmail();
//}

public class NikolaNovarlicCode implements Player {
    public NikolaNovarlicCode() {
    }

    @Override
    public void reset() {
    }

    @Override
    public int move(int opponentLastMove, int xA, int xB, int xC) {
        int range = xA + xB + xC;
        Random rand = new Random();
        int chance = rand.nextInt(range);
        // Random choice will give more chance to higher X areas
        if(chance < xA) return 1;
        if(chance < xA + xB) return 2;
        return 3;
    }

    @Override
    public String getEmail() {
      return "n.novarlic@innopolis.ru";
    }

    public static void main(String[] args) throws Exception {}
}
