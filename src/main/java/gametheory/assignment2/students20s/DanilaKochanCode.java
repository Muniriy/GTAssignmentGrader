package gametheory.assignment2.students20s;

import gametheory.assignment2.Player;

import java.util.concurrent.ThreadLocalRandom;

/**
 * This code implements my own agent for the Game of Moose. This code implements greedy approach with enhancements
 * in order to avoid starvation in game with the players of the same type and greedy player by changing the strategy
 * if it has several fights in a row.
 */
public class DanilaKochanCode implements Player {

    boolean flag = false;
    private int fightLimits = ThreadLocalRandom.current().nextInt(1, 4);
    private int countFights;
    private int myLastMove = 0;

    /**
     * Set all values used in the game to default
     */
    @Override
    public void reset() {
        countFights = 0;
        myLastMove = 0;
        flag = false;
    }

    /**
     * @param opponentLastMove opponent's last move
     * @param xA               amount of food in A field
     * @param xB               amount of food in B field
     * @param xC               amount of food in C field
     * @return which field to go
     */
    @Override
    public int move(int opponentLastMove, int xA, int xB, int xC) {
        if (myLastMove == opponentLastMove) {
            countFights++;
        } else {
            countFights = 0;
        }

        if (countFights > fightLimits) {
            flag = !flag;
            countFights = 0;
            fightLimits = ThreadLocalRandom.current().nextInt(1, 4);
        }
        int absoluteMax = Math.max(Math.max(xA, xB), xC);
        if (flag) {
            int secondMax = Math.min(Math.min(Math.max(xA, xB), Math.max(xC, xB)), Math.max(xA, xC));
            if (secondMax == 0) {
                secondMax = absoluteMax;
            }
            return chooseMax(xA, xB, secondMax);
        } else {
            return chooseMax(xA, xB, absoluteMax);

        }
    }

    /**
     * This method returns the email of the owner of this moose
     *
     * @return returns the email as ususal String
     */
    @Override
    public String getEmail() {
        return "d.kochan@innopolis.ru";
    }

    /**
     * Choose which field with max amount of food to go
     *
     * @param xA  amount of food in A field
     * @param xB  amount of food in B field
     * @param max maximum value found at the field
     * @return the number of food where to go
     */
    private int chooseMax(int xA, int xB, int max) {
        if (Math.random() > 0.5) {
            if (xB == max) {
                myLastMove = 2;
                return 2;
            }
            if (xA == max) {
                myLastMove = 1;
                return 1;
            }
        } else {
            if (xA == max) {
                myLastMove = 1;
                return 1;
            }
            if (xB == max) {
                myLastMove = 2;
                return 2;
            }
        }
        myLastMove = 3;
        return 3;
    }
}
