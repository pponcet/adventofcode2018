package com.pponcet.adventofcode.day19;

import java.util.Map;

public class Memory {
    private Map<Integer, Integer> registers;

    public Memory(Map<Integer, Integer> registers) {
        this.registers = registers;
    }

    public Integer get(int key) {
        return registers.get(key);
    }

    public Map<Integer, Integer> getRegisters() {
        return registers;
    }

    public void set(int key, int value){
        registers.put(key, value);
    }

    @Override
    public String toString() {
        return "Memory{" + registers +
                '}';
    }
}
