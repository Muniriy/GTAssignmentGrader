package com.company;


import java.util.*;


public class NiyazFahretdinovCode extends ABPlayer {
    NiyazFahretdinovCode() {
        super(new ABPlayer(new RandomPlayer(), new AlphaPlayer()), new AlphaPlayer());
    }
}


/**
 * An interface to have human readable names for each player.
 */
interface Named {
    String getName();
}


/**
 * The base abstract class I use for all the players.
 * It implements naming, numbering each instance of the same type uniquely.
 * It implements {@link com.company.Player#getEmail}.
 * It also provides a random for each subclass, because I'm too lazy to create one for each subclass needed.
 */
abstract class NamedPlayer implements Player, Named {

    protected Random r = new Random();
    private static Map<Class<?>, Integer> counters = new HashMap<>();
    private String name;

    NamedPlayer() {
        counters.merge(getClass(), 1, Integer::sum);
        name = getClass().getSimpleName() + " " + counters.get(getClass());
    }

    @Override
    public String getEmail() {
        return "n.fahretdinov@innopolis.ru";
    }

    @Override
    public String getName() {
        return name;
    }
}


/**
 * The structure I used for argsorting.
 * It just stores pair of index and value.
 *
 * @param <V> the value stored.
 */
class IndexedValue<V> {
    int ind;
    V v;

    IndexedValue(int ind, V v) {
        this.v = v;
        this.ind = ind;
    }
}


/**
 * The basic player strategy that picks n-th best option.
 * If there are several such options, picks randomly from them.
 */
class SkipAtTopPlayer extends NamedPlayer {
    private int top;

    SkipAtTopPlayer(int top) {
        this.top = top;
    }

    @Override
    public void reset() {

    }

    @Override
    public int move(int opponentLastMove, int xA, int xB, int xC) {
        // Map the numbers to the index value pairs, and store them in a list.
        var list = Arrays.asList(new IndexedValue<>(1, xA), new IndexedValue<>(2, xB), new IndexedValue<>(3, xC));

        // shuffle the list to pick randomly at case of a tie.
        Collections.shuffle(list);

        // Sort them in the order from best to worst.
        list.sort(Comparator.comparingInt(a -> -a.v));

        // Pick the n-th best item's index.
        return list.get(top).ind;
    }
}


/**
 * The player which picks the best available option.
 */
class AlphaPlayer extends SkipAtTopPlayer {

    AlphaPlayer() {
        super(0);
    }
}


/**
 * The player which picks the second best available option.
 */
class BetaPlayer extends SkipAtTopPlayer {
    BetaPlayer() {
        super(1);
    }
}


/**
 * The player which picks the worst available option.
 */
class GammaPlayer extends SkipAtTopPlayer {
    GammaPlayer() {
        super(2);
    }
}


/**
 * A player that chooses randomly.
 */
class RandomPlayer extends NamedPlayer {
    private static Random r = new Random();

    @Override
    public void reset() {

    }

    @Override
    public int move(int opponentLastMove, int xA, int xB, int xC) {
        return r.nextInt(3) + 1;
    }
}


/**
 * A player that combines two basic strategies and chooses randomly between them.
 */
class ABPlayer extends NamedPlayer {
    private Player a, b;

    ABPlayer(Player a, Player b) {
        this.a = a;
        this.b = b;
    }

    @Override
    public void reset() {
        a.reset();
        b.reset();
    }

    @Override
    public int move(int opponentLastMove, int xA, int xB, int xC) {
        int[] res = new int[2];

        res[0] = a.move(opponentLastMove, xA, xB, xC);
        res[1] = b.move(opponentLastMove, xA, xB, xC);

        return res[r.nextInt(2)];
    }
}


/**
 * todo
 */
class RandomInverseCopycat extends NamedPlayer {

    @Override
    public void reset() {

    }

    @Override
    public int move(int opponentLastMove, int xA, int xB, int xC) {
        if (opponentLastMove == 0)
            return r.nextInt(3) + 1;
        return 1 + 2 + 3 - opponentLastMove - (opponentLastMove + (r.nextBoolean() ? 0 : 1)) % 3 - 1;
    }
}


/**
 * todo
 */
class AlphaInverseCopycat extends NamedPlayer {

    @Override
    public void reset() {

    }

    @Override
    public int move(int opponentLastMove, int xA, int xB, int xC) {

        var vals = new ArrayList<IndexedValue<Integer>>();
        Collections.addAll(vals, new IndexedValue<>(1, xA), new IndexedValue<>(2, xB), new IndexedValue<>(3, xC));
        vals.removeIf(a -> a.ind == opponentLastMove);
        Collections.shuffle(vals);
        vals.sort(Comparator.comparingInt(a -> -a.v));

        return vals.get(0).ind;
    }
}


/**
 * Player that was intended to put in the tournament with several players of it's kind.
 * At the beginning check the other player's moves fot the secret password.
 * If the password is incorrect, starts playing plan B strategy - the violated strategy provided through the constructor.
 * If the password check succeeded, it plays which the other player the well predefined strategy.
 */
class CoopPlayer extends NamedPlayer {

    private int moveCounter = 0;
    private int[] pass = {1, 1, 1, 3, 2, 1};
    private boolean violated = false;
    private Player violatedStrategy;
    private boolean wait = false;
    private int place = -1;
    private int lastPlace = 0;

    CoopPlayer(Player violatedStrategy) {
        this.violatedStrategy = violatedStrategy;
    }


    @Override
    public void reset() {
        violatedStrategy.reset();
        moveCounter = 0;
        violated = false;
        wait = false;
        place = -1;
    }

    @Override
    public int move(int opponentLastMove, int xA, int xB, int xC) {
        var passInd = moveCounter++;

        if (violated)
            return violatedStrategy.move(opponentLastMove, xA, xB, xC);

        if (passInd == 0) {
            return pass[passInd];

        } else if (passInd < pass.length) {
            if (opponentLastMove != pass[passInd - 1]) {
                violated = true;
                return violatedStrategy.move(opponentLastMove, xA, xB, xC);
            }
            return pass[passInd];

        } else {
            if (passInd == pass.length)
                if (opponentLastMove != pass[passInd - 1]) {
                    violated = true;
                    return violatedStrategy.move(opponentLastMove, xA, xB, xC);
                }


            if (wait) {
                wait = false;

                if (place == -1) {
                    if (opponentLastMove != lastPlace) {
                        place = lastPlace;
                    }
                }

                return 1;

            } else {
                wait = true;

                if (place != -1)
                    return place;

                lastPlace = r.nextInt(2) + 2;
                return lastPlace;
            }
        }
    }
}
