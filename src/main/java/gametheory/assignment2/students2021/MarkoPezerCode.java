/**
 * Game Theory: Assignment 2
 * Student: Marko Pezer (BS18-SE01)
 * MarkoPezerCode.java
 */

package gametheory.assignment2.students2021;

import gametheory.assignment2.Player;

/**
 * Player who always moves to the field with the highest value.
 * If there are more than one fields with highest value, move to random field
 */
public class MarkoPezerCode implements Player {

  /**
   * Reset player before next match
   */
  public void reset() {
    return;
  }

  /**
   * This method returns move to field with the highest value
   *
   * @param opponentLastMove last move of the opponent
   * @param xA               argument X for field A
   * @param xB               argument X for field B
   * @param xC               argument X for field C
   * @return move of the player
   */
  public int move(int opponentLastMove, int xA, int xB, int xC) {
    /* If there is a single field with the highest value */
    if (xA > xB && xA > xC) return 1;
    else if (xB > xA && xB > xC) return 2;
    else if (xC > xA && xC > xB) return 3;
    /* If there are two fields with the highest value
       (randomly choose one) */
    else if (xA == xB && xA > xC) {
      if (Math.random() >= 0.5) return 1;
      else return 2;
    } else if (xA == xC && xA > xB) {
      if (Math.random() >= 0.5) return 1;
      else return 3;
    } else if (xB == xC && xB > xA) {
      if (Math.random() >= 0.5) return 2;
      else return 3;
    }
    /* If all three fields have the same value
       (randomly choose one) */
    else return (int) (Math.random() * 3 + 1);
  }

  /**
   * This method returns your IU email
   *
   * @return your email
   */
  public String getEmail() {
    return "m.pezer@innopolis.university";
  }
}
