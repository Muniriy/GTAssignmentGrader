package com.company;

import java.util.Arrays;
import java.util.Comparator;
import java.util.concurrent.ThreadLocalRandom;

public class NikolaiMikriukovCode implements Player {

    //choose randomly from best fields (random when there are multiple best choices)

    @Override
    public void reset() {}

    class Field{
        int id; //1-3
        int grass;
        public Field(int id_, int grass_) {
            this.id = id_;
            this.grass = grass_;
        }
    }

    @Override
    public int move(int opponentLastMove, int xA, int xB, int xC) {
        //sort fields by grass
        Field[] fields = new Field[] {new Field(1, xA), new Field(2, xB), new Field(3, xC)};
        Arrays.sort(fields, Comparator.comparingInt((Field a) -> -a.grass));
        int best_border = 0;
        //find how many best options exist
        if (fields[1].grass == fields[0].grass){
            best_border++;
            if (fields[2].grass == fields[1].grass)
                best_border++;
        }
        //choose randomly form best ones.
        return fields[ThreadLocalRandom.current().nextInt(0, best_border + 1)].id;
    }

    @Override
    public String getEmail() {
        return "n.mikriukov@innopolis.ru";
    }
}
