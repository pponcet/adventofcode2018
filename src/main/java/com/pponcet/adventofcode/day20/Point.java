package com.pponcet.adventofcode.day20;

import java.util.ArrayList;
import java.util.List;

public class Point implements  Comparable<Point>{
    public static final Point nullPoint = new Point(-10000, -10000);

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
        int result = (int) (getX() ^ (getX() >>> 32));
        result = 31 * result + (int) (getY() ^ (getY() >>> 32));
        return result;
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
            if(this.getY() == p.getY()){
                if(this.getX() > p.getX()){
                    return 1;
                }else{
                    return -1;
                }
            }else{
                if(this.getY() > p.getY()){
                    return 1;
                }else{
                    return -1;
                }
            }

        }
    }

    @Override
    public String toString() {
        return "("+x+","+y+")";
    }

}
