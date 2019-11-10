package com.pponcet.adventofcode.day17;

import java.util.ArrayList;
import java.util.List;

public class Point{
    private final int x;
    private final int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public List<Point> adjacentPoints(){
        List<Point> points = new ArrayList<>();
        points.add(new Point(x-1, y-1));
        points.add(new Point(x-1, y));
        points.add(new Point(x-1, y+1));
        points.add(new Point(x, y-1));
        points.add(new Point(x, y+1));
        points.add(new Point(x+1, y-1));
        points.add(new Point(x+1, y));
        points.add(new Point(x+1, y+1));
        return points;

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Point)) return false;

        Point point = (Point) o;

        if (getX() != point.getX()) return false;
        return getY() == point.getY();

    }

    @Override
    public int hashCode() {
        int result = getX();
        result = 31 * result + getY();
        return result;
    }

    @Override
    public String toString() {
        return "("+x+","+y+")";
    }
}
