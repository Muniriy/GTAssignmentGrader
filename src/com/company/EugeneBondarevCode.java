package com.company;

//player which never move as enemy in last round, chose maximum from other fields
public class EugeneBondarevCode implements com.company.Player {
    @Override
    public void reset() {
    }

    @Override
    public int move(int opponentLastMove, int xA, int xB, int xC) {

        return getMove(new int[]{xA,xB,xC}, opponentLastMove) + 1;
    }

    private int getMove(int[] array, int opponentLastMove){
        int max = -1;
        int maxId = 0;
        for(int i=0;i<3;i++){
            if( array[i] >= max && i != opponentLastMove - 1){
                if( array[i] == max ){
                    int temp = (int) (Math.random()+1);
                    if(temp == 1){
                        max = array[i];
                        maxId = i;
                    }
                }else {
                    max = array[i];
                    maxId = i;
                }

            }
        }
        return maxId;
    }

    @Override
    public String getEmail() {
        return "e.bondarev@innopolis.ru";
    }

}
