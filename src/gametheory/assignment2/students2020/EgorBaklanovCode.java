package gametheory.assignment2.students2020;

import gametheory.assignment2.Player;

import java.util.*;

public class EgorBaklanovCode implements Player {

    private int myLastMove = -1;

    public EgorBaklanovCode() {
    }

    public double calculatePayoff(int X) {
        return (10 * Math.pow(Math.E, X)) / (1 + Math.pow(Math.E, X)) - (10 * Math.pow(Math.E, 0)) / (1 + Math.pow(Math.E, 0));
    }

    // Calculates previous state of all fields based on opponents and player moves.
    public Map<Integer, Integer> getPreviousState(int opponentLastMove, int playerLastMove, Map<Integer, Integer> currFields) {
        Map<Integer, Integer> prevFields = new TreeMap<>(currFields);
        if (opponentLastMove == playerLastMove) {
            prevFields.put(playerLastMove, prevFields.get(playerLastMove) + 1);
        } else {
            prevFields.put(playerLastMove, prevFields.get(playerLastMove) + 1);
            prevFields.put(opponentLastMove, prevFields.get(opponentLastMove) + 1);
        }

        return prevFields;
    }

    public void reset() {
        myLastMove = -1;
    }

    public boolean amIGreedy(){
        return false;
    }

    public int move(int opponentLastMove, int xA, int xB, int xC) {
        Map<Double, ArrayList<Integer>> moveToPayloadMap = new TreeMap<>();
        Map<Integer, Integer> currFields = new TreeMap<>();
        currFields.put(1, xA);
        currFields.put(2, xB);
        currFields.put(3, xC);

        moveToPayloadMap.put(calculatePayoff(xA), new ArrayList<>(Arrays.asList(1)));
        if (moveToPayloadMap.containsKey(calculatePayoff(xB)))
            moveToPayloadMap.get(calculatePayoff(xB)).add(2);
        else
            moveToPayloadMap.put(calculatePayoff(xB), new ArrayList<>(Arrays.asList(2)));

        if (moveToPayloadMap.containsKey(calculatePayoff(xC)))
            moveToPayloadMap.get(calculatePayoff(xC)).add(3);
        else
            moveToPayloadMap.put(calculatePayoff(xC), new ArrayList<>(Arrays.asList(3)));

        boolean opponentAggressive = false;

        if (opponentLastMove != 0 && this.myLastMove != -1) {
            Map<Integer, Integer> prevFields = getPreviousState(opponentLastMove, this.myLastMove, currFields);
            int maxX = ((TreeMap<Integer, Integer>) prevFields).lastEntry().getValue();
            for (Integer fieldNumber : prevFields.keySet())
                if (prevFields.get(fieldNumber) == maxX)
                    if (fieldNumber == opponentLastMove)
                        opponentAggressive = true;
        }

        ArrayList<Integer> maxPossibleMoves = ((TreeMap<Double, ArrayList<Integer>>) moveToPayloadMap).lastEntry().getValue();

        // If opponent chooses maximum payload on last move - select the second best payload.
        // In this case we get at least something, but not 0.
        if (opponentAggressive && maxPossibleMoves.size() < 2 && !amIGreedy()) {
            ((TreeMap<Double, ArrayList<Integer>>) moveToPayloadMap).pollLastEntry();
        }
        ArrayList<Integer> possibleMoves = ((TreeMap<Double, ArrayList<Integer>>) moveToPayloadMap).lastEntry().getValue();
        int resultMove = possibleMoves.get(new Random().nextInt(possibleMoves.size()));
        this.myLastMove = resultMove;
        return resultMove;
    }

    public int getLastMove() {
        return this.myLastMove;
    }

    public String getEmail() {
        return "e.baklanov@innopolis.ru";
    }

}
