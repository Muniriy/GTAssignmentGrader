package gametheory.assignment2.students2021;

import gametheory.assignment2.Player;

import java.util.Random;

public class AlexandrGrichshenkoCode implements Player {

    int my_prev_move = 0; // remembering the previous move
    int current_round = 0; // keeping track of the number of rounds
    int my_field = 0; // identifier for the "home" field
    int opponent_field = 0; // identifier for the opponent's "home" field
    int neutral_filed = 0; // identifier for neutral field
    boolean start_coop = false; // whether or not to keep cooperate
    boolean always_greedy = false; // whether or not to move in a greedy fashion

    /**
     * This method is called to reset the agent before the match
     * with another player containing several rounds
     */

    @Override
    public void reset() {

        // resetting all variables

        my_prev_move = 0;
        current_round = 0;
        my_field = 0;
        opponent_field = 0;
        neutral_filed = 0;
        start_coop = false;
        always_greedy = false;
    }

    /**
     * @return cooperating move if opponent is also cooperating,
     * greedy move, otherwise
     */

    @Override
    public int move(int opponentLastMove, int xA, int xB, int xC) {

        // move randomly on the first round
        if (current_round == 0) {
            Random rand = new Random();
            my_prev_move = rand.nextInt(3) + 1;
            current_round++;
            return my_prev_move;
        }

        /*
         * if the coop hasn't started yet and the opponent hasn't betrayed you
         * move randomly if on the previous round both players ended up on the same
         * field. If they were on different once, start cooperation and take not of
         * home fields
         * */
        if (!start_coop && !always_greedy) {
            if (my_prev_move == opponentLastMove) {
                Random rand = new Random();
                my_prev_move = rand.nextInt(3) + 1;
                current_round++;
                return my_prev_move;
            } else {
                my_field = my_prev_move;
                opponent_field = opponentLastMove;
                for (int i = 1; i <= 3; i++) {
                    if (i != my_field && i != opponent_field) {
                        neutral_filed = i;
                    }
                }
                start_coop = true;
                my_prev_move = neutral_filed;
                return neutral_filed;
            }

        }
        if (start_coop) {

            // if the player has just been on the home field, go to neutral
            if (my_prev_move == my_field) {
                my_prev_move = neutral_filed;
                return neutral_filed;
            } else {
                // if both players were on neutral and home terrain x is 6, move there
                if (my_prev_move == opponentLastMove) {
                    if (my_field == 1 && xA == 6) {
                        my_prev_move = my_field;
                        return my_field;
                    }
                    if (my_field == 2 && xB == 6) {
                        my_prev_move = my_field;
                        return my_field;
                    }
                    if (my_field == 3 && xC == 6) {
                        my_prev_move = my_field;
                        return my_field;
                    }
                    return my_prev_move;
                } else {
                    // if the above condition is not met, the opponent is not cooperating, go greedy
                    start_coop = false;
                    always_greedy = true;
                    return moveGreedy(opponentLastMove, xA, xB, xC);
                }
            }
        }
        return moveGreedy(opponentLastMove, xA, xB, xC);
    }

    /**
     * @return the number of the field with the highest x
     */

    private int moveGreedy(int opponentLastMove, int xA, int xB, int xC) {
        int best_x = Math.max(xA, Math.max(xB, xC));
        if (xA == best_x && xB == best_x && xC == best_x) {
            Random rand = new Random();
            return rand.nextInt(3) + 1;
        }
        if (xA == best_x && xB == best_x) {
            Random rand = new Random();
            return rand.nextInt(2) + 1;
        }
        if (xA == best_x && xC == best_x) {
            Random rand = new Random();
            int n = rand.nextInt(2);
            if (n == 0) {
                return 1;
            } else {
                return 3;
            }
        }
        if (xB == best_x && xC == best_x) {
            Random rand = new Random();
            return rand.nextInt(2) + 2;
        }
        if (xA == best_x) {
            return 1;
        }
        if (xB == best_x) {
            return 2;
        }
        return 3;
    }

    /**
     * This method returns my IU email
     *
     * @return my email
     */

    @Override
    public String getEmail() {
        return "a.grishchenko@innopolis.university";
    }
}
