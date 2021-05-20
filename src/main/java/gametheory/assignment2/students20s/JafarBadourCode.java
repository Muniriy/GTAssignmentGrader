package gametheory.assignment2.students20s;

import gametheory.assignment2.Player;

import java.util.Arrays;

public class JafarBadourCode implements Player { // Self Update Weighted Probability Agent
    static double[] V = {0.0, 0.0, 0.0};
    static double[] H = {1.0, 1.0, 1.0};
    int myLastMove, indexOfLastMove, lastMax;
    double alpha;

    public JafarBadourCode() {
        indexOfLastMove = -1;
        myLastMove = -1;
        lastMax = -1;
        this.alpha = 0.06;
        V[0] = 0.0;
        V[1] = (Math.sqrt(2) - 1) / 2.0;
        V[2] = Math.sqrt(2);
        H[0] = H[1] = H[2] = 1;
        normalize();
    }

    public static String to_string() {
        return "V = {" + V[0] + ", " + V[1] + ", " + V[2] + "},  H = {" + H[0] + ", " + H[1] + ", " + H[2] + "}";
    }

    private void normalize() {
        double R = 0;
        normie(R, V);

        R = 0;
        normie(R, H);
    }

    private void normie(double r, double[] h) {
        for (int i = 0; i < 3; i++) {
            //h[i] = Math.max(h[i], 0.01);
            r += Math.abs(h[i]);
        }
        r = Math.sqrt(r);
        for (int i = 0; i < 3; i++) {
            h[i] = h[i] / r;
        }
    }

    private void multi() {
        for (int i = 0; i < 3; i++) {
            V[i] = H[i] * V[i];
        }
        normalize();
    }

    @Override
    public void reset() {
        indexOfLastMove = -1;
        myLastMove = -1;
        lastMax = -1;
        this.alpha = 0.06;
        V[0] = 0.0;
        V[1] = (Math.sqrt(2) - 1) / 2.0;
        V[2] = Math.sqrt(2);
        H[0] = H[1] = H[2] = 1;
        normalize();
    }

    @Override
    public int move(int opponentLastMove, int xA, int xB, int xC) {
        Boolean maxie = true;
        if (indexOfLastMove == opponentLastMove) {
            maxie = false;
        }

        int[] ar = {xA, xB, xC};
        int[] original = {xA, xB, xC};
        int max_index = 1, ndmax_index = 2, low_index = 3;
        Arrays.sort(ar);
        //System.out.println(ar[0] + " " + ar[1] + " " + ar[2]);
        int maxi = ar[ar.length - 1], ndmax = ar[ar.length - 2];
        for (int i = 1; i <= 3; i++) {
            if (original[i - 1] == maxi) {
                max_index = i;
            } else if (original[i - 1] == ndmax) {
                ndmax_index = i;
            }
        }
        low_index = 3 - (max_index - 1) - (ndmax_index - 1) + 1;
        double ran_choice = Math.random();

        int[] sorted_indexes = {low_index, ndmax_index, max_index};
        int res = 1;
        //  System.out.println(ran_choice);
        for (int i = 0; i < 3; i++) {
            if (ran_choice < V[i]) {
                // System.out.println("DEBUG "+ran_choice + " " + V[i] + " " + i + " " + sorted_indexes[i]);
                res = sorted_indexes[i];
                indexOfLastMove = i + 1;
                break;
            }
            ran_choice -= V[i];
        }
        int t = myLastMove;
        myLastMove = res;

        lastMax = max_index;
        if (!maxie) {
            H[1] += H[2] / 2;
            H[0] += H[2] / 2;
            H[2] = H[2] / 2;
            V[0] = V[1] = V[2] = 1;

            normalize();
        } else {
            H[2] += 1 - H[2] / 2;
            V[0] = V[1] = V[2] = 1;
            normalize();
        }
        V[0] = 0;
        multi();
        return myLastMove;
    }

    @Override
    public String getEmail() {
        return "j.badour@innopolis.ru";
    }
}
