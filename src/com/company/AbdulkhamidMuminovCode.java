package com.company;
import java.util.Random;

public class AbdulkhamidMuminovCode implements Player {
    Random r = new Random();
    // magic number -> empirically chosen probability that have shown outstanding results.
    private double p = 0.3;

    // f stands for fields 1,2,3
    private int[] f = new int[4];

    // x stands for coeficients on those fields
    private int[] x = new int[4];

    // `void process(xA, xB, xC)` method used for simplicity of implementation.
    // I sort the fields in a manner from 1 - 3 and map the coefficients in the same manner.
    private void process(int xA, int xB, int xC) {
        f[1] = 1;
        f[2] = 2;
        f[3] = 3;
        x[1] = xA;
        x[2] = xB;
        x[3] = xC;

        if (x[1] < x[2])
            swap(1, 2);
        if (x[1] < x[3])
            swap(1, 3);

        if (x[3] > x[2])
            swap(3, 2);
    }

    /* Explanation:
     * Current implementation of the agent wraps two strategies inside it.
     * Agent decides with certain probability which to use.
     * Hyper parameter `double p` has been set to 0.3 through empirical way.
     * It had been tested on other agents which closely remind the same behaviour.
     * His behaviour is based upon two approaches - greedy and weighted greedy.
     * Greedy is pretty obvious - agent chooses the best field he thinks it it.
     * When there are several fields with the same coeficients - it tosses a coin and goes as a fortune will tell him.
     * Weighted greedy is different from previous by that it weights up its decision before going to certain field.
     * For example we have following coefficients xA, xB, xC ->
     *      Agent weights up each of them and choose the where to go by following P's ->
     *              s = xA + xB + xC
     *              pA(xA/s)
     *              pB(xB/s)
     *              pC(xC/s)
     *      Then we introduce some random behavior so our agent would be more stochastic
     *
     * So why I have came up with following solution.
     * I have made several assumptions:
     *      Assumption #1:
     *          I assume that most of the players will be implemented in a greedy way, because it makes sense to use it,
     *          since it is quite optimal. But when coefficients have no big difference between each other, for ex:
     *                  1000 999 1
     *          my agent won't loose a lot if it will go to the second field, meanwhile when both greedy players
     *          are going to play -> they will gain 0 payoff
     *          so in long term purposes this weighted does matter.
     *      Assumption #2:
     *          I assume that another big amount of the players will try to implement greedy with some randomness in it.
     *          It doesn't really matter which approach they will use, but what matter is that my agent is able to
     *          switch strategies on fly, basing on some "magical" hyper parameter.
     *          In previous Assumption, I have said that payoff won't differ that much. But still, it is going to differ,
     *          in a way that greedy will be winning those random/weighted greedys.
     *          So having all those above, agent is going to switch between two approaches to pursue maximum payoff.
     *
     *
     */
    @Override
    public void reset() {

    }

    public String getEmail(){
        return "a.muminov@innopolis.ru";
    }
    // simple implementation of discrete greedy, which chooses the best f   ield.
    private int nonRandomGreedy() {
        if(x[1] == x[3])
            return r.nextInt(3) + 1;
        else if(x[1] == x[2]) {
            int rnd = r.nextInt(2);
            if(rnd == 0)
                return f[1];
            else
                return f[2];
        }
        else
            return f[1];
    }

    // implementation has been explained above
    private int weightedGreedy() {

        double[] p = new double[4];

        double s = x[1] + x[2] + x[3];
        for(int i=1; i<=3; i++)
            p[i] = x[i] / s;


        double rnd = r.nextDouble();

        if (rnd < p[1])
            return f[1];
        else if (rnd < p[1] + p[3])
            return f[3];
        else
            return f[2];
    }

    private void swap(int i, int j) {
        int t;

        t = f[i];
        f[i] = f[j];
        f[j] = t;

        t = x[i];
        x[i] = x[j];
        x[j] = t;
    }

    @Override
    public int move(int opponentLastMove, int xA, int xB, int xC) {
        process(xA, xB, xC);

        double rnd = r.nextDouble();
        // here some chaos happens where agent chooses between two strategies
        // rationale of this approach explained above
        if (rnd < p)
            return weightedGreedy();
        else
            return nonRandomGreedy();
    }
}

