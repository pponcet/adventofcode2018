package com.pponcet.adventofcode.day24;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.pponcet.adventofcode.day24.Group.Attack.*;
import static java.util.Map.*;

public class Group{

    public enum Syst{
        Immune,
        Infection
    }

    public enum Attack{
        Bludgeoning,
        Slashing,
        Fire,
        Cold,
        Radiation,
        NullAttack
    }

    private final Syst system;
    private int units;
    private final int hp;
    private final int initiative;
    private final Attack attack;
    private final int damage;
    private final Map<Attack, Integer> weakness;
    private int effectivePower;

    public Group(Syst system, int units, int hp, int initiative, Attack attack, int damage, List<Entry<Attack, Integer>> weakness) {
        this.system = system;
        this.units = units;
        this.hp = hp;
        this.initiative = initiative;
        this.attack = attack;
        this.damage = damage;
        Map<Attack, Integer> map = new HashMap<>();
        map.put(Bludgeoning, 1);
        map.put(Slashing, 1);
        map.put(Fire, 1);
        map.put(Cold, 1);
        map.put(Radiation, 1);
        weakness.stream().forEach(w -> map.put(w.getKey(), w.getValue()));
        this.weakness = map;
        this.effectivePower = units * damage;
    }

    public Syst getSystem() {
        return system;
    }

    public int getUnits() {
        return units;
    }

    public int getHp() {
        return hp;
    }

    public int getInitiative() {
        return initiative;
    }

    public Attack getAttack() {
        return attack;
    }

    public int getDamage() {
        return damage;
    }

    public Map<Attack, Integer> getWeakness() {
        return weakness;
    }

    public int getEffectivePower() {
        return effectivePower;
    }

    public void killUnits(int kills) {
        units -= kills;
        if(units < 0){
            units = 0;
        }
        effectivePower = units * damage;
    }

    public boolean isAlive(){
        return units > 0;
    }

    @Override
    public String toString() {
        return "Group{" +
                "system=" + system +
                ", units=" + units +
                ", hp=" + hp +
                ", initiative=" + initiative +
                ", attack=" + attack +
                ", damage=" + damage +
                ", weakness=" + weakness +
                ", effectivePower=" + effectivePower +
                '}';
    }

    public int calculateMostDamage(Group defender){
        return this.getWeakness().keySet().stream()
                .mapToInt(a -> defender.getWeakness().get(a))
                .max().getAsInt() * this.getEffectivePower();
    }




}
