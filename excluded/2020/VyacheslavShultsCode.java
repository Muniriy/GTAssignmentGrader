package gametheory.assignment2.excluded2020;

import java.util.ArrayList;
import java.util.Random;
import java.lang.Math;


abstract class VyacheslavShultsAbstractPlayer implements Player {
    protected float score = 0;
    protected String name;

    public void resetScore() {
        this.score = 0;
    }

    public float getScore() {
        return score;
    }

    public void addScore(int x) {
        this.score += get_profit(x);
    }

    protected double get_profit(int x) {
        return 10 / (1 + Math.exp(-x)) - 5;
    }

    @Override
    public String toString() {
        return name + " has score " + score;
    }

    @Override
    public String getEmail(){
        return "v.shults@innopolis.ru";
    }
}

class VyacheslavShultsRandomPlayer extends VyacheslavShultsAbstractPlayer {

    private Random random = new Random();

    public VyacheslavShultsRandomPlayer(String name) {
        this.name = "Random_" + name;
    }

    public VyacheslavShultsRandomPlayer() {
        this.name = "Random";
    }

    @Override
    public void reset() {

    }

    @Override
    public int move(int opponentLastMove, int xA, int xB, int xC) {
        return random.nextInt(3) + 1;
    }
}

class VyacheslavShultsAlphaPlayer extends VyacheslavShultsAbstractPlayer {

    private Random random = new Random();

    public VyacheslavShultsAlphaPlayer(String name) {
        this.name = "Alpha_" + name;
    }

    public VyacheslavShultsAlphaPlayer() {
        this.name = "Alpha";
    }

    @Override
    public void reset() {

    }

    @Override
    public int move(int opponentLastMove, int xA, int xB, int xC) {
        if ((xA > xB) && (xA > xC)) {
            return 1;
        } else if ((xB > xA) && (xB > xC)) {
            return 2;
        } else if ((xC > xA) && (xC > xB)) {
            return 3;
        } else if ((xA == xB) && (xA > xC)) {
            return random.nextInt(2) + 1;
        } else if ((xA == xC) && (xA > xB)) {
            return random.nextInt(2) * 2 + 1;
        } else if ((xC == xB) && (xC > xA)) {
            return random.nextInt(2) + 2;
        } else {
            return random.nextInt(3) + 1;
        }
    }
}

class VyacheslavShultsSmartPlayer extends VyacheslavShultsAbstractPlayer {
    private Random random = new Random();
    private int[] opponents_priority_story = {1, 1, 1};
    int[] t = {0, 0, 0};
    private int last_move = 0;

    public VyacheslavShultsSmartPlayer(String name) {
        this.name = "Smart_" + name;
    }

    public VyacheslavShultsSmartPlayer() {
        this.name = "Smart";
    }

    @Override
    public void reset() {
        opponents_priority_story = new int[]{1, 1, 1};
        t = new int[]{0, 0, 0};
        last_move = 0;
    }

    public static double[] sotfmax(double[] inputs) {
        int n = inputs.length;
        double[] probabilities = new double[n];
        double sum = 0;
        for (int i = 0; i < n; i++) {
            probabilities[i] = Math.exp(inputs[i]);
            sum += probabilities[i];
        }
        for (int i = 0; i < n; i++) {
            probabilities[i] /= sum;
        }
        return probabilities;
    }


    protected double get_profit(int x) {
        double profit = 10 / (1 + Math.exp(-x)) - 5;
        if (profit == 0) return -10000;
        else return profit;
    }

    @Override
    public int move(int opponentLastMove, int xA, int xB, int xC) {
        if (opponentLastMove == 0) return random.nextInt(3) + 1;
        boolean is_not_first = last_move != 0;
        if (is_not_first) {
            opponents_priority_story[t[opponentLastMove - 1]] += 1;
        }

        if ((xA >= xB) && (xA >= xC)) {
            t[0] = 0;
            if (xB >= xC) {
                t[1] = 1;
                t[2] = 2;
            } else {
                t[1] = 2;
                t[2] = 1;
            }
        } else if ((xB >= xA) && (xB >= xC)) {
            t[1] = 0;
            if (xA >= xC) {
                t[0] = 1;
                t[2] = 2;
            } else {
                t[0] = 2;
                t[2] = 1;
            }
        } else {
            t[2] = 0;
            if (xA >= xB) {
                t[0] = 1;
                t[1] = 2;
            } else {
                t[0] = 2;
                t[1] = 1;
            }
        }
        double[] scores = {get_profit(xA), get_profit(xB), get_profit(xC)};
        scores = sotfmax(scores);
        double sum = 0;
        for (int i = 0; i < 3; i++) {
            scores[i] /= opponents_priority_story[t[i]];
            sum += scores[i];
        }
        for (int i = 0; i < 3; i++) scores[i] /= sum;
        double rand = Math.random();
        if (rand < scores[0]) {
            last_move = 1;
        } else if (rand < scores[0] + scores[1]) {
            last_move = 2;
        } else {
            last_move = 3;
        }
        return last_move;
    }
}

class Field {

    int x;

    public Field() {
        this.x = 1;
    }

    public void reset() {
        this.x = 1;
    }

    public void step(boolean is_there_moose) {
        if (is_there_moose) {
            this.x = Math.max(0, this.x - 1);
        } else {
            this.x += 1;
        }
    }

    public int getX() {
        return x;
    }

}


public class VyacheslavShultsCode {
    private ArrayList<Field> fields;
    private ArrayList<VyacheslavShultsAbstractPlayer> players;

    public VyacheslavShultsCode(ArrayList<VyacheslavShultsAbstractPlayer> players) {
        this.fields = new ArrayList<Field>();
        for (int i = 0; i < 3; i++) {
            this.fields.add(new Field());
        }
        setPlayers(players);
    }

    public void setPlayers(ArrayList<VyacheslavShultsAbstractPlayer> players) {
        this.players = players;
        fullResetPlayers();
        resetFields();
    }

    private void fullResetPlayers() {
        for (VyacheslavShultsAbstractPlayer player : this.players) {
            player.reset();
            player.resetScore();
        }
    }

    private void resetPlayers() {
        for (VyacheslavShultsAbstractPlayer player : this.players) {
            player.reset();
        }
    }

    protected double p(int x) {
        return 10 / (1 + Math.exp(-x)) - 5;
    }

    public double p2(int numTours) {
        int m = (int) Math.ceil(Math.log(2*numTours));
        int gamma = (numTours + 1) % 2 + 1;
        double mu = Math.ceil(Math.max(0, numTours - 2 * m + 1) / 2.0);
        double accumulation = p(m + 1) * mu;
        for (int k = gamma; k <= m; k++) {
            accumulation += p(k);
        }
        return accumulation / numTours;
    }

    private void resetFields() {
        for (int i = 0; i < 3; i++) {
            this.fields.get(i).reset();
        }
    }

    private void playTours(VyacheslavShultsAbstractPlayer player1, VyacheslavShultsAbstractPlayer player2, int numTours) {
        int player1_last_turn = 0;
        int player2_last_turn = 0;
        for (int i = 0; i < numTours; i++) {
            int player1_current_turn = player1.move(player2_last_turn, fields.get(0).getX(), fields.get(1).getX(), fields.get(2).getX());
            int player2_current_turn = player2.move(player1_last_turn, fields.get(0).getX(), fields.get(1).getX(), fields.get(2).getX());
            if (player1_current_turn != player2_current_turn) {
                player1.addScore(fields.get(player1_current_turn - 1).getX());
                player2.addScore(fields.get(player2_current_turn - 1).getX());
            }
            player1_last_turn = player1_current_turn;
            player2_last_turn = player2_current_turn;

            for (int j = 1; j <= fields.size(); j++) {
                if (player1_current_turn == j || player2_current_turn == j) {
                    fields.get(j - 1).step(true);
                } else {
                    fields.get(j - 1).step(false);
                }
            }
        }
    }

    public void startTournament(int numTours) {
        resetPlayers();
        resetFields();
        for (int i = 0; i < players.size(); i++) {
            for (int j = i + 1; j < players.size(); j++) {
                VyacheslavShultsAbstractPlayer player1 = players.get(i);
                VyacheslavShultsAbstractPlayer player2 = players.get(j);
                playTours(player1, player2, numTours);
                player1.reset();
                player2.reset();
                resetFields();
            }
        }
    }
}
