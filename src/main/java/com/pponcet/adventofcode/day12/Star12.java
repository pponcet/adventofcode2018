package com.pponcet.adventofcode.day12;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static java.util.AbstractMap.SimpleEntry;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

@Component
public class Star12 {
    private static final Logger logger = LoggerFactory.getLogger(Star12.class);

    public static void execute() {

        String fileName = "./src/main/resources/dataStar12.txt";
        String input = "#..####.##..#.##.#..#.....##..#.###.#..###....##.##.#.#....#.##.####.#..##.###.#.......#............";

        int result = 0;
        try (Stream<String> stream = Files.lines(Paths.get(fileName))) {

            Map<String, String> rules = stream.map(Star12::extractRule)
                    .collect(toMap(
                            SimpleEntry::getKey,
                            SimpleEntry::getValue
                    ));

            StringBuilder sb = new StringBuilder(input);
            sb.insert(0, "....................");
            sb.append("....................");
            String str = sb.toString();

            for(int i = 0; i < 20; i++){
                str = calculateNextIteration(rules, str);
            }
            result = calculatePoints(str);

        } catch (IOException e) {
            logger.error(e.getMessage());
        }

        logger.info("result: " + result);

    }

    private static String calculateNextIteration(Map<String, String> map, String str) {
        StringBuilder iteration = new StringBuilder();
        iteration.append("..");
        for(int i = 2; i<str.length() - 2; i++){
            iteration.append(findNextSymbol(map, str.substring(i-2, i+3)));
        }
        iteration.append("..");
        return iteration.toString();
    }

    public static SimpleEntry<String, String> extractRule(String s){
        String key = s.substring(0, 5);
        String value = s.substring(9, 10);
        return new SimpleEntry<>(key, value);
    }

    public static String findNextSymbol(Map<String, String> map, String sequence){
        if(map.keySet().contains(sequence)){
            return map.get(sequence);
        }else{
            return ".";
        }
    }

    public static int calculatePoints(String str){
        int sum = 0;
        List<Integer> chars = str.chars().boxed()
                .collect(toList());
        for(int i = -20; i<str.length() - 20; i++){
            if(chars.get(i+20) == 35){
                sum += i;
            }

        }

        return sum;
    }

}
