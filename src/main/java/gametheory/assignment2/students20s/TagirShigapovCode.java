package gametheory.assignment2.students20s;

import gametheory.assignment2.Player;

public class TagirShigapovCode implements Player {

    public double formula(int x) {
        return (10 * Math.exp(x)) / (10 + Math.exp(x));
    }

    @Override
    public void reset() {
    }

    @Override
    public int move(int opponentLastMove, int xA, int xB, int xC) {
        // I decided to implement fastidious algorithm
        // due to the fact that my moose don't want to eat
        // where other mooses already ate last turn
        if (opponentLastMove == 1)
            if (xB > xC) {
                return 2;
            } else {
                return 3;
            }
        else if (opponentLastMove == 2)
            if (xA > xC) {
                return 1;
            } else {
                return 3;
            }
        else {
            if (xA > xB) {
                return 1;
            } else {
                return 2;
            }
        }
    }

    @Override
    public String getEmail() {
        return "t.shigapov@innopolis.university";
    }
}

class RandomStrat implements Player {
    @Override
    public void reset() {

    }

    @Override
    public int move(int opponentLastMove, int xA, int xB, int xC) {

        return (int) (Math.random() * 2 + 1);
    }

    @Override
    public String getEmail() {
        return "t.shigapov@innopolis.university";
    }
}

class Greedy implements Player {

    public double formula(int x) {
        return (10 * Math.exp(x)) / (10 + Math.exp(x));
    }

    @Override
    public void reset() {
    }

    @Override
    public int move(int opponentLastMove, int xA, int xB, int xC) {
        if (xA > xB && xA > xC) {
            return 1;
        } else if (xB > xA && xB > xC) {
            return 2;
        } else {
            return 3;
        }
    }

    @Override
    public String getEmail() {
        return "t.shigapov@innopolis.university";
    }
}

class Lazy implements Player {

    int position;

    @Override
    public void reset() {
        position = (int) (Math.random() * 2 + 1);
    }

    @Override
    public int move(int opponentLastMove, int xA, int xB, int xC) {
        Integer[] fields = {xA, xB, xC};
        if (fields[position] != 0) {
            return position;
        } else {
            int max_index = 0;
            for (int i = 0; i < fields.length; i++) {
                max_index = fields[i] > fields[max_index] ? i : max_index;
            }
            return max_index + 1;
        }
    }

    @Override
    public String getEmail() {
        return "t.shigapov@innopolis.university";
    }
}



