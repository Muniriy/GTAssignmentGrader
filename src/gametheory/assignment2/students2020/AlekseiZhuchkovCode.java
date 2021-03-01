package gametheory.assignment2.students2020;

import gametheory.assignment2.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class AlekseiZhuchkovCode implements Player {
    @Override
    public void reset() {

    }

    @Override
    public int move(int opponentLastMove, int xA, int xB, int xC) {
        Random r = new Random();
        List<Integer> maxMoves = getFields(xA, xB, xC, getMaxMove(xA, xB, xC));
        return maxMoves.get(r.nextInt(maxMoves.size())); // Get random field with maximum value
    }

    /**
     * Get all fields that equals or not (depend on 'include' param) input parameter 'value'
     *
     * @param xA    Value of first field
     * @param xB    Value of second field
     * @param xC    Value of third field
     * @param value Value of fields to return
     * @return List of fields those value equals or not (depend on 'include' param) input parameter 'value'
     */
    List<Integer> getFields(int xA, int xB, int xC, int value) {
        HashMap<Integer, Integer> fields = new HashMap<>();
        fields.put(1, xA);
        fields.put(2, xB);
        fields.put(3, xC);

        fields.values().removeIf(v -> !v.equals(value)); // Remove fields that not equal input 'value'
        return new ArrayList<>(fields.keySet());
    }

    /**
     * Get maximum values among all the fields
     *
     * @param xA Value of first field
     * @param xB Value of second field
     * @param xC Value of third field
     * @return Maximum values among all the fields
     */
    int getMaxMove(int xA, int xB, int xC) {
        return Math.max(xA, Math.max(xB, xC));
    }

    @Override
    public String getEmail() {
        return "a.zhuchkov@innopolis.ru";
    }
}
