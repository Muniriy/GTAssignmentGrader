package gametheory.assignment2.students2021;

import gametheory.assignment2.Player;

import java.util.ArrayList;
import java.util.Random;

/**
 * @author Alisa Martyanova, B18-SE02
 * <p>This class contains implementation of agent,
 * that will take part in the tournament</p>
 * <p>The main idea is to find a cooperator via predefined combination of steps;
 * if cooperator is recognized, then use cooperation strategy:
 * sit for 40 rounds in the field A (fields B and C will save more score),
 * then my agent goes to C, cooperator's agent goes to B, and we both get points;
 * if cooperator is not recognized, then use the following strategy:
 * go to field with maximum score, if there are several such fields,
 * then choose random</p>
 */
public class AlisaMartyanovaCode implements Player {

    private int round = 1; // number of rounds played
    private int recognized = 1; // check if student-cooperator recognized

    @Override
    public void reset() {
        this.round = 1;
        this.recognized = 1;
    }

    @Override
    public int move(int opponentLastMove, int xA, int xB, int xC) {
        int move;
        int[] tmp = {xA, xB, xC};

        switch (round) {
            case (1):
                move = 3;
                round++;
                return move;
            case (2):
                if (opponentLastMove == 2)
                    move = 1;
                else {
                    recognized = 0;
                    move = getPosition(tmp);
                }

                round++;
                return move;
            case (3):
                if (recognized == 0)
                    move = getPosition(tmp);
                else {
                    if (opponentLastMove == 1)
                        move = 1;
                    else {
                        recognized = 0;
                        move = getPosition(tmp);
                    }
                }

                round++;
                return move;
            case (4):
                if (recognized == 0)
                    move = getPosition(tmp);
                else {
                    if (opponentLastMove == 3)
                        move = 2;
                    else {
                        recognized = 0;
                        move = getPosition(tmp);
                    }
                }

                round++;
                return move;
            case (5):
                if (recognized == 0)
                    move = getPosition(tmp);
                else {
                    if (opponentLastMove == 2)
                        move = 3;
                    else {
                        recognized = 0;
                        move = getPosition(tmp);
                    }
                }

                round++;
                return move;
            case (6):
                if (recognized == 0)
                    move = getPosition(tmp);
                else {
                    if (opponentLastMove == 2)
                        move = 1;
                    else {
                        recognized = 0;
                        move = getPosition(tmp);
                    }
                }

                round++;
                return move;
            default:

                if ((round < 47 && opponentLastMove != 1) || (round >= 47 && opponentLastMove != 2)) {
                    recognized = 0;
                }
                if (recognized == 1) {
                    if (round < 46) {
                        move = 1;
                    } else {
                        move = 3;
                    }
                } else {
                    move = getPosition(tmp);
                }
                round++;
                return move;

        }
    }

    @Override
    public String getEmail() {
        return "a.martyanova@innopolis.university";
    }

    /**
     * <p>get position of field with maximum score,
     * if there are several - choose random</p>
     *
     * @param array current fields score
     * @return number 1, 2 or 3 - move
     */
    private int getPosition(int[] array) {
        ArrayList<Integer> idxs = getAllMax(array);
        int move = idxs.get(new Random().nextInt(idxs.size())) + 1;
        return move;
    }

    /**
     * <p>get positions of all fields with maximum value</p>
     *
     * @param array current fields score
     * @return indexes of fields with maximum score
     */
    private ArrayList<Integer> getAllMax(int[] array) {
        int maxVal = getMaxVal(array);
        ArrayList<Integer> idxs = new ArrayList<>();
        for (int i = 0; i < array.length; i++) {
            if (array[i] == maxVal) {
                idxs.add(i);
            }
        }
        return idxs;
    }

    /**
     * <p>get maximum value</p>
     *
     * @param array current fields score
     * @return value of field with maximum score
     */
    private int getMaxVal(int[] array) {
        int maxIdx = 0;
        for (int i = 1; i < array.length; i++) {
            if (array[i] > array[maxIdx]) {
                maxIdx = i;
            }
        }
        return array[maxIdx];
    }

}
