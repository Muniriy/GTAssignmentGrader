package gametheory.assignment2.students2021;

import gametheory.assignment2.Player;

import java.util.Arrays;
import java.util.Random;

/**
 * This class implements moose which trying to cooperate, randomly moving until no collision with
 * another moose, then moving only to it's own field and common one. If other steps on our field,
 * then turn greedy mode.
 */
public class RenatValeevCode implements Player {
    final Random random;
    int lastMove = 0; // our last move
    int ourField = -1; // our own field
    int restField = -1; // rest field number
    int mode = 0; // current behaviour mode of a agent or moose

    // public constructor of a class
    public RenatValeevCode() {
        random = new Random();
    }

    // resets mode, last move, our field values to default
    public void reset() {
        lastMove = 0;
        ourField = -1;
        restField = -1;
        mode = 0;
    }

    ;

    /**
     * Make greedy move given amount of grass on fields
     * choose fields with max grass on them and choose one of them randomly
     *
     * @param xA - amount of grass on field A
     * @param xB - amount of grass on field B
     * @param xC - amount of grass on field C
     * @return number of one of the fields with max grass on it
     */
    public int greedyMove(int xA, int xB, int xC) {
        // finding max amount of grass on fields
        int fields1[] = {xA, xB, xC};
        Arrays.sort(fields1);
        int max_grass = fields1[2];
        int max_fields[] = new int[3];

        // number of fields with max amount of grass on them
        int count_of_max = 0;
        if (xA == max_grass) {
            max_fields[count_of_max] = 1;
            count_of_max = count_of_max + 1;
        }
        ;
        if (xB == max_grass) {
            max_fields[count_of_max] = 2;
            count_of_max = count_of_max + 1;
        }
        ;
        if (xC == max_grass) {
            max_fields[count_of_max] = 3;
            count_of_max = count_of_max + 1;
        }
        ;
        // chooose random field with max amount of grass
        int random_max_field_index = random.nextInt(count_of_max);
        return max_fields[random_max_field_index];
    }

    // return move of a agent given opponent last move, amount grass on the fields
    public int move(int opponentLastMove, int xA, int xB, int xC) {
        if (mode == 0) { // assuming that we versus coop
            // if we step on same field, generate new random field where to go
            if (opponentLastMove == lastMove) {
                lastMove = random.nextInt(3) + 1;
                return lastMove;
            } else { // else assume that we versus coop moose and change to mode 1, cooperation mode
                mode = 1; // checking for coop
                ourField = lastMove;
                restField = 6 - lastMove - opponentLastMove;
                return restField;
            }
        } else if (mode == 1) { // we in cooperation mode
            /*
            pretend to be coop
            if betrayed then change to mod -1, greedy mode
            */
            int fields[] = {xA, xB, xC};
            if (opponentLastMove == ourField) { // if opponent not cooperating as we expected, turn greedy mode
                mode = -1; // greedy mode
                return greedyMove(xA, xB, xC);
            } else if (fields[ourField - 1] >= 6) { // our fields full of grass
                lastMove = ourField;
                return ourField;
            } else if (lastMove == ourField) { // our last move was on our field, next move is to go to rest field
                lastMove = restField;
                return restField;
            } else {
                // unless our field is ours and other moose dont step on it, play nicely
                // and go from rest field to our and back
                return restField;
            }
        } else { // we in greedy mode, mo change back
            return greedyMove(xA, xB, xC);
        }
    }

    ;

    public String getEmail() {
        return "r.valeev@innopolis.university";
    }

    ;
}