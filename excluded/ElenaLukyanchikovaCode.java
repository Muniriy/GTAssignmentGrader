package com.company;
public class ElenaLukyanchikovaCode {
    /**
     * copy the previous action of the opponent(
     */
    public static class ELukyanchikovaCopyCatPlayer extends ELukyanchikovaGettingMax implements Player {
        int xALast, xBLast, xCLast;

        @Override
        public void reset() {
            this.xALast = 0;
            this.xBLast = 0;
            this.xCLast = 0;

        }

        @Override
        public int move(int opponentLastMove, int xA, int xB, int xC) {
            int lastMaxByScore = findLastMax();
            xBLast = xB;
            xCLast = xC;
            xALast = xA;
            //   lastOpponentChoice = convertTopponentLastMove;
            if (opponentLastMove == lastMaxByScore) {
                return super.chooseMax(xA, xB, xC);
            } else {
                return chooseMiddle(xA, xB, xC);
            }
            //проверка максималка из прошлого(метод из переменных, выбранный щас)
            //если максимум - то щас максимум, если нет, то второй по величине
        }

        @Override
        public String getEmail() {
            return "e.lukyanchikova@innopolis.ru";
        }

        private int chooseMiddle(int xA, int xB, int xC) {
            if ((xA >= xB && xA <= xC) || (xA <= xB && xA >= xC)) {
                return 1;
            } else if ((xB >= xA && xB <= xC) || (xB <= xA && xB >= xC)) {
                return 2;
            } else if ((xC >= xA && xC <= xB) || (xC <= xA && xC >= xB)) {
                return 3;
            } else return 0;

        }


        private int findLastMax() {
            return super.internalMax(xALast, xBLast, xCLast);
        }
    }


    public static abstract class ELukyanchikovaGettingMax {
        protected int chooseMax(int xA, int xB, int xC) {
            return internalMax(xA, xB, xC);
        }

        /**
         * @param xA
         * @param xB
         * @param xC
         * @return max among all
         */
        private int internalMax(int xA, int xB, int xC) {
            if (xA >= xB && xA >= xC) {
                return 1;
            } else if (xB >= xA && xB >= xC) {
                return 2;
            } else if (xC >= xA && xC >= xB) {
                return 3;
            } else return 0;
        }

    }

}


