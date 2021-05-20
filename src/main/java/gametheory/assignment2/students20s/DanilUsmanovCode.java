/*
 * Copyright (c) 2021. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */
package gametheory.assignment2.students20s;

import gametheory.assignment2.Player;

import java.util.LinkedList;
import java.util.Random;

import static java.lang.Math.max;

/**
 * Agent implementation of Player interface for Moose game.
 * It use Cooperate or Punish strategy
 *
 * @author Danil Usmanov
 * @version 1.0 11 March 2021
 */
public class DanilUsmanovCode implements Player {
    LinkedList<Integer> opponentLastMoves;  // Stores opponent's moves
    LinkedList<Integer> myLastMoves;  // Store agent's moves
    LinkedList<Integer> a_score;  // Store a field values
    LinkedList<Integer> b_score;  // Store b field values
    LinkedList<Integer> c_score;  // Store c field values
    Random random;
    int my_move;
    int counter;
    boolean flag;

    /**
     * Constructor of the class
     */
    public DanilUsmanovCode() {
        this.opponentLastMoves = new LinkedList<>();
        this.myLastMoves = new LinkedList<>();
        this.a_score = new LinkedList<>();
        this.b_score = new LinkedList<>();
        this.c_score = new LinkedList<>();
        this.random = new Random();
        this.my_move = random.nextInt(3) + 1;
        this.counter = 0;
        this.flag = true;
    }

    /**
     * This method is called to reset the agent before the match
     * with another player containing several rounds
     */
    @Override
    public void reset() {
        this.opponentLastMoves.clear();
        this.myLastMoves.clear();
        this.a_score.clear();
        this.b_score.clear();
        this.c_score.clear();
        this.my_move = random.nextInt(3) + 1;
        this.counter = 0;
        this.flag = true;
    }

    /**
     * This method returns the max field number of the fields based on
     * X values of all fields.
     * Initially, X for all fields is equal to 1.
     *
     * @param xA the argument X for a field A
     * @param xB the argument X for a field B
     * @param xC the argument X for a field C
     * @return max field can be 1 for A, 2 for B
     * and 3 for C fields
     */
    private int getMaxField(int xA, int xB, int xC) {
        if (xA >= max(xB, xC)) {
            if (xA == max(xB, xC)) {  // If there is more max values
                if (random.nextInt(2) == 1) {  // Randomly return some of them
                    return 1;
                } else if (xB > xC) {
                    return 2;
                } else {
                    return 3;
                }

            } else {
                return 1;  // If xA is absolute max
            }
        }
        if (xB >= max(xA, xC)) {
            if (xB == max(xA, xC)) {  // If there is more max values
                if (random.nextInt(2) == 1) {  // Randomly return some of them
                    return 2;
                } else {
                    return 3;
                }
            } else {  // If xB is absolute max
                return 2;
            }

        }
        return 3;  // Return xC as it is absolute max
    }

    /**
     * This method returns the second max field number of the fields based on
     * X values of all fields.
     * Initially, X for all fields is equal to 1.
     *
     * @param xA the argument X for a field A
     * @param xB the argument X for a field B
     * @param xC the argument X for a field C
     * @return second max field can be 1 for A, 2 for B
     * and 3 for C fields
     */
    private int getSecondMaxField(int xA, int xB, int xC) {
        if (xA >= max(xB, xC)) {
            if (xB == max(xB, xC)) { // Return the second max field
                return 2;
            } else {
                return 3;
            }
        }
        if (xB >= max(xA, xC)) {
            if (xA == max(xA, xC)) { // Return the second max field
                return 1;
            } else {
                return 3;
            }
        }
        // if xC is absolute max
        if (xA == max(xA, xB)) { // Return the second max field
            return 1;
        } else {
            return 2;
        }
    }

    /**
     * This method returns True if opponents plays as Greedy
     *
     * @return boolean
     */
    private boolean isGreedy() {
        boolean flag2 = false;
        int cnt = 0;
        for (int i = 0; i < opponentLastMoves.size(); i++) {
            if (opponentLastMoves.get(i) == getMaxField(a_score.get(i),
                    b_score.get(i), c_score.get(i))) {
                cnt++;
            } else {
                cnt = 0;
                flag2 = false;
            }

            if (cnt > 5) {
                flag2 = true;
            }
        }

        return flag2;
    }

    /**
     * This method returns True if opponents plays as Cooperator
     *
     * @return boolean
     */
    private boolean isCooperator() {
        int cnt1 = 0;
        int cnt2 = 0;
        for (int i = 0; i < opponentLastMoves.size(); i++) {
            if (opponentLastMoves.get(i) == getMaxField(a_score.get(i),
                    b_score.get(i), c_score.get(i))) {
                cnt1 = cnt1 + 1;
            } else {
                cnt2 = cnt2 + 1;
            }
        }
        return Math.abs(cnt1 - cnt2) < 3;
    }


    /**
     * This method returns the move of the player based on
     * the last move of the opponent and X values of all fields.
     * Initially, X for all fields is equal to 1 and last opponent
     * move is equal to 0
     *
     * @param opponentLastMove the last move of the opponent
     *                         varies from 0 to 3
     *                         (0 – if this is the first move)
     * @param xA               the argument X for a field A
     * @param xB               the argument X for a field B
     * @param xC               the argument X for a field C
     * @return the move of the player can be 1 for A, 2 for B
     * and 3 for C fields
     */
    @Override
    public int move(int opponentLastMove, int xA, int xB, int xC) {
        if (opponentLastMoves.size() > 5) {
            if (isCooperator()) {  // If opponent is cooperator
                if ((opponentLastMove == getMaxField(a_score.getLast(),
                        b_score.getLast(), c_score.getLast()))
                        && (opponentLastMove != myLastMoves.getLast())) {

                    my_move = getMaxField(xA, xB, xC);
                } else {
                    my_move = getSecondMaxField(xA, xB, xC);
                }
            } else if (isGreedy()) {  // If opponent is greedy
                my_move = getSecondMaxField(xA, xB, xC);
            } else {
                my_move = getMaxField(xA, xB, xC);
            }

        } else {
            my_move = getMaxField(xA, xB, xC);
        }

        // Store data from this round
        a_score.add(xA);
        b_score.add(xB);
        c_score.add(xC);
        opponentLastMoves.add(opponentLastMove);
        myLastMoves.add(my_move);
        return my_move;
    }

    /**
     * This method returns my IU email
     *
     * @return my email
     */
    @Override
    public String getEmail() {
        return "d.usmanov@innopolis.university";
    }
}
