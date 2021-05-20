package gametheory.assignment2.excluded2020;

import gametheory.assignment2.Player;

import java.util.Random;
import java.util.ArrayList;
import java.util.Collections;
import java.lang.Math;

public class DanilGinzburgCode {

//    public interface Player {
//        void reset();
//
//        int move(int opponentLastMove, int xA, int xB, int xC);
//
//        String getEmail();
//    }

    public static class DanilGinzburgGoose implements Player {
        Random randomizer = new Random();

        @Override
        public void reset() {
        }

        @Override
        public int move(int opponentLastMove, int xA, int xB, int xC) {
            return 0;
        }

        @Override
        public String getEmail(){
            return "d.ginzburg@innopolis.ru";
        }

        public int moveOrRandom(int result, int xA, int xB, int xC) {
            // return move or pick random
            if (xA == xB && xB == xC) return this.randomizer.nextInt(3) + 1;
            else if (result == xA && result == xB) return this.randomizer.nextInt(2) + 1;
            else if (result == xA && result == xC) {
                if (Math.random() > 0.5) return 1;
                else return 3;
            } else if (result == xB && result == xC) return this.randomizer.nextInt(2) + 2;
            return result;
        }
    }

    public static class DanilGinzburgSmartGoose extends DanilGinzburgGoose {
        // My implementation of Moose Game strategy - Smart Goose.
        int lastMove = 0;
        boolean lastMoveMax = true;

        @Override
        public void reset() {
            this.lastMove = 0;
            this.lastMoveMax = true;
        }

        @Override
        public int move(int opponentLastMove, int xA, int xB, int xC) {

            ArrayList<Integer> x = new ArrayList<>();
            x.add(xA);
            x.add(xB);
            x.add(xC);
            Collections.sort(x);
            int max = x.get(2);
            int mid = x.get(1);
            int min = x.get(0);
            int result = 1;
            int moveResult;

            if (xA == 0 && xB == 0) {
                this.lastMoveMax = true;
                this.lastMove = 3;
                return 3;
            } else if (xA == 0 && xC == 0) {
                this.lastMoveMax = true;
                this.lastMove = 2;
                return 2;
            } else if (xB == 0 && xC == 0) {
                this.lastMoveMax = true;
                this.lastMove = 1;
                return 1;
            } else if (min >= 3) {
                if (min >= 5) result = min;
                else if (mid >= 4) result = mid;
                else result = min;
            } else if (mid >= 3) result = mid;
            else {
                if (max == 2 || max == 1) {
                    result = max;
                } else { // we know that max >= 3; mid < 3; min < 3.
                    if (opponentLastMove == this.lastMove) {  // had fight last time
                        if (Math.random() > 0.5) result = max;
                        else result = mid;
                    } else {  // switch or "collaborate"
                        if (lastMoveMax) result = mid;
                        else result = max;
                    }
                }
            }

            if (result == xA) moveResult = 1;
            else if (result == xB) moveResult = 2;
            else moveResult = 3;
            this.lastMove = moveOrRandom(moveResult, xA, xB, xC);
            this.lastMoveMax = this.lastMove == x.get(2);
            return this.lastMove;
        }
    }

    public static class DanilGinzburgRandomGoose extends DanilGinzburgGoose {
        @Override
        public int move(int opponentLastMove, int xA, int xB, int xC) {
            return this.randomizer.nextInt(3) + 1;
        }
    }

    public static class DanilGinzburgRepeatGoose extends DanilGinzburgGoose {
        @Override
        public int move(int opponentLastMove, int xA, int xB, int xC) {
            if (opponentLastMove != 0) return opponentLastMove;
            return this.randomizer.nextInt(2) + 1;
        }
    }

    public static class DanilGinzburgMaxGoose extends DanilGinzburgGoose {

        @Override
        public int move(int opponentLastMove, int xA, int xB, int xC) {
            ArrayList<Integer> x = new ArrayList<>();
            x.add(xA);
            x.add(xB);
            x.add(xC);
            Collections.sort(x);
            int max = x.get(2);
            int result;
            if (max == xA) result = 1;
            else if (max == xB) result = 2;
            else result = 3;
            return moveOrRandom(result, xA, xB, xC);
        }
    }

}
