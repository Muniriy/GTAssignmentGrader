package gametheory.assignment2.students2020;

import gametheory.assignment2.Player;

import java.util.Random;

/**
 * this mouse takes randomly max field value or middle
 * if some values are equal (1 1 2)
 * if will return 1st and 3rd indexes (takes first occurrence)
 */
public class DmitryKonevCode implements Player {
    private String email = "d.konev@innopolis.ru";

    /**
     * finding two highest values of 3 given, where
     *
     * @param xA X value of first field
     * @param xB X value of second field
     * @param xC X value of third field
     * @return array of appropriate indexes
     */
    private int[] findMax(int xA, int xB, int xC) {
        // check if first is the highest
        if (xA >= xB && xA >= xC) {
            //if second > third than first two are the highest
            if (xB >= xC)
                return new int[]{1, 2};
                //otherwise first and third are the highest
            else
                return new int[]{1, 3};
        }
        //otherwise second and third suit us
        else
            return new int[]{2, 3};
    }

    /**
     * constructor without name
     */
    public DmitryKonevCode() {
        super();
    }

    /**
     * name getter
     *
     * @return email
     */
    public String getEmail() {
        return email;
    }

    /**
     * needed for beautiful printing
     * setter, where
     *
     * @param email is an email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * no need to remember something, so reset does nothing
     */
    @Override
    public void reset() {

    }

    /**
     * move returns index of random choice
     * between two highest values
     *
     * @param opponentLastMove last opponent move
     * @param xA               X value of first field
     * @param xB               X value of second field
     * @param xC               X value of third field
     * @return field number, where to go
     */
    @Override
    public int move(int opponentLastMove, int xA, int xB, int xC) {
        // get array of indexes of the highest values
        int[] middleAndMax = findMax(xA, xB, xC);
        // take random index and return it
        Random generator = new Random();
        return middleAndMax[generator.nextInt(middleAndMax.length)];
    }
}
