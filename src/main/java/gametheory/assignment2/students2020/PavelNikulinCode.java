package gametheory.assignment2.students2020;

import gametheory.assignment2.Player;

import java.util.Random;

// based on Moose Adaptive class
public class PavelNikulinCode implements Player {

    int my_last_move = 0;

    public PavelNikulinCode() {
        my_last_move = 0;
    }

    @Override
    public void reset() {
        my_last_move = 0;
        return;
    }

    @Override
    public int move(int opponentLastMove, int xA, int xB, int xC) {
        //first move can be random since we do not have context yet
        if (opponentLastMove == 0) {
            int move = new Random().nextInt(3) + 1;
            my_last_move = move;
            return move;
        }

        //then we can check if our moves was identical (collision)
        //then we need to change strategy
        if (opponentLastMove == my_last_move) {
            //change strategy
            //select randomly between two best moves (to overplay greedy opponent)
            int[] array = new int[]{xA, xB, xC};

            // random initialization to avoid bias
            int maxAt = new Random().nextInt(3);
            int secondMaxAt = new Random().nextInt(3);

            for (int i = 0; i < array.length; i++) {
                secondMaxAt = maxAt;
                maxAt = array[i] > array[maxAt] ? i : maxAt;
            }

            //flip a coin to select move
            return (new Random().nextFloat() > 0.5 ? maxAt : secondMaxAt) + 1;

        } else {
            //if we good as greedy, continue as greedy

            // find field with highest score available and go there
            int[] array = new int[]{xA, xB, xC};

            int maxAt = 0;

            for (int i = 0; i < array.length; i++) {
                maxAt = array[i] > array[maxAt] ? i : maxAt;
            }

            int move = maxAt + 1;
            my_last_move = move;
            return move;
        }
    }

    @Override
    public String getEmail() {
        return "p.nikulin@innopolis.ru";
    }

}
