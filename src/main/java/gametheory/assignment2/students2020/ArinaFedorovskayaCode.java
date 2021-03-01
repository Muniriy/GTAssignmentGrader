package gametheory.assignment2.students2020;

import gametheory.assignment2.Player;

import java.util.*;

public class ArinaFedorovskayaCode implements Player {
    private final Random rand = new Random();
    private int currentPosition;
    private int hand;
    private int turnGiveWay;
    private int previousDomField;
    private int numOfConcession;

    public ArinaFedorovskayaCode() {
        this.currentPosition = -1;
        this.hand = -1;
        this.turnGiveWay = 1;
        this.previousDomField = -1;
        this.numOfConcession = 0;
    }

    @Override
    public void reset() {
        this.currentPosition = -1;
        this.hand = -1;
        this.turnGiveWay = 1;
        this.previousDomField = -1;
        this.numOfConcession = 0;
    }

    @Override
    public int move(int opponentLastMove, int xA, int xB, int xC) {
        List<Integer> equalFields = getEqualFields(xA, xB, xC);

        if(opponentLastMove == this.previousDomField && this.currentPosition == this.previousDomField){ // Case when opponent did not respect your generosity
            this.turnGiveWay = 0;
            this.numOfConcession = 0;
            this.previousDomField = getDominant(xA, xB, xC);
        }

        if(opponentLastMove != this.previousDomField && this.currentPosition == this.previousDomField && opponentLastMove != 0){ //Case when opponent give way, so give way him next time
            this.turnGiveWay = 2;
            this.numOfConcession = 0;
            this.previousDomField = getDominant(xA, xB, xC);
        }

        if(opponentLastMove == this.previousDomField){ // Case when opponent grab provided opportunity to get dominant field
            this.turnGiveWay = 1;
            this.numOfConcession = 0;
            this.previousDomField = getDominant(xA, xB, xC);
        }

        if (equalFields.size() == 2 || equalFields.size() == 3) { // If two or three fields with equal numbers

            if (this.hand == -1) { // If we did not choose the hand to move, choose random from equal fields

                Collections.shuffle(equalFields, this.rand);
                this.currentPosition = (Integer) ((List<?>) equalFields).get(0);

            } else { // If we chose the hand, move like chosen

                if (equalFields.contains(this.hand)) {

                    this.currentPosition = this.hand;

                } else {
                    if (this.hand == 1) {
                        this.currentPosition = this.hand++;
                    }

                    if (this.hand == 2 || this.hand == 3) {
                        this.currentPosition = this.hand--;
                    }
                }
            }
        } else {
            if (this.turnGiveWay == 2) { // If there is dominant field, give way to opponent
                this.numOfConcession++;
                this.previousDomField = getDominant(xA, xB, xC);

                if (this.numOfConcession == 3) { // If there is dominant field, but opponent did not choose it on the third move, so choose it
                    this.turnGiveWay = 2;
                    this.currentPosition = getDominant(xA, xB, xC);
                    this.numOfConcession = 0;
                }
            } else { // If there is dominant strategy, but you give way in the previous time, so choose it
                if(this.turnGiveWay != 0) { // If did not opponent break your trust
                    this.turnGiveWay = 2;
                }
                this.numOfConcession = 0;
                this.currentPosition = getDominant(xA, xB, xC);
                this.previousDomField = this.currentPosition;
            }
        }

        return this.currentPosition;
    }

    @Override
    public String getEmail() {
        return "a.fedorovskaya@innopolis.ru";
    }

    private static List<Integer> getEqualFields(int xA, int xB, int xC) {
        HashMap<Integer, Integer> fields = new HashMap<>();
        fields.put(1, xA);
        fields.put(2, xB);
        fields.put(3, xC);

        int min = 100000000;

        for (Integer key: fields.keySet()) {
            if (min > fields.get(key)) {
                min = fields.get(key);
            }
        }

        int finalMin = min;
        fields.values().removeIf(v -> v == finalMin);

        return new ArrayList<>(fields.keySet());
    }

    private static int getDominant(int xA, int xB, int xC) {
        HashMap<Integer, Integer> fields = new HashMap<>();
        fields.put(1, xA);
        fields.put(2, xB);
        fields.put(3, xC);

        int index = -1;
        int max = 0;

        for (Integer key: fields.keySet()) {
            if (max < fields.get(key)) {
                index = key;
                max = fields.get(key);
            }
        }

        return index;
    }
}
