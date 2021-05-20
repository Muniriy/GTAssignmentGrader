package gametheory.assignment2.students20s;

import gametheory.assignment2.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * Class implements Smart greedy player.
 * if all cells X’s are higher or equal to 8: then choose
 * minimal among them, else behave like Random greedy.
 *
 * @author Ivan Abramov
 * @version 1.0
 */
public class IvanAbramovCode implements Player {

    /**
     * Get indexes of largest elements
     *
     * @param array - array of elements
     * @return array of indexes
     */
    public static ArrayList<Integer> getIndexesOfLargest(ArrayList<Integer> array) {
        if (array == null || array.size() == 0) {
            // null or empty array
            return new ArrayList<>();
        }

        ArrayList<Integer> indexes = new ArrayList<>();
        int largest = 0;
        indexes.add(largest);

        for (int i = 1; i < array.size(); i++) {
            if (array.get(i) > array.get(largest)) {
                largest = i;
                indexes.clear();
                indexes.add(i);
            } else if (array.get(i).equals(array.get(largest))) {
                indexes.add(i);
            }
        }
        return indexes;
    }

    @Override
    public void reset() {
    }

    @Override
    public int move(int opponentLastMove, int xA, int xB, int xC) {
        ArrayList<Integer> elements = new ArrayList<>();
        elements.add(xA);
        elements.add(xB);
        elements.add(xC);

        if (xA >= 8 && xB >= 8 && xC >= 8) {
            // if all fields X are greater then 8, than choose minimal among them
            return elements.indexOf(Collections.min(elements)) + 1;
        } else {
            // if not all fields X are greater than 10, then choose random maximum cell
            ArrayList<Integer> maxElementsIndexes = getIndexesOfLargest(elements);
            int random = new Random().nextInt(maxElementsIndexes.size());

            return maxElementsIndexes.get(random) + 1;
        }
    }

    @Override
    public String getEmail() {
        return "I.abramov@innopolis.university";
    }
}
