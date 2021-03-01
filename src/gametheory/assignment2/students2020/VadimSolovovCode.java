package gametheory.assignment2.students2020;

import gametheory.assignment2.Player;

import java.util.concurrent.ThreadLocalRandom;

import static java.lang.Math.*;

public class VadimSolovovCode implements Player {
    public VadimSolovovCode() {
        prevMa = -1;
        justRandom = false;
    }

    int prevMa; // previous round maximum field
    boolean justRandom; // will be set if there is more then one max or median in field values

    public void reset() {
        prevMa = -1;
    }

    @Override
    public String getEmail() {
        return "v.solovov@innopolis.ru";
    }

    double sigmoid(int f) {
        return 10 * exp(f) / (1 + exp(f));
    }

    int maxField(double xA, double xB, double xC) {
        double ma = Math.max(xA, Math.max(xB, xC));
        return findByVal(ma, xA, xB, xC);
    }
    int medianField(double xA, double xB, double xC){
        double ma  = max(min(xA,xB), min(max(xA,xB),xC));
        return findByVal(ma,xA,xB,xC);
    }

    // find the field, which holds ma value
    int findByVal(double ma, double xA, double xB, double xC){
        if (ma<1){
            int m = maxField(xA, xB, xC);
            justRandom = true;
            return m;
        }
        if (ma==xB){
            if (ma==xA){
                if (ma==xC){
                    justRandom=true;
                    return ThreadLocalRandom.current().nextInt(0, 3);
                }
                justRandom=true;
                return ThreadLocalRandom.current().nextInt(0, 2);
            }
            if (ma==xC){
                justRandom=true;
                return ThreadLocalRandom.current().nextInt(1, 3);
            }
            justRandom=false;
            return 1;
        }
        if (ma==xC){
            if (ma==xA){
                justRandom=true;
                if (ThreadLocalRandom.current().nextBoolean()){
                    return 2;
                }
                justRandom=false;
                return 0;
            }
            justRandom=false;
            return 2;
        }
        justRandom=false;
        return 0;
    }


    public int move(int opponentLastMove, int xA, int xB, int xC) {
        boolean jr = justRandom;
        int median = medianField(sigmoid(xA) - sigmoid(0), sigmoid(xB) - sigmoid(0), sigmoid(xC) - sigmoid(0));
        int ma = maxField(sigmoid(xA) - sigmoid(0), sigmoid(xB) - sigmoid(0), sigmoid(xC) - sigmoid(0));
        if(justRandom){
            return ma+1;
        }
        boolean flag = opponentLastMove==prevMa;
        prevMa = ma;
        if ((!jr)&&(flag)) {
            return median+1;
        }
        return ma+1;
        // +1 in returns is because I count from 0, not 1
    }
}
