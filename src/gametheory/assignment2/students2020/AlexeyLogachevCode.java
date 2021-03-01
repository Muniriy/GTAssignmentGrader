package gametheory.assignment2.students2020;

import gametheory.assignment2.Player;

import java.util.Date;
import java.util.Random;

public class AlexeyLogachevCode implements Player {
    boolean first = true;

    int lastDecided = 0;
    int collisionCounter = 0;

    double lastCalculated = 0;
    double payoff = 0;

    public void reset() {
        first = true;
        payoff = 0;
    }

    public String getEmail() {
        return "a.logachev@innopolis.ru";
    }

    // Payoff function
    double calcPayoff(int X) {
        return (10 * Math.exp(X)) / (1 + Math.exp(X)) - 5;
    }

    public int move(int opponentLastMove, int xA, int xB, int xC) {
        // case for the first round - take the random field
        if (first) {
            Random rand = new Random();
            rand.setSeed(new Date().getTime());

            first = false;

            return rand.nextInt(3) + 1;
        }

        // calculate possible payoffs
        double payoff_a = calcPayoff(xA), payoff_b = calcPayoff(xB), payoff_c = calcPayoff(xC);
        int decided = 0;
        int second_decided = 0;

        // choose the first and second best payoff
        if (payoff_a > payoff_b) {
            if (payoff_a > payoff_c) {
                decided = 1;
                if (payoff_b > payoff_c) {
                    second_decided = 2;
                } else {
                    second_decided = 3;
                }
            } else {
                decided = 3;
                second_decided = 1;
            }
        } else if (payoff_b > payoff_c) {
            decided = 2;
            if (payoff_a > payoff_c) {
                second_decided = 1;
            } else {
                second_decided = 3;
            }
        } else {
            decided = 3;
            second_decided = 2;
        }

        // if last round was a collision
        if (opponentLastMove == lastDecided) {
            collisionCounter+=1;
            // if collided twice - opponent is trying to cooperate
            // return to greedy
            if (collisionCounter > 1) {
                lastDecided = decided;
                return decided;
            }
            // choose second best option assuming that the opponent will take the best again
            lastDecided = second_decided;
            return second_decided;
        }

        // just take the best available option
        collisionCounter = 0;
        payoff += lastCalculated;
        lastCalculated = Math.max(Math.max(payoff_a, payoff_b), payoff_c);

        lastDecided = decided;
        return decided;
    }
}
