package gametheory.assignment2.students2020;

import gametheory.assignment2.Player;

import java.util.Random;

public class EgorKlementevCode implements Player {

    private Random random; // Random is used for probability moves

    public EgorKlementevCode() {
        random = new Random();
    }

    @Override
    public void reset() {
        random = new Random();
    }

    @Override
    public int move(int opponentLastMove, int xA, int xB, int xC) {
        int move;

        // Division of the interval from 0 to 1 into three parts.
        float p1 = (float) xA / (xA + xB + xC);
        float p2 = p1 + (float) xB / (xA + xB + xC);

        // Random point between 0 and 1.
        float chance = random.nextFloat();

        // The agent chooses move according to the region where random point is lying.
        if (chance < p1) {
            move = 1;
        } else if (chance < p2) {
            move = 2;
        } else {
            move = 3;
        }

        return move;
    }

    @Override
    public String toString() {
        return "Egor Klementev's Moose Agent";
    }

	@Override
	public String getEmail() {
		return "e.klementev@innopolis.ru";
	}
}
