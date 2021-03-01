package gametheory.assignment2.students2020;

import gametheory.assignment2.Player;

import java.util.ArrayList;

public class TimurAnufriyevCode implements Player {

    public ArrayList<Integer> moves; // to save opponents previous moves
    public ArrayList<Integer> scores; // to save previous regions' scores
    boolean max;    // to make move depends on the opponents' move

    @Override
    public void reset() { // cleanes Array List
        moves = new ArrayList<>();
        scores = new ArrayList<>();
        max = false;
    }

    @Override
    public int move(int opponentLastMove, int xA, int xB, int xC) {
        if (opponentLastMove != 0) {    // First move does not make sense
            moves.add(opponentLastMove);
            scores.add(xA);
            scores.add(xB);
            scores.add(xC);
        }
        if (moves.size() == 4){     // After 4 moves find whether Opponent just taking maximum or not
            max = isOpponentTakingMax(); // call of the function
        }
        if (max){  // If opponent taking always maximum, my agent will take the mean
            double mean = xA + xB + xC - Math.max(xA, Math.max(xB, xC)) - Math.min(xA, Math.min(xB, xC));
            int m = (int)Math.round(mean);
            if (m == xC)
                return 3;
            else if (m == xB)
                return 2;
            else return 1;
        }
        else {  // Otherwise, my agent will take maximum
            int mx = Math.max(xA, Math.max(xB, xC));
            if (mx == xC)
                return 3;
            else if(mx == xB)
                return 2;
            else return 1;
        }
    }

    @Override
    public String getEmail() {
        return "t.anufriev@innopolis.ru";
    }

    public boolean isOpponentTakingMax(){   // Function, which is
        int isTakingMax = 0;
        for (int i = 0; i < moves.size(); i++) {
            int new_xA = scores.get(i*3);
            int new_xB = scores.get(i*3+1);
            int new_xC = scores.get(i*3+2);
            int new_mx = Math.max(new_xA, Math.max(new_xB, new_xC));
            int new_move = moves.get(i);
            int tokenPos;
            if (new_move == 1)
                tokenPos = new_xA;
            else if (new_move == 2)
                tokenPos = new_xB;
            else tokenPos = new_xC;
            if (tokenPos == new_mx)
                isTakingMax++;
        }
        return isTakingMax == 4;
    }
}
