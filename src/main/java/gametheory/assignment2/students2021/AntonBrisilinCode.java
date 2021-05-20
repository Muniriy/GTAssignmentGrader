package gametheory.assignment2.students2021;

import gametheory.assignment2.Player;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

/**
 * My player chosen for tournament
 */
public class AntonBrisilinCode implements Player {
    // States
    static final int beginning = 0;
    static final int handshake1 = 1;
    static final int handshake2 = 2;
    static final int play = 3;
    static final int tricked = 4;

    // N
    static final int treshold = 6;

    int state;
    int myPos;
    int myField;

    /**
     * Method that converts its three arguments to List<FieldInfo>. Just for convenience
     */
    public static List<FieldInfo> createFields(int xA, int xB, int xC) {
        List<FieldInfo> turns = new ArrayList<>();
        turns.add(new FieldInfo(1, xA));
        turns.add(new FieldInfo(2, xB));
        turns.add(new FieldInfo(3, xC));
        return turns;
    }

    @Override
    public void reset() {
        state = beginning;
    }

    @Override
    public String getEmail() {
        return "a.brisilin@innopolis.university";
    }

    @Override
    public int move(int opponentLastMove, int xA, int xB, int xC) {
        List<Integer> fields = new ArrayList<>();
        fields.add(xA);
        fields.add(xB);
        fields.add(xC);
        switch (state) {
            case beginning:
                state = handshake1;
                myPos = 2;
                return 2;
            case handshake1:
                state = handshake2;
                if (opponentLastMove != myPos) {
                    state = tricked;
                }
                myPos = (new Random().nextInt() % 2 == 0) ? 1 : 3;
                return myPos;
            case handshake2:
                if (myPos == opponentLastMove) {
                    myPos = (new Random().nextInt() % 2 == 0) ? 1 : 3;
                    return myPos;
                }
                state = play;
                myField = myPos;
                myPos = 2;
                return 2;
            case play:
                if (opponentLastMove == myField) {
                    state = tricked;
                    return (myField == 1) ? 3 : 1;
                }
                if (fields.get(myField - 1) >= treshold) {
                    return myField;
                }
                return 2;
            case tricked:
                List<FieldInfo> turns = createFields(xA, xB, xC);
                return RandomExt.randomItem(chooseBest(turns)).position;
        }
        return 2;
    }

    private List<FieldInfo> chooseBest(List<FieldInfo> fields) {
        fields.sort(Comparator.comparingInt(FieldInfo::getX).reversed());
        ArrayList<FieldInfo> ret = new ArrayList<>();
        ret.add(fields.get(0));
        for (int i = 1; i < fields.size(); i++) {
            if (fields.get(i).getX() == fields.get(i - 1).getX()) {
                ret.add(fields.get(i));
            }
        }
        return ret;
    }
}

/**
 * Class for convenient representation of fields
 */
class FieldInfo {
    int position;

    private int X;

    public FieldInfo(int position, int x) {
        this.position = position;
        X = x;
    }

    public int getX() {
        assert X >= 0;
        return X;
    }

    public void setX(int x) {
        X = Math.max(x, 0);
    }
}

/**
 * Functions that could be part of java.util.Random, but they are not
 */
class RandomExt {
    private static final Random random = new Random();

    static <T> T randomItem(List<T> list) {
        int index = random.nextInt(list.size());
        return list.get(index);
    }
}