package com.pponcet.adventofcode.day19;

import java.util.function.BiFunction;

public class Instruction {
    public static final Instruction nullInstruction = new Instruction(Name.ADDR, -1, -1, -1);

    public enum Name{
        ADDR(Addr.biFunction),
        ADDI(Addi.biFunction),
        MULR(Mulr.biFunction),
        MULI(Muli.biFunction),
        BANR(Banr.biFunction),
        BANI(Bani.biFunction),
        BORR(Borr.biFunction),
        BORI(Bori.biFunction),
        SETR(Setr.biFunction),
        SETI(Seti.biFunction),
        GTIR(Gtir.biFunction),
        GTRI(Gtri.biFunction),
        GTRR(Gtrr.biFunction),
        EQIR(Eqir.biFunction),
        EQRI(Eqri.biFunction),
        EQRR(Eqrr.biFunction);

        private BiFunction<Memory, Instruction, Memory> biFunction;

        Name(BiFunction<Memory, Instruction, Memory> biFunction) {
            this.biFunction = biFunction;
        }

        public BiFunction<Memory, Instruction, Memory> getBiFunction() {
            return biFunction;
        }
    }

    private final Name name;
    private final int input0;
    private final int input1;
    private final int output;

    public Instruction(Name name, int input0, int input1, int output) {
        this.name = name;
        this.input0 = input0;
        this.input1 = input1;
        this.output = output;
    }

    public int getInput0() {
        return input0;
    }

    public int getInput1() {
        return input1;
    }

    public int getOutput() {
        return output;
    }

    public Name getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Instruction{" +
                "name=" + name +
                ", input0=" + input0 +
                ", input1=" + input1 +
                ", output=" + output +
                '}';
    }
}
