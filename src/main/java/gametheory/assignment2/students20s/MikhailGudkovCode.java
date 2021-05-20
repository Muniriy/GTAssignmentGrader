package gametheory.assignment2.students20s;

import gametheory.assignment2.Player;

import java.util.ArrayList;

public class MikhailGudkovCode implements Player {

    /**
     * Move method is based on the greedy algorithm with the use of random for selecting one from several maximums
     *
     * @param opponentLastMove the last move of the opponent
     *                         varies from 0 to 3
     *                         (0 – if this is the first move)
     * @param xA               the argument X for a field A
     * @param xB               the argument X for a field B
     * @param xC               the argument X for a field C
     * @return selected field
     */
    @Override
    public int move(int opponentLastMove, int xA, int xB, int xC) {

        //Store values of fields
        ArrayList values = new ArrayList<Integer[]>();
        values.add(new Integer[]{xA, 1});
        values.add(new Integer[]{xB, 2});
        values.add(new Integer[]{xC, 3});
        //Sort them
        values.sort(((o1, o2) -> {
            return ((Integer[]) o1)[0].compareTo(((Integer[]) o2)[0]);
        }));

        //If two fields have maximum
        if (((Integer[]) values.get(2))[0].equals(((Integer[]) values.get(1))[0])) {
            //Check if third is also maximum
            if (((Integer[]) values.get(1))[0].equals(((Integer[]) values.get(0))[0])) {
                //Randomly select field of three maximums
                if (Math.random() > 0.5 && Math.random() > 0.5) {
                    return ((Integer[]) values.get(2))[1];
                } else if (Math.random() > 0.5) {
                    return ((Integer[]) values.get(1))[1];
                } else {
                    return ((Integer[]) values.get(0))[1];
                }
            } else {
                //Randomly select field of two maximums
                if (Math.random() > 0.5) {
                    return ((Integer[]) values.get(2))[1];
                } else {
                    return ((Integer[]) values.get(1))[1];
                }
            }
        } else { //Just return field of maximum value
            return ((Integer[]) values.get(2))[1];
        }
    }

    /**
     * Class doesn't store any information, so it doesn't need to reset anything
     */
    @Override
    public void reset() {

    }

    /**
     * Method returns my email.
     *
     * @return String that stores my email
     */
    @Override
    public String getEmail() {
        return "m.gudkov@innopolis.university";
    }

}
