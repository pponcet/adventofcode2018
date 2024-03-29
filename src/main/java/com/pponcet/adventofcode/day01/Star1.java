package com.pponcet.adventofcode.day01;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Paths;

import java.util.stream.Stream;

@Component
public class Star1 {
    private static final Logger logger = LoggerFactory.getLogger(Star1.class);

    public static void execute(){

        String fileName = "./src/main/resources/dataStar1.txt";

        long result = 0L;
        try (Stream<String> stream = Files.lines(Paths.get(fileName))) {


            result = stream.mapToLong(Long::valueOf)
            .sum();

        } catch (IOException e) {
            logger.error(e.getMessage());
        }

        logger.info("result: "+result);


    }



}
