package com.pponcet.adventofcode.day01;

import java.util.List;

public class Accumulator {

    public static void accept(List<Long> list, Long value) {
        list.add(value + (list.isEmpty() ? 0 : list.get(list.size() - 1)));
    }

    public static List<Long> combine(List<Long> list1, List<Long> list2) {
        long total = list1.get(list1.size() - 1);
        list2.stream().map(n -> n + total).forEach(list1::add);
        return list1;
    }
}
