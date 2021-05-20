package gametheory.assignment2.students2021;

import java.util.ArrayList;
import java.util.Random;
import gametheory.assignment2.Player;

public class AlexanderTrushinCode implements Player {
    private ArrayList<Integer> myLastMoves = new ArrayList<>();
    private ArrayList<Integer> opponentLastMoves = new ArrayList<>();
    int opponentsMaxMovesInRaw = 0;
    int myMaxMoveInRaw = 0;

    @Override
    public void reset() {
        myLastMoves = new ArrayList<Integer>();
        opponentLastMoves = new ArrayList<Integer>();
        opponentsMaxMovesInRaw = 0;
        myMaxMoveInRaw = 0;
    }

    @Override
    public int move(int opponentLastMove, int xA, int xB, int xC) {

        opponentLastMoves.add(opponentLastMove);
        GameField[] fields = {new GameField(1, xA), new GameField(2, xB), new GameField(3, xC)};
        if (opponentLastMove != 0) {

            GameField[] previousStates = getPreviousState(opponentLastMove, fields);
            // if previous step of opponent was max and my wasn't max increment opponentsMaxMovesInRaw
            if (previousStates[opponentLastMove - 1].value == max(previousStates[0], previousStates[1], previousStates[2]).value && previousStates[getMyLastMove() - 1].value != max(previousStates[0], previousStates[1], previousStates[2]).value) {
                opponentsMaxMovesInRaw++;
            }

            // if previous step of opponent was max and my step was max and our steps weren't equal opponentsMaxMovesInRaw = 0 myMaxMoveInRaw = 0
            else if (previousStates[opponentLastMove - 1].value == max(previousStates[0], previousStates[1], previousStates[2]).value && previousStates[getMyLastMove() - 1].value == max(previousStates[0], previousStates[1], previousStates[2]).value && opponentLastMove != getMyLastMove()) {
                opponentsMaxMovesInRaw++;
                myMaxMoveInRaw++;
            } else if (previousStates[opponentLastMove - 1].value == max(previousStates[0], previousStates[1], previousStates[2]).value && previousStates[getMyLastMove() - 1].value == max(previousStates[0], previousStates[1], previousStates[2]).value && opponentLastMove == getMyLastMove()) {
                opponentsMaxMovesInRaw++;
                myMaxMoveInRaw++;
            } else if (previousStates[opponentLastMove - 1].value != max(previousStates[0], previousStates[1], previousStates[2]).value && previousStates[getMyLastMove() - 1].value == max(previousStates[0], previousStates[1], previousStates[2]).value) {
                myMaxMoveInRaw++;
            }

            Random rand = new Random();

            if (opponentsMaxMovesInRaw >= 1 ) {
                if (fields[getMyLastMove() - 1].value == max(fields[0], fields[1], fields[2]).value ) {
                    myLastMoves.add(getMyLastMove());
                    return getMyLastMove();
                }
                myLastMoves.add(max(fields[0], fields[1], fields[2]).index);

                return getMyLastMove();
            }
            // if our last move were equal go to max
            if (opponentLastMove == getMyLastMove() && getMyLastMove() != 0) {
                myLastMoves.add(max(fields[0], fields[1], fields[2]).index);
                return getMyLastMove();
            }

            // if its first turn go to max
            if (getMyLastMove() == 0) {
                myLastMoves.add(max(fields[0], fields[1], fields[2]).index);
                return getMyLastMove();
            }

            //if previous step of opponent was max and my wasn't i go to max now
            if (opponentLastMove == max(previousStates[0], previousStates[1], previousStates[2]).index && max(previousStates[0], previousStates[1], previousStates[2]).value != previousStates[getMyLastMove() - 1].value) {
                myLastMoves.add(max(fields[0], fields[1], fields[2]).index);
                return getMyLastMove();
            }
            // if previous step of opponent was max and also my was max and our steps wasn't the same go to max, if max move remains the same for me i just go to the same field that i went in previous step
            if (opponentLastMove == max(previousStates[0], previousStates[1], previousStates[2]).index && max(previousStates[0], previousStates[1], previousStates[2]).value == previousStates[getMyLastMove() - 1].value && opponentLastMove != getMyLastMove()) {
                // if my previous step remains max i again go to max
                if (fields[getMyLastMove() - 1].value == max(fields[0], fields[1], fields[2]).value) {
                    myLastMoves.add(getMyLastMove());
                    return getMyLastMove();
                }
                myLastMoves.add(max(fields[0], fields[1], fields[2]).index);
                return getMyLastMove();
            }

            myLastMoves.add(preMax(fields[0], fields[1], fields[2]).index);
            return getMyLastMove();

        } else {
            myLastMoves.add(max(fields[0], fields[1], fields[2]).index);
            return getMyLastMove();
        }


    }

    @Override
    public String getEmail() {
        return "a.trushin@innopolis.ru";
    }

    // returns max field
    private GameField max(GameField xA, GameField xB, GameField xC) {
        GameField max;
        if (xA.value > xB.value) {
            max = xA;
        } else {
            max = xB;
        }

        if (xC.value > max.value) {
            max = xC;
        }


        return max;
    }

    // returns second max field
    private GameField preMax(GameField xA, GameField xB, GameField xC) {
        GameField preMax;
        if (xA.value >= xB.value && xB.value >= xC.value || xC.value >= xB.value && xB.value >= xA.value) {
            preMax = xB;
        } else if (xB.value >= xA.value && xA.value >= xC.value || xC.value >= xA.value && xA.value >= xB.value) {
            preMax = xA;
        } else preMax = xC;


        return preMax;
    }

    // returns array of fields with previous state
    GameField[] getPreviousState(int opponentLastMove, GameField[] fields) {
        GameField[] prevFields = {new GameField(fields[0].index, fields[0].value), new GameField(fields[1].index, fields[1].value), new GameField(fields[2].index, fields[2].value)};
        if (opponentLastMove == getMyLastMove()) {
            prevFields[opponentLastMove - 1].value++;
        } else {
            prevFields[opponentLastMove - 1].value++;
            prevFields[getMyLastMove() - 1].value++;
        }

        return prevFields;
    }

    //returns last move of my agent
    int getMyLastMove() {
        if (myLastMoves != null) {
            return myLastMoves.get(myLastMoves.size() - 1);
        } else {
            return 0;
        }

    }





}



// describes game field
class GameField {
    int index;
    int value;

    GameField(int index, int value) {
        this.index = index;
        this.value = value;
    }
}
