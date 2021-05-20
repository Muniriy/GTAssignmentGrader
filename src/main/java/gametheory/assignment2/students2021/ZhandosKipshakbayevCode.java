package gametheory.assignment2.students2021;

import gametheory.assignment2.Player;

public class ZhandosKipshakbayevCode implements Player {
    boolean bestA = false;
    boolean bestB = false;
    boolean bestC = false;
    /**
     * patience is the amount of round we are ready to fight
     * to identify if opponent is aggressive too
     * <p>
     * 'best' variables are checkers of whether
     * some field is better than the others
     * <p>
     * myLastMove is storage of our previous move
     */
    private int patience = 7;
    private int myLastMove = 0;

    /**
     * reset method resets variables to initial values
     */
    public void reset() {
        patience = 7;
        bestA = false;
        bestB = false;
        bestC = false;
        myLastMove = 0;
    }

    /**
     * @param opponentLastMove the last move of the opponent
     *                         varies from 0 to 3
     *                         (0 – if this is the first move)
     * @param xA               the argument X for a field A
     * @param xB               the argument X for a field B
     * @param xC               the argument X for a field C
     * @return
     */
    public int move(int opponentLastMove, int xA, int xB, int xC) {
        /*
         * PART ONE (CALCULATE PATIENCE WHEN FOUGHT)
         */

        // if previous round happened so that we fought
        if (myLastMove == opponentLastMove) {
            // if there was the Best field
            if (bestA || bestB || bestC) {
                switch (opponentLastMove) {
                    case 0:
                        //on first move do not change patience
                        break;
                    case 1:
                        if (bestA) {
                            //if we fought on field A which was the best
                            //we reduce patience meaning we want less to fight
                            patience = Math.max(patience - 1, -5);
                        } else {
                            //if we fought on field A which was not the best
                            //we increase patience meaning we go aggressive to take the best field
                            patience = Math.min(patience + 1, 5);
                        }
                        break;
                    case 2:
                        if (bestB) {
                            //if we fought on field B which was the best
                            //we reduce patience meaning we want less to fight
                            patience = Math.max(patience - 1, -5);
                        } else {
                            //if we fought on field B which was not the best
                            //we increase patience meaning we go aggressive to take the best field
                            patience = Math.min(patience + 1, 5);
                        }
                        break;
                    case 3:
                        if (bestC) {
                            //if we fought on field C which was the best
                            //we reduce patience meaning we want less to fight
                            patience = Math.max(patience - 1, -5);
                        } else {
                            //if we fought on field C which was not the best
                            //we increase patience meaning we go aggressive to take the best field
                            patience = Math.min(patience + 1, 5);
                        }
                        break;
                }
            }
        } else {
            /*
             * PART TWO (CALCULATE PATIENCE WHEN DID NOT FOUGHT)
             */
            // if there was the Best field
            if (bestA || bestB || bestC) {
                switch (opponentLastMove) {
                    case 0:
                        //on first move do not change patience
                        break;
                    case 1:
                        if (!bestA) {
                            //if opponent chose field A which was not the best
                            //we increase patience meaning we go aggressive to take the best field
                            patience = Math.min(patience + 1, 5);
                        }
                        break;
                    case 2:
                        if (!bestB) {
                            //if opponent chose field B which was not the best
                            //we increase patience meaning we go aggressive to take the best field
                            patience = Math.min(patience + 1, 5);
                        }
                        break;
                    case 3:
                        if (!bestC) {
                            //if opponent chose field C which was not the best
                            //we increase patience meaning we go aggressive to take the best field
                            patience = Math.min(patience + 1, 5);
                        }
                        break;
                }
            }
        }
        //calculate expected payoffs of each field
        double payA = (10 * Math.exp(xA)) / (1 + Math.exp(xA)) - 5;
        double payB = (10 * Math.exp(xB)) / (1 + Math.exp(xB)) - 5;
        double payC = (10 * Math.exp(xC)) / (1 + Math.exp(xC)) - 5;
        //reset checkers of best field
        bestA = false;
        bestB = false;
        bestC = false;
        //if we are in aggressive mode
        /*
         * PART THREE (AGGRESSIVE MODE)
         */
        if (patience > 0) {
            //choose the best field
            if (payA > payB && payA > payC) { //best field A
                myLastMove = 1;
                bestA = true;
            } else if (payB > payA && payB > payC) { //best field B
                myLastMove = 2;
                bestB = true;
            } else if (payC > payA && payC > payB) { //best field C
                myLastMove = 3;
                bestC = true;
            } else
                //if there are several best fields choose random one
                if (payA == payB && payA > payC) { //best fields A and B choose random
                    myLastMove = (int) (Math.random()) + 1;
                    bestA = true;
                    bestB = true;
                } else if (payB == payC && payB > payA) { //best fields B and C choose random
                    myLastMove = (int) (Math.random()) + 2;
                    bestB = true;
                    bestC = true;
                } else if (payA == payC && payA > payB) { //best fields A and C choose random
                    myLastMove = (int) (Math.random()) * 2 + 1;
                    bestA = true;
                    bestC = true;
                } else if (payA == payC && payA == payB) { //fields are equal choose random
                    bestA = false;
                    bestB = false;
                    bestC = false;
                    if (payA == 0) {
                        myLastMove = opponentLastMove;
                    } else {
                        myLastMove = (int) (Math.random() * 2) + 1;
                    }
                }
        } else {
            /*
             * PART FOUR (PASSIVE MODE)
             */
            //if we are in passive mode choose second best field
            if (payA > payB && payB > payC) { //second best fields B choose it
                myLastMove = 2;
                bestA = true; //change best checker
            } else if (payA > payC && payC > payB) { //second best fields C choose it
                myLastMove = 3;
                bestA = true; //change best checker
            } else if (payB > payA && payA > payC) { //second best fields A choose it
                myLastMove = 1;
                bestB = true; //change best checker
            } else if (payB > payC && payC > payA) { //second best fields C choose it
                myLastMove = 3;
                bestB = true; //change best checker
            } else if (payC > payA && payA > payB) { //second best fields A choose it
                myLastMove = 1;
                bestC = true; //change best checker
            } else if (payC > payB && payB > payA) { //second best fields B choose it
                myLastMove = 2;
                bestC = true; //change best checker
            } else
                //if there are several second best fields choose random one
                //if there are two zero fields choose best field
                if (payA == payB && payA != payC) {
                    if (payA > payC) { //two best fields
                        bestA = true; //change best checker
                        bestB = true; //change best checker
                        //if risk of fight worth payoff bigger than third field choose random best
                        if ((0.5 * payA) > payC) {
                            myLastMove = (int) (Math.random()) + 1;
                        } else
                            myLastMove = 3;
                    } else if (payA < payC) {
                        bestC = true; //change best checker
                        if (payA == 0) { //if there are two zero fields choose best field
                            myLastMove = 3;
                        } else { //if two non-zero worst fields choose random
                            myLastMove = (int) (Math.random()) + 1;
                        }
                    }
                } else if (payB == payC && payA != payC) {
                    if (payB > payA) {
                        bestB = true; //change best checker
                        bestC = true; //change best checker
                        //if risk of fight worth payoff bigger than third field choose random best
                        if (0.5 * payB > payA) {
                            myLastMove = (int) (Math.random()) + 2;
                        } else //if risk does not worth a try choose worst field
                            myLastMove = 1;
                    } else if (payB < payA) {
                        bestA = true; //change best checker
                        if ((int) payB == 0) { //if there are two zero fields choose best field
                            myLastMove = 1;
                        } else { //if two non-zero worst fields choose random
                            myLastMove = (int) (Math.random()) + 2;
                        }
                    }
                } else if (payA == payC && payA != payB) {
                    if (payA > payB) {
                        bestA = true; //change best checker
                        bestC = true; //change best checker
                        //if risk of fight worth payoff bigger than third field choose random best
                        if (0.5 * payA > payB) {
                            myLastMove = (int) (Math.random()) * 2 + 1;
                        } else //if risk does not worth a try choose worst field
                            myLastMove = 3;
                    } else if (payA < payB) {
                        bestB = true; //change best checker
                        if ((int) payA == 0) { //if there are two zero fields choose best field
                            myLastMove = 2;
                        } else { //if two non-zero worst fields choose random
                            myLastMove = (int) (Math.random()) * 2 + 1;
                        }
                    }
                } else if (payA == payC && payA == payB) {
                    bestA = false; //change best checker
                    bestB = false; //change best checker
                    bestC = false; //change best checker
                    if ((int) payA == 0) {
                        myLastMove = opponentLastMove;
                    } else //if risk does not worth a try choose worst field
                        myLastMove = (int) (Math.random() * 2) + 1;
                }
        }
        return myLastMove;
    }

    /**
     * This method returns your IU email
     *
     * @return your email
     */
    public String getEmail() {
        return "z.kipshakbaev@innopolis.university";
    }

    ;
}