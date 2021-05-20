package gametheory.assignment2.students2021;

import java.util.Random;

public class NikitaDubinaCode implements gametheory.assignment2.Player {

    public void reset() {

    }

    public int move(int opponentLastMove, int xA, int xB, int xC) {
        Random randomizer = new Random();

        int result = 0;
        int resultIndex = -1;
        while (result == 0) {
            int index = randomizer.nextInt(3) + 1;
            if (index == 1) {
                result = xA;
            }
            if (index == 2) {
                result = xB;
            }
            if (index == 3) {
                result = xC;
            }
            resultIndex = index;
        }
        return resultIndex;
    }


    public String getEmail() {
        return "n.dubina@innopolis.university";
    }
}