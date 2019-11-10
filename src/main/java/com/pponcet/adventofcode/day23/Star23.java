package com.pponcet.adventofcode.day23;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Component
public class Star23 {
    private static final Logger logger = LoggerFactory.getLogger(Star23.class);

    public static void execute() {
        String fileName = "./src/main/resources/dataStar23.txt";
        long result = 0L;

        try (Stream<String> stream = Files.lines(Paths.get(fileName))) {

            List<NanoBot> nanoBots = stream.map(Star23::extractNanoBot)
                    .collect(Collectors.toList());

            logger.info("nanoBots: "+nanoBots);

            NanoBot strongest = nanoBots.stream()
                    .max(Comparator.comparing(NanoBot::getRadius)).get();

            logger.info("strongest: "+strongest);

            result = nanoBots.stream()
                    .filter(n -> calculateDistance(strongest.getPosition(), n.getPosition()) <= strongest.getRadius())
                    .count();

        } catch (IOException e) {
            logger.error(e.getMessage());
        }

        logger.info("result: " + result);
    }

    public static NanoBot extractNanoBot(String line){
        int index = line.indexOf(",");
        int x = Integer.valueOf(line.substring(line.indexOf("<") + 1, index));
        line = line.substring(index + 1);
        int y = Integer.valueOf(line.substring(0, line.indexOf(",")));
        line = line.substring(line.indexOf(",") + 1);
        int z = Integer.valueOf(line.substring(0, line.indexOf(">")));
        int radius = Integer.valueOf(line.substring(line.indexOf("r=") + 2));

        return new NanoBot(new Point(x, y, z), radius);
    }

    public static int calculateDistance(Point from, Point to){
        return Math.abs(from.getX() - to.getX())
                + Math.abs(from.getY() - to.getY())
                + Math.abs(from.getZ() - to.getZ());
    }

}
