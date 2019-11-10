package com.pponcet.adventofcode.day13;

import java.util.HashMap;
import java.util.Map;

import static com.pponcet.adventofcode.day13.Cart.Direction.*;
import static com.pponcet.adventofcode.day13.Cart.TurnOption.*;

public class Cart implements Comparable<Cart> {
    public enum Direction{
        NORTH(new Point(0, -1)),
        SOUTH(new Point(0, 1)),
        WEST(new Point(-1, 0)),
        EAST(new Point(1, 0));
        private final Point next;

        Direction(Point next) {
            this.next = next;
        }

        public Point getNext() {
            return next;
        }
    }

    public enum TurnOption{
        SLASH(EAST, SOUTH, WEST, NORTH),
        BACKSLASH(WEST, NORTH, EAST, SOUTH),
        LEFT(WEST, SOUTH, EAST, NORTH),
        STRAIGHT(NORTH, WEST, SOUTH, EAST),
        RIGHT(EAST, NORTH, WEST, SOUTH);

        private final Map<Direction, Direction> nextDirection;

        TurnOption(Direction north, Direction west, Direction south, Direction east) {
            Map<Direction, Direction> nextDirection = new HashMap<>();
            nextDirection.put(NORTH, north);
            nextDirection.put(WEST, west);
            nextDirection.put(SOUTH, south);
            nextDirection.put(EAST, east);
            this.nextDirection = nextDirection;
        }

        public Direction get(Direction previous){
            return nextDirection.get(previous);
        }
    }

    private Direction direction;
    private Point position;
    private TurnOption turnOption;

    public Cart(Direction direction, Point position) {
        this.direction = direction;
        this.position = position;
        this.turnOption = LEFT;
    }

    public Point move() {
        position = new Point(this.position.getX() + this.direction.getNext().getX(),
                this.position.getY() + this.direction.getNext().getY());
        return position;
    }

    public Point getPosition() {
        return position;
    }

    public void setDirection(Character symbol) {
        switch(symbol){
            case '/':
                direction = SLASH.get(direction);
                break;
            case '\\':
                direction = BACKSLASH.get(direction);
                break;
            case '+':
                direction = turnOption.get(direction);
                changeTurnOption();
                break;
            default:
                break;
        }

        this.direction = direction;
    }

    public void changeTurnOption(){
        switch (turnOption){
            case LEFT:
                turnOption = STRAIGHT;
                break;
            case STRAIGHT:
                turnOption = RIGHT;
                break;
            case RIGHT:
                turnOption = LEFT;
                break;
        }
    }

    @Override
    public int compareTo(Cart c) {
        if(this.getPosition().getX() == c.getPosition().getX()){
            if(this.getPosition().getY() >= c.getPosition().getY()){
                return 1;
            }else{
                return -1;
            }
        }
        else{
            if(this.getPosition().getX() > c.getPosition().getX()){
                return 1;
            }else{
                return -1;
            }
        }
    }

    @Override
    public String toString() {
        return "Cart{" +
                "direction=" + direction +
                ", position=" + position +
                ", turnOption=" + turnOption +
                '}';
    }
}
