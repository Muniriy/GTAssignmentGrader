/*
 * LevChelyadinovCode
 *
 * No license. All rights reserved.
 */

package gametheory.assignment2.students2021;

import gametheory.assignment2.Player;

import java.util.Random;


/**
 * The player class
 *
 * @author Lev Chelyadinov
 * @version 1.0.0 11 Mar 2021
 */
public class LevChelyadinovCode implements Player {
  public static final int STEPPING_AWAY = 0;
  public static final int WAITING_FOR_GROWTH = 1;
  public static final int FARMING = 2;
  public static final int ANGRY = 3;

  private static final int satisfactoryGrowth = 2;

  private int state;
  private int position;
  private int myHome;
  private int theirHome;
  private int buffer;
  private Random randomGenerator;

  public LevChelyadinovCode() {
    reset();
  }

  /**
   * Return the index of a region that is different from
   * the other two passed ones.
   *
   * @param region1 some region
   * @param region2 some other region
   * @return the third region
   */
  private static int otherFrom(int region1, int region2) {
    return (1 + 2 + 3) - region1 - region2;
  }

  /**
   * Return the X-value of the region at a given index.
   *
   * @param regionIndex the index of the desired region
   * @param xA          the X-value at the region A
   * @param xB          the X-value at the region B
   * @param xC          the X-value at the region C
   * @return the X-value on the desired region
   */
  private static int xAt(int regionIndex, int xA, int xB, int xC) {
    int[] xs = {xA, xB, xC};
    return xs[regionIndex - 1];
  }

  /**
   * This method is called to reset the agent before the match
   * with another player containing several rounds
   */
  public void reset() {
    state = STEPPING_AWAY;
    position = 0;
    myHome = -1;
    theirHome = -1;
    randomGenerator = new Random();
  }

  /**
   * This method returns the move of the player based on
   * the last move of the opponent and X values of all fields.
   * Initially, X for all fields is equal to 1 and last opponent
   * move is equal to 0
   *
   * @param opponentLastMove the last move of the opponent
   *                         varies from 0 to 3
   *                         (0 – if this is the first move)
   * @param xA               the argument X for a field A
   * @param xB               the argument X for a field B
   * @param xC               the argument X for a field C
   * @return the move of the player can be 1 for A, 2 for B
   * and 3 for C fields
   */
  public int move(int opponentLastMove, int xA, int xB, int xC) {
    if (state == STEPPING_AWAY) {
      if (position != opponentLastMove) {
        state = WAITING_FOR_GROWTH;
        myHome = position;
        theirHome = opponentLastMove;
        buffer = otherFrom(myHome, theirHome);
      } else {
        position = 1 + randomGenerator.nextInt(3);
        return position;
      }
    }

    if (state == WAITING_FOR_GROWTH) {
      if (opponentLastMove == myHome) {
        state = ANGRY;
      } else {
        if (xAt(myHome, xA, xB, xC) < satisfactoryGrowth) {
          position = buffer;
        } else {
          state = FARMING;
          position = myHome;
        }
        return position;
      }
    }

    if (state == FARMING) {
      if (opponentLastMove == myHome) {
        state = ANGRY;
      } else {
        if (position == myHome) {
          position = buffer;
        } else {
          position = myHome;
        }
        return position;
      }
    }

    return indexOfMax(xA, xB, xC);
  }

  /**
   * Returns the one-based index of the maximum number among the three.
   * If there are several maximum numbers, chooses randomly among them
   *
   * @param num1 the first number
   * @param num2 the second number
   * @param num3 the third number
   * @return the one-based index of the maximum number among the three
   */
  private int indexOfMax(int num1, int num2, int num3) {
    if (num1 > num2) {
      if (num1 > num3) {
        return 1;
      } else if (num1 == num3) {
        return randomChoice(new int[]{1, 3});
      } else {
        return 3;
      }
    } else if (num1 == num2) {
      if (num1 > num3) {
        return randomChoice(new int[]{1, 2});
      } else if (num1 == num3) {
        return randomChoice(new int[]{1, 2, 3});
      } else {
        return 3;
      }
    } else {
      if (num2 > num3) {
        return 2;
      } else if (num2 == num3) {
        return randomChoice(new int[]{2, 3});
      } else {
        return 3;
      }
    }
  }

  /**
   * Returns a random item of a given array
   *
   * @param numbers an array of integers
   * @return a random element from that array
   */
  private int randomChoice(int[] numbers) {
    return numbers[randomGenerator.nextInt(numbers.length)];
  }

  /**
   * This method returns your IU email
   *
   * @return your email
   */
  public String getEmail() {
    return "l.chelyadinov@innopolis.university";
  }
}
