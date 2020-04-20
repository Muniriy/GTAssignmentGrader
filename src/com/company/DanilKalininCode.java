package com.company;


class DanilKalininCode implements Player {

    public void reset() {
    }

    public String getEmail(){
        return "d.kalinin@innopolis.ru";
    }

    public int move(int opponentLastMove, int xA, int xB, int xC) {
        // Transform integers to real reward function (sigmoid)
        double a = this.reward(xA);
        double b = this.reward(xB);
        double c = this.reward(xC);
        // Get weighted choice of move based on propotion of reward function
        return this.random_choice(a, b, c);
    }

    // Function to calculate sigmoid function (reward function)
    private double reward(int x){
        double e_x = Math.exp(x);
        double reward = 10 * e_x / (1 + e_x);
        return reward;
    }

    // This function generates wighted random int 1, 2 or 3
    private int random_choice(double a, double b, double c){
        double sum = a + b + c;
        double rand_gen = Math.random();
        rand_gen = rand_gen * sum;
        if (rand_gen < a)
            return 1;
        if (rand_gen < b)
            return 2;
        return 3;
    }

}
