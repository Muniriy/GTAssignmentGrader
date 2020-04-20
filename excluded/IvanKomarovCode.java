package com.company;
import java.lang.Math;

//interface Player {
//    void reset();
//    int move(int opponentLastMove, int xA, int xB, int xC);
//    String getEmail();
//}

// Mixin that erases magic numbers in some places
class FieldNames {
    static int A = 1;
    static int B = 2;
    static int C = 3;
}

// Base class that implements knows email
abstract class BasePlayer extends FieldNames implements Player {
    public void reset() { }

    public abstract int move(int opponentLastMove, int xA, int xB, int xC);

    public String getEmail() {
        return "i.komarov@innopolis.ru";
    }
}

// Player that only selects A
class IdleMoose extends BasePlayer {
    public int move(int opponentLastMove, int xA, int xB, int xC) {
        return A;
    }
}

// Moose that traverses each field in infinite loop
class RotatingMoose extends BasePlayer {
    int nextMove = A;

    public void reset() {
        nextMove = A;
    }

    public int move(int opponentLastMove, int xA, int xB, int xC) {
        int m = nextMove;
        nextMove = nextMove % 3 + 1;
        return m;
    }
}

// Player that chooses next step randomly
class RandomMoose extends BasePlayer {
    public int move(int opponentLastMove, int xA, int xB, int xC) {
        double r = Math.random();
        if (r < 1.0 / 3.0) return A;
        if (r < 2.0 / 3.0) return B;
        return C;
    }
}

// Player that selects most rich field
class GreedyMoose extends BasePlayer {
    public int move(int opponentLastMove, int xA, int xB, int xC) {
        if (xA > xB) {
            if (xA > xC) return A;
            if (xB > xC) return B;
            return C;
        }
        if (xB > xC) return B;
        if (xA > xC) return A;
        return C;
    }
}

// Main entrypoint and test runner
public class IvanKomarovCode extends FieldNames {

    // State variables for playing tournament in playDuel
    int xA;
    int xB;
    int xC;
    int lastMove1;
    int lastMove2;

    public static void main(String args[]) {
        IvanKomarovCode code = new IvanKomarovCode();

        // create Players
        Player idle = new IdleMoose();
        Player rotating = new RotatingMoose();
        Player random = new RandomMoose();
        Player greedy = new GreedyMoose();

        // first two games
        Player p1 = code.getWinner(idle, rotating);
        Player p2 = code.getWinner(random, greedy);

        // final game
        Player winner = code.getWinner(p1, p2);

        // print winner
        System.out.format("Winner is %s", winner.getClass().getName());
    }

    // Wrapper for playDuel to resolve tie condition
    Player getWinner(Player p1, Player p2) {
        Player winner = null;
        while (winner == null) winner = playDuel(p1, p2, 100);
        return winner;
    }

    // Forumla of score points
    static double f(int x) {
        return 10 * Math.exp(x) / (1 + Math.exp(x));
    }

    // Score at given field at current turn
    double scoreAt(int field) {
        switch (field) {
            case 1:
                return f(xA) - f(0);
            case 2:
                return f(xB) - f(0);
            case 3:
                return f(xC) - f(0);
        }
        return 0;
    }

    // Decrease field capacity for givent turn
    // Subtracts 2 because in playDuel we always add one to each field
    // by default
    void decreaseAt(int field) {
        switch (field) {
            case 1:
                xA -= 2;
                if (xA < 0) xA = 0;
                break;
            case 2:
                xB -= 2;
                if (xB < 0) xB = 0;
                break;
            case 3:
                xC -= 2;
                if (xC < 0) xC = 0;
                break;
        }
    }

    // Update field capacities at the end of play iteration
    void updateFields() {
        // Increace capacity by default
        xA += 1;
        xB += 1;
        xC += 1;

        decreaseAt(lastMove1);

        // Decrease second field if players have chosen different fields
        if (lastMove1 != lastMove2) {
            decreaseAt(lastMove2);
        }
    }

    // Main duel function
    Player playDuel(Player p1, Player p2, int turns) {
        System.out.format("Duel %s - %s | %d turns\n", p1.getClass().getName(), p2.getClass().getName(), turns);

        double score1 = 0;
        double score2 = 0;

        // Setup play state
        lastMove1 = 0;
        lastMove2 = 0;
        xA = 1;
        xB = 1;
        xC = 1;

        p1.reset();
        p2.reset();
        for (int i = 0; i < turns; i++) {
            lastMove1 = p1.move(lastMove2, xA, xB, xC);
            lastMove2 = p2.move(lastMove1, xA, xB, xC);

            // Add scores if possible
            if (lastMove1 != lastMove2) {
                score1 += scoreAt(lastMove1);
                score2 += scoreAt(lastMove2);
            }

            // update field capacities
            updateFields();
        }
        System.out.format("Score %f - %f\n", score1, score2);

        // return winner or null if tie
        if (score1 > score2) return p1;
        if (score2 > score1) return p2;
        return null;
    }
}

