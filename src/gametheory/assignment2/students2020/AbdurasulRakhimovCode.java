package gametheory.assignment2.students2020;

import gametheory.assignment2.Player;

import java.util.Arrays;
import java.util.Random;

public class AbdurasulRakhimovCode implements Player {
    private Random random;
    private boolean isNotGreedy = false;
    private int lastA, lastB, lastC;
    private double alpha = 1.4;

    public AbdurasulRakhimovCode(){
        this.reset();
    }

    public void reset(){
        this.random = new Random();
        this.lastA = 1;
        this.lastB = 1;
        this.lastC = 1;
        this.isNotGreedy = false;
    }

    private boolean isOpponentNotGreedy(int opMove){
        int[] a = new int[]{this.lastA, this.lastB, this.lastC};
        Arrays.sort(a);
        String s = "";
        if (this.lastA == a[2]) {s += "1";}
        if (this.lastB == a[2]) {s += "2";}
        if (this.lastC == a[2]) {s += "3";}

        boolean isTrue = true;
        for (int i = 0;i < s.length();i ++){
            int value = Character.digit(s.charAt(i), 10);
            if (value == opMove){
                isTrue = false;
            }
        }
        return isTrue;
    }

    public int move(int opponentLastMove, int xA, int xB, int xC){
        if (opponentLastMove != 0) {
            if (!this.isNotGreedy) {
                if (this.isOpponentNotGreedy(opponentLastMove)) {
                    this.isNotGreedy = true;
                }
            }
        }

        this.lastA = xA; this.lastB = xB; this.lastC = xC;

        int[] a = new int[] {xA, xB, xC};
        Arrays.sort(a);
        int best = a[2];
        int secondBest;
        if (a[0] == a[1] && a[0] == a[2]){
            secondBest = a[0];
        }else{
            if (a[1] != a[2])
                secondBest = a[1];
            else
                secondBest = a[0];
        }

        String s = "", d = "";
        if (best == xA) {s += "1";} if (best == xB) {s += "2";} if (best == xC) {s += "3";}

        if (secondBest == xA) {d += "1";} if (secondBest == xB) {d += "2";} if (secondBest == xC) {d += "3";}

        if (this.isNotGreedy) {
            int pos = random.nextInt(s.length());
            return Character.digit(s.charAt(pos), 10);
        }else{
            double loss = f(best) - f(secondBest);
            if (s.length() == 1 && loss < this.alpha){
                int pos = random.nextInt(d.length());
                return Character.digit(d.charAt(pos), 10);
            }else{
                int pos = random.nextInt(s.length());
                return Character.digit(s.charAt(pos), 10);
            }
        }
    }

    private double f(int x){
        return (10.0 * Math.exp(x)) / (1.0 + Math.exp(x));
    }

    public String getEmail(){
        return "a.rahimov@innopolis.ru";
    }

}
