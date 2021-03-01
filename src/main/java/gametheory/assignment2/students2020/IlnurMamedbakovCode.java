package gametheory.assignment2.students2020;

import gametheory.assignment2.Player;

import java.util.ArrayList;

public class IlnurMamedbakovCode implements Player {
    private int lastxA, lastxB, lastxC; // previous x's on squares
    private int badM, goodM, countM; // counters of bad and kind moves of opponent
    private int numberGames; // counter of all games

    @Override
    public void reset() {
        lastxA = 0;
        lastxB = 0;
        lastxC = 0;
        badM   = 0;
        goodM  = 0;
        countM = 0;
        numberGames = 0;
    }


    @Override
    public int move(int opponentLastMove, int xA, int xB, int xC) {
        int result = -1; // here will be result

        //If its a first game between mooses
        //We don't care which square take, because they all equal
        if (opponentLastMove == 0) {//If its a first game between mooses
            goodM  = 0; // number of kind moves of opponent
            badM   = 0; // number of bad moves of opponent
            countM = 0; // number of total kind or bad moves
            lastxA = xA;
            lastxB = xB;
            lastxC = xC;
            numberGames = 1;
            return (int) (1 + Math.random()*3);
        }

        //if its second round
        //we can choose best square here, but we still don't care about moves of opponent
        //because he still didn't made any logical moves
        if (numberGames == 1){
            ArrayList<Integer> listPresentSquares = new ArrayList<>();
            listPresentSquares.add(xA);
            listPresentSquares.add(xB);
            listPresentSquares.add(xC);
            ArrayList<Integer> sortedPresentSquares = sort(listPresentSquares);

            lastxA = xA;
            lastxB = xB;
            lastxC = xC;
            numberGames = numberGames + 1;
            return sortedPresentSquares.get(0);
        }

        //sorting previous squares for understanding which was best
        ArrayList<Integer> listPreviousSquares = new ArrayList<>();
        listPreviousSquares.add(lastxA);
        listPreviousSquares.add(lastxB);
        listPreviousSquares.add(lastxC);
        ArrayList<Integer> sortedPrevSquares =  sort(listPreviousSquares); //this method return ID's of top squares

        //sorting present squares for understanding which is the best
        ArrayList<Integer> listPresentSquares = new ArrayList<>();
        listPresentSquares.add(xA);
        listPresentSquares.add(xB);
        listPresentSquares.add(xC);
        ArrayList<Integer> sortedPresentSquares = sort(listPresentSquares);
        listPresentSquares = usualSort(listPresentSquares); //this method just sort x's of each squares

        //I am calculating coefficient of how opponent are kind or evil
        //If he made greedy choice I'm adding to badM, because he stole my best move
        //if he didn't take the best move, I add goodM, cause he left for me best square
        if (opponentLastMove == sortedPrevSquares.get(0)){
            // if in previous game opponent made greedy decision
            badM = badM + 1;
            countM = countM + 1;
        } else {
            // if in previous game opponent made NOT greedy decision
            goodM = goodM + 1;
            countM = countM + 1;
        }

        //Here I am doing actual calculation of coefficient
        //This coefficient located within the borders between -1 to +1
        //If coef = 1 -> opponent is really kind and always leave for me best square
        //if coef = -1-> opponent are greedy and always trying to take best square
        //if -1 < coef < 1 -> opponent not greedy and not too kind
        double evilCoef = Double.valueOf((goodM - badM)) / Double.valueOf(countM); // if coef = 1 its a kind opponent, if its -1 its greedy opponent, if coef =0 its strange opponent

        //1. If opponent are greedy
        //1.1 I will take second after best square
        //1.2 But if second square after best have x = 0, I will go to best square and will fight with opponent
        //2. If opponent are too kind
        //2.1 I will take best square
        //3. If opponent are not greedy and not too kind
        //3.1 I will take best or second square with probability 0.5
        //3.2 But if in second square x = 0, I will go to best square
        if (evilCoef == -1) { // if moose are greedy
            if (listPresentSquares.get(1) == 0){ // if second square are empty
                result = sortedPresentSquares.get(0); // go to fight with another moose on best square
            } else {
                result = sortedPresentSquares.get(1); // if second square are not empty go there
            }
        } else if (evilCoef == 1){ // if opponent moose are kind
            result = sortedPresentSquares.get(0); // go and eat on best square
        } else {//if opponent not greedy or too kind
            if(Math.random() > 0.5) { //eat randomly on best or second square
                result = sortedPresentSquares.get(0);
            } else {
                if(listPresentSquares.get(1) != 0) // eat second square only if its not empty
                    result = sortedPresentSquares.get(1);
                else
                    result = sortedPresentSquares.get(0);
            }
        }

        lastxA = xA;
        lastxB = xB;
        lastxC = xC;
        numberGames = numberGames + 1;
        return result;

    }

    @Override
    public String getEmail() {
        return "i.mamedbakov@innopolis.ru";
    }

    // This bubble sort returning indexes of sorted list
    private ArrayList<Integer> sort(ArrayList<Integer> arr){
        ArrayList<Integer> arrI = new ArrayList<>();
        arrI.add(1);
        arrI.add(2);
        arrI.add(3);

        for (int i = arr.size() - 1; i > 0; i--) {
            for (int j = 0; j < i; j++) {

                if (arr.get(j) < arr.get(j + 1)) {
                    int tmp = arr.get(j);
                    int tmpI = arrI.get(j);

                    arr.set(j, arr.get(j + 1));
                    arrI.set(j,arrI.get(j + 1));

                    arr.set(j + 1, tmp);
                    arrI.set(j + 1, tmpI);

                }
            }
        }
        return arrI;
    }
    //This is usual bubble sort that just returning sorted list
    private ArrayList<Integer> usualSort(ArrayList<Integer> arr){
        for (int i = arr.size() - 1; i > 0; i--) {
            for (int j = 0; j < i; j++) {

                if (arr.get(j) < arr.get(j + 1)) {
                    int tmp = arr.get(j);
                    arr.set(j, arr.get(j + 1));
                    arr.set(j + 1, tmp);

                }
            }
        }
        return arr;
    }
}
