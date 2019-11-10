package com.pponcet.adventofcode.day08;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class Star8 {
    private static final Logger logger = LoggerFactory.getLogger(Star8.class);

    public static void execute(){

        String fileName = "./src/main/resources/dataStar8.txt";

        int result = 0;
        try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
            List<Integer> input = Arrays.asList(stream.findFirst().get().split(" ")).stream()
                    .map(Integer::valueOf)
                    .collect(Collectors.toList());

            List<Node> nodes = new ArrayList<>();
            extractNodeAndReduceList(input, nodes);
            result = nodes.stream().mapToInt(n -> n.getSumMetadata())
                    .sum();

        } catch (IOException e) {
            logger.error(e.getMessage());
        }

        logger.info("result: "+result);
    }

    //i don't like to modify list
    public static void extractNodeAndReduceList(List<Integer> list, List<Node> nodes){
        int numberNodes = list.get(0);
        list.remove(0);
        int numberMetadata = list.get(0);
        list.remove(0);

        for(int i = 0; i < numberNodes ; i++){
            extractNodeAndReduceList(list, nodes);
        }

        int sumMetadata = 0;
        for(int i = 0; i < numberMetadata; i++){
            sumMetadata += list.get(0);
            list.remove(0);
        }

        nodes.add(new Node(numberNodes, sumMetadata));
    }


}
