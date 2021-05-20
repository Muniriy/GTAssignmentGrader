package gametheory.assignment2.students20s;

import gametheory.assignment2.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AlecseyMurashkoCode implements Player {

    private final Random rnd = new Random();
    private int waitingField;
    private int growingField;
    private boolean shakedHands = false;
    private boolean betrayed = false;
    private boolean takingGrowing = false;

    private List<Integer> myMoves = new ArrayList<>();
    private List<Integer> opponentsMoves = new ArrayList<>();

    @Override
    public void reset() {
        shakedHands = false;
        betrayed = false;
        myMoves = new ArrayList<>();
        opponentsMoves = new ArrayList<>();
    }

    @Override
    public int move(int opponentLastMove, int xA, int xB, int xC) {
        if (opponentLastMove == 0) {
            return getRandomMove();
        }

        opponentsMoves.add(opponentLastMove);
        if (!shakedHands) {
            return shakeHands(opponentLastMove);
        } else if (!betrayed) {
            return moveWhileNotBetrayed(opponentLastMove, xA, xB, xC);
        } else {
            return offenciveStrategy(opponentLastMove, xA, xB, xC);
        }

    }

    private int shakeHands(int opponentLastMove) {
        if (myLastMove() == opponentLastMove) {
            return getRandomMove();
        } else {
            growingField = myLastMove();
            waitingField = 6 - growingField - opponentLastMove;
            myMoves.add(waitingField);
            shakedHands = true;
            return waitingField;
        }
    }

    private int moveWhileNotBetrayed(int opponentLastMove, int xA, int xB, int xC) {
        if (opponentLastMove == growingField) {
            betrayed = true;
            return offenciveStrategy(opponentLastMove, xA, xB, xC);
        } else {
            int[] fields = new int[]{-1, xA, xB, xC};
            if (fields[growingField] >= 5 || takingGrowing) {
                if (fields[growingField] == 1) {
                    takingGrowing = false;
                }

                myMoves.add(growingField);
                return growingField;
            } else {
                myMoves.add(waitingField);
                return waitingField;
            }
        }
    }

    private int offenciveStrategy(int opponentLastMove, int xA, int xB, int xC) {
        int[] fields = {-1, xA, xB, xC};
        if (fields[opponentLastMove] == 0) {
            myMoves.add(myLastMove());
            return myLastMove();
        } else {
            if (myLastMove() == opponentLastMove) {
                return getRandomMove();
            } else {
                if (fields[myLastMove()] != 0) {
                    myMoves.add(myLastMove());
                } else {
                    myMoves.add(6 - myLastMove() - opponentLastMove);
                }
                return myLastMove();
            }
        }
    }

    private int getRandomMove() {
        myMoves.add(getRandomField());
        return myLastMove();
    }

    private int myLastMove() {
        return myMoves.get(myMoves.size() - 1);
    }

    private int getRandomField() {
        return rnd.nextInt(3) + 1;
    }

    @Override
    public String getEmail() {
        return "a.murashko@innopolis.university";
    }

}
