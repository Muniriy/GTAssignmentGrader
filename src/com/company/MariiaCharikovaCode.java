package com.company;
import java.util.Random;

public class MariiaCharikovaCode implements Player {

    @Override
    public void reset() {
        int round = 0;  // reset number of round
    }

    @Override 
    public String getEmail() {
        return "m.charikova@innopolis.ru";
    }

    @Override
    public int move(int opponentLastMove, int xA, int xB, int xC) {  // function for player's decision about next move
        int my_move;
        Random rand = new Random();  // if it is the first round, decision goes at random
        my_move = rand.nextInt(2) + 1;
        if (opponentLastMove != 0) {
            if (xA == xB && xA > xC) {  // if the have 2 fields which are maximum and equal to each other
                if (opponentLastMove == 1) {  // in this case it is better not to change the field and give the
                    // opponent the ability not to change the field
                    my_move = 2;
                } else {
                    my_move = 1;
                }
            }

            if (xA == xC && xA > xB) {
                if (opponentLastMove == 1) {
                    my_move = 3;
                } else {
                    my_move = 1;
                }
            }

            if (xB == xC && xB > xA) {
                if (opponentLastMove == 2) {
                    my_move = 3;
                } else {
                    my_move = 2;
                }
            }

            if (xA > xB && xA > xC) {  // if it is obvious that one of fields is better than others
                double diff_ab;
                double diff_ac;

                diff_ab = xA - xB;  // we calculate differents between fields
                diff_ac = xA - xC;
                if (opponentLastMove != 1) { // it will be fair to "distribute" the best fields (by agreement, players
                    // take turns to go to the best field)
                    if (diff_ab < diff_ac) {
                        if (diff_ab < 3 && xC != 0) { // if differents between fields is not critical, we will choose
                            // the 2nd maximum field
                            my_move = 2;
                        } else {
                            my_move = 1;
                        }
                    } else {
                        my_move = 1;
                    }

                }
            }
            if (xB > xC && xB > xA) {
                double diff_bc;
                double diff_ba;

                diff_bc = xB - xC;
                diff_ba = xB - xA;
                if (opponentLastMove != 2) {
                    if (diff_bc < diff_ba) {
                        if (diff_bc < 3 && xB != 0) {
                            my_move = 2;
                        } else {
                            my_move = 3;
                        }
                    } else {
                        my_move = 2;
                    }
                }
            }

            if (xC > xA && xC > xB) {
                double diff_ca;
                double diff_cb;

                diff_ca = xC - xA;
                diff_cb = xC - xB;
                if (opponentLastMove != 3) {
                    if (diff_ca < diff_cb) {
                        if (diff_ca < 3 && xA != 0) {
                            my_move = 1;
                        } else {
                            my_move = 3;
                        }
                    } else {
                        my_move = 3;
                    }
                }
            }
        }
        return my_move;
    }
}