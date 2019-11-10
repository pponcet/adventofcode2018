package com.pponcet.adventofcode.day06;

public class Point implements  Comparable<Point> {
    public static final Point equallyFar = new Point(-1, -1);
    public static final Point infinitePoint = new Point(-1, -1);

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

    @Override
    public int compareTo(Point p) {
        if(this.getX() == p.getX()){
            if(this.getY() >= p.getY()){
                return 1;
            }else{
                return -1;
            }
        }
        else{
            if(this.getX() > p.getX()){
                return 1;
            }else{
                return -1;
            }
        }
    }

}
