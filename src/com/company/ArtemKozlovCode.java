package com.company;

import java.util.*;
import java.util.stream.Collectors;

public class ArtemKozlovCode implements Player {


    private Integer lastMove = 0;

    @Override
    public int move(int opponentLastMove, int xA, int xB, int xC) {
        // Calculate previous state of fields
        int lastRoundXA = xA == 0 ? 0 : opponentLastMove == 1 || lastMove == 1 ? xA + 1 : xA - 1;
        int lastRoundXB = xB == 0 ? 0 : opponentLastMove == 2 || lastMove == 2 ? xB + 1 : xB - 1;
        int lastRoundXC = xC == 0 ? 0 : opponentLastMove == 3 || lastMove == 3 ? xC + 1 : xC - 1;

        // structure to sort fields(state of last round) by value
        Map<String, Integer> lastRoundFieldsRank = new HashMap<>();
        lastRoundFieldsRank.put("a", lastRoundXA);
        lastRoundFieldsRank.put("b", lastRoundXB);
        lastRoundFieldsRank.put("c", lastRoundXC);

        // structure to sort fields(state of current round) by value
        Map<String, Integer> currentRoundFeildsRank = new HashMap<>();
        currentRoundFeildsRank.put("a", xA);
        currentRoundFeildsRank.put("b", xB);
        currentRoundFeildsRank.put("c", xC);
        // Maping String -> Integer in order to calculate move based on field
        Map<String, Integer> mapping = new HashMap<>();
        mapping.put("a",1);
        mapping.put("b",1);
        mapping.put("c",1);

        Map<Integer, String> reverseMapping = new HashMap<>();
        reverseMapping.put(1, "a");
        reverseMapping.put(2, "b");
        reverseMapping.put(3, "c");

        if (opponentLastMove == 0) {
            this.lastMove =  new Random().nextInt(3) + 1;
        } else {

            // Sort fields of the last round state
            Map<String, Integer> lastRoundSorted = lastRoundFieldsRank
                    .entrySet()
                    .stream()
                    .sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue()))
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
            Object[] lastRoundRes = lastRoundSorted.keySet().toArray();

            // Sort fields of the current round state
            Map<String, Integer> currentRoundSorted = currentRoundFeildsRank
                    .entrySet()
                    .stream()
                    .sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue()))
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
            Object[] currentRoundRes = currentRoundSorted.keySet().toArray();

            // Make a prediction
            if (reverseMapping.get(opponentLastMove).equals(lastRoundRes[0])) {
                // Opponent have chosen max, thus chose median
                this.lastMove = mapping.get(String.valueOf(currentRoundRes[1]));
            } else if (reverseMapping.get(opponentLastMove).equals(lastRoundRes[2])) {
                // Opponent have chosen min, thus chose max
                this.lastMove = mapping.get(String.valueOf(currentRoundRes[0]));
            } else {
                // Opponent have chosen median, thus chose max
                this.lastMove = mapping.get(String.valueOf(currentRoundRes[0]));
            }
        }
        return this.lastMove;
    }

    @Override
    public String getEmail() {
        return "a.kozlov@innopolis.ru";
    }

    @Override
    public void reset() {
        this.lastMove = 0;

    }

}
