package com.pponcet.adventofcode.day03;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toMap;

@Component
public class Star3 {
    private static final Logger logger = LoggerFactory.getLogger(Star3.class);

    public static void execute(){

        String fileName = "./src/main/resources/dataStar3.txt";

        long result = 0L;

        try (Stream<String> stream = Files.lines(Paths.get(fileName))) {

           result = stream.flatMap(Star3::calculateClaim)
               .collect(toMap(
                       Map.Entry::getKey,
                       Map.Entry::getValue,
                Integer::sum
            )).entrySet().stream()
                   .filter(m -> m.getValue() > 1)
                   .count();

        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        logger.info("result: "+result);

    }

    public static Stream<Map.Entry<Point, Integer>> calculateClaim(String s){
        Map<Point, Integer> map = new HashMap<>();
        String reducedString = s.substring(s.indexOf('@')).substring(2);
        int leftEdge = Integer.valueOf(reducedString.substring(0, reducedString.indexOf(',')));
        int topEdge = Integer.valueOf(reducedString.substring(reducedString.indexOf(',')+1, reducedString.indexOf(':')));
        int width = Integer.valueOf(reducedString.substring(reducedString.indexOf(':')+2, reducedString.indexOf('x')));
        int height = Integer.valueOf(reducedString.substring(reducedString.indexOf('x')+1));

        for(int i=leftEdge; i<leftEdge+width; i++){
            for(int j=topEdge; j<topEdge+height; j++){
                map.put(new Point(i, j), 1);
            }
        }

        return map.entrySet().stream();
    }
}
