package com.pponcet.adventofcode.day15;

public class Unit implements Comparable<Unit>{
    public static final Unit noSuitableUnit = new Unit(Side.NEUTRAL, Point.nullPoint);

    public enum Side{
        GOBLIN('G'),
        ELF('E'),
        NEUTRAL('N');

        private final Character symbol;

        Side(Character symbol) {
            this.symbol = symbol;
        }

        public Character getSymbol() {
            return symbol;
        }
    }

    private final Side side;
    private int hp;
    private Point position;
    private boolean alive;

    public Unit(Side side, Point position) {
        this.side = side;
        this.hp = 200;
        this.position = position;
        this.alive = true;
    }

    public void takeAHit(){
        hp -= 3;
        if(hp <= 0){
            alive = false;
            hp = 0;
            position = Point.nullPoint;
        }
    }

    public Side getSide() {
        return side;
    }

    public int getHp() {
        return hp;
    }

    public Point getPosition() {
        return position;
    }

    public void setPosition(Point position) {
        this.position = position;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    @Override
    public int compareTo(Unit u) {
        if(this.getPosition().getX() == u.getPosition().getX()){
            if(this.getPosition().getY() >= u.getPosition().getY()){
                return 1;
            }else{
                return -1;
            }
        }
        else{
            if(this.getPosition().getY() == u.getPosition().getY()){
                if(this.getPosition().getX() > u.getPosition().getX()){
                    return 1;
                }else{
                    return -1;
                }
            }else{
                if(this.getPosition().getY() > u.getPosition().getY()){
                    return 1;
                }else{
                    return -1;
                }
            }

        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Unit)) return false;

        Unit unit = (Unit) o;

        if (getSide() != unit.getSide()) return false;
        return getPosition() != null ? getPosition().equals(unit.getPosition()) : unit.getPosition() == null;

    }

    @Override
    public int hashCode() {
        int result = getSide() != null ? getSide().hashCode() : 0;
        result = 31 * result + (getPosition() != null ? getPosition().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Unit{" +
                "side=" + side +
                ", hp=" + hp +
                ", position=" + position +
                '}';
    }
}
