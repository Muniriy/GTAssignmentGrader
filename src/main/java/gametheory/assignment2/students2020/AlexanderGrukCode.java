package gametheory.assignment2.students2020;

import gametheory.assignment2.Player;

import java.util.*;

// A - 1, B - 2, C - 3
// Starts with cooperate, then copy movements
class Move {
    public static Map<Integer, Integer> checkMoves(List<Integer> regions) {
        HashMap<Integer, Integer> regToValue = new HashMap<>();

        for (int i = 0; i < regions.size(); i++) {
            regToValue.put(i + 1, regions.get(i));
        }

        List<Map.Entry<Integer, Integer>> listToSort = new LinkedList<Map.Entry<Integer, Integer>>(regToValue.entrySet());

        Collections.sort(listToSort, Collections.reverseOrder(new Comparator<Map.Entry<Integer, Integer>>() {
                                                                  public int compare(Map.Entry<Integer, Integer> o1, Map.Entry<Integer, Integer> o2) {
                                                                      return (o1.getValue()).compareTo(o2.getValue());
                                                                  }
                                                              }
        ));

        Map<Integer, Integer> sortedRegToValue = new LinkedHashMap<Integer, Integer>();
        for (Map.Entry<Integer, Integer> entry : listToSort) {
            sortedRegToValue.put(entry.getKey(), entry.getValue());
        }

        return sortedRegToValue;
    }
}

public class AlexanderGrukCode implements Player {

    private int movesN;
    private int myMove;
    private boolean wasIDominated = false;
    private Map<Integer, Integer> allMoves = new HashMap<>();

    public AlexanderGrukCode() {
        this.movesN = 0;
        this.myMove = 1;
        this.wasIDominated = false;
        this.allMoves = new HashMap<>();
    }

    public void reset() {
        this.movesN = 0;
        this.myMove = 1;
        this.wasIDominated = false;
        this.allMoves = new HashMap<>();
    }

    public boolean wasCollision(int opponentLastMove) {
        return this.myMove == opponentLastMove;
    }

    // a, b and c are fields in from min to max
    // a = b = c - no method - the begining only situation
    // a < b < c - allDiffers
    // a < b = c - dominatingEqualAverage
    // a = b < c - averageEqualDominated
    public boolean allDiffers () {
        if (((int) this.allMoves.values().toArray()[0]) > ((int) this.allMoves.values().toArray()[1])) {
            if (((int) this.allMoves.values().toArray()[1]) > ((int) this.allMoves.values().toArray()[2])) {
                return true;
            }
        }
        return false;
    }

    public boolean dominatingEqualAverage () {
        return ((int) this.allMoves.values().toArray()[0]) ==
                ((int) this.allMoves.values().toArray()[1]);
    }

    public boolean averageEqualDominated () {
        return ((int) this.allMoves.values().toArray()[1]) ==
                ((int) this.allMoves.values().toArray()[2]);
    }

    public int chooseRandomlyFromAll() {
        int rn = new java.util.Random().nextInt(3)+1;
        return (int) this.allMoves.keySet().toArray()[rn-1];
    }

    public int chooseRandomlyFromTwoBest() {
        int rn = new java.util.Random().nextInt(2)+1;
        return (int) this.allMoves.keySet().toArray()[rn-1];
    }

    public int chooseRandomlyFromTwoWorst() {
        int rn = new java.util.Random().nextInt(2)+2;
        return (int) this.allMoves.keySet().toArray()[rn-1];
    }

    public int chooseDominating() {
        this.wasIDominated = true;
        return (int) this.allMoves.keySet().toArray()[0];
    }

    public int chooseAverage() {
        this.wasIDominated = false;
        return (int) this.allMoves.keySet().toArray()[1];
    }

    public int chooseDominated() {
        this.wasIDominated = false;
        return (int) this.allMoves.keySet().toArray()[2];
    }

    public int move(int opponentLastMove, int xA, int xB, int xC) {

        this.movesN++;
        if (this.movesN == 1) {
            this.allMoves = Move.checkMoves(new ArrayList<>(Arrays.asList(xA, xB, xC)));
            this.myMove = chooseRandomlyFromAll();

            return this.myMove;
        }
        else {
            if (wasCollision(opponentLastMove)) {
                if (this.wasIDominated) {
                    this.myMove = chooseAverage();
                    this.allMoves = Move.checkMoves(new ArrayList<>(Arrays.asList(xA, xB, xC)));
                    return myMove;
                }
                else {
                    this.myMove = chooseDominated();
                    this.allMoves = Move.checkMoves(new ArrayList<>(Arrays.asList(xA, xB, xC)));
                    return this.myMove;
                }
            }
            else {
                if (dominatingEqualAverage()) {
                    this.myMove = chooseRandomlyFromTwoBest();
                    this.allMoves = Move.checkMoves(new ArrayList<>(Arrays.asList(xA, xB, xC)));
                    return myMove;
                } else if (averageEqualDominated()) {
                    this.myMove = chooseRandomlyFromTwoWorst();
                    this.allMoves = Move.checkMoves(new ArrayList<>(Arrays.asList(xA, xB, xC)));
                    return myMove;
                }
            }
            this.myMove = chooseRandomlyFromAll();
            this.allMoves = Move.checkMoves(new ArrayList<>(Arrays.asList(xA, xB, xC)));
            return myMove;
        }
    }

    public String getEmail() { return "a.gruk@innopolis.ru";}
}

// Starts with cooperation and then copy opponent's move
class Copycat implements Player {

    private int movesN;
    private Map<Integer, Integer> allMoves;

    public Copycat() {
        this.movesN = 0;
        this.allMoves = new HashMap<>();
    }

    public void reset() {
        this.movesN = 0;
        this.allMoves = new HashMap<>();
    }

    public int move(int opponentLastMove, int xA, int xB, int xC) {

        int myMove;

        if (movesN == 0) {
            this.movesN++;
            java.util.Random rn = new java.util.Random();
            myMove = rn.nextInt(3) + 1;
//            System.out.println("My first turn. I choose random: " + myMove);

            this.allMoves = Move.checkMoves(new ArrayList<>(Arrays.asList(xA, xB, xC)));
            return myMove;
        } else {
            this.movesN++;

            if (opponentLastMove == (int) this.allMoves.keySet().toArray()[0]) {
                this.allMoves = Move.checkMoves(new ArrayList<>(Arrays.asList(xA, xB, xC)));
                myMove = (int) this.allMoves.keySet().toArray()[0];
//                System.out.println("My opposite player choose Domination: "
//                        + opponentLastMove + ". I copy that: " + myMove);

                return myMove;
            } else {
                this.allMoves = Move.checkMoves(new ArrayList<>(Arrays.asList(xA, xB, xC)));
                myMove = (int) this.allMoves.keySet().toArray()[1];
//                System.out.println("My opposite player choose Average: "
//                        + opponentLastMove + ". I copy that: " + myMove);

                return myMove;
            }
        }
    }

    public String getEmail() { return "a.gruk@innopolis.ru";}
}

class AllCooperate implements Player {

    private Map<Integer, Integer> allMoves;

    public AllCooperate() {
        this.allMoves = new HashMap<>();
    }

    public void reset() {
        this.allMoves = new HashMap<>();
    }

    public int move(int opponentLastMove, int xA, int xB, int xC) {
        int myMove;
        this.allMoves = Move.checkMoves(new ArrayList<>(Arrays.asList(xA, xB, xC)));
        myMove = (int) this.allMoves.keySet().toArray()[1];
//        System.out.println("I always cooperate. That's why I choose :" + myMove);
        return myMove;
    }

    public String getEmail() { return "a.gruk@innopolis.ru";}
}

class AllLoose implements Player {

    private Map<Integer, Integer> allMoves;

    public AllLoose() {
        this.allMoves = new HashMap<>();
    }

    public void reset() {
        this.allMoves = new HashMap<>();
    }

    public int move(int opponentLastMove, int xA, int xB, int xC) {
        int myMove;
        this.allMoves = Move.checkMoves(new ArrayList<>(Arrays.asList(xA, xB, xC)));
        myMove = (int) this.allMoves.keySet().toArray()[2];
//        System.out.println("I always get the worst option. That's why I choose :" + myMove);
        return myMove;
    }

    public String getEmail() { return "a.gruk@innopolis.ru";}
}

class AllCheat implements Player {

    private Map<Integer, Integer> allMoves;

    public AllCheat() {
        this.allMoves = new HashMap<>();
    }

    public void reset() {
        this.allMoves = new HashMap<>();
    }

    public int move(int opponentLastMove, int xA, int xB, int xC) {
        int myMove;
        this.allMoves = Move.checkMoves(new ArrayList<>(Arrays.asList(xA, xB, xC)));
        myMove = (int) this.allMoves.keySet().toArray()[0];
//        System.out.println("I always get the best option. That's why I choose :" + myMove);
        return myMove;
    }

    public String getEmail() { return "a.gruk@innopolis.ru";}
}

// Starts with cooperation until cheating. Then cheat
class Grudger implements Player {

    private int movesN;
    boolean shouldICheat;
    private Map<Integer, Integer> allMoves;
    private Map<Integer, Integer> prevMoves;

    public Grudger() {
        this.movesN = 0;
        this.shouldICheat = false;
        this.allMoves = new HashMap<>();
    }

    public void reset() {
        this.movesN = 0;
        this.shouldICheat = false;
        this.allMoves = new HashMap<>();
    }

    public int move(int opponentLastMove, int xA, int xB, int xC) {

        int myMove;

        if (movesN == 0) {
            this.movesN++;
            java.util.Random rn = new java.util.Random();
            myMove = rn.nextInt(3) + 1;
//            System.out.println("My first turn. I choose random: " + myMove);
            this.prevMoves = Move.checkMoves(new ArrayList<>(Arrays.asList(xA, xB, xC)));
            return myMove;
        } else {
            this.movesN++;

            if (opponentLastMove == (int) this.prevMoves.keySet().toArray()[0]) {
                shouldICheat = true;
            }

            if (shouldICheat) {
                this.prevMoves = Move.checkMoves(new ArrayList<>(Arrays.asList(xA, xB, xC)));
                myMove = (int) this.prevMoves.keySet().toArray()[0];
//                System.out.println("I was tricked. I will cheat: " + myMove);

                return myMove;
            } else {
                this.prevMoves = Move.checkMoves(new ArrayList<>(Arrays.asList(xA, xB, xC)));
                myMove = (int) this.prevMoves.keySet().toArray()[1];
//                System.out.println("They are honest. I will cooperate: " + myMove);

                return myMove;
            }
        }
    }

    public String getEmail() { return "a.gruk@innopolis.ru";}
}


// Cooperate, Cheat, Cooperate, Cooperate. Then like Copycat
class Detective implements Player {

    private int movesN;
    private Map<Integer, Integer> allMoves;

    public Detective() {
        this.movesN = 0;
    }

    public void reset() {
        this.movesN = 0;
    }

    public int move(int opponentLastMove, int xA, int xB, int xC) {

        int myMove;

        if (movesN == 0 || movesN == 2 || movesN == 3) {
            this.movesN++;
            this.allMoves = Move.checkMoves(new ArrayList<>(Arrays.asList(xA, xB, xC)));
            myMove = (int) this.allMoves.keySet().toArray()[1];
            return myMove;
        } else if (movesN == 1) {
            this.movesN++;
            this.allMoves = Move.checkMoves(new ArrayList<>(Arrays.asList(xA, xB, xC)));
            myMove = (int) this.allMoves.keySet().toArray()[0];
            return myMove;
        } else {
            this.movesN++;

            if (opponentLastMove == (int) this.allMoves.keySet().toArray()[0]) {
                this.allMoves = Move.checkMoves(new ArrayList<>(Arrays.asList(xA, xB, xC)));
                myMove = (int) this.allMoves.keySet().toArray()[0];
//                System.out.println("My opposite player choose Domination: "
//                        + opponentLastMove + ". I copy that: " + myMove);

                return myMove;
            } else {
                this.allMoves = Move.checkMoves(new ArrayList<>(Arrays.asList(xA, xB, xC)));
                myMove = (int) this.allMoves.keySet().toArray()[1];
//                System.out.println("My opposite player choose Average: "
//                        + opponentLastMove + ". I copy that: " + myMove);

                return myMove;
            }

        }
    }

    public String getEmail() { return "a.gruk@innopolis.ru";}
}

// Cooperate, unless the other players cheats twice in a row
class CopyKitten implements Player {

    private int movesN;
    private boolean shouldICheat;
    private Map<Integer, Integer> allMoves;
    private List<Boolean> opMoves;

    public CopyKitten() {
        this.movesN = 0;
        this.shouldICheat = false;
        this.opMoves = new ArrayList<>();
        this.allMoves = new HashMap<>();
    }

    public void reset() {
        this.movesN = 0;
        this.shouldICheat = false;
        this.opMoves = new ArrayList<>();
        this.allMoves = new HashMap<>();
    }

    public int move(int opponentLastMove, int xA, int xB, int xC) {
        if (this.movesN == 0) {
            this.movesN++;
            this.allMoves = Move.checkMoves(new ArrayList<>(Arrays.asList(xA, xB, xC)));
            int myMove = (int) this.allMoves.keySet().toArray()[1];

            return myMove;
        } else if (this.movesN == 1) {
            this.movesN++;
            if (opponentLastMove == (int) this.allMoves.keySet().toArray()[0]) {
                this.opMoves.add(Boolean.TRUE);
            } else {
                this.opMoves.add(Boolean.FALSE);
            }

            this.allMoves = Move.checkMoves(new ArrayList<>(Arrays.asList(xA, xB, xC)));
            int myMove = (int) this.allMoves.keySet().toArray()[1];

            return myMove;
        } else {
            this.movesN++;
            if (this.shouldICheat) {
                this.allMoves = Move.checkMoves(new ArrayList<>(Arrays.asList(xA, xB, xC)));
                int myMove = (int) this.allMoves.keySet().toArray()[0];

                return myMove;

            } else {
                if (opponentLastMove == (int) this.allMoves.keySet().toArray()[0]) {
                    this.opMoves.add(Boolean.TRUE);
                } else {
                    this.opMoves.add(Boolean.FALSE);
                }

                if (this.opMoves.get(this.opMoves.size() - 1).equals(Boolean.TRUE)) {
                    if (this.opMoves.get(this.opMoves.size() - 2).equals(Boolean.TRUE)) {
                        this.shouldICheat = Boolean.TRUE;
                    }
                }

                int myMove = (int) this.allMoves.keySet().toArray()[1];
                this.allMoves = Move.checkMoves(new ArrayList<>(Arrays.asList(xA, xB, xC)));

                return myMove;
            }
        }
    }

    public String getEmail() { return "a.gruk@innopolis.ru";}
}

// Start with random. If I 'won', do the same thing again. If I 'lost', do the other thing.
class Simpleton implements Player {
    private int movesN;
    private boolean amICoop;
    private Map<Integer, Integer> allMoves;

    public Simpleton() {
        this.movesN = 0;
        this.amICoop = true;
        this.allMoves = new HashMap<>();
    }

    public void reset() {
        this.amICoop = true;
        this.movesN = 0;
        this.allMoves = new HashMap<>();
    }

    public int move(int opponentLastMove, int xA, int xB, int xC) {

        int myMove;

        if (movesN == 0) {
            this.movesN++;
            this.allMoves = Move.checkMoves(new ArrayList<>(Arrays.asList(xA, xB, xC)));

            myMove = (int) this.allMoves.keySet().toArray()[1];
            return myMove;
        } else {
            this.movesN++;

            if (opponentLastMove == (int) this.allMoves.keySet().toArray()[1]) {
                this.amICoop = true ? false : true;
            }

            this.allMoves = Move.checkMoves(new ArrayList<>(Arrays.asList(xA, xB, xC)));
            if (this.amICoop)
                myMove = (int) this.allMoves.keySet().toArray()[1];
            else
                myMove = (int) this.allMoves.keySet().toArray()[0];

            return myMove;
        }
    }

    public String getEmail() { return "a.gruk@innopolis.ru";}
}


class RandomPlayer1 implements Player {

    public RandomPlayer1() {
    }

    public void reset() {
    }

    public int move(int opponentLastMove, int xA, int xB, int xC) {
        return new java.util.Random().nextInt(3) + 1;
    }

    public String getEmail() { return "a.gruk@innopolis.ru";}
}

class Sequantialist implements Player {
    private int myPrevMove;
    private int movesN;

    public Sequantialist() {
        this.myPrevMove = 1;
        this.movesN = 0;
    }

    public void reset() {
        this.myPrevMove = 1;
        this.movesN = 0;
    }

    public int move(int opponentLastMove, int xA, int xB, int xC) {
        int myMove;
        if (this.movesN == 0) {
            this.movesN++;
            java.util.Random rn = new java.util.Random();
            myMove = rn.nextInt(3) + 1;

            return myMove;
        } else {
            this.movesN++;
            myMove = this.myPrevMove;

            switch (this.myPrevMove) {
                case 1:
                    myMove = 2;
                    break;
                case 2:
                    myMove = 3;
                    break;
                case 3:
                    myMove = 1;
            }

            return myMove;
        }
    }

    public String getEmail() { return "a.gruk@innopolis.ru";}
}

class CooperateDominate implements Player {
    private Map<Integer, Integer> allMoves;
    private int movesN;

    public CooperateDominate() {
        this.allMoves = new HashMap<>();
        this.movesN = 0;
    }

    public void reset() {
        this.allMoves = new HashMap<>();
        this.movesN = 0;
    }

    public int move(int opponentLastMove, int xA, int xB, int xC) {
        int myMove;
        this.movesN++;

        if (this.movesN % 2 == 1) {
            this.allMoves = Move.checkMoves(new ArrayList<>(Arrays.asList(xA, xB, xC)));
            myMove = (int) this.allMoves.keySet().toArray()[2];
        } else {
            this.allMoves = Move.checkMoves(new ArrayList<>(Arrays.asList(xA, xB, xC)));
            myMove = (int) this.allMoves.keySet().toArray()[0];

        }

        return myMove;
    }

    public String getEmail() { return "a.gruk@innopolis.ru";}
}