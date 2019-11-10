package com.pponcet.adventofcode.day01;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Star1a {

    private static final Logger logger = LoggerFactory.getLogger(Star1a.class);

    public static void execute(){
        /*String data = null;
        try {
            data = get("https://adventofcode.com/2018/day/1/input");
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        logger.info("length: "+data.length());*/


        String fileName = "./src/main/resources/dataStar1.txt";

        long result = 0L;
        try (Stream<String> stream = Files.lines(Paths.get(fileName))) {


            result = stream.mapToLong(Long::valueOf)
                    .sum();

        } catch (IOException e) {
            logger.error(e.getMessage());
        }


        /*long result2 = 0L;
        try (Stream<String> stream = Files.lines(Paths.get(fileName))) {


            stream.mapToLong(Long::valueOf)
                    .collect(ArrayList<Long>::new, Accumulator::accept, Accumulator::combine)
                    .stream()
                    .forEach(System.out::println);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }

        logger.info("result2: "+result2);*/

        List<Long> firstList = new ArrayList<>();
        try (Stream<String> stream = Files.lines(Paths.get(fileName))) {


            firstList = stream.map(Long::valueOf)
                    .collect(Collectors.toList());

        } catch (IOException e) {
            logger.error(e.getMessage());
        }

        List<Long> modifiedFirstList = new ArrayList<>(firstList);
        List<Long> currentList = new ArrayList<>();
        long value = firstList.get(0);

        List<Long> duplicates;
        do {
            if(!currentList.isEmpty()){

                modifiedFirstList.set(0, Long.sum(value, currentList.get(currentList.size() - 1)));
                //logger.info("modified: "+modifiedFirstList.toString());
            }

            List<Long> updatedList = modifiedFirstList.stream().collect(ArrayList<Long>::new, Accumulator::accept, Accumulator::combine);
            //logger.info("updated: "+ updatedList.toString());
            currentList.addAll(updatedList);
            logger.info("current: "+currentList.toString());
            duplicates = currentList.stream().filter(i -> Collections.frequency(currentList, i) > 1)
                    .peek(System.out::println)
                    .collect(Collectors.toList());



        }while(duplicates.isEmpty());
        logger.info("duplicate: "+duplicates.toString());
    }


    /*public static String get(String uri) throws Exception {
        HttpClient client = HttpClient.newBuilder()
                .authenticator(new Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication("pponcet", "pwGH2019&".toCharArray());
                    }
                })
                .build();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(uri))
                .build();

        HttpResponse<String> response =
                client.send(request, HttpResponse.BodyHandlers.ofString());

        return response.body();
    }*/
}
