package com.pponcet.adventofcode.day19;

import java.util.function.BiFunction;

public class Gtri implements BiFunction<Memory, Instruction, Memory> {
    public static final BiFunction<Memory, Instruction, Memory> biFunction = new Gtri();

    @Override
    public Memory apply(Memory memory, Instruction instruction) {
        int value = 0;
        if(memory.get(instruction.getInput0()) > instruction.getInput1()){
            value = 1;
        }
        memory.set(instruction.getOutput(), value);
        return memory;
    }
}
