package gametheory.assignment2.students20s;

import gametheory.assignment2.Player;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/*
 * NashPlayer class. It find the payoff matrix of the round. Finds the Nash equilibrium.
 * Chooses the nash equilibrium with the maximum possible score for itself. If there are several moves in
 * nash equilibrium with the maximum possible score, It takes one of the move randomly. In case if there is no moves
 * in nash equilibrium it takes a random move.
 * */
public class RuslanIsrafilovCode implements Player {
    public void reset() {
    }

    public int move(int opponentLastMove, int xA, int xB, int xC) {
        // Payoff matrix for both players, first index is for the strategy of the first player,
        // second index is for the strategy of the second player, third index is for payoff of the player
        double[][][] PlayersProfits = {{{0, 0}, {evaluateProfit(xA), evaluateProfit(xB)}, {evaluateProfit(xA), evaluateProfit(xC)}},
                {{evaluateProfit(xB), evaluateProfit(xA)}, {0, 0}, {evaluateProfit(xB), evaluateProfit(xC)}},
                {{evaluateProfit(xC), evaluateProfit(xA)}, {evaluateProfit(xC), evaluateProfit(xB)}, {0, 0}}};
        // Initialize arrays for the best responses of each player with respect to other player
        ArrayList<Integer>[] bestResponsesForTheFirstPlayer = new ArrayList[3];
        ArrayList<Integer>[] bestResponsesForTheSecondPlayer = new ArrayList[3];
        for (int i = 0; i < 3; ++i) {
            bestResponsesForTheFirstPlayer[i] = new ArrayList<>();
            bestResponsesForTheSecondPlayer[i] = new ArrayList<>();
        }
        // find best responses for the first player
        for (int j = 0; j < 3; ++j) {
            double max = 0;
            for (int i = 0; i < 3; ++i) {
                if (PlayersProfits[i][j][0] > max) max = PlayersProfits[i][j][0];
            }
            for (int i = 0; i < 3; ++i) {
                if (PlayersProfits[i][j][0] == max) {
                    bestResponsesForTheFirstPlayer[j].add(i);
                }
            }
        }

        // find best responses for the second player
        for (int i = 0; i < 3; ++i) {
            double max = 0;
            for (int j = 0; j < 3; ++j) {
                if (PlayersProfits[i][j][0] > max) max = PlayersProfits[i][j][1];
            }
            for (int j = 0; j < 3; ++j) {
                if (PlayersProfits[i][j][1] == max) {
                    bestResponsesForTheSecondPlayer[i].add(j);
                }
            }
        }
        // find Nash equilibrium
        ArrayList<int[]> nashEquilibrium = new ArrayList<>();
        for (int i = 0; i < 3; ++i) {
            for (int strategy : bestResponsesForTheSecondPlayer[i]) {
                if (bestResponsesForTheFirstPlayer[strategy].contains(i)) {
                    int[] equilibrium = new int[]{i, strategy};
                    nashEquilibrium.add(equilibrium);
                }
            }
        }
        // if there is no nash equilibrium -> return random move
        if (nashEquilibrium.size() == 0) {
            Random rand = new Random();
            return rand.nextInt(3) + 1;
        } // otherwise return move in nash equilibrium with the best payoff
        else {
            // Max value
            double max = 0;
            // Set of all strategies with the mash equilibrium
            Set<Integer> bestStrategies = new HashSet<Integer>();
            // Find max
            for (int[] i : nashEquilibrium) {
                if (PlayersProfits[i[0]][i[1]][0] > max) {
                    max = PlayersProfits[i[0]][i[1]][0];
                }
            }
            // Add the best strategies in the nash equilibrium to the set
            for (int[] i : nashEquilibrium) {
                if (PlayersProfits[i[0]][i[1]][0] == max) {
                    bestStrategies.add(i[0]);
                }
            }
            int n = bestStrategies.size();
            Integer arr[] = new Integer[n];
            System.arraycopy(bestStrategies.toArray(), 0, arr, 0, n);

            // Choose random move from the set of the best strategies in nash euqilibrium
            Random rand = new Random();
            return arr[rand.nextInt(arr.length)] + 1;
        }
    }

    public String getEmail() {
        return "r.israfilov@innopolis.university";
    }

    public double evaluateField(int X) {
        return (10 * Math.exp(X)) / (1 + Math.exp(X));
    }

    public double evaluateProfit(int X) {
        return this.evaluateField(X) - this.evaluateField(0);
    }
}