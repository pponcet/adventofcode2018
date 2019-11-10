package com.pponcet.adventofcode.day18;

import com.pponcet.adventofcode.day17.Point;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;


@Component
public class Star18 {
    private static final Logger logger = LoggerFactory.getLogger(Star18.class);

    public static void execute() {
        String fileName = "./src/main/resources/dataStar18.txt";

        long result = 0L;
        try (Stream<String> stream = Files.lines(Paths.get(fileName))) {

            List<String> lines = stream.collect(toList());
            Map<Point, Character> forest = extractPoints(lines);


            int iteration = 0;
            do{
                iteration++;
                forest = getNextState(forest);
                //showFile(forest);
            }while(iteration < 10);

            result = forest.values().stream()
                    .filter(c -> c.equals('|'))
                    .count()
                    *
                    forest.values().stream()
                    .filter(c -> c.equals('#'))
                    .count();

        } catch (IOException e) {
            logger.error(e.getMessage());
        }

        logger.info("result: " + result);
    }

    public static Map<Point, Character> extractPoints(List<String> lines){

        Map<Point, Character> points = new HashMap<>();

        for(int j = 1; j <= lines.size(); j++){
            String line = lines.get(j-1);
            for(int i = 1; i <= line.length(); i++){
                points.put(new Point(i, j), line.charAt(i-1));
            }
        }

        return points;
    }

    public static void showFile(Map<Point, Character> forest){

        String outputName = "./src/main/resources/output18.txt";
        StringBuilder sb = new StringBuilder();
        int minX = forest.keySet().stream()
                .map(Point::getX)
                .mapToInt(Integer::intValue)
                .min().getAsInt();

        int maxX = forest.keySet().stream()
                .map(Point::getX)
                .mapToInt(Integer::intValue)
                .max().getAsInt();

        int minY = forest.keySet().stream()
                .map(Point::getY)
                .mapToInt(Integer::intValue)
                .min().getAsInt();

        int maxY = forest.keySet().stream()
                .map(Point::getY)
                .mapToInt(Integer::intValue)
                .max().getAsInt();

        for(int j = minY; j <= maxY; j++){
            for(int i = minX; i <= maxX; i++){

                Point point = new Point(i, j);
                if(forest.containsKey(point)){
                    sb.append(forest.get(point));
                }else{
                   sb.append("X");
                }

            }
            sb.append("\n");
        }
        Path path = Paths.get(outputName);
        byte[] strToBytes = sb.toString().getBytes();
        try {
            Files.write(path, strToBytes);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    public static Map<Point, Character> getNextState(Map<Point, Character> forest){
        Map<Point, Character> nextForest = new HashMap<>();
        for(Point point : forest.keySet()) {
            List<Point> points = point.adjacentPoints();
            long numberTrees = count(forest, points, '|');
            long numberLumberyards = count(forest, points, '#');
            switch (forest.get(point)) {
                case '.':
                    if (numberTrees >= 3) {
                        nextForest.put(point, '|');
                    }else{
                        nextForest.put(point, '.');
                    }
                    break;
                case '|':
                    if (numberLumberyards >= 3) {
                        nextForest.put(point, '#');
                    }else{
                        nextForest.put(point, '|');
                    }
                    break;
                case '#':
                    if (numberLumberyards == 0 || numberTrees == 0) {
                        nextForest.put(point, '.');
                    }else{
                        nextForest.put(point, '#');
                    }
                    break;
            }
        }
        return nextForest;
    }

    public static long count(Map<Point, Character> forest, List<Point> points, Character ch){
        return points.stream().filter(forest::containsKey)
                .map(forest::get)
                .filter(c -> c.equals(ch))
                .count();
    }

}
