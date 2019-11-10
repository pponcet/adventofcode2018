package com.pponcet.adventofcode.day10;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.AbstractMap.SimpleEntry;

@Component
public class Star10 {
    private static final Logger logger = LoggerFactory.getLogger(Star10.class);

    public static void execute() {

        String fileName = "./src/main/resources/dataStar10.txt";
        String outputName = "./src/main/resources/output10-";

        String result = "";
        try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
            List<String> list = new ArrayList<>();
            list.add("position=< 9,  1> velocity=< 0,  2>");
            list.add("position=< 7,  0> velocity=<-1,  0>");
            list.add("position=< 3, -2> velocity=<-1,  1>");
            list.add("position=< 6, 10> velocity=<-2, -1>");
            list.add("position=< 2, -4> velocity=< 2,  2>");
            list.add("position=<-6, 10> velocity=< 2, -2>");
            list.add("position=< 1,  8> velocity=< 1, -1>");
            list.add("position=< 1,  7> velocity=< 1,  0>");
            list.add("position=<-3, 11> velocity=< 1, -2>");
            list.add("position=< 7,  6> velocity=<-1, -1>");
            list.add("position=<-2,  3> velocity=< 1,  0>");
            list.add("position=<-4,  3> velocity=< 2,  0>");
            list.add("position=<10, -3> velocity=<-1,  1>");
            list.add("position=< 5, 11> velocity=< 1, -2>");
            list.add("position=< 4,  7> velocity=< 0, -1>");
            list.add("position=< 8, -2> velocity=< 0,  1>");
            list.add("position=<15,  0> velocity=<-2,  0>");
            list.add("position=< 1,  6> velocity=< 1,  0>");
            list.add("position=< 8,  9> velocity=< 0, -1>");
            list.add("position=< 3,  3> velocity=<-1,  1>");
            list.add("position=< 0,  5> velocity=< 0, -1>");
            list.add("position=<-2,  2> velocity=< 2,  0>");
            list.add("position=< 5, -2> velocity=< 1,  2>");
            list.add("position=< 1,  4> velocity=< 2,  1>");
            list.add("position=<-2,  7> velocity=< 2, -2>");
            list.add("position=< 3,  6> velocity=<-1, -1>");
            list.add("position=< 5,  0> velocity=< 1,  0>");
            list.add("position=<-6,  0> velocity=< 2,  0>");
            list.add("position=< 5,  9> velocity=< 1, -2>");
            list.add("position=<14,  7> velocity=<-2,  0>");
            list.add("position=<-3,  6> velocity=< 2, -1>");
            Stream<String> stream2 = list.stream();

            List<MobilePoint> points = stream2.map(Star10::extractMobilePoint)
                    .collect(Collectors.toList());

            int seconds = 0;
            boolean found = false;
            String s = "";
            do {
                seconds++;
                //logger.info("seconds: "+seconds);

                points = calculateNextInstant(points, seconds);

                int minX = points.stream()
                        .map(MobilePoint::getX)
                        .mapToInt(Integer::intValue)
                        .min().getAsInt();

                int maxX = points.stream()
                        .map(MobilePoint::getX)
                        .mapToInt(Integer::intValue)
                        .max().getAsInt();

                int minY = points.stream()
                        .map(MobilePoint::getY)
                        .mapToInt(Integer::intValue)
                        .min().getAsInt();

                int maxY = points.stream()
                        .map(MobilePoint::getY)
                        .mapToInt(Integer::intValue)
                        .max().getAsInt();

                s = showPoints(points, seconds, minX, maxX, minY, maxY);
                int area = (maxX - minX)*(maxY - minY);

                //logger.info("length: "+s.length());

                /*if (seconds == 64289) {
                    Path path = Paths.get(outputName+seconds+".txt");
                    byte[] strToBytes = s.getBytes();
                    Files.write(path, strToBytes);
                }*/

            } while (seconds < 100);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }

        logger.info("result: " + result);

    }

    public static MobilePoint extractMobilePoint(String string) {
        int x = Integer.valueOf(string.substring(10, string.indexOf(",")).trim());
        int y = Integer.valueOf(string.substring(string.indexOf(",") + 1, string.indexOf(">")).trim());
        int vX = Integer.valueOf(string.substring(string.indexOf("v") + 10, string.indexOf("v") + 12).trim());
        int vY = Integer.valueOf(string.substring(string.indexOf("v") + 13, string.indexOf("v") + 16).trim());
        return new MobilePoint(x, y, vX, vY);
    }

    public static List<MobilePoint> calculateNextInstant(List<MobilePoint> points, int seconds) {
        return points.stream()
                .map(p -> new MobilePoint(p.getX() + p.getvX() * seconds, p.getY() + p.getvY() * seconds, p.getvX(), p.getvY()))
                .collect(Collectors.toList());
    }

    public static String showPoints(List<MobilePoint> points, int seconds, int minX, int maxX, int minY, int maxY) {
        boolean found = false;
        StringBuilder sb = new StringBuilder();
        String str = "";
        Map<Point, Integer> mapPoints = points.stream()
                .map(p -> new SimpleEntry<>(new Point(p.getX(), p.getY()), 1))
                .collect(Collectors.toMap(
                        SimpleEntry::getKey,
                        SimpleEntry::getValue,
                        Integer::sum
                ));
        int dist = mapPoints.keySet().stream()
                .map(p -> Math.sqrt(p.getX() * p.getX() + p.getY() * p.getY()))
                .mapToInt(Double::intValue)
                .max().getAsInt();

        if(detectAlignedPoint(mapPoints.keySet())){
            logger.info("seconds: "+seconds);
            //found = true;
            int margin = 0;

            for (int j = minY - margin * seconds; j <= maxY + margin * seconds; j++) {
                for (int i = minX - margin * seconds; i <= maxX + margin * seconds; i++) {
                    Point point = new Point(i, j);

                    if (mapPoints.containsKey(point)) {
                        System.out.print("#");
                        //sb.append("#");
                        //str += "#";
                    } else {
                        System.out.print(".");
                        //sb.append(".");
                        //str += ".";
                    }

                }
                System.out.print("\n");
                //sb.append("\n");
                //str += "\n";

            }
        }
        return sb.toString();

    }

    public static boolean detectAlignedPoint(Set<Point> points) {
        return points.stream()
                .anyMatch(p -> detectAlignment(points, p));
    }

    public static int detectXAlignment(Set<Point> points, Point point, int xAlignment) {
        Point p = new Point(point.getX() - 1, point.getY());
        if (points.contains(p)) {
            xAlignment++;
            xAlignment = detectXAlignment(points, p, xAlignment);
        }
        return xAlignment;
    }

    public static int detectYAlignment(Set<Point> points, Point point, int yAlignment) {
        Point p = new Point(point.getX(), point.getY() - 1);
        if (points.contains(p)) {
            yAlignment++;
            yAlignment = detectYAlignment(points, p, yAlignment);
        }
        return yAlignment;
    }

    public static boolean detectAlignment(Set<Point> points, Point point) {
        int numberAlignedXPoints = 5;
        int numberAlignedYPoints = 7;
        int xAlignment = 0;
        int yAlignment = 0;
        return detectXAlignment(points, point, xAlignment) >= numberAlignedXPoints
                || detectYAlignment(points, point, yAlignment) >= numberAlignedYPoints;
    }
}
