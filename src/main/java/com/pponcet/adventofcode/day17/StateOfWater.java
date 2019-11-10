package com.pponcet.adventofcode.day17;

public class StateOfWater {
    private boolean spread;
    private boolean bottom;

    public StateOfWater(){
        this.spread = false;
        this.bottom = false;
    }

    public boolean canSpread() {
        return spread;
    }

    public boolean reachBottom() {
        return bottom;
    }

    public StateOfWater spread(boolean c){
        spread = c;
        return this;
    }

    public StateOfWater bottom(boolean b){
        bottom = b;
        return this;
    }
}
