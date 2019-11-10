package com.pponcet.adventofcode.day15;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;

import static com.pponcet.adventofcode.day15.Unit.Side.*;
import static java.util.stream.Collectors.toList;
import static com.pponcet.adventofcode.day15.Unit.*;
import static com.pponcet.adventofcode.day15.Point.*;

import static java.util.stream.Collectors.toMap;
import static java.util.AbstractMap.SimpleEntry;
import static java.util.Map.*;

@Component
public class Star15 {
    private static final Logger logger = LoggerFactory.getLogger(Star15.class);
    private static final int obstacle = 2000;
    private static final int distanceToFill = 1000;

    public static void execute() {
        String fileName = "./src/main/resources/dataStar15.txt";

        int result = 0;

        try (Stream<String> stream = Files.lines(Paths.get(fileName))) {

            List<String> lines = stream.collect(toList());
            List<Unit> units = new ArrayList<>();
            Map<Point, Character> area = initializeAreaAndUnits(lines, units);

            int currentRound = 0;
            boolean foundTarget = true;
            do{
                currentRound++;
                units = orderUnits(units);

                for (Unit unit : units){
                    /*logger.info("----------------------"+currentRound);
                    logger.info("units: "+units);
                    logger.info("----------------------");
                    logger.info("----------------------");
                    logger.info("----------------------");*/

                    if(unit.isAlive()){
                        Map<Point, Character> areaWithUnits = fillAreaWithUnits(area, units);
                        drawMap(areaWithUnits);

                        List<Unit> targets = findTargets(units, unit.getSide());
                        if(targets.isEmpty()){
                            foundTarget = false;
                            break;
                        }

                        if(currentRound == 48){
                            logger.info("48");
                        }
                        Map<Unit, List<Point>> pointsInRange = findInRangePoints(area, targets);

                        if(!pointsInRange.isEmpty()){
                            boolean attackOccured = attackAttempt(unit, areaWithUnits, pointsInRange, units);
                            if(!attackOccured && existsValidPointInRange(areaWithUnits, pointsInRange)){
                                move(unit, areaWithUnits, pointsInRange);
                                attackAttempt(unit, areaWithUnits, pointsInRange, units);
                            }

                        }
                    }

                }
                logger.info("round: "+currentRound+" finished");
            }while(/*currentRound < 29*/ foundTarget);

            int sum = units.stream()
                    .filter(Unit::isAlive)
                    .mapToInt(Unit::getHp)
                    .sum();

            result = sum * (currentRound - 1);
            logger.info("sum: "+sum);
            logger.info("currentRound: "+currentRound);
            logger.info("units: "+units);

        } catch (IOException e) {
            logger.error(e.getMessage());
        }


        logger.info("result: " + result);
    }

    public static Map<Point, Character> initializeAreaAndUnits(List<String> lines, List<Unit> units){
        Map<Point, Character> area = new HashMap<>();
        for(int i = 0; i < lines.size(); i++){
            String line = lines.get(i);
            for(int j = 0; j < line.length(); j++){
                Point point = new Point(j, i);
                Character ch = line.charAt(j);
                if(ch.equals('G')){
                    units.add(new Unit(GOBLIN, point));
                    ch = '.';
                }
                if(ch.equals('E')){
                    units.add(new Unit(ELF, point));
                    ch = '.';
                }
                area.put(point, ch);
            }
        }
        return area;
    }

    public static List<Unit> orderUnits(List<Unit> units){
        return units.stream()
                .sorted()
                .collect(toList());
    }

    public static Map<Point, Character> fillAreaWithUnits(Map<Point, Character> area, List<Unit> units){
        Map<Point, Character> map = new HashMap<>(area);
        units.stream()
                .filter(Unit::isAlive)
                .forEach(u -> map.replace(u.getPosition(), u.getSide().getSymbol()));
        return map;
    }

    public static List<Unit> findTargets(List<Unit> units, Side side){
        return units.stream()
                .filter(Unit::isAlive)
                .filter(u -> !(u.getSide().equals(side)))
                .collect(toList());
    }

    public static Map<Unit, List<Point>> findInRangePoints(Map<Point, Character> area, List<Unit> targets){
        return targets.stream()
                .map(u -> new SimpleEntry<Unit, List<Point>>(u, new ArrayList<>(u.getPosition().getPointsInRange())))
                .collect(toMap(
                        SimpleEntry::getKey,
                        SimpleEntry::getValue
                ));
    }

    public static Unit findOpponent(Map<Point, Character> areaWithUnits, Point position, Map<Unit, List<Point>> pointsInRange){
        Unit unit = noSuitableUnit;
        Optional<Unit> unitInRange = pointsInRange.keySet().stream()
                .filter(u -> pointsInRange.get(u).contains(position))
                .sorted()
                .min(Comparator.comparing(Unit::getHp));
        if(unitInRange.isPresent()){
            unit = unitInRange.get();
        }
        return unit;
    }

    public static void move(Unit unit, Map<Point, Character> areaWithUnits, Map<Unit, List<Point>> pointsInRange){

        Point point = findNextPointToMove(unit, areaWithUnits, pointsInRange);


        if(!point.equals(noSuitablePoint)){
            /*logger.info(unit+" --> move: "+point);*/
            unit.setPosition(point);
        }

    }

    public static boolean attackAttempt(Unit unit, Map<Point, Character> areaWithUnits, Map<Unit, List<Point>> pointsInRange, List<Unit> units){
        boolean attackOccured = false;

        Unit opponent = findOpponent(areaWithUnits, unit.getPosition(), pointsInRange);
        if(opponent != noSuitableUnit){
            /*logger.info("before attack: "+"unit: "+unit+", opponent: "+opponent);*/

            units.stream()
                    .filter(u -> u.equals(opponent))
                    .findFirst()
                    .get()
                    .takeAHit();

            /*logger.info("after attack: "+"unit: "+unit+", opponent: "+opponent);*/
            attackOccured = true;
        }
        return attackOccured;
    }

    public static Point findNextPointToMove(Unit unit, Map<Point, Character> areaWithUnits, Map<Unit, List<Point>> pointsInRange){
        Point next = noSuitablePoint;
        Map<Point, Map<Point, Integer>> allPaths = new HashMap<>();

        for(Unit target : pointsInRange.keySet()){
            for(Point pointInRange : pointsInRange.get(target)){
                char symbol = areaWithUnits.get(pointInRange);
                if(symbol == '.'){
                    Map<Point, Integer> distances = initializeDistanceMap(unit, areaWithUnits, pointInRange);
                    fillDistances(distances);
                    allPaths.put(pointInRange, distances);
                }

            }

        }

        if(unit.getPosition().equals(new Point(13, 11))){
            logger.info("unit: "+unit.getPosition().getPointsInRange());
        }
        /*logger.info("unit: "+unit.getPosition().getPointsInRange());*/

        Map<Point, Map<Point, Integer>> mapForAdjacent = new HashMap<>();
        for(Point point : allPaths.keySet()){
            Map<Point, Integer> allPathsForThisPoint = allPaths.get(point);
            for(Point p2 : allPathsForThisPoint.keySet()){
                if(unit.getPosition().getPointsInRange().contains(p2)){
                    Integer distance = allPathsForThisPoint.get(p2);
                    if(!mapForAdjacent.keySet().contains(point)){
                        Map<Point, Integer> map = new HashMap<>();
                        map.put(p2, distance);
                        mapForAdjacent.put(point, map);
                    }else{
                        Map<Point, Integer> map = mapForAdjacent.get(point);
                        map.put(p2, distance);
                        /*if(!map.keySet().contains(p2)){
                            map.putIfAbsent(p2, distance);
                        }else{
                            if(map.get(p2) > distance){
                                map.put(p2, distance);
                            }
                        }*/
                    }
                }
            }
        }

        Integer min = mapForAdjacent.entrySet().stream()
                .flatMap(e -> mapForAdjacent.get(e.getKey()).entrySet().stream())
                .map(Entry::getValue)
                .mapToInt(Integer::intValue)
                .min().getAsInt();

        Map<Point, Integer> minInRange = mapForAdjacent.keySet().stream()
                .filter(p -> mapForAdjacent.get(p).values().contains(min))
                .sorted()
                .map(p -> mapForAdjacent.get(p))
                .findFirst().get();

        /*Map<Point, Integer> minInRange = allPaths.keySet().stream()
                .flatMap(point -> allPaths.get(point).entrySet().stream())
                .filter(p -> unit.getPosition().getPointsInRange().contains(p.getKey()))
                .collect(toMap(
                        Entry::getKey,
                        Entry::getValue,
                        Integer::min));*/

        if(unit.getPosition().equals(new Point(13, 11))){
            logger.info("map: "+minInRange);
        }
        /*logger.info("map: "+minInRange);*/

        OptionalInt max = minInRange.keySet().stream()
                .mapToInt(minInRange::get)
                .filter(i -> i < distanceToFill)
                .findAny();


        /*logger.info("max: "+max);*/

        if(max.isPresent()){
            next = minInRange.entrySet().stream()
                    .sorted(Comparator.comparing(Entry::getKey))
                    .min(Entry.comparingByValue())
                    .get().getKey();
        }

        return next;

    }

    public static Map<Point, Integer> initializeDistanceMap(Unit unit, Map<Point, Character> area, Point pointInRange){
        Map<Point, Integer> distances = area.keySet().stream()
                .filter(p -> !area.get(p).equals('.'))
                .map(p -> new SimpleEntry<>(p, obstacle))
                .collect(toMap(
                        SimpleEntry::getKey,
                        SimpleEntry::getValue
                ));
        area.keySet().stream()
                .filter(p -> area.get(p).equals('.'))
                .forEach(p -> distances.put(p, distanceToFill));
        distances.put(pointInRange, 0);

        return distances;

    }

    public static void fillDistances(Map<Point, Integer> distances){
        int fills = 0;
        do{
            fills++;
            for(Point point : distances.keySet()){
                int currentDistance = distances.get(point);
                if(currentDistance == distanceToFill){
                    int min = distanceToFill;
                    for(Point pointInRange : point.getPointsInRange()){
                        int dist = distances.get(pointInRange);
                        if(dist < distanceToFill){
                            min = Math.min(min, dist);
                        }
                    }
                    min++;

                    if(min < distanceToFill){
                        distances.replace(point, min);
                    }
                }

            }

        }while(fills < 1000);
        /*if(distances.values().contains(distanceToFill)){
            logger.info("some unreachable");
        }*/
    }

    public static void drawMap(Map<Point, Character> area){
        for(int i = 0; i < 32 ; i++){
            for(int j = 0; j < 32 ; j++){
                System.out.print(area.get(new Point(j, i)));
            }
            System.out.print("\n");
        }
        System.out.print("\n");
    }

    public static boolean existsValidPointInRange(Map<Point, Character> areaWithUnits, Map<Unit, List<Point>> pointsInRange){
        return pointsInRange.keySet().stream()
                    .anyMatch(u -> pointsInRange.get(u).stream()
                        .anyMatch(p -> areaWithUnits.get(p).equals('.'))
                    );
    }

}
