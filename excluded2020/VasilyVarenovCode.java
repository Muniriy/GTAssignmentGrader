package gametheory.assignment2.excluded2020;

import gametheory.assignment2.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

class MyPlayer implements Player {

  int getBestMove(int xA, int xB, int xC) {
    List<Move> moves = new ArrayList<>();
    moves.add(new Move(1, xA));
    moves.add(new Move(2, xB));
    moves.add(new Move(3, xC));
    moves.sort((Move m1, Move m2) -> Integer.compare(m2.points, m1.points));

    if (moves.get(0).points == moves.get(2).points)
      return ThreadLocalRandom.current().nextInt(1, 4);
    if (moves.get(0).points == moves.get(1).points){
      return moves.get(ThreadLocalRandom.current().nextInt(0, 2)).id;}
    return moves.get(0).id;
  }

  private class Move {
    public int points;
    int id;

    public Move(int id, int points) {
      this.id = id;
      this.points = points;
    }
  }



}

public class VasilyVarenovCode extends MyPlayer {
  @Override
  public void reset() {
  }

  @Override
  public int move(int opponentLastMove, int xA, int xB, int xC) {
    return this.getBestMove(xA, xB, xC);
  }

  @Override
  public String getEmail() {
    return "v.varenov@innopolis.ru";
  }
}
