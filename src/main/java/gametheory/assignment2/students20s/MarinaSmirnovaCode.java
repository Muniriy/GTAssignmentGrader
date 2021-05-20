package gametheory.assignment2.students20s;

import gametheory.assignment2.Player;

import java.util.Random;

public class MarinaSmirnovaCode implements Player {

    public int agentLastMove;
    public boolean isFirstCooperation;
    public boolean isStrategyCooperative;

    public int waitingCell;
    public int myCell;
    public int opponentCell;

    public int aggressivenessThreshold;


    public MarinaSmirnovaCode() {
        reset();
    }

    @Override
    public void reset() {
        agentLastMove = 0;
        waitingCell = 0;
        myCell = 0;
        opponentCell = 0;
        isStrategyCooperative = true;
        isFirstCooperation = true;
        aggressivenessThreshold = 100;
    }

    @Override
    public int move(int opponentLastMove, int xA, int xB, int xC) {

        Random randomizer = new Random();
        int randomCell = randomizer.nextInt(3) + 1;

        int[] vegetationAmountArray = new int[3];
        vegetationAmountArray[0] = xA;
        vegetationAmountArray[1] = xB;
        vegetationAmountArray[2] = xC;

        // choose 1st cell randomly
        if (opponentLastMove == 0) {
            agentLastMove = randomCell;
            return randomCell;
        }

        // before choosing between cooperative and noon-cooperative strategies,
        // agents need to be placed on the different cells
        // if cells are the same -> continue with random
        if (isFirstCooperation && opponentLastMove == agentLastMove) {
            agentLastMove = randomCell;
            return randomCell;
        }

        // if cells are different -> try to cooperate
        if (isFirstCooperation) {
            // 1. assign cell types
            myCell = agentLastMove;
            opponentCell = opponentLastMove;
            waitingCell = (1 + 2 + 3) - myCell - opponentCell;

            isFirstCooperation = false;

            // 2. move to waiting
            agentLastMove = waitingCell;
            return waitingCell;
        }

        // 1. check cooperation condition
        if (opponentLastMove == myCell) {
            isStrategyCooperative = false;
        }

        // 2. act accordingly
        if (isStrategyCooperative) {
            // cooperating
            // check the amount of vegetation on my cell
            if (vegetationAmountArray[myCell - 1] < 2) {
                // waiting
                agentLastMove = waitingCell;
            } else {
                // eating
                agentLastMove = myCell;
            }
            return agentLastMove;
        } else {
            // cheating
            // 1. most of the time -> be aggressive (capture the highest score)
            // 2. add periodic randomness
            // 3. add periodic conceding when aggressiveness limit is decreased to 0
            int maxIndex;
            int secondMaxIndex;

            //find the greatest amount of vegetation in the cells
            if (vegetationAmountArray[0] > vegetationAmountArray[1]) {
                if (vegetationAmountArray[0] > vegetationAmountArray[2]) {
                    maxIndex = 0;
                    if (vegetationAmountArray[1] > vegetationAmountArray[2]) {
                        secondMaxIndex = 1;
                    } else {
                        secondMaxIndex = 2;
                    }

                } else {
                    maxIndex = 2;
                    secondMaxIndex = 0;
                }
            } else {
                if (vegetationAmountArray[1] > vegetationAmountArray[2]) {
                    maxIndex = 1;
                    if (vegetationAmountArray[0] > vegetationAmountArray[2]) {
                        secondMaxIndex = 0;
                    } else {
                        secondMaxIndex = 2;
                    }
                } else {
                    maxIndex = 2;
                    secondMaxIndex = 1;
                }
            }

            // with probability 1% there will be random moves
            if (randomizer.nextInt(100) == 0) {
                agentLastMove = randomCell;
                return agentLastMove;
            }

            // periodically around 50 or more steps the strategy allows friendly move
            if (aggressivenessThreshold == 0) {
                aggressivenessThreshold = 50;
                agentLastMove = secondMaxIndex + 1;
                return agentLastMove;
            }

            aggressivenessThreshold--;

            agentLastMove = maxIndex + 1;
            return agentLastMove;
        }

    }

    @Override
    public String getEmail() {
        return "m.smirnova@innopolis.university";
    }
}
