package com.pponcet.adventofcode.day20;

public class Room {
    public enum Direction{
        NORTH,
        WEST,
        EAST,
        SOUTH;

    }

    private final Point position;
    private boolean up;
    private boolean left;
    private boolean right;
    private boolean down;

    public Room(Point position) {
        this.position = position;
    }

    public Point getPosition() {
        return position;
    }

    public boolean isUp() {
        return up;
    }

    public Room setUp() {
        this.up = true;
        return this;
    }

    public boolean isLeft() {
        return left;
    }

    public Room setLeft() {
        this.left = true;
        return this;
    }

    public boolean isRight() {
        return right;
    }

    public Room setRight() {
        this.right = true;
        return this;
    }

    public boolean isDown() {
        return down;
    }

    public Room setDown() {
        this.down = true;
        return this;
    }

    @Override
    public String toString() {
        return "Room{" +
                "position=" + position +
                ", up=" + up +
                ", left=" + left +
                ", right=" + right +
                ", down=" + down +
                '}';
    }
}
