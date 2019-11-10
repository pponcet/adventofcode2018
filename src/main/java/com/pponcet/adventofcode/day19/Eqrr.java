package com.pponcet.adventofcode.day19;

import java.util.function.BiFunction;

public class Eqrr implements BiFunction<Memory, Instruction, Memory> {
    public static final BiFunction<Memory, Instruction, Memory> biFunction = new Eqrr();

    @Override
    public Memory apply(Memory memory, Instruction instruction) {
        int value = 0;
        if(memory.get(instruction.getInput0()).equals(memory.get(instruction.getInput1()))){
            value = 1;
        }
        memory.set(instruction.getOutput(), value);
        return memory;
    }
}
