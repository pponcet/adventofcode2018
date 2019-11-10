package com.pponcet.adventofcode.day09;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class Star9 {
    private static final Logger logger = LoggerFactory.getLogger(Star9.class);

    public static void execute(){

        int result = 0;

        int numberPlayers = 466;
        int lastPoints = 71436;
        List<Player> players = new ArrayList<>();

        for(int i = 0; i < numberPlayers; i++){
            players.add(new Player());
        }

        List<Integer> marbles = new ArrayList<>();
        int currentMarble = 0;
        int currentPlayerIndex = -1;
        int currentMarbleIndex = 0;
        marbles.add(currentMarble);
        int removed = 0;
        int sum = 0;


        do{
            currentMarble++;
            currentPlayerIndex = (currentPlayerIndex+1) % numberPlayers;

            if(currentMarble % 23 != 0){
                currentMarbleIndex = (currentMarbleIndex+1) % marbles.size() +1;
                marbles.add(currentMarbleIndex, currentMarble);
            }else{
                Player currentPlayer = players.get(currentPlayerIndex);
                currentPlayer.addToScore(currentMarble);
                int indexToBeRemoved = 0;
                if(currentMarbleIndex >= 7){
                    indexToBeRemoved = (currentMarbleIndex - 7) % marbles.size();
                }else{
                    indexToBeRemoved = currentMarbleIndex - 7 + marbles.size();
                }

                removed = marbles.remove(indexToBeRemoved);
                currentPlayer.addToScore(removed);
                currentMarbleIndex = indexToBeRemoved;
                sum = currentMarble + removed;
            }


        }while(currentMarble != lastPoints);

        result = players.stream()
                .map(Player::getScore)
                .mapToInt(Integer::intValue)
                .max().getAsInt();

        logger.info("result: "+result);
    }


}
