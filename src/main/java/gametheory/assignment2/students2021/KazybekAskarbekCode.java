package gametheory.assignment2.students2021;

import gametheory.assignment2.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * Class description goes here.
 *
 * @author Kazybek Askarbek
 * @version 1.1 12 Mar 2021
 */
public class KazybekAskarbekCode implements Player {
    private ArrayList<Integer> myMoves, opponentMoves;
    private ArrayList<ArrayList<Integer>> fieldValuesHistory;
    private boolean isOurMoose, isOurMooseTest2, isEating;
    private int roundNum, counter, maxInitialRandomRounds, sleepStepNum, sleepStepNum2, eatStepNum;
    private int eatingCell, opponentEatingCell, restingCell;

    public KazybekAskarbekCode() {
        roundNum = 0;
        myMoves = new ArrayList<Integer>();
        opponentMoves = new ArrayList<Integer>();
        fieldValuesHistory = new ArrayList<ArrayList<Integer>>();
        isOurMoose = false;
        isOurMooseTest2 = true;
        isEating = true;
        sleepStepNum = 10;
        sleepStepNum2 = 5;
        eatStepNum = 5;
        counter = 0;
        restingCell = 1;
        eatingCell = 1;
        opponentEatingCell = 1;
        maxInitialRandomRounds = 3;
    }

    public void reset() {
        roundNum = 0;
        myMoves = new ArrayList<Integer>();
        opponentMoves = new ArrayList<Integer>();
        fieldValuesHistory = new ArrayList<ArrayList<Integer>>();
        isOurMoose = false;
        isOurMooseTest2 = true;
        isEating = true;
        sleepStepNum = 10;
        sleepStepNum2 = 5;
        eatStepNum = 5;
        counter = 0;
        restingCell = 1;
        eatingCell = 1;
        opponentEatingCell = 1;
        maxInitialRandomRounds = 3;
    }


    public int moveAsRandomGreedy(ArrayList<Integer> fieldValues) {
        int max = Collections.max(fieldValues);
        int min = Collections.min(fieldValues);

        ArrayList<Integer> maxValueIds = new ArrayList<Integer>();

        for (int i = 0; i < fieldValues.size(); i++) {
            if (fieldValues.get(i) > 6) {
                return i + 1;
            }
            if (fieldValues.get(i) == max) {
                maxValueIds.add(i);
            }
        }

        Random rand = new Random();
        return maxValueIds.get(rand.nextInt(maxValueIds.size())) + 1;
    }


    public int moveAsRandom() {
        Random rand = new Random();
        return rand.nextInt(3) + 1;
    }


    public int getRestingCell(int eatingCell, int opponentEatingCell) {
        for (int i = 1; i <= 3; i++) {
            if (i != eatingCell && i != opponentEatingCell) {
                return i;
            }
        }
        return 1;
    }

    public int move(int opponentLastMove, int xA, int xB, int xC) {
        roundNum++;
        opponentMoves.add(opponentLastMove);

        ArrayList<Integer> fieldValues = new ArrayList<Integer>();

        fieldValues.add(xA);
        fieldValues.add(xB);
        fieldValues.add(xC);


        int myMove = 1;

        if (isOurMoose) {
            // test whether moose played fair
            if (opponentLastMove == eatingCell) {
                isOurMoose = false;
                isOurMooseTest2 = false;
                myMove = moveAsRandomGreedy(fieldValues);
            } else {
                if (isEating) {
                    if (counter <= eatStepNum) {
                        counter++;
                        myMove = eatingCell;
                    } else {
                        counter = 1;
                        eatStepNum = 5;
                        sleepStepNum = 5;
                        isEating = false;

                        myMove = restingCell;
                    }
                } else {
                    if (counter <= sleepStepNum) {
                        counter++;
                        myMove = restingCell;
                    } else {
                        counter = 1;
                        isEating = true;
                        eatStepNum = 5;
                        sleepStepNum = 5;

                        myMove = eatingCell;
                    }
                }
            }
        } else {
            if (isOurMooseTest2) {
                if (opponentLastMove != 0 && myMoves.size() != 0 && opponentLastMove != myMoves.get(myMoves.size() - 1)) {

                    // cell where my moose will eat
                    eatingCell = myMoves.get(myMoves.size() - 1);
                    // cell where opponent will eat
                    opponentEatingCell = opponentLastMove;
                    // cell where both players go to rest
                    restingCell = getRestingCell(eatingCell, opponentEatingCell);

                    counter = 1;
                    isEating = false;
                    isOurMoose = true;
                    sleepStepNum = 10;
                    eatStepNum = 5;

                    myMove = restingCell;
                } else {
                    if (counter == maxInitialRandomRounds) {
                        isOurMooseTest2 = false;
                        myMove = moveAsRandomGreedy(fieldValues);
                    }
                    myMove = moveAsRandom();
                }
            } else {
                myMove = moveAsRandomGreedy(fieldValues);
            }
        }
        myMoves.add(myMove);
        return myMove;
    }

    public String getEmail() {
        return "k.askarbek@innopolis.university";
    }
}