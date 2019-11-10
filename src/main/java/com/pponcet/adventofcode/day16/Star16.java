package com.pponcet.adventofcode.day16;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;


@Component
public class Star16 {
    private static final Logger logger = LoggerFactory.getLogger(Star16.class);

    public static void execute() {
        String fileName = "./src/main/resources/dataStar16.txt";

        int result = 0;
        try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
            List<String> lines = stream.collect(toList());

            for(int i = 0; i < lines.size() - 3; i+=4){

                if(extractSample(lines.get(i), lines.get(i+1), lines.get(i+2)).calculateNumberBehaviour() >= 3){
                        result++;
                    }
            }

        } catch (IOException e) {
            logger.error(e.getMessage());
        }

        logger.info("result: " + result);
    }

    public static Sample extractSample(String line0, String line1, String line2){
        int offset = 0;
        if(line1.length() > 7) {
            offset = 1;
        }
        return new Sample(
                Integer.valueOf(line0.substring(9, 10)),
                Integer.valueOf(line0.substring(12, 13)),
                Integer.valueOf(line0.substring(15, 16)),
                Integer.valueOf(line0.substring(18, 19)),
                Integer.valueOf(line1.substring(offset+2, offset+3)),
                Integer.valueOf(line1.substring(offset+4, offset+5)),
                Integer.valueOf(line1.substring(offset+6, offset+7)),
                Integer.valueOf(line2.substring(9, 10)),
                Integer.valueOf(line2.substring(12, 13)),
                Integer.valueOf(line2.substring(15, 16)),
                Integer.valueOf(line2.substring(18, 19))
        );
    }

}
