package gametheory.assignment2.students2021;
import gametheory.assignment2.Player;

import static java.lang.Math.*;
import java.util.concurrent.ThreadLocalRandom;

/**
 *
 */
public class NataliyaTupikinaCode implements Player {
    /**
     * This method returns my IU email
     *
     * @return my email
     */
    @Override
    public String getEmail(){ return "n.tupikina@innopolis.ru";}
    int previousRound;
    boolean randomMove;

    @Override
    public void reset() {
        previousRound = -1;
    }

    /**
     * Initializer
     */
    public NataliyaTupikinaCode (){
        previousRound = -1;
        randomMove = false;
    }

    /**
     * @param f int for which we want sigmoid calculated
     * @return sigmoid of f
     */
    double sigmoid(int f){
        return 10*exp(f)/(1+exp(f));
    }


    /**
     * @param xA               the argument X for a field A
     * @param xB               the argument X for a field B
     * @param xC               the argument X for a field C
     * @return the field with the X value being maximum (best field)
     */
    int maxField(double xA, double xB, double xC){
        return findVal(Math.max(xA, max(xB,xC)),xA,xB,xC);
    }
    /**
     * @param xA               the argument X for a field A
     * @param xB               the argument X for a field B
     * @param xC               the argument X for a field C
     * @return the field with the X value being in the middle
     */
    int medianField(double xA, double xB, double xC){
        return findVal(max(min(xA,xB), min(max(xA,xB),xC)),xA,xB,xC);
    }

    /**
     * @param val value for which we want to find field
     * @param xA               the argument X for a field A
     * @param xB               the argument X for a field B
     * @param xC               the argument X for a field C
     * @return field, which holds the given value (val). e.g. if field 1 has value val then method returns 1
     */
    /**
     * @param val value for which we want to find field
     * @param xA               the argument X for a field A
     * @param xB               the argument X for a field B
     * @param xC               the argument X for a field C
     * @return field, which holds the given value (val). e.g. if field 1 has value val then method returns 1
     */
    int findVal(double val, double xA, double xB, double xC){
        if (val<1){
            int m = maxField(xA, xB, xC);
            randomMove = true;
            return m;
        }
        if (val==xB){
            if (val==xA){
                if (val==xC){
                    randomMove =true;
                    return ThreadLocalRandom.current().nextInt(0, 3);
                }
                randomMove =true;
                return ThreadLocalRandom.current().nextInt(0, 2);
            }
            if (val==xC){
                randomMove =true;
                return ThreadLocalRandom.current().nextInt(1, 3);
            }
            randomMove =false;
            return 1;
        }
        if (val==xC){
            if (val==xA){
                randomMove =true;
                if (ThreadLocalRandom.current().nextBoolean()){
                    return 2;
                }
                randomMove =false;
                return 0;
            }
            randomMove =false;
            return 2;
        }
        randomMove =false;
        return 0;
    }


    /**
     * This method returns the move of the player based on
     * the last move of the opponent and X values of all fields.
     * Initially, X for all fields is equal to 1 and last opponent
     * move is equal to 0
     *
     * @param opponentLastMove the last move of the opponent
     *                         varies from 0 to 3
     *                         (0 – if this is the first move)
     * @param xA               the argument X for a field A
     * @param xB               the argument X for a field B
     * @param xC               the argument X for a field C
     * @return the move of the player can be 1 for A, 2 for B
     *         and 3 for C fields
     */
    @Override
    public int move(int opponentLastMove, int xA, int xB, int xC){
        boolean jr = randomMove;
        int median = medianField(sigmoid(xA) - sigmoid(0), sigmoid(xB) - sigmoid(0), sigmoid(xC) - sigmoid(0));
        int maximum = maxField(sigmoid(xA) - sigmoid(0), sigmoid(xB) - sigmoid(0), sigmoid(xC) - sigmoid(0));
        if(randomMove){
            return maximum+1;
        }
        previousRound = maximum;
        if ((!jr)&&(opponentLastMove==previousRound)) {
            return median+1;
        }
        return maximum+1;
    }
}