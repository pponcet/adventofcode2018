package com.pponcet.adventofcode.day23;

public class Point{
    public static final Point nullPoint = new Point(-1, -1, -1);

    private final int x;
    private final int y;
    private final int z;

    public Point(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getZ() {
        return z;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Point)) return false;

        Point point = (Point) o;

        if (getX() != point.getX()) return false;
        if (getY() != point.getY()) return false;
        return getZ() == point.getZ();

    }

    @Override
    public int hashCode() {
        int result = getX();
        result = 31 * result + getY();
        result = 31 * result + getZ();
        return result;
    }

    @Override
    public String toString() {
        return "("+x+","+y+","+z+")";
    }

}
