package com.pponcet.adventofcode.day17;

import com.pponcet.adventofcode.day16.Sample;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;
import static java.util.AbstractMap.SimpleEntry;
import static java.util.Map.*;


@Component
public class Star17 {
    private static final Logger logger = LoggerFactory.getLogger(Star17.class);

    public static void execute() {
        String fileName = "./src/main/resources/dataStar17.txt";


        long result = 0L;
        try (Stream<String> stream = Files.lines(Paths.get(fileName))) {

            Map<Point, Character> clayVeins = stream
                    .flatMap(Star17::extractClay)
                    .collect(toMap(
                            Entry::getKey,
                            Entry::getValue,
                            (ch1, ch2) -> ch1
                    ));

            Point source = new Point(500, 0);
            clayVeins.put(source, '+');
            //clayVeins.put(new Point(502, 11), '#');

            int maxY = clayVeins.keySet().stream()
                    .map(Point::getY)
                    .mapToInt(Integer::intValue)
                    .max().getAsInt();

            StateOfWater state = new StateOfWater();
            searchWater(clayVeins, source, state, maxY);
            showFile(clayVeins);

            result = clayVeins.values().stream()
                    .filter(c -> c.equals('|'))
                    .count();

            logger.info("map: "+clayVeins);



        } catch (IOException e) {
            logger.error(e.getMessage());
        }

        logger.info("result: " + result);
    }

    public static Stream<Entry<Point, Character>> extractClay(String line){
        char first = line.charAt(0);
        Map<Point, Character> clays = new HashMap<>();
        if(first == 'x'){
            int x = Integer.valueOf(line.substring(2, line.indexOf(",")));
            int yFrom = Integer.valueOf(line.substring(line.indexOf(",")+4, line.indexOf(".")));
            int yTo = Integer.valueOf(line.substring(line.indexOf(".")+2));
            for(int i = yFrom; i <= yTo; i++){
                clays.put(new Point(x, i), '#');
            }
        }

        if(first == 'y'){
            int y = Integer.valueOf(line.substring(2, line.indexOf(",")));
            int xFrom = Integer.valueOf(line.substring(line.indexOf(",")+4, line.indexOf(".")));
            int xTo = Integer.valueOf(line.substring(line.indexOf(".")+2));
            for(int i = xFrom; i <= xTo; i++){
                clays.put(new Point(i, y), '#');
            }
        }

        return clays.entrySet().stream();
    }

    public static StateOfWater searchWater(Map<Point, Character> clayVeins, Point currentPoint, StateOfWater state, int maxY){
        //show(clayVeins);
        showFile(clayVeins);
        Point pointBelow = new Point(currentPoint.getX(), currentPoint.getY() + 1);
        if(pointBelow.getY() > maxY){
            state.bottom(true);
        }else{
            if(canFall(clayVeins, currentPoint)){
                clayVeins.put(pointBelow, '|');
                state = searchWater(clayVeins, pointBelow, state, maxY);
            }else{
                state.spread(true);
            }

            if(state.canSpread()){
                state.spread(spreadLine(clayVeins, currentPoint, state, maxY));
            }
        }

        return state;
    }

    public static boolean spreadLine(Map<Point, Character> clayVeins, Point currentPoint, StateOfWater state, int maxY){
        if(state.reachBottom()){
            state.spread(false);
            return true;
        }else{
            StateOfWater left = spreadIntoDirection(clayVeins, currentPoint, maxY, -1);
            StateOfWater right = spreadIntoDirection(clayVeins, currentPoint, maxY, 1);
            return !left.reachBottom() && !right.reachBottom() && left.canSpread() == right.canSpread() ;
        }
    }

    private static StateOfWater spreadIntoDirection(Map<Point, Character> clayVeins, Point currentPoint, int maxY, int direction) {
        StateOfWater state = new StateOfWater();
        StateOfWater state2;
        boolean foundClay = false;
        Point cPoint = currentPoint;
        do{
            cPoint = new Point(cPoint.getX()+direction, cPoint.getY());
            state.spread(!canFall(clayVeins, cPoint));
            if(!state.canSpread()){
                clayVeins.put(cPoint, '|');
                state = searchWater(clayVeins, cPoint, state, maxY);

            }else{
                if(clayVeins.containsKey(cPoint) && clayVeins.get(cPoint).equals('#')){
                    foundClay = true;
                }else{
                    clayVeins.put(cPoint, '|');
                }
            }

        }while(!foundClay && state.canSpread());
        return state;
    }

    public static boolean canFall(Map<Point, Character> clayVeins, Point currentPoint){
        return !clayVeins.containsKey(new Point(currentPoint.getX(), currentPoint.getY() + 1));
    }

    public static void show(Map<Point, Character> clayVeins){
        for(int j = 0; j < 14; j++){
            for(int i = 494; i < 507; i++){

                Point point = new Point(i, j);
                if(clayVeins.containsKey(point)){
                    System.out.print(clayVeins.get(point));
                }else{
                    System.out.print(".");
                }

            }
            System.out.print("\n");
        }
        System.out.print("\n");
    }

    public static void showFile(Map<Point, Character> clayVeins){

        String outputName = "./src/main/resources/output17.txt";
        StringBuilder sb = new StringBuilder();
        int minX = clayVeins.keySet().stream()
                .map(Point::getX)
                .mapToInt(Integer::intValue)
                .min().getAsInt();

        int maxX = clayVeins.keySet().stream()
                .map(Point::getX)
                .mapToInt(Integer::intValue)
                .max().getAsInt();

        int minY = clayVeins.keySet().stream()
                .map(Point::getY)
                .mapToInt(Integer::intValue)
                .min().getAsInt();

        int maxY = clayVeins.keySet().stream()
                .map(Point::getY)
                .mapToInt(Integer::intValue)
                .max().getAsInt();

        for(int j = minY; j <= maxY; j++){
            for(int i = minX; i <= maxX; i++){

                Point point = new Point(i, j);
                if(clayVeins.containsKey(point)){
                    sb.append(clayVeins.get(point));
                }else{
                   sb.append(".");
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

}
