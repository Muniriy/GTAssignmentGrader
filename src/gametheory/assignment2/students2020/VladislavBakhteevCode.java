package gametheory.assignment2.students2020;

import gametheory.assignment2.Player;


abstract class VladislavBakhteevPlayer implements Player {
    protected int position;

    void Player() {
        reset();
    }

    public void reset() {
        position = 0;
    }

    public String getEmail() {
        return "v.bahteev@innopolis.ru";
    }
}


public class VladislavBakhteevCode extends VladislavBakhteevPlayer {
    // Makes move to environment with the best reward.

    public int move(int opponentLastMove, int xA, int xB, int xC) {
        int max_reward = Math.max(Math.max(xA, xB), xC);

        if (max_reward == xA)
            position = 1;
        else if (max_reward == xB)
            position = 2;
        else
            position = 3;

        return position;
    }

}


//class VladislavBakhteevRandomMoose extends VladislavBakhteevPlayer {
//    // Makes random move
//
//    public int move(int opponentLastMove, int xA, int xB, int xC) {
//        position = 1 + (int) (Math.random() * 3);
//        return position;
//    }
//}
//
//
//class VladislavBakhteevMedianMoose extends VladislavBakhteevPlayer {
//    // Makes move to environment with second by value reward
//
//    public int move(int opponentLastMove, int xA, int xB, int xC) {
//        int max_reward = Math.max(Math.max(xA, xB), xC);
//        int min_reward = Math.min(Math.max(xA, xB), xC);
//        int median = (xA + xB + xC) - max_reward - min_reward;
//
//        if (median == xA)
//            position = 1;
//        else if (median == xB)
//            position = 2;
//        else
//            position = 3;
//
//        return position;
//    }
//}
//
//
//class VladislavBakhteevCopyMoose extends VladislavBakhteevPlayer {
//    // Copies last move of opponent
//
//    public int move(int opponentLastMove, int xA, int xB, int xC) {
//        if (opponentLastMove == 0) { // if it is first move, then do random
//            position = 1 + (int) (Math.random() * 3);
//        }
//        position = opponentLastMove;
//        return position;
//    }
//}
//
//
//class VladislavBakhteevNewMoveMoose extends VladislavBakhteevPlayer {
//    // Makes move that wasn't done at previous step nor by he nor by opponent.
//
//    public int move(int opponentLastMove, int xA, int xB, int xC) {
//        if (1 != position && 1 != opponentLastMove)
//            position = 1;
//        else if (2 != position && 2 != opponentLastMove)
//            position = 2;
//        else
//            position = 3;
//
//        return position;
//    }
//}
//
//
//class VladislavBakhteevRandomNewMoveMoose extends VladislavBakhteevNewMoveMoose {
//    // Moose that makes move that wasn't done at previous step nor by opponent or he.
//    // If previous moves were the same then take random move from 2 than were't taken
//
//    private int random(int a, int b) {
//        Random random = new Random();
//
//        if (random.nextInt() % 2 == 1) {
//            return a;
//        }
//        return b;
//    }
//
//    public int move(int opponentLastMove, int xA, int xB, int xC) {
//        if (position != opponentLastMove) {
//
//            if (1 != position && 1 != opponentLastMove)
//                position = 1;
//            else if (2 != position && 2 != opponentLastMove)
//                position = 2;
//            else
//                position = 3;
//
//        } else { // if previous positions are equal
//
//            if (position == 1)
//                position = random(2, 3);
//            else if (position == 2)
//                position = random(1, 3);
//            else
//                position = random(1, 2);
//        }
//
//
//        return position;
//    }
//}