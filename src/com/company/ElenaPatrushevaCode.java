package com.company;

import java.util.Random;
import java.util.stream.DoubleStream;

/*
Weighted probabilities random moose but the worst possible is not an option
Weights are proportional to possible payoffs
 */
public class ElenaPatrushevaCode implements Player{
    @Override
    public void reset() {
    }

    @Override
    public int move(int opponentLastMove, int xA, int xB, int xC) {
        int min = Math.min(xC, Math.min(xA, xB));
        //found position with min value and make it 0
        if (xA == min) {
            xA = 0;
        }
        else if(xB == min) {
            xB = 0;
        }
        else{
            xC = 0;
        }
        double[] arr = {possible_reward(xA), possible_reward(xB), possible_reward(xC)};
        return this.getChoice(arr);
    }
    @Override
    public String getEmail() {
        return "e.patrusheva@innopolis.ru";
    }
    private double possible_reward(int x){
        double exp_res = Math.exp((double)x);
        return 10 * exp_res / (1 + exp_res) - 5;
    }

    private int getChoice(double[] values){
        /*
        make a choice about move according to weight of each position
         */
        int[] choices = {1, 2, 3};
        Random rand = new Random();
        double[] probs = new double[3];
        double total = DoubleStream.of(values).sum();
        //if sum of weights is zero than choose randomly
        if (total == 0){
            return rand.nextInt(3) + 1;
        }
        for (int i =0; i<3; i++){
            //count probabilities from weights
            probs[i] = ((double)values[i]) / total;
        }
        //rand float from 0 to 1
        double generated = rand.nextDouble();
        //check areas of generated float
        if (generated < probs[0]){
            return 1;
        }
        if (generated >= probs[0] && generated < probs[1] + probs[0]){
            return 2;
        }
        return 3;
    }
}
