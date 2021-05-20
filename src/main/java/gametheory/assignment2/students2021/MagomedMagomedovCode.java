package gametheory.assignment2.students2021;

import gametheory.assignment2.Player;

public class MagomedMagomedovCode implements Player {
    int home, reserve1, reserve2, current;
    int[] foods = new int[]{0, 0, 0, 0};
    int foodLimit;
    EnemyType enemy = EnemyType.Pending;

    public MagomedMagomedovCode() {
        reset();
        foodLimit = 5;
    }

    public MagomedMagomedovCode(int optimalFood) {
        reset();
        this.foodLimit = optimalFood;
    }

    private int setHome(int newHome) {
        home = newHome;
        boolean swapped = Math.random() < 0.5;

        if (home == 1) {
            reserve1 = swapped ? 3 : 2;
            reserve2 = swapped ? 2 : 3;
        } else if (home == 2) {
            reserve1 = swapped ? 3 : 1;
            reserve2 = swapped ? 1 : 3;
        } else {
            reserve1 = swapped ? 2 : 1;
            reserve2 = swapped ? 1 : 2;
        }
        return home;
    }

    private int setHome() {
        return setHome(Math.random() < 0.5 ? 1 : 3);
    }

    @Override
    public void reset() {
        setHome();
        enemy = EnemyType.Pending;
        current = home;
    }

    private int travel() {
        if (current == 2 && foods[home] < foodLimit)
            return 2;
        else if (current == 2 && foods[home] >= foodLimit)
            return home;
        else if (current == home && foods[home] > 0)
            return home;
        else
            return 2;
    }

    @Override
    public int move(int opponentLastMove, int xA, int xB, int xC) {
        if (opponentLastMove == 0)
            return current = 2;

        foods = new int[]{0, xA, xB, xC};

        if (enemy == EnemyType.Same)
            if (opponentLastMove == home)
                return current = setHome();
            else
                return current = travel();

        if (enemy == EnemyType.Pending) {
            if (opponentLastMove == home) {
                int otherHome = home == 1 ? 3 : 1;
                if (foods[otherHome] == foodLimit + 1 && foods[2] == 1) {
                    enemy = EnemyType.Same;
                    return current = setHome();
                } else
                    enemy = EnemyType.Other;
            } else
                return current = travel();
        }

        if (enemy == EnemyType.Other) {
            if (current == home)
                if (foods[reserve1] > 0)
                    return current = reserve1;
                else if (foods[reserve2] > 0)
                    return current = reserve2;
                else {
                    setHome(home);
                    return current = home;
                }
            // if (foods[home] > 0)
            // return home;
            // else if (foods[reserve1] > 0)
            // return current = foods
            // if (opponentLastMove != current && )
            // return current;
            // else {
            // setHome(current);
            // if (foods[reserve1] > 0)
            // return current = reserve1;
            // else if (foods[reserve2] > 0)
            // return current = reserve2;
            // else
            // return current = home;
            // }
        }
        return current = home;
    }

    @Override
    public String getEmail() {
        return "ma.magomedov@innopolis.ru";
    }

    enum EnemyType {
        Pending, Same, Other
    }
}
