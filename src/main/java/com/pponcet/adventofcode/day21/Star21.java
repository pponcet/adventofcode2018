package com.pponcet.adventofcode.day21;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import java.util.stream.Stream;


@Component
public class Star21 {
    private static final Logger logger = LoggerFactory.getLogger(Star21.class);

    public static void execute() {
        String fileName = "./src/main/resources/dataStar21.txt";
        int result = 0;

        try (Stream<String> stream = Files.lines(Paths.get(fileName))) {





        } catch (IOException e) {
            logger.error(e.getMessage());
        }

        logger.info("result: " + result);
    }



}
