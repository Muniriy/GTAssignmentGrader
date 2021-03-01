package gametheory.assignment2.students2020;

import gametheory.assignment2.Player;

import java.util.ArrayList;
import java.util.Random;


public class DenisLevkovetsCode implements Player {
    // Counter of how many times opponent chose max in the row
    int maxStike = 0;
    //Save previous max fields to check the opponentLastMove
    ArrayList<Integer> prevMaxs = new ArrayList<Integer>();
    //Description will be below
    public String mode = "tournament";

    @Override
    public void reset() {
        maxStike = 0;
        prevMaxs = new ArrayList<Integer>();
    }

    @Override
    public String getEmail() {
        return "d.levkovets@innopolis.ru";
    }

    @Override
    public int move(int opponentLastMove, int xA, int xB, int xC) {
        int max = Math.max(Math.max(xA, xB), xC);
        Random random = new Random();

        // The purpose of this 'if' block is to detect when the strike will become equal to 3 (trick of this strategy)
        if (maxStike < 3) {
            if (prevMaxs.contains(Integer.valueOf(opponentLastMove))) maxStike++;
            else maxStike = 0;
        }

        // PvP mode is just pure Greedy algorithm when we choose the max field
        if (mode.equals("PvP")) {
            // Find the max X from arguments
            ArrayList<Integer> maxFields = new ArrayList<Integer>();

            // Fill list of fields with max X
            if (xA == max) maxFields.add(1);
            if (xB == max) maxFields.add(2);
            if (xC == max) maxFields.add(3);

            // If we have more then 1 field with maximum X, then Agent will choose the field, which wasn't chosen by opponent
            // Because what's the reason to find another place for Moose, if in the current field everything is okey :)
            if (maxFields.size() > 1) maxFields.remove(Integer.valueOf(opponentLastMove));


            // From final list of fields choose random one or just choose a max
            int newMove = maxFields.size() > 1 ? maxFields.get(random.nextInt(maxFields.size() - 1)) : maxFields.get(0);

            return newMove;
        }
        // In this mode it works as greedy until opponent get 3 maxs in the row and from this moment agent will choose middle value
        // Details are in the report
        else if (mode.equals("tournament")) {
            // Find the max X from arguments
            ArrayList<Integer> maxFields = new ArrayList<Integer>();

            // Fill list of fields with max X
            if (xA == max) maxFields.add(1);
            if (xB == max) maxFields.add(2);
            if (xC == max) maxFields.add(3);

            prevMaxs = maxFields;

            // Simple greedy
            if (maxStike < 3) {

                // From list of fields choose random one
                int newMove = maxFields.size() > 1 ? maxFields.get(random.nextInt(maxFields.size() - 1)) : maxFields.get(0);

                return newMove;
            }
            // Return middle
            else {

                if (maxFields.size()>1) return maxFields.get(random.nextInt(maxFields.size() - 1));

                int min = Math.min(Math.min(xA, xB), xC);

                if (xA != min && xA != max) return 1;
                if (xB != min && xB != max) return 2;
                if (xC != min && xC != max) return 3;
            }
        }
        // Unreachable line with right using :) Just write correct modes names
        return opponentLastMove;
    }
}
