package com.pponcet.adventofcode.day11;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

@Component
public class Star11 {
    private static final Logger logger = LoggerFactory.getLogger(Star11.class);

    public static void execute() {

        int serialNumber = 3613;
        Map<Point, Long> powerLevels = new HashMap<>();
        for(int i = 1; i <= 300; i++){
            for(int j = 1; j <= 300; j++){
                Point point = new Point(i, j);
                long calc = calculatePowerLevel(point, serialNumber);
                powerLevels.put(point, calc);
            }
        }

        Map<Point, Long> sumPowerLevels = new HashMap<>();
        long sum = 0L;
        for(int i = 1; i <= 298; i++){
            for(int j = 1; j <= 298; j++){
                Point point = new Point(i, j);
                sum = 0L;

                for(int i2 = i; i2 <= i+2; i2++){
                    for(int j2 = j; j2 <= j+2; j2++){

                        sum += powerLevels.get(new Point(i2, j2));
                    }
                }
                sumPowerLevels.put(point, sum);
            }

        }

        Point result = sumPowerLevels.entrySet().stream()
                .max(Comparator.comparing(Map.Entry::getValue))
                .get().getKey();


        logger.info("result: " + result);

    }

    public static long calculatePowerLevel(Point p, int serialNumber){
        long rackId = 10+p.getX();
        long tempLevel = (rackId*p.getY()+serialNumber)*rackId;
        if(tempLevel < 100){
            return -5;
        }else{
            return (tempLevel / 100) % 10 - 5 ;
        }
    }
}
