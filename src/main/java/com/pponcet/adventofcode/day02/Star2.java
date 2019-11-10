package com.pponcet.adventofcode.day02;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toMap;

@Component
public class Star2 {
    private static final Logger logger = LoggerFactory.getLogger(Star2.class);

    public static void execute(){

        String fileName = "./src/main/resources/dataStar2.txt";

        long result = countFrequencies(fileName, 2) * countFrequencies(fileName, 3);
        logger.info("result: "+result);

    }

    public static Map<Character, Integer> calculateFrequencies(String s){
        return s.chars().boxed()
                .collect(toMap(
                        k -> (char) k.intValue(),
                        v -> 1,
                        Integer::sum));

    }

    public static long countFrequencies(String fileName, int timesAChar){
        long countFrequence = 0L;
        try (Stream<String> stream = Files.lines(Paths.get(fileName))) {

             countFrequence = stream.map(Star2::calculateFrequencies).filter(m -> m.containsValue(timesAChar))
                    .count();

        } catch (IOException e) {
            logger.error(e.getMessage());
        }

        return countFrequence;
    }


}
