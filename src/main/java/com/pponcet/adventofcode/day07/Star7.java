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

@Component
public class Star7 {
    private static final Logger logger = LoggerFactory.getLogger(Star7.class);

    public static void execute() {

        String fileName = "./src/main/resources/dataStar7.txt";


        String result = "";
        try (Stream<String> stream = Files.lines(Paths.get(fileName))) {


            //List<String> instructions = stream.collect(Collectors.toList());
            List<String> instructions = new ArrayList<>();
            instructions.add("Step C must be finished before step A can begin.");
            instructions.add("Step C must be finished before step F can begin.");
            instructions.add("Step A must be finished before step B can begin.");
            instructions.add("Step A must be finished before step D can begin.");
            instructions.add("Step B must be finished before step E can begin.");
            instructions.add("Step D must be finished before step E can begin.");
            instructions.add("Step F must be finished before step E can begin.");

            Map<Character, List<Character>> mapChar = new HashMap<>();
            Set<Character> letters = new HashSet<>();
            for (String instruction : instructions) {

                char before = instruction.charAt(5);
                char after = instruction.charAt(36);

                logger.info(before + " --> " + after);

                letters.add(before);
                letters.add(after);

                if (!mapChar.containsKey(after)) {
                    List<Character> characters = new ArrayList<>();
                    characters.add(before);
                    mapChar.put(after, characters);

                } else {
                    mapChar.get(after).add(before);
                }

            }

            for (Character chara : mapChar.keySet()) {
                Collections.sort(mapChar.get(chara));
            }

            logger.info("map: \n" + printMap(mapChar));
            logger.info("letters: " + letters);

            List<Character> firsts = letters.stream()
                    .filter(c -> !mapChar.keySet().contains(c))
                    .sorted()
                    .collect(Collectors.toList());

            StringBuilder sb = new StringBuilder();
            for (Character f : firsts) {
                //sb.append(f+":");
                sb = recursiveFind(f, mapChar, sb);
            }

            logger.info("result: " + sb.toString());


        } catch (IOException e) {
            logger.error(e.getMessage());
        }


    }

    public static List<Character> findNext(Character c, Map<Character, List<Character>> map) {
        return map.keySet().stream()
                .filter(ch -> map.get(ch).contains(c) && map.get(ch).get(map.get(ch).size() - 1).equals(c))
                .sorted()
                .collect(Collectors.toList());
    }

    public static StringBuilder recursiveFind(Character c, Map<Character, List<Character>> map, StringBuilder sb) {
        List<Character> list = findNext(c, map);
        for (Character ch : list) {
            sb.append(ch);
            sb = recursiveFind(ch, map, sb);
        }
        return sb;
    }

    public static String printMap(Map<Character, List<Character>> map) {
        return map.entrySet().stream()
                .sorted(Comparator.comparing(Map.Entry::getKey))
                .map(e -> e.getKey() + "=" + e.getValue())
                .collect(Collectors.joining("\n"));
    }

}
