package com.pponcet.adventofcode.day19;

import java.util.function.BiFunction;

public class Borr implements BiFunction<Memory, Instruction, Memory> {
    public static final BiFunction<Memory, Instruction, Memory> biFunction = new Borr();

    @Override
    public Memory apply(Memory memory, Instruction instruction) {
        memory.set(instruction.getOutput(), memory.get(instruction.getInput0()) | memory.get(instruction.getInput1()));
        return memory;
    }
}
