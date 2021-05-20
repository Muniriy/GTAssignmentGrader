package gametheory.assignment2.students20s;

import gametheory.assignment2.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * This code is the implementation of my agent for assignment 2 of course Game Theory.
 */
public class DanisBegishevCode implements Player {
    public boolean greedy = false;
    public int myLastMove = 0;
    public int oppLastMove = 0;
    public int oppLast_notEqual = 0;
    public boolean leftDirection;
    public boolean chooseDirection = false;
    public boolean gentle;
    public boolean firstEquality = true;
    public boolean firstComparison = false;
    public int prevA = -1, prevB = -2, prevC = -3;
    public int prevA_notEqual = -1, prevB_notEqual = -2, prevC_notEqual = -3;
    List<Boolean> oppTakesBest = new ArrayList<Boolean>();

    public String getEmail() {
        return "d.begishev@innopolis.ru";
    }

    //Setting all values to default
    public void reset() {
        greedy = false;
        myLastMove = 0;
        oppLastMove = 0;
        chooseDirection = false;
        gentle = new Random().nextInt(2) == 0;
        firstEquality = true;
        prevA = -1;
        prevB = -2;
        prevC = -3;
        prevA_notEqual = -1;
        prevB_notEqual = -2;
        prevC_notEqual = -3;
    }

    public void gentleFlip() {
        gentle = !gentle;
    }

    /**
     * @param opponentLastMove - move that opponents did in the last turn
     * @param xA               - amount of vegetation in field A
     * @param xB               - amount of vegetation in field B
     * @param xC               - amount of vegetation in field C
     * @return
     */
    public int move(int opponentLastMove, int xA, int xB, int xC) {
        // need to set the value of gentle variable at the beginning of the game
        if (firstComparison) {
            gentle = new Random().nextInt(2) == 0;
            firstComparison = false;
        }
        // if direction of the agent is already chosen, don't need to update oppLastMove
        int[] prevBest = bestField(prevA, prevB, prevC);
        int[] prevSecondBest = secondBestField(prevA, prevB, prevC);
        if (prevBest[1] == prevSecondBest[1]) {
            if (!chooseDirection) {
                oppLastMove = opponentLastMove;
            }
        } else {
            oppLast_notEqual = opponentLastMove;
        }
        prevA = xA;
        prevB = xB;
        prevC = xC;

        /**
         * if there are 2 best fields or more, need to analyze whether the last time 2 Moose faced this situation,
         * they choose different fields or the same.
         * If they chose different fields, remember it, and go always left or right depending on this remembered action.
         * Otherwise choose random square to go.
         */
        if (xA == xB && xB == xC) {
            if (myLastMove != oppLastMove && firstEquality) {
                leftDirection = myLastMove < oppLastMove;
                chooseDirection = true;
                firstEquality = false;
            }
            if (chooseDirection) {
                return leftDirection ? 1 : 3;
            }
            myLastMove = new Random().nextInt(3) + 1;
            return myLastMove;
        }

        int[] best = bestField(xA, xB, xC);
        int[] secondBest = secondBestField(xA, xB, xC);
        if (best[1] == secondBest[1]) {
            if (myLastMove != oppLastMove && firstEquality) {
                leftDirection = myLastMove < oppLastMove;
                chooseDirection = true;
                firstEquality = false;
            }
            if (chooseDirection) {
                if (leftDirection) {
                    return best[0] < secondBest[0] ? best[0] : secondBest[0];
                }
                return best[0] > secondBest[0] ? best[0] : secondBest[0];
            }

            int result = new Random().nextInt(2);
            if (result == 0) {
                myLastMove = best[0];
                return myLastMove;
            } else {
                myLastMove = secondBest[0];
                return myLastMove;
            }
        }

        if (prevA_notEqual == -1) {
            prevBest = bestField(prevA, prevB, prevC);
            prevSecondBest = secondBestField(prevA, prevB, prevC);
        } else {
            prevBest = bestField(prevA_notEqual, prevB_notEqual, prevC_notEqual);
            prevSecondBest = secondBestField(prevA_notEqual, prevB_notEqual, prevC_notEqual);
        }

        prevA_notEqual = xA;
        prevB_notEqual = xB;
        prevC_notEqual = xC;
        /**
         * remember last opponent's move and save it to the list
         */
        oppTakesBest.add(oppLast_notEqual == prevBest[0]);
        if (oppTakesBest.size() > 4) {
            for (int i = oppTakesBest.size() - 1; i > 2; i--) {
                if (oppTakesBest.get(i) == oppTakesBest.get(i - 1) == oppTakesBest.get(i - 2) == oppTakesBest.get(i - 3)) {
                    greedy = true;
                }
            }
        }

        // this is signal to change strategy to greedy
        if (greedy) {
            return best[0];
        }

        /**
         * gentle - is taking second best field
         * each turn swap this value to cooperate with opponent
         */
        if (gentle) {
            if (oppLastMove == prevBest[0]) {
                return best[0];
            }

            if (oppLastMove != prevBest[0] && prevBest[1] != prevSecondBest[1]) {
                gentleFlip();
                return secondBest[0];
            }

            return secondBest[0];
        }
        /**
         * if opponent takes second best, then there is a possibilty that he wants to cooperate
         * that's why I'm taking random choice to make cooperation.
         * For example, I will take best and opponent second best. So after that we can eassily swap each turn.
         */
        else {
            if (oppLast_notEqual != prevBest[0]) {
                if (new Random().nextInt(2) == 0) {
                    gentleFlip();
                    return best[0];
                } else {
                    return secondBest[0];
                }
            }
            gentleFlip();
            return best[0];
        }
    }

    //method for calculating best field
    public int[] bestField(int A, int B, int C) {
        int maxx = Math.max(Math.max(A, B), C);
        int[] ans = new int[2];
        if (maxx == A) {
            ans[0] = 1;
            ans[1] = A;
            return ans;
        }
        ;
        if (maxx == B) {
            ans[0] = 2;
            ans[1] = B;
            return ans;
        }
        ;
        if (maxx == C) {
            ans[0] = 3;
            ans[1] = C;
            return ans;
        }
        ;
        return ans;
    }

    //method for calculation second best field
    public int[] secondBestField(int A, int B, int C) {
        int secondBest = A > B ? (C > A ? A : (B > C ? B : C)) : (C > B ? B : (A > C ? A : C));
        int ans[] = new int[2];
        if (secondBest == C) {
            ans[0] = 3;
            ans[1] = C;
            return ans;
        }
        if (secondBest == B) {
            ans[0] = 2;
            ans[1] = B;
            return ans;
        }
        if (secondBest == A) {
            ans[0] = 1;
            ans[1] = A;
            return ans;
        }

        return ans;
    }
}