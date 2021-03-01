package gametheory.assignment2.students2020;

import gametheory.assignment2.Player;

import java.util.ArrayList;

public class KusalKcCode implements Player {
    private ArrayList<Integer> myMovesHistory = new ArrayList<>();
    private ArrayList<Integer> opponentMovesHistory = new ArrayList<>();
    private int movesCounter = 0;

    @Override
    public void reset() {
        myMovesHistory = new ArrayList<>();
        opponentMovesHistory = new ArrayList<>();
        movesCounter = 0;
    }

    // gain function f(x)
    private static double gain(int fieldX) {
        double temp = Math.exp(fieldX);
        return (10 * temp)/(1 + temp);
    }

    @Override
    public int move(int opponentLastMove, int xA, int xB, int xC) {
        movesCounter++;

        if (opponentLastMove > 0) opponentMovesHistory.add(opponentLastMove);;


        // BEST STRATEGY: avoid the opponent
        // find how likely oppponent is to go to a field
        // calculate your expected payoff in a field accordingly

        // value represents probability that opponent is in field index+1
        double[] probabilityOpponentInField = {0, 0, 0};

        for (int i=0; i<opponentMovesHistory.size(); i++) {
            // accumulate frequency of opponent moves in each field
            probabilityOpponentInField[opponentMovesHistory.get(i)-1] += 1;
        }

        for (int i=0; i<3; i++) {
            // calculate probability of opponent moves in each field
            probabilityOpponentInField[i] = probabilityOpponentInField[i] / opponentMovesHistory.size();
        }

        double f_0 = gain(0);

        // calculate likely payoffs in
        double likelyPayoffInA = (probabilityOpponentInField[1] + probabilityOpponentInField[2]) * (gain(xA) - f_0);
        double likelyPayoffInB = (probabilityOpponentInField[0] + probabilityOpponentInField[2]) * (gain(xB) - f_0);
        double likelyPayoffInC = (probabilityOpponentInField[0] + probabilityOpponentInField[1]) * (gain(xC) - f_0);

        double maxPayoff = Math.max(likelyPayoffInA, Math.max(likelyPayoffInB, likelyPayoffInC));

        if (likelyPayoffInA == likelyPayoffInB && likelyPayoffInA == likelyPayoffInC) {
            // go to a field that is not opponent's last move
            int[] choices = new int[2];

            for (int i=1, j=0; i<=3; i++) {
                if (i != opponentLastMove && j<2) {
                    choices[j] = i;
                    j++;
                }
            }

            int choiceIndex = (Math.random() < 0.5) ? 0 : 1;
            return choices[choiceIndex];
        } else {

        }

        if (likelyPayoffInA == maxPayoff) return 1;
        if (likelyPayoffInB == maxPayoff) return 2;
        if (likelyPayoffInC == maxPayoff) return 3;

        return 1;
    }

    @Override
    public String getEmail() {
        return "k.kc@innopolis.ru";
    }
}
