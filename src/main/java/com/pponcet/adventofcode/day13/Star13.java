package com.pponcet.adventofcode.day13;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static com.pponcet.adventofcode.day13.Point.noCollidePoint;
import static java.util.stream.Collectors.toList;

import static com.pponcet.adventofcode.day13.Cart.Direction.*;

@Component
public class Star13 {
    private static final Logger logger = LoggerFactory.getLogger(Star13.class);

    public static void execute() {

        String fileName = "./src/main/resources/dataStar13.txt";

        Point result = noCollidePoint;
        try (Stream<String> stream = Files.lines(Paths.get(fileName))) {

            List<String> input = stream.collect(toList());
            List<Cart> carts = new ArrayList<>();
            Map<Point, Character> tracks = initializeTracksAndCarts(input, carts);

            do {
                carts = orderCarts(carts);
                for (Cart cart : carts) {
                    Point nextPoint = cart.move();
                    Character ch = tracks.get(nextPoint);
                    if (collide(carts, nextPoint)) {
                        result = nextPoint;
                    } else {
                        cart.setDirection(ch);
                    }
                }
            }while(result.equals(noCollidePoint));

        } catch (IOException e) {
            logger.error(e.getMessage());
        }

        logger.info("result: " + result);

    }

    public static Map<Point, Character> initializeTracksAndCarts(List<String> input, List<Cart> carts){
        Map<Point, Character> tracks = new HashMap<>();
        for(int i = 0; i < input.size(); i++){
            String line = input.get(i);
            for(int j = 0; j < line.length(); j++){
                char symbol = line.charAt(j);
                Point point = new Point(j, i);
                if(!(symbol == ' ')){
                    switch (symbol){
                        case '^':
                            carts.add(new Cart(NORTH, point));
                            tracks.put(point, '|');
                            break;
                        case '<':
                            carts.add(new Cart(WEST, point));
                            tracks.put(point, '-');
                            break;
                        case 'v':
                            carts.add(new Cart(SOUTH, point));
                            tracks.put(point, '|');
                            break;
                        case '>':
                            carts.add(new Cart(EAST, point));
                            tracks.put(point, '-');
                            break;
                        default:
                            tracks.put(point, symbol);
                            break;
                    }

                }
            }
        }
        return tracks;
    }

    public static boolean collide(List<Cart> carts, Point position){
        return carts.stream()
                .map(Cart::getPosition)
                .filter(p -> p.equals(position))
                .count() >= 2;
    }

    public static List<Cart> orderCarts(List<Cart> carts){
        return carts.stream()
                .sorted()
                .collect(toList());
    }
}
