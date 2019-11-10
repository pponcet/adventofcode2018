package com.pponcet.adventofcode.day19;

import java.util.function.BiFunction;

public class Bani implements BiFunction<Memory, Instruction, Memory> {
    public static final BiFunction<Memory, Instruction, Memory> biFunction = new Bani();

    @Override
    public Memory apply(Memory memory, Instruction instruction) {
        memory.set(instruction.getOutput(), memory.get(instruction.getInput0()) & instruction.getInput1());
        return memory;
    }
}
