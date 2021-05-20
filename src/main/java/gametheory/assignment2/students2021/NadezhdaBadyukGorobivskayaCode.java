package gametheory.assignment2.students2021;

import gametheory.assignment2.Player;

import java.util.ArrayList;
import java.util.Random;

public class NadezhdaBadyukGorobivskayaCode implements Player {

    private ArrayList<String> sliding_window;
    private int opponent_number_of_maxes;
    private int opponent_number_of_mids;
    private int opponent_number_of_mins;
    private int number_of_rounds_played;
    private int sliding_window_size;
    private int max_prob;
    private int min_prob;
    private int mid_prob;
    private Random random;

    static double f(int x) {
        return 10 * Math.exp(x) / (1 + Math.exp(x));
    }

    @Override
    public void reset() {

        sliding_window = new ArrayList<>();
        sliding_window_size = 15;

        opponent_number_of_maxes = 0;
        opponent_number_of_mids = 0;
        opponent_number_of_mins = 0;
        number_of_rounds_played = 0;

        random = new Random();
        max_prob = random.nextInt(40) + 20;
        min_prob = random.nextInt(10) + 10;
        mid_prob = 100 - max_prob - min_prob;

    }

    @Override
    public int move(int opponentLastMove, int xA, int xB, int xC) {

        if (opponentLastMove == 0) {
            return random.nextInt(3) + 1;
        }

        int prev_X[] = new int[3];
        prev_X[0] = xA;
        prev_X[1] = xB;
        prev_X[2] = xC;
        prev_X[opponentLastMove - 1]++;
        int[] prev_indices = max_mid_min_indices(prev_X);

        if (opponentLastMove == prev_indices[0]) {
            sliding_window.add("max");
            opponent_number_of_maxes++;
        }
        if (opponentLastMove == prev_indices[1]) {
            sliding_window.add("mid");
            opponent_number_of_mids++;
        }
        if (opponentLastMove == prev_indices[2]) {
            sliding_window.add("min");
            opponent_number_of_mins++;
        }

        number_of_rounds_played++;

        int X[] = new int[3];
        X[0] = xA;
        X[1] = xB;
        X[2] = xC;
        int[] indices = max_mid_min_indices(X);

        int max = X[indices[0] - 1];
        int mid = X[indices[1] - 1];
        int min = X[indices[2] - 1];

        if (number_of_rounds_played < sliding_window_size) {
            int generated_number = random.nextInt(100);
            if (generated_number < max_prob) {
                return indices[0];
            } else {
                if (generated_number >= max_prob && generated_number < max_prob + mid_prob) {
                    return indices[1];
                } else {
                    return indices[2];
                }
            }
        } else {
            if (sliding_window.get(0) == "max") {
                opponent_number_of_maxes--;
            }
            if (sliding_window.get(0) == "mid") {
                opponent_number_of_mids--;
            }
            if (sliding_window.get(0) == "min") {
                opponent_number_of_mins--;
            }
            sliding_window.remove(0);


            double opponent_max_prob = (opponent_number_of_maxes * 1.) / sliding_window.size();
            double opponent_mid_prob = (opponent_number_of_mids * 1.) / sliding_window.size();
            double opponent_min_prob = (opponent_number_of_mins * 1.) / sliding_window.size();

            double expected_payoff_of_going_to_max = (1 - opponent_max_prob) * (f(max) - f(0));
            double expected_payoff_of_going_to_mid = (1 - opponent_mid_prob) * (f(mid) - f(0));
            double expected_payoff_of_going_to_min = (1 - opponent_min_prob) * (f(min) - f(0));

            if (expected_payoff_of_going_to_max > expected_payoff_of_going_to_mid - 0.000001 && expected_payoff_of_going_to_max > expected_payoff_of_going_to_min - 0.000001) {
                return indices[0];
            } else {
                if (expected_payoff_of_going_to_mid > expected_payoff_of_going_to_max - 0.000001 && expected_payoff_of_going_to_mid >= expected_payoff_of_going_to_min - 0.000001) {
                    return indices[1];
                } else {
                    return indices[2];
                }
            }
        }
    }

    @Override
    public String getEmail() {
        return "n.badyuk-gorobivskaya@innopolis.university";
    }

    private int[] max_mid_min_indices(int[] X) {

        int indices[] = new int[3];

        int max = X[0];
        int max_ind = 1;
        for (int i = 1; i < 3; i++) {
            if (X[i] > max) {
                max = X[i];
                max_ind = i + 1;
            }
        }

        int min = X[0];
        int min_ind = 1;
        for (int i = 1; i < 3; i++) {
            if (X[i] <= min) {
                min = X[i];
                min_ind = i + 1;
            }
        }

        int mid = 0;
        int mid_ind = 0;
        for (int i = 1; i < 4; i++) {
            if (i != max_ind && i != min_ind) {
                mid_ind = i;
                mid = X[i - 1];
            }
        }

        indices[0] = max_ind;
        indices[1] = mid_ind;
        indices[2] = min_ind;

        return indices;
    }
}
