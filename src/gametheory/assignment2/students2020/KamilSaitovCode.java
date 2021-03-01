package gametheory.assignment2.students2020;

import gametheory.assignment2.Player;

import java.util.Random;

/**
 * My implementation of the Player interface, presenting a smart statistical agent
 * which can derive the greediness of the opponent player and act accordingly.
 */
public class KamilSaitovCode implements Player {

    int turnCount; // the current turn in the game
    int opponentGreedyChoiceCount; // increments if the opponent chose a greedy move last turn
    int opponentNonGreedyChoiceCount; // increments if the opponent did not choose a greedy move last turn
    int myLastChoice; // the choice this player made last turn
    int prevOptimalChoice; // the field which had was the optimal (greedy) last turn


    public KamilSaitovCode() {
        this.turnCount = 0;
        this.opponentGreedyChoiceCount = 0;
        this.opponentNonGreedyChoiceCount = 0;
        this.myLastChoice = 0;
        this.prevOptimalChoice = 0;
    }

    @Override
    public void reset() {
        this.turnCount = 0;
        this.opponentGreedyChoiceCount = 0;
        this.opponentNonGreedyChoiceCount = 0;
        this.myLastChoice = 0;
        this.prevOptimalChoice = 0;
    }

    /**
     * This method chooses the suboptimal field, meaning that is returns the field which
     * has the second best amount of vegetation.
     *
     * @param xA, xB, xC - field vegetation values for this turn
     * @return the second most "optimal" field to choose.
     */
    int getSubOptimalFieldNumber(int xA, int xB, int xC) {
        int max = getGreedyFieldNumber(xA, xB, xC);
        switch (max) {
            case 1:
                return (Math.max(xB, xC) == xB) ? 2 : 3;
            case 2:
                return Math.max(xA, xC) == xA ? 1 : 3;
            default:
                return Math.max(xA, xB) == xA ? 1 : 2; // == case 3
        }
    }

    /**
     * This method returns what the greedy move would be for this turn.
     *
     * @param xA, xB, xC - field vegetation values for this turn
     * @return The most "optimal" field to choose.
     */
    int getGreedyFieldNumber(int xA, int xB, int xC) {
        int max = Math.max(xA, Math.max(xB, xC));
        if (max == xA) {
            return 1;
        } else if (max == xB) {
            return 2;
        } else {
            return 3;
        }
    }

    /**
     * This is the method where most of the logic of my player lies.
     * Short description of the agent:
     * 1st move: chooses random field
     * 2nd move: chooses the greedy move (the field where the amount of vegetation is the highest)
     * 3+ move: if the opponent played greedily in more than 50% of his moves, then choose "sub-greedy"
     * option. If opponent played greedily in less than 50% of the moves, then choose a greedy move.
     */
    @Override
    public int move(int opponentLastMove, int xA, int xB, int xC) {
        if (this.turnCount == 0) {
            // return random on first move
            this.turnCount++;
            return new Random().nextInt(3) + 1;
        } else if (this.turnCount == 1) {
            // return the greedy move on the second move
            this.turnCount++;
            int choice = getGreedyFieldNumber(xA, xB, xC);
            this.myLastChoice = choice;
            this.prevOptimalChoice = getGreedyFieldNumber(xA, xB, xC);
            return choice;
        } else {
            // on 3+ turn:
            this.turnCount++;
            if (this.prevOptimalChoice == opponentLastMove) {
                // we made a greedy choice last turn. If we fight, that means that opponent
                // made a greedy choice too.
                this.opponentGreedyChoiceCount++;
            } else {
                this.opponentNonGreedyChoiceCount++;
            }
            int greedy = getGreedyFieldNumber(xA, xB, xC);

            // the below algorithm makes a greedy move "smarter".
            // If there are multiple optimal choices (i.e. 2 0 2), always choose the latest one.
            // (I predict that greedy players out there will always take only the first greedy move.)
            if (greedy == 1) {
                if (xA == xC) {
                    return 3;
                } else if (xA == xB) {
                    return 2;
                }
            }
            if (greedy == 2) {
                if (xB == xC) {
                    return 3;
                }
            }

            // If we are here, that means that there is only one greedy move on the fields (i.e. 1 2 3).
            // We check if opponent's greedy choices occurred more than non-greedy.
            if (this.opponentGreedyChoiceCount >= this.opponentNonGreedyChoiceCount) {

                // now there is one more optimization. If there is only one non-zero field (i.e. 0 2 0)
                // then that we cannot get any points in any move we make this turn.
                // (because we are playing against a greedy opponent who will always choose 2 in the mentioned case)
                // In such case we will fight with the greedy opponent. That is the best choice because
                //  1) opponent will not get points either
                //  2) The vegetation will grow on two fields instead of one in this turn.
                int sumNonOptimal = 0;
                switch (greedy) {
                    case 1:
                        sumNonOptimal = xB + xC;
                        break;
                    case 2:
                        sumNonOptimal = xA + xC;
                        break;
                    default:
                        sumNonOptimal = xA + xB;
                        break;
                }
                if (sumNonOptimal == 0) {
                    return greedy;
                } else {
                    // if there are fields that are non-zero and are not greedy, we choose the sub-greedy move.
                    return getSubOptimalFieldNumber(xA, xB, xC);
                }
            } else {
                // if most of the opponent's moves are non-greedy, then we become greedy
                // (that, obviously, will give us the most points.)
                return getGreedyFieldNumber(xA, xB, xC);
            }

        }
    }

    @Override
    public String getEmail() {
        return "k.saitov@innopoli.ru";
    }
}
