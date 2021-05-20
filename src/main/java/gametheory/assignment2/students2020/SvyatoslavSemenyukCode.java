package gametheory.assignment2.students2020;

import gametheory.assignment2.Player;

import java.util.ArrayList;
import java.util.Random;

public class SvyatoslavSemenyukCode implements Player {
    private ArrayList<Integer> MyMoves = new ArrayList<>();
    private ArrayList<Integer> FoeMoves = new ArrayList<>();
    private ArrayList<int[]> X = new ArrayList<>();
    private ArrayList<ArrayList<Integer>> BestX = new ArrayList<>();
    private Random rand = new Random();

    public void reset() {
        MyMoves.clear();
        FoeMoves.clear();
        X.clear();
        BestX.clear();
        }

    public SvyatoslavSemenyukCode()
        {
        }

    private int MovesToCheck = 3;
    private int DoNotBestThreshold = 3;

    public SvyatoslavSemenyukCode(int movesCount, int notBestThreshold)
        {
        if (movesCount >= 2) MovesToCheck = movesCount;
        if (notBestThreshold >= 1) DoNotBestThreshold = notBestThreshold;
        }

    private static ArrayList<Integer>[] SeparateFields(int[] x)
        {
        int l = x.length;
            ArrayList<Integer> best = new ArrayList<>(3);
        ArrayList<Integer> norm = new ArrayList<>(3);
        ArrayList<Integer> worst = new ArrayList<>(3);
            // if all values are equal, fill only best lists
            if (x[1] == x[2] && x[2] == x[3]) {
                for (int i = 1; i < l; i++) {
                    best.add(i);
                }
                return new ArrayList[]{worst, norm, best};
            }
            // get min and max
            int max = Integer.MIN_VALUE;
            int min = Integer.MAX_VALUE;
            for (int i = 1; i < l; i++) {
                if (x[i] > max) max = x[i];
                if (x[i] < min) min = x[i];
                // not else if since x[i] can satisfy both conditions
            }
            // add indexes to respected list
            for (int i = 1; i < l; i++) {
                if (x[i] == max) best.add(i);
            else if (x[i] == min) worst.add(i);
            else norm.add(i);
            }
        return new ArrayList[] {worst, norm, best};
        }

    private boolean CheckLastMoves()
        {
        int start = MyMoves.size() - 1;
        int end = start - MovesToCheck;
        // Not enough moves have been made yet - return false
        if (end < -1) return false;

        for (int i = start; i > end; i--)
            {
                int move = MyMoves.get(i);
            // Agent made best move - return false
            if (BestX.get(i).contains(move)) return false;
            }
        // None of the moves made were best
        return true;
        }

    private int MakeRandomMoveSaveItAndReturn(ArrayList<Integer> list)
        {
        int size = list.size();
        int idx;
        if (size == 1) idx = 0;
        else idx = rand.nextInt(size);
        int move = list.get(idx);
        MyMoves.add(move);
        return move;
        }

    public int move(int opponentLastMove, int xA, int xB, int xC)
        {
        if (opponentLastMove > 0) FoeMoves.add(opponentLastMove);
        // Determine if foe were selecting fields with best x last FoeMovesToCheck turns
        boolean do_best = CheckLastMoves();

            int[] x = {0, xA, xB, xC};
            ArrayList<Integer>[] lists = SeparateFields(x);
        // remember worst and best fields have the same values inside their groups
        // if norm is not empty then worst, norm and best have 1 value
        ArrayList<Integer> worst = lists[0], norm = lists[1], best = lists[2];

        X.add(x);
        BestX.add(best);

            boolean norm_is_empty = norm.isEmpty();

        // go to a field with the best x if one of conditions is true
        // 1. do_best is true
        // 2. worst is empty
        // 3. norm is empty and worst has fields with x < DoNotBestThreshold
        //
        // if worst is empty, norm is empty too
        // because there are always min and max
        // no min means it all fields have equal x
        // if norm is empty then a field to go should be selected from worst
        // no sense to go on field with x < DoNotBestThreshold
        if (do_best || worst.isEmpty() || (norm_is_empty && x[worst.get(0)] < DoNotBestThreshold))
            return MakeRandomMoveSaveItAndReturn(best);
        if (norm_is_empty) return MakeRandomMoveSaveItAndReturn(worst);
        // go to "middle" field if its x >= DoNotBestThreshold
        // next condition is valid since norm has only 1 value
        if (x[norm.get(0)] < DoNotBestThreshold) return MakeRandomMoveSaveItAndReturn(best);
        return MakeRandomMoveSaveItAndReturn(norm);
        }

    public String toString()
        {
        return String.format("Semenyuk agent (t=%d, v=%d)", MovesToCheck, DoNotBestThreshold);
        }

    public String getEmail() {return "s.semenyuk@innopolis.ru";}
    }
