package gametheory.assignment2.students2021;

import gametheory.assignment2.Player;

import java.util.ArrayList;
import java.util.Random;

public class AsanaliFazylzhanCode implements Player {

    ArrayList<Integer> myMoves;
    ArrayList<Integer> opponentsMoves;
    int mode;

    /**
     * constructor for mu player method. It initializes all parameters.
     */
    public AsanaliFazylzhanCode() {
        this.myMoves = new ArrayList();
        this.opponentsMoves = new ArrayList();
        this.mode = 1;
    }

    @Override
    /**
     * resetting the parameters of the instance before a new game
     */
    public void reset() {
        this.myMoves = new ArrayList();
        myMoves.add(0);
        this.opponentsMoves = new ArrayList();
        this.mode = 1;
    }

    /**
     * implementation of an interface method that collects data about other player and calls another function for the move
     */
    public int move(int opponentLastMove, int xA, int xB, int xC) {
        this.opponentsMoves.add(opponentLastMove);
        return aggressiveModeMove(xA, xB, xC);
    }

    /**
     * method that chooses the best cell, stores the move and returns it
     */

    private int aggressiveModeMove(int xA, int xB, int xC) {
        int move = chooseBiggest(xA, xB, xC);
        this.myMoves.add(move);
        return move;
    }

    /**
     * This method chooses the best cell out of all. If there are multiple best fields, it chooses one at random
     */

    private int chooseBiggest(int xA, int xB, int xC) {
        Random random = new Random();
        int grass = Math.max(xA, Math.max(xB, xC));
        ArrayList<Integer> arr = new ArrayList<>();
        if (xA == grass) {
            arr.add(1);
        }
        if (xB == grass) {
            arr.add(2);
        }
        if (xC == grass) {
            arr.add(3);
        }
        return arr.get(random.nextInt(arr.size()));
    }

    /**
     * This method returns email
     */
    public String getEmail() {
        return "a.fazylzhan@innopolis.university";
    }
}
