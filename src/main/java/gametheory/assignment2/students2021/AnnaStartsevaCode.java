package gametheory.assignment2.students2021;

import gametheory.assignment2.Player;

import java.util.Random;


public class AnnaStartsevaCode implements Player {

    boolean handshake = false;

    //variables
    int mem = -1;   //memory cell - remembers your cell(for cooperating strategy)
    int opp = -1;   //opponent cell for cooperating strategy
    int stall = -1;     //cell to wait for the grass to grow

    public AnnaStartsevaCode() {
        reset();
    }

    @Override
    public void reset() {
        boolean handshake = false;
        int mem = -1;   //memory cell - remembers your cell(for cooperating strategy)
        int opp = -1;   //opponent cell for cooperating strategy
        int stall = -1;     //cell to wait for the grass to grow
    }


    @Override
    public int move(int opponentLastMove, int xA, int xB, int xC) {
        if (opponentLastMove == 0) { //As a first move always choose the random cell
            Random r = new Random();
            mem = r.nextInt(3) + 1;
            return mem;
        }
        //handshake
        if (opponentLastMove != mem && (!handshake)) {
            opp = opponentLastMove;
            handshake = true;
            if (opp != mem % 3 + 1) {
                stall = mem % 3 + 1;
            } else {
                stall = opp % 3 + 1;
            }
        } else if (!handshake) {
            Random rand = new Random();
            int old_mem = mem;
            mem = rand.nextInt(3) + 1;
            while (mem == old_mem) {
                mem = rand.nextInt(3) + 1;
            }
            return mem;
        }
        //cooperation strategy
        if (handshake) {
            if (opponentLastMove == mem) {
                handshake = false;
                Random rand = new Random();
                int new_cell = rand.nextInt(3) + 1;
                if (new_cell != mem)
                    return new_cell;
                else return rand.nextInt(3) + 1;
            }
            if (mem == 1) {
                if (xA < 6 || xA == 0)
                    return stall;
                else return mem;
            }
            if (mem == 2) {
                if (xB < 6 || xB == 0)
                    return stall;
                else return mem;
            }
            if (mem == 3) {
                if (xC < 6 || xC == 0)
                    return stall;
                else return mem;
            }
        }

        return 0;
    }

    @Override
    public String getEmail() {
        return "an.startseva@innopolis.university";
    }
}
