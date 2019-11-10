package com.pponcet.adventofcode.day20;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

import static com.pponcet.adventofcode.day20.Room.*;


@Component
public class Star20 {
    private static final Logger logger = LoggerFactory.getLogger(Star20.class);

    public static void execute() {
        String fileName = "./src/main/resources/dataStar20.txt";
        int result = 0;

        try (Stream<String> stream = Files.lines(Paths.get(fileName))) {

            //String test = "^ENWWW(NEEE|SSE(EE|N))$";

            //String test = "^ENNWSWW(NEWS|)SSSEEN(WNSE|)EE(SWEN|)NNN$";

            //String test = "^ESSWWN(E|NNENN(EESS(WNSE|)SSS|WWWSSSSE(SW|NNNE)))$";

            //String test = "^WSSEESWWWNW(S|NENNEEEENN(ESSSSW(NWSW|SSEN)|WSWWN(E|WWS(E|SS))))$";
            String test = stream.findFirst().get();
            test = test.substring(1);
            logger.info("test string: "+test);
            Map<Point, Room> rooms = new HashMap<>();
            Point initialPosition = new Point(0, 0);
            Room currentRoom = new Room(initialPosition);
            rooms.put(initialPosition, currentRoom);

            State state = new State(test, currentRoom);
            process(rooms, state);

            print(rooms);


        } catch (IOException e) {
            logger.error(e.getMessage());
        }

        logger.info("result: " + result);
    }

    public static State process(Map<Point, Room> rooms, State state){
        Point nextPosition = Point.nullPoint;
        int index = 0;

        String str = state.getString();
        Room currentRoom = state.getCurrentRoom();

        do{
            if(str.charAt(index) == '$'){
                break;
            }else {
                switch (str.charAt(index)) {
                    case '(':
                        int indexAfterBranch = searchParenthesisIndex(str.substring(index + 1));
                        int newIndex = index + indexAfterBranch + 1;
                        String branchStr = str.substring(index + 1, newIndex);

                        logger.info("branchStr: "+branchStr);

                        Room currentR = currentRoom;
                        int currentPipeIndex = 0;

                        do{

                            int nextPipeIndex = nextPipeOfCurrentParenthesis(branchStr, currentPipeIndex);
                            if (nextPipeIndex != -1) {
                                process(rooms, new State(branchStr.substring(currentPipeIndex, nextPipeIndex), currentR)).getString();
                                //branchStr = branchStr.substring(nextPipeIndex + 1);
                                currentPipeIndex = nextPipeIndex;
                            } else {
                                if(currentPipeIndex + 1 < branchStr.length()) {
                                    currentRoom = process(rooms, new State(branchStr.substring(currentPipeIndex + 1), currentR)).getCurrentRoom();
                                }
                                break;
                            }

                        }while(currentPipeIndex < branchStr.length());

                        index = newIndex;
                        break;

                    case 'N':
                        currentRoom.setUp();
                        nextPosition = new Point(currentRoom.getPosition().getX(), currentRoom.getPosition().getY() + 1);
                        if (rooms.containsKey(nextPosition)) {
                            currentRoom = rooms.get(nextPosition).setDown();
                        } else {
                            currentRoom = new Room(nextPosition).setDown();
                            rooms.put(nextPosition, currentRoom);
                        }
                        break;
                    case 'W':
                        currentRoom.setLeft();
                        nextPosition = new Point(currentRoom.getPosition().getX() - 1, currentRoom.getPosition().getY());
                        if (rooms.containsKey(nextPosition)) {
                            currentRoom = rooms.get(nextPosition).setRight();
                        } else {
                            currentRoom = new Room(nextPosition).setRight();
                            rooms.put(nextPosition, currentRoom);
                        }
                        break;
                    case 'E':
                        currentRoom.setRight();
                        nextPosition = new Point(currentRoom.getPosition().getX() + 1, currentRoom.getPosition().getY());
                        if (rooms.containsKey(nextPosition)) {
                            currentRoom = rooms.get(nextPosition).setLeft();
                        } else {
                            currentRoom = new Room(nextPosition).setLeft();
                            rooms.put(nextPosition, currentRoom);
                        }
                        break;
                    case 'S':
                        currentRoom.setDown();
                        nextPosition = new Point(currentRoom.getPosition().getX(), currentRoom.getPosition().getY() - 1);
                        if (rooms.containsKey(nextPosition)) {
                            currentRoom = rooms.get(nextPosition).setUp();
                        } else {
                            currentRoom = new Room(nextPosition).setUp();
                            rooms.put(nextPosition, currentRoom);
                        }
                        break;
                    default:
                        break;
                }
            }
            index++;
        }while(index < str.length());
        return new State(str.substring(index), currentRoom);
    }

    public static int searchParenthesisIndex(String str){
        int index = 0;
        int parenthesis = 1;
        do{
            char c = str.charAt(index);
            if(c == '('){
                parenthesis++;
            }

            if(c == ')'){
                parenthesis--;
            }

            index++;
        }while(parenthesis > 0);
        return index - 1;
    }

    public static void print(Map<Point, Room> rooms){
        int maxX = rooms.keySet().stream()
                .map(Point::getX)
                .mapToInt(Integer::intValue)
                .max().getAsInt();

        int maxY = rooms.keySet().stream()
                .map(Point::getY)
                .mapToInt(Integer::intValue)
                .max().getAsInt();

        int minX = rooms.keySet().stream()
                .map(Point::getX)
                .mapToInt(Integer::intValue)
                .min().getAsInt();

        int minY = rooms.keySet().stream()
                .map(Point::getY)
                .mapToInt(Integer::intValue)
                .min().getAsInt();

        for(int i = minY; i <= maxY ; i++){
            for(int j = minX; j <= maxX; j++){
                Point point = new Point(j, i);
                if(rooms.containsKey(point)){
                    System.out.print(".");
                }else{
                    System.out.print("#");
                }

            }
            System.out.print("\n");
        }

    }

    public static int nextPipeOfCurrentParenthesis(String str, int currentIndex){
        int index = 0;
        int parenthesis = 0;
        if(currentIndex != 0){
            str = str.substring(currentIndex + 1);
        }

        if(!str.contains("|")){
            return -1;
        }

        do{
            char c = str.charAt(index);
            if(c == '('){
                index = index + searchParenthesisIndex(str.substring(index + 1)) + 1;
            }

            if(str.charAt(index) == '|'){
                return index + 1 + currentIndex;
            }
            index++;
        }while(index < str.length());
        return -1;
    }

    /*public static Map<Room, Integer> calculateDistances(Map<Point, Room> rooms){

    }*/

}
