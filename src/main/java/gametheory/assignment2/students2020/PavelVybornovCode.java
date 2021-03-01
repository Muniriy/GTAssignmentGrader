package gametheory.assignment2.students2020;

import gametheory.assignment2.Player;

// My player's strategy is to analyze the probability of the opponent to take each field
// and to take the field which is not currently taken and has less probability to be taken
// during the next move
public class PavelVybornovCode implements Player {

    private int takenA, takenB, takenC, numMoves;

    // this function is to get an email
    public String getEmail() {
        return "p.vybornov@innopolis.ru";
    }

    // this function is to perform move
    public int move(int opponentLastMove, int xA, int xB, int xC) {

        if(opponentLastMove == 1){
            this.takenA += 1;
            this.numMoves+=1;
            int probB = this.takenB / numMoves;
            int probC = this.takenC / numMoves;
            if (probB < probC){
                return 2;
            }
            else {
                return 3;
            }
        }
        else if(opponentLastMove == 2){
            this.takenB += 1;
            this.numMoves+=1;
            int probA = this.takenA / numMoves;
            int probC = this.takenC / numMoves;
            if (probA < probC){
                return 1;
            }
            else {
                return 3;
            }
        }
        else {
            this.takenC += 1;
            this.numMoves+=1;
            int probA = this.takenA / numMoves;
            int probB = this.takenB / numMoves;
            if (probA < probB){
                return 1;
            }
            else {
                return 2;
            }
        }
    }

    // this function is to reset the values of the player
    public void reset(){
        
        this.takenA = 0;
        this.takenB = 0;
        this.takenC = 0;
        this.numMoves = 1;

    }
}
