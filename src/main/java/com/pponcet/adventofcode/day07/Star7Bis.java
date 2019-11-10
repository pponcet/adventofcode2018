package com.pponcet.adventofcode.day07;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Map.*;

@Component
public class Star7Bis {
    private static final Logger logger = LoggerFactory.getLogger(Star7Bis.class);

    public static void execute() {

        String fileName = "./src/main/resources/dataStar7.txt";

        String result = "";
        try (Stream<String> stream = Files.lines(Paths.get(fileName))) {

            List<String> instructions = stream.collect(Collectors.toList());

            Map<Character, List<Character>> steps = new HashMap<>();
            for (String instruction : instructions) {

                char before = instruction.charAt(5);
                char after = instruction.charAt(36);

                //build all steps
                steps.computeIfAbsent(before, key -> new ArrayList<>());
                steps.computeIfAbsent(after, key -> new ArrayList<>());
                steps.get(after).add(before);
            }

            StringBuilder sb = new StringBuilder();
            printMap(steps);
            while(!steps.isEmpty()) {
                Optional<Character> nextStep = findNextStep(steps);
                if(nextStep.isPresent()){
                    Character step = nextStep.get();
                    steps.remove(step);
                    steps.values().forEach(list -> list.remove(step));
                    sb.append(step);
                }
                printMap(steps);
            }

            logger.info("result: " + sb.toString());

        } catch (IOException e) {
            logger.error(e.getMessage());
        }

    }

    public static String printMap(Map<Character, List<Character>> map) {
        return map.entrySet().stream()
                .sorted(Comparator.comparing(Map.Entry::getKey))
                .map(e -> e.getKey() + "=" + e.getValue())
                .collect(Collectors.joining("\n"));
    }

    public static Optional<Character> findNextStep(Map<Character, List<Character>> steps){
        return steps.entrySet().stream()
                .filter(e -> e.getValue().isEmpty())
                .map(Entry::getKey)
                .min(Comparator.comparing(Character::charValue));

    }

}
