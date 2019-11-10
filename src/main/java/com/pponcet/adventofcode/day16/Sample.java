package com.pponcet.adventofcode.day16;

import java.util.ArrayList;
import java.util.List;

public class Sample {
    private final List<Integer> before;
    private final int a;
    private final int b;
    private final int c;
    private final List<Integer> after;

    public Sample(int before0, int before1, int before2, int before3,
                  int a, int b, int c,
                  int after0, int after1, int after2, int after3) {
        List<Integer> bf = new ArrayList<>();
        bf.add(before0);
        bf.add(before1);
        bf.add(before2);
        bf.add(before3);
        this.before = bf;
        this.a = a;
        this.b = b;
        this.c = c;
        List<Integer> af = new ArrayList<>();
        af.add(after0);
        af.add(after1);
        af.add(after2);
        af.add(after3);
        this.after = af;
    }

    public int addr(){
        int match = 0;
        if(before.get(a)+before.get(b) == after.get(c)){
            match = 1;
        }
        return match;
    }

    public int addi(){
        int match = 0;
        if(before.get(a)+b == after.get(c)){
            match = 1;
        }
        return match;
    }

    public int mulr(){
        int match = 0;
        if(before.get(a)*before.get(b) == after.get(c)){
            match = 1;
        }
        return match;
    }

    public int muli(){
        int match = 0;
        if(before.get(a)*b == after.get(c)){
            match = 1;
        }
        return match;
    }

    public int banr(){
        int match = 0;
        if((before.get(a) & before.get(b)) == after.get(c)){
            match = 1;
        }
        return match;
    }

    public int bani(){
        int match = 0;
        if((before.get(a) & b) == after.get(c)){
            match = 1;
        }
        return match;
    }

    public int borr(){
        int match = 0;
        if((before.get(a) | before.get(b)) == after.get(c)){
            match = 1;
        }
        return match;
    }

    public int bori(){
        int match = 0;
        if((before.get(a) | b) == after.get(c)){
            match = 1;
        }
        return match;
    }

    public int setr(){
        int match = 0;
        if(before.get(a) == after.get(c)){
            match = 1;
        }
        return match;
    }

    public int seti(){
        int match = 0;
        if(a == after.get(c)){
            match = 1;
        }
        return match;
    }

    public int gtir(){
        int match = 0;
        boolean condition = a > before.get(b);
        if((condition && after.get(c) == 1) || (!condition && after.get(c) == 0)){
            match = 1;
        }
        return match;
    }

    public int gtri(){
        int match = 0;
        boolean condition = before.get(a) > b;
        if((condition && after.get(c) == 1) || (!condition && after.get(c) == 0)){
            match = 1;
        }
        return match;
    }

    public int gtrr(){
        int match = 0;
        boolean condition = before.get(a) > before.get(b);
        if((condition && after.get(c) == 1) || (!condition && after.get(c) == 0)){
            match = 1;
        }
        return match;
    }

    public int eqir(){
        int match = 0;
        boolean condition = a == before.get(b);
        if((condition && after.get(c) == 1) || (!condition && after.get(c) == 0)){
            match = 1;
        }
        return match;
    }

    public int eqri(){
        int match = 0;
        boolean condition = before.get(a) == b;
        if((condition && after.get(c) == 1) || (!condition && after.get(c) == 0)){
            match = 1;
        }
        return match;
    }

    public int eqrr(){
        int match = 0;
        boolean condition = before.get(a) == before.get(b);
        if((condition && after.get(c) == 1) || (!condition && after.get(c) == 0)){
            match = 1;
        }
        return match;
    }


    public int calculateNumberBehaviour(){
        return    addr()
                + addi()
                + mulr()
                + muli()
                + banr()
                + bani()
                + borr()
                + bori()
                + setr()
                + seti()
                + gtir()
                + gtri()
                + gtrr()
                + eqir()
                + eqri()
                + eqrr();
    }

}
