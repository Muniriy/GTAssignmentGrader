package gametheory.assignment2.students20s;

import gametheory.assignment2.Player;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


public class LeonidTyurinCode implements Player {
    BasePlayer player = new PlayerRNDGREEDYGENTLE();

    public LeonidTyurinCode() {

    }

    @Override
    public void reset() {
        player.reset();
    }

    @Override
    public int move(int opponentLastMove, int xA, int xB, int xC) {
        return player.move(opponentLastMove, xA, xB, xC);
    }

    @Override
    public String getEmail() {
        return player.getEmail();
    }

    public static class BasePlayer implements Player {
        @Override
        public void reset() {
        }

        @Override
        public int move(int opponentLastMove, int xA, int xB, int xC) {
            return 0;
        }

        @Override
        public String getEmail() {
            return "l.tyurin@innopolis.university";
        }
    }

    public static class PlayerRNDGREEDYGENTLE extends BasePlayer {
        @Override
        public int move(int opponentLastMove, int xA, int xB, int xC) {
            List<Integer> fields = Arrays.asList(xA, xB, xC);
            List<Integer> sortedIndices = Utils.getSortedIndicesOfNMaxElements(fields, fields.size());
            List<Integer> appropriateScoreIndices = sortedIndices
                    .stream()
                    .filter(i -> fields.get(i) > 3)
                    .collect(Collectors.toList());
            // The same as RNDGREEDY if there are less than 2 indices with grass <= 3
            if (appropriateScoreIndices.size() < 2) {
                return Utils.getRandomMaxIndex(fields) + 1;
            } else {
                return Utils.getRandomElem(appropriateScoreIndices) + 1;
            }
        }
    }

    /**
     * Utility class with useful functions for agents
     */
    public static class Utils {
        private static final Random random = new Random();

        public static <T extends Comparable<T>> List<Integer> getIndicesOfSecondMaxElement(List<T> arr) {
            T largest = Collections.max(arr);
            T secondLargest = null;
            for (T cur : arr) {
                if (cur != largest && (secondLargest == null || cur.compareTo(secondLargest) > 0))
                    secondLargest = cur;
            }

            if (largest == secondLargest || secondLargest == null) return new ArrayList<>();

            List<Integer> indices = new ArrayList<>();
            for (int i = 0; i < arr.size(); i++) {
                if (arr.get(i) == secondLargest) {
                    indices.add(i);
                }
            }
            return indices;
        }


        public static <T extends Comparable<T>> List<Integer> getIndicesOfMaxElement(List<T> arr) {
            T max = Collections.max(arr);
            return IntStream
                    .rangeClosed(0, arr.size() - 1)
                    .boxed()
                    .filter(i -> arr.get(i) == max)
                    .collect(Collectors.toList());
        }

        public static <T extends Comparable<T>> List<Integer> getSortedIndicesOfNMaxElements(List<T> arr, int n) {
            assert n < arr.size();
            return IntStream
                    .rangeClosed(0, arr.size() - 1)
                    .boxed()
                    .sorted(Comparator.comparing(arr::get).reversed())
                    .collect(Collectors.toList())
                    .subList(0, n);
        }

        public static <T extends Comparable<T>> List<Integer> getIndicesOfNotMinElements(List<T> arr) {
            T min = Collections.min(arr);
            return IntStream
                    .rangeClosed(0, arr.size() - 1)
                    .boxed()
                    .filter(i -> arr.get(i) != min)
                    .collect(Collectors.toList());
        }


        public static int randomTurn() {
            return random.nextInt(3) + 1;
        }

        public static <T> T getRandomElem(List<T> arr) {
            return arr.get(random.nextInt(arr.size()));
        }

        public static <T extends Comparable<T>> Integer getRandomMaxIndex(List<T> arr) {
            return Utils.getRandomElem(Utils.getIndicesOfMaxElement(arr));
        }

    }


}
