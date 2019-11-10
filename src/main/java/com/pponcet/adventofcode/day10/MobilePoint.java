package com.pponcet.adventofcode.day10;

public class MobilePoint {
    private final int x;
    private final int y;
    private final int vX;
    private final int vY;

    public MobilePoint(int x, int y, int vX, int vY) {
        this.x = x;
        this.y = y;
        this.vX = vX;
        this.vY = vY;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getvX() {
        return vX;
    }

    public int getvY() {
        return vY;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MobilePoint)) return false;

        MobilePoint that = (MobilePoint) o;

        if (getX() != that.getX()) return false;
        if (getY() != that.getY()) return false;
        if (getvX() != that.getvX()) return false;
        return getvY() == that.getvY();

    }

    @Override
    public int hashCode() {
        int result = getX();
        result = 31 * result + getY();
        result = 31 * result + getvX();
        result = 31 * result + getvY();
        return result;
    }

    @Override
    public String toString() {
        return "("+x+", "+y+") ["+vX+", "+vY+"]";
    }
}
