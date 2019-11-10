package com.pponcet.adventofcode.day06;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toMap;
import static java.util.AbstractMap.SimpleEntry;

@Component
public class Star6 {
    private static final Logger logger = LoggerFactory.getLogger(Star6.class);

    public static void execute(){

        String fileName = "./src/main/resources/dataStar6.txt";

        int result = 0;
        try (Stream<String> stream = Files.lines(Paths.get(fileName))) {

        List<Point> list = stream.map(s -> new Point(Integer.valueOf(s.substring(0, s.indexOf(','))), Integer.valueOf(s.substring(s.indexOf(',')+2))))
        .collect(Collectors.toList());

        int minX = list.stream().mapToInt(Point::getX).min().getAsInt();
        int maxX = list.stream().mapToInt(Point::getX).max().getAsInt();
        int minY = list.stream().mapToInt(Point::getY).min().getAsInt();
        int maxY = list.stream().mapToInt(Point::getY).max().getAsInt();

        Map<Point, Point> mapToFixedPoint = new HashMap<>();
        for(int i=minX; i<=maxX; i++){
            for(int j=minY; j<=maxY; j++){
                Point point = new Point(i, j);
                mapToFixedPoint.put(point, searchClosestPoint(point, list));
            }
        }

        Map<Point, Boolean> infinitePoints = new HashMap<>(
                list.stream()
                .map(p -> new SimpleEntry<>(p, false))
                .collect(toMap(
                        SimpleEntry::getKey,
                        SimpleEntry::getValue
                ))
        );

        mapToFixedPoint.keySet().stream()
                .filter(p -> p.getX() == minX  || p.getX() == maxX
                || p.getY() == minY || p.getY() == maxY)
                .forEach(pinfty -> infinitePoints.replace(mapToFixedPoint.get(pinfty), true));

        Map<Point, Integer> points = mapToFixedPoint.entrySet().stream()
                .map(e -> new SimpleEntry<>(e.getValue(), 1) {})
                .collect(toMap(
                        SimpleEntry::getKey,
                        SimpleEntry::getValue,
                        Integer::sum));

        Map<Point, Integer> pointsNotInfinite = points.entrySet().stream()
                .filter(p -> !p.getKey().equals(Point.equallyFar))
                .filter(p -> !infinitePoints.get(p.getKey()))
                .collect(toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue));


        result = pointsNotInfinite.entrySet().stream().max(Map.Entry.comparingByValue()).get().getValue();

        } catch (IOException e) {
            logger.error(e.getMessage());
        }

        logger.info("result:" +result);
    }

    public static Point searchClosestPoint(Point point, List<Point> list){
        Point candidatePoint = null;
        int size = 0;

        do{
            size++;
            List<Point> pointsSearched = fillList(point, size);
            for(Point p : pointsSearched){
                for(Point cp : list){
                    if(cp.equals(p)){
                        if(candidatePoint != null){
                            return Point.equallyFar;
                        }else{
                            candidatePoint = cp;
                        }
                    }
                }
            }
            if(candidatePoint != null){
                return candidatePoint;
            }
        }while(true);

    }

    public static List<Point> fillList(Point p, int size){
        List<Point> pointsSearched = new ArrayList<>();
        for(int i=p.getX()-size; i<=p.getX()+size;i++){
            for(int j=p.getY()-size; j<=p.getY()+size; j++){
                if(Math.abs(p.getX() - i) + Math.abs(p.getY() - j) <= size){
                    pointsSearched.add(new Point(i, j));
                }
            }
        }

        return pointsSearched;
    }



}
