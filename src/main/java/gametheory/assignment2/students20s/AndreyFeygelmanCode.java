package gametheory.assignment2.students20s;

public class AndreyFeygelmanCode implements gametheory.assignment2.Player {
    /**
     * will we go on field with higher
     * or lower id when avoiding fight
     */
    boolean goMax;
    /**
     * defauld value for goMax
     */
    int startGoMax;
    /**
     * our last move
     */
    int lastMove;
    /**
     * on which field we will go when xA=xB=xC and we are avoiding fight
     */
    int eq;
    /**
     * on which field we will go when xA=xB=xC and we are looking for fight
     */
    int opEq;
    /**
     * previous state of the field
     */
    int[] prevField;
    /**
     * Controls the verbosity when calculating
     */
    boolean verbose;
    /**
     * minimal xA+xB+xC to start avoiding fight
     */
    int minAvoid;

    /**
     * will we go on field with higher
     * or lower id when looking for fight
     */
    boolean opGoMax;

    /**
     * Main constructor that will be called from the main program
     */
    public AndreyFeygelmanCode() {
        this(4, false);
    }

    /**
     * @param minAvoid minimal xA+xB+xC to start avoiding fight
     * @param verbose  Controls the verbosity when calculating
     */
    AndreyFeygelmanCode(int minAvoid, boolean verbose) {
        this.minAvoid = minAvoid;
        this.verbose = verbose;
        startGoMax = -1;
    }

    /**
     * sets attributes to default values
     */
    public void reset() {
        if (startGoMax == -1) {
            goMax = !(Math.random() < 0.5);
        } else goMax = (startGoMax != 0);

        lastMove = -2;

        eq = 0;
        opEq = 0;
        opGoMax = !goMax;
        prevField = new int[]{-1, -1, -1};
        if (verbose) System.out.println(" goMax=" + goMax + " eq=" + (eq + 1));
    }

    /**
     * Calculates move based on parameters
     *
     * @param goMax whether we will go to field with higher or lower index if their value are the same
     * @param eq    on which field we will go if all the values are the same
     * @param field array {xA, xB, xC}
     * @return the move 0 for A, 1 for B and 2 for C fields
     */
    private int calculateMove(boolean goMax, int eq, int[] field) {
        int l;
        int m;
        int s;
        if (field[0] >= field[1] && field[0] >= field[2]) {
            l = 0;
            if (field[1] >= field[2]) {
                m = 1;
                s = 2;
            } else {
                m = 2;
                s = 1;
            }
        } else if (field[1] >= field[0] && field[1] >= field[2]) {
            l = 1;
            if (field[0] >= field[2]) {
                m = 0;
                s = 2;
            } else {
                m = 2;
                s = 0;
            }
        } else {
            l = 2;
            if (field[0] >= field[1]) {
                m = 0;
                s = 1;
            } else {
                m = 1;
                s = 0;
            }
        }

        if (field[s] == field[m] && field[m] == field[l]) {
            // A=B=C
            return eq;
        } else if (field[l] > field[m] && field[m] > field[s]) {
            // A>B>C
            return l;
        } else if (field[l] == field[m]) {
            // A=B>C
            if (goMax) return Math.max(l, m);
            else return Math.min(l, m);
        } else {
            // A>B=C
            return l;
        }
    }


    /**
     * Studies opponents move to adjust attributes
     * then calculates and return our move
     *
     * @param opponentLastMove the last move of the opponent
     *                         varies from 0 to 3
     *                         (0 – if this is the first move)
     * @param xA               the argument X for a field A
     * @param xB               the argument X for a field B
     * @param xC               the argument X for a field C
     * @return our move
     */
    public int move(int opponentLastMove, int xA, int xB, int xC) {
        int opMove = opponentLastMove - 1;
        int[] field = new int[]{xA, xB, xC};
        int prevSum = prevField[0] + prevField[1] + prevField[2];

        if (prevField[0] == prevField[1] && prevField[1] == prevField[2]) {

            // decent assumption I assume
            if (prevField[0] == 1) {
                if (opMove == 1) opGoMax = false;
                else opGoMax = true;
            }

            if (opMove != -1)
                if (Math.random() < 0.5) opEq = opMove;
            if (opMove != -1 && eq == opMove) {
                double rnd = Math.random();
                if (rnd < 1.0 / 3) eq = 0;
                else if (rnd < 2.0 / 3) eq = 1;
                else eq = 2;
                if (verbose) System.out.println("we have same eq, changing to eq=" + (eq + 1));
            }
        } else {
            int opMax = calculateMove(true, -1, prevField);
            int opMin = calculateMove(false, -1, prevField);
            if (opMax != opMin) {
                // if we can conclude their strategy
                if (opMove == opMax) {
                    // if they are "max" we become "min" with prob. 50%
                    if (prevSum < minAvoid) {
                        if (Math.random() < 0.5) opGoMax = true;
                    } else if (Math.random() < 0.5) {
                        goMax = false;
                        if (verbose) System.out.println("op max, we change to min");
                    } else {
                        if (verbose) System.out.println("op max, we stay max");
                    }

                } else if (opMove == opMin) {
                    // if they are "min" we become "max" with prob. 50%
                    if (prevSum < minAvoid) {
                        if (Math.random() < 0.5) opGoMax = false;
                    } else if (Math.random() < 0.5) {
                        goMax = true;
                        if (verbose) System.out.println("op min, we change to max");
                    } else {
                        if (verbose) System.out.println("op min, we stay min");
                    }

                }
            }
        }

        int result;
        if (field[0] + field[1] + field[2] < minAvoid)
            result = calculateMove(opGoMax, opEq, field);
        else
            result = calculateMove(goMax, eq, field);

        lastMove = result;
        prevField = new int[]{field[0], field[1], field[2]};
        if (verbose) System.out.println();
        return result + 1;
    }

    /**
     * @return my email
     */
    public String getEmail() {
        return "a.feygelman@innopolis.university";

    }
}
