package com.pponcet.adventofcode.day19;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static com.pponcet.adventofcode.day19.Instruction.Name.*;
import static com.pponcet.adventofcode.day19.Instruction.nullInstruction;
import static java.util.stream.Collectors.toList;


@Component
public class Star19 {
    private static final Logger logger = LoggerFactory.getLogger(Star19.class);

    public static void execute() {
        String fileName = "./src/main/resources/dataStar19.txt";
        int result = 0;


        final int ip = 4;
        int instructionIndex = 0;
        try (Stream<String> stream = Files.lines(Paths.get(fileName))) {

            List<Instruction> instructions = stream.map(Star19::createInstruction).collect(toList());
            Map<Integer, Integer> registers = new HashMap<>();
            for(int i = 0; i < 6; i++){
                registers.put(i, 0);
            }
            Memory memory = new Memory(registers);
            Instruction currentInstruction = nullInstruction;
            memory.set(0, 1);

            do{

                currentInstruction = instructions.get(instructionIndex);
                memory.set(ip, instructionIndex);
                memory = currentInstruction.getName().getBiFunction().apply(memory, currentInstruction);
                //logger.info("instructionIndex before: "+instructionIndex);
                instructionIndex = memory.get(ip) +1;

                //logger.info("currentInstruction: "+currentInstruction);
                //logger.info("memory: "+memory);
                /*int mem0 = memory.get(0);
                if(mem0 != 0){
                    logger.info("mem0: "+mem0);
                }*/
                //logger.info("instructionIndex after: "+instructionIndex);
            }while(instructionIndex < instructions.size());

            logger.info("final memory: "+memory);
            result = memory.get(0);

        } catch (IOException e) {
            logger.error(e.getMessage());
        }

        logger.info("result: " + result);
    }

    public static Instruction createInstruction(String line){
        String[] infos = line.split(" ");
        String name = infos[0];
        int input0 = Integer.valueOf(infos[1]);
        int input1 = Integer.valueOf(infos[2]);
        int output = Integer.valueOf(infos[3]);

        switch(name){
            case "addr":
                return new Instruction(ADDR, input0, input1, output);
            case "addi":
                return new Instruction(ADDI, input0, input1, output);
            case "mulr":
                return new Instruction(MULR, input0, input1, output);
            case "muli":
                return new Instruction(MULI, input0, input1, output);
            case "banr":
                return new Instruction(BANR, input0, input1, output);
            case "bani":
                return new Instruction(BANI, input0, input1, output);
            case "borr":
                return new Instruction(BORR, input0, input1, output);
            case "bori":
                return new Instruction(BORI, input0, input1, output);
            case "setr":
                return new Instruction(SETR, input0, input1, output);
            case "seti":
                return new Instruction(SETI, input0, input1, output);
            case "gtir":
                return new Instruction(GTIR, input0, input1, output);
            case "gtri":
                return new Instruction(GTRI, input0, input1, output);
            case "gtrr":
                return new Instruction(GTRR, input0, input1, output);
            case "eqir":
                return new Instruction(EQIR, input0, input1, output);
            case "eqri":
                return new Instruction(EQRI, input0, input1, output);
            case "eqrr":
                return new Instruction(EQRR, input0, input1, output);
            default :
                return nullInstruction;

        }

    }

}
