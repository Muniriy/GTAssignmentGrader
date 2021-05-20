package gametheory.assignment2.students20s;

import gametheory.assignment2.Player;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.concurrent.ThreadLocalRandom;

public class GrigoriyDolgovCode implements Player {

    private int minAvg;
    private int[] xs;
    private ArrayList<Player> models;
    private int opponentModel;
    private int modelFails;
    private int modelPos;
    private int myPos;

    public GrigoriyDolgovCode() {
        minAvg = 3;
        reset();
    }

    public String getEmail() {
        return "g.dolgov@innopolis.university";
    }

    public void reset() {
        xs = new int[]{1, 1, 1};

        models = new ArrayList<>();
        models.add(new DeflectorRandom());
        models.add(new DeflectorFirst());
        models.add(new DeflectorDontFlee());
        models.add(new DeflectorNoPlayer());

        opponentModel = 0;
        modelFails = 0;
        modelPos = -1;

        myPos = -1;
    }

    public int move(int opponentPos, int xA, int xB, int xC) {
        xs[0] = xA;
        xs[1] = xB;
        xs[2] = xC;

        opponentPos--;
        if (opponentPos != modelPos) {
            modelFails++;
        }

        if (modelFails == 3) {
            modelFails = 0;

            // Switch model
            opponentModel++;
            if (opponentModel == models.size())
                opponentModel = 0;
        }

        for (int i = 0; i < models.size(); i++) {
            int pos = models.get(i).move(myPos + 1, xA, xB, xC) - 1;
            if (i == opponentModel) {
                modelPos = pos;
            }
        }

        int avg = (xA + xB + xC) / 3;

        if (avg < minAvg) {
            // Look for a fight
            // Go where the model is predicted to be
            myPos = modelPos;
            return myPos + 1;
        }

        xs[modelPos] = 0;
        DeflectorRandom bestGetter = new DeflectorRandom();
        myPos = bestGetter.move(0, xs[0], xs[1], xs[2]) - 1;
        return myPos + 1;
    }

    class DeflectorDontFlee implements Player {

        private ThreadLocalRandom rand;

        private int myPos = -1;

        public DeflectorDontFlee() {
            rand = ThreadLocalRandom.current();
        }

        public void reset() {
        }

        public String getEmail() {
            return "g.dolgov@innopolis.university";
        }

        public int move(int opponentPos, int xA, int xB, int xC) {
            opponentPos--;

            // Don't go anywhere. Make min(x) = 4 happen as soon as possible.
            if (myPos != -1 && opponentPos == myPos)
                return myPos + 1;

            ArrayList<Cell> xs = new ArrayList<>();
            xs.add(new Cell(xA, 0));
            xs.add(new Cell(xB, 1));
            xs.add(new Cell(xC, 2));

            xs.sort(new Comparator<Cell>() {
                @Override
                public int compare(Cell o1, Cell o2) {
                    return o2.x - o1.x;
                }
            });

            ArrayList<Cell> candidates = new ArrayList<>();
            candidates.add(xs.get(0));

            for (int i = 1; i < xs.size(); i++) {
                if (xs.get(i).x != xs.get(0).x)
                    break;

                candidates.add(xs.get(i));
            }

            int choice = rand.nextInt(candidates.size());

            myPos = candidates.get(0).id;
            return myPos + 1;
        }

        class Cell {
            public int x;
            public int id;

            public Cell(int x, int id) {
                this.x = x;
                this.id = id;
            }
        }
    }

    class DeflectorRandom implements Player {

        private ThreadLocalRandom rand;

        public DeflectorRandom() {
            rand = ThreadLocalRandom.current();
        }

        public void reset() {
        }

        public String getEmail() {
            return "g.dolgov@innopolis.university";
        }

        public int move(int opponentPos, int xA, int xB, int xC) {

            ArrayList<Cell> xs = new ArrayList<>();
            xs.add(new Cell(xA, 0));
            xs.add(new Cell(xB, 1));
            xs.add(new Cell(xC, 2));

            xs.sort(new Comparator<Cell>() {
                @Override
                public int compare(Cell o1, Cell o2) {
                    return o2.x - o1.x;
                }
            });

            ArrayList<Cell> candidates = new ArrayList<>();
            candidates.add(xs.get(0));

            for (int i = 1; i < xs.size(); i++) {
                if (xs.get(i).x != xs.get(0).x)
                    break;

                candidates.add(xs.get(i));
            }

            int choice = rand.nextInt(candidates.size());

            return candidates.get(choice).id + 1;
        }

        class Cell {
            public int x;
            public int id;

            public Cell(int x, int id) {
                this.x = x;
                this.id = id;
            }
        }
    }

    class DeflectorFirst implements Player {

        public void reset() {
        }

        public String getEmail() {
            return "g.dolgov@innopolis.university";
        }

        public int move(int opponentPos, int xA, int xB, int xC) {

            ArrayList<Cell> xs = new ArrayList<>();
            xs.add(new Cell(xA, 0));
            xs.add(new Cell(xB, 1));
            xs.add(new Cell(xC, 2));

            // if (opponentPos != -1)
            //     xs.remove(opponentPos);

            xs.sort(new Comparator<Cell>() {
                @Override
                public int compare(Cell o1, Cell o2) {
                    return o2.x - o1.x;
                }
            });

            return xs.get(0).id + 1;
        }

        class Cell {
            public int x;
            public int id;

            public Cell(int x, int id) {
                this.x = x;
                this.id = id;
            }
        }
    }

    class DeflectorNoPlayer implements Player {

        public void reset() {
        }

        public String getEmail() {
            return "g.dolgov@innopolis.university";
        }

        public int move(int opponentPos, int xA, int xB, int xC) {
            opponentPos--;

            ArrayList<Cell> xs = new ArrayList<>();
            xs.add(new Cell(xA, 0));
            xs.add(new Cell(xB, 1));
            xs.add(new Cell(xC, 2));

            if (opponentPos != -1)
                xs.remove(opponentPos);

            xs.sort(new Comparator<Cell>() {
                @Override
                public int compare(Cell o1, Cell o2) {
                    return o2.x - o1.x;
                }
            });

            return xs.get(0).id + 1;
        }

        class Cell {
            public int x;
            public int id;

            public Cell(int x, int id) {
                this.x = x;
                this.id = id;
            }
        }
    }
}

