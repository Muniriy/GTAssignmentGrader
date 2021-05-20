package gametheory.assignment2.students20s;

import gametheory.assignment2.Player;

public class AhmedElBatanonyCode implements Player {

    // Start by being random.
    // If the opponent cooperates (does not take a greedy move) -
    // - make a a greedy move.
    // If opponent is greedy, make a generous move
    // Otherwise, be random.
    // Basically do the opposite (if it exists), otherwise be random.

    // Store the field values from the last round
    int lastA, lastB, lastC;

    @Override
    public void reset() {
        // Reset the stored field values to zero
        lastA = lastB = lastC = 0;
    }

    // Return the maximum value among A, B and C
    int maximumValue(int xA, int xB, int xC) {
        return Math.max(Math.max(xA, xB), xC);
    }

    // Is there a unique maxium value among A, B and C?
    boolean uniqueMax(int xA, int xB, int xC) {
        int maxValue = maximumValue(xA, xB, xC);
        // How many fields have max value
        int maxCounter = 0;
        if (xA == maxValue) maxCounter++;
        if (xB == maxValue) maxCounter++;
        if (xC == maxValue) maxCounter++;
        // Only one unique max value
        if (maxCounter == 1) return true;
        // More than one field with max value
        return false;
    }

    // Check if a move was greedy
    boolean isGreedyMove(int move, int xA, int xB, int xC) {
        // A move can not be greedy if -
        // - there is more than one max value
        if (!uniqueMax(xA, xB, xC)) return false;
        // If code is here, there is one max value
        int maxValue = maximumValue(xA, xB, xC);
        // If A had maxValue and player moved to -
        // - A then it was a greedy move
        if (xA == maxValue && move == 1) return true;
        if (xB == maxValue && move == 2) return true;
        if (xC == maxValue && move == 3) return true;
        // Otherwise, it was not a greedy move
        return false;
    }

    // Was this move generous?
    boolean isGenerousMove(int move, int xA, int xB, int xC) {
        int maxValue = maximumValue(xA, xB, xC);
        if (xA == maxValue && move != 1) return true;
        if (xB == maxValue && move != 2) return true;
        if (xC == maxValue && move != 3) return true;
        return false;
    }

    // Return the index for the best field (greedy move)
    int bestIndex(int xA, int xB, int xC) {
        int maxValue = maximumValue(xA, xB, xC);
        // If C has maxValue, go to C
        if (xC == maxValue) return 3;
        // Repeat check with B
        if (xB == maxValue) return 2;
        // Otherwise, go to A
        return 1;
    }

    // Return the index for a non best field (non greedy move)
    int notBestIndex(int xA, int xB, int xC) {
        int maxValue = maximumValue(xA, xB, xC);
        // Go to B if not zero and is less than the max value
        if (xC != 0 && xC < maxValue) return 3;
        // Otherwise, check with B
        if (xB != 0 && xB < maxValue) return 2;
        // Otherwise, go to A
        return 1;
    }

    @Override
    public int move(int opponentLastMove, int xA, int xB, int xC) {

        // Check if opponent was greedy or generous
        boolean opponentGreedy = isGreedyMove(opponentLastMove, lastA, lastB, lastC);
        boolean opponentGenerous = isGenerousMove(opponentLastMove, lastA, lastB, lastC);

        // Store the field values for the next round
        lastA = xA;
        lastB = xB;
        lastC = xC;

        // If the opponent is being generous
        if (opponentGenerous) {
            // Make a greedy move
            return bestIndex(xA, xB, xC);
        }

        // If the opponent is being greedy
        if (opponentGreedy) {
            // Make a generous move
            return notBestIndex(xA, xB, xC);
        }

        // If reached here, make a random move -
        // - that does not lead to a zero field
        // Take a random position and check if not zero
        for (int i = 1; i <= 100; i += 1) {
            int rand = (int) (Math.random() * 3) + 1;
            if (rand == 1 && xA != 0) return 1;
            if (rand == 2 && xB != 0) return 2;
            if (rand == 3 && xC != 0) return 3;
        }
        // Should not (theoritically) reach here
        return 3;

    }

    @Override
    public String getEmail() {
        return "a.elbatanony@innopolis.ru";
    }
}
