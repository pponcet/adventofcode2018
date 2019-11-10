package com.pponcet.adventofcode.day05;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toMap;

@Component
public class Star5 {
    private static final Logger logger = LoggerFactory.getLogger(Star5.class);

    public static void execute(){

        String fileName = "./src/main/resources/dataStar5.txt";


        long result = 0L;


        try (Stream<String> stream = Files.lines(Paths.get(fileName))) {

            String input = stream.findFirst().get();

            String current = input;
            int indextoDelete = -1;
            boolean delete = false;
            boolean exitLoop = false;

            do {
                for (int i = 0; i < current.length() - 1; i++) {
                    if (current.charAt(i + 1) == current.charAt(i) + 32 || current.charAt(i) == current.charAt(i + 1) + 32) {
                        indextoDelete = i;
                        delete = true;
                        break;
                    }

                    if(i == current.length() -2){
                        exitLoop = true;
                    }
                }

                if(delete){
                    current = new StringBuilder(current).deleteCharAt(indextoDelete).deleteCharAt(indextoDelete).toString();
                }
                delete = false;

            }while(!exitLoop);
            logger.info("current: "+current);
            logger.info("result: "+current.length());


        } catch (IOException e) {
            logger.error(e.getMessage());
        }


    }



}
