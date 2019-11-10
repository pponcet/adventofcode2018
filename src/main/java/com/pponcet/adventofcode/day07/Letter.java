package com.pponcet.adventofcode.day07;

import java.util.Set;
import java.util.TreeSet;

public class Letter {
    public static final Letter nullLetter = new Letter('!');

    private final char c;
    private Set<Letter> previous;

    public Letter(char c) {
        this.c = c;
        this.previous = new TreeSet<>();

    }

    public char getC() {
        return c;
    }

    public Set<Letter> getPrevious() {
        return previous;
    }

    public void addPrevious(Letter previous) {
        this.previous.add(previous);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Letter)) return false;

        Letter letter = (Letter) o;

        return getC() == letter.getC();

    }

    @Override
    public int hashCode() {
        return (int) getC();
    }

    @Override
    public String toString() {
        return String.valueOf(c);
    }
}
