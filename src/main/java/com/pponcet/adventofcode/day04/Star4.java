package com.pponcet.adventofcode.day04;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toMap;
import static java.util.AbstractMap.SimpleEntry;

@Component
public class Star4 {
    private static final Logger logger = LoggerFactory.getLogger(Star4.class);

    public static void execute(){

        String fileName = "./src/main/resources/dataStar4.txt";


        long result = 0L;
        try (Stream<String> stream = Files.lines(Paths.get(fileName))) {

            Map<LocalDateTime, String> mapDates = new TreeMap<>(stream.map(s -> new SimpleEntry<>(LocalDateTime.parse(s.substring(1, s.indexOf(']')), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")), s.substring(s.indexOf(']')+2)) {

            }).collect(toMap(
                    SimpleEntry::getKey,
                    SimpleEntry::getValue)));

            Map<Long, Map<Integer, Integer>> minutesAsleep = new HashMap<>();
            long currentId = 0L;
            int oldMinute = 0;
            for(LocalDateTime ldt : mapDates.keySet()){
                String str = mapDates.get(ldt);

                int newMinute = ldt.getMinute();
                if(str.contains("Guard")){
                    currentId = Long.valueOf(str.substring(str.indexOf('#')+1, str.indexOf('b')-1));
                    if(!minutesAsleep.keySet().contains(currentId)){
                        minutesAsleep.put(currentId, new HashMap<>());
                    }

                }
                else {
                    if (str.contains("wakes")) {
                        if(newMinute < oldMinute){
                            for (int i = oldMinute; i<60; i++){
                                minutesAsleep.get(currentId).merge(i, 1, Integer::sum);
                            }
                        }
                        for (int i = oldMinute; i<newMinute; i++){
                            minutesAsleep.get(currentId).merge(i, 1, Integer::sum);
                        }
                        oldMinute = newMinute;

                    }

                    if (str.contains("falls")) {
                        oldMinute = newMinute;
                    }
                }
            }

            logger.info("minutes: "+minutesAsleep.toString());

            //max should be deleted...
            int max = minutesAsleep.keySet().stream()
                    .map(id -> minutesAsleep.get(id).values().stream().mapToInt(Integer::intValue).sum())
                    .max(Comparator.comparing(Integer::intValue)).get();


            long guard = minutesAsleep.keySet().stream().filter(id -> minutesAsleep.get(id).values().stream().mapToInt(Integer::intValue).sum() == max)
                    .findAny().get();
            logger.info("guard: "+guard);

            //maxOccurrency should be deleted...
            int maxOccurrency = minutesAsleep.get(guard).values().stream().mapToInt(Integer::intValue).max().getAsInt();
            logger.info("maxOcc: "+maxOccurrency);

            int maxMinute = minutesAsleep.get(guard).keySet().stream().filter(min -> minutesAsleep.get(guard).get(min) == maxOccurrency)
                    .findAny().get();
            logger.info("maxMinute: "+maxMinute);

            result = guard * maxMinute;

        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        logger.info("result: "+result);

    }

    public void addMinute(Map<Long, Map<Integer, Integer>> minutes, long currentId, int currentMinute){
        minutes.get(currentId).merge(currentMinute, 1, Integer::sum);
    }


}
