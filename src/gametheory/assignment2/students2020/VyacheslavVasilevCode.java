package gametheory.assignment2.students2020;

//interface Player {
//    double score = 0.0;
//    void reset();
//    int move(int opponentLastMove, int xA, int xB, int xC);
//    String getEmail();
//}

import gametheory.assignment2.Player;

//it's my primary bot
public class VyacheslavVasilevCode implements Player {
    int opponentBest = 0;
    int opponentMiddle = 0;
    int opponentWorst = 0;
    int best = 0;
    int middle = 0;
    int worst = 0;
    int myLastMove = 0;
    int lastBest = 0;
    int lastMiddle = 0;
    int lastWorst = 0;
    double bestProb = 0;
    double middleProb = 0;
    double worstProb = 0;
    double bestExpValue = 0;
    double middleExpValue = 0;
    double worstExpValue = 0;


    @Override
    public void reset() {
        opponentBest = 0;
        opponentMiddle = 0;
        opponentWorst = 0;
        myLastMove = 0;
        lastBest = 0;
        lastMiddle = 0;
        lastWorst = 0;
    }

    @Override
    public int move(int opponentLastMove, int xA, int xB, int xC) {
        //sort moves by cost
        if (xA > xB) {
            if (xA > xC) {
                best = 1;
                if (xB > xC) {
                    middle = 2;
                    worst = 3;
                }
                else {
                    middle = 3;
                    worst = 2;
                }
            }
            else {
                best = 3;
                middle = 1;
                worst = 2;
            }
        }
        else {
            if (xB > xC) {
                best = 2;
                if (xA > xC) {
                    middle = 1;
                    worst = 3;
                }
                else {
                    middle = 3;
                    worst = 1;
                }
            }
            else {
                best = 3;
                middle = 2;
                worst = 1;
            }
        }

        //update statistics
        if (opponentLastMove != 0) {
            if (opponentLastMove == lastBest)
                opponentBest++;
            else if (opponentLastMove == lastMiddle)
                opponentMiddle++;
            else if (opponentLastMove == lastWorst)
                opponentWorst++;
        }

        //if best is always free, be greedy
        if (opponentBest <= 1) {
            remember(best);
            return best;
        }

        //if opponent is greedy, be 'ded'
        if (opponentWorst == 0 && opponentMiddle == 0) {
            remember(middle);
            return middle;
        }

        //calculate expected value, and do best move
        bestProb = ((double) opponentBest) / (opponentBest + opponentMiddle + opponentWorst);
        middleProb = ((double) opponentMiddle) / (opponentBest + opponentMiddle + opponentWorst);
        worstProb = ((double) opponentWorst) / (opponentBest + opponentMiddle + opponentWorst);

        if (best == 1)
            bestExpValue = bestProb * income(xA);
        else if (best == 2)
            bestExpValue = bestProb * income(xB);
        else
            bestExpValue = bestProb * income(xC);

        if (middle == 1)
            middleExpValue = middleProb * income(xA);
        else if (middle == 2)
            middleExpValue = middleProb * income(xB);
        else
            middleExpValue = middleProb * income(xC);

        if (worst == 1)
            worstExpValue = worstProb * income(xA);
        else if (worst == 2)
            worstExpValue = worstProb * income(xB);
        else
            worstExpValue = worstProb * income(xC);

        if (bestExpValue > middleExpValue && bestExpValue > worstExpValue) {
            remember(best);
            return (best);
        }
        if (middleExpValue > worstExpValue) {
            remember(middle);
            return middle;
        }
        remember(worst);
        return worst;

        /*
        //if most probable move is greedy, be 'ded'
        if (opponentBest > opponentMiddle && opponentBest > opponentWorst) {
            remember(middle);
            return middle;
        }

        //in other cases just be greedy
        remember(best);
        return best;
        */
    }

    @Override
    public String getEmail() {
        return "v.vasilev@innopolis.ru";
    }

    void remember (int myMove) {
        lastBest = best;
        lastMiddle = middle;
        lastWorst = worst;
        myLastMove = myMove;
    }

    double f(int x) {
        return (10 * Math.exp(x)) / (1 + Math.exp(x));
    }

    double income(int x) {
        return f(x) - f(0);
    }

    @Override
    public String toString() {
        return getClass().getName();
    }
}

//it's not my primary bot, he is just for testing
class VyacheslavVasilevDedBot implements Player {
    @Override
    public void reset() {}

    @Override
    public int move(int opponentLastMove, int xA, int xB, int xC) {
        if (xA > xB) {
            if (xB > xC)
                return 2;
            if (xA > xC)
                return 3;
            return 1;
        }
        if (xB > xC) {
            if (xA > xC)
                return 3;
            return 1;
        }
        return 2;
    }

    @Override
    public String getEmail() {
        return "v.vasilev@innopolis.ru";
    }

    @Override
    public String toString() {
        return getClass().getName();
    }
}

//it's not my primary bot, he is just for testing
class VyacheslavVasilevGreedyBot implements Player {
    @Override
    public void reset() {}

    @Override
    public int move(int opponentLastMove, int xA, int xB, int xC) {
        if (xA > xB) {
            if (xA > xC)
                return 1;
            return 3;
        }
        if (xB > xC)
            return 2;
        return 3;
    }

    @Override
    public String toString() {
        return getClass().getName();
    }

    @Override
    public String getEmail() {
        return "v.vasilev@innopolis.ru";
    }
}

//it's not my primary bot, he is just for testing
class VyacheslavVasilevRandomBot implements Player {
    @Override
    public void reset() {}

    @Override
    public int move(int opponentLastMove, int xA, int xB, int xC) {
        return (int) Math.ceil(Math.random() * 3);
    }

    @Override
    public String toString() {
        return getClass().getName();
    }

    @Override
    public String getEmail() {
        return "v.vasilev@innopolis.ru";
    }
}