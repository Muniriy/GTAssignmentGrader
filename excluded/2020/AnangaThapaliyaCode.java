package gametheory.assignment2.excluded2020;

import java.util.*;
//interface Player {
//    void reset();
//    int move(int rivalFinalTurn, int xA, int xB, int xC);
//}

class RandMove implements Player {
    RandMove() {}
    @Override
    public void reset() {}

    @Override
    public int move(int rivalFinalTurn, int xA, int xB, int xC) {
        return StoCh.gtRandElem();
    }

    @Override
    public String getEmail() { return "a.thapaliya@innopolis.ru"; }
}
class HasA implements Player {
    HasA() {}
    @Override
    public void reset() {}

    @Override
    public int move(int rivalFinalTurn, int xA, int xB, int xC) {
        return 1;
    }

    @Override
    public String getEmail() { return "a.thapaliya@innopolis.ru"; }
}
class HasB implements Player {
    HasB() {}
    @Override
    public void reset() {}

    @Override
    public int move(int rivalFinalTurn, int xA, int xB, int xC) {
        return 2;
    }

    @Override
    public String getEmail() { return "a.thapaliya@innopolis.ru"; }
}

class HasC implements Player {
    HasC() {}
    @Override
    public void reset() {}

    @Override
    public int move(int rivalFinalTurn, int xA, int xB, int xC) {
        return 3;
    }

    @Override
    public String getEmail() { return "a.thapaliya@innopolis.ru"; }
}
class Mirpy implements Player {
    Mirpy() {}
    @Override
    public void reset() {}

    @Override
    public int move(int rivalFinalTurn, int xA, int xB, int xC) {
        if (rivalFinalTurn == 0) return StoCh.gtRandElem();
        return rivalFinalTurn;
    }

    @Override
    public String getEmail() { return "a.thapaliya@innopolis.ru"; }
}
class AvgRiv implements Player {
    private Vector<Integer> rivalFinalmoves;
    AvgRiv() {
        rivalFinalmoves = new Vector<>(10);
    }
    @Override
    public void reset() {
        rivalFinalmoves.clear();
    }
    @Override
    public int move(int rivalFinalTurn, int xA, int xB, int xC) {
        if (rivalFinalTurn == 0) return 1;
        rivalFinalmoves.add(rivalFinalTurn);
        int add = 0;
        for (Integer finalTurn : rivalFinalmoves) {
            add += finalTurn;
        }
        return (int) Math.round(add * 1.0 / rivalFinalmoves.size());
    }

    @Override
    public String getEmail() { return "a.thapaliya@innopolis.ru"; }
}
class Oppositor implements Player {
    Oppositor() {}
    @Override
    public void reset() {}

    @Override
    public int move(int rivalFinalTurn, int xA, int xB, int xC) {
        if (rivalFinalTurn == 0) return 1;
        return (rivalFinalTurn + 1) % 3 + 1;
    }

    @Override
    public String getEmail() { return "a.thapaliya@innopolis.ru"; }
}
class Takemoves implements Player {
    private int present = 0;
    Takemoves() {}
    @Override
    public void reset() {
        present = 0;
    }

    @Override
    public int move(int rivalFinalTurn, int xA, int xB, int xC) {
        present = (present + 1) % 3;
        return present + 1;
    }

    @Override
    public String getEmail() { return "a.thapaliya@innopolis.ru"; }
}
class Sounder implements Player {
    private int present = 0;
    private final int[] sound = new int[]{1,2,3,3,1,2,3,3,1,2,3,1,2,3,3,1,2,3,3,3,2,2,1};
    Sounder() {}
    @Override
    public void reset() {
        present = 0;
    }

    @Override
    public int move(int rivalFinalTurn, int xA, int xB, int xC) {
        present = (present + 1) % sound.length;
        return sound[present];
    }

    @Override
    public String getEmail() { return "a.thapaliya@innopolis.ru"; }
}
class Grdy implements Player {
    Grdy() {}
    @Override
    public void reset() {}

    @Override
    public int move(int rivalFinalTurn, int xA, int xB, int xC) {
        if (xA >= xB && xA >= xC) {
            return 1;
        } else if (xB >= xA && xB >= xC) {
            return 2;
        } else {
            return 3;
        }
    }

    @Override
    public String getEmail() { return "a.thapaliya@innopolis.ru"; }
}
class Platform {
    private Vector<Player> Players = new Vector<Player>(2);
    private int moves;
    private int repetitions;
    private int xA, xB, xC;
    private Double[][] payMat;
    Platform(Player[] participants, int numberOfTurns, int numberOfIterations) {
        for (Player participant : participants) {
            Players.addElement(participant);
        }
        moves = numberOfTurns;
        repetitions = numberOfIterations;
        payMat = new Double[participants.length][participants.length];
        for (Double[] row: payMat) Arrays.fill(row, 0.0);
    }
    private Double sigmoid(int X) {
        double exppow = Math.pow(Math.E, X);
        return (10 * exppow) / (1 + exppow);
    }
    private Double getPayoff(int select) throws Exception {
        if (select != 1 && select != 2 && select != 3) throw new Exception("select can be 1, 2 or 3");
        Double f0 = sigmoid(0);
        if (select == 1) {
            return sigmoid(xA) - f0;
        } else if (select == 2) {
            return sigmoid(xB) - f0;
        } else {
            return sigmoid(xC) - f0;
        }
    }
    private Double[] pOff(int select1, int select2) throws Exception {
        if (select1 == select2) {
            return new Double[]{0.0, 0.0};
        } else {
            return new Double[]{getPayoff(select1), getPayoff(select2)};
        }
    }
    private Double[] playBetweenPlayers(Player p1, Player p2) throws Exception {
        Double[] lastShowdown = new Double[]{0.0, 0.0};
        Vector<Integer> p1Moves = new Vector<Integer>(moves);
        Vector<Integer> p2Moves = new Vector<Integer>(moves);

        rstXs();
        p1.reset(); p2.reset();

        for (int k = 0; k < moves; k++) {
            int p1finalTurn = k == 0 ? 0 : p1Moves.lastElement();
            int p2finalTurn = k == 0 ? 0 : p2Moves.lastElement();

            int p1Move = p1.move(p2finalTurn, xA, xB, xC);
            int p2Move = p2.move(p1finalTurn, xA, xB, xC);

            Double[] pOff = pOff(p1Move, p2Move);
            upXsMov(p1Move, p2Move);

            p1Moves.addElement(p1Move);
            p2Moves.addElement(p2Move);

            lastShowdown[0] += pOff[0];
            lastShowdown[1] += pOff[1];
        }
        return lastShowdown;
    }
      void play() throws Exception {
        for (int r = 0; r < repetitions; r++) {
            for (int i = 0; i < Players.size(); i++) {
                for (int j = i + 1; j < Players.size(); j++) {
                    Double[] pOff = playBetweenPlayers(Players.elementAt(i), Players.elementAt(j));
                    payMat[i][j] += pOff[0];
                    payMat[j][i] += pOff[1];
                }
            }
        }
    }
    void printResults() {
        Double[] sumOff = new Double[payMat.length];
        Arrays.fill(sumOff, 0.0);

        for (int i = 0; i < payMat.length; i++) {
            for (int j = 0; j < payMat.length; j++) {
                sumOff[i] += payMat[i][j];
            }
        }

        Two[] arr = new Two[payMat.length];
        for (int i = 0; i < sumOff.length; i++) {
            arr[i] = new Two(Players.elementAt(i), sumOff[i]);
        }
        Relate.relate(arr);

        System.out.println("RANK AGENT POINTS");

        for (int i = 0; i < arr.length; i++) {
            String className = arr[i].p.getClass().getSimpleName();
            System.out.println(Integer.toString(i + 1) + " " + className + " " + String.format("%.3f", arr[i].score));
        }
    }
    private void rstXs() {
        xA = 1; xB = 1; xC = 1;
    }
    private void lowIfHigher(int select) {
        if (select == 1) {
            if (xA > 0) xA--;
        } else if (select == 2) {
            if (xB > 0) xB--;
        } else if (select == 3) {
            if (xC > 0) xC--;
        }
    }
    private void upXsMov(int select1, int select2) {
        if (select1 == select2) {
            lowIfHigher(select1);
        } else {
            lowIfHigher(select1);
            lowIfHigher(select2);
            if ((select1 == 1 && select2 == 2) || (select1 == 2 && select2 == 1)) xC++;
            if ((select1 == 1 && select2 == 3) || (select1 == 3 && select2 == 1)) xB++;
            if ((select1 == 2 && select2 == 3) || (select1 == 3 && select2 == 2)) xA++;
        }
    }
}
class Two {
    Player p;
    Double score;

    Two(Player x, Double s) {
        p = x;
        score = s;
    }
}
class Relate {
    static void relate(Two[] arr) {
        Arrays.sort(arr, new Relator<Two>() {
            @Override
            public int relate(Two o1, Two o2) {
                return o1.score >= o2.score ? -1 : 1;
            }
        });
    }
}
class StoCh {
    static int gtRandElem() {
        Random rand = new Random();
        List<Integer> list = Arrays.asList(1, 2, 3);
        return list.get(rand.nextInt(list.size()));
    }
    static int gtRandOt(List<Integer> l) {
        Random rand = new Random();
        return l.get(rand.nextInt(l.size()));
    }
}
public class AnangaThapaliyaCode{
    public static void main(String[] args) throws Exception {
        Player[] participants = new Player[15];

        participants[0] = new RandMove();
        participants[1] = new Mirpy();
        participants[2] = new HasA();
        participants[3] = new HasB();
        participants[4] = new HasC();
        participants[5] = new Oppositor();
        participants[6] = new Takemoves();
        participants[7] = new AvgRiv();
        participants[8] = new Sounder();
        participants[9] = new Grdy();

        Platform t = new Platform(participants, 1000, 100);
        t.play();
        t.printResults();
    }
}
