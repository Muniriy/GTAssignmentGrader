package gametheory.assignment2.students2020;


import gametheory.assignment2.Player;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;

//My Player agent code
public class EgorPolivtsevCode implements Player {

    public String getEmail() {
        return "e.polivtsev@innopolis.ru";
    }

    //My last move memory
    private Integer myLM;

    //Reset function
    public void reset() {
        //Reset my last move memory
        myLM = 0;
    }

    //Move function
    public int move(int opponentLastMove, int xA, int xB, int xC) {

        class Pair {
            int key;
            int value;

            public Pair(int x, int y)
            {
                this.key = x;
                this.value = y;
            }
        }

        Pair[] fields = new Pair[3];
        fields[0] = new Pair(xA, 1);
        fields[1] = new Pair(xB, 2);
        fields[2] = new Pair(xC, 3);

        Arrays.sort(fields, Comparator.comparingInt(p -> p.key));

        //If first move - try to get max
        if(opponentLastMove == 0) {
            myLM = fields[2].value;
            return myLM;
        }

        //If all fields are equal - don't fight
        if(fields[2].key == fields[1].key && fields[1].key == fields[0].key) {
            if(myLM.equals(opponentLastMove)) {
                myLM = new Random().nextInt( 3) + 1;
            }
            return myLM;
        }
        //If two maximum fields are equal - try to reach an agreement
        if(fields[2].key == fields[1].key) {
            if(myLM.equals(opponentLastMove)) {
                if(myLM == fields[1].key) {
                    myLM = fields[2].value;
                }
                else {
                    myLM = fields[1].value;
                }
            }
            return myLM;
        }

        //If my agent was already in the largest field and the opponent lost the field to me,
        //my agent will lose the largest field. Sharing is good:)
        if(fields[2].value == myLM && !myLM.equals(opponentLastMove)) {
            myLM = fields[1].value;
            return myLM;
        }

        //If the enemy does not cede the field to my agent, we will fight for this field
        if(fields[2].value == myLM && myLM.equals(opponentLastMove)) {
            return myLM;
        }

        //If other conditions are not met, then try to take the maximum field
        myLM = fields[2].value;
        return myLM;
    }
}
