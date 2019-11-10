package com.pponcet.adventofcode.day23;

public class NanoBot {
    private final Point position;
    private final int radius;

    public NanoBot(Point position, int radius) {
        this.position = position;
        this.radius = radius;
    }

    public Point getPosition() {
        return position;
    }

    public int getRadius() {
        return radius;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof NanoBot)) return false;

        NanoBot nanoBot = (NanoBot) o;

        if (getRadius() != nanoBot.getRadius()) return false;
        return getPosition() != null ? getPosition().equals(nanoBot.getPosition()) : nanoBot.getPosition() == null;

    }

    @Override
    public int hashCode() {
        int result = getPosition() != null ? getPosition().hashCode() : 0;
        result = 31 * result + getRadius();
        return result;
    }

    @Override
    public String toString() {
        return "NanoBot{" +
                "position=" + position +
                ", radius=" + radius +
                '}';
    }
}
