package gametheory.assignment2.students20s;

import gametheory.assignment2.Player;

import java.util.LinkedList;
import java.util.Random;

public class AnnaGorbCode implements Player {

    private int counter = 1; // to count 7 trial rounds
    private int[] opponentMoves = {0, 0, 0, 0, 0, 0, 0, 0}; // 7 first opponent's moves
    private int[] myMoves = {0, 0, 0, 0, 0, 0, 0, 0}; // 7 first my moves
    private int mainField = 0; // at this field my player will eat the grass
    private int waitingField = 0; // at this field we together will wait till the grass on our Main fields will grow
    private int opponentMainField = 0; // at this field opponent's player will eat the grass
    private boolean CooperationEstablished = false; // absence of collision considered as establishement of cooperation
    private boolean Betrayal = false; // if the opponent will not cooperate - stop cooperate also
    private boolean grow = false; // true during the period of the gross growing
    private boolean eat = false; // true during the period of eating
    private int timeCounter = 0; //when reach 6 - player can start eat or wait
    private int myLastMove = 0; // my last move
    private LinkedList<Integer> generatedTargetFields = new LinkedList<>(); // if random strategy - save my previous moves here

    /**
     * here we need to reset health of the player and all fields of the class
     */
    @Override
    public void reset() {
        counter = 1;
        opponentMoves = new int[]{0, 0, 0, 0, 0, 0, 0, 0};
        myMoves = new int[]{0, 0, 0, 0, 0, 0, 0, 0};
        mainField = 0;
        waitingField = 0;
        opponentMainField = 0;
        CooperationEstablished = false;
        Betrayal = false;
        grow = false;
        eat = false;
        timeCounter = 0;
        myLastMove = 0;
        generatedTargetFields = new LinkedList<>();
    }

    /**
     * cooperate with other players while they also cooperate
     *
     * @param opponentLastMove opponent last move
     * @param xA               amount of grass on A field
     * @param xB               amount of grass on B field
     * @param xC               amount of grass on C field
     * @return new field which we want to choose
     */
    @Override
    public int move(int opponentLastMove, int xA, int xB, int xC) {
        if (CooperationEstablished && Betrayal || !CooperationEstablished && counter >= 8) {
            Betrayal = true;
            return (randomWithoutRepetitions());
        } else if (!CooperationEstablished && counter < 8) {
            // try to cooperate
            opponentMoves[counter - 1] = opponentLastMove;
            myMoves[counter] = tryToCooperate();
            counter++;
            return (myMoves[counter - 1]);
        } else if (opponentLastMove == mainField && mainField != 0) {
            Betrayal = true;
            return randomWithoutRepetitions();
        } else {
            // continue to cooperate
            myLastMove = continueToCooperate();
            return (myLastMove);
        }
    }

    /**
     * if cooperation was not established yet, players try to do it
     *
     * @return move
     */
    private int tryToCooperate() {
        if (opponentMoves[counter - 1] == myMoves[counter - 1]) {
            // if my player and the opponent are in the same field - randomly choose the new one
            Random rand = new Random();
            return (rand.nextInt(3) + 1);
        } else {
            // if my player and the opponent are in different fields - then put a flag - cooperate
            CooperationEstablished = true;
            mainField = myMoves[counter - 1];
            opponentMainField = opponentMoves[counter - 1];
            waitingField = 6 - mainField - opponentMainField;
            grow = true;
            return waitingField;
        }
    }

    /**
     * if grass grow - player wait in waitingField, if there is enough grass - player eat it
     *
     * @return
     */
    private int continueToCooperate() {
        if (grow && timeCounter != 6) {
            // if grass grow - we wait
            timeCounter++;
            return (waitingField);
        } else if (grow && timeCounter == 6) {
            timeCounter = 0;
            grow = false;
            eat = true;
            return (mainField);
        } else if (eat && timeCounter != 6) {
            // if grass already grown - we eat
            timeCounter++;
            return (mainField);
        } else {
            timeCounter = 0;
            grow = true;
            eat = false;
            return (waitingField);
        }
    }

    /**
     * generate random numbers without repetitions
     *
     * @return move
     */
    private int randomWithoutRepetitions() {
        int targetField = randomGenerator();
        if (generatedTargetFields.size() != 0) {
            targetField = checkRepetitions(generatedTargetFields, targetField);
        }
        generatedTargetFields.add(targetField);
        return targetField;
    }

    /**
     * generate random number from 1 to 3 inclusive
     *
     * @return int from 1 to 3 inclusive
     */
    private int randomGenerator() {
        Random rand = new Random();
        return rand.nextInt(3) + 1;
    }

    /**
     * check if the proposed field is the same with the previous one and choose new one if yes
     *
     * @param list        just list
     * @param targetField new field which we want to choose
     * @return new field which we want to choose
     */
    private int checkRepetitions(LinkedList<Integer> list, int targetField) {
        Random rand = new Random();
        while (list.getLast() == targetField) {
            targetField = rand.nextInt(3) + 1;
        }
        return targetField;
    }

    @Override
    public String getEmail() {
        return "a.gorb@innopolis.university";
    }

}
