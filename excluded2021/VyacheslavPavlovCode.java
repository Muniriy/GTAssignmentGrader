package gametheory.assignment2.students2021;

import gametheory.assignment2.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class VyacheslavPavlovCode implements Player {

    static class Pair implements Comparable{
        int x;
        int n;

        Pair(int in_x, int in_n){
            x = in_x;
            n = in_n;
        }

        @Override
        public int compareTo(Object o) {
            return x - ((Player11.Pair)o).x;
        }
    }

    Random r;
    private double edge_1 = 0.62;
    private double edge_2 = 0.10;
    private final double step = 0.01;
    private ArrayList<Integer> prev_situation;

    VyacheslavPavlovCode(){
        r = new Random();
        r.setSeed(System.currentTimeMillis());
        prev_situation = new ArrayList<>();
    }

    @Override
    public void reset() {
        r.setSeed(System.currentTimeMillis());
        edge_1 = 0.62;
        edge_2 = 0.10;
        prev_situation = new ArrayList<>();
    }

    @Override
    public int move(int opponentLastMove, int xA, int xB, int xC) {
        double noise = r.nextDouble() / 30;
        // Change "edges"
        if (opponentLastMove > 0) {
            if (prev_situation.indexOf(opponentLastMove) == 2) {
                edge_1 += (step + noise);
                edge_1 = Math.min(0.7, edge_1);
                edge_2 += (step + noise) / 3;
                edge_2 = Math.min(0.15, edge_2);
            }
            if (prev_situation.indexOf(opponentLastMove) == 1) {
                edge_1 -= (step + noise);
                edge_2 += (step + noise) / 3;
                edge_2 = Math.min(edge_2, 0.5);
                edge_1 = Math.max(edge_1, 0.15);
            }
            if (prev_situation.indexOf(opponentLastMove) == 0) {
                edge_2 -= (step + noise);
                edge_2 = Math.max(0.07, edge_2);
                edge_1 -= (step + noise) / 2;
                edge_1 = Math.max(edge_1, 0.5);
            }
        } else{
            prev_situation.add(0);
            prev_situation.add(0);
            prev_situation.add(0);
        }

        // Sort cells
        ArrayList<Player11.Pair> arr = new ArrayList<>();
        arr.add(new Player11.Pair(xA, 1));
        arr.add(new Player11.Pair(xB, 2));
        arr.add(new Player11.Pair(xC, 3));
        Collections.sort(arr);

        // Remember situation
        prev_situation.set(0, arr.get(0).n);
        prev_situation.set(1, arr.get(1).n);
        prev_situation.set(2, arr.get(2).n);

        // Decide
        if (r.nextDouble() > edge_2) {
            if (r.nextDouble() > edge_1) {
                return arr.get(1).n;
            }
            return arr.get(2).n;
        } else {
            return arr.get(0).n;
        }
    }

    @Override
    public String getEmail() {
        return "v.pavlov@innopolis.university";
    }
}