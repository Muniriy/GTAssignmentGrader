package com.company;
import java.lang.Math;

public class OlegAndriyanchenkoCode implements Player {
    @Override
    public void reset() {
    }

    // Efficient approach for finding the middle number
    // https://www.geeksforgeeks.org/middle-of-three-using-minimum-comparisons/
    public int middleOfThree(int a, int b, int c) {
        int x = a - b;
        int y = b - c;
        int z = a - c;
        if (x * y > 0)
            return b;
        else if (x * z > 0)
            return c;
        else
            return a;
    }

    // Get the number of the field according to first value (middle or max)
    private int getMove(int compare, int a, int b, int c) {
        int move = 0;
        if (a == b && a == c)
            return (int)(1 + Math.random() * 3);

        if (compare == c)
            move = 3;
        if (compare == b)
            move = 2;
        if (compare == a)
            move = 1;
        return move;
    }

    @Override
    public int move(int opponentLastMove, int xA, int xB, int xC) {
        int maxnum = Math.max(xA, Math.max(xB, xC));
        int middle = middleOfThree(xA, xB, xC);

        // If max < 2 => play greedy/defend since the gain is so small
        if (maxnum < 2) {
            return getMove(maxnum, xA, xB, xC);
        }
        // If max = 2 we might consider going for it
        if (maxnum == 2) {
            if (Math.random() > 0.5) return getMove(maxnum, xA, xB, xC);
            else return getMove(middle, xA, xB, xC);
        }
        // If middle is small => defend
        if (maxnum == 3 && middle < 2) {
            return getMove(maxnum, xA, xB, xC);
        }
        // If middle is much slower than max consider going for it
        if (maxnum == 3 && middle == 2) {
            if (Math.random() > 0.5) return getMove(maxnum, xA, xB, xC);
            else return getMove(middle, xA, xB, xC);
        }
        // If middle is 3 go for it
        if (middle == 3) {
            // But if max is large consider going for it
            if (maxnum == 5) {
                if (Math.random() > 0.4) return getMove(maxnum, xA, xB, xC);
                else return getMove(middle, xA, xB, xC);
            }
            return getMove(middle, xA, xB, xC);
        }
        // If middle is > 3 always go for it
        if (middle > 3) {
            return getMove(middle, xA, xB, xC);
        }
        return getMove(maxnum, xA, xB, xC);
    }

    @Override
    public String getEmail() {
        return "o.andriyanchenko@innopolis.ru";
    }
}

//class OlegAndriyanchenkoCode  {
//
//    public interface Player {
//        void reset();
//        int move(int opponentLastMove, int xA, int xB, int xC);
//        String getEmail();
//    }
//
//    // TODO - This is the Best agent for competition!!!!!!!
//    // God moose with insane mathematics behind it
//    static class OlegAndriyanchenkoTheMoose implements Player {
//        @Override
//        public void reset() {
//        }
//
//        // Efficient approach for finding the middle number
//        // https://www.geeksforgeeks.org/middle-of-three-using-minimum-comparisons/
//        public int middleOfThree(int a, int b, int c) {
//            int x = a - b;
//            int y = b - c;
//            int z = a - c;
//            if (x * y > 0)
//                return b;
//            else if (x * z > 0)
//                return c;
//            else
//                return a;
//        }
//
//        // Get the number of the field according to first value (middle or max)
//        private int getMove(int compare, int a, int b, int c) {
//            int move = 0;
//            if (a == b && a == c)
//                return (int)(1 + Math.random() * 3);
//
//            if (compare == c)
//                move = 3;
//            if (compare == b)
//                move = 2;
//            if (compare == a)
//                move = 1;
//            return move;
//        }
//
//        @Override
//        public int move(int opponentLastMove, int xA, int xB, int xC) {
//            int maxnum = Math.max(xA, Math.max(xB, xC));
//            int middle = middleOfThree(xA, xB, xC);
//
//            // If max < 2 => play greedy/defend since the gain is so small
//            if (maxnum < 2) {
//                return getMove(maxnum, xA, xB, xC);
//            }
//            // If max = 2 we might consider going for it
//            if (maxnum == 2) {
//                if (Math.random() > 0.5) return getMove(maxnum, xA, xB, xC);
//                else return getMove(middle, xA, xB, xC);
//            }
//            // If middle is small => defend
//            if (maxnum == 3 && middle < 2) {
//                return getMove(maxnum, xA, xB, xC);
//            }
//            // If middle is much slower than max consider going for it
//            if (maxnum == 3 && middle == 2) {
//                if (Math.random() > 0.5) return getMove(maxnum, xA, xB, xC);
//                else return getMove(middle, xA, xB, xC);
//            }
//            // If middle is 3 go for it
//            if (middle == 3) {
//                // But if max is large consider going for it
//                if (maxnum == 5) {
//                    if (Math.random() > 0.4) return getMove(maxnum, xA, xB, xC);
//                    else return getMove(middle, xA, xB, xC);
//                }
//                return getMove(middle, xA, xB, xC);
//            }
//            // If middle is > 3 always go for it
//            if (middle > 3) {
//                return getMove(middle, xA, xB, xC);
//            }
//            return getMove(maxnum, xA, xB, xC);
//        }
//
//        @Override
//        public String getEmail() {
//            return "o.andriyanchenko@innopolis.ru";
//        }
//    }
//
//    // random
//    // for testing
//    static class OARandomMoose implements Player {
//        @Override
//        public void reset() {    }
//
//        @Override
//        public int move(int opponentLastMove, int xA, int xB, int xC) {
//            return (int) (1 + Math.random() * 3);
//        }
//
//        @Override
//        public String getEmail() {
//            return "o.andriyanchenko@innopolis.ru";
//        }
//    }
//
//    // copy previous opponent's move
//    // for testing
//    static class OACopyMoose implements Player {
//        @Override
//        public void reset() { }
//
//        @Override
//        public int move(int opponentLastMove, int xA, int xB, int xC) {
//            if (opponentLastMove == 0) {
//                return (int) (1 + Math.random() * 3);
//            } else {
//                return opponentLastMove;
//            }
//        }
//
//        @Override
//        public String getEmail() {
//            return "o.andriyanchenko@innopolis.ru";
//        }
//    }
//
//    // go for biggest field which is not the same as opponent's
//    // for testing
//    static class OALooserMoose implements Player {
//        @Override
//        public void reset() { }
//
//        @Override
//        public int move(int opponentLastMove, int xA, int xB, int xC) {
//            if (opponentLastMove == 0)
//                return (int) (1 + Math.random() * 3);
//            else {
//                switch (opponentLastMove) {
//                    case 1:
//                        if (xB > xC) return 2;
//                        else return 3;
//                    case 2:
//                        if (xA > xC) return 1;
//                        else return 3;
//                    case 3:
//                        if (xA > xB) return 1;
//                        else return 2;
//                }
//            }
//            return 0;
//        }
//
//        @Override
//        public String getEmail() {
//            return "o.andriyanchenko@innopolis.ru";
//        }
//    }
//
//    // take turns in going to biggest fields
//    // for testing
//    static class OATakingTurnsMoose implements Player {
//        boolean goTurn = false;
//        @Override
//        public void reset() {
//            goTurn = false;
//        }
//
//        @Override
//        public int move(int opponentLastMove, int xA, int xB, int xC) {
//            if (opponentLastMove == 0) {
//                return (int)(1 + Math.random() * 3);
//            } else {
//                if (goTurn) {
//                    goTurn = false;
//                    if (Math.max(xA, xB) == xA && Math.max(xA, xC) == xA)
//                        return 1;
//                    if (Math.max(xA, xB) == xB && Math.max(xB, xC) == xB)
//                        return 2;
//                    if (Math.max(xB, xC) == xC && Math.max(xA, xC) == xC)
//                        return 3;
//                } else {
//                    goTurn = true;
//                    if ((xA >= xB && xA <= xC) || (xA >= xC && xA <= xB))
//                        return 1;
//                    if ((xB >= xA && xB <= xC) || (xB >= xC && xB <= xA))
//                        return 2;
//                    return 3;
//                }
//            }
//            return 0;
//        }
//
//        @Override
//        public String getEmail() {
//            return "o.andriyanchenko@innopolis.ru";
//        }
//    }
//
//    // Strong moose that uses wisdom to move
//    // for testing
//    static class OAMooseThatWillNeverLoose implements Player {
//        private int steps = 0;
//        private boolean goMax = false;
//        private boolean cheatLast = false;
//        private int lastMove = 0;
//        private boolean max = true;
//
//        @Override
//        public void reset() {
//            steps = 0;
//            goMax = false;
//            cheatLast = false;
//            lastMove = 0;
//            max = true;
//        }
//
//        // Efficient approach for finding the middle number
//        // https://www.geeksforgeeks.org/middle-of-three-using-minimum-comparisons/
//        public int middleOfThree(int a, int b, int c) {
//            int x = a - b;
//            int y = b - c;
//            int z = a - c;
//            if (x * y > 0)
//                return b;
//            else if (x * z > 0)
//                return c;
//            else
//                return a;
//        }
//
//        private int getMove(int compare, int a, int b, int c) {
//            int move = 0;
//            if (a == b && a == c)
//                return (int)(1 + Math.random() * 3);
//
//            if (compare == c)
//                move = 3;
//            if (compare == b)
//                move = 2;
//            if (compare == a)
//                move = 1;
//            return move;
//        }
//
//        @Override
//        public int move(int opponentLastMove, int xA, int xB, int xC) {
//            int maxnum = Math.max(xA, Math.max(xB, xC));
//            int middle = middleOfThree(xA, xB, xC);
//            int threshold = 50;
//            this.steps++;
//
//            if (opponentLastMove == 0) {
//                if (middle == xA && middle == xB && middle == xC) {
//                    this.lastMove = (int)(1 + Math.random() * 3);
//                    return this.lastMove;
//                }
//                if (Math.random() > 0.5) {
//                    this.max = true;
//                    this.lastMove = getMove(middle, xA, xB, xC);
//                    return this.lastMove;
//                } else {
//                    this.max = false;
//                    this.lastMove = getMove(maxnum, xA, xB, xC);
//                    return this.lastMove;
//                }
//            }
//
//            // If we ended up in the same position last turn
//            // try to randomly pick adopting to different strategies this turn
//            if (opponentLastMove == this.lastMove) {
//                if (!this.cheatLast) {
//                    this.steps = 0;
//                    this.cheatLast = true;
//                }
//                if (steps > 1) { // cheated more than twice
//                    this.goMax = true;
//                }
//
//                // If the difference is less than threshold% then try to randomly pick between middle one and the max
//                if (maxnum * 100 / middle < threshold) {
//                    this.max = true;
//                    this.lastMove = getMove(middle, xA, xB, xC);
//                    return this.lastMove;
//                } else {  // If the difference is huge go for the max defend the field
//                    this.max = false;
//                    this.lastMove = getMove(maxnum, xA, xB, xC);
//                    return this.lastMove;
//                }
//            }
//            // All other time try to cooperate by going to the max one in turns
//            if (this.max || this.goMax) {
//                this.max = false;
//                this.lastMove = getMove(maxnum, xA, xB, xC);
//            } else {
//                this.max = true;
//                this.lastMove = getMove(middle, xA, xB, xC);
//            }
//            return this.lastMove;
//        }
//
//        @Override
//        public String getEmail() {
//            return "o.andriyanchenko@innopolis.ru";
//        }
//    }
//
//}