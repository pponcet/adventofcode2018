package com.pponcet.adventofcode.day24;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import static com.pponcet.adventofcode.day24.Group.*;
import static com.pponcet.adventofcode.day24.Group.Attack.*;
import static com.pponcet.adventofcode.day24.Group.Syst.*;
import static java.util.stream.Collectors.toList;
import static java.util.Map.*;
import static java.util.AbstractMap.SimpleEntry;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;


@Component
public class Star24 {
    private static final Logger logger = LoggerFactory.getLogger(Star24.class);

    public static void execute() {
        String fileName = "./src/main/resources/test24.txt";
        int result = 0;

        try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
            List<String> lines = stream.collect(toList());

            int infectionLine = lines.indexOf("Infection:");
            List<Group> groups = lines.subList(1, infectionLine - 1).stream()
                    .map(s -> extractGroup(s, Immune))
                    .collect(toList());
            groups.addAll(
                    lines.subList(infectionLine + 1, lines.size()).stream()
                            .map(s -> extractGroup(s, Infection))
                            .collect(toList())
            );
            logger.info("groups: "+groups);

            do{
                groups = groups.stream()
                        .sequential()
                        .sorted(Comparator.comparing(Group::getEffectivePower).reversed().thenComparing(Group::getInitiative).reversed())
                        .collect(toList());



            }while(oneArmyRemains(groups));

        } catch (IOException e) {
            logger.error(e.getMessage());
        }

        logger.info("result: " + result);
    }

    public static boolean oneArmyRemains(List<Group> groups) {
        return groups.stream()
                .filter(Group::isAlive)
                .map(Group::getSystem)
                .distinct()
                .count() == 1;

    }

    public static Group extractGroup(String line, Syst system){
        StringBuilder sb = new StringBuilder(line);
        int units = Integer.valueOf(extractInfo(sb, " units each with "));
        int hp = Integer.valueOf(extractInfo(sb, " hit points "));
        List<Entry<Attack, Integer>> weakness = extractWeakness(extractInfo(sb, "with an attack that does ").trim());
        int damage = Integer.valueOf(extractInfo(sb, " "));
        Attack attack = extractAttack(extractInfo(sb, " damage at initiative "));
        int initiative = Integer.valueOf(sb.toString());

        return new Group(system, units, hp, initiative, attack, damage, weakness);
    }

    public static String extractInfo(StringBuilder sb, String end){
        String line = sb.toString();
        String info = line.substring(0, line.indexOf(end));
        sb.delete(0, line.indexOf(end) + end.length());
        return info;
    }

    public static List<Entry<Attack, Integer>> extractWeakness(String weaknessStr){
        List<Entry<Attack, Integer>> weakness = new ArrayList<>();
        if(!weaknessStr.isEmpty()){
            weaknessStr = weaknessStr.substring(1, weaknessStr.indexOf(")"));
            if(weaknessStr.charAt(0) == 'i'){
                if(weaknessStr.contains(";")){
                    addEntryWeakness(weakness, weaknessStr.substring(weaknessStr.indexOf(";") + 2), 2);
                    weaknessStr = weaknessStr.substring(0, weaknessStr.indexOf(";"));
                }
                addEntryWeakness(weakness, weaknessStr, 0);

            }else{
                if(weaknessStr.contains(";")){
                    addEntryWeakness(weakness, weaknessStr.substring(weaknessStr.indexOf(";") + 2), 0);
                    weaknessStr = weaknessStr.substring(0, weaknessStr.indexOf(";"));
                }
                addEntryWeakness(weakness, weaknessStr, 2);
            }
        }
        return weakness;
    }

    public static void addEntryWeakness(List<Entry<Attack, Integer>> weakness, String weaknessStr, int typeMultiplier){
        StringBuilder sb = new StringBuilder(weaknessStr);
        sb.delete(0, sb.indexOf(" to ") + " to ".length());
        String[] infos = sb.toString().split(", ");
        for(String info : infos){
            weakness.add(new SimpleEntry<>(extractAttack(info), typeMultiplier));
        }
    }

    public static Attack extractAttack(String attackStr){
        switch (attackStr){
            case "bludgeoning":
                return Bludgeoning;
            case "slashing":
                return Slashing;
            case "fire":
                return Fire;
            case "cold":
                return Cold;
            case "radiation":
                return Radiation;
            default:
                return NullAttack;
        }
    }

    public static Optional<Group> findTarget(List<Group> groups, Map<Group, Group> targets, Group attacker){
        return groups.stream()
                .filter(defender -> !defender.getSystem().equals(attacker.getSystem()))
                .filter(defender -> !targets.values().contains(defender))
                .sorted(Comparator.comparingInt(attacker::calculateMostDamage).reversed())
                .max(Comparator.comparing(Group::getEffectivePower).reversed().thenComparing(Group::getInitiative))
                .filter(defender -> defender.calculateMostDamage(attacker) > 0);
    }

    public static int calculateMostDamage(Group attacker, Group defender){
        return attacker.getWeakness().keySet().stream()
                .mapToInt(a -> defender.getWeakness().get(a))
                .max().getAsInt() * attacker.getEffectivePower();
    }

}
