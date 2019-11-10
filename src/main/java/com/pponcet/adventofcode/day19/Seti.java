package com.pponcet.adventofcode.day19;

import java.util.function.BiFunction;

public class Seti implements BiFunction<Memory, Instruction, Memory> {
    public static final BiFunction<Memory, Instruction, Memory> biFunction = new Seti();

    @Override
    public Memory apply(Memory memory, Instruction instruction) {
        memory.set(instruction.getOutput(), instruction.getInput0());
        return memory;
    }
}
