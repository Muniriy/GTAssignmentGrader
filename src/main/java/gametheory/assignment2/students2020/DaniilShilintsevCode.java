package gametheory.assignment2.students2020;

import gametheory.assignment2.Player;

public class DaniilShilintsevCode implements Player {

    private int prevRoundXa; // previous Xa
    private int prevRoundXb; // previous Xb
    private int prevRoundXc; // previous Xc
    private int Rounds = 0; // Amount of done rounds
    private int notMaxOppMoves = 0; // amount of moves in which opponent chose not best field


    /*
    The idea of DaniilShilintsevCode is:

    I check the condition if opponent mostly goes to the largest fields
    or he prefers to go to the not best field.
    If an opponent goes to the biggest fields,
    my agent will choose second best field.
    Otherwise my agent will take the best field move

    One important note: I check all previous actions
    of my opponent, not just last move.
    */


    @Override
    public void reset() {
        Rounds = 0;
        notMaxOppMoves = 0;
        prevRoundXa = 0;
        prevRoundXb = 0;
        prevRoundXc = 0;
        notMaxOppMoves = 0;
    }

    @Override
    public int move(int opponentLastMove, int xA, int xB, int xC) {
        if (opponentLastMove != 0){
//            Rounds = 0;
            boolean opponentIQflag = false; // this is a flag to know if my opponent took best X in his last move or not
            if (prevRoundXa >= prevRoundXb && prevRoundXa >= prevRoundXc){
                if (opponentLastMove != 1){
                    opponentIQflag = true; // if he did, change the flag
                } else opponentIQflag = false;
            } else if (prevRoundXb >= prevRoundXa && prevRoundXb >= prevRoundXc){
                if (opponentLastMove != 2){
                    opponentIQflag = true; // if he did, change the flag
                } else opponentIQflag = false;
            } else{
                if (opponentLastMove != 3){
                    opponentIQflag = true; // if he did, change the flag
                } else opponentIQflag = false;
            }
            if (opponentIQflag = true){
                notMaxOppMoves = notMaxOppMoves + 1;
            }
            Rounds = Rounds + 1; // Increase amount of rounds
        }

        prevRoundXa = xA; // reset value of previous xA
        prevRoundXb = xB; // reset value of previous xB
        prevRoundXc = xC; // reset value of previous xC

        if (Rounds / 2 <= notMaxOppMoves) { // if our opponent have chosen not best fields more then best fields in past, my agent will go to best one
            int max = Math.max(xA, (Math.max(xB, xC)));
            if (max == xA){
                return 1;
            }
            else if (max == xB){
                return 2;
            }
            else{
                return 3;
            }
        } else{ // otherwise my agent will go to the second large field
            if (xB >= xA && xA >= xC || xC >= xA && xA >= xB)
                return 1;
            else if (xA >= xB && xB >= xC || xC >= xB && xB >= xA)
                return 2;
            else
                return 3;
        }

    }

    @Override
    public String getEmail() {
        return "d.shilintsev@innopolis.ru";
    }
}