package gametheory.assignment2.students20s;

import gametheory.assignment2.Player;

import java.util.*;

/**
 * My agent I've chosen for the tournament, it's description can be found in report, here I will only explain the code
 */
public class VladislavSavchukCode implements Player {
    final int max_same_moves = 10; // Amount of moves to store
    final int passive_threshold = 3; // Threshold to transform into Take Max
    final int take_max_threshold = 3; // Threshold to transform into taking second Max
    Set<Integer> last_best; // For storing best moves of the previous run
    boolean random;  // If the player is in the random state
    int best_count, not_best_count; // Count of best and non_best moves
    ArrayList<Integer> last_moves; // List for storing n last moves of this player
    ArrayList<Integer> op_last_moves; // List for storing n last moves of the opponent

    /**
     * Init clean stat of the agent
     */
    public VladislavSavchukCode() {
        last_best = new HashSet<>();
        best_count = 0;
        last_moves = new ArrayList<>();
        op_last_moves = new ArrayList<>();
        random = false;
        not_best_count = 0;
    }

    /**
     * Clean agent's state
     */
    @Override
    public void reset() {
        last_best = new HashSet<>();
        best_count = 0;
        last_moves = new ArrayList<>();
        op_last_moves = new ArrayList<>();
        random = false;
        not_best_count = 0;
    }

    /**
     * Helper function that adds new move to the list of last opponents moves and return a boolean if
     * this agents moves and opponents moves were the same for the last max_same_moves times
     *
     * @param move last move of the opponent
     * @return true if last_moves and op_last_moves are the same, and false otherwise
     */
    private boolean add_op_move(int move) {

        // If op_last_moves is "full" remove first value
        if (op_last_moves.size() == max_same_moves) {
            op_last_moves.remove(0);
        }
        // Add new move to the end of the list
        op_last_moves.add(move);

        // If we have n values in the list - check if opponents move are the same with this agent
        if (last_moves.size() == max_same_moves) {
            boolean to_return = true;
            for (int i = 0; i < last_moves.size(); i++) {
                if (!last_moves.get(i).equals(op_last_moves.get(i))) {
                    to_return = false;
                    break;
                }
            }
            return to_return;
        }
        return false;
    }

    @Override
    public int move(int opponentLastMove, int xA, int xB, int xC) {

        // If this isn't first round and we are not in the random state - add new opponent's move
        if (!random && opponentLastMove != 0) {
            random = add_op_move(opponentLastMove);
        }

        if (random) { // If the player is turned into a random we just take a random field, without tracking new data
            Random rand = new Random();
            return 1 + rand.nextInt(3);
        }

        int to_return;

        // Take moves and sort them in decreasing order
        List<Move> moves = Arrays.asList(new Move(xA, 1), new Move(xB, 2), new Move(xC, 3));
        moves.sort(Collections.reverseOrder(Comparator.comparing(Move::getX)));

        // Check if opponent took the best option previously
        boolean took_best = last_best.contains(opponentLastMove);

        // Update corresponding values based on opponent's decision
        if (!took_best) {
            best_count = 0; // We reset counter for best
            not_best_count++;
        } else {
            best_count++;
        }

        // If best_count > take_max_threhold - tactic is aggressive
        // If not_best_count is high tactic doesn't seem to be aggressive
        if ((best_count > take_max_threshold || took_best) && !(not_best_count > passive_threshold)) {
            // If the tactic seem aggressive - take second to best option if it is non-zero one (take best otherwise)
            if (moves.get(1).x != 0) {
                to_return = moves.get(1).label;
            } else {
                to_return = moves.get(0).label;
            }
        } else {
            // if the tactic doesn't seem aggressive - take best option
            to_return = moves.get(0).label;
        }

        // Update last_best with current best options of x's
        if (opponentLastMove != 0) {
            last_best.clear();
            last_best.add(moves.get(0).label); // First one is best (sorted decreasingly)
            if (moves.get(1).x == moves.get(0).x) { // Check if second one is the same as first
                last_best.add(moves.get(1).label);
            }
            if (moves.get(2).x == moves.get(0).x) { // Do the same for the third option
                last_best.add(moves.get(2).label);
            }
        }

        //If last_moves is full remove first element
        if (last_moves.size() == max_same_moves) {
            last_moves.remove(0);
        }
        // Add new move to the list
        last_moves.add(to_return);
        return to_return;
    }

    @Override
    public String getEmail() {
        return "v.savchuk@innopolis.ru";
    }

    /**
     * Helper class to store x of moves and their labels, used for easier sorting
     */
    static class Move {
        int x, label;

        Move(int x, int label) {
            this.x = x;
            this.label = label;
        }

        int getX() {
            return this.x;
        }
    }
}
