package gametheory.assignment2.students2020;

import gametheory.assignment2.Player;

public class AntonTimchenkoCode implements Player {

    private static final String EMAIL = "a.timchenko@innopolis.ru";

    @Override
    public String getEmail() {
        return EMAIL;
    }

    @Override
    public void reset() {

    }

    @Override
    public int move(int opponentLastMove, int xA, int xB, int xC) {
        if (xA >= xB && xA > xC) {
            return 1;
    } else if (xB > xA && xB > xC) {
      return 2;
    } else {
      return 3;
    }
  }
}
