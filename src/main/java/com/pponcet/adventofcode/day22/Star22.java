package com.pponcet.adventofcode.day22;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;


@Component
public class Star22 {
    private static final Logger logger = LoggerFactory.getLogger(Star22.class);

    public enum RegionType{
        ROCKY(0),
        WET(1),
        NARROW(2);

        private final int risk;

        RegionType(int risk) {
            this.risk = risk;
        }

        public int getRisk() {
            return risk;
        }
    }

    public static void execute() {

        long multiplyY = 16807L;
        long multiplyX = 48271L;
        long modulo = 20183L;

        long depth = 6084L;
        Point target = new Point(14,709);

        //long depth = 510L;
        //Point target = new Point(10,10);

        Map<Point, Long> geologicalIndexes = new HashMap<>();
        Map<Point, Long> erosionLevels = new HashMap<>();
        Point mouth = new Point(0, 0);
        geologicalIndexes.put(mouth, 0L);
        erosionLevels.put(mouth, 0L);

        for(int i = 1; i <= target.getX() ; i++){
            Point point = new Point(i, 0);
            long geologicalIndex = i * multiplyY;
            geologicalIndexes.put(point, geologicalIndex);
            erosionLevels.put(point,(geologicalIndex + depth) % modulo);
        }

        for(int j = 1; j <= target.getY() ; j++){
            Point point = new Point(0, j);
            long geologicalIndex = j * multiplyX;
            geologicalIndexes.put(point, geologicalIndex);
            erosionLevels.put(point,(geologicalIndex + depth) % modulo);
        }

        for(int j = 1; j <= target.getY() ; j++){
            for(int i = 1; i <= target.getX() ; i++){
                Point point = new Point(i, j);
                long geologicalIndex = erosionLevels.get(new Point(i-1, j)) * erosionLevels.get(new Point(i, j-1));
                geologicalIndexes.put(point, geologicalIndex);
                erosionLevels.put(point,(geologicalIndex + depth) % modulo);
            }
        }
        geologicalIndexes.put(target, 0L);
        erosionLevels.put(target, 0L);

        logger.info("erosion: "+erosionLevels);

        long result = erosionLevels.values().stream()
                .mapToLong(Long::longValue)
                .map(l -> l % 3)
                .sum();

        logger.info("result: " + result);
    }

}
