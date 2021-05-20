package gametheory.assignment2.students20s;

import gametheory.assignment2.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class AbdelrahmanAbounegmCode implements Player {
    final int historySize = 10;
    /**
     * The coefficients identified by the logistic regressor
     */
    final double[][] coefficients = {
            {0.22459103317119825, -0.3792837674066267, 0.1541131801856785},
            {-0.16150076929279475, 0.49233574910007677, -0.33081583051680424},
            {0.033804126689505345, -0.1221452880827711, 0.08790482363480621},
            {-0.4793984020796279, 0.17886596170471414, 0.30054079712958814},
            {0.30230214700119457, -0.4004111651703456, 0.09794310401130096},
            {0.13671592411508782, 0.2914452640655227, -0.4284543357647777},
            {-0.1988452748002958, 0.18269031593473875, 0.01631664306228272},
            {0.10519344858140625, -0.07010304800508628, -0.034623112529606724},
            {0.020161581134395236, -0.14849839853751268, 0.1287373266482837},
            {-0.3251877664926929, 0.23082785506680487, 0.09407324166317846},
            {0.17586142387815076, -0.2636582771555363, 0.08771486026505372},
            {0.07453812681297295, 0.1502031078959018, -0.2250185771406543},
            {0.2003021273664583, -0.05755771467500875, -0.1421464118532369},
            {-0.157102638493074, 0.21126559213200036, -0.053909623168881454},
            {-0.0103272345002072, -0.11736024109393164, 0.1282868405794485},
            {-0.3471656275114052, -0.04770553229891322, 0.3954705304727357},
            {-0.012040670276124555, 0.08983926007995664, -0.0775268654838349},
            {0.29366106712712137, 0.02628900871191276, -0.3202418948595582},
            {0.21384161516014405, 0.07234876889604822, -0.2864761417663311},
            {0.030328903738824894, -0.0026996692778954567, -0.02762257759476803},
            {-0.15060008120419038, 0.025283600332968427, 0.1256303042078505},
            {-0.2366151108351998, 0.007413801006317267, 0.22934133996180334},
            {0.30543341893065684, -0.5828231338494659, 0.27708964829357213},
            {0.04318378913797811, 0.5081532306336944, -0.5516275400622896},
            {-0.23694227412856567, -0.12247092338196917, 0.35992761325601613},
            {0.15489088548798416, 0.146039958783784, -0.301103276321935},
            {0.25161709071983734, -0.13488262821162803, -0.11613755808882323},
            {-0.280752489167254, 0.04152816801752313, 0.23902933419349018},
            {0.032025997718953154, 0.13032660680344155, -0.16231418248760573},
            {0.023136365471618726, -0.11398015404640906, 0.09097275512798068},
            {0.04438052506814178, -0.18028742703301356, 0.13575430595732904},
            {0.11014385898279411, 0.13728225034677044, -0.2473921136145131},
            {-0.39583633594400036, 0.0405977836603588, 0.35541165934791946},
            {-0.07607653760130108, 0.05638269436498091, 0.01939932111779173},
            {0.09076726258980926, -0.13356245855023097, 0.04219582813842759},
            {-0.05503576206378582, 0.11040479722983693, -0.05492414948051779},
            {-0.4197131374240549, 0.14986716955237603, 0.2693966107727199},
            {0.15282877807986656, -0.17273331265442757, 0.019904406118797718},
            {0.21143325246296718, 0.08858139836764696, -0.30015848109591314},
            {0.21799566260131462, -0.1826234813597916, -0.03529685276474376},
            {-0.3361954559573375, 0.3249578823821821, 0.010827573131653835},
            {0.07867178355298955, -0.1085877416576265, 0.029963727258093594},
            {-0.5216760904640615, 0.33534959283313087, 0.18612881210776494},
            {0.2068545359792283, -0.4758576869497382, 0.26840378317517716},
            {0.2957574685332578, 0.17814577929063496, -0.47405361461783363},
            {-0.2630242075724911, -0.1403879822137091, 0.403834914835855},
            {0.11590490443640628, 0.3048103173208556, -0.42124525538517243},
            {0.137678641578767, -0.15326648239560345, 0.01582918569833678},
            {-0.1341965504582819, 0.18459139096345953, -0.04998880639783782},
            {-0.06679922559724177, -0.04240522327038634, 0.10911823481407881},
            {0.19427871890285786, -0.1283288415114974, -0.06551877240137473},
            {-0.22039041323967123, 0.11326859082933907, 0.10704955067575334},
            {0.0005735510869891134, 0.07459272170388487, -0.07547092097521454},
            {0.25279833276964736, -0.2060333103012025, -0.046578047616228434},
            {-0.40386432805708583, 0.28703710999245813, 0.11639569030744529},
            {0.306349211504632, -0.31588824553160044, 0.009180089458987344},
            {0.08784665250564115, 0.04720158160236112, -0.1351960196330142},
            {-0.6889212837971451, -0.15347858881864893, 0.8428008030209029},
            {0.18463765075779806, -0.04042056065772009, -0.1439300287235765},
            {0.5710760116425555, 0.17344485438951487, -0.7446567851677409},
            {-0.289581368675072, 0.2109920231393506, 0.07823772503229552},
            {0.20439999183029986, -0.46797708136422667, 0.2629777217354464},
            {0.09898555760192251, 0.3334373613235025, -0.4325679980564564},
    };
    final double[] intercepts = {0.0053765821, 0.0940711787, -0.0994477608};
    int[][] opponentHistory;
    int[][] myHistory;


    public AbdelrahmanAbounegmCode() {
        reset();
    }

    @Override
    public void reset() {
        opponentHistory = new int[historySize][3];
        myHistory = new int[historySize][3];
    }

    /**
     * One-hot encode the given action
     *
     * @param action A number from 1 to 3
     * @return A list with a 1 in the appropriate place, and 0s otherwise
     */
    private int[] oneHotEncode(int action) {
        int[] ret = {0, 0, 0};
        if (action != 0)
            ret[action - 1] = 1;
        return ret;
    }

    /**
     * Run the logistic function on the features and pick the best field
     *
     * @param features The inputs consisting of field configuration and history
     * @return The field that should be picked
     */
    private int predict(List<Integer> features) {
        double[] sums = intercepts.clone();

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 2 * 3 * historySize + intercepts.length; j++) {
                sums[i] += features.get(j) * coefficients[j][i];
            }
        }

        if (sums[0] >= sums[1] && sums[0] >= sums[2])
            return 1;
        if (sums[1] >= sums[2])
            return 2;
        return 3;
    }

    /**
     * Pushes the new item at the end of the array, removing the first element
     *
     * @param array The array to be modified
     * @param x     The action to insert at the end
     */
    private void pushToHistory(int[][] array, int[] x) {
        if (array.length >= 1) System.arraycopy(array, 1, array, 0, array.length - 1);
        array[array.length - 1] = x.clone();
    }

    @Override
    public int move(int opponentLastMove, int xA, int xB, int xC) {
        // Remember the opponent's new action
        pushToHistory(opponentHistory, oneHotEncode(opponentLastMove));
        // Start the input with the current field value
        List<Integer> features = new ArrayList<>(Arrays.asList(xA, xB, xC));
        // Push the opponent's history
        for (int i = 0; i < historySize; i++)
            Collections.addAll(features, opponentHistory[i][0], opponentHistory[i][1], opponentHistory[i][2]);
        // Push my history
        for (int i = 0; i < historySize; i++)
            Collections.addAll(features, myHistory[i][0], myHistory[i][1], myHistory[i][2]);
        // Predict my next move
        int newMove = predict(features);
        // Save it for the future
        pushToHistory(myHistory, oneHotEncode(newMove));
        return newMove;
    }

    @Override
    public String getEmail() {
        return "a.abounegm@innopolis.university";
    }

    @Override
    public String toString() {
        return "AbdelrahmanAbounegm";
    }
}

/**
 * This class contains the strategy that was used before switching to the ML one
 */
class AbdelrahmanAbounegmOld implements Player {
    private int[] previous_fields;

    public AbdelrahmanAbounegmOld() {
        reset();
    }

    @Override
    public void reset() {
        previous_fields = new int[]{1, 1, 1};
    }

    private int kthLargestField(int k, int[] arr) {
        int[] copy = Arrays.copyOf(arr, arr.length);
        Arrays.sort(copy);
        return 1 + Arrays.binarySearch(copy, copy[k - 1]);
    }

    @Override
    public int move(int opponentLastMove, int xA, int xB, int xC) {
        if (opponentLastMove == 0) {     // first move
            return ThreadLocalRandom.current().nextInt(1, 3 + 1);  // play randomly. there is no preference
        }

        if (opponentLastMove == kthLargestField(1, previous_fields)) { // opponent "cheated"
            previous_fields = new int[]{xA, xB, xC};
            return kthLargestField(1, previous_fields);    // pick the best field
        }

        // opponent "cooperated"
        previous_fields = new int[]{xA, xB, xC};
        return kthLargestField(2, previous_fields);          // pick the 2nd best field
    }

    @Override
    public String getEmail() {
        return "a.abounegm@innopolis.university";
    }

    @Override
    public String toString() {
        return "AbdelrahmanAbounegm";
    }
}