package gametheory.assignment2.students20s;

import gametheory.assignment2.Player;

import java.util.Random;


public class AllaChepurovaCode implements Player {


    public void reset() {
    }

    /**
     * Function for the next moose's move calculation
     *
     * @param opponentLastMove - integer variable for the field number corresponding to the last opponent's move
     * @param xA               - how much nutrients there are in region labeled 1
     * @param xB               - how much nutrients there are in region labeled 1
     * @param xC               - how much nutrients there are in region labeled 1
     * @return integer corresponding to the filed on which this moose go in the next round
     */
    public int move(int opponentLastMove, int xA, int xB, int xC) {

        int correspondingMove = 0;

        if (opponentLastMove == 0) {
            correspondingMove = new Random().nextInt(3) + 1;
        } else {
            int largestMovesCount = 0;
            int[] movesList = {0, 0, 0};

            if ((xA >= xB) && (xA >= xC)) {
                movesList[0]++;
                largestMovesCount++;
            }
            if ((xB >= xA) && (xB >= xC)) {
                movesList[1]++;
                largestMovesCount++;
            }
            if ((xC >= xA) && (xC >= xB)) {
                movesList[2]++;
                largestMovesCount++;
            }

            int tempMove = new Random().nextInt(largestMovesCount) + 1;

            while (tempMove > 0) {
                if (movesList[correspondingMove] == 1) {
                    tempMove--;
                }
                correspondingMove++;
            }

        }

        return correspondingMove;
    }

    /**
     * Method to find out what is my e-mail
     *
     * @return string with my Innopolis student e-mail
     */
    public String getEmail() {
        return "a.chepurova@innopolis.university";
    }
}