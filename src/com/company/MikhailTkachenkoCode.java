package com.company;

import java.util.*;
import java.util.stream.Collectors;

public class MikhailTkachenkoCode implements Player {

    Map<Double, ArrayList<Integer>> invertedScores = new TreeMap<>();       // List of scores. Stored as a dictionary with score as key and options as values
    Map<Integer, Long> opponentChoiceCounts = new HashMap<>();              // Choices of opponent statistics

    @Override
    public void reset() {
        invertedScores = new TreeMap<>();
        opponentChoiceCounts = new TreeMap<>();
    }

    @Override
    public int move(int opponentLastMove, int xA, int xB, int xC) {

        // Initially player decides to move randomly
        int playerMove = 1 + (int) Math.round((Math.random() * 2));

        // Finding order of selected option last move
        if (opponentLastMove > 0) {
            int profitOpponentLastMove = 1;         // choice of opponent last move was ith best
            for (Double score : invertedScores.keySet()) {
                if (!invertedScores.get(score).contains(opponentLastMove)) {
                    profitOpponentLastMove += 1;
                } else {
                    if (opponentChoiceCounts.containsKey(profitOpponentLastMove)) {
                        opponentChoiceCounts.put(profitOpponentLastMove, opponentChoiceCounts.get(profitOpponentLastMove) + 1);
                    } else {
                        opponentChoiceCounts.put(profitOpponentLastMove, new Long(1));
                    }
                    break;
                }
            }

            // Sorting counts by value to get most probable option
            opponentChoiceCounts =
                    opponentChoiceCounts.entrySet().stream()
                            .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                            .collect(Collectors.toMap(
                                    Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
        }

        // Flushing scores from last move
        invertedScores = new HashMap<>();
        // Filling current choices
        int[] scores = {xA, xB, xC};
        for (int i = 0; i < scores.length; i++) {
            double currentScore = calculateScore(scores[i]);
            if (invertedScores.containsKey(currentScore)) {
                ArrayList<Integer> positionsList = invertedScores.get(currentScore);
                positionsList.add(i + 1);
            } else {
                ArrayList<Integer> positionsList = new ArrayList<>();
                positionsList.add(i + 1);
                invertedScores.put(currentScore, positionsList);
            }
        }


        // Sorting current move scores
        invertedScores = new TreeMap<>(invertedScores).descendingMap();

        // Creating a copy of invertedScores
        // because I will delete most probable
        // opponent's option from here later
        Map<Double, ArrayList<Integer>> invertedScoresCopy = new TreeMap<>(invertedScores).descendingMap();

        // If we have statistics from opponent, we remove its potential move to not intersect with it
        if (opponentChoiceCounts.size() > 0) {

            // Finding potential opponent move
            // It means that opponent will pick nth best option, where n is potentialOpponentMove
            int potentialOpponentMove = opponentChoiceCounts.keySet().iterator().next();

            // Getting list of keys
            final List<Double> keys = new ArrayList<>(invertedScoresCopy.keySet());

            // If index is not overflowing
            if (potentialOpponentMove < keys.size()) {
                final Double deleteKey = keys.get(potentialOpponentMove);
                ArrayList<Integer> scoreFields = invertedScoresCopy.get(deleteKey);

                // Delete option only in case it's only one for such score.
                // Otherwise, keep it as it is and let random decide
                if (scoreFields.size() == 1) {
                    invertedScoresCopy.remove(deleteKey);
                }
            }
        }


        // Choosing option to take

        // If there are scores left
        if (invertedScoresCopy.size() > 0) {

            // Pick best position (assuming that opponent's move is removed from copy)
            ArrayList<Integer> candidatePositions = invertedScoresCopy.entrySet().iterator().next().getValue();
            Random random = new Random();

            // Pick random option from a set of options for best score
            playerMove = candidatePositions.get(random.nextInt(candidatePositions.size()));
        }

        return playerMove;
    }


    // Calculate score
    double calculateScore(int x) {
        return (10 * Math.exp(x)) / (1 + Math.exp(x)) - 5;
    }

    @Override
    public String getEmail() {
        return "m.tkachenko@innopolis.ru";
    }
}

