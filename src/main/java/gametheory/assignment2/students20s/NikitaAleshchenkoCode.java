package gametheory.assignment2.students20s;

import gametheory.assignment2.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class NikitaAleshchenkoCode implements Player {
    private List<Integer> opponentMoves;
    private List<Integer> myMoves;
    private PeacefulStrategy peacefulS;
    private RandomStrategy randomS;
    private GreedyStrategy greedyS;
    private int fieldA;
    private int fieldB;
    private int fieldC;

    /**
     * Constructor of an agent
     */
    public NikitaAleshchenkoCode() {
        this.opponentMoves = new ArrayList();
        this.myMoves = new ArrayList();
        this.peacefulS = new PeacefulStrategy(10, 5);
        this.randomS = new RandomStrategy();
        this.greedyS = new GreedyStrategy();
        this.fieldA = 1;
        this.fieldB = 1;
        this.fieldC = 1;
    }


    /**
     * Implementation of methods described in Player interface
     */

    public void reset() {
        this.opponentMoves.clear();
        this.myMoves.clear();
        this.peacefulS.clear();
        this.randomS.clear();
        this.fieldA = 1;
        this.fieldB = 1;
        this.fieldC = 1;
    }

    public int move(int opponentLastMove, int xA, int xB, int xC) {
        logOpponentMove(opponentLastMove);

        this.fieldA = xA;
        this.fieldB = xB;
        this.fieldC = xC;

        Integer nextM = moveHelper();

        myMoves.add(nextM);

        return nextM.intValue();
    }

    public String getEmail() {
        return "n.aleschenko@innopolis.university";
    }


    /**
     * Helper funtions
     */

    private Integer moveHelper() {
        if (peacefulS.isPeacefulGame()) return peacefulS.nextMove();
        return greedyS.nextMove();
    }

    private void logOpponentMove(int move) {
        if (move != 0) {
            this.opponentMoves.add(Integer.valueOf(move));
        }
    }


    /**
     * Funtions for a testing purpose
     */

    public List<Integer> getOpponentMoves() {
        return this.opponentMoves;
    }

    public List<Integer> getMyMoves() {
        return this.myMoves;
    }


    /**
     * Interface for a Strategy
     */

    interface Strategy {
        Integer nextMove();

        void clear();
    }

    class PeacefulStrategy {
        private Integer feedingField;
        private Integer restField;
        private Integer initWait;
        private Integer eat;
        /**
         * Variable which represents state of peaceful strategy
         * 0 for initialWait
         * 1 for rest
         * 2 for eat
         */
        private Integer state;
        private Integer tick;

        public PeacefulStrategy(Integer initWait, Integer eat) {
            this.feedingField = 0;
            this.restField = 0;
            this.initWait = initWait;
            this.eat = eat;
            this.tick = 0;
            this.state = 0;
        }

        public boolean isPeacefulGame() {
            List<Integer> player1 = getOpponentMoves();
            List<Integer> player2 = getMyMoves();

            int minLength = Math.min(player1.size(), player2.size());

            Integer p1Feed = 0;
            Integer p2Feed = 0;
            Integer rest = 0;

            for (int i = 0; i < minLength; i++) {
                Integer p1Move = player1.get(i);
                Integer p2Move = player2.get(i);

                if (p1Feed + p2Feed + rest == 0 && p1Move != p2Move) {
                    p1Feed = p1Move;
                    p2Feed = p2Move;
                    rest = 6 - p1Move - p2Move;

                    continue;
                }

                if (p1Feed + p2Feed + rest == 0) continue;

                if (p1Feed == p2Move || p2Feed == p1Move) return false;
            }

            return true;
        }

        public Integer nextMove() {
            if (getOpponentMoves().size() == 0 || getMyMoves().size() == 0) {
                return randomS.nextMove();
            }

            if (this.feedingField + this.restField == 0) {
                Integer lastOpMove = getOpponentMoves().get(getOpponentMoves().size() - 1);
                Integer lastMyMove = getMyMoves().get(getMyMoves().size() - 1);

                if (lastOpMove != lastMyMove) {
                    this.feedingField = lastMyMove;
                    this.restField = 6 - lastOpMove - lastMyMove;
                    return this.restField;
                } else {
                    return randomS.nextMove();
                }
            }

            if (this.state == 0) {
                if (this.tick == this.initWait - 1) {
                    this.tick = 0;
                    this.state = 2;
                    return this.feedingField;
                }
                this.tick++;
                return this.restField;
            }

            if (this.state == 1) {
                if (this.tick == this.eat - 1) {
                    this.tick = 0;
                    this.state = 2;
                    return this.feedingField;
                }
                this.tick++;
                return this.restField;
            }

            if (this.state == 2) {
                if (this.tick == this.eat - 1) {
                    this.tick = 0;
                    this.state = 1;
                    return this.restField;
                }
                this.tick++;
                return this.feedingField;
            }

            return this.feedingField;
        }

        public void clear() {
            this.feedingField = 0;
            this.restField = 0;
            this.tick = 0;
            this.state = 0;
        }
    }

    class GreedyStrategy implements Strategy {
        private Random randGen;

        public GreedyStrategy() {
            this.randGen = new Random();
        }

        public Integer nextMove() {
            if (fieldA > fieldB && fieldA > fieldC) return 1;
            if (fieldB > fieldC && fieldB > fieldA) return 2;
            if (fieldC > fieldA && fieldC > fieldB) return 3;

            if (fieldB > fieldA && fieldC > fieldA && fieldA > 5) return 1;
            if (fieldA > fieldB && fieldC > fieldB && fieldB > 5) return 2;
            if (fieldA > fieldC && fieldB > fieldC && fieldC > 5) return 3;

            if (fieldA > fieldC && fieldB > fieldC)
                return Integer.valueOf(this.randGen.nextInt(2) + 1);
            if (fieldB > fieldA && fieldC > fieldA)
                return Integer.valueOf(this.randGen.nextInt(2) + 2);
            if (fieldC > fieldB && fieldA > fieldB)
                return Integer.valueOf(this.randGen.nextInt(2) * 2 + 1);

            return Integer.valueOf(this.randGen.nextInt(3) + 1);
        }

        public void clear() {
        }
    }

    class RandomStrategy implements Strategy {
        private Random randGen;

        public RandomStrategy() {
            this.randGen = new Random();
        }

        public Integer nextMove() {
            return Integer.valueOf(this.randGen.nextInt(3) + 1);
        }

        public void clear() {
        }
    }
}
