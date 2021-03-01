package gametheory.assignment2.students2020;

import gametheory.assignment2.Player;

import java.util.ArrayList;

public class IskenderGuseynovCode implements Player {
    // Storage of moves. Format is the following: <this.lastMove, opponent.lastMove, currentA, currentB, currentC>
    ArrayList<int[]> data;
    // Last step index
    int lastStep;

    public IskenderGuseynovCode() {
        data = new ArrayList<>();
        data.add(new int[]{0, 0, 1, 1, 1});
        this.lastStep = 0;
    }

	public String getEmail(){
		return "i.guseynov@innopolis.ru";
	}

    // Yes, my agent needs initial field values.
    public IskenderGuseynovCode(int initA, int initB, int initC){
        data = new ArrayList<>();
        data.add(new int[]{0,0,initA,initB,initC});
        this.lastStep = 0;
    }

    public IskenderGuseynovCode(IskenderGuseynovCode other){
        data = new ArrayList<>();
        data.add(other.data.get(0));
        this.lastStep = 0;
    }

    // Yes, resetting leads to keeping only first line of data - initial parameters.
    @Override
    public void reset() {
        int[] popped = this.data.remove(0);
        this.data.clear();
        this.data.add(popped);
        this.lastStep = 0;
    }

    /**
     * Returns opcode of success of selected move.
     *
     * @param checkMyMove: TRUE for checking move of this agent, FALSE - for checking opponent's move
     * @param lastStep: index of data
     * @return moveCode: either move itself or 0.
     */
    int lastMoveWasSuccessful(boolean checkMyMove, int lastStep){
        int x = checkMyMove ? 0 : 1;
        if (this.data.get(lastStep)[2] >= this.data.get(lastStep + 1)[2] && this.data.get(lastStep)[x] == 1 && this.data.get(lastStep)[1-x] != 1){
            return 1;
        } else if (this.data.get(lastStep)[3] >= this.data.get(lastStep + 1)[3] && this.data.get(lastStep)[x] == 2 && this.data.get(lastStep)[1-x] != 2){
            return 2;
        } else if (this.data.get(lastStep)[4] >= this.data.get(lastStep + 1)[4] && this.data.get(lastStep)[x] == 3 && this.data.get(lastStep)[1-x] != 3){
            return 3;
        } else {
            return 0;
        }
    }


    /**
     * 0 is natural, according to ISO 80000-2.
     *
     * @param opponentLastMove: natural value in {1,2,3}
     * @param xA: natural value, represents 1st field.
     * @param xB: natural value, represents 2nd field.
     * @param xC: natural value, represents 3rd field.
     * @return own move at this step, as natural value in {1,2,3}.
     *
     * This agent acts under the "cooperate as long as possible" strategy.
     * Reason for this is an obvious fact: it is better to gain something even not maximum possible, than gain nothing.
     * Still, this agent is about to gain something valuable, not always being the last. Thus, agent will try to be greedy while it's possible.
     * In cases of weak difference of input fields, randomness will be used.
     */
    @Override
    public int move(int opponentLastMove, int xA, int xB, int xC){
        // Register new entry in storage
        this.data.add(new int[]{-1, opponentLastMove, xA, xB, xC});

        // Examine, which move was successful.
        int otherSuccessfulMove = this.lastMoveWasSuccessful(false, this.lastStep), thisSuccessfulMove = this.lastMoveWasSuccessful(true, this.lastStep);
        this.lastStep += 1;

        /* There are three possible cases of moves */
        if ((otherSuccessfulMove > 0 && thisSuccessfulMove > 0) || (otherSuccessfulMove == 0 && thisSuccessfulMove > 0)){
            // Both agents have avoided each other, OR opponent selected poor field. Wish to wait opponent, OR do not go there.
            // Select secondary big field if clearly possible or go randomly.
            this.data.get(lastStep)[0] = (xA < xB && xB < xC || xC > xB && xB > xA) ? 2
                    : (xA < xC && xC < xB || xA > xC && xC > xB) ? 3
                    : (xB < xA && xA < xC || xC < xA && xA < xB) ? 1
                    : (int) (1 + Math.round(2.0 * Math.random()));

        } else if ((otherSuccessfulMove > 0 && thisSuccessfulMove == 0) || (otherSuccessfulMove == 0 && thisSuccessfulMove == 0) ){
            // Both agents have met each other, OR this agent selected poor field. Hustle up, OR do not go there.
            // Try to avoid other agent. Get the primary profitable move if possible, else go randomly.
            this.data.get(lastStep)[0] = (xA > xB && xA > xC) ? 1
                    : (xB > xA && xB > xC) ? 2
                    : (xA < xC && xB < xC) ? 3
                    : (int) (1 + Math.round(2.0 * Math.random()));
        }

        return this.data.get(lastStep)[0];
    }
}
