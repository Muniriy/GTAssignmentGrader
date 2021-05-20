package gametheory.assignment2.students2021;
import gametheory.assignment2.Player;

class MaximKorsunovCode implements Player {
    Integer defaultCell = null;
    Integer neutralCell = null;
    Integer prevMove = null;
    Integer waiting = 0;
    Boolean isOnNeutral = false;
    Boolean isDefection = false;

    /**
     * To begin a new game, reset all previous variables
     */
    public void reset() {
        defaultCell = null;
        neutralCell = null;
        prevMove = null;
        waiting = 0;
        isOnNeutral = false;
        isDefection = false;
    }
    
    public void Player () {
        reset();
    }

    public static int getRandomNumber (int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

    public int defection (int opponentLastMove, int xA, int xB, int xC) {
        if (xA == xB && xB == xC)
            return getRandomNumber(1, 3);

        if (xA > xB) {
            if (xA > xC)
                return 1;
            else if (xA == xC)
                return getRandomNumber(0, 1) == 1 ? 1 : 3;
            else
                return 3;
        } else {
            if (xB > xC)
                return 2;
            else if (xB == xC)
                return getRandomNumber(2, 3);
            else
                return 3;
        }
    }
    
    public int move (int opponentLastMove, int xA, int xB, int xC) {
        if (prevMove == null) {
            prevMove = getRandomNumber(1, 3);
            return prevMove;
        }

        if (defaultCell == null) {
            if (opponentLastMove == prevMove) {
                prevMove = getRandomNumber(1, 3);
                return prevMove;
            } else {
                defaultCell = prevMove;
                neutralCell = (1 + 2 + 3) - (defaultCell + opponentLastMove);
                waiting++;
                return defaultCell;
            }
        } else {
            if (opponentLastMove == defaultCell || isDefection) {
                isDefection = true;
                return defection(opponentLastMove, xA, xB, xC);
            } else {
                if (waiting >= 7) {
                    waiting = 0;
                    if (isOnNeutral) {
                        isOnNeutral = false;
                        return defaultCell;
                    } else {
                        isOnNeutral = true;
                        return neutralCell;
                    }
                } else {
                    waiting++;
                    return isOnNeutral ? neutralCell : defaultCell;
                }
            }
        }
    }

    public String getEmail () {
        return "m.korsunov@innopolis.university";
    }
}