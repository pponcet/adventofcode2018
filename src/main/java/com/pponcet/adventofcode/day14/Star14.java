package com.pponcet.adventofcode.day14;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Component
public class Star14 {
    private static final Logger logger = LoggerFactory.getLogger(Star14.class);

    public static void execute() {
        int input = 47801;
        String result = "";

        List<Integer> recipes = new ArrayList<>();
        recipes.add(3);
        recipes.add(7);

        int firstPosition = 0;
        int secondPosition = 1;


        do{
            int sum = recipes.get(firstPosition) + recipes.get(secondPosition);
            if(sum > 9){
                recipes.add(sum / 10);
            }
            recipes.add(sum % 10);
            firstPosition = (firstPosition + (1 + recipes.get(firstPosition))) % recipes.size();
            secondPosition = (secondPosition + (1 + recipes.get(secondPosition))) % recipes.size();
        }while(recipes.size() < input + 10);

        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < 10; i++){
            sb.append(recipes.get(recipes.size() - 10 + i));
        }
        logger.info("result: " + sb.toString());

    }

}
