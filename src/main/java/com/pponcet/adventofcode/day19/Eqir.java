package com.pponcet.adventofcode.day19;

import java.util.function.BiFunction;

public class Eqir implements BiFunction<Memory, Instruction, Memory> {
    public static final BiFunction<Memory, Instruction, Memory> biFunction = new Eqir();

    @Override
    public Memory apply(Memory memory, Instruction instruction) {
        int value = 0;
        if(instruction.getInput0() == memory.get(instruction.getInput1())){
            value = 1;
        }
        memory.set(instruction.getOutput(), value);
        return memory;
    }
}
